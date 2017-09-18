package io.renren.controller.publicModule;

import io.renren.constant.ControllerConstant;
import io.renren.enums.WeChatEnum;
import io.renren.model.dto.SignParameter;
import io.renren.utils.AliyunFileTypeUtil;
import io.renren.utils.HttpsClient;
import io.renren.utils.RRException;
import io.renren.utils.Sign;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.google.code.kaptcha.Producer;

@Controller("commonConroller")
@RequestMapping("/publicModule/common")
public class CommonConroller {
	
	// 日志操作
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
	public static String accessId = "LTAIyY1y6mvPjVip";
	public static String accessKey = "zio4dKlkF6424JE3gUxa8vzPyBcAaF";
	public static String bucket = "guanyukeji-static";
	//public static String dir = "mytest_";
	public static String host = "http://" + bucket + "." + endpoint;
	public static String CALL_BACK_URL = "http://wrs.gykjewm.com/sys/aliOss/callBack";
	public static String CDN_URL = "http://static.gykjewm.com/";
	
	/** * 回调参数文件名 */
	public static String FILE_NAME = "filename";
	
	// 静态变量
	public static String SUCCESS = "ok";
	public static String FAIL = "error";
	
	public static Map<String,String> CALL_BACK_URL_MAP = new HashMap<String, String>();
	
	static {
		CALL_BACK_URL_MAP.put("smartMsgPic", "http://wrs.gykjewm.com/smart/callBack/msgPic");
	}
	
	@Autowired
	private Producer producer;
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到 session 
        request.getSession().setAttribute(ControllerConstant.CHECK_CODE_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 获取微信签名
	 * @param request
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWeChatSign")
	@ResponseBody
    public Map<String, String> getWeChatSign(HttpServletRequest request, String url) throws Exception{
    	
    	ServletContext application = request.getSession().getServletContext();
    	
    	String httpUrl = request.getParameter("url");
  		String tickets = "ticket";
  		String access_tocken = "access_token";
  		
  		Map<String, String > map = null;
  		String ticket = (String)application.getAttribute(tickets);
  		
  		
  		if(ticket != null && !ticket.equals(""))
  		{
  			Long timeStamp = (Long)application.getAttribute("time");
  			if(timeStamp == null)
  			{
  				timeStamp = 3l;
  			}
  			Long cha = (System.currentTimeMillis()-timeStamp)/(1000*60*60);
  			
  			if(cha > 2)
  			{
  				
  				// 重新生成ticket  
  				String back = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TOCHEN.getValue());
				String back2 = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TICHET.getValue() + JSONObject.fromObject(back).getString(access_tocken));
				application.setAttribute(tickets, JSONObject.fromObject(back2).getString(tickets));
				application.setAttribute("time", System.currentTimeMillis());
				map = Sign.sign(JSONObject.fromObject(back2).getString(tickets), httpUrl);
				
				// 系统输出
				System.out.println(back);
				System.out.println(back2);
  			}else{
  				map = Sign.sign(ticket, httpUrl);
  			}
  		}else{
  			// 重新生成ticket  
 		    String back = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TOCHEN.getValue());
			String back2 = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TICHET.getValue() + JSONObject.fromObject(back).getString(access_tocken));
			
			application.setAttribute(tickets, JSONObject.fromObject(back2).getString(tickets));
			application.setAttribute("time", System.currentTimeMillis());
			map = Sign.sign(JSONObject.fromObject(back2).getString(tickets), httpUrl);
			// 系统输出
			System.out.println(back);
			System.out.println(back2);
  		}
  		
  		return map;
    }
	
	
	
	/**
	 * 阿里云获取图片上传签名
	 * @param request
	 * @param response
	 * @param userId 用户的id
	 * @param type 判断是那种类型图片，设置回调地址
	 * @return
	 */
	@RequestMapping("/oss/getSign")
	@ResponseBody
	public Map<String, String> getSignTimeOut(HttpServletRequest request, HttpServletResponse response, SignParameter parameter ){
		
        // 设置参数
		if ( parameter.getType() == null){
			RRException e = new RRException("参数错误 ");
			throw e;
		}
		SignParameter parameterNew = AliyunFileTypeUtil.getSignParameter(parameter.getType());
		
		System.out.println(parameterNew.toString());
        
		if ( parameter.getUserId() != null) {
			parameterNew.setUserId(parameter.getUserId());
		} else {
			
		}
        
        return buildBackData(parameterNew,response, request);
	}
	
	/**
	 * 负责具体签名业务的处理
	 * @param id
	 * @param type
	 * @return
	 */
	private Map<String, String> buildBackData(SignParameter parameter,HttpServletResponse response, HttpServletRequest request){
		
		// 获取所有参数
		StringBuffer buffer = new StringBuffer("");
		Map<String, String[]> mapp = request.getParameterMap();
		Set<Entry<String, String[]>> set= mapp.entrySet();
		for(Entry<String, String[]> entry : set){
			buffer.append("\"" +entry.getKey() + "\":");
			buffer.append("\"" +request.getParameter(entry.getKey()).toString() + "\",");
		}
		
		System.out.println(buffer.toString());
		
		Map<String, String> respMap = null;
		
		if (parameter.getStatus().equals(SUCCESS)){
			OSSClient client = new OSSClient(endpoint, accessId, accessKey);
			// 负责回调方法的处理
	        JSONObject json = new JSONObject();
	        json.element("callbackUrl", parameter.getCallBackUrl());
	        json.element("callbackBody", "{\"format\":${imageInfo.format},"+ buffer.toString() +"\"bucket\":${bucket},\"object\":${object},"+
	        "\"filename\":${object},\"size\":${size},\"mimeType\":${mimeType}}");
	        json.element("callbackBodyType", "application/json");
	        //json.element("callbackHost", "oss-demo.aliyuncs.com");
	        String callBack = json.toString();
	        
	        
	        try { 	
	        	// 过期时间
	        	long expireTime = 100;
	        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
	            Date expiration = new Date(expireEndTime);
	            PolicyConditions policyConds = new PolicyConditions();
	            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
	            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, parameter.getDocument() + parameter.getDirPrefix());

	            String postPolicy = client.generatePostPolicy(expiration, policyConds);
	            byte[] binaryData = postPolicy.getBytes("utf-8");
	            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
	            String postSignature = client.calculatePostSignature(postPolicy);
	            String callBackeEncode = BinaryUtil.toBase64String(callBack.getBytes("utf-8"));
	            
	            respMap = new LinkedHashMap<String, String>();
	            respMap.put("accessid", accessId);
	            respMap.put("policy", encodedPolicy);
	            respMap.put("signature", postSignature);
	            //respMap.put("expire", formatISO8601Date(expiration));
	            respMap.put("dir", parameter.getDocument() + parameter.getDirPrefix());
	            respMap.put("host", host);
	            respMap.put("expire", String.valueOf(expireEndTime / 1000));
	            respMap.put("callback", callBackeEncode);
	            //respMap.put("dir_head", parameter.getDocument());
	            respMap.put("status", parameter.getStatus());
	            //JSONObject ja1 = JSONObject.fromObject(respMap);
	            //System.out.println(json.getString("callbackUrl"));
	            //System.out.println(ja1.toString());
	            response.setHeader("Access-Control-Allow-Origin", "*");
	            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
	            //response(request, response, ja1.toString());
	            //FileOperation.writeTxtFileFast(ja1.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
		} else {
			
			respMap = new LinkedHashMap<String, String>();
			respMap.put("status", parameter.getStatus());
			respMap.put("msg", parameter.getMsg());
		}
		return respMap;
	}
	
}

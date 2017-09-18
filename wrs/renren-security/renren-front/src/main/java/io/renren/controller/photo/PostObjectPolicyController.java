package io.renren.controller.photo;

import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.enums.TypeEnum;
import io.renren.model.dto.SignParameter;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoPicClassService;
import io.renren.service.PhotoPicHeadService;
import io.renren.service.PhotoPicPublicService;
import io.renren.service.PhotoPicSchoolService;
import io.renren.service.PhotoPicUserService;
import io.renren.service.PhotoTypeService;
import io.renren.utils.AliyunFileTypeUtil;
import io.renren.utils.OssCallBackUtil;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;

@RestController
public class PostObjectPolicyController  {
	
	// 日志操作
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
	public static String accessId = "LTAIyY1y6mvPjVip";
	public static String accessKey = "zio4dKlkF6424JE3gUxa8vzPyBcAaF";
	public static String bucket = "guanyukeji-static";
	//public static String dir = "mytest_";
	public static String host = "http://" + bucket + "." + endpoint;
	public static String CALL_BACK_URL = "http://localhost:8080/wrs/sys/aliOss/callBack";
	public static String CDN_URL = "http://static.gykjewm.com/";
	
	/** * 回调参数文件名 */
	public static String FILE_NAME = "filename"; 
	
	// 静态变量
	public static String SUCCESS = "ok";
	public static String FAIL = "error";
	
	// 注入业务数据操作类
	@Autowired
	private PhotoPicClassService photoPicClassService;
	@Autowired
	private PhotoPicHeadService photoPicHeadService;
	@Autowired
	private PhotoPicUserService photoPicUserService;
	@Autowired
	private PhotoPicSchoolService photoPicSchoolService;
	@Autowired
	private PhotoPicPublicService photoPicPublicService;
	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	
	@Autowired 
	private PhotoTypeService photoTypeService;
	
	
	/**
	 * 获取图片上传签名
	 * @param request
	 * @param response
	 * @param userId 用户的id
	 * @param type 判断是那种类型图片，设置回调地址
	 * @return
	 */
	@RequestMapping("/front/oss/getSign")
	public Map<String, String> getSignTimeOut(HttpServletRequest request, HttpServletResponse response, SignParameter parameter ){
		
        // 设置参数
		SignParameter parameterNew = AliyunFileTypeUtil.getSignParameter(parameter.getType());
        if (parameter != null && parameter.getUserId() == null) {
        	PhotoFrontUserEntity fUser = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
    		
        	if (fUser == null || fUser.getId() == null) {
        		parameterNew.setUserId(0L);
        	} else {
        		parameterNew.setUserId(fUser.getId());
        	}
        	
        }
        
        return buildBackData(parameterNew,response, request);
	}
	
	
	@RequestMapping(value="/oss/callBack")
	public JSONObject callBace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println("calbackFRont");
		JSONObject jsona = OssCallBackUtil.analyzeCallBackData(request);
		JSONObject json = new JSONObject();
		if (jsona.getBoolean(OssCallBackUtil.VERIFY))
		{
			// 返回阿里云通知信息
			json.element("Status", "OK");
			response.setStatus(HttpServletResponse.SC_OK);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			
			// 从json中获取响应的数据
			Integer type = jsona.containsKey("type") && jsona.getString("type") != null ? 
					jsona.getInt("type") : null;
			// 进行参数判断
			if (type == null){
				logger.error("type值为空" + jsona.toString());
				return json;
			}else{
				try{
					
					if (type.equals(TypeEnum.PHOTO_CLASS.getType())){
			        	// 班级照片
						
			        	
			        } else if (type.equals(TypeEnum.PHOTO_HOME.getType())){
			        	// 家庭照片
			        	
			        	
			        } else if (type.equals(TypeEnum.PHOTO_SCHOOL.getType())){
			        	// 学校照片
			        	
			        	
			        } else if (type.equals(TypeEnum.PHOTO_USER.getType())){
			        	// 用户照片
			        	
			        	
			        } else if (type.equals(TypeEnum.PHOTO_USER_HEAD.getType())){
			        	// 用户头像
			        	
			        	// 先判断是否已经上传头像
			        	//PhotoPicHeadEntity head = this.photoPicHeadService.selectById(jsona.getInt("user_id"));
			        	PhotoFrontUserEntity user = this.photoFrontUserService.selectById(jsona.getInt("user_id"));
			        	if(user != null){
			        		
			        		PhotoFrontUserEntity userUpdate = new PhotoFrontUserEntity();
			        		userUpdate.setId(user.getId());
			        		userUpdate.setHeadImg(jsona.getString(FILE_NAME));
			        		
			        		this.photoFrontUserService.updateById(userUpdate);
			        		//this.sysUserService.updateById()
				        	//this.sysUserService.update(user);
			        	}
			        	
			        	
			        }  else {
			        	
			        	// 无此类型值
			        }
				} catch(Exception e){
					logger.error("回调异常", e);
				}
				
			}
			
			return json;
		} else {
			json.element("Status", "verdify not ok");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.addHeader("Content-Length", String.valueOf(json.toString().length()));
			return json;
		}
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

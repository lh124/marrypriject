package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryMainEntity;
import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.entity.married.ResultState;
import io.renren.service.married.MarryMainService;
import io.renren.service.married.MarryOrderMainService;
import io.renren.service.married.MarryOrdersService;
import io.renren.util.WeixinPayUtil;
import io.renren.utils.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("married/weixin")
public class PayController {
	
	@Autowired
	private MarryOrdersService marryOrdersService;
	@Autowired
	private MarryOrderMainService marryOrderMainService;
	@Autowired
	private MarryMainService marryMainService;
	
	//支付接口
	@RequestMapping("/pay")
    public R payparm(HttpServletRequest request, HttpServletResponse response){  
		String sb = "";
        try {  
        	Integer id = Integer.parseInt(request.getParameter("id"));
            Map<String, String> paraMap = getSign(id,request);  
            Map<String, Object> payMap = getPayData(id,paraMap);
            sb = JSONObject.fromObject(payMap).toString();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return R.ok().put("data", sb);
    }  
	
	
	/**
	 * 获取支付所需要的所有参数
	 * @throws Exception 
	 */
	public Map<String, Object> getPayData(Integer id,Map<String, String> paraMap) throws Exception{
		String appid = WeixinPayUtil.appid; 
		//将map转为String
        String xml = WeixinPayUtil.mapToXml(paraMap); 
        xml = new String(xml.toString().getBytes("UTF-8"));  
        String xmlStr = post(WeixinPayUtil.createOrderURL, xml);
        // 预付商品id  
        String prepay_id = "";  
        xmlStr = new String(xmlStr.toString().getBytes("GBK"));
        if (xmlStr.indexOf("SUCCESS") != -1) {  
            Map<String, String> map = WeixinPayUtil.xmlToMap(xmlStr);  
            prepay_id = (String) map.get("prepay_id");  
        }  
        String timeStamp = System.currentTimeMillis()/1000+"";  
        String nonceStr = UUID.randomUUID().toString().replace("-", "");  
        Map<String, String> payMap = new HashMap<String, String>();  
        payMap.put("appId", appid);  
        payMap.put("timeStamp", timeStamp);  
        payMap.put("nonceStr", nonceStr);  
        payMap.put("signType", "MD5");  
        payMap.put("package", "prepay_id=" + prepay_id);  
        String paySign = WeixinPayUtil.generateSignature(payMap, WeixinPayUtil.partnerkey);  
        payMap.put("pg", prepay_id);  
        payMap.put("paySign", paySign);  
        // 拼接并返回json  
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id="+prepay_id);
        map.put("paySign", paySign);
        map.put("signType", "MD5");
        map.put("appId", appid);
        map.put("id", id);
        return map;
	}
	
	/**
	 * 统一下单
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> getSign(Integer id,HttpServletRequest request) throws Exception{
		MarryOrdersEntity marryOrders = marryOrdersService.queryObject(id);
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		String attach = "";
		String body = "";
		Integer total_fee = 0;
		if(marryOrders.getOrderType() == 1){//直接下单的情况
			body = URLEncoder.encode(marryOrders.getMainDescribe(), "UTF-8");
			attach = URLEncoder.encode(marryOrders.getBusiness(), "UTF-8");
			total_fee = (new  Double(Double.valueOf(marryOrders.getMainPrice())*100)).intValue();
		}else{
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("orderId", marryOrders.getId());
			body = URLEncoder.encode("婚礼商品的购买", "UTF-8");
			attach = URLEncoder.encode("商品购买", "UTF-8");
			List<MarryOrderMainEntity> marryOrderMainList = marryOrderMainService.queryList(m);
			for (Iterator iterator2 = marryOrderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMainEntity.getMainId());
				total_fee += (new  Double(Double.valueOf(marryMain.getPrice())*100)).intValue();
			}
		}
		String appid = WeixinPayUtil.appid;  
        Map<String, String> paraMap = new HashMap<String, String>();  
        paraMap.put("appid", appid);  
        paraMap.put("attach", attach);  
        paraMap.put("body", body);  
        paraMap.put("mch_id", WeixinPayUtil.partner);//商户号 
        paraMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));//随机数  
        paraMap.put("sign_type", "MD5"); 
        paraMap.put("openid", user.getOpenid()); 
        paraMap.put("out_trade_no", marryOrders.getOrderNumber());  //订单号
        paraMap.put("spbill_create_ip", "47.92.117.143");//服务器IP地址  
        paraMap.put("total_fee", total_fee+"");  //订单价格
        paraMap.put("trade_type", "JSAPI");  //交易类型取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
        paraMap.put("notify_url", WeixinPayUtil.notify_url);// 此路径是微信服务器调用支付结果通知路径  
        String sign = WeixinPayUtil.generateSignature(paraMap, WeixinPayUtil.partnerkey);  
        paraMap.put("sign", sign);
        request.getSession().setAttribute("sign", sign);
		return paraMap;
	}
    
    public static String post(String url,String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
	}
    
    @ResponseBody
    @RequestMapping("notify")
    public ResultState notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultState resultState = new ResultState();
        String resXml = ""; // 反馈给微信服务器
        String notifyXml = convertStreamToString(request.getInputStream());// 微信支付系统发送的数据（<![CDATA[product_001]]>格式）
        // 验证签名
        JSONObject json = JSONObject.fromObject(WeixinPayUtil.xmlToMap(notifyXml.replace(">/n", ">")));
        System.out.println(json);
            if ("SUCCESS".equals(json.getString("result_code"))) {
            	String out_trade_no = json.getString("out_trade_no");
            	MarryOrdersEntity marryOrdersEntity = new MarryOrdersEntity();
            	marryOrdersEntity.setOrderNumber(out_trade_no);
            	EntityWrapper<MarryOrdersEntity> wrapper = new EntityWrapper<MarryOrdersEntity>(marryOrdersEntity);
            	marryOrdersEntity = marryOrdersService.selectOne(wrapper);
            	if(marryOrdersEntity == null){
            		resultState.setErrcode(-1);// 支付失败
                    resultState.setErrmsg("支付失败");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[支付失败]]></return_msg>" + "</xml> ";
            	}else{
            		if(!marryOrdersEntity.getMainPrice().equals(json.getInt("total_fee")/100)){
            			resultState.setErrcode(-1);// 支付失败
                        resultState.setErrmsg("支付失败");
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[支付失败]]></return_msg>" + "</xml> ";
            		}else{
            			marryOrdersEntity.setStates(1);
                        marryOrdersService.update(marryOrdersEntity);
                        request.getSession().setAttribute("jurisdiction", 1);
                        resultState.setErrcode(0);// 表示成功
                        resultState.setErrmsg("支付成功");
                        /**** 业务逻辑  保存openid之类的****/
                        // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            		}
            	}
            } else {
                resultState.setErrcode(-1);// 支付失败
                resultState.setErrmsg("支付失败");
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[支付失败]]></return_msg>" + "</xml> ";
            }
 
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        return resultState;
    }
    
    public String convertStreamToString(InputStream is) {   
    	   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
    	        StringBuilder sb = new StringBuilder();   
    	        String line = null;   
    	        try {   
    	            while ((line = reader.readLine()) != null) {   
    	                sb.append(line + "/n");   
    	            }   
    	        } catch (IOException e) {   
    	            e.printStackTrace();   
    	        } finally {   
    	            try {   
    	                is.close();   
    	            } catch (IOException e) {   
    	                e.printStackTrace();   
    	            }   

    	        }   
    	        return sb.toString();   
    	    }    
    
}

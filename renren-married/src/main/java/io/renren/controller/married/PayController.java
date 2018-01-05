package io.renren.controller.married;

import io.renren.util.WeixinPayUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayController {
	
	public static void main(String[] args) {
		payparm(null, null);
	}
	
    public static void payparm(HttpServletRequest request, HttpServletResponse response){  
        try {  
            // 获取openid  
            String openId = "o7__rjjocXdATM4sz0rYbt2z7SRw";  
            String appid = WeixinPayUtil.appid;  
            String paternerKey = WeixinPayUtil.appsecret;  
            String out_trade_no = "201701547842";  //订单号
            Map<String, String> paraMap = new HashMap<String, String>();  
            paraMap.put("appid", appid);  
            paraMap.put("attach", "支付测试");  
            paraMap.put("body", "测试购买支付");  
            paraMap.put("mch_id", WeixinPayUtil.partner);//商户号 
            paraMap.put("nonce_str", "234234242342342");//随机数  
            paraMap.put("openid", openId);  
            paraMap.put("out_trade_no", out_trade_no);  
            paraMap.put("spbill_create_ip", "123.12.12.123");//终端IP  APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。  
            paraMap.put("total_fee", "1");  //订单价格
            paraMap.put("trade_type", "JSAPI");  //交易类型取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
            paraMap.put("notify_url", WeixinPayUtil.notify_url);// 此路径是微信服务器调用支付结果通知路径  
            String sign = WeixinPayUtil.generateSignature(paraMap, paternerKey);  
            paraMap.put("sign", sign);  
            String xml = WeixinPayUtil.mapToXml(paraMap);  
            String xmlStr = post(WeixinPayUtil.createOrderURL, xml);
            System.out.println("--------"+xmlStr);
            // 预付商品id  
            String prepay_id = "";  
            if (xmlStr.indexOf("SUCCESS") != -1) {  
                Map<String, String> map = WeixinPayUtil.xmlToMap(xmlStr);  
                prepay_id = (String) map.get("prepay_id");  
            }  
            String timeStamp = System.currentTimeMillis()/1000+"";  
            String nonceStr = "423423424242";  
            Map<String, String> payMap = new HashMap<String, String>();  
            payMap.put("appId", appid);  
            payMap.put("timeStamp", timeStamp);  
            payMap.put("nonceStr", nonceStr);  
            payMap.put("signType", "MD5");  
            payMap.put("package", "prepay_id=" + prepay_id);  
            String paySign = WeixinPayUtil.generateSignature(payMap, paternerKey);  
            payMap.put("pg", prepay_id);  
            payMap.put("paySign", paySign);  
            // 拼接并返回json  
            StringBuilder sBuilder = new StringBuilder("[{");  
            sBuilder.append("appId:'").append(appid).append("',")  
                        .append("timeStamp:'").append(timeStamp).append("',")  
                        .append("nonceStr:'").append(nonceStr).append("',")  
                        .append("pg:'").append(prepay_id).append("',")  
                        .append("signType:'MD5',")  
                        .append("paySign:'").append(paySign).append("'");  
            sBuilder.append("}]");  
            response.getWriter().print(sBuilder.toString());  
            response.getWriter().close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
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
                    new InputStreamReader(conn.getInputStream()));
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
    
}

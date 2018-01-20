package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryBlessingEntity;
import io.renren.entity.married.MarryGetmoneyEntity;
import io.renren.entity.married.MarryRedmoneyDetailEntity;
import io.renren.entity.married.MarryRedmoneyMainEntity;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.entity.married.ResultState;
import io.renren.service.married.MarryBlessingService;
import io.renren.service.married.MarryGetmoneyService;
import io.renren.service.married.MarryRedmoneyDetailService;
import io.renren.service.married.MarryRedmoneyMainService;
import io.renren.service.married.MarryWeddingService;
import io.renren.util.WeixinPayUtil;
import io.renren.utils.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
@SuppressWarnings("deprecation")
@RestController
@RequestMapping("married/weixin")
public class MarryWeixinMoneyController {
	
	@Autowired
	private MarryGetmoneyService marryGetmoneyService;//用户提现表，余额表
	@Autowired
	private MarryRedmoneyDetailService marryRedmoneyDetailService;//红包的详细表
	@Autowired
	private MarryRedmoneyMainService marryRedmoneyMainService;//红包的主表
	@Autowired
	private MarryWeddingService marryWeddingService;
	@Autowired
	private MarryBlessingService marryBlessingService;
	
	/**
	 * 将自己账号余额提现
	 * @param request
	 * @return
	 */
	@RequestMapping("/get_money_tome")
	public R get_money(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		MarryGetmoneyEntity mg = marryGetmoneyService.queryObject(id);
		try {
			String xml = WeixinPayUtil.mapToXml(pay(mg));
			String result = creaCa("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xml);
			JSONObject json = JSONObject.fromObject(WeixinPayUtil.xmlToMap(result.replace(">/n", ">")));
			if("SUCCESS".equals(json.getString("result_code"))){
				mg.setTotalFee(0.00);
				marryGetmoneyService.update(mg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok();
	}
	
	public static void main(String[] args) {
		MarryGetmoneyEntity mg = new MarryGetmoneyEntity();
		mg.setOpenid("oZSiWxOxNIVH7s8wxiORTchfGxEo");
		mg.setTotalFee(0.01);
		try {
			String xml = WeixinPayUtil.mapToXml(pay(mg));
			String result = creaCa("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xml);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前婚礼下的所有红包记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/findAllredmoneydetail")
	public R findAllredmoneydetail(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weddingId", request.getParameter("weddingId"));
		map.put("offset", 0);
		map.put("limit", 200);
		map.put("states", 0);
		return R.ok().put("list", marryRedmoneyDetailService.queryList(map));
	}
	
	/**
	 * 根据id查询红包且放入自己的账号中
	 * @param request
	 * @return
	 */
	@RequestMapping("/findredmoneydetail")
	public R findredmoneydetail(HttpServletRequest request){
		if("undefined".equals(request.getParameter("id"))){
			return R.error("下手太慢了，已被别人领取了");
		}
		Integer id= Integer.parseInt(request.getParameter("id"));
		MarryRedmoneyDetailEntity marryRedmoneyDetail = marryRedmoneyDetailService.queryObject(id);
		if(marryRedmoneyDetail.getStates()==1){
			return R.error("下手太慢了，已被别人领取了");
		}else{
			MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
			MarryGetmoneyEntity marryGetmoney = new MarryGetmoneyEntity();
			marryGetmoney.setOpenid(user.getOpenid());
			EntityWrapper<MarryGetmoneyEntity> wrapper = new EntityWrapper<MarryGetmoneyEntity>(marryGetmoney);
			MarryGetmoneyEntity mg = marryGetmoneyService.selectOne(wrapper);
			if(mg == null){
				MarryGetmoneyEntity mgm = new MarryGetmoneyEntity();
				mgm.setOpenid(user.getOpenid());
				mgm.setTotalFee(marryRedmoneyDetail.getTotalFee());
				mgm.setGmtModifiedtime(new Date());
				marryGetmoneyService.save(mgm);
			}else{
				mg.setGmtModifiedtime(new Date());
				mg.setTotalFee(mg.getTotalFee()+marryRedmoneyDetail.getTotalFee());
				marryGetmoneyService.update(mg);
			}
			marryRedmoneyDetail.setUserId(user.getId());
			marryRedmoneyDetail.setStates(1);
			marryRedmoneyDetailService.update(marryRedmoneyDetail);
		}
		return R.ok().put("totalFee", marryRedmoneyDetail.getTotalFee());
	}
	
	public static String creaCa(String url,String postDataXML) throws Exception {
		String result = "";
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("E:/web/webroot/wrs/statics/marry/apiclient_cert.p12"));
        try {
            keyStore.load(instream, WeixinPayUtil.partner.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WeixinPayUtil.partner.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
        	HttpPost httpget = new HttpPost(url);
            // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpget.setEntity(postEntity);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(
                    		           new InputStreamReader(entity.getContent(),"utf-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                    	result += text + "\n";
                    }
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }
	
	public static Map<String, String> pay(MarryGetmoneyEntity marryGetmoney) throws Exception{ 
		 Map<String, String> payMap = new HashMap<String, String>(); 
		 payMap.put("mch_appid", WeixinPayUtil.appid);
		 payMap.put("mchid", WeixinPayUtil.partner);
		 payMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));//随机数
		 payMap.put("partner_trade_no", "494955"+new Date().getTime());//商户订单号
		 payMap.put("openid", marryGetmoney.getOpenid());
		 payMap.put("check_name", "NO_CHECK");//NO_CHECK：不校验真实姓名FORCE_CHECK：强校验真实姓名
		 payMap.put("amount", (new  Double(Double.valueOf(marryGetmoney.getTotalFee())*100)).intValue()+"");//金额
		 payMap.put("desc", "红包提现");
		 payMap.put("spbill_create_ip", "47.92.117.143");
		 String sign = WeixinPayUtil.generateSignature(payMap, WeixinPayUtil.partnerkey);
		 payMap.put("sign", sign);
		return payMap;
	}
	
	/**
	 * 查询自己账号余额
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findAllgetMoney")
	public R findAllgetMoney(HttpServletRequest request, HttpServletResponse response){  
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);  
		MarryGetmoneyEntity marryGetmoney = new MarryGetmoneyEntity();
		marryGetmoney.setOpenid(user.getOpenid());
		EntityWrapper<MarryGetmoneyEntity> wrapper = new EntityWrapper<MarryGetmoneyEntity>(marryGetmoney);
		MarryGetmoneyEntity mg = marryGetmoneyService.selectOne(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pic", user.getPic());
		map.put("obj", mg);
		
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> w = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = marryWeddingService.selectOne(w);
		if(marryWedding == null){
			map.put("blessing", null);
		}else{
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("weddingId", marryWedding.getId());
			m.put("states",1);
			m.put("blessingtype", 2);
			List<MarryBlessingEntity> list2 = marryBlessingService.queryList(m);//红包祝福
			map.put("blessing", list2);
		}
		return R.ok().put("map", map);
	}
	
	/**
	 * 用户发送红包时将钱放入对公账号里面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/moneyMainSave")
	public R payparm(HttpServletRequest request, HttpServletResponse response){  
		Integer totalNum = Integer.parseInt(request.getParameter("total"));//红包个数
		double total_fee = 0.00;
		String sb = "";
		total_fee = (new  Double(Double.valueOf(request.getParameter("total_fee"))*100)).intValue();
		Integer id = saveMoneyMain(total_fee, request, totalNum);//将红包主表数据进行添加
		try {
			Map<String, String> paraMap = getSign(request,id,request.getParameter("content"));
			MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
			MarryWeddingEntity marryWedding = new MarryWeddingEntity();
			marryWedding.setUserId(user.getId());
			EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
			Map<String, Object> payMap = getPayData(marryWeddingService.selectOne(wrapper).getId(),paraMap);
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
        System.out.println(xmlStr);
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
	
	public static String post(String url,String param) throws FileNotFoundException {
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
                result += line+"\n";
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
	
	/**
	 * 统一下单
	 * @throws Exception 
	 */
	public Map<String, String> getSign(HttpServletRequest request,Integer id,String content) throws Exception{
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
		MarryRedmoneyMainEntity marryRedmoneyMain = marryRedmoneyMainService.queryObject(id);
		String appid = WeixinPayUtil.appid;  
        Map<String, String> paraMap = new HashMap<String, String>();  
        paraMap.put("appid", appid);  
        paraMap.put("attach", URLEncoder.encode("微信红包", "UTF-8"));  
        paraMap.put("body", URLEncoder.encode((content==null||"".equals(content))?"恭喜发财，大吉大利":content, "UTF-8"));  
        paraMap.put("mch_id", WeixinPayUtil.partner);//商户号 
        paraMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));//随机数  
        paraMap.put("sign_type", "MD5"); 
        paraMap.put("openid", user.getOpenid()); 
        paraMap.put("out_trade_no", marryRedmoneyMain.getOutTradeNo());  //订单号
        paraMap.put("spbill_create_ip", "47.92.117.143");//服务器IP地址  
        paraMap.put("total_fee", (new  Double(Double.valueOf(marryRedmoneyMain.getTotalFee())*100)).intValue()+"");  //订单价格
        paraMap.put("trade_type", "JSAPI");  //交易类型取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
        paraMap.put("notify_url", "http://wrs.gykjewm.com/married/weixin/moneyNotify");// 此路径是微信服务器调用支付结果通知路径  
        String sign = WeixinPayUtil.generateSignature(paraMap, WeixinPayUtil.partnerkey);  
        paraMap.put("sign", sign);
        request.getSession().setAttribute("sign", sign);
		return paraMap;
	}
	
	@ResponseBody
    @RequestMapping("moneyNotify")
    public ResultState notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultState resultState = new ResultState();
        String resXml = ""; // 反馈给微信服务器
        String notifyXml = convertStreamToString(request.getInputStream());// 微信支付系统发送的数据（<![CDATA[product_001]]>格式）
        // 验证签名
        JSONObject json = JSONObject.fromObject(WeixinPayUtil.xmlToMap(notifyXml.replace(">/n", ">")));
        if ("SUCCESS".equals(json.getString("result_code"))) {
        	String out_trade_no = json.getString("out_trade_no");
        	MarryRedmoneyMainEntity marryRedmoneyMain = new MarryRedmoneyMainEntity();
        	marryRedmoneyMain.setOutTradeNo(out_trade_no);
        	EntityWrapper<MarryRedmoneyMainEntity> wrapper = new EntityWrapper<MarryRedmoneyMainEntity>(marryRedmoneyMain);
        	marryRedmoneyMain = marryRedmoneyMainService.selectOne(wrapper);
            saveMoneyDetail(marryRedmoneyMain.getId());//按红包个数保存详细红包
            resultState.setErrcode(0);// 表示成功
            resultState.setErrmsg("支付成功");
            /**** 业务逻辑  保存openid之类的****/
            // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
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
	
	private void saveMoneyDetail(Integer id){
		MarryRedmoneyMainEntity marryRedmoneyMain = marryRedmoneyMainService.queryObject(id);
		MarryWeddingEntity mw = new MarryWeddingEntity();
		mw.setUserId(marryRedmoneyMain.getUserId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		double total = 0.00;
		for (int i = 0; i < marryRedmoneyMain.getTotalNum()-1; i++) {
			double boundedDouble = new Random().nextDouble() * (marryRedmoneyMain.getTotalFee()-total);
			String s = String.format("%.2f", boundedDouble);
			MarryRedmoneyDetailEntity marryRedmoneyDetail = new MarryRedmoneyDetailEntity();
			marryRedmoneyDetail.setGmtModifiedtime(new Date());
			marryRedmoneyDetail.setStates(0);
			marryRedmoneyDetail.setTotalFee(new  Double(s));
			marryRedmoneyDetail.setWeddingId(mw.getId());
			marryRedmoneyDetailService.save(marryRedmoneyDetail);
			total += new  Double(s);
		}
		MarryRedmoneyDetailEntity marryRedmoneyDetail = new MarryRedmoneyDetailEntity();
		marryRedmoneyDetail.setGmtModifiedtime(new Date());
		marryRedmoneyDetail.setStates(0);
		marryRedmoneyDetail.setTotalFee(new  Double(String.format("%.2f", (marryRedmoneyMain.getTotalFee()-total))));
		marryRedmoneyDetail.setWeddingId(mw.getId());
		marryRedmoneyDetailService.save(marryRedmoneyDetail);
	}
	
//	public static void main(String[] args) {
//		   double total = 0.00;
//		   double max = 10.00;
//		   int length = 5;
//		   for (int i = 0; i < length-1; i++) {
//			   double boundedDouble = new Random().nextDouble() * (max-total);
//	           // 根据格式化器格式化数据
//			   System.out.println(boundedDouble);
//			   String s = String.format("%.2f", boundedDouble);
//			   System.out.println(s);
//			   total += new  Double(s);
//			   System.out.println((max-total)+"---------------------total="+total);
//		   }
//		   System.out.println(String.format("%.2f", (max-total))+"--------------"+max+"---------"+total);
//	}
	
	private Integer saveMoneyMain(double total_fee,HttpServletRequest request,Integer totalNum){
		String orderNumber = "494955"+new Date().getTime();
		MarriedUserEntity user = (MarriedUserEntity)request.getSession().getAttribute(ControllerConstant.SESSION_MARRIED_USER_KEY);
    	MarryRedmoneyMainEntity marryRedmoneyMain = new MarryRedmoneyMainEntity();
    	marryRedmoneyMain.setGmtModifiedtime(new Date());
    	marryRedmoneyMain.setTotalFee(total_fee);
    	marryRedmoneyMain.setUserId(user.getId());
    	marryRedmoneyMain.setTotalNum(totalNum);
    	marryRedmoneyMain.setStates(0);
    	marryRedmoneyMain.setOutTradeNo(orderNumber);
    	marryRedmoneyMainService.insert(marryRedmoneyMain);
    	return marryRedmoneyMain.getId();
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

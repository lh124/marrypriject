package io.renren.controller.married;

import io.renren.constant.ControllerConstant;
import io.renren.entity.SysUserEntity;
import io.renren.entity.married.MarriedUserEntity;
import io.renren.entity.married.MarryBlessingEntity;
import io.renren.entity.married.MarryCartEntity;
import io.renren.entity.married.MarryGetmoneyEntity;
import io.renren.entity.married.MarryHelpEntity;
import io.renren.entity.married.MarryMain;
import io.renren.entity.married.MarryMainEntity;
import io.renren.entity.married.MarryOrderMainEntity;
import io.renren.entity.married.MarryOrdersEntity;
import io.renren.entity.married.MarryParticipateEntity;
import io.renren.entity.married.MarryPhotoEntity;
import io.renren.entity.married.MarryRedmoneyDetailEntity;
import io.renren.entity.married.MarryRedmoneyMainEntity;
import io.renren.entity.married.MarryScreenEntity;
import io.renren.entity.married.MarrySignEntity;
import io.renren.entity.married.MarryWeddingEntity;
import io.renren.entity.married.OrderAndMain;
import io.renren.entity.married.ResultState;
import io.renren.service.SysUserService;
import io.renren.service.married.MarriedUserService;
import io.renren.service.married.MarryBlessingService;
import io.renren.service.married.MarryCartService;
import io.renren.service.married.MarryGetmoneyService;
import io.renren.service.married.MarryHelpService;
import io.renren.service.married.MarryImgService;
import io.renren.service.married.MarryMainService;
import io.renren.service.married.MarryOrderMainService;
import io.renren.service.married.MarryOrdersService;
import io.renren.service.married.MarryParticipateService;
import io.renren.service.married.MarryPhotoService;
import io.renren.service.married.MarryRedmoneyDetailService;
import io.renren.service.married.MarryRedmoneyMainService;
import io.renren.service.married.MarryScreenService;
import io.renren.service.married.MarrySignService;
import io.renren.service.married.MarryWeddingService;
import io.renren.util.OssUploadUtil;
import io.renren.util.SessionContext;
import io.renren.util.WeixinSmallPayUtil;
import io.renren.util.WeixinUtil;
import io.renren.util.WeixinUtilApp;
import io.renren.utils.R;
import io.renren.utils.RRException;

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import redis.clients.jedis.Jedis;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("married/small/smallProgram")
public class SmallProgramController {
	@Autowired
	private MarryMainService marryMainService;

	private final static String JEDISPATH = "127.0.0.1";
	private final static String DATA = "data";
	@Autowired
	private MarryWeddingService marryWeddingService;
	@Autowired
	private MarryParticipateService marryParticipateService;
	@Autowired
	private MarriedUserService marriedUserService;
	@Autowired
	private MarrySignService marrySignService;
	@Autowired
	private MarryImgService marryImgService;
	@Autowired
	private MarryBlessingService marryBlessingService;
	@Autowired
	private MarryPhotoService marryPhotoService;
	@Autowired
	private MarryHelpService marryHelpService;
	@Autowired
	private MarryCartService marryCartService;
	@Autowired
	private MarryOrdersService marryOrdersService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private MarryScreenService marryScreenService;
	@Autowired
	private MarryOrderMainService marryOrderMainService;

	@Autowired
	private MarryGetmoneyService marryGetmoneyService;//用户提现表，余额表
	@Autowired
	private MarryRedmoneyDetailService marryRedmoneyDetailService;//红包的详细表
	@Autowired
	private MarryRedmoneyMainService marryRedmoneyMainService;//红包的主表
	@Autowired
	private MarriedUserService marryUserService;

	private final static String FILEPATH = "http://static.gykjewm.com/";


	@RequestMapping("/main")
	public R main(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		if(request.getParameter("token") == null){
			if(type.equals("userLogin")){
				//小程序端进入首页默认登录接口
				return userLogin(request);//返回参数中有：userId（用户id），token（为后面的接口验证登录所用），list（商品列表）
			}else if(type.equals("findObj")){
				return R.ok(findObj(request));
			}else if(type.equals("list")){
				return R.ok(list(request));
			}else if(type.equals("saveSign")){//保存签到记录
				return R.ok(saveSign(request));
			}else if(type.equals("attendawedding")){//保存参加婚礼
				return R.ok(attendawedding(request));
			}else if(type.equals("findWedding")){//查询婚礼记录
				return R.ok(findWedding(request));
			}else if(type.equals("saveBlessing")){//保存祝福
				return R.ok(saveBlessing(request));
			}else if(type.equals("fileweddingphoto")){//上传婚礼图片
				return R.ok().put("id", fileweddingphoto(request,response));
			}else if(type.equals("fileupload")){//上传资料图片
				return R.ok(fileupload(request,response));
			}
			else{
				return R.error("请重新登录");
			}
		}else {
			String token = request.getParameter("token");
			System.out.println("------获取的=="+token);
			if(!getToken(token)){
				return R.error(1000, "请重新登录");
			}
			if(type.equals("saveHelp")){//保存用户帮助记录
				return R.ok(saveHelp(request));
			}else if(type.equals("saveWeddingPhoto")){//保存所有的视频路径
				return R.ok(saveWeddingPhoto(request));
			}else if(type.equals("findAllWeddingPhoto")){//通过婚礼id查询所有的摄影展图片
				return R.ok(findAllWeddingPhoto(request));
			}else if(type.equals("getWeddingId")){//通过当前用户获取婚礼id
				return R.ok(getWeddingId(request));
			}else if(type.equals("blessManage")){//祝福管理
				return R.ok(blessManage(request));
			}else if(type.equals("filevideo")){//上传视频
				return R.ok(filevideo(request,response));
			}else if(type.equals("allweddingImg")){//查询所有的婚礼背景图片
				return R.ok(allweddingImg(request));
			}else if(type.equals("allSign")){//查询所有签到记录
				return R.ok(allSign(request));
			}else if(type.equals("allMeSign")){//查询自己所有的婚礼签到记录
				return R.ok(allMeSign(request));
			}else if(type.equals("alljieshouattendawedding")){//查询自己所接受邀请的所有婚礼
				return R.ok(alljieshouattendawedding(request));
			}else if(type.equals("allattendawedding")){//查询所有参加婚礼的人
				return R.ok(allattendawedding(request));
			}else if(type.equals("saveWeddingInfo")){//保存婚礼信息
				return R.ok(saveWeddingInfo(request));
			}else if(type.equals("cartSave")){//保存数据到购物车
				return R.ok(cartSave(request));
			}else if(type.equals("cartList")){//购物车列表
				return R.ok(cartList(request)); 
			}else if(type.equals("deletemarrycart")){//删除购物车
				return R.ok(deletemarrycart(request));
			}else if(type.equals("submitCart")){//购物车结算
				return R.ok(submitCart(request));
			}else if(type.equals("saveScreen")){//屏幕管理
				return R.ok(saveScreen(request));
			}else if(type.equals("getScreen")){//获取屏幕管理的内容
				return R.ok(getScreen(request));
			}else if(type.equals("jurisdiction")){
				return R.ok().put(DATA, jurisdiction(request));
			}else if(type.equals("getAllWeddingPhoto")){//播放婚礼图片视频
				return R.ok(getAllWeddingPhoto(request));
			}else if(type.equals("saveOrder")){//用户保存订单
				return R.ok(saveOrder(request));
			}else if (type.equals("deleteOrder")){// 用户删除订单
				return R.ok( deleteOrder(request));
			}else if(type.equals("findOrderlist")){//查询当前用户的所有订单
				return R.ok(findOrderlist(request));
			}else if(type.equals("findOrder")){
				return R.ok(findOrder(request));
			}else if(type.equals("pay")){//订单支付
				return R.ok(pay(request,response));
			}else if (type.equals("get_money_tome")){//提现
				return R.ok(get_money_tome(request));
			}else if (type.equals("findAllredmoneydetail")){//获取红包记录
				return R.ok(findAllredmoneydetail(request));
			}else if (type.equals("findredmoneydetail")){// 根据id查询红包且放入自己的账号中
				return R.ok(findredmoneydetail(request));
			}else if (type.equals("findAllgetMoney")){// 查询自己账号余额
				return R.ok(findAllgetMoney(request,response));
			}	else if (type.equals("moneyMainSave")){// 用户发送红包时将钱放入对公账号里面
				return R.ok(moneyMainSave(request,response));
			}	else if (type.equals("blesspay")){//发送红包祝福时的接口
				return R.ok(blesspay(request,response));
			}
		}
		return null;
	}

	//===========================================首页================================//
	//微信查询商品接口
		@RequestMapping("/findObj")
		public R findObj(HttpServletRequest request){
			Integer id = Integer.parseInt(request.getParameter("id"));
			return R.ok().put("obj", marryMainService.queryObject(id));
		}
		//首页查询商品列表
		@RequestMapping("/list")
		public R list(HttpServletRequest request){
			Integer offset = (Integer.parseInt(request.getParameter("page"))-1)*10;
			Integer limit = Integer.parseInt(request.getParameter("page"))*10;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sidx", null);
			map.put("order", null);
			map.put("offset", offset);
			map.put("limit", limit);
			return R.ok().put("list", marryMainService.queryList(map));
		}
	
	//===========================================首页================================//
	//=========================祝福发送礼金================================//
	/**
	 * 发送红包祝福时的接口
	 * @param request
	 * @param response
	 * @return
	 */
	public R blesspay(HttpServletRequest request, HttpServletResponse response){  
		String sb = "";
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("用户不存在");
		}
		String totalFee = request.getParameter("totalFee");
		Integer weddingId = Integer.parseInt(request.getParameter("weddingId"));
		MarryBlessingEntity marryBlessing = new MarryBlessingEntity();
		marryBlessing.setBlessingtype(2);
		marryBlessing.setContent(totalFee);
		if(user != null){
			marryBlessing.setNickname(user.getNickname());
			marryBlessing.setOpenid(user.getOpenid());
			marryBlessing.setPic(user.getPic());
		}else{
			String code = request.getParameter("js_code");
			String nickname=request.getParameter("nickname");//用户名称
			String avatarUrl=request.getParameter("avatarUrl");//头像
			String openId;
			try {
				openId = WeixinUtilApp.getWeixinOpenId(code);
				marryBlessing.setNickname(nickname);
				marryBlessing.setOpenid(openId);
				marryBlessing.setPic(avatarUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		marryBlessing.setOrdernumber("494955"+new Date().getTime());
		marryBlessing.setWeddingid(weddingId);
		marryBlessing.setStates(0);
		marryBlessing.setGmtModifiedtime(new Date());
		marryBlessingService.insert(marryBlessing);
		try {  
			Map<String, String> paraMap = getSign(marryBlessing.getId(),request);  
			Map<String, Object> payMap = getPayData(paraMap);
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
	public Map<String, Object> getPayData(Map<String, String> paraMap) throws Exception{
		String appid = WeixinSmallPayUtil.appid; 
		//将map转为String
		String xml = WeixinSmallPayUtil.mapToXml(paraMap); 
		xml = new String(xml.toString().getBytes("UTF-8"));  
		String xmlStr = post(WeixinSmallPayUtil.createOrderURL, xml);
		System.out.println(xmlStr);
		// 预付商品id  
		String prepay_id = "";  
		xmlStr = new String(xmlStr.toString().getBytes("GBK"));
		if (xmlStr.indexOf("SUCCESS") != -1) {  
			Map<String, String> map = WeixinSmallPayUtil.xmlToMap(xmlStr);  
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
		String paySign = WeixinSmallPayUtil.generateSignature(payMap, WeixinSmallPayUtil.partnerkey);  
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
		return map;
	}

	//=========================发送礼金================================//
	//=========================提现================================//


	/**
	 * 将自己账号余额提现
	 * @param request
	 * @return
	 */
	public R get_money_tome(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		MarryGetmoneyEntity mg = marryGetmoneyService.queryObject(id);
		MarriedUserEntity entity=marriedUserService.findByOpenIdLike(mg.getOpenid());
		if(entity==null){
			R.error("用户不存在");
		}
		Double money=Double.parseDouble(request.getParameter("total_fee"));
		if(money>mg.getTotalFee()){
			R.ok("输入金额超过了余额，不能提现");
		}else{
			try {
				String xml = WeixinSmallPayUtil.mapToXml(pay(mg));
				String result = creaCa("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xml);
				JSONObject json = JSONObject.fromObject(WeixinSmallPayUtil.xmlToMap(result.replace(">/n", ">")));
				if("SUCCESS".equals(json.getString("result_code"))){
					mg.setTotalFee(mg.getTotalFee()-money);
					marryGetmoneyService.update(mg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return R.ok();
	}

	/**
	 * 获取红包记录
	 * @param request
	 * @return
	 */
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
	public R findredmoneydetail(HttpServletRequest request){
		if("undefined".equals(request.getParameter("id"))){
			return R.error("下手太慢了，已被别人领取了");
		}
		Integer id= Integer.parseInt(request.getParameter("id"));
		MarryRedmoneyDetailEntity marryRedmoneyDetail = marryRedmoneyDetailService.queryObject(id);
		if(marryRedmoneyDetail.getStates()==1){
			return R.error("下手太慢了，已被别人领取了");
		}else{
			MarriedUserEntity user =  marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
			if(user==null){
				R.error("下手太慢了，已被别人领取了");
			}
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

	@SuppressWarnings("deprecation")
	public static String creaCa(String url,String postDataXML) throws Exception {
		String result = "";
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File("E:/web/webroot/wrs/statics/marry/apiclient_cert.p12"));
		try {
			keyStore.load(instream, WeixinSmallPayUtil.partner.toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, WeixinSmallPayUtil.partner.toCharArray())
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
		payMap.put("mch_appid", WeixinSmallPayUtil.appid);
		payMap.put("mchid", WeixinSmallPayUtil.partner);
		payMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));//随机数
		payMap.put("partner_trade_no", "494955"+new Date().getTime());//商户订单号
		payMap.put("openid", marryGetmoney.getOpenid());
		payMap.put("check_name", "NO_CHECK");//NO_CHECK：不校验真实姓名FORCE_CHECK：强校验真实姓名
		payMap.put("amount", (new  Double(Double.valueOf(marryGetmoney.getTotalFee())*100)).intValue()+"");//金额
		payMap.put("desc", "红包提现");
		payMap.put("spbill_create_ip", "47.92.117.143");
		String sign = WeixinSmallPayUtil.generateSignature(payMap, WeixinSmallPayUtil.partnerkey);
		payMap.put("sign", sign);
		return payMap;
	}

	/**
	 * 查询自己账号余额
	 * @param request
	 * @param response
	 * @return
	 */
	public R findAllgetMoney(HttpServletRequest request, HttpServletResponse response){  
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("数据不存在");
		}
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
			Double sum=0.00;
			List<MarryBlessingEntity> list2 = marryBlessingService.queryList(m);//红包祝福
			for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
				MarryBlessingEntity marryBlessingEntity = (MarryBlessingEntity) iterator.next();
				sum+=Double.parseDouble(marryBlessingEntity.getContent());
			}
			System.out.println("====>"+sum);
			map.put("sumcash", getTwoDecimal(sum));
			map.put("blessing", list2);
		}
		return R.ok().put("map", map);
	}

	
	/**  
	 * 将数据保留两位小数  
	 */  
	private static double getTwoDecimal(double num) {  
		DecimalFormat dFormat=new DecimalFormat("#.00");  
		String yearString=dFormat.format(num);  
		Double temp= Double.valueOf(yearString);  
		return temp;  
	} 

	public static void main(String[] args) {
		System.out.println(getTwoDecimal(123.0011));
	}

	/**
	 * 用户发送红包时将钱放入对公账号里面
	 * @param request
	 * @param response
	 * @return
	 */
	public R moneyMainSave(HttpServletRequest request, HttpServletResponse response){  
		Integer totalNum = Integer.parseInt(request.getParameter("total"));//红包个数
		System.out.println("======>"+request.getParameter("total_fee"));
		double total_fee = 0.00;
		String sb = "";
		total_fee = (new  Double(Double.valueOf(request.getParameter("total_fee")))).intValue();

		Integer id = saveMoneyMain(total_fee, request, totalNum);//将红包主表数据进行添加
		try {
			Map<String, String> paraMap = getSign(request,id,request.getParameter("content"));//红包祝福语
			MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
			MarryWeddingEntity marryWedding = new MarryWeddingEntity();
			marryWedding.setUserId(user.getId());
			System.out.println("========>userId="+user.getId());
			EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
			Map<String, Object> payMap = getPayData(marryWeddingService.selectOne(wrapper).getId(),paraMap);
			sb = JSONObject.fromObject(payMap).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return R.ok().put("data", sb);
	}

	/**
	 * 统一下单
	 * @throws Exception 
	 */
	public Map<String, String> getSign(HttpServletRequest request,Integer id,String content) throws Exception{
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("数据不存在");
		}
		MarryRedmoneyMainEntity marryRedmoneyMain = marryRedmoneyMainService.queryObject(id);
		String appid = WeixinSmallPayUtil.appid;  
		Map<String, String> paraMap = new HashMap<String, String>();  
		paraMap.put("appid", appid);  
		paraMap.put("attach", URLEncoder.encode("微信红包", "UTF-8"));  
		paraMap.put("body", URLEncoder.encode((content==null||"".equals(content))?"恭喜发财，大吉大利":content, "UTF-8"));  
		paraMap.put("mch_id", WeixinSmallPayUtil.partner);//商户号 
		paraMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));//随机数  
		paraMap.put("sign_type", "MD5"); 
		paraMap.put("openid", user.getOpenid()); 
		paraMap.put("out_trade_no", marryRedmoneyMain.getOutTradeNo());  //订单号
		paraMap.put("spbill_create_ip", "47.92.117.143");//服务器IP地址  
		paraMap.put("total_fee", (new  Double(Double.valueOf(marryRedmoneyMain.getTotalFee())*100)).intValue()+"");  //订单价格
		paraMap.put("trade_type", "JSAPI");  //交易类型取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
		paraMap.put("notify_url", "http://wrs.gykjewm.com/married/weixin/moneyNotify");// 此路径是微信服务器调用支付结果通知路径  
		String sign = WeixinSmallPayUtil.generateSignature(paraMap, WeixinSmallPayUtil.partnerkey);  
		paraMap.put("sign", sign);
		request.getSession().setAttribute("sign", sign);
		return paraMap;
	}
	/**
	 * 支付回调接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("moneyNotify")
	public ResultState moneyNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultState resultState = new ResultState();
		String resXml = ""; // 反馈给微信服务器
		String notifyXml = convertStreamToString(request.getInputStream());// 微信支付系统发送的数据（<![CDATA[product_001]]>格式）
		// 验证签名
		JSONObject json = JSONObject.fromObject(WeixinSmallPayUtil.xmlToMap(notifyXml.replace(">/n", ">")));
		if ("SUCCESS".equals(json.getString("result_code"))) {
			String out_trade_no = json.getString("out_trade_no");
			MarryRedmoneyMainEntity marryRedmoneyMain = new MarryRedmoneyMainEntity();
			marryRedmoneyMain.setOutTradeNo(out_trade_no);
			EntityWrapper<MarryRedmoneyMainEntity> wrapper = new EntityWrapper<MarryRedmoneyMainEntity>(marryRedmoneyMain);
			marryRedmoneyMain = marryRedmoneyMainService.selectOne(wrapper);
			if(marryRedmoneyMain != null){
				marryRedmoneyMain.setStates(1);
				marryRedmoneyMainService.update(marryRedmoneyMain);
				saveMoneyDetail(marryRedmoneyMain.getId());//按红包个数保存详细红包
				resultState.setErrcode(0);// 表示成功
				resultState.setErrmsg("支付成功");
				/**** 业务逻辑  保存openid之类的****/
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			}else{
				resultState.setErrcode(-1);// 表示成功
				resultState.setErrmsg("支付失败");
				/**** 业务逻辑  保存openid之类的****/
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
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
		Integer userId=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user = marriedUserService.queryObject(userId);
		if(user == null){
			R.error("数据不存在");
		}
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

	//    public String convertStreamToString(InputStream is) {   
	//    	   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
	//    	        StringBuilder sb = new StringBuilder();   
	//    	        String line = null;   
	//    	        try {   
	//    	            while ((line = reader.readLine()) != null) {   
	//    	                sb.append(line + "/n");   
	//    	            }   
	//    	        } catch (IOException e) {   
	//    	            e.printStackTrace();   
	//    	        } finally {   
	//    	            try {   
	//    	                is.close();   
	//    	            } catch (IOException e) {   
	//    	                e.printStackTrace();   
	//    	            }   
	//
	//    	        }   
	//    	        return sb.toString();   
	//    }    

	//=========================提现================================//
	//=========================支付================================//
	//支付接口
	@RequestMapping("/pay")
	public R pay(HttpServletRequest request, HttpServletResponse response){  
		String sb = "";
		try {  
			Integer id = Integer.parseInt(request.getParameter("id"));//订单Id 
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
		String appid = WeixinSmallPayUtil.appid; 
		//将map转为String
		String xml = WeixinSmallPayUtil.mapToXml(paraMap); 
		//	        xml = new String(xml.toString().getBytes("UTF-8"));  
		String xmlStr = post(WeixinSmallPayUtil.createOrderURL, xml);
		// 预付商品id  
		String prepay_id = "";  
		xmlStr = new String(xmlStr.toString().getBytes("GBK"));
		if (xmlStr.indexOf("SUCCESS") != -1) {  
			Map<String, String> map = WeixinSmallPayUtil.xmlToMap(xmlStr);  
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
		String paySign = WeixinSmallPayUtil.generateSignature(payMap, WeixinSmallPayUtil.partnerkey);  
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
		MarryOrdersEntity marryOrders = marryOrdersService.queryObject(id);//订单Id 
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		Integer total_fee = 0;
		if(marryOrders.getOrderType() == 1){//直接下单的情况
			total_fee = (new  Double(Double.valueOf(marryOrders.getMainPrice())*100)).intValue();
		}else{
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("orderId", marryOrders.getId());
			List<MarryOrderMainEntity> marryOrderMainList = marryOrderMainService.queryList(m);
			for (Iterator iterator2 = marryOrderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMainEntity.getMainId());
				total_fee += (new  Double(Double.valueOf(marryMain.getPrice())*100)).intValue();
			}
		}
		String appid = WeixinSmallPayUtil.appid;  
		Map<String, String> paraMap = new HashMap<String, String>();  
		paraMap.put("appid", appid);  
		paraMap.put("attach", marryOrders.getOrderNumber());  
		paraMap.put("body", marryOrders.getOrderNumber());  
		paraMap.put("mch_id", WeixinSmallPayUtil.partner);//商户号 
		paraMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));//随机数  
		paraMap.put("sign_type", "MD5"); 
		paraMap.put("openid", user.getOpenid()); 
		paraMap.put("out_trade_no", marryOrders.getOrderNumber());  //订单号
		paraMap.put("spbill_create_ip", "47.92.117.143");//服务器IP地址  
		paraMap.put("total_fee", total_fee+"");  //订单价格
		paraMap.put("trade_type", "JSAPI");  //交易类型取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
		paraMap.put("notify_url", WeixinSmallPayUtil.notify_url);// 此路径是微信服务器调用支付结果通知路径  
		String sign = WeixinSmallPayUtil.generateSignature(paraMap, WeixinSmallPayUtil.partnerkey);  
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
	/**
	 * 支付回调接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("notify")
	public ResultState notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultState resultState = new ResultState();
		String resXml = ""; // 反馈给微信服务器
		String notifyXml = convertStreamToString(request.getInputStream());// 微信支付系统发送的数据（<![CDATA[product_001]]>格式）
		// 验证签名
		JSONObject json = JSONObject.fromObject(WeixinSmallPayUtil.xmlToMap(notifyXml.replace(">/n", ">")));
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
				marryOrdersEntity.setStates(1);
				marryOrdersService.update(marryOrdersEntity);
				MarriedUserEntity userEntity = new MarriedUserEntity();
				userEntity.setId(marryOrdersEntity.getUserId());
				userEntity.setJurisdiction(1);
				marriedUserService.update(userEntity);
				request.getSession().setAttribute("jurisdiction", 1);
				resultState.setErrcode(0);// 表示成功
				resultState.setErrmsg("支付成功");
				/**** 业务逻辑  保存openid之类的****/
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
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
	//=========================支付================================//
	//==========================订单============================//

	@SuppressWarnings("unused")
	private  String checkDate(String str)throws Exception{
		if(str != null || !"".equals(str)){
			return str;
		}else{
			if(str==null||str.equals("")){
				throw new RRException(str + "值不存在，请检查参数是否正确");
			}else{
				throw new RRException(str + "参数不存在，请检查参数是否正确");
			}
		}
	}

	/**
	 * 用户保存订单
	 * @param request
	 * @return
	 */
	public R saveOrder(HttpServletRequest request){
		String token = request.getParameter("token");
		String userId=request.getParameter("userId");
		MarriedUserEntity user=marryUserService.queryObject(Integer.parseInt(userId));
		if(user==null){
			R.error("还没有该用户的商品，去首页添加");
		}
//		System.out.println(request.getParameter("type"));
		Integer type = Integer.parseInt(request.getParameter("types"));
		String orderNumber = "494955"+new Date().getTime();
		Integer id = 0;
		if(type == 1){//直接下单
			MarryOrdersEntity marryOrders = new MarryOrdersEntity();
			marryOrders.setOrderNumber(orderNumber);//订单号
			marryOrders.setUserId(user.getId());
			marryOrders.setGmtModifiedtime(new Date());
			marryOrders.setStates(0); 
			Integer mainId = Integer.parseInt(request.getParameter("mainId"));
			MarryMainEntity marryMain = marryMainService.queryObject(mainId);
			SysUserEntity u = sysUserService.queryObject(new Long(marryMain.getUserId()));
			marryOrders.setBusiness(u.getName());
			marryOrders.setOrderType(1);
			marryOrders.setMainPrice(marryMain.getPrice());
			marryOrders.setMainDescribe(u.getName()+marryMain.getTitle());
			marryOrdersService.insert(marryOrders);
			id = marryOrders.getId();
			MarryOrderMainEntity marryOrderMainEntity = new MarryOrderMainEntity();
			marryOrderMainEntity.setMainId(mainId);
			marryOrderMainEntity.setOrderId(id);
			marryOrderMainService.save(marryOrderMainEntity);
		}else if(type == 2){
			String[] ids = request.getParameter("mainId").split(",");
			MarryOrdersEntity marryOrders = new MarryOrdersEntity();
			marryOrders.setOrderNumber(orderNumber);//订单号
			marryOrders.setUserId(user.getId());
			marryOrders.setGmtModifiedtime(new Date());
			marryOrders.setStates(0);
			marryOrders.setOrderType(2);
			marryOrdersService.insert(marryOrders);
			id = marryOrders.getId();
			Double totalFee = 0.00;
			for (int i = 0; i < ids.length; i++) {
				MarryCartEntity marryCart = marryCartService.queryObject(Integer.parseInt(ids[i]));
				String price = marryMainService.queryObject(marryCart.getMainId()).getPrice();
				totalFee += (new  Double(Double.valueOf(price))).doubleValue();
				MarryOrderMainEntity marryOrderMainEntity = new MarryOrderMainEntity();
				marryOrderMainEntity.setOrderId(id);
				marryOrderMainEntity.setMainId(marryCart.getMainId());
				marryOrderMainService.save(marryOrderMainEntity);
				marryCart.setStates(0);
				marryCartService.update(marryCart);
			}
			marryOrders.setMainPrice(totalFee.toString());
			marryOrdersService.update(marryOrders);
		}
		return R.ok().put("id", id);
	}

	/**
	 * 通过订单id查询订单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public R findOrder(HttpServletRequest request)throws Exception{
		String token = request.getParameter("token");
		Integer id = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<MarryOrdersEntity> list = marryOrdersService.queryListorder(map);
		List<MarryOrdersEntity> listtotal = new ArrayList<MarryOrdersEntity>();
		Map<String, List<MarryMainEntity>> marryMainMap = new HashMap<String, List<MarryMainEntity>>();
		List<MarryMainEntity> marryMainList = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			Map<String, Object> orderMainMap = new HashMap<String, Object>();
			orderMainMap.put("orderId", marryOrdersEntity.getId());
			List<MarryOrderMainEntity> orderMainList = marryOrderMainService.queryList(orderMainMap);
			Double sumprice=0.0;
			for (Iterator iterator2 = orderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMainEntity.getMainId());
				SysUserEntity u = sysUserService.queryObject(new Long(marryMain.getUserId()));
				marryOrdersEntity.setBusiness(u.getName());
				marryOrdersEntity.setMainDescribe(u.getName()+marryMain.getTitle());
				Double price = Double.parseDouble(marryMain.getPrice());
				sumprice+=price;
				if(marryMainMap.get(marryMain.getUserId()) == null){
					marryMainList = new ArrayList<MarryMainEntity>();
				}
				marryMainList.add(marryMain);
				marryMainMap.put(marryMain.getUserId()+","+marryOrderMainEntity.getOrderId(), marryMainList);
			}
			marryOrdersEntity.setMainPrice(String.valueOf(sumprice));
			marryOrdersEntity.setCount(orderMainList.size());
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			List<OrderAndMain> orderAndMainList = new ArrayList<OrderAndMain>();
			for(String entry : marryMainMap.keySet()){
				String[] entrys = entry.split(",");
				if(Integer.parseInt(entrys[1]) == marryOrdersEntity.getId()){
					OrderAndMain andMain = new OrderAndMain();
					andMain.setUserId(Integer.parseInt(entrys[0]));
					andMain.setUserName(sysUserService.queryObject(new Long(entrys[0])).getName());
					andMain.setMainList(marryMainMap.get(entry));
					orderAndMainList.add(andMain);
				}
			}
			marryOrdersEntity.setMarryMainList(orderAndMainList);
			listtotal.add(marryOrdersEntity);

		}
		return R.ok().put("data", listtotal);
	}


	/**
	 * 用户删除订单
	 * @param request
	 * @return
	 */
	public R deleteOrder(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer id = Integer.parseInt(request.getParameter("id"));
		marryOrderMainService.delete(id);
		marryOrdersService.delete(id);
		return R.ok();
	}

	/**
	 * 查询当前用户的所有订单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public R findOrderlist(HttpServletRequest request){
		String token = request.getParameter("token");
		//System.out.println(request.getParameter("userId"));
		Integer userid=Integer.parseInt(request.getParameter("userId"));
		System.out.println("========"+userid);
		MarriedUserEntity queryObject = marryUserService.queryObject(userid);
		if(queryObject==null){
			R.error("还没有订单，赶快去下单吧");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", queryObject.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
		List<MarryOrdersEntity> listtotal = new ArrayList<MarryOrdersEntity>();
		Map<String, List<MarryMainEntity>> marryMainMap = new HashMap<String, List<MarryMainEntity>>();
		List<MarryMainEntity> marryMainList = null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();

			Map<String, Object> orderMainMap = new HashMap<String, Object>();
			orderMainMap.put("orderId", marryOrdersEntity.getId());
			List<MarryOrderMainEntity> orderMainList = marryOrderMainService.queryList(orderMainMap);
			Double sumprice=0.0;
			for (Iterator iterator2 = orderMainList.iterator(); iterator2.hasNext();) {
				MarryOrderMainEntity marryOrderMainEntity = (MarryOrderMainEntity) iterator2.next();
				MarryMainEntity marryMain = marryMainService.queryObject(marryOrderMainEntity.getMainId());
				SysUserEntity u = sysUserService.queryObject(new Long(marryMain.getUserId()));
				marryOrdersEntity.setBusiness(u.getName());
				marryOrdersEntity.setMainDescribe(u.getName()+marryMain.getTitle());
				Double price = Double.parseDouble(marryMain.getPrice());
				sumprice+=price;
				if(marryMainMap.get(marryMain.getUserId()) == null){
					marryMainList = new ArrayList<MarryMainEntity>();
				}
				marryMainList.add(marryMain);
				marryMainMap.put(marryMain.getUserId()+","+marryOrderMainEntity.getOrderId(), marryMainList);
			}
			marryOrdersEntity.setMainPrice(String.valueOf(sumprice));
			marryOrdersEntity.setCount(orderMainList.size());
			//			marryOrdersService.update(marryOrdersEntity);
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryOrdersEntity marryOrdersEntity = (MarryOrdersEntity) iterator.next();
			List<OrderAndMain> orderAndMainList = new ArrayList<OrderAndMain>();
			for(String entry : marryMainMap.keySet()){
				String[] entrys = entry.split(",");
				if(Integer.parseInt(entrys[1]) == marryOrdersEntity.getId()){
					OrderAndMain andMain = new OrderAndMain();
					andMain.setUserId(Integer.parseInt(entrys[0]));
					andMain.setUserName(sysUserService.queryObject(new Long(entrys[0])).getName());
					andMain.setMainList(marryMainMap.get(entry));
					orderAndMainList.add(andMain);
				}
			}
			marryOrdersEntity.setMarryMainList(orderAndMainList);
			listtotal.add(marryOrdersEntity);
		}
		System.out.println("-----------"+listtotal);
		return R.ok().put("list", listtotal);
	}

	//==========================订单============================//

	//-------购物车-----------------------------------------------------------------------------------------------------------------------//
	/**
	 * 购物车提交结算
	 * @param request
	 * @return
	 */
	public R submitCart(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity entity=marriedUserService.queryObject(id);
		if(entity==null){
			return R.error("数据不存在");
		}
		String[] key = request.getParameter("ids").toString().split(",");
		List<MarryMain> data = new ArrayList<MarryMain>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("states", 0);
		map.put("userId", entity.getId());
		for (int i = 0; i < key.length; i++) {
			MarryCartEntity marryCartEntity = marryCartService.queryObject(Integer.parseInt(key[i]));
			MarryMainEntity marryMainEntity = marryMainService.queryObject(marryCartEntity.getMainId());
			SysUserEntity sysUser = sysUserService.queryObject(new Long(marryMainEntity.getUserId()));
			MarryMain marryMain = new MarryMain();
			marryMain.setUsername(sysUser.getName());
			map.put("user_id", sysUser.getUserId());
			map.put("Id", marryCartEntity.getId());
			marryMain.setList(marryMainService.queryListtongji(map));
			data.add(marryMain);
		}
		return R.ok().put("list", data);
	}
	/**
	 * 删除购物车
	 * @param request
	 * @return
	 */
	public R deletemarrycart(HttpServletRequest request){
		String token = request.getParameter("token");
		String key = request.getParameter("ids").toString();
		String[] ids = key.split(",");
		System.out.println(ids.length);
		Integer[] id = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++) {
			id[i] = Integer.parseInt(ids[i]);
		}
		marryCartService.deleteBatch(id);
		return R.ok();
	}

	@SuppressWarnings("rawtypes")
	public R cartList(HttpServletRequest request){
		String token = request.getParameter("token");
		MarriedUserEntity entity=marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(entity==null){
			return R.error("用户信息数据不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("states", 1);
		map.put("userId", entity.getId());
		List<MarryMain> data = new ArrayList<MarryMain>();
		Integer id = 0;
		for (Iterator iterator = marryCartService.queryListtongji(map).iterator(); iterator.hasNext();) {
			MarryMainEntity marryMainEntity = (MarryMainEntity) iterator.next();
			if( id != marryMainEntity.getUserId()){
				id = marryMainEntity.getUserId();
				SysUserEntity sysUser = sysUserService.queryObject(new Long(marryMainEntity.getUserId()));
				MarryMain marryMain = new MarryMain();
				marryMain.setUsername(sysUser.getName());
				map.put("user_id", marryMainEntity.getUserId());
				marryMain.setList(marryMainService.queryListtongji(map));
				data.add(marryMain);
			}
		}
		return R.ok().put("list", data);
	}

	public R cartSave(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer main_id = Integer.parseInt(request.getParameter("id"));//商品id
		Integer userId = Integer.parseInt(request.getParameter("userId"));//用户id
		MarriedUserEntity queryObject = this.marriedUserService.queryObject(userId); 
		if(queryObject==null){
			return R.error("用户信息数据不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("main_id", main_id);
		map.put("userId", queryObject.getId());
		map.put("states", 1);
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 10);
		List<MarryCartEntity> list = marryCartService.queryList(map);
		
		if(list.size() > 0){
			return R.error("该商品已存在购物车");
		}
		MarryMainEntity marryMain = marryMainService.queryObject(main_id);
		MarryCartEntity marryCartEntity = new MarryCartEntity();
		marryCartEntity.setMainId(main_id);
		marryCartEntity.setCreatetime(new Date());
		marryCartEntity.setUserid(queryObject.getId());
		marryCartEntity.setBusinessid(marryMain.getUserId());
		marryCartEntity.setStates(1);
		marryCartService.insert(marryCartEntity);
		return R.ok();
	}


	//---------------------------------------------------购物车cart----------------------------------------------------------------//

	//=============================smallMe===============================//

	/**
	 * 保存婚礼信息
	 * @param request
	 * @return
	 */
	public R saveWeddingInfo(HttpServletRequest request){
		String token = request.getParameter("token");
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		System.out.println("userId="+request.getParameter("userId"));
		MarriedUserEntity userEntity=marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(userEntity==null){
			R.error("没有查询到该条数据");
		}
		String id=request.getParameter("id");
		System.out.println("id="+id);
		MarryWeddingEntity queryObject = marryWeddingService.queryObject(Integer.parseInt(id));
		if(queryObject==null){
			return R.error("数据不存在");
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try{
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println(is);
			if(is != null){
				marryWedding.setPhoto(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[0], "married/photo/"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String bridename = request.getParameter("brideName");
		String content = request.getParameter("content");
		String groomname = request.getParameter("groomName");
		String weddingaddress = request.getParameter("weddingAddress");
		String url = request.getParameter("bg");
		String color = "#"+request.getParameter("color");

		Date weddingdate = new Date();
		try {
			weddingdate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(request.getParameter("weddingDate").replace("T", " "));
		} catch (Exception e) {
			e.printStackTrace();
		}
		marryWedding.setBridename(bridename);
		marryWedding.setContent(content);
		marryWedding.setGroomname(groomname);
		marryWedding.setUserId(userEntity.getId());
		marryWedding.setWeddingaddress(weddingaddress);
		marryWedding.setWeddingdate(weddingdate);
		marryWedding.setBgcolor(color);
		marryWedding.setUrl(url);
		marryWedding.setId(Integer.parseInt(id));
		marryWeddingService.update(marryWedding);
		System.out.println("wedding"+marryWedding.getId());
		return R.ok().put("id", marryWedding.getId());
	}

	/**
	 * 屏幕管理
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	public R saveScreen(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer userId = Integer.parseInt(request.getParameter("userid"));//用户id
		Integer weddingId = Integer.parseInt(request.getParameter("weddingid"));//婚礼
		Integer screenCount = Integer.parseInt(request.getParameter("screencount"));//请求参数
		MarryScreenEntity marryScreenEntity=new MarryScreenEntity();
		marryScreenEntity.setUserid(userId);
		marryScreenEntity.setWeddingid(weddingId);
		marryScreenEntity.setScreencount(screenCount);
		marryScreenEntity.setCreateTime(new Date());
		marryScreenService.save(marryScreenEntity);
		return R.ok();
	}
	/**
	 * 获取屏幕管理保存的内容  根据screenCount倒序取出 
	 * @param request
	 * @return
	 */
	public R getScreen(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer userId=Integer.parseInt(request.getParameter("userId"));
		Integer weddingId=Integer.parseInt(request.getParameter("weddingId"));
		System.out.println("===>"+weddingId);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("weddingId", weddingId);
		List<MarryScreenEntity> queryList1 = marryScreenService.queryList1(map);
		return R.ok().put("data", queryList1.get(queryList1.size()-1));
	}

	/**
	 * 权限管理和查看购物车数据
	 * @param request
	 * @return
	 */
	public R jurisdiction(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer userId = Integer.parseInt(request.getParameter("userId"));//用户id
		MarriedUserEntity user = this.marriedUserService.queryObject(userId); 
		if(user==null){
			return R.error("用户信息数据不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sidx", null);
		map.put("order", null);
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("states", 1);
		map.put("userId", user.getId());
		int total = marryCartService.queryList(map).size();
		if(marriedUserService.queryObject(user.getId()).getJurisdiction()==0){
			request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
		}else{
			request.getSession().setAttribute("jurisdiction", 1);
		}
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("total", total);
		if(marryWeddingService.selectOne(wrapper) == null){
			m.put("obj", null);
		}else{
			m.put("obj", marryWeddingService.selectOne(wrapper).getId());
		}
		return R.ok().put("map", m);
	}

	/**
	 * 播放图片视频
	 * 通过婚礼id查询所有的摄影展图片
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public R getAllWeddingPhoto(HttpServletRequest request){
		String token = request.getParameter("token");
		Map<String, Object> map = new HashMap<String, Object>();
		String weddingId = request.getParameter("id");
		if(weddingId==null){
			R.error("婚礼id不存在");
		}
		map.put("weddingId", weddingId);
		List<MarryPhotoEntity> queryList = marryPhotoService.queryList(map);
		List<MarryPhotoEntity> list1=new ArrayList<MarryPhotoEntity>();
		List<MarryPhotoEntity> list2=new ArrayList<MarryPhotoEntity>();
		for (Iterator iterator = queryList.iterator(); iterator.hasNext();) {
			MarryPhotoEntity marryPhotoEntity = (MarryPhotoEntity) iterator.next();
			if(marryPhotoEntity.getType()==1){
				list1.add(marryPhotoEntity);
			}else if(marryPhotoEntity.getType()==2){
				list2.add(marryPhotoEntity);
			}
		}
		Map<String,Object> allmap=new HashMap<String, Object>();
		allmap.put("list1", list1);
		allmap.put("list2", list2);
		return R.ok().put("list", allmap);
	}

	/**
	 * 保存用户帮助记录
	 * @param request
	 * @return
	 */
	public R saveHelp(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer userId = Integer.parseInt(request.getParameter("userId"));//用户id
		MarriedUserEntity user = this.marriedUserService.queryObject(userId); 
		if(user==null){
			return R.error("用户信息数据不存在");
		}
		MarryHelpEntity marryHelp = new MarryHelpEntity();
		marryHelp.setUsername(user.getNickname());
		marryHelp.setPic(user.getPic());
		marryHelp.setUserid(user.getId());
		marryHelp.setContent(request.getParameter("content"));
		marryHelp.setCreateTime(new Date());
		marryHelpService.save(marryHelp);
		return R.ok();
	}

	/**
	 * 保存所有的视频路径
	 * @param request
	 * @return
	 */
	public R saveWeddingPhoto(HttpServletRequest request){
		String token = request.getParameter("token");
		String[] videos = request.getParameter("video").toString().split(",");
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
		MarryWeddingEntity mw = new MarryWeddingEntity();
		mw.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		MarryPhotoEntity marryPhoto = new MarryPhotoEntity();
		String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?mw.getId().toString():request.getParameter("id");
		for (int i = 0; i < videos.length; i++) {
			marryPhoto.setWeddingid(Integer.parseInt(weddingId));
			marryPhoto.setType(2);
			marryPhoto.setPic(videos[i]);
			marryPhotoService.save(marryPhoto);
		}
		return R.ok();
	}
	/**
	 * 通过婚礼id查询所有的摄影展图片
	 * @param request
	 * @return
	 */
	public R findAllWeddingPhoto(HttpServletRequest request){
		String token = request.getParameter("token");
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
		MarryWeddingEntity mw = new MarryWeddingEntity();
		MarryOrdersEntity entity=new MarryOrdersEntity();
		mw.setUserId(user.getId());
		entity.setUserId(user.getId());
		EntityWrapper<MarryOrdersEntity> entityWrapper=new EntityWrapper<MarryOrdersEntity>(entity);
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		entity = marryOrdersService.selectOne(entityWrapper);
		Map<String,Object> maps=new HashMap<String, Object>();
		System.out.println("----->"+entity.getId());
		maps.put("orderId", entity.getId());
		List<MarryOrderMainEntity> mainorder=marryOrderMainService.queryList(maps);
		System.out.println("=====>"+mainorder.size());
		Map<String,Object> mainmap=null;
		for (Iterator iterator = mainorder.iterator(); iterator.hasNext();) {
			MarryOrderMainEntity entity3 = (MarryOrderMainEntity) iterator.next();
			System.out.println("====>"+marryOrderMainService.queryObject(entity3.getMainId()));
			MarryMainEntity entity4=marryMainService.queryObject(entity3.getMainId());
			System.out.println("main="+marryOrderMainService.queryObject(entity3.getMainId()));
			mainmap=new HashMap<String, Object>();
			mainmap.put("mainlist", entity4);
		}

		System.out.println("wedding--->"+entity);
		Map<String, Object> map = new HashMap<String, Object>();
		if(mw != null){
			String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?mw.getId().toString():request.getParameter("id");
			map.put("weddingId", weddingId);
		}else{
			String weddingId = (request.getParameter("id")==null || "".equals(request.getParameter("id")))?"0":request.getParameter("id");
			map.put("weddingId", weddingId);
		}
		List<MarryPhotoEntity> queryList = marryPhotoService.queryList(map);
		List<MarryPhotoEntity> list1=new ArrayList<MarryPhotoEntity>();
		List<MarryPhotoEntity> list2=new ArrayList<MarryPhotoEntity>();
		for (Iterator iterator = queryList.iterator(); iterator.hasNext();) {
			MarryPhotoEntity marryPhotoEntity = (MarryPhotoEntity) iterator.next();
			if(marryPhotoEntity.getType()==1){
				list1.add(marryPhotoEntity);
			}else if(marryPhotoEntity.getType()==2){
				list2.add(marryPhotoEntity);
			}
		}
		Map<String,Object> allmap=new HashMap<String, Object>();
		allmap.put("list1", list1);
		allmap.put("list2", list2);
		allmap.put("list3",mainmap);
		return R.ok().put("list", allmap);
	}

	/**
	 * 通过当前用户获取婚礼id
	 * @param request
	 * @return
	 */
	public R getWeddingId(HttpServletRequest request){
		String token = request.getParameter("token");
		//获取当前登录用户
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
		MarryWeddingEntity mw = new MarryWeddingEntity();
		mw.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(mw);
		mw = marryWeddingService.selectOne(wrapper);
		if(mw == null){
			return R.error();
		}
		return R.ok().put("id", mw.getId());
	}

	/**
	 * 祝福管理
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public R blessManage(HttpServletRequest request){
		String token = request.getParameter("token");
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(user==null){
			R.error("没有查到数据");
		}
		System.out.println("---->"+request.getParameter("userId"));
		System.out.println("----00"+request.getParameter("token"));
		//获取当前登录用户
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = marryWeddingService.selectOne(wrapper);
		System.out.println("=========="+marryWedding);
		if(marryWedding == null){
			return R.error("暂无婚礼记录");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("weddingId", 26);
		//		map.put("weddingId", marryWedding.getId());
		map.put("blessingtype", 1);
		map.put("states", 0);
		List<MarryBlessingEntity> list1 = marryBlessingService.queryList(map);//普通祝福
		List<MarryBlessingEntity> list3 = new ArrayList<MarryBlessingEntity>();//视频祝福
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			MarryBlessingEntity marryBlessingEntity = (MarryBlessingEntity) iterator.next();
			if(marryBlessingEntity.getVideoblessing() != null && !"".equals(marryBlessingEntity.getVideoblessing())){
				list3.add(marryBlessingEntity);
			}
		}
		map.put("blessingtype", 2);
		map.put("states", 1);
		List<MarryBlessingEntity> list2 = marryBlessingService.queryList(map);//红包祝福
		Iterator<MarryBlessingEntity> content=list2.iterator();
		Double sum=0.0;//红包总额
		while(content.hasNext()){
			MarryBlessingEntity counthongbao=content.next();
			Double   count=Double.parseDouble(counthongbao.getContent());
			sum+=count;
		}
		System.out.println("--->"+sum);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list1", list1);
		m.put("list2", list2);
		m.put("list3", list3);
		m.put("sum", sum);
		//红包个数
		m.put("counts", list2.size());
		return R.ok().put("list", m);
	}


	/**
	 * 上传视频
	 * @param request
	 * @param response
	 * @return
	 */
	public R filevideo(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		MarryBlessingEntity blessingEntity=new MarryBlessingEntity();
		try {
			Integer id=Integer.parseInt(request.getParameter("id"));
			MarryBlessingEntity queryObject = marryBlessingService.queryObject(id);
			if(queryObject==null){
				R.error("数据不存在");
			}
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println("----->"+request.getParameter("files"));
			System.out.println(is);
			if(is != null){
				blessingEntity.setId(id);
				blessingEntity.setVideoblessing(FILEPATH+"married_video/"+OssUploadUtil.uploadObject2OSSVideo(is[0], "married/video/"));
			}
			marryBlessingService.update(blessingEntity);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}

	/**
	 * 保存祝福
	 * @param request
	 * @return
	 */
	public R saveBlessing(HttpServletRequest request){
		String token = request.getParameter("token");
		System.out.println("saveBlessing="+request.getParameter("userId"));
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity	user = this.marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}
		MarryBlessingEntity mbe = new MarryBlessingEntity();
		if(user!=null){
			mbe.setNickname(user.getNickname());
			mbe.setOpenid(user.getOpenid());
			mbe.setPic(user.getPic());
		}
		mbe.setContent(request.getParameter("content"));
		mbe.setWeddingid(Integer.parseInt(request.getParameter("weddingId")));
		
		mbe.setBlessingtype(Integer.parseInt(request.getParameter("types")));
		mbe.setGmtModifiedtime(new Date());
		marryBlessingService.save(mbe);
		return R.ok().put("id", mbe.getId());
	}

	/**
	 * 查询所有的婚礼背景图片
	 * @param request
	 * @return
	 */
	public R allweddingImg(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer page = Integer.parseInt(request.getParameter("page"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imgType", 1);
		//		map.put("offset", (page-1)*8);
		//		map.put("limit", 2);
		map.put("offset", (page-1)*2);
		map.put("limit", page*2);
		return R.ok().put("list", marryImgService.queryList(map));
	}

	/**
	 * 查询所有签到记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public R allSign(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = marryWeddingService.selectOne(wrapper);
		List<MarriedUserEntity> list = new ArrayList<MarriedUserEntity>();
		if(marryWedding != null){
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("weddingId", marryWedding.getId());
			list = marriedUserService.queryListtongji(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> m = new HashMap<String, Object>();
		map.put("weddingId", marryWedding.getId());
		map.put("states",1);
		m.put("totalPerson", marryParticipateService.queryList(map).size());//应到人数
		m.put("personwd", marryParticipateService.queryList(map).size() - list.size());//未到人数
		m.put("smtj", list);//扫码统计
		map.put("userId", user.getId());
		map.put("offset", 0);
		map.put("limit", 10);
		map.put("blessingtype", 1);
		map.put("states",null);
		List<MarryBlessingEntity> list1 = marryBlessingService.queryList(map);//普通祝福
		int zftotal = 0;
		int videototal = 0;
		for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
			MarryBlessingEntity marryBlessingEntity = (MarryBlessingEntity) iterator.next();
			if(marryBlessingEntity.getContent() != null && !"".equals(marryBlessingEntity.getContent())){
				zftotal++;
			}
			if(marryBlessingEntity.getVideoblessing() != null && !"".equals(marryBlessingEntity.getVideoblessing())){
				videototal++;
			}
		}
		map.put("states",1);
		map.put("blessingtype", 2);
		List<MarryBlessingEntity> list2 = marryBlessingService.queryList(map);//红包祝福
		Iterator<MarryBlessingEntity> content=list2.iterator();
		Double sum=0.0;//红包总额
		while(content.hasNext()){
			MarryBlessingEntity counthongbao=content.next();
			Double   count=Double.parseDouble(counthongbao.getContent());
			sum+=count;
		}
		m.put("zftotal", zftotal);
		m.put("videototal", videototal);
		m.put("moneytotal", list2);
		m.put("redcount", list2.size());//红包个数
		m.put("sum", sum);//红包总金额

		return R.ok().put("list", m);
	}

	/**
	 * 查询自己所有的婚礼签到记录
	 * @param request
	 * @return
	 */
	public R allMeSign(HttpServletRequest request){
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		return R.ok().put("list", marrySignService.queryListtongji(map));
	}

	/**
	 * 保存签到记录
	 * @param request
	 * @return
	 */
	public R saveSign(HttpServletRequest request){
		String token = request.getParameter("token");
		String openId = "";
		try {
			openId = WeixinUtilApp.getWeixinOpenId(request.getParameter("js_code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		MarriedUserEntity u = new MarriedUserEntity();
		u.setOpenid(openId);
		EntityWrapper<MarriedUserEntity> wrapper1 = new EntityWrapper<MarriedUserEntity>(u);
		u = this.marriedUserService.selectOne(wrapper1);
		MarrySignEntity marrySign = new MarrySignEntity();
		MarrySignEntity ms = new MarrySignEntity();
		if(u == null){
			JSONObject jsonObject = WeixinUtil.getUserInfo(openId);
			MarriedUserEntity user = new MarriedUserEntity();
			user.setCreatetime(new Date());
			user.setNickname(jsonObject.getString("nickname"));
			user.setPic(jsonObject.getString("headimgurl"));
			user.setOpenid(openId);
			marriedUserService.insert(user);
			marrySign.setUserid(user.getId());
			ms.setUserid(user.getId());
		}else{
			marrySign.setUserid(u.getId());
			ms.setUserid(u.getId());
		}
		marrySign.setWeddingid(Integer.parseInt(request.getParameter("id")));
		EntityWrapper<MarrySignEntity> wrapper = new EntityWrapper<MarrySignEntity>(marrySign);
		marrySign = marrySignService.selectOne(wrapper);
		if(marrySign != null){
			return R.ok("您已签到");
		}
		ms.setWeddingid(Integer.parseInt(request.getParameter("id")));
		marrySignService.save(ms);
		return R.ok("签到成功");
	}

	/**
	 * 查询自己所接受邀请的所有婚礼
	 * @param request
	 * @return
	 */
	public R alljieshouattendawedding(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer id=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(id);
		if(user==null){
			R.error("没有查询到该用户的数据");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getOpenid());
		return R.ok().put("userlist", marryParticipateService.queryListtongji(map));
	}

	/**
	 * 查询所有参加婚礼的人
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public R allattendawedding(HttpServletRequest request){
		String token = request.getParameter("token");
		String  id=request.getParameter("userId");
		MarriedUserEntity user=marriedUserService.queryObject(Integer.parseInt(id));
		if(user==null){
			return R.error("数据不存在");
		}
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		marryWedding.setUserId(user.getId());
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = this.marryWeddingService.selectOne(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weddingId", marryWedding.getId());
		map.put("offset", 0);
		map.put("limit", 1000);
		List<MarryParticipateEntity> list = marryParticipateService.queryList(map);
		List<MarriedUserEntity> list0 = new ArrayList<MarriedUserEntity>();
		List<MarriedUserEntity> list1 = new ArrayList<MarriedUserEntity>();
		List<MarriedUserEntity> list2 = new ArrayList<MarriedUserEntity>();
		List<MarriedUserEntity> list3 = new ArrayList<MarriedUserEntity>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			MarryParticipateEntity marryParticipateEntity = (MarryParticipateEntity) iterator.next();
			MarriedUserEntity marrieduser = new MarriedUserEntity();
			marrieduser.setNickname(user.getNickname());
			marrieduser.setPic(user.getPic());
			marrieduser.setOpenid(marryParticipateEntity.getOpenid());
			list3.add(marrieduser); 
			if(marryParticipateEntity.getStates() == 1){
				list1.add(marrieduser);
			}else if(marryParticipateEntity.getStates() == 2){
				list2.add(marrieduser);
			}
			//			if(marryParticipateEntity.getStates() == 0){
			//				list0.add(marrieduser);
			//			}else if(marryParticipateEntity.getStates() == 1){
			//				list1.add(marrieduser);
			//			}else if(marryParticipateEntity.getStates() == 2){
			//				list2.add(marrieduser);
			//			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list1", list1);
		m.put("list2", list2);
		m.put("list3", list3);
		return R.ok().put("data", m);
	}

	/**
	 * 保存参加婚礼
	 * @param request
	 * @return
	 */
	public R attendawedding(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer userid=Integer.parseInt(request.getParameter("userId"));
		MarriedUserEntity user=marriedUserService.queryObject(userid);
		try {
			if(user==null){
				R.error("没有查询到该用户的数据");
			}
			Integer states = Integer.parseInt(request.getParameter("states"));
			Integer id = Integer.parseInt(request.getParameter("id"));
			MarryParticipateEntity marryParticipate = new MarryParticipateEntity();
			marryParticipate.setOpenid(user.getOpenid());
			marryParticipate.setWeddingid(id);
			EntityWrapper<MarryParticipateEntity> wrapper = new EntityWrapper<MarryParticipateEntity>(marryParticipate);
			marryParticipate = marryParticipateService.selectOne(wrapper);
			if(marryParticipate == null){
				MarryParticipateEntity mp = new MarryParticipateEntity();
				mp.setOpenid(user.getOpenid());
				mp.setWeddingid(id);
				mp.setStates(states);
				marryParticipateService.save(mp);
			}else{
				marryParticipate.setStates(states);
				marryParticipateService.update(marryParticipate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok().put("openId", user.getOpenid());
	}


	/**
	 * 保存婚礼信息
	 * @param request
	 * @return
	 */
	public R saveWedding(HttpServletRequest request,HttpSession session){
		String token = request.getParameter("token");
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		System.out.println("userId="+request.getParameter("userId"));
		MarriedUserEntity userEntity=marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(userEntity==null){
			R.error("没有查询到该条数据");
		}
		String id=request.getParameter("id");
		MarryWeddingEntity queryObject = marryWeddingService.queryObject(Integer.parseInt(id));
		if(queryObject==null){
			return R.error("数据不存在");
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try{
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println(is);
			if(is != null){
				marryWedding.setPhoto(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[0], "married/photo/"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String bridename = request.getParameter("brideName");
		String content = request.getParameter("content");
		String groomname = request.getParameter("groomName");
		String weddingaddress = request.getParameter("weddingAddress");
		String url = request.getParameter("bg");
		String color = "#"+request.getParameter("color");

		Date weddingdate = new Date();
		try {
			weddingdate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(request.getParameter("weddingDate").replace("T", " "));
		} catch (Exception e) {
			e.printStackTrace();
		}
		marryWedding.setBridename(bridename);
		marryWedding.setContent(content);
		marryWedding.setGroomname(groomname);
		marryWedding.setUserId(userEntity.getId());
		marryWedding.setWeddingaddress(weddingaddress);
		marryWedding.setWeddingdate(weddingdate);
		marryWedding.setBgcolor(color);
		marryWedding.setUrl(url);
		marryWedding.setId(Integer.parseInt(id));
		marryWeddingService.update(marryWedding);
		System.out.println("wedding"+marryWedding.getId());
		return R.ok().put("id", marryWedding.getId());
	}

	/**
	 * 查询婚礼记录
	 * @param request
	 * @return
	 */

	public R findWedding(HttpServletRequest request){
		String token = request.getParameter("token");
		Integer type = 0;
		String openId = "";
		MarryWeddingEntity marryWedding = new MarryWeddingEntity();
		MarriedUserEntity user = marriedUserService.queryObject(Integer.parseInt(request.getParameter("userId")));
		if(request.getParameter("id") == null || "".equals(request.getParameter("id")) || "null".equals(request.getParameter("id"))){
			System.out.println("----"+request.getParameter("id"));
			marryWedding.setUserId(user.getId());//邀请好友时通过当前用户id查询婚礼记录
			//				marryWedding.setUserId(user.getId());//邀请好友时通过当前用户id查询婚礼记录
		}else{
			marryWedding.setId(Integer.parseInt(request.getParameter("id")));//参加婚礼模块中通过婚礼id查询婚礼记录
		}
		if(request.getParameter("js_code")!=null&& !"".equals(request.getParameter("js_code")) && !"null".equals(request.getParameter("js_code"))){
			try {//好友通过邀请函中带的婚礼id查询婚礼记录
				MarryParticipateEntity marryParticipate = new MarryParticipateEntity();
				marryParticipate.setOpenid(user.getOpenid());
				marryParticipate.setWeddingid(Integer.parseInt(request.getParameter("id")));
				EntityWrapper<MarryParticipateEntity> wrapper = new EntityWrapper<MarryParticipateEntity>(marryParticipate);
				if(marryParticipateService.selectOne(wrapper) != null){
					type = 1;
				}
				marryWedding.setId(Integer.parseInt(request.getParameter("id")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		EntityWrapper<MarryWeddingEntity> wrapper = new EntityWrapper<MarryWeddingEntity>(marryWedding);
		marryWedding = this.marryWeddingService.selectOne(wrapper);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("marryWedding", marryWedding);
		map.put("user", marriedUserService.queryObject(marryWedding==null?0:marryWedding.getUserId()));
		map.put("type", type);
		map.put("openId", openId);
		return R.ok().put("data", map);
	}




	/**
	 * 上传婚礼图片
	 * @param request
	 * @param response
	 * @return
	 */
	private R fileweddingphoto(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		MarryWeddingEntity marryWeddingEntities=new MarryWeddingEntity();
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			InputStream[] is = uploadfile(multipartResolver, request);
			System.out.println(is);
			if(is != null){
				marryWeddingEntities.setPhoto(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[0], "married/photo/"));
			}
			marryWeddingService.insert(marryWeddingEntities);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok().put("id", marryWeddingEntities.getId());
	}

	/**
	 * 上传资料图片
	 * @param request
	 * @param response
	 * @return
	 */
	public R fileupload(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MarryPhotoEntity marryPhotoEntity=new MarryPhotoEntity();
		try {
			InputStream[] is = uploadfile(multipartResolver, request);

			if(is != null){
				for (int i = 0; i < is.length; i++) {
					marryPhotoEntity.setPic(FILEPATH+"married_photo/"+OssUploadUtil.uploadObject2OSS(is[i], "married/photo/"));
					marryPhotoService.save(marryPhotoEntity);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.ok();
	}


	//图片上传
	public InputStream[] uploadfile(CommonsMultipartResolver multipartResolver,HttpServletRequest request) throws IllegalStateException, IOException{
		InputStream[] is = null;
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest m= (MultipartHttpServletRequest)request;
			List<MultipartFile> detail=new ArrayList<MultipartFile>();
			//			if(m.getFiles("files").size() != 0){
			detail = m.getFiles("files");
			System.out.println(m.getFiles("files").size() + "-------------");
			is = new InputStream[detail.size()];
			for (int i = 0; i < detail.size(); i++) {
				if(!detail.get(i).isEmpty()){
					is[i] = detail.get(i).getInputStream();
				}
				//				}
			}
		}
		return is;
	}
	//==============================smallMe=====================================//

	@SuppressWarnings("resource")
	private R userLogin(HttpServletRequest request){
		int total = 0;
		//		int userId=0;
		try {
			String token = new Sha256Hash(""+new Date()).toString();
			Jedis jedis =  new Jedis(JEDISPATH,6379,30000);
			jedis.set(token, token);
			jedis.expire(token, 24*60*60*365);
			System.out.println("----生成---"+token);
			String code = request.getParameter("js_code");
			String nickName=request.getParameter("nickName");
			String avatarUrl=request.getParameter("avatarUrl");
			System.out.println("----->"+nickName);
			System.out.println("----->"+avatarUrl);
			String openId = WeixinUtilApp.getWeixinOpenId(code);
			MarriedUserEntity u = new MarriedUserEntity();
			u.setOpenid(openId);
			EntityWrapper<MarriedUserEntity> wrapper = new EntityWrapper<MarriedUserEntity>(u);
			u = this.marriedUserService.selectOne(wrapper);
			Integer id = 0;
			MarriedUserEntity us = marriedUserService.findByOpenIdLike(openId);
			if(code != null && !"".equals(code) && !"null".equals(code)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sidx", null);
				map.put("order", null);
				map.put("offset", 0);
				map.put("limit", 100);
				map.put("states", 1);
				if(u == null){
					MarriedUserEntity user = new MarriedUserEntity();
					user.setCreatetime(new Date());
					user.setNickname(nickName);
					user.setPic(avatarUrl);
					user.setOpenid(openId);
					user.setJurisdiction(0);
					marriedUserService.insert(user);
					map.put("userId", user.getId());
					//					userId=1;
					//新增后将用户id赋值给id
				}else{
					id = u.getId();
					map.put("userId", u.getId());
					map.put("offset", 0);
					map.put("limit", 10);
					if(u.getJurisdiction()==0){
						request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
					}else{
						request.getSession().setAttribute("jurisdiction", 1);
					}
					//					userId=1;
				}
				total = marryCartService.queryList(map).size();
				List<MarryOrdersEntity> list = marryOrdersService.queryList(map);
				if(list.size()==0){
					request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
				}else{
					request.getSession().setAttribute("jurisdiction", 1);
				}
			}else if(us!=null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sidx", null);
				map.put("order", null);
				map.put("offset", 0);
				map.put("limit", 100);
				map.put("userId", us.getId());
				total = marryCartService.queryList(map).size();
				if(marriedUserService.queryObject(us.getId()).getJurisdiction()==0){
					request.getSession().setAttribute("jurisdiction", 0);//无权限就不让其显示相关模块
				}else{
					request.getSession().setAttribute("jurisdiction", 1);
				}
				//				userId=1;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String,Object> map1=new HashMap<String, Object>();
			System.out.println(marryMainService.queryList(map1));
			map.put("userId", id);
			map.put("token", token);
			//			map.put("userId", userId);
			map.put("total", total);//购物车数量
			map.put("list", marryMainService.queryList(map1));//此处list为商品列表
			return R.ok().put(DATA, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("resource")
	private boolean getToken(String token){
		if(new Jedis(JEDISPATH,6379,30000).get(token) == null || "".equals(new Jedis(JEDISPATH,6379,30000).get(token))){
			return false;
		}else{
			return true;
		}
	}
	
}

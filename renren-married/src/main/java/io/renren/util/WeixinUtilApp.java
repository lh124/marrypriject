package io.renren.util;



import net.sf.json.JSONObject;
import io.renren.utils.HttpsClient;
import io.renren.utils.RRException;
import io.renren.weixin.util.CommonUtil;

public class WeixinUtilApp {
//	String openId = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxeb69173af9cb4cba&secret=b249fc6ffe795ed58e3328f9e36d1027&code="+code+"&grant_type=authorization_code";
	public static String GET_TOCKEN_URL = "https://api.weixin.qq.com/sns/jscode2session";
	public static String WECHAT_APPID = "wxc5ae0fb544f1aaef";
	public static String WECHAT_SECRTE = "27f7a4ebd879b6ee1eb71d6e58d83478";
//	public static String WECHAT_APPID = "wxeb69173af9cb4cba";
//	public static String WECHAT_SECRTE = "b249fc6ffe795ed58e3328f9e36d1027";
	
	/**
	 * 获取微信的openid
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String getWeixinOpenId(String code) throws Exception{
        StringBuffer str = new StringBuffer(GET_TOCKEN_URL);
        str.append("?");
        str.append("appid=" + WECHAT_APPID);
        str.append("&secret=" + WECHAT_SECRTE);
        str.append("&js_code=" + code);
        str.append("&grant_type=authorization_code");
        
        String back = HttpsClient.httpsGet(str.toString());
        JSONObject json = JSONObject.fromObject(back);
        
        return checkData(json, "openid");
	}
	
	public static String checkData(JSONObject json , String str) throws Exception{
		 if(json.containsKey(str)){
	        	String strTemp = json.getString(str);
	        	if(strTemp == null || strTemp.equals(""))
	        		throw new RRException(str + "值不存在，请检查参数是否正确");
	        	
	        	return strTemp;
	     } else {
	    	 throw new RRException(str + "参数不存在，请检查参数是否正确");
	     }
	}
	
	
	public static void main(String[] args) throws Exception{
		System.out.println(WeixinUtilApp.getWeixinOpenId("013T3orj2VfB6F0N81qj20fcrj2T3orT"));
	}
	/**
	 * 获取微信用户信息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @return WeixinUserInfo
	 */
	public static JSONObject getUserInfo( String openId) {
		String accessToken = CommonUtil.getToken(WECHAT_APPID, WECHAT_SECRTE).getAccessToken();
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		return jsonObject;
	}
}

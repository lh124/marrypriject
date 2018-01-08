package io.renren.util;

import io.renren.utils.HttpsClient;
import io.renren.weixin.util.CommonUtil;
import net.sf.json.JSONObject;

public class WeixinUtil {
	
	public static String GET_TOCKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public static String WECHAT_APPID = "wxb9072ff1ebcf745c";
	public static String WECHAT_SECRTE = "b298e38e02eb3d45ca5cc22c68e9bae5";
	
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
        str.append("&code=" + code);
        str.append("&grant_type=authorization_code");
        
        String back = HttpsClient.httpsGet(str.toString());
        JSONObject json = JSONObject.fromObject(back);
        return json.getString("openid");
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
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		return jsonObject;
	}

}

package io.renren.enums;

public enum WeChatEnum {

	WEICHAT_APP_ID("AppID","wxb9072ff1ebcf745c"),
	WEICHAT_APP_SECRETE("AppSecret","b298e38e02eb3d45ca5cc22c68e9bae5"),
	WEICHAT_URL_TOCHEN("access_token","https://api.weixin.qq.com"+
	"/cgi-bin/token?grant_type=client_credential&appid="+
	WEICHAT_APP_ID.getValue()+"&secret=" + 
	WEICHAT_APP_SECRETE.getValue()),
	WEICHAT_URL_TICHET("ticket", "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=");
	
	private String key;
	private String value;
	
	private WeChatEnum(String key,String value)
	{
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}

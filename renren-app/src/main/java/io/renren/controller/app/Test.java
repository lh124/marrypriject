package io.renren.controller.app;

import java.net.URLEncoder;

public class Test {
	
	public static String  GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public static String getCodeRequest(){
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("APPID", "wx948285e688ee8d66");
        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI","http%3a%2f%2f192.168.1.107%3a8080%2fwrs%2fsmart%2findex.html");
        GetCodeRequest = GetCodeRequest.replace("SCOPE", "SCOPE");
        result = GetCodeRequest;
        return result;
    }
    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(getCodeRequest());
    }

}

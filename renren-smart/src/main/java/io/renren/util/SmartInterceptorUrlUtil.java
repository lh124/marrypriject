package io.renren.util;

import java.util.regex.Pattern;

public class SmartInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/smart/userLogin",
				".*/smart/login.html",
				".*/smart/test.html",
				".*/smart/shouye/handler",
				".*/sysweixinmsg/handler", 
				".*/smart/captcha.jpg"};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			
		}
		
		return false;
	}
}

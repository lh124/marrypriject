package io.renren.util;

import java.util.regex.Pattern;

public class SmartInterceptorUrlUtil {

	public static boolean authCheck(String url){
		
		String[] annos = {".*/smart/userLogin",
				".*/smart/login.html",
				".*/smart/shouye/index.html",
				".*/smart/shouye/js6.html",
				".*/smart/captcha.jpg"};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			
		}
		
		return false;
	}
}

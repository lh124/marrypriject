package io.renren.util;

import java.util.regex.Pattern;

public class PublicInterceptorUrlUtil {

	public static boolean authCheck(String url){
		
		String[] annos = {};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			
		}
		
		return false;
	}
}

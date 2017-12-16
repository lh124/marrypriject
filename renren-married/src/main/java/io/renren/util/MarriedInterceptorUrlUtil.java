package io.renren.util;

import java.util.regex.Pattern;

public class MarriedInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/married/weixin/index.html",
						  ".*/married/callBack/msgPic",
						  ".*/married/weixin/index/list",
				          ".*/married/user/save"		
		};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			 
		}
		
		return false;
	}
}

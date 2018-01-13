package io.renren.util;

import java.util.regex.Pattern;

public class AppInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/appInterface/main", 
						  ".*/appInterface/teacher/main",
						  ".*/appInterface/test/main",
				          ".*/appInterface/student/main"		         
		 };
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			 
		}
		
		return false;
	}
}

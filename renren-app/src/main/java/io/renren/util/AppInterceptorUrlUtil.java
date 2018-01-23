package io.renren.util;

import java.util.regex.Pattern;

public class AppInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/appInterface/main", 
						  ".*/appInterface/teacher/main",
						  ".*/appInterface/test/main",
						  ".*/appInterface/callBack/msgPic",
						  ".*/appInterface/main",
						  ".*/appInterface/update",
						  ".*/appInterface/list",
						  ".*/appInterface/info/.*",
						  ".*/appInterface/save",
						  ".*/appInterface/updatestates",
						  ".*/appInterface/updateHandle",
						  ".*/appInterface/delete",
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

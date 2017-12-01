package io.renren.util;

import java.util.regex.Pattern;

public class TombstoneInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/tombstone/weixin/login.html",
				".*/tombstone/getrelationship",
				".*/tombstone/geterweima",
				".*/tombstone/savemessage",
				".*/tombstone/getdeaddata",
				".*/alter_getTheMubei",
				".*/index.php",
				".*/tombstone/uniform.html",
				".*/tombstone/uniform0.html",
				".*/tombstone/getbusinessCard",
				".*/tombstone/getallbusinessCard",
				".*/tombstone/weixin/dead.html",
				".*/tombstone/weixin/photo_show.html",
				".*/tombstone/callBack/msgPic"
				};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			 
		}
		
		return false;
	}
}

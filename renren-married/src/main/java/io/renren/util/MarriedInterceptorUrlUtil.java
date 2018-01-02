package io.renren.util;

import java.util.regex.Pattern;

public class MarriedInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/married/weixin/index.html",
						  ".*/married/callBack/msgPic",
						  ".*/married/htgl/marrieduser.html",
						  ".*/married/htgl/marrymain.html",
						  ".*/married/htgl/marrywedding.html",
						  ".*/married/weixin/index/list",
						  ".*/married/weixin/invite.html",
						  ".*/married/weixin/sign_in.html",
						  ".*/married/weixin/invite_card",
						  ".*/married/weixin/me/findWedding",
						  ".*/married/weixin/me/attendawedding",
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

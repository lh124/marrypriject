package io.renren.util;

import java.util.regex.Pattern;

public class SmartInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {".*/smart/userLogin",
				".*/smart/login.html",
				".*/smart/demo1.html",
				".*/smart/shouye/handler",
				".*/smart/chatwebsocket",
				".*/smart/websocket",
				".*/smart/shouye/uploadMedio",
				".*/smart/datainpution/getData",
				".*/smart/interface_test.html",
				//工作管理平台
				".*/smart/worklogin.html", 
				".*/smart/workManage.html",
				".*/smart/workManage2.html",
				".*/smart/workManage3.html",
				
				".*/smart/yinsishengming.html",
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

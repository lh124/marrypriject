package io.renren.util;

import java.util.regex.Pattern;

public class MarriedInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {
				          //微信端需要屏蔽掉验证登录的页面
						  ".*/married/weixin/index.html",//首页页面
				          ".*/married/user/save",//首页用户授权微信登录保存和查询微信用户记录的接口以及查询当前购物车商品数量
				          ".*/married/weixin/index/list",//首页商品列表接口
				          ".*/married/weixin/theme.html",//首页商品列表接口
				          ".*/married/weixin/marriing.html",//首页商品列表接口
				          
				          ".*/married/weixin/theme_details.html",//商品详情页面
				          ".*/married/weixin/index/findObj",//获取商品详情接口
				          
						  ".*/married/weixin/sign_in.html",//扫码签到页面
						  ".*/married/weixin/me/saveSign",//保存签到记录接口
						  
						  ".*/married/weixin/invite_main.html",//接收邀请的中间界面
						  ".*/married/weixin/invite_card.html",//接收邀请页面
						  ".*/married/weixin/me/attendawedding",//保存是否接受参加婚礼接口
						  ".*/married/weixin/me/findWedding",//通过id查询婚礼记录接口
						  ".*/married/weixin/send_bless.html",//进入祝福页面 
						  ".*/married/weixin/send_video.html",//保存祝福页面
						  ".*/married/weixin/me/saveBlessing",//保存普通祝福接口
						  ".*/married/weixin/blessing/pay",//保存红包祝福接口
						  ".*/married/weixin/blessing/notify",//支付回调接口
						  
						  ".*/married/weixin/notify",//支付接口回调接口
						  ".*/married/weixin/moneyNotify",//支付接口回调接口
						  
						  //PC端现场页面需要屏蔽掉
						  ".*/married/xianchang/hongbao.html",
						  ".*/married/xianchang/index.html",
						  ".*/married/xianchang/photoShow.html",
						  
						  //后台端需要屏蔽掉
						  ".*/married/callBack/msgPic",
						  ".*/married/htgl/marrieduser.html",
						  ".*/married/htgl/marrymain.html",
						  ".*/married/htgl/marrywedding.html",
						  ".*/married/htgl/marryhelp.html"		
		};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){ 
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			 
		}
		
		return false;
	}
}

package io.renren.util;

import java.util.regex.Pattern;

public class MarriedInterceptorUrlUtil {

	public static boolean authCheck(String url){
		String[] annos = {
				          //微信端需要屏蔽掉验证登录的页面
						  ".*/married/small/weixin/index.html",//首页页面
				          ".*/married/small/user/save",//首页用户授权微信登录保存和查询微信用户记录的接口以及查询当前购物车商品数量
				          ".*/married/small/weixin/index/list",//首页商品列表接口
				          ".*/married/small/weixin/theme.html",//首页商品列表接口
				          ".*/married/small/weixin/marriing.html",//首页商品列表接口
				          
				          ".*/married/small/weixin/theme_details.html",//商品详情页面
				          ".*/married/small/weixin/index/findObj",//获取商品详情接口
				          
						  ".*/married/small/weixin/sign_in.html",//扫码签到页面
						  ".*/married/small/weixin/me/saveSign",//保存签到记录接口
						  
						  ".*/married/small/weixin/invite_main.html",//接收邀请的中间界面
						  ".*/married/small/weixin/invite_card.html",//接收邀请页面
						  ".*/married/small/weixin/me/attendawedding",//保存是否接受参加婚礼接口
						  ".*/married/small/weixin/me/findWedding",//通过id查询婚礼记录接口
						  ".*/married/small/weixin/send_bless.html",//进入祝福页面 
						  ".*/married/small/weixin/send_video.html",//保存祝福页面
						  ".*/married/small/weixin/me/saveBlessing",//保存普通祝福接口
						  ".*/married/weixin/blessing/pay/small",//保存红包祝福接口
						  ".*/married/weixin/blessing/notify/small",//支付回调接口
						  
						  ".*/married/weixin/notify/small",//支付接口回调接口
						  ".*/married/weixin/moneyNotify/small",//支付接口回调接口
						  
						  //PC端现场页面需要屏蔽掉
						  ".*/married/xianchang/hongbao.html",
						  ".*/married/xianchang/index.html",
						  ".*/married/xianchang/photoShow.html",
						  
						  ".*/married/small/weixin/me/saveWedding",
						  
						  //小程序端接口
						  ".*/married/smallProgram/main",
						  
						  //后台端需要屏蔽掉
						  ".*/married/callBack/msgPic",
						  ".*/married/htgl/marrieduser.html",
						  ".*/married/htgl/marrymain.html",
						  ".*/married/htgl/marrywedding.html",
						  ".*/married/htgl/marryhelp.html"	,
						  
						  //测试
						  ".*/married/small/weixin/me/allSign",//保存签到记录接口
						  ".*/married/small/weixin/me/saveHelp",//保存签到记录接口
						  ".*/married/small/weixin/me/getUserPhoto",//保存签到记录接口
						  ".*/married/small/weixin/me/allweddingImg",
						  ".*/married/small/weixin/me/findAllWeddingPhoto",
						  ".*/married/small/weixin/me/getWeddingId",
						  ".*/married/small/weixin/me/fileupload",
						  ".*/married/small/weixin/me/blessManage",
						  ".*/married/small/weixin/me/saveBlessing",
						  ".*/married/small/weixin/me/filevideo",
						  ".*/married/small/weixin/me/allSign",
						  ".*/married/small/weixin/me/allMeSign",
						  ".*/married/small/weixin/me/saveSign",
						  ".*/married/small/weixin/me/alljieshouattendawedding",
						  ".*/married/small/weixin/me/allattendawedding",
						  ".*/married/small/weixin/me/attendawedding",
						  ".*/married/small/weixin/me/fileweddingphoto",
						  ".*/married/small/weixin/me/saveScreen",
						  ".*/married/small/weixin/me/getScreen",
						  ".*/married/small/weixin/me/picture",
						  ".*/married/small/weixin/cart/submitCart",
						  ".*/married/small/weixin/cart/deletemarrycart",
						  ".*/married/small/weixin/cart/list",
						  ".*/married/small/weixin/cart/save",
						  ".*/married/small/weixin/order/findOrderdemo",
						  ".*/married/small/weixin/pay",
						  ".*/married/small/weixin/user/userinfo",
						  ".*/married/small/weixin/user/getUserInfo",
						  ".*/married/smallProgram/main/blessManage",
						  
						  ".*/married/htgl/marryhelp.html",
						  ".*/smart/chatwebsocket",
						  ".*/smart/websocket",
						  ".*/websocket",
						  ".*/married/weixin/samll/findredmoneydetail",
		};
		boolean isMatch = false;
		
		for(int i = 0; i < annos.length; i++){ 
			
			isMatch = Pattern.matches(annos[i], url);
			if (isMatch) return true;
			 
		}
		
		return false;
	}
}

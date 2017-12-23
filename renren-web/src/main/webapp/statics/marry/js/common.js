/**
 * 
 */
function init(){
  $.ajax({ 
	type: "POST",
    url: "http://192.168.1.102:8080/wrs/publicModule/common/getWeChatSign",
    data: {"url":window.location.href},
    dataType:'json',
    success: function (result) {
			wx.config({
				debug: false, //
				appId: 'wx948285e688ee8d66', // 必填，公众号的唯一标识
				timestamp: result.timestamp, // 必填，生成签名的时间戳
				nonceStr: result.nonceStr, // 必填，生成签名的随机串
				signature: result.signature,// 必填，签名，见附录1
		        jsApiList: [
					'checkJsApi',
					'onMenuShareTimeline',
					'onMenuShareAppMessage',
					'onMenuShareQQ',
					'onMenuShareWeibo',
					'hideMenuItems',
					'showMenuItems',
					'hideAllNonBaseMenuItem',
					'showAllNonBaseMenuItem',
					'translateVoice',
					'startRecord',
					'stopRecord',
					'onRecordEnd',
					'playVoice',
					'pauseVoice',
					'stopVoice',
					'uploadVoice',
					'downloadVoice',
					'chooseImage',
					'previewImage',
					'uploadImage',
					'downloadImage',
					'getNetworkType',
					'openLocation',
					'getLocation',
					'hideOptionMenu',
					'showOptionMenu',
					'closeWindow',
					'scanQRCode',
					'chooseWXPay',
					'openProductSpecificView',
					'addCard',
					'chooseCard',
					'openCard'
			        ]
			    });
		} 
	});
  }
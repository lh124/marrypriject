// 变量定义
// 学生相册
var type_student_photo = 1;
// 智能校服
var type_smart_uniform = 2;
// 获取传递参数,1标识智能校服,2表示相册
var code = GetQueryString("state");

$(function(){
			weChatBinding();
			// 加载底部按钮栏
			loadHtml("tabbar/student_photo_tabbar.html", ".weui-tabbar");
			
			// 微信基本配置
	   		$.ajax({
	                type: "POST",
	                url: "../publicModule/common/getWeChatSign",
	                data: {"url":window.location.href},
	                dataType:'json',
	                success: function (result) { 
	                	var shareUrl = 'http://wrs.gykjewm.com/front/classes/classHomePage.html?classId=1';  //分享的URL地址
						var shareTitle = "微人生相册"; //分享的标题
						var shareImage = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/static-sources/wrsLogo.jpg"; //分享的图片地址
						var shareDesc = "不一样的毕业相册,不一样的毕业回忆"; //分享的描述信息
						
						wx.config({
							    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
							    appId: 'wxb9072ff1ebcf745c', // 必填，公众号的唯一标识
							    timestamp: result.timestamp, // 必填，生成签名的时间戳
							    nonceStr: result.nonceStr, // 必填，生成签名的随机串
							    signature: result.signature,// 必填，签名，见附录1
							    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone']  // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						});
						//注入成功会执行下面方法
						wx.ready(function(){
						//分享到朋友圈
						     wx.onMenuShareTimeline({
							    title: shareTitle, // 分享标题
							    link: shareUrl, // 分享链接
							    imgUrl: shareImage, // 分享图标
							    success: function () { 
							        // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
							//分享到朋友圈
							wx.onMenuShareAppMessage({
							    title: shareTitle, // 分享标题
							    desc: shareDesc, // 分享描述
							    link: shareUrl, // 分享链接
							    imgUrl: shareImage, // 分享图标
							    type: '', // 分享类型,music、video或link，不填默认为link
							    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
							    success: function () { 
							        // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
							//分享到QQ
							wx.onMenuShareQQ({
							    title: shareTitle, // 分享标题
							    desc: shareDesc, // 分享描述
							    link: shareUrl, // 分享链接
							    imgUrl: shareImage, // 分享图标
							    success: function () { 
							       // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							       // 用户取消分享后执行的回调函数
							    }
							});
							//分享到腾讯微博
							wx.onMenuShareWeibo({
							    title: shareTitle, // 分享标题
							    desc: shareDesc, // 分享描述
							    link: shareUrl, // 分享链接
							    imgUrl: shareImage, // 分享图标
							    success: function () { 
							       // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
							//分享到QQ空间
							wx.onMenuShareQZone({
							    title: shareTitle, // 分享标题
							    desc: shareDesc, // 分享描述
							    link: shareUrl, // 分享链接
							    imgUrl: shareImage, // 分享图标
							    success: function () { 
							       // 用户确认分享后执行的回调函数
							    },
							    cancel: function () { 
							        // 用户取消分享后执行的回调函数
							    }
							});
							wx.getLocation({
							    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
							    success: function (res) {
							        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
							        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
							        var speed = res.speed; // 速度，以米/每秒计
							        var accuracy = res.accuracy; // 位置精度
							        //alert(latitude);
							        $.ajax({
										type: "POST",
									    url: "./user/setUserPosition",
									    data: {"latitude": latitude, "longitude": longitude},
									    dataType: "json",
									    success: function(result){
											if(result.status == 'ok'){//登录成功
												
											}else{
											
											}
										}
									});
							    }
							});
						});
					//注入失败会执行下面方法
					wx.error(function(res){
						//alert(res);
				  	});
	                }
	            });
		});
		
		// 绑定微信
		function weChatBinding(){
			var code = GetQueryString("code");
			if ( code != undefined && code != null && code != "" ) {
				$.ajax({
					type: "POST",
				    url: "./user/weChatBinding",
				    data: {"code": code},
				    dataType: "json",
				    success: function(result){
						if(result.status == 'ok'){//登录成功
							$('#showPan').text(result.msg);
							$('#iosDialog2').fadeIn(100);
						}else{
							//错误不通知
							//$('#showPan').text(result.msg);
							//$('#iosDialog2').fadeIn(100);
						}
					}
				});
			}
		}

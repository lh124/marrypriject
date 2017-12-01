		// 获取传递参数,1标识智能校服,2表示相册
		//var code = GetQueryString("state");
        var i = 0;
        document.getElementById("yanzhengma").style.display = "none";
		var state = GetQueryString("state");
		if (state == null|| state == undefined || state == '')
			state = 2;
		
		
		var iosDialog1 = $('#iosDialog1'),
            iosDialog2 = $('#iosDialog2'),
            loadingToast = $('#loadingToast');
		
		$(function(){
			// 这个是框架出的一个问题，，，，暂时用这个解决着
			$('.page').css('opacity','1');
			$('#dialogs').on('click', '.weui-dialog__btn_default', function(){
	            $(this).parents('.js_dialog').fadeOut(200);
	        });
		});
		// 刷新验证码
		function reflushCode(){
			$("#checkCode").attr('src',"captcha.jpg?t=" + Date.parse(new Date()));
		}
		// 登录提交
		function login(){
			var userName = $("#userName").val();
			var password = $("#password").val();
			var code = $("#code").val();
			
			// 先设置为所有都不提示
			$('.weui-cell').attr('class', 'weui-cell');
			
			if(userName === null || userName === "" || userName.indexOf(' ')>0){
		
				//$("#account").parent().parent().addClass("weui-cell_warn");
				var $acc = $("#userName").parent().parent();
				forWarm($acc, '账号输入错误');
				return ;
			}
			
			if(password === null || password === "" || password.indexOf(' ')>0){
		
				//$("#account").parent().parent().addClass("weui-cell_warn");
				var $acc = $("#password").parent().parent();
				forWarm($acc, '密码输入错误');
				return ;
			}
			
			var data = "mobile="+userName+"&password="+password+"&checkCode="+code+"&state="+state+"&type="+i;
			$.ajax({
				type: "POST",
			    url: "userLogin",
			    data: data,
			    dataType: "json",
			    success: function(result){
					if(result.status == 'ok'){//登录成功
						//parent.location.href ='index.html';
						iosDialog1.fadeIn(200);
					}else{
						if(result.msg == "密码错误"){
							i++;
							if(i >= 3){
								document.getElementById("yanzhengma").style.display = "";
							}
						}
						forWarm(null,result.msg);
						reflushCode();
					}
				}
			});
		};
		
		//  验证数据
		// obj 需要修改css的对象，必须是jquery对象，msg是错误提示内容
		function forWarm(obj,msg){
			var $tooltips = $('.js_tooltips');
			
			if ($tooltips.css('display') === 'none'){
				
				// toptips的fixed, 如果有`animation`, `position: fixed`不生效
				$('.page.cell').removeClass('slideIn');
				
				$tooltips.text(msg);
				$tooltips.css('display', 'block');
				
				setTimeout(function () {
					$tooltips.css('display', 'none');
					$tooltips.text("错误提示");
				}, 2000);
			}
			// 输入框提示
			if(typeof(obj) != 'undefined' && obj != null){
					obj.addClass("weui-cell_warn");
			}
		}
		
		//  微信登录验证
		$(function(){
			var code = GetQueryString("code");
			if ( code != undefined && code != null && code != "" ) {
				$.ajax({
				type: "GET",
			    url: "./user/weChatuserLogin?code=" + code,
			    dataType: "json",
			    success: function(result){
					if(result.status == 'ok'){//登录成功
						parent.location.href ='index.html?state='+ state;
					}
				}
			});
			} 
		});
		// 绑定微信
		function weChatBinding(){
			window.location.href = 
			'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http%3a%2f%2fwrs.gykjewm.com%2fsmart%2findex.html&response_type=code&scope=snsapi_base&state=' + state+ '#wechat_redirect';
		}
		
		function cancelBinding(){
			window.location.href = 'index.html';
		}
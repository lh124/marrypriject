		// 获取传递参数,1标识智能校服,2表示相册
		//var code = GetQueryString("state");
		var state = 3;
		
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
			
			if(userName === null || userName === "" || userName.indexOf(' ')>0){
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
			
			var data = "mobile="+userName+"&password="+password+"&state="+state;
			$.ajax({
				type: "POST",
			    url: "../userLogin",
			    data: data,
			    dataType: "json",
			    success: function(result){
					if(result.status == 'ok'){//登录成功
						parent.location.href ='index.html';
						//iosDialog1.fadeIn(200);
					}else{
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
			var code = GetQueryString("code");
			alert(code);
			$.ajax({
				type: "POST",
			    url: "../user/weChatBinding?code="+code,
			    dataType: "json",
			    success: function(result){
					if(result.status == 'ok'){
						$('#iosDialog2').fadeIn(200);
					}
				}
			});
		}
		
		function cancelBinding(){
			window.location.href = 'index.html';
		}
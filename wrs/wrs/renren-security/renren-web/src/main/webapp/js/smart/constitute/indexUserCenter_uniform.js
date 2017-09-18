$(function(){
			$.ajax({
			  type: 'GET',
			  url: './user/userInfo',
			  dataType: "json",
			  success: function(result){
					var myContainer = $('#functionContainer');
					myContainer.empty();
					var url = 'http://static.gykjewm.com/';
					$("#nickName").text(result.info.studentName);
					$("#headImg").attr("src", result.info.pic);
					var content = "";
					// 添加功能
					/*for(var i = 0; i < result.user.authList.length; i++){
						if (result.user.authList[i].perm == 'user:logined' || result.user.authList[i].perm == 'uniform:all') 
							continue;
						content = '<a class="weui-cell weui-cell_access" href="' + result.user.authList[i].url + '">'+
									'<div class="weui-cell__hd"><img src="' + result.user.authList[i].icon + '" alt="" style="width:20px;margin-right:5px;display:block"></div>'+
									'<div class="weui-cell__bd">'+
										'<p>' + result.user.authList[i].name +'</p>'+
									'</div>'+
									'<div class="weui-cell__ft"></div>'+
									'</a>';
						
						myContainer.append(content);
					}*/
					
					if (result.info.openId != null && result.info.openId != ""){
						var htmlT = '<a id="cancelBinding" class="weui-cell weui-cell_access" onclick="$(\'#iosDialog1\').fadeIn(200);" href="javascript:void(0);">'+
										'<div class="weui-cell__hd"><img src="/statics/images/userCenter/calcel.png" alt="" style="width:20px;margin-right:5px;display:block"></div>'+
										'<div class="weui-cell__bd">'+
											'<p>取消微信绑定</p>'+
										'</div>'+
										'<div class="weui-cell__ft"></div>'+
									'</a>';
						$('#baseFunction').append(htmlT);
					}

			  },
			  error: function(){
			  		alert("操作错误");
			  }
			});
		});

function cancelWechatBinding(){
	$.ajax({
		  type: 'GET',
		  url: './user/cancelWeChatBinding',
		  dataType: "json",
		  success: function(result){
			  $('#iosDialog1').fadeOut(200);
			  forWarm(null,result.msg);
			  if (result.status == "ok") {
				  $('#cancelBinding').remove();
			  }
		  }
	});
}

//验证数据
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

function showMsg(msg){
	$('#showPan').text(msg);
	$('#iosDialog2').fadeIn(100);
}
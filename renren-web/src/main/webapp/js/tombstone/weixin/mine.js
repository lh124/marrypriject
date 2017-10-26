$(function () {
	$.ajax({
		type: "POST",
	    url: "../getloginuser",
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    		if(result.user.pic != null && result.user.pic != ""){
	    			$("#userimg").attr("src",result.user.pic);
	    		}
	    		$("#username").html('<b>'+result.user.nickname+'</b>');
			}else{
				forWarm(null,result.msg);
			}
		}
	});
});

function updateimage(){
	window.location.href = "updatephoto.html?id="+$("#userId").val();
}

function alterUserPassword(){
	var oripassword = $("#oldPassword").val();
	var newpassword = $("#newPassword").val();
	if(oripassword === null || oripassword === "" || oripassword.indexOf(' ')>0){
		var $acc = $("#oldPassword").parent().parent();
		forWarm($acc, '账号输入原密码错误');
		return ;
	}
	if(newpassword === null || newpassword === "" || newpassword.indexOf(' ')>0){
		var $acc = $("#newPassword").parent().parent();
		forWarm($acc, '账号输入新密码错误');
		return ;
	}
	var data = "oripassword="+oripassword+"&newpassword="+newpassword;
	$.ajax({
		type: "POST",
	    url: "../updatepassword",
	    data: data,
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    		forWarm(null,result.msg);
			}else{
				forWarm(null,result.msg);
			}
		}
	});
}

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
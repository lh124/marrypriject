$(function(){
	$.ajax({
		type: "POST",
	    url: "../weixin/me/jurisdiction",
		dataType: "json",
		success: function(result){
		     if(result.status == 'ok'){
		    	 $("#total").html(result.map.total);
		    	 if(result.map.obj != null){
		    		 $("#weddingId").val(result.map.obj);
		    	 }else{
		    		 $("#weddingId").val("qwe");
		    	 }
		     }
		}
	});
});

function closeCk(){
	$("#iosDialog1").fadeOut(200);
}

function toWeddingId(type){
	if(isNaN($("#weddingId").val())){//判断当前用户是否有婚礼请柬
		$("#iosDialog1").fadeIn(200);
	    return false;
	}
	if(type==1){//去祝福管理
		window.location.href="bless_manage.html";
	}else if(type==2){//去好友回馈
		window.location.href="feedback.html";
	}else if(type==3){//去屏幕管理
		window.location.href="screen_management.html?weddingId="+$("#weddingId").val();
	}else if(type==4){//去数据统计
		window.location.href="data_statistics.html";
	}else if(type==5){//去资料上传
		window.location.href="upload.html";
	}
}

function hunlihudong(){
        $.ajax({
				type: "POST",
			    url: "../weixin/me/allMeSign",
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				         if(result.list.length == 0){
				             alert("暂无");
				         }else if(result.list.length == 1){
				             window.location.href = "interact.html?weddingId="+result.list[0].id;
				         }else{
				             window.location.href = "smqdlist.html";
				         }
				     }
				}
		 });
}

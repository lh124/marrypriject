$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/schoolNoticeInfo?id=" + $("#id").val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var pic = "";
				if(r.schoolnotice.noticepic == null || r.schoolnotice.noticepic == ""){
	            	  
	              }else{
	            	  pic = '<div style="text-align: center;"><img src="'+ r.schoolnotice.noticepic + '" width="100%" height="55%"></div>';
	              }
				var content = '<div style="text-align: center;padding-top: -20px;font-size:20px;"><b>' + r.schoolnotice.title +'</b></div>' +
				              pic + 
				              '<div><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+ r.schoolnotice.content + '</p></div><br>'+
				              '<div style="float:right;padding-right: 20px;font-size:20px;"><p >发布时间：'+ r.schoolnotice.createtime + '</p></div>';
			$("#weui-cells").html(content);
				
			}
		}
	});
});
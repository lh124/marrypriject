$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/shouyeInfo?id=" + $("#id").val() + "&type=1",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var pic = "";
				if(r.obj.noticepic == null || r.obj.noticepic == ""){
					  pic = '<div style="text-align: center;"><img src="http://static.gykjewm.com/smart_notice_pic/smart_notice_pic_1505892816000.jpg" width="100%" height="55%"></div>';
	              }else{
	            	  pic = '<div style="text-align: center;"><img src="'+ r.obj.noticepic + '" width="99%" height="400px"></div>';
	              }
				var content = '<div style="text-align: center;padding-top: -20px;font-size:20px;"><b>' + r.obj.title +'</b></div>' +
				              pic + 
				              '<div><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+ r.obj.content + '</p></div><br>'+
				              '<div style="float:right;padding-right: 20px;font-size:20px;"><p >发布时间：'+ r.obj.createtime + '</p></div>';
			$("#weui-cells").html(content);
				
			}
		}
	});
});
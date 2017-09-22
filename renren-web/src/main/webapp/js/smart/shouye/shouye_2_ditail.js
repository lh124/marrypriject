$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/shouyeInfo?id=" + $("#id").val() + "&type=2",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var pic = "";
				var type = r.obj.type;
				if(type == "1"){
					type = "家庭作业";
				}else if(type == "2"){
					type = "课堂作业";
				}
				if(r.obj.pic == null || r.obj.pic == ""){
					  
	              }else{
	            	  pic = '<div style="text-align: center;"><img src="'+ r.obj.pic + '" width="100%" height="55%"></div>';
	              }
				var content = '<div style="text-align: center;padding-top: -20px;font-size:20px;"><b>' + r.obj.name +'【'+ type+'】</b></div>' +
				              pic + 
				              '<div><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+ r.obj.content + '</p></div><br>';
			$("#weui-cells").html(content);
				
			}
		}
	});
});
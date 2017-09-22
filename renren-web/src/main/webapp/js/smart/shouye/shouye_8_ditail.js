$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/shouyeInfo?id=" + $("#id").val() + "&type=8",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var pic = "";
				if(r.obj.pic == null || r.obj.pic == ""){
					  
	              }else{
	            	  pic = '<div style="text-align: center;"><a href="'+ r.obj.pic + '">打开</a></div>';
	              }
				var content = '<div style="text-align: center;padding-top: -20px;font-size:20px;"><b>' + r.obj.name +'</b></div>' +
				r.obj.pic ;
			$("#weui-cells").html(content);
				
			}
		}
	});
});
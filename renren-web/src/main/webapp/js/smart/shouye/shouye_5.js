$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/list_5?page=1&limit=1000&sidx=&order=desc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				for(var i = 0; i< r.page.list.length; i++){
					var pic = "";
					if(r.page.list[i].pic == null || r.page.list[i].pic == ""){
			        	   
			        }else{
			        	pic = '<div style="text-align: center;"><img src= "' +  r.page.list[i].pic+'" width="100%" height="55%"></div>';
			        };
					content += '<div><p style="font-size:20px;"><b>' + r.page.list[i].title +'</b></p></div>' +
					           pic +
					           '<div><p style="font-size:18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + r.page.list[i].content + '</p></div><br>';
				}
			$("#weui-cells").html(content);
				
			}
		}
	});
});
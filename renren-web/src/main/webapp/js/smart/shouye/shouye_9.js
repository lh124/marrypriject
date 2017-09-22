$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/list_9?page=1&limit=1000&sidx=&order=desc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				for(var i = 0; i< r.page.list.length; i++){
					var pic = "";
					if(r.page.list[i].freshmanpic == null || r.page.list[i].freshmanpic == ""){
			        	   
			        }else{
			        	var index1=r.page.list[i].freshmanpic.lastIndexOf(".");
			        	var index2=r.page.list[i].freshmanpic.length;
			        	var suffix=r.page.list[i].freshmanpic.substring(index1+1,index2);//后缀名
			        	pic = '<div style="text-align: center;"><img src= "' +  r.page.list[i].freshmanpic+'" width="100%" height="55%"></div>';
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
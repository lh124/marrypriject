$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/list_4?page=1&limit=1000&sidx=&order=desc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = '<div style="padding-left: 10%;padding-top:10%; width:82%;">';
				var obj = r.page.list;
				var bzrteacher = "";//班主任老师
				var teachername = "";//科任老师
				var studentname = "";//班委
				var contents = "";
				for(var i = 0; i < obj.length; i++){
					if(obj[i].type == "1"){
						bzrteacher = obj[i].name ;
						teachername += obj[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;";
						contents = obj[i].content;
					}else if(obj[i].type == "2"){
						if(teachername.length > 50 && teachername.length < 60 || teachername.length > 230 && teachername.length < 240 || teachername.length > 410 && teachername.length < 420){
							teachername += obj[i].name + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							teachername += obj[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}else if(obj[i].type == "3"){
						if(studentname.length > 50 && studentname.length < 60 || studentname.length > 230 && studentname.length < 240 || studentname.length > 410 && studentname.length < 420){
							studentname += obj[i].name + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							studentname += obj[i].name + "&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
				}
				if(contents == null || contents == ""){
					contents = "暂无班级寄语";
				}
				content += '<div style="padding-top:3px; "><b>班&nbsp;&nbsp;主&nbsp;&nbsp;任：</b>' + bzrteacher + '</div>' +
					       '<div style="padding-top:3px; "><b>科任老师：</b>'+teachername + '</div>'+
				           '<div style="padding-top:3px; "><b>班委班干：</b>' + studentname + '</div>'+
				           '<div style="padding-top:3px; "><b>班级人数：</b>'+r.page.totalCount+'人</div>'+
				           '<div style="padding-top:3px; "><b>班级寄语：</b>'+ contents+'</div>' + 
				           "</div>";
			$("#weui-cells").html(content);
			}
		}
	});
});
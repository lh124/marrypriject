$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/list_4?page=1&limit=1000&sidx=&order=desc&classId="+$("#classId").val(),
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
					var classPost = "";
					if(obj[i].classPost!= null && obj[i].classPost != ""){
						classPost = "("+obj[i].classPost+")";
					}
					if(obj[i].type == "1"){
						bzrteacher = obj[i].name + classPost;
						teachername += obj[i].name + classPost+"&nbsp;&nbsp;&nbsp;&nbsp;";
						contents = obj[i].content;
					}else if(obj[i].type == "2"){
						if((teachername.length > 30 && teachername.length < 40) || 
						   (teachername.length > 180 && teachername.length < 200) || 
						   (teachername.length > 350 && teachername.length < 370) || 
						   (teachername.length > 510 && teachername.length < 530)){
							teachername += obj[i].name +  classPost+"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							teachername += obj[i].name +  classPost+"&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}else if(obj[i].type == "3"){
						if((studentname.length > 30 && studentname.length < 40) || 
						   (studentname.length > 180 && studentname.length < 200) || 
						   (studentname.length > 350 && studentname.length < 370) ||
						   (studentname.length > 510 && studentname.length < 530)){
							studentname += obj[i].name + classPost+"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							studentname += obj[i].name + classPost+"&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
				}
				if(contents == null || contents == ""){
					contents = "暂无";
				}
				if(bzrteacher == null || bzrteacher == ""){
					bzrteacher = "暂无";
				}
				if(teachername == null || teachername == ""){
					teachername = "暂无";
				}
				if(studentname == null || studentname == ""){
					studentname = "暂无";
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
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
				var t = 0;
				var s = 0;
				var kg = "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				for(var i = 0; i < obj.length; i++){
					var classPost = "";
					if(obj[i].classPost!= null && obj[i].classPost != ""){
						classPost = "("+obj[i].classPost+")";
					}
					if(obj[i].type == "1"){
						bzrteacher = obj[i].name + classPost;
						teachername += obj[i].name + classPost+"&nbsp;&nbsp;&nbsp;&nbsp;";
						contents = obj[i].content;
						t++;
					}else if(obj[i].type == "2"){
						if(t%2 == 0){
							teachername += (kg+obj[i].name +  classPost);
						}else{
							teachername += ("&nbsp;&nbsp;&nbsp;&nbsp;"+obj[i].name +  classPost);
						}
						t++;
					}else if(obj[i].type == "3"){
						if(s%2 == 0 && s != 0){
							studentname += (kg+obj[i].name + classPost);
						}else{
							if(s == 0){
								studentname += (obj[i].name + classPost);
							}else{
								studentname += ("&nbsp;&nbsp;&nbsp;&nbsp;"+obj[i].name + classPost);
							}
						}
						s++;
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
$(function(){
	$.ajax({
		type: "GET",
	    url: "../photoscore/userList?order=desc&page=1&limit=100&sidx=&examinationId=" + $('#examinationId').val() + 
	    "&userId=" + $('#userId').val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				
				for(var i = 0; i < r.page.list.length; i++){
					content += '<tr><td>'+ r.page.list[i].subjectName+'</td><td>'+
					r.page.list[i].subjectPoint+'</td><td>'+
					r.page.list[i].teacherComment+'</td></tr>';
					
				}
				$("tbody").html(content);
				
			}
		}
	});
});
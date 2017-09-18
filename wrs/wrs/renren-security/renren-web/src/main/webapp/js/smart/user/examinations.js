$(function(){
	$.ajax({
		type: "GET",
	    url: "../photoexamination/userList?order=desc&page=1&limit=100&sidx=&classId=" + $('#classId').val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				$("#classContainer").empty();
				for(var i = 0; i < r.page.list.length; i++){
					content = '<a class="weui-cell weui-cell_access" href="./examinationDetail.html?examinationId='+ r.page.list[i].id 
										+'">'+
						                '<div class="weui-cell__bd">'+
						                '<p>' + r.page.list[i].name + '</p>'+
						           '</div>'+
						            '<div class="weui-cell__ft"></div>'+
						        '</a>';
					$(".weui-cells").html(content);
				}
				
			}
		}
	});
});
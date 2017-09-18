$(function(){
	$.ajax({
		type: "GET",
	    url: "../photoclass/list?order=asc&page=1&limit=10&sidx=",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				$("#classContainer").empty();
				for(var i = 0; i < r.page.list.length; i++){
					content = '<a class="weui-cell weui-cell_access" href="../classes/classHomePage.html?classId='+ r.page.list[i].id 
										+'">'+
						                '<div class="weui-cell__bd">'+
						                '<p>' + r.page.list[i].name + '</p>'+
						           '</div>'+
						            '<div class="weui-cell__ft"></div>'+
						        '</a>';
					$("#classContainer").append(content);
				}
				
			}
		}
	});
});
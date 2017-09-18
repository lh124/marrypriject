$(function(){
	$.ajax({
		type: "GET",
	    url: "./class/list",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				$("#classContainer").empty();
				content = '<a class="weui-cell weui-cell_access" href="./user/classesFunction.html?classId='+ r.cla.id 
							+'">'+
			                '<div class="weui-cell__bd">'+
			                '<p>' + r.cla.className + '</p>'+
			           '</div>'+
			            '<div class="weui-cell__ft"></div>'+
			        '</a>';
			$(".weui-cells").html(content);
				
			}
		}
	});
});
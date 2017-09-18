$(function(){
	$.ajax({
		type: "GET",
	    url: "../photoclass/adminClass",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				for(var i = 0; i < r.classList.length; i++){
					content += '<a class="weui-cell weui-cell_access" href="classSetup.html?classId='+ r.classList[i].id 
										+'">'+
						                '<div class="weui-cell__bd">'+
						                '<p>' + r.classList[i].name + '</p>'+
						           '</div>'+
						            '<div class="weui-cell__ft"></div>'+
						        '</a>';
				}
				$("#classContainer").html(content);
			}
		}
	});
});
$(function(){
	$.ajax({
		type: "GET",
	    url: "../photograduationtime/queryAllTime?collegeId=" + $('#collegeId').val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				if ( r.timeList.length > 0) {
					
					for(var i = 0; i < r.timeList.length; i++){
						var htmContent = '<a class="weui-cell weui-cell_access" '+
											' href="allClasses.html?timeId='+ r.timeList[i].id +'&collegeId=' + $('#collegeId').val()+ '">'+
							                '<div class="weui-cell__bd">'+
							                    '<p>' + r.timeList[i].graduationName + '</p>'+
							                '</div>'+
							                '<div class="weui-cell__ft">'+
							                '</div>'+
							           '</a>';
						
						$('#timeContainer').append(htmContent);
					}
				}
				
			}
	    }
	});
});
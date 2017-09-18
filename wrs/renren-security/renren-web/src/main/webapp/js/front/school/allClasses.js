
$(function(){
	
	var para = "";
	
	if (isNaN($('#schoolId').val())) {
		para = "gradutionTimeId=" + $('#timeId').val() + "&collegeId=" + $('#collegeId').val();
	} else {
		para = "gradutionTimeId=" + $('#timeId').val() + "&schoolId=" + $('#schoolId').val();
	}
	
	$.ajax({
		type: "GET",
	    url: "../photoclass/queryByGruadtionTime?" + para,
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				
				if ( r.photoClass.length > 0 ) {
					
					for(var i = 0; i < r.photoClass.length; i++){
						var htmContent = '<a class="weui-cell weui-cell_access" '+
											' href="../classes/classHomePage.html?classId='+ r.photoClass[i].id +'">'+
							                '<div class="weui-cell__bd">'+
							                    '<p>' + r.photoClass[i].name + '</p>'+
							                '</div>'+
							                '<div class="weui-cell__ft">'+
							                '</div>'+
							           '</a>';
						
						$('#classContainer').append(htmContent);
					}
				}
				
			}
	    }
	});
});
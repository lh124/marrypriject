$(function(){
	$(function(){
		$.ajax({
	        type: 'GET',
	        url:'./shouye/getTeacherClass',
	        dataType: 'json',
	        success: function(data){
	           if(data.status == "ok"){
	           		$("#listschool").html(content);
	               var content = "";
	               for(var i = 0; i < data.list.length; i++){
	                   content += '<a class="weui-cell weui-cell_access" href="./user/classesFunction.html?classId='+data.list[i].id+'">'+
	                              '<div class="weui-cell__bd"><p>'+data.list[i].className+'</p></div><div class="weui-cell__ft">'+
	                              '</div></a>';
	               }
	               $("#listschool").html(content);
	           }
	        }
	 	});
	});
});
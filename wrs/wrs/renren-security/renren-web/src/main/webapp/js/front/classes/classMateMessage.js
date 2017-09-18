$(function (){
	 $.ajax({
         type: 'GET',
         url:'../photoclassmates/info/' + $('#id').val(),
         dataType: 'json',
         success: function(data){
        	 if(data.status == 'ok' && data.photoClassmates != null) {
        		 $('#contents').html(data.photoClassmates.contents);
        	 } else {
        		 alert(data.msg);
        	 }
         }
     });
});
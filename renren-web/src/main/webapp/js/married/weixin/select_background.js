		
		function init(){
                 $.ajax({
					type: "POST",
				    url: "../weixin/me/allweddingImg?page="+1,
				    dataType: "json",
				    success: function(result){
				        if(result.status == 'ok'){
				             var content = "";
				             for(var i = 0; i < result.list.length; i++){
				                 content += '<div class="placeholder">'+
				                            '<img src="'+result.list[i].url+'" />'+
				                            '<input class="radio" checked="" type="radio" name="bg" value="'+result.list[i].url+'"></div>';
				             }
				             $("#list").html(content);
				        }
					}
				});
           }
           
           function next(){
               var obj = document.getElementsByName("bg");
                 var chkObjs = "";
                 for (var i=0;i<obj.length;i++){ //遍历Radio
					 if(obj[i].checked){
						 chkObjs=obj[i].value;
					 } 
				 }
				 window.location.href="card_information.html?url="+chkObjs; 
           }
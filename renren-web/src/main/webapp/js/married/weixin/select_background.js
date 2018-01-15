		
		function init(){
                 $.ajax({
					type: "POST",
				    url: "../weixin/me/allweddingImg?page="+1,
				    dataType: "json",
				    success: function(result){
				        if(result.status == 'ok'){
				             var content = "";
				             for(var i = 0; i < result.list.length; i++){
				                 if(i%2==0){
				                     content +='<div class="weui-flex">';
				                 }
				                 content += '<div class="weui-flex__item"><div class="placeholder">'+
				                            '<img src="'+result.list[i].url+'" style="width:180%;" />'+
				                            '<input class="radio" checked="" type="radio" name="bg" value="'+result.list[i].url+'"></div></div>';
				                 if((i+1)%2==0){
				                     content += '</div>';
				                 }
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
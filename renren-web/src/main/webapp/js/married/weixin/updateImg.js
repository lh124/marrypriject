$(function(){
		/*图片预览*/
		function addNewContent(obj) {
			$("#uploaderFiles").html("");
			var oldBox = "";
			for(var a = 0; a < obj.length; a++) {
				oldBox += "<li class='weui-uploader__file'><img class='weui-uploader__file weiImg' name='msgpic' src='"+obj[a]+"' /></li>";
			}
			$("#picSize").text(obj.length + "/9");
			$("#uploaderFiles").html(oldBox);
			$(".weiImg").on("click",function(){
				 var index = $(this).prevAll().length;
				 var src=$(this).attr("src"); 
			  	 wx.previewImage({
			      urls:obj
			     }); 
		  	});
		}
		wx.ready(function(){
		/*添加 图片*/
			var images = {
			    localId: [],
			    serverId: []
			};
		    var length = 0;
			  //图片选择
			document.querySelector('#selectfiles1').onclick = function () {
			    wx.chooseImage({
			      count: 9, 
			      success: function (res) {
			        localId = res.localIds;
			        length = localId.length
			        addNewContent(localId);
			      } 
			    });
			 };
			document.querySelector('#postfiless').onclick = function () {
		      if(length > 0){
		    	        $("#loadingToast").fadeIn(200);
			            var k = 0;
			            var kk = 0;
				            $.ajax({
								type: "POST",
							    url: "../weixin/me/getWeddingId",
								dataType: "json",
								    success: function(r){
									    var json = r;
									    if(json.code == '0'){
									    	for(var i = 0; i < length; i++){
						                    	kk = i;
							                        wx.uploadImage({
										        	localId: localId[i],
										        	success: function (res) {
										        	     $.ajax({ 
															url : "../../smart/shouye/uploadMedio?serverId="+res.serverId, 
															type : 'POST', 
								        					dataType:"json",  
															contentType : false,
															success : function(responseStr) { 
															     var imgSrc = "http://wrs.gykjewm.com/statics/video/"+responseStr.path;
															     function getBase64(img){//传入图片路径，返回base64
											        			    function getBase64Image(img,width,height) {//width、height调用时传入具体像素值，控制大小 ,不传则默认图像大小
											          				   var canvas = document.createElement("canvas");
															            canvas.width = width ? width : img.width;
															            canvas.height = height ? height : img.height;
															            var ctx = canvas.getContext("2d");
															            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
															            var dataURL = canvas.toDataURL();
															            return dataURL;
															         }
															        var image = new Image();
															        image.crossOrigin = '';
															        image.src = img;
															        var deferred=$.Deferred();
															        if(img){
															          image.onload =function (){
															            deferred.resolve(getBase64Image(image));//将base64传给done上传处理
															          }
															          return deferred.promise();//问题要让onload完成后再return sessionStorage['imgTest']
															        }
															      }
															      getBase64(imgSrc).then(function(base64){
															          set_upload_param(base64,32,json.id,3);
															          if((parseInt(kk)+parseInt(1))==length){
													                    	$("#loadingToast").fadeOut(200);
													                    	window.location.href="mine.html";
													                    }
															      });
																}
														});
												    }
										        });
									    }
									   }
									}
							});
				}
		};
	});
	});
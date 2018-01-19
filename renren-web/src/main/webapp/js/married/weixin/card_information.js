var localId;
var serverId;
$(function(){
		/*判断正确姓名新郎*/
		function checkGroomName(val){
		 reg = /^[\u4E00-\u9FA5]{2,4}$/;
		 if(!reg.test(val)){
		  	$(".msg").eq(0).show(); 
		 } else{
		 	$(".msg").eq(0).hide(); 
		 	return val;
		 }
		}
		/*判断正确姓名新娘*/
		function checkbrideName(val){
		 reg = /^[\u4E00-\u9FA5]{2,4}$/;
		 if(!reg.test(val)){
		  	$(".msg").eq(1).show(); 
		 } else{
		 	$(".msg").eq(1).hide(); 
		 	return val;
		 }
		}
		/*图片预览*/
		function addNewContent(obj) {
			$("#uploaderFiles").html("");
			var oldBox = "";
			for(var a = 0; a < obj.length; a++) {
				oldBox += "<li class='weui-uploader__file'><img class='weui-uploader__file weiImg' name='msgpic' src='"+obj[a]+"' /></li>";
			}
			$("#picSize").text(obj.length + "/1");
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
			      count: 1, 
			      success: function (res) {
			        localId = res.localIds;
			        length = localId.length;
			        addNewContent(localId);
			      }
			    });
			  };
			  
			  document.querySelector('#save').onclick = function () {
	              var groomName = $("#groomName").val();//新郎姓名
		          var brideName = $("#brideName").val();//新娘姓名
		          var weddingDate = $("#weddingDate").val();//婚礼日期
		          var weddingAddress = $("#weddingAddress").val();//婚礼地址
		          var content = $("#content").val();//婚礼留言
		          var bg = $("#url").val();//背景图片
		          var color = $("#color").val();//字体颜色
		          if(groom && bride && localId && weddingAddress && length){
	        	  $.ajax({
						type: "POST",
					    url: "../weixin/me/saveWedding?groomName="+groomName+"&color="+color.substring(1,color.length)+"&brideName="+brideName+"&weddingDate="+
					         weddingDate + "&weddingAddress="+weddingAddress+"&content="+content+
					         "&bg="+bg,
						dataType: "json",
						    success: function(r){
							    var json = r;
							    if(length > 0){
				                    wx.uploadImage({
							        	localId: localId[0],
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
												          set_upload_param(base64,30,json.id,1);
												      });
													}
											});
									    }
							        });
			                     }
							}
					});
	          }else{
					alert("请填写完整的正确信息");
					return false;
			  }
             };
		});
		var groom,bride;
		/*失去焦点事件*/
		$("#groomName").on("blur",function(){
			var groomName = $("#groomName").val();
			groom = checkGroomName(groomName);
		});
		$("#brideName").on("blur",function(){
			var brideName = $("#brideName").val();
			bride = checkbrideName(brideName);
		});
		$("#weddingAddress").on("blur",function(){
			var weddingAddress = $("#weddingAddress").val();
			if(weddingAddress == "null"){
				$(".msg").eq(2).show(); 
			}else{
				$(".msg").eq(2).hide(); 
				weddingAddress = $("#weddingAddress").val();
			}
		});
		 var max = 79;
		 $("#content").on("input", function(){
		     var text = $(this).val();
		     var len = text.length;
		     $("#count").text(len);
		     if(len > max){
		        alert("字数超过限制，请重新输入");  
		        $("#content").val(text.substr(0,max));  
		     }
		     else{
		       $(this).closest(".weui_cell").addClass(".weui_cell_warn");
		     }
		  });
		$("#preview").on("click",function(){
			var bg = localStorage.getItem("select_bg");
			var groomName = $("#groomName").val();
			var weddingDate = $("#weddingDate").val();
			var brideName = $("#brideName").val();
			var weddingAddress = $("#weddingAddress").val();
			var col = $("#color").val();
			var content = $("#content").val();
			$(".fanhui").on("click",function(){
				$(".container").show();
				$(".page").eq(1).hide();
			});
			$("#shut").on("click",function(){
				$(".container").show();
				$(".page").eq(1).hide();
			});
			if(groom && bride && localId && weddingAddress){
				$(".container").hide();
				$(".page").eq(1).show();
				$(".invite_con").css("background-image","url("+bg+")");
				$(".invite_con").find("img").attr("src",localId);
				$(".invite_con").find("h3").text(groomName+"&"+brideName);
				$(".invite_con").find("h3").css("color",col);
				$(".invite_con").find("h4").text(weddingDate.substring(0,10)+"    "+weddingDate.substring(11));
				$(".invite_con").find("h4").css("color",col);
				$(".invite_con").find("p").css("color",col);
				$(".invite_con").find("p").eq(0).text(content);
				$(".invite_con").find("p").eq(1).text("地址："+weddingAddress);
			}else{
				alert("请填写完整的正确信息");
			}
		});
	});

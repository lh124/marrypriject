	$(function() {
		var code = GetQueryString("code");
	    $.ajax({
			type: "POST",
		    url: "../weixin/me/findWedding?id="+$("#weddingId").val()+"&code="+code,
			dataType: "json",
			success: function(result){
			     if(result.status == 'ok'){
			    	  if(result.data.type==1 || result.data.type == "1"){
			    		  window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/index.html&response_type=code&scope=snsapi_base&state=2#wechat_redirect";
			    		  return false;
			    	  }
			          if(result.data.marryWedding != null){
				          $("#groomNameandbrideName").html(result.data.marryWedding.groomname+"&"+result.data.marryWedding.bridename);
				          $("#weddingDate").html(result.data.marryWedding.weddingdate);
				          $("#weddingAddress").html("地址："+result.data.marryWedding.weddingaddress);
				          $("#content").html(result.data.marryWedding.content);
				          $('#photo').prop('src',result.data.marryWedding.photo);
				          $(".invite_con").css("background-image","url("+result.data.marryWedding.url+")");
				          $(".invite_con").find("h4").css("color",result.data.marryWedding.bgcolor);
						  $(".invite_con").find("p").css("color",result.data.marryWedding.bgcolor);
						  $(".invite_con").find("h3").css("color",result.data.marryWedding.bgcolor);
			          }
			          $("#openId").val(result.data.openId);
			     }else{
			    	 if(result.msg.indexOf("您未授权获取微信信息") >= 0){
			    		 $("#msg").html(result.msg);
			    		 $("#msg1").hide();
			    		 $("#iosDialog1").fadeIn(200);
			    		 //window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/invite_card.html?id="+$("#weddingId").val()+"&response_type=code&scope=snsapi_base&state=2#wechat_redirect"; 
			    	 }else{
			    		 $("#msg").html(result.msg.substring(0,9));
				    	 $("#msg1").html(result.msg.substring(10));
				    	 $("#iosDialog1").fadeIn(200);
			    	 }
			     }
			}
	 	});
 	});
 	
	function closeCk(){
		wx.closeWindow();
		$("#iosDialog1").fadeOut(200);
	}
	
 	function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
 	
 	function update(states){
 	      var id = $("#weddingId").val();
 	      $.ajax({
				type: "POST",
			    url: "../weixin/me/attendawedding?states="+states+"&id="+id+"&openId="+$("#openId").val(),
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				          window.location.href="send_bless.html?weddingId="+id+"&openId="+result.openId; 
				     }
				}
		 	});
 	}
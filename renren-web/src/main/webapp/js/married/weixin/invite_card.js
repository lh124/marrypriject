	$(function() {
	    $.ajax({
			type: "POST",
		    url: "../weixin/me/findWedding?id="+$("#weddingId").val(),
			dataType: "json",
			success: function(result){
			     if(result.status == 'ok'){
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
			     }
			}
	 	});
 	});
 	
 	function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
 	
 	function update(states){
 	      var id = $("#weddingId").val();
 	      var code = GetQueryString("code");
 	      $.ajax({
				type: "POST",
			    url: "../weixin/me/attendawedding?states="+states+"&id="+id+"&code="+code,
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				          window.location.href="send_bless.html?weddingId="+id+"&openId="+openId; 
				     }
				}
		 	});
 	}
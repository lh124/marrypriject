	function init(){
           $.ajax({
				type: "POST",
			    url: "../weixin/me/findWedding?id="+$("#id").val(),
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				          $('#pic').prop('src',result.data.user.pic);
				          $("#nickname").html(result.data.user.nickname);
				          $("#gropname").html("1.欢迎来到"+result.data.marryWedding.groomname+
				                        "&"+result.data.marryWedding.bridename+"的婚礼");
				     }
				}
		 });
     }
     
     function saveqiandao(id){
         var code = GetQueryString("code");
         $.ajax({
				type: "POST",
			    url: "../weixin/me/saveSign?id="+id+"&code="+code,
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				         alert(result.msg);
				         window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/index.html&response_type=code&scope=snsapi_base&state=2#wechat_redirect";
				     }else{
				         alert(result.msg);
				         window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/index.html&response_type=code&scope=snsapi_base&state=2#wechat_redirect";
				     }
				}
		 });
     }
     function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
	}
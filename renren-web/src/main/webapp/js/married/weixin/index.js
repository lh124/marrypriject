$(function(){
        var ua = navigator.userAgent.toLowerCase();  
		if(ua.match(/MicroMessenger/i)!="micromessenger") {  
		     //return false;  
		}
	    var code = GetQueryString("code");
	    $.ajax({
			type: "POST",
		    url: "../user/save?code="+code,
		    dataType: "json",
		    success: function(result){
		        if(result.status == 'ok'){
		             $("#total").html(result.total);
		        }
			}
		});
	
	    $.ajax({
			type: "POST",
		    url: "../weixin/index/list?page=1&limit=10&order=&sdix=",
		    dataType: "json",
		    crossDomain: true,//是否跨域:是  
		    success: function(result){
				if(result.status == 'ok'){
				    var content = "";
				    var length = result.list.length;
				    if(length > 3){
				        length = 3;
				    }
				    for(var i = 0; i < length; i++){
				        content += '<a href="theme_details.html?id='+result.list[i].id+'"><dl><dt>'+
				                   '<img src="'+result.list[i].pic+'"/></dt><dd>'+
				                   '<p>'+result.list[i].title+'<span>￥'+result.list[i].price+
				                   '</span></p></dd></dl></a>';
				    }
				    content += '<div class="more"><a href="./theme.html">查看更多'+
				               '<img src="http://wrs.gykjewm.com/statics/marry/img/right.png" alt="">'+
				               '</a></div>';
				    $("#list").html(content);
				}
			}
		});
		
		function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
	});

function tosession(type){
	if(isNaN($("#userId").val())){//判断是否登录
		window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/index.html&response_type=code&scope=snsapi_base&state=2#wechat_redirect";
	    return false;
	}
	if(type==3){//去购物车
		window.location.href="shop_cart.html";
	}else if(type==4){//去我的
		window.location.href="mine.html";
	}
}
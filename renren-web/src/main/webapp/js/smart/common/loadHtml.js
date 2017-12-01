// url 请求的地址, selecter jQuery选择元素
function loadMyHtml(theUrl,sel){ 
	$.ajax({
        type: "GET",
        dataType: "text",
        url: theUrl,
        success: function (resultHtml) { 
        	$(sel).html(resultHtml);
        	console.log("请求成功");
        },
        error: function (){
        	console.log("请求失败");
        }
	});
}

//URL参数获取
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)
    	 return  unescape(r[2]); return null;
}
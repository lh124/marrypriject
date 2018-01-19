function init(){
         $.ajax({
				type: "POST",
				url: "../weixin/index/findObj?id="+$("#id").val(),
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
						$("#title").html(result.obj.title);
						$("#author").html(result.obj.author);
						$("#content").html(result.obj.content);
						$("#prices").val(result.obj.price);
						$("#price").html("￥"+result.obj.price);
					}
				}
		});
    }
    function savecart(id){
         if(isNaN($("#userId").val())){//将商品加入购物车时判断是否登录
              window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/index.html&response_type=code&scope=snsapi_base&state=2#wechat_redirect";
              return false;
         }else{
             $.ajax({
				type: "POST",
				url: "../weixin/cart/save?id="+id,
				dataType: "json",
				success: function(result){
				    alert(result.msg);
					if(result.status == 'ok'){
						window.location.href="shop_cart.html";
					}
				}
			});
         }
    }
    
    function savedingdan(id){
    	if(isNaN($("#userId").val())){//立即下单时判断是否登录
            window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http://wrs.gykjewm.com/married/weixin/index.html&response_type=code&scope=snsapi_base&state=2#wechat_redirect";
            return false;
    	}
         var price = $("#prices").val();
         $.ajax({
				type: "POST",
				url: "../weixin/order/saveOrder?mainId="+id+"&type=1",//传的是商品id
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
						window.location.href="confirm_order1.html?orderId="+result.id;//将订单id传过去
					}
				}
			});
    }
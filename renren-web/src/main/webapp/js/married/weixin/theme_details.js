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
						$("#pic").prop('src',result.obj.pic);
						$("#prices").val(result.obj.price);
						$("#price").html("￥"+result.obj.price);
					}
				}
		});
    }
    function savecart(id){
         if(isNaN($("#userId").val())){
              window.location.href="index.html";
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
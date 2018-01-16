$(function(){
         $.ajax({
				type: "POST",
				url: "../weixin/order/findOrder?id="+$("#id").val()+"&type=1",
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
						if(result.map.marryOrders.states == '1'){
							$("#zhifu").html("订单状态：支付成功");
						}else{
							$("#zhifu").html("订单状态：未支付");
						}
					    if(result.map.type == 1){
					        $("#price").html("￥"+result.map.marryMain.price);
					    }else if(result.map.type == 2){
					    	var pricetotal = 0.00;
					    	for(var i = 0; i < result.map.marryOrders.marryMainList.length; i++){
					    		 pricetotal += parseFloat(result.map.marryOrders.marryMainList[i].price);
					    	}
					    	$("#price").html("￥"+pricetotal);
					    }
					}
				}
		});
    });
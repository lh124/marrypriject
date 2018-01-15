$(function(){
         $.ajax({
				type: "POST",
				url: "../weixin/order/findOrder?id="+$("#id").val()+"&type=1",
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
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
function init(){
         $.ajax({
				type: "POST",
				url: "../weixin/findAllgetMoney",
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
						var totalFee = 0;
						if(result.map.obj != null){
							$("#totalFee").html("￥"+result.map.obj.totalFee+"元");
							$("#id").val(result.map.obj.id);
							totalFee = result.map.obj.totalFee;
						}
						$("#totalfeetotal").val(totalFee);
						var content = 0.00;
						if(result.map.blessing != null){
							for(var i=0; i <result.map.blessing.length; i++){
					              content += parseFloat(result.map.blessing[i].content);
					        }
						}
						$("#hongbaoFee").html("￥"+content+"元");
					}
				}
		});
}

function topay(){
	if($("#totalfeetotal").val() < 1){
		alert("账号余额小于1元，不能提现");
		return false;
	}
	$.ajax({
		type: "POST",
		url: "../weixin/get_money_tome?id="+$("#id").val(),
		dataType: "json",
		success: function(result){
			if(result.status == 'ok'){
				init();
			}
		}
	});
}
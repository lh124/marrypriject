function init(){
         $.ajax({
				type: "POST",
				url: "../weixin/findAllgetMoney",
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
						$('#bgmusic').prop('src',result.map.pic);
						if(result.map.obj != null){
							$("#totalFee").html("￥"+result.map.obj.totalFee+"元");
							$("#id").val(result.map.obj.id);
						}
					}
				}
		});
}

function topay(){
	$.ajax({
		type: "POST",
		url: "../weixin/get_money_tome?id="+$("#id").val(),
		dataType: "json",
		success: function(result){
			if(result.status == 'ok'){
				//init();
			}
		}
	});
}
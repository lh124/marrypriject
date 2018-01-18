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
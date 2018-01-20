function topay(){
	$("#iosDialog1").fadeIn(200);
}

function closedialog(){
	$("#iosDialog1").fadeOut(200);
}

function saveTotalFee(){
	var totalFee = $("#totalFee").val();
	if(totalFee == null || totalFee == ""){
		alert("金额不能为空");
		return false;
	}
	if(isNaN(totalFee)){
		alert("金额不正确");
		return false;
	}
	$.ajax({
		type: "POST",
	    url: "../weixin/blessing/pay?weddingId="+$("#weddingId").val()+"&totalFee="+totalFee,
		dataType: "json",
		success: function(result){
		     if(result.status == 'ok'){
		    	 $("#totalFee").val("");
		    	 var json = JSON.parse(result.data);
		    	 WeixinJSBridge.invoke('getBrandWCPayRequest',{
					      "appId":json.appId,     //公众号名称，由商户传入     
		           "timeStamp":json.timeStamp,         //时间戳，自1970年以来的秒数     
		           "nonceStr":json.nonceStr, //随机串     
		           "package":json.package,     
		           "signType":"MD5",         //微信签名方式：     
		           "paySign":json.paySign //微信签名
					},function(res){
							if(res.err_msg == "get_brand_wcpay_request:ok" ) {
								alert('支付成功');
						    }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
						        alert('支付取消');
						    }else if(res.err_msg == "get_brand_wcpay_request:fail" ){
						        alert('支付失败');
						    }
		            }
		        );
		     }
		}
	});
}
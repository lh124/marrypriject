
    function chooseWXPay() {
    	 var total = $("#total").val();
    	 var content = $("#content").val();
    	 var total_fee = $("#total_fee").val();
    	 if(parseFloat(total_fee)<1.00 || total_fee == null || total_fee == ""){
    		 alert("金额不能低于1元");
    		 return false;
    	 }
    	 if(parseInt(total)<1 || total == null ||total == ""){
    		 alert("红包个数不能少于1个");
    		 return false;
    	 }
         $.ajax({
				type: "POST",
				url: "../weixin/moneyMainSave?content="+content+"&total="+total+"&total_fee="+total_fee,
				dataType: "json",
				success: function(result){
				    var json = JSON.parse(result.data);
				    if(result.status == 'ok'){
				    	var id = json.id;
						WeixinJSBridge.invoke('getBrandWCPayRequest',{
						      "appId":json.appId,     //公众号名称，由商户传入     
			           "timeStamp":json.timeStamp,         //时间戳，自1970年以来的秒数     
			           "nonceStr":json.nonceStr, //随机串     
			           "package":json.package,     
			           "signType":"MD5",         //微信签名方式：     
			           "paySign":json.paySign //微信签名
						}, 
			                function(res){
							alert(JSON.stringify(res));
									if(res.err_msg == "get_brand_wcpay_request:ok" ) {
										alert('支付成功');
										var websocket = null;
										if ('WebSocket' in window) {
									         websocket = new WebSocket("ws://rctest.guanyukj.com/smart/websocket");
									     }
									     else {
									         alert('当前浏览器 Not support websocket')
									     }
										var obj = JSON.stringify({
								    		id:1
								    	});
										websocket.send(obj);
							            window.location.replace("hongbao.html?weddingId="+id);
							        }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
							            alert('支付取消');
							        }else if(res.err_msg == "get_brand_wcpay_request:fail" ){
							           //alert('支付失败');
							        }
			                }
			            );
				    }
				
				}
		 });
    }
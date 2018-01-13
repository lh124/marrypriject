$(function(){
         $.ajax({
				type: "POST",
				url: "../weixin/order/findOrder?id="+$("#orderId").val(),
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
					    var content = "";
					    if(result.map.type == 1){
					         $("#pricetotal").html("￥"+result.map.marryMain.price+"元");
					         content += '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
					                    '<div class="cart_tit"><p>'+result.map.marryOrders.business+'</p>'+
					                    '</div><div class="weui-panel__bd">'+
					                    '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
					                    '<div class="weui-media-box__hd">'+
					                    '<img style="display:block;width:60px;height:60px;" class="weui-media-box__thumb" src="'+result.map.marryMain.pic+'"></div>'+
					                    '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.map.marryMain.title+'</h4>'+
					                    '<p class="weui-media-box__desc">作者：'+result.map.marryMain.author+'</p>'+
					                    '<p class="weui-media-box__desc">价格：￥<span class="sub">'+result.map.marryMain.price+'</span></p>'+
					                    '<div class="weui-cells_checkbox"><label class="weui-check__label">'+
					                    '<div class="weui-cell__hd">'+
					                    '</div></label></div></div></a></div></div>';
					    }else if(result.map.type == 2){
					    	 var pricetotal = 0;
					    	 for(var i = 0; i < result.map.marryOrders.marryMainList.length; i++){
					    		 pricetotal += parseFloat(result.map.marryOrders.marryMainList[i].price);
					    		 content += '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
				                    '<div class="cart_tit"><p>'+result.map.marryOrders.marryMainList[i].username+'</p>'+
				                    '</div><div class="weui-panel__bd">'+
				                    '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
				                    '<div class="weui-media-box__hd">'+
				                    '<img style="display:block;width:60px;height:60px;" class="weui-media-box__thumb" src="'+result.map.marryOrders.marryMainList[i].pic+'"></div>'+
				                    '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.map.marryOrders.marryMainList[i].title+'</h4>'+
				                    '<p class="weui-media-box__desc">作者：'+result.map.marryOrders.marryMainList[i].author+'</p>'+
				                    '<p class="weui-media-box__desc">价格：￥<span class="sub">'+result.map.marryOrders.marryMainList[i].price+'</span></p>'+
				                    '<div class="weui-cells_checkbox"><label class="weui-check__label">'+
				                    '<div class="weui-cell__hd">'+
				                    '</div></label></div></div></a></div></div>';
					    	 }
					    	 $("#pricetotal").html("￥"+pricetotal+"元");
					    }
						$("#list").html(content);
					}
				}
		});
    });



wx.ready(function () {
    document.querySelector('#chooseWXPay').onclick = function () {
         $.ajax({
				type: "POST",
				url: "../weixin/pay?id="+$("#orderId").val(),
				dataType: "json",
				success: function(result){
				    var json = JSON.parse(result.data);
				    if(result.status == 'ok'){
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
			                }
			            );
				    }
				
				}
		 });
    }
 });
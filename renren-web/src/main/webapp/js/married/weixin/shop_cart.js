$(function(){
		  var w=document.documentElement?document.documentElement.clientHeight:document.body.clientHeight;
		  $(".body").css("height",w);
		  console.log(w)
          $.ajax({
				type: "POST",
				url: "../weixin/cart/list",
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
						var content = "";
						if(result.list.length > 0){
							for(var i = 0; i < result.list.length;i++){
							    content += '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
							               '<div class="cart_tit"><p>'+result.list[i].username+'<span class="check_part">全选</span></p>'+
							               '</div><div class="weui-panel__bd">'
							               for(var j = 0; j < result.list[i].list.length;j++){
							                     content += '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
											               '<div class="weui-media-box__hd">'+
											               '<img style="display:block;width:60px;height:60px;" class="weui-media-box__thumb" src="'+result.list[i].list[j].pic+'"></div>'+
											               '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.list[i].list[j].title+'</h4>'+
											               '<p class="weui-media-box__desc">'+result.list[i].list[j].author+'</p>'+
											               '<p class="weui-media-box__desc">价格：￥<span class="sub">'+result.list[i].list[j].price+'</span></p>'+
											               '<div class="weui-cells_checkbox"><label class="weui-check__label">'+
											               '<div class="weui-cell__hd"><input value="'+result.list[i].list[j].cartId+'" type="checkbox" class="weui-check ck" name="checkbox1">'+
											               '<i class="weui-icon-checked"></i></div></label></div></div></a>';
							                }
							               content += '</div></div>';
							}
							$(".cart_total").show();
							$("#list").html(content);
						}else{
							
						}
						native();
					}
				}
		});
});

function native(){
		/*商家全选*/
	$(".check_part").on("click",function(){
		if($(this).parents(".cart_con").find(".ck").prop("checked")== false){
			$(this).parents(".cart_con").find(".ck").prop("checked",true);
		}else{
			$(this).parents(".cart_con").find(".ck").prop("checked",false);
		}
		allCheck();
		calcTotal();
	});
	/*单选*/
	$(".ck").on("click",function(){
		allCheck();
		calcTotal();
	});
	/*全选*/
	$("#ck_all").click(function(){
		var status=$(this).prop("checked");
		$(".ck").prop("checked",status);
		calcTotal();
	});
	//全部选中
	function allCheck(){
		var len=$(".ck").length;
		var a=0;
		for(var i=0;i<len;i++){
			if($(".ck").eq(i).prop("checked")==true){
				a++;
			}
		}
		if(a==len){
			$("#ck_all").prop("checked", true);
		}else{
			$("#ck_all").prop("checked", false);
		}
	};
	del();
	/*删除*/
	function del(){
		$("#del").on("click",function(){
			var len=$(".ck").length;
			var len1 = $(".cart_con").length;
			var ids = [];
			for(var i=0;i<len;i++){
				if($(".ck").eq(i).prop("checked")){
					ids.push($(".ck").eq(i).val());
					$(".ck").eq(i).parents(".weui-media-box").addClass("delete");
				}
			}
			$(".delete").remove();
			for(var j=0;j<len1;j++){
				var a = $(".cart_con").eq(j).find(".weui-media-box").length;
				if(a){
					
				}else{
					$(".cart_con").eq(j).addClass("del");	
				}
			}
			$(".del").remove();
			calcTotal();
			allCheck();
			 $.ajax({
				type: "POST",
				url: "../weixin/cart/deletemarrycart?ids="+ids,
				dataType: "json",
				success: function(result){
					if(result.status == 'ok'){
					
					}
				}
			});
		});
	}
	// 合计
	function calcTotal() {
		var element = $(".ck:checked").parents(".weui-media-box__bd").find(".sub");
		var total = 0; // 总金额
		element.each(function(i, e){
			total += parseFloat($(e).text());
		});
		$(".total").text("￥"+total.toFixed(0));
	}  
	
	/*结算*/
	$(".cart_total_pay").on("click",function(){
		var len=$(".ck").length;
		var goodsId = [];
		var price = $(".total").text();
		for(var i=0;i<len;i++){
			if($(".ck").eq(i).prop("checked")){
				goodsId.push($(".ck").eq(i).val());
			}
		}
		if(goodsId.length){
		     $.ajax({
					type: "POST",
					url: "../weixin/order/saveOrder?mainId="+goodsId+"&type=2",
					dataType: "json",
					success: function(result){
						if(result.status == 'ok'){
							window.location.href="confirm_order1.html?orderId="+result.id;
						}
					}
			});
		}else{
			alert("亲，请先选择您需要购买的商品");
		}
	});

	}
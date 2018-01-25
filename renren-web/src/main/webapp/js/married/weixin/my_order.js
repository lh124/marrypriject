$(function(){
         getData();
    });
    
    function deleteOrders(id,obj){
    	$("#iosDialog1").show();
    	$(".quxiao").on("click",function(){
    		$("#iosDialog1").hide();
    	});
    	$(".sure").on("click",function(){
    		$.ajax({
    			type: "POST",
    			url: "../weixin/order/deleteOrder?id="+id,
    			dataType: "json",
    			success: function(result){
    				if(result.status == 'ok'){
    					$("#iosDialog1").hide();
    					$(obj).parents(".cart_con").remove();
    				}
    			}
        	});
    	});
    }

    function toPay(id){
        window.location.href="confirm_order1.html?orderId="+id;//将订单id传过去
    }
    
    function getData(){
    	$.ajax({
			type: "POST",
			url: "../weixin/order/findOrderlist",
			dataType: "json",
			success: function(result){
				if(result.status == 'ok'){
				    var totallist="";
				    var totalstates0="";
				    var totalstates1="";
				    for(var i = 0; i < result.list.length; i++){
				        if(result.list[i].orderType == 1){
				        	 if(result.list[i].states == '1'){
				        		 var list = '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
		                          '<div class="cart_tit" ><p>订单号：<span>'+result.list[i].orderNumber+'</span>'+
		                          '<span class="check_part" onclick="deleteOrders('+result.list[i].id+',this)">删除</span>'+
		                          '</p>'+'</div><div class="weui-panel__bd">'+
		                          '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.4rem;color:rgb(255, 67, 103);">'+
		                          result.list[i].business+'</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
		                          '<div class="weui-media-box__hd">'+
		                          '<img class="weui-media-box__thumb" src="'+result.list[i].marryMainList[0].pic+'" alt=""></div>'+
		                          '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.list[i].marryMainList[0].title+'</h4>'+
		                          '<p class="weui-media-box__desc">'+result.list[i].marryMainList[0].author+'</p>'+
		                          '<p class="weui-media-box__desc">价格：<span class="sub">'+result.list[i].marryMainList[0].price+'</span></p></div></a>'+
		                          '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">下单时间：<span style="font-size:0.4rem; ">'+result.list[i].gmtModifiedtime+'</span></div>'+
		                          '</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">共<span>1</span>件商品&nbsp;&nbsp;实付款：<span style="font-size:0.4rem; ">￥'+result.list[i].mainPrice+'元</span>'+
		                          '</div>'+
		                          ' </a></div></div>';
				        		 totalstates1 += list;
				        		 totallist += list;
				        	 }else if(result.list[i].states == '0'){
				        		 var list = '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
		                          '<div class="cart_tit" ><p>订单号：<span>'+result.list[i].orderNumber+'</span>'+
		                          '</p>'+'</div><div class="weui-panel__bd">'+
		                          '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.4rem;color:rgb(255, 67, 103);">'+
		                          result.list[i].business+'</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
		                          '<div class="weui-media-box__hd">'+
		                          '<img class="weui-media-box__thumb" src="'+result.list[i].marryMainList[0].pic+'" alt=""></div>'+
		                          '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.list[i].marryMainList[0].title+'</h4>'+
		                          '<p class="weui-media-box__desc">'+result.list[i].marryMainList[0].author+'</p>'+
		                          '<p class="weui-media-box__desc">价格：<span class="sub">'+result.list[i].marryMainList[0].price+'</span></p></div></a>'+
		                          '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">下单时间：<span style="font-size:0.4rem; ">'+result.list[i].gmtModifiedtime+'</span></div>'+
		                          '</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">共<span>1</span>件商品&nbsp;&nbsp;实付款：<span style="font-size:0.4rem; ">￥'+result.list[i].mainPrice+'元</span>'+
		                          '<button id="del" onclick="toPay('+result.list[i].id+')" style="margin-left:0.2rem;background:#fe4567;" class="weui-btn weui-btn_mini weui-btn_warn" type="button">去付款</button>'+
		                          '</div>'+
		                          ' </a></div></div>';
				        		 totalstates0 += list;
				        		 totallist += list;
				        	 }
				        }else if(result.list[i].orderType == 2){
				              var priceTotal = 0;
				              if(result.list[i].states == '1'){
				            	  var list =  '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
		                          '<div class="cart_tit" ><p>订单号：<span>'+result.list[i].orderNumber+'</span>'+
		                          '<span class="check_part" onclick="deleteOrders('+result.list[i].id+',this)">删除</span>'+
		                          '</p>'+'</div><div class="weui-panel__bd">';
		                          for(var j=0;j<result.list[i].marryMainList.length; j++){
		                             priceTotal += parseFloat(result.list[i].marryMainList[j].price);
		                             list +=  '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.4rem;color:rgb(255, 67, 103);">'+
			                          result.list[i].marryMainList[j].username+'</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
			                          '<div class="weui-media-box__hd">'+
			                          '<img class="weui-media-box__thumb" src="'+result.list[i].marryMainList[j].pic+'" alt=""></div>'+
			                          '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.list[i].marryMainList[j].title+'</h4>'+
			                          '<p class="weui-media-box__desc">'+result.list[i].marryMainList[j].author+'</p>'+
			                          '<p class="weui-media-box__desc">价格：<span class="sub">'+result.list[i].marryMainList[j].price+'</span></p></div></a>';
		                          }
		                          
		                          list += '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">下单时间：<span style="font-size:0.4rem; ">'+result.list[i].gmtModifiedtime+'</span></div>'+
		                          '</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">共<span>'+result.list[i].marryMainList.length+'</span>件商品&nbsp;&nbsp;实付款：<span style="font-size:0.4rem; ">￥'+priceTotal+'元</span>'+
		                          '</div></a></div></div>';
		                          totalstates1 += list;
					        	  totallist += list;
				              }else if(result.list[i].states == '0'){
				            	  var list = '<div class="cart_con weui-panel weui-panel_access" style="margin-top:10px;">'+
		                          '<div class="cart_tit" ><p>订单号：<span>'+result.list[i].orderNumber+'</span>'+
		                          '</p>'+'</div><div class="weui-panel__bd">';
		                          for(var j=0;j<result.list[i].marryMainList.length; j++){
		                             priceTotal += parseFloat(result.list[i].marryMainList[j].price);
		                             list +=  '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.4rem;color:rgb(255, 67, 103);">'+
			                          result.list[i].marryMainList[j].username+'</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'+
			                          '<div class="weui-media-box__hd">'+
			                          '<img class="weui-media-box__thumb" src="'+result.list[i].marryMainList[j].pic+'" alt=""></div>'+
			                          '<div class="weui-media-box__bd"><h4 class="weui-media-box__title">'+result.list[i].marryMainList[j].title+'</h4>'+
			                          '<p class="weui-media-box__desc">'+result.list[i].marryMainList[j].author+'</p>'+
			                          '<p class="weui-media-box__desc">价格：<span class="sub">'+result.list[i].marryMainList[j].price+'</span></p></div></a>';
		                          }
		                          
		                          list += '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">下单时间：<span style="font-size:0.4rem; ">'+result.list[i].gmtModifiedtime+'</span></div>'+
		                          '</a><a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" style="font-size:0.35rem;">'+
		                          '<div style="width:100%;text-align:right;">共<span>'+result.list[i].marryMainList.length+'</span>件商品&nbsp;&nbsp;实付款：<span style="font-size:0.4rem; ">￥'+priceTotal+'元</span>'+
		                           '<button id="del" onclick="toPay('+result.list[i].id+')" style="margin-left:0.2rem;background:#fe4567;" class="weui-btn weui-btn_mini weui-btn_warn" type="button">去付款</button>'+
		                          '</div>'+
		                          ' </a></div></div>';
		                          totalstates0 += list;
					        	  totallist += list;
				              }
				              
				        }
				        $("#totallist").html(totallist);
				        $("#totalstates0").html(totalstates0);
				        $("#totalstates1").html(totalstates1);
				    }
				}
			}
    	});
    }
$(document).ready(function() {
				var win = (parseInt($(".couten").css("width"))) - 60;
				$(".mo").css("height", $(document).height());
				$(".couten").css("height", $(document).height());
				$(".backward").css("height", $(document).height());
				$("li").css({});
				// 点击确认的时候关闭模态层
				$(".sen a").click(function(){
				  $(".mo").css("display", "none")
				});
				
				var del = function(){
					nums++;
					$(".li" + nums).remove();
					setTimeout(del,200)
				}
				
				var add = function() {
					var length = 0;
					var goodsId = [];
					$.ajax({
						type: "POST",
					    url: "../weixin/findAllredmoneydetail?weddingId="+$("#weddingId").val(),
						dataType: "json",
						success: function(result){
						     if(result.status == 'ok'){
						    	 length = result.list.length;
						    	 for(var i = 0; i < length; i++){
						    		 goodsId.push(result.list[i].id);
						    	 }
						    	 if(length != 0){
										redMoney(goodsId[num]);
										num++;
										if(length == num || num > length){
											num = 0;
										}
										setTimeout(add,1000);
									}
						     }
						}
					});
				}	
				
				
				function redMoney(num){
					var hb = parseInt(Math.random() * (3 - 1) + 1);
					var Wh = parseInt(Math.random() * (70 - 30) + 20);
					var Left = parseInt(Math.random() * (win - 0) + 0);
					var rot = (parseInt(Math.random() * (45 - (-45)) - 45)) + "deg";
					$(".couten").append("<li class='li" + num + "' ><a href='javascript:;'><img src='/wrs/statics/marry/img/hb_" + hb + ".png'></a></li>");
					$(".li" + num).css({
						"left": Left,
					});
					$(".li" + num + " a img").css({
						"width": Wh,
						"transform": "rotate(" + rot + ")",
						"-webkit-transform": "rotate(" + rot + ")",
						"-ms-transform": "rotate(" + rot + ")", /* Internet Explorer */
						"-moz-transform": "rotate(" + rot + ")", /* Firefox */
						"-webkit-transform": "rotate(" + rot + ")",/* Safari 和 Chrome */
						"-o-transform": "rotate(" + rot + ")" /* Opera */
					});	
					$(".li" + num).animate({'top':$(window).height()+20},5000,function(){
						//删掉已经显示的红包
						this.remove()
					});
					//点击红包的时候弹出模态层
					$(".li" + num).click(function(){
						$.ajax({
							type: "POST",
						    url: "../weixin/findredmoneydetail?id="+num,
							dataType: "json",
							success: function(result){
							     if(result.status == 'ok'){
							    	 $("#totalFee").html("获得红包"+result.totalFee+"元");
							    	 $(".mo").css("display", "block")
							     }else{
							    	 alert(result.msg);
							     }
							}
						});
					});
				}
				
				//增加红包
				var num = 0;
				setTimeout(add,3000);
				
				//倒数计时
				var backward = function(){
					numz--;
					if(numz>0){
						$(".backward span").html(numz);
					}else{
						$(".backward").remove();
					}
					setTimeout(backward,1000)
							
				}
				
				var numz = 4;
				backward();
			
			})
	function init(){
          $.ajax({
				type: "POST",
			    url: "../weixin/me/blessManage",
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				         var content1 = "";
				         var content2 = "";
				         var content3 = "";
				         for(var i = 0 ; i < result.list.list1.length; i++){
				        	  var pic = "";
			            	  if(result.list.list1[i].pic == null || result.list.list1[i].pic == ""){
			            	      pic = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/1.png";
			            	  }else{
			            		  pic = result.list.list1[i].pic;
			            	  }
				              if(result.list.list1[i].content != null && result.list.list1[i].content != ""){
				              		content1 += '<div class="friend_bless"><dl class="friend_bless_top">'+
				                         '<dt><img src="'+pic+'" /></dt>'+
				                         '<dd>'+result.list.list1[i].nickname+'</dd>'+
				                         '</dl>'+
				                         '<div class="friend_bless_con"><p>'+result.list.list1[i].content+'</p>'+
				                         '<p style="text-align:right;">'+result.list.list1[i].gmtModifiedtime+'</p></div></div>';
				               }
				              if(result.list.list1[i].videoblessing != null && result.list.list1[i].videoblessing != ""){
				                   content2 += '<div class="friend_bless"><dl class="friend_bless_top">'+
				                               '<dt><img src="'+pic+'" /></dt>'+
				                               '<dd>'+result.list.list1[i].nickname+'</dd>'+
				                               '<div class="weui-cells_checkbox"><label class="weui-check__label">'+
				                               '<div class="weui-cell__hd"><input type="checkbox" class="weui-check ck" name="checkbox1">'+
				                               '<i class="weui-icon-checked"></i></div></label></div></dl>'+
				                               '<div class="friend_bless_con"><dl class="video">'+
				                               '<dt><img class="videoSrc" title="'+result.list.list1[i].videoblessing+'" src="'+pic+'"></dt>'+
				                               '<dd><p style="text-align:right;">'+result.list.list1[i].gmtModifiedtime+'</p></dd></dl></div></div>';
				              }
				         }
				         $("#list1").html(content1);
				         $("#list2").html(content2);
				         var total = 0;
				         for(var i = 0; i < result.list.list2.length; i++){
				              total += parseFloat(result.list.list2[i].content);
				              content3 += '<div class="person_packet"><dl>'+
				                          '<dt><img src="'+result.list.list2[i].pic+'"></dt>'+
				                          '<dd class="weui-flex"><div class="weui-flex__item">'+
				                          '<p>'+result.list.list2[i].nickname+'</p><p>2017.12.25</p>'+
				                          '</div><div class="weui-flex__item person_packet_right">'+
				                          result.list.list2[i].content+'元</div></dd></dl></div>';
				         }
				         $("#list3").append(content3);
				         $("#money").html("￥"+total+"元");
				         $("#total").html(result.list.list2.length);
				     }
				}
		 });
     }
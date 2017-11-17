function init(){
	$.ajax({ 
		type: "POST",
	    url: "../../publicModule/common/getWeChatSign",
	    data: {"url":window.location.href},
	    dataType:'json',
	    success: function (result) {
				wx.config({
					debug: false, //
					appId: 'wxb9072ff1ebcf745c', // 必填，公众号的唯一标识
					timestamp: result.timestamp, // 必填，生成签名的时间戳
					nonceStr: result.nonceStr, // 必填，生成签名的随机串
					signature: result.signature,// 必填，签名，见附录1
			        jsApiList: [
			           'startRecord',
			           'stopRecord',
			           'uploadVoice'
			        ]
			    });
		} 
	});
	var sss = $('.dropload-up');
	if(sss.length>1){
		  for(var i = 1; i < sss.length; i++){
			  sss[i].remove();
		  }
	}
	$('.weui_panel_bd').html(""); 
	//页数 
	    var page = 0;
	    // 每页展示10个
	    var size =10;
	    $('.weui_panel').dropload({
	        scrollArea : window,
	        autoLoad : true,//自动加载
	  domDown : {//上拉
	            domClass   : 'dropload-down',
	            domRefresh : '<div class="dropload-refresh f15 "><i class="icon icon-20"></i>上拉加载更多</div>',
	            domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>',
	            domNoData  : '<div class="dropload-noData">没有更多数据了</div>'
	        },
	        domUp : {//下拉
	            domClass   : 'dropload-up',
	            domRefresh : '<div class="dropload-refresh"><i class="icon icon-114"></i>上拉加载更多</div>',
	            domUpdate  : '<div class="dropload-load f15"><i class="icon icon-20"></i>释放更新...</div>',
	            domLoad    : '<div class="dropload-load f15"><span class="weui-loading"></span>正在加载中...</div>'
	        },
	        loadUpFn : function(me){//刷新
	            var result = '';
	            $.ajax({
	                type: 'GET',
	                url:'../photoclassworkmsg/list?order=desc&page=' + 1 + '&picType=1&limit=10&sidx=&classId='+ $('#classId').val(),
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.list.length;
	           			var result = "";
	           			
	                    if(arrLen > 0){
	                        for(var i = 0; i < arrLen; i++){
		                        //var name = GetLength(data.page.list[i].contents) > 20 ? cutstr(data.page.list[i].contents,20) : data.page.list[i].contents;
		           				var img = (data.page.list[i].student.pic == null || data.page.list[i].student.pic == '') ? data.url + 'static-sources/head.jpg' :  data.page.list[i].student.pic;
		   						
		           				var imgs = "";
		           				var content = "";
		           				for(var j=0; j < data.page.list[i].picList.length; j++){
		           					imgs += '<div class="thumbnail">'+
	           		                '<img data-img="http://static.gykjewm.com/'+ data.page.list[i].picList[j].name +'" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=" />'+
		           		              '</div>';
		           				}
		           				if((data.page.list[i].content == null || data.page.list[i].content == "") && data.page.list[i].voice != null && data.page.list[i].voice != ""){
		           					content = '<audio src="'+data.page.list[i].voice+'" controls="controls"></audio>';
		           				}else{
		           					content = '<p id="paragraph" class="paragraph">' + data.page.list[i].content +
		           		            '</p>';
		           				}
		           				result += '<div class="weui_cells moments">'+
		           		        '<div class="weui_cell moments__post">'+
		           		         '<div class="weui_cell_hd">'+
		           		            '<img class="circle" data-img="'  + img + '" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=" />'+
		           		          '</div>'+
		           		          '<div class="weui_cell_bd" style="width:100%;">'+
		           		            '<a class="title" href="javascript:;">'+
		           		              '<span>' + data.page.list[i].student.studentName + '</span>'+
		           		            '</a>'+content +
		           		            /*'<a id="paragraphExtender" class="paragraphExtender">显示全文</a>'+*/
		           		           '<div class="thumbnails">'+
		           		           	imgs+
		           		            '</div>'+
		           		          '</div>'+
		           		        '</div>'+
		           		      '</div>';
		                    }
		                    page = 1;
	                    }else{
	                    	// 如果没有数据
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
						
	                    // 为了测试，延迟1秒加载
	                    $('.weui_panel_bd').html(result);  
	                        var lazyloadImg = new LazyloadImg({
					            el: '.weui_panel_bd [data-img]', //匹配元素
					            top: 50, //元素在顶部伸出长度触发加载机制
					            right: 50, //元素在右边伸出长度触发加载机制
					            bottom: 50, //元素在底部伸出长度触发加载机制
					            left: 50, //元素在左边伸出长度触发加载机制
					            qriginal: false, // true，自动将图片剪切成默认图片的宽高；false显示图片真实宽高
					            load: function(el) {
					                el.style.cssText += '-webkit-animation: fadeIn 01s ease 0.2s 1 both;animation: fadeIn 1s ease 0.2s 1 both;';
					            },
					            error: function(el) {
					
					            }
	        				});
	        				// 每次数据加载完，必须重置
	                        me.resetload();
	                        //afterDo();
	                        page = 1;
	                },
	                error: function(xhr, type){
	                    alert('Ajax error!');
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                    //afterDo();
	                }
	            });
	        },
	        loadDownFn : function(me){//加载更多
	         page++;
	         window.history.pushState(null, document.title, window.location.href);
	         var result = '';
	            $.ajax({
	                type: 'GET',
	                url:'../photoclassworkmsg/list?order=desc&page=' + page + '&picType=1&limit=10&sidx=&classId='+ $('#classId').val(),
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.list.length;
	           			
	                    if(arrLen > 0){
	                        for(var i = 0; i < arrLen; i++){
	                        	var img = (data.page.list[i].student.pic == null || data.page.list[i].student.pic == '') ? data.url + 'static-sources/head.jpg' :  data.page.list[i].student.pic;
		   						
	                        	var imgs = "";
	                        	var content = "";
		           				for(var j=0; j < data.page.list[i].picList.length; j++){
		           					imgs += '<div class="thumbnail">'+
	           		                '<img data-img="http://static.gykjewm.com/'+ data.page.list[i].picList[j].name +'" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII="/>'+
		           		              '</div>';
		           				}
		           				if((data.page.list[i].content == null || data.page.list[i].content == "") && data.page.list[i].voice != null && data.page.list[i].voice != ""){
		           					content = '<audio src="'+data.page.list[i].voice+'" controls="controls"></audio>';
		           				}else{
		           					content = '<p id="paragraph" class="paragraph">' + data.page.list[i].content +
		           		            '</p>';
		           				}
		           				result += '<div class="weui_cells moments">'+
		           		        '<div class="weui_cell moments__post">'+
		           		         '<div class="weui_cell_hd" >'+
		           		            '<img class="circle" data-img="'  + img + '" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=" />'+
		           		          '</div>'+
		           		          '<div class="weui_cell_bd" style="width:100%;">'+
		           		            '<a class="title" href="javascript:;">'+
		           		              '<span>' + data.page.list[i].student.studentName + '</span>'+
		           		            '</a>'+content +
		           		            /*'<a id="paragraphExtender" class="paragraphExtender">显示全文</a>'+*/
			           		         '<div class="thumbnails">'+
			           		         imgs+
		           		            '</div>'+
		           		          '</div>'+
		           		        '</div>'+
		           		      '</div>';
		                    }
	                    }else{
	                    	// 如果没有数据
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
	
	                    // 为了测试，延迟1秒加载
	                    $('.weui_panel_bd').append(result);  
	                        var lazyloadImg = new LazyloadImg({
					            el: '.weui_panel_bd [data-img]', //匹配元素
					            top: 50, //元素在顶部伸出长度触发加载机制
					            right: 50, //元素在右边伸出长度触发加载机制
					            bottom: 50, //元素在底部伸出长度触发加载机制
					            left: 50, //元素在左边伸出长度触发加载机制
					            qriginal: false, // true，自动将图片剪切成默认图片的宽高；false显示图片真实宽高
					            load: function(el) {
					                el.style.cssText += '-webkit-animation: fadeIn 01s ease 0.2s 1 both;animation: fadeIn 1s ease 0.2s 1 both;';
					            },
					            error: function(el) {
					
					            }
	        				});
	        				// 每次数据加载完，必须重置
	                        me.resetload();
	                        //afterDo();
	                },
	                error: function(xhr, type){
	                    alert('Ajax error!');
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                }
	            });
	        }
	    });
	    var ss = $('.dropload-down');
		   if(ss.length>1){
			  $('.footers').html(ss[0]);
			  for(var i = 1; i < ss.length; i++){
				  ss[i].remove();
			  }
		   }
	}
	
	var GetLength = function (str) {
	    ///<summary>获得字符串实际长度，中文2，英文1</summary>
	    ///<param name="str">要获得长度的字符串</param>
	    var realLength = 0, len = str.length, charCode = -1;
	    for (var i = 0; i < len; i++) {
	      charCode = str.charCodeAt(i);
	      if (charCode >= 0 && charCode <= 128) realLength += 1;
	      else realLength += 2;
	    }
	    return realLength;
	  };
	 
	  //js截取字符串，中英文都能用 
	  //如果给定的字符串大于指定长度，截取指定长度返回，否者返回源字符串。 
	  //字符串，长度 
	 
	  /** 
	   * js截取字符串，中英文都能用 
	   * @param str：需要截取的字符串 
	   * @param len: 需要截取的长度 
	   */
	  function cutstr(str, len) {
	    var str_length = 0;
	    var str_len = 0;
	    str_cut = new String();
	    str_len = str.length;
	    for (var i = 0; i < str_len; i++) {
	      a = str.charAt(i);
	      str_length++;
	      if (escape(a).length > 4) {
	        //中文字符的长度经编码之后大于4 
	        str_length++;
	      }
	      str_cut = str_cut.concat(a);
	      if (str_length >= len) {
	        str_cut = str_cut.concat("...");
	        return str_cut;
	      }
	    }
	    //如果给定字符串小于指定长度，则返回源字符串； 
	    if (str_length < len) {
	      return str;
	    }
	  }
function afterDo(){
		//定义文本
		const paragraph = $('#paragraph');
		const paragraphText = paragraph.text();
		const paragraphLength = paragraph.text().length;
		//定义文章长度
		const maxParagraphLength = 80;
		//定义全文按钮
		const paragraphExtender = $('#paragraphExtender');
		var toggleFullParagraph = false;
		
		//定义全文按钮
		if (paragraphLength < maxParagraphLength) {
		  paragraphExtender.hide();
		} else {
		  paragraph.html(paragraphText.substring(0, maxParagraphLength) + '...');
		  paragraphExtender.click(function(){
		    if (toggleFullParagraph) {
		      toggleFullParagraph = false;
		      paragraphExtender.html('显示全文');
		      paragraph.html(paragraphText.substring(0, maxParagraphLength) + '...');
		    } else {
		      toggleFullParagraph = true;
		      paragraphExtender.html('收起');
		      paragraph.html(paragraphText);
		    }
		  });
		};
		const menu = $('#actionMenu');
		const menuBtn = $('#actionToggle');
		menuBtn.click(function(){menu.toggleClass('active')});
	}
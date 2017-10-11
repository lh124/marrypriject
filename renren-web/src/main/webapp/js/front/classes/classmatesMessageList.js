$(function(){
	var frontUser = $("#frontUser").val();
	if(frontUser ){
		//window.location.href="../login.html";
		var $iosDialog1 = $('#iosDialog1');
		$iosDialog1.fadeIn(200);
	}
	
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
	                url:'../photoclassmates/list?order=desc&page=' + 1 + '&limit=10&sidx=&classId='+ $('#classId').val(),
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.list.length;
	           			var result = "";
	           			
	                    if(arrLen > 0){
	                        for(var i = 0; i < arrLen; i++){
		                        var name = GetLength(data.page.list[i].contents) > 20 ? cutstr(data.page.list[i].contents,20) : data.page.list[i].contents;
		           				var img = (data.page.list[i].headImg == null || data.page.list[i].headImg == '') ? data.url + 'static-sources/head.jpg' :  data.url + data.page.list[i].headImg;
		   						result+='  <a href="classMateMessage.html?id=' + data.page.list[i].id + '" class="weui_media_box weui_media_appmsg">'
			                    +'<div class="weui_media_hd weui-updown">'
			                        +'<img class="weui_media_appmsg_thumb lazyload" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFACAYAAADNkKWqAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAwNi8wNS8xNrqrthwAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzbovLKMAAAEMElEQVR4nO3UQQEAEADAQPRvqIESxPDYXYK9Nvc+dwAErd8BAL8YIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBA1gPYJgYfB4WzDQAAAABJRU5ErkJggg==" alt="" data-img="'+
			                        img + '">'
			                    +'</div>'
			                    +'<div class="weui_media_bd">'
			                        +'<h4 class="weui_media_title">'+data.page.list[i].nickname+'</h4>'
			                        +'<p class="weui_media_desc">'+
			                        name +'</p>'
			                    +'</div>'
			                	+'</a>';
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
					            el: '.weui-updown [data-img]', //匹配元素
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
	                        page = 1;
	                },
	                error: function(xhr, type){
	                    alert('Ajax error!');
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                }
	            });
	        },
	        loadDownFn : function(me){//加载更多
	         page++;
	         window.history.pushState(null, document.title, window.location.href);
	         var result = '';
	            $.ajax({
	                type: 'GET',
	                url:'../photoclassmates/list?order=desc&page=' + page + '&limit=10&sidx=&classId='+ $('#classId').val(),
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.list.length;
	           			
	                    if(arrLen > 0){
	                        for(var i = 0; i < arrLen; i++){
	                        	var name = GetLength(data.page.list[i].contents) > 20 ? cutstr(data.page.list[i].contents,20) : data.page.list[i].contents;
	           					var img = (data.page.list[i].headImg == null || data.page.list[i].headImg == '') ? data.url + 'static-sources/head.jpg' :  data.url + data.page.list[i].headImg;
		   						result+='  <a href="classMateMessage.html?id=' + data.page.list[i].id + '" class="weui_media_box weui_media_appmsg">'
			                    +'<div class="weui_media_hd weui-updown">'
			                        +'<img class="weui_media_appmsg_thumb lazyload" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFACAYAAADNkKWqAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAwNi8wNS8xNrqrthwAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzbovLKMAAAEMElEQVR4nO3UQQEAEADAQPRvqIESxPDYXYK9Nvc+dwAErd8BAL8YIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBA1gPYJgYfB4WzDQAAAABJRU5ErkJggg==" alt="" data-img="'+
			                         img + '">'
			                    +'</div>'
			                    +'<div class="weui_media_bd">'
			                        +'<h4 class="weui_media_title">'+data.page.list[i].nickname+'</h4>'
			                        +'<p class="weui_media_desc">'+name
			                         +'</p>'
			                    +'</div>'
			                	+'</a>';
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
					            el: '.weui-updown [data-img]', //匹配元素
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
	                },
	                error: function(xhr, type){
	                    alert('Ajax error!');
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                }
	            });
	        }
	    });
	});
	
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
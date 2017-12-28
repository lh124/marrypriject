$(function(){
	//页数 
	    var page = 0;
	    // 每页展示10个
	    var size =10;
	    $('.weui_panel').dropload({
	        scrollArea : window,
	        autoLoad : true,//自动加载
	  domDown : {//上拉
	            domClass   : 'dropload-down',
	            domRefresh : '<div class="dropload-refresh f15 " style="text-align:center;"><i class="icon icon-20"></i>上拉加载更多</div>',
	            domLoad    : '<div class="dropload-load f15" style="text-align:center;"><span class="weui-loading"></span>正在加载中...</div>',
	            domNoData  : '<div class="dropload-noData" style="text-align:center;">没有更多数据了</div>'
	        },
	        domUp : {//下拉
	            domClass   : 'dropload-up',
	            domRefresh : '<div class="dropload-refresh" style="text-align:center;"><i class="icon icon-114"></i>上拉加载更多</div>',
	            domUpdate  : '<div class="dropload-load f15" style="text-align:center;"><i class="icon icon-20"></i>释放更新...</div>',
	            domLoad    : '<div class="dropload-load f15" style="text-align:center;"><span class="weui-loading"></span>正在加载中...</div>'
	        },
	        loadUpFn : function(me){//刷新
	        	page=1;
	            var result = '';
	            $.ajax({
	                type: 'post',
	                url:serverURL+'me/findimg?limit=10&order=&sdix=&page='+page+'&imgType=1',
	                dataType: 'json',
	                success: function(data){
	                	var data = data.data;
	           			var arrLen = data.length;
	           			var result = "";
	                    if(arrLen > 0){
	                        for(var i = 0; i < arrLen; i++){
		           				result += '<div class="placeholder"><img src="'+data[i].imgUrl+'" /><input type="radio" name="bg"></div>';
		                    }
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
	                url: serverURL+'me/findimg?limit=10&order=&sdix=&page='+page+'&imgType=1',
	                dataType: 'json',
	                success: function(data){
	                	var data = data.data;
	           			var arrLen = data.length;
	                    if(arrLen > 0){
	                        for(var i = 0; i < arrLen; i++){
	                        	result += '<div class="placeholder"><img src="'+data[i].imgUrl+'" /><input type="radio" name="bg"></div>';
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
	});

$(function(){
	//页数 
	    var page = 0;
	    // 每页展示10个
	    var limit =10;
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
	            $.ajax({
	                type: 'GET',
	                url: '../photopicuser/list',
	                data: 'page=' + 1 + "&limit=" + limit + "&order=desc&sidx=&id=" + $('#id').val() ,
	                dataType: 'json',
	                success: function(data){
	                    var result = '';
	                    var arrLen = data.page.list.length;
	                    if(arrLen > 0){
	                        for(var i=0; i<arrLen; i++){
	                        	result += '<img picId="' + data.page.list[i].id + '" style="margin:6%;width:85%;height:auto;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAArIAAAGFBAMAAADzwA07AAAAIVBMVEXr6+vPz8/X19fp6ene3t7k5OTm5ubh4eHT09Pb29vR0dHqLrSfAAACyklEQVR42uzBgQAAAACAoP2pF6kCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGB27GdHaSiO4vgJ5e+s5lzAFlZ2Y1yWoIm6AjPJbItG3VJdmLiiOg9Aow8AybinK1/Tey9ldDttl+eTFC6w+4b+2luRNi2XcIJl8v/H6quHhf9BHiXnFlbGE6wep/D8+gm8Dp8CnEAeJePOBz4n7XKssi254gFWzEkVUWVb0uEeVklz7rxS2ZYM/PnfZ8zEz4atyrYkYOjn63emAPI5VLYtxczHe+HnbWxc7K9fVLYFxwjAIur5eVuGQLcgn6lsczkTYDNDOQb6Nma/oJWqbGMLV/EYIg6BAffIaG5iTlW2sSFXQDFGblzDAwqTIIi5VdmmRrZbYI/F3FXeDbj3uQ8q29SA1/ZYYcgtFkzeMAXQ50llmwo4RYcpRlwhj5DN4RShyjZWTHA1Bwa233Fm435w4onKNnY0yAwQ8BrFFGuezVS2sc0c6xBAPA04Rswz8+9Z7VBl61lwG48BrCd+IEQ33i36DB8eNKpsHUP+KPcAMuMvYgYXnFzS71S2jhF/8+D/mq+YIotwURh4GVOVraPHXy4dOsznfrNb3XUh9kv3nqhsHQEjlw5dFsa97qudAnK/RI8GKltLTANfkyEQlFECfOMBGDFKAaw5Vtl61lW1wifMaW7fMkpcZJr7u+fkTmXr2VRbgqM/+3slrZ+wPtP7A5WtZ8FTVfgA63VhYyZw3tGaJSrbkuDTe1Re3t1/hIiIiIiIiIiIiIj8ZQ8OBAAAAACA/F8bQVVVVVVVVVVVVVVVVVVVhT04EAAAAAAA8n9tBFVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVpT04JAAAAAAQ9P+10RMAAAAAAAAAAAAAAADAArTtXKLPR7LcAAAAAElFTkSuQmCC" '+
		    					  'data-img="' + data.url + data.page.list[i].name + '" origile="'  + data.url + data.page.list[i].name + 
		    					  '"   class="img-border img-max img-radius">';
							}
	                    }else{
	                    	// 如果没有数据
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
	
	                    // 为了测试，延迟1秒加载
	                    $('.weui_grids').html(result);  
                        var lazyloadImg = new LazyloadImg({
				            el: '.weui_grids [data-img]', //匹配元素
				            top: 50, //元素在顶部伸出长度触发加载机制
				            right: 50, //元素在右边伸出长度触发加载机制
				            bottom: 50, //元素在底部伸出长度触发加载机制
				            left: 50, //元素在左边伸出长度触发加载机制
				            qriginal: false, // true，自动将图片剪切成默认图片的宽高；false显示图片真实宽高
				            load: function(el) {
				                el.style.cssText += '-webkit-animation: fadeIn 01s ease 0.2s 1 both;animation: fadeIn 1s ease 0.2s 1 both;margin:6%;width:85%;height:auto;';
				            },
				            error: function(el) {
				
				            }
        				});
                        // 微信图片窗口
                        addLoadEvent(getPicInfo);
        				// 每次数据加载完，必须重置
                        me.resetload();
	                    
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
	                url:'../photopicuser/list',
	                data: 'page=' + page + "&limit=" + limit + "&order=desc&sidx=&id=" + $('#id').val() ,
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.list.length;
	                    if(arrLen > 0){
	                        for(var i=0; i<arrLen; i++){
	                        	result += '<img style="margin:6%;width:85%;height:auto;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAArIAAAGFBAMAAADzwA07AAAAIVBMVEXr6+vPz8/X19fp6ene3t7k5OTm5ubh4eHT09Pb29vR0dHqLrSfAAACyklEQVR42uzBgQAAAACAoP2pF6kCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGB27GdHaSiO4vgJ5e+s5lzAFlZ2Y1yWoIm6AjPJbItG3VJdmLiiOg9Aow8AybinK1/Tey9ldDttl+eTFC6w+4b+2luRNi2XcIJl8v/H6quHhf9BHiXnFlbGE6wep/D8+gm8Dp8CnEAeJePOBz4n7XKssi254gFWzEkVUWVb0uEeVklz7rxS2ZYM/PnfZ8zEz4atyrYkYOjn63emAPI5VLYtxczHe+HnbWxc7K9fVLYFxwjAIur5eVuGQLcgn6lsczkTYDNDOQb6Nma/oJWqbGMLV/EYIg6BAffIaG5iTlW2sSFXQDFGblzDAwqTIIi5VdmmRrZbYI/F3FXeDbj3uQ8q29SA1/ZYYcgtFkzeMAXQ50llmwo4RYcpRlwhj5DN4RShyjZWTHA1Bwa233Fm435w4onKNnY0yAwQ8BrFFGuezVS2sc0c6xBAPA04Rswz8+9Z7VBl61lwG48BrCd+IEQ33i36DB8eNKpsHUP+KPcAMuMvYgYXnFzS71S2jhF/8+D/mq+YIotwURh4GVOVraPHXy4dOsznfrNb3XUh9kv3nqhsHQEjlw5dFsa97qudAnK/RI8GKltLTANfkyEQlFECfOMBGDFKAaw5Vtl61lW1wifMaW7fMkpcZJr7u+fkTmXr2VRbgqM/+3slrZ+wPtP7A5WtZ8FTVfgA63VhYyZw3tGaJSrbkuDTe1Re3t1/hIiIiIiIiIiIiIj8ZQ8OBAAAAACA/F8bQVVVVVVVVVVVVVVVVVVVhT04EAAAAAAA8n9tBFVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVpT04JAAAAAAQ9P+10RMAAAAAAAAAAAAAAADAArTtXKLPR7LcAAAAAElFTkSuQmCC" '+
		    					  'data-img="' + data.url + data.page.list[i].name + '" origile="'  + data.url + data.page.list[i].name + 
		    					  '"   class="img-border img-max img-radius">';
							}
	                    }else{
	                    	// 如果没有数据
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
	
	                    // 为了测试，延迟1秒加载
	                    $('.weui_grids').append(result);  
                        var lazyloadImg = new LazyloadImg({
				            el: '.weui_grids [data-img]', //匹配元素
				            top: 50, //元素在顶部伸出长度触发加载机制
				            right: 50, //元素在右边伸出长度触发加载机制
				            bottom: 50, //元素在底部伸出长度触发加载机制
				            left: 50, //元素在左边伸出长度触发加载机制
				            qriginal: false, // true，自动将图片剪切成默认图片的宽高；false显示图片真实宽高
				            load: function(el) {
				                el.style.cssText += '-webkit-animation: fadeIn 01s ease 0.2s 1 both;animation: fadeIn 1s ease 0.2s 1 both;margin:6%;width:85%;height:auto;';
				            },
				            error: function(el) {
				
				            }
        				});
                        // 微信图片窗口
                        addLoadEvent(getPicInfo);
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

	function addLoadEvent(func){ 
    //将函数作为参数，此函数就是 onload 触发时需要执行的某个函数
        var oldonload=window.onload; 
        //将原来的 onload 的值赋给临时变量 oldonload。
        if(typeof window.onload!="function"){
        //判断 onload 的类型是否是 function。如果已经执行window.onload=function(){...} 赋值，那么此时 onload 的类型就是 function
        //否，则说明 onload 还没有被赋值，当前任务 func 为第一个加入的任务
            window.onload=func(); 
            
            //作为第一个任务，给 onload 赋值
        }else{ 
        //是，则说明 onload 已被赋值，onload 中先前已有任务加入
            window.onload=function(){
                oldonload();
                func(); 
                //作为后续任务，追加到先前的任务后面
            };
        }
	}

  var imgs=new Array();
  var nowImgurl="";
  function getPicInfo()
  {
    var imgObj=document.getElementsByTagName('img');  //获取图文中所有的img标签对象
    
    for(var i=0; i<imgObj.length; i++)
    {
      imgs.push(imgObj[i].getAttribute("origile")); 
      nowImgurl = this.src;    //获取当前点击图片url
      //下面调用微信内置图片浏览组建
      imgObj[i].onclick=function(){
        WeixinJSBridge.invoke("imagePreview",{
          "urls":imgs,
          "current":this.src
          });
      };
    }
  }
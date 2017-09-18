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
	                url: './io/list',
	                data: 'page=' + 1 + "&limit=" + limit + "&order=desc&sidx=",
	                dataType: 'json',
	                success: function(data){
	                    var result = '';
	                    var arrLen = data.page.list.length;
	                    if(arrLen > 0){
	                        for(var i=0; i<arrLen; i++){
	                        	if (i == 0) {
	                        		result += '<li class="timeline-item">'+
						        					'<div class="timeline-item-head-first">'+
						    						'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked"></i>'+ 
						    					'</div>'+
						    					'<div class="timeline-item-tail">'+
						    					'</div>'+
						    					'<div class="timeline-item-content">'+
						    						'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
						    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
						    					'</div>'+
						    				'</li>';
	                        	} else if ( i > 0 && i < (arrLen - 1)) {
	                        		result += '<li class="timeline-item">'+
						        					'<div class="timeline-item-head">'+
						    						'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked hide"></i>'+ 
						    					'</div>'+
						    					'<div class="timeline-item-tail">'+
						    					'</div>'+
						    					'<div class="timeline-item-content">'+
							    					'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
						    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
						    					'</div>'+
						    				'</li>';
	                        		
	                        	} else {
	                        		result += '<li class="timeline-item">'+
					        					'<div class="timeline-item-head">'+
					    						'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked hide"></i>'+ 
					    					'</div>'+
					    					'<div class="timeline-item-tail hide">'+
					    					'</div>'+
					    					'<div class="timeline-item-content">'+
						    					'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
					    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
					    					'</div>'+
					    				'</li>';
	                        	}
	                        	
	                        	
							}
	                    }else{
	                    	// 如果没有数据
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
	                    page = 1;
	                    // 为了测试，延迟1秒加载
	                    $('#changContainer').html(result);  
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
	                url: './io/list',
	                data: 'page=' + page + "&limit=" + limit + "&order=desc&sidx=",
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.list.length;
	                    if(arrLen > 0){
	                    	if (page > 1 ){
	                    		var sec = $("div .timeline-item-tail").attr("class","timeline-item-tail");
	                    		alert(sec.length);
	                    	}
	                    		
	                        for(var i=0; i<arrLen; i++){
	                        	if (i == 0) {
	                        		if (page == 1) {
	                        			result += '<li class="timeline-item">'+
							        					'<div class="timeline-item-head-first">' +
							        					'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked"></i>'+ 
							    					'</div>'+
							    					'<div class="timeline-item-tail">'+
							    					'</div>'+
							    					'<div class="timeline-item-content">'+
							    						'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
							    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
							    					'</div>'+
							    				'</li>';
	                        		} else {
	                        			result += '<li class="timeline-item">'+
								        					'<div class="timeline-item-head">'+
								    						'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked hide"></i>'+ 
								    					'</div>'+
								    					'<div class="timeline-item-tail">'+
								    					'</div>'+
								    					'<div class="timeline-item-content">'+
									    					'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
								    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
								    					'</div>'+
								    				'</li>';
	                        		}
	                        	} else if ( i > 0 && i < (arrLen - 1)) {
	                        		result += '<li class="timeline-item">'+
						        					'<div class="timeline-item-head">'+
						    						'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked hide"></i>'+ 
						    					'</div>'+
						    					'<div class="timeline-item-tail">'+
						    					'</div>'+
						    					'<div class="timeline-item-content">'+
							    					'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
						    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
						    					'</div>'+
						    				'</li>';
	                        		
	                        	} else {
	                        		result += '<li class="timeline-item">'+
					        					'<div class="timeline-item-head">'+
					    						'<i class="weui_icon weui_icon_success_no_circle timeline-item-checked hide"></i>'+ 
					    					'</div>'+
					    					'<div class="timeline-item-tail hide">'+
					    					'</div>'+
					    					'<div class="timeline-item-content">'+
						    					'<h4 class="recent">' + data.page.list[i].ioType + '</h4>'+
					    						'<p class="recent">' + data.page.list[i].ioDate + '</p>'+
					    					'</div>'+
					    				'</li>';
	                        	}
							}
	                    }else{
	                    	// 如果没有数据
	                        // 锁定
	                        me.lock();
	                        // 无数据
	                        me.noData();
	                    }
	
	                    // 为了测试，延迟1秒加载
	                    $('#changContainer').append(result);  
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

	/*wx.config({
		    // 配置信息
	});
	wx.ready(function () {
		var media = document.getElementById("mp3");
	    media.src = "http://a.f265.com/project/shake-money/img/shake.mp3"
	    media.play();
	});*/
	var playStatus = 0;
	$(function(){
		// 加载数据
		$.ajax({
			type: "GET",
		    url: "../photoschool/info/" + $('#schoolId').val(),
		    dataType: "json",
		    success: function(r){
				if(r.status == 'ok'){
					//轮播图
					if (r.photoSchool.typeList.length > 0) {
						for(var i = 0 ; i < r.photoSchool.typeList[0].picList.length; i++){
							var contentPic = '<li>'+
									            '<a href="../classes/classPhotoClassify.html?type=4&id=' + $('#schoolId').val() + '">'+
									                '<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC" '+ 
									                ' data-src="' + r.url + r.photoSchool.typeList[0].picList[i].name +'" alt="">'+
									           '</a>'+
									          '</li>';
							$('#slideContainer').append(contentPic);
							$('#myDot').append('<span></span>');
						}
						// 图片滑动
						 $('#slide2').swipeSlide({
								autoSwipe:true,//自动切换默认是
								speed:3000,//速度默认4000
								continuousScroll:true,//默认否
								transitionType:'cubic-bezier(0.22, 0.69, 0.72, 0.88)',//过渡动画linear/ease/ease-in/ease-out/ease-in-out/cubic-bezier
								lazyLoad:true,//懒加载默认否
								firstCallback : function(i,sum,me){
								            me.find('.dot').children().first().addClass('cur');
								},
						        callback : function(i,sum,me){
						            me.find('.dot').children().eq(i).addClass('cur').siblings().removeClass('cur');
						        }
						});
					}
					// 班级介绍
					$('#desc').append(r.photoSchool.schoolDesc);
					// 学校名
					$('#shoolName').text(r.photoSchool.name);
					//修改音乐
					var music = '<audio id="mp3"  preload="preload" >'+
									'<source id="music" src="' + r.url  + r.photoSchool.music + '">不支持</source>'+
								'</audio>';
					$('#musicContainer').append(music);
					
					//添加学生
					if (r.photoSchool != null) {
						
						var htmlCont = "";
						var dispatcherUrl = "";
						
						if (r.photoSchool.schoolType == 1) {
							// 初高中
							$('#schoolTypeText').text("毕业年级");
							$("#theContainer").empty();
							for(var i = 0; i < r.photoSchool.timeList.length; i++){
								
								dispatcherUrl = "allClasses.html?schoolId=" + $('#schoolId').val() + "&timeId=" + r.photoSchool.timeList[i].id;
								
								//名字截取
								var graduationName = r.photoSchool.timeList[i].graduationName;
								if ( GetLength(graduationName) > 6) {
									graduationName = cutstr(graduationName, 6);
								}
								
								htmlCont = '<a href="' + dispatcherUrl + '" class="weui_grid js_grid" >'+
									                '<div class="weui_grid_icon" style="width: 58px;height: 58px;">'+
								                    '<img class="circle" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFACAYAAADNkKWqAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAwNi8wNS8xNrqrthwAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzbovLKMAAAEMElEQVR4nO3UQQEAEADAQPRvqIESxPDYXYK9Nvc+dwAErd8BAL8YIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBA1gPYJgYfB4WzDQAAAABJRU5ErkJggg=="'+
								                     'data-src="' + r.url + 'static-sources/maozi.jpg" alt="">'+
								                '</div>'+
								                '<p class="weui_grid_label">' + graduationName + '</p>'+
								            '</a>';
								
								$("#theContainer").append(htmlCont);
							}
						} else {
							// 大学
							$('#schoolTypeText').text("学校学院");
							for(var i = 0; i < r.photoSchool.collegeList.length; i++){
								
								dispatcherUrl = "allGrades.html?collegeId=" + r.photoSchool.collegeList[i].id;
								//名字截取
								var collegeName = r.photoSchool.collegeList[i].name;
								if ( GetLength(collegeName) > 6) {
									collegeName = cutstr(collegeName, 6);
								}
								
								htmlCont = '<a href="' + dispatcherUrl + '" class="weui_grid js_grid" >'+
									                '<div class="weui_grid_icon" style="width: 58px;height: 58px;">'+
								                    '<img class="circle" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFACAYAAADNkKWqAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAwNi8wNS8xNrqrthwAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzbovLKMAAAEMElEQVR4nO3UQQEAEADAQPRvqIESxPDYXYK9Nvc+dwAErd8BAL8YIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBA1gPYJgYfB4WzDQAAAABJRU5ErkJggg=="'+
								                     'data-src="' + r.url + 'static-sources/maozi.jpg" alt="">'+
								                '</div>'+
								                '<p class="weui_grid_label">' + collegeName + '</p>'+
								            '</a>';
								$("#theContainer").append(htmlCont);
							}
						}
						
						
					}
					// 设置视频
					if ( r.photoSchool.vedio != null && r.photoSchool.vedio != '') {
						var container = $('#theVedio');
						container.empty();
						var videoContent = '<video poster="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495257128452&di=e47e76193976650d5b6bdf61bf50a061&imgtype=0&src=http%3A%2F%2Fp3.gexing.com%2FG1%2FM00%2FCE%2F80%2FrBACFFO5DIvgwQEZAAOnGl9ELEc532.jpg" data-config=\'{"mediaTitle": "班级视频"}\' webkit-playsinline>'+
										       '<source id="vedioSrc" src="' + r.url + r.photoSchool.vedio + '" type="video/mp4" >'+
										        '您的浏览器不支持HTML5视频'+
										    '</video>';
						container.append(videoContent);
					}else{
						$('#theVedio').css("display","none");
					    $('#schoolvoideid').css("display","none");
					}
					
					//视频样式设置
					zymedia('video',{});
				}
				
			}
		});
		
		
		// 图片懒加载
		var lazyloadImg = new LazyloadImg({
        el: '.weui_grid_icon [data-src]', //匹配元素
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
    
	
    
  });
  
  function audioplay(id){
	  var audioEle = document.getElementById(id);
		
	  if (playStatus == 0) {
		  audioEle.play();
		  
		  playStatus = 1;
	  } else {
		  audioEle.pause();
		  
		  playStatus = 0;
	  }
	  
	   /* var audio = document.getElementById(id);
	   !audio.paused?audio.pause():audio.play();
		
		
		
		//var media = document.getElementById("musicBox");
		if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
		    WeixinJSBridge.invoke('getNetworkType', {}, function (res) {
		        // 在这里拿到 e.err_msg, 这里面就包含了所有的网络类型
		        // alert(res.err_msg);
		        !audio.paused?audio.pause():audio.play();
		        //audio.load();
		        //audio.play();
		    });
		}*/
		
	    /* var audioa=document.querySelector("#" + id);
	    document.addEventListener("WeixinJSBridgeReady", function () {
	          !audioa.paused?audioa.pause():audioa.play();
	    }, false); */ 
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
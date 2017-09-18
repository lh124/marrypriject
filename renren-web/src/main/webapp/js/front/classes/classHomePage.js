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
		loadData();
	});
	
	function loadData(){
		// 加载数据
		$.ajax({
			type: "GET",
		    url: "../photoclass/info/" + $('#classId').val(),
		    dataType: "json",
		    success: function(r){
				if(r.status == 'ok'){
					// 渲染界面
					setData(r);
					// 设置懒加载图片
					lazyImg();
				} else {
					dialog("请输入密码");
				}
			}
		});
  };
  
  function dialog(msg){
	  $.prompt(msg, "查看密码", function(text) {
  		$.ajax({
  			type: "GET",
  		    url: "../photoclass/info/" + $('#classId').val() + "?password="+ text,
  		    dataType: "json",
  		    success: function(r){
  				if(r.status == 'ok'){
  					// 渲染界面
  					setData(r);
  					// 设置懒加载图片
  					lazyImg();
  				} else {
  					dialog(r.msg);
  				}
  		    }
  		});
  	}, function() {
  		window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9072ff1ebcf745c&redirect_uri=http%3a%2f%2fwrs.gykjewm.com%2ffront%2flogin.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    		//取消操作
  	});
  }
  
  function setData(json){
	  var r = json;
	//轮播图
		if (r.photoClass.photoTypeList.length > 0) {
			for(var i = 0 ; i < r.photoClass.photoTypeList[0].picList.length; i++){
				var contentPic = '<li>'+
						            '<a href="classPhotoClassify.html?type=1&id=' + $('#classId').val() + '">'+
						                '<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC" '+ 
						                ' data-src="' + r.url + r.photoClass.photoTypeList[0].picList[i].name + '" alt="">'+
						           '</a>'+
						          '</li>';
				$('#slideContainer').append(contentPic);
				$('#myDot').append('<span></span>');
				
				// 最多只显示5张
				if( i >= 5) {
					break;
				}
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
		// 班级相册连接
		$('#classPhoto').attr("href", 'classPhotoClassify.html?type=1&id=' + $('#classId').val());
		// 班级名
		$('#className').text(r.photoClass.name);
		// 班级介绍
		$('#desc').append(r.photoClass.classDesc);
		//修改音乐
		var music = '<audio id="mp3"  preload loop="loop" >'+
						'<source  src="' + r.url  + r.photoClass.music + '">不支持</source>'+
					'</audio>';
		$('#musicContainer').append(music);
		// 设置学校id
		$('#forSchoolId').attr( 'href', $('#forSchoolId').attr('href') + "?schoolId=" + r.photoClass.schoolId);
		
		//添加学生
		if (r.photoClass.userList.length > 0) {
			var htmlCont = "";
			for(var i = 0; i < r.photoClass.userList.length; i++){
				
				var role = "";
				if (r.photoClass.userList[i].userClass.classRoleType == 1) {
					role = "班主任";
				} else if (r.photoClass.userList[i].userClass.classRoleType == 2) {
					role = "任课老师";
				} else if (r.photoClass.userList[i].userClass.classRoleType == 3) {
					role = "班长";
				} else if (r.photoClass.userList[i].userClass.classRoleType == 4) {
					role = "班委";
				} else if (r.photoClass.userList[i].userClass.classRoleType == 5) {
					role = '&nbsp;&nbsp;';
				} else {
					role = '&nbsp;&nbsp;';
				}
				
				var nickname = r.photoClass.userList[i].nickname;
				if ( GetLength(nickname) > 6) {
					nickname = cutstr(nickname, 6);
				}
					
				
				htmlCont = '<a href="./userPhotoType.html?id=' + r.photoClass.userList[i].id + '&classId='+ $('#classId').val() +'" class="weui_grid js_grid" >'+
					                '<div class="weui_grid_icon" style="width: 58px;height: 58px;">'+
				                    '<img class="circle" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAAFACAYAAADNkKWqAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAwNi8wNS8xNrqrthwAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzbovLKMAAAEMElEQVR4nO3UQQEAEADAQPRvqIESxPDYXYK9Nvc+dwAErd8BAL8YIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBAlgECWQYIZBkgkGWAQJYBAlkGCGQZIJBlgECWAQJZBghkGSCQZYBA1gPYJgYfB4WzDQAAAABJRU5ErkJggg=="'+
				                     'data-src="'+ r.url + r.photoClass.userList[i].headImg + '" alt="">'+
				                '</div>'+
				                '<p class="weui_grid_label">' + nickname + '</p>'+
				               	'<p class="weui_grid_label">' + role +'</p>'+
				            '</a>';
				$("#studentContainer").append(htmlCont);
			}
		}
		
		// 设置视频
		if ( r.photoClass.video != null && r.photoClass.video != '') {
			var container = $('#theVedio');
			container.empty();
			var videoContent = '<video poster="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495257128452&di=e47e76193976650d5b6bdf61bf50a061&imgtype=0&src=http%3A%2F%2Fp3.gexing.com%2FG1%2FM00%2FCE%2F80%2FrBACFFO5DIvgwQEZAAOnGl9ELEc532.jpg" data-config=\'{"mediaTitle": "班级视频"}\' webkit-playsinline>'+
							       '<source id="vedioSrc" src="' + r.url + r.photoClass.video + '" type="video/mp4" >'+
							        '您的浏览器不支持HTML5视频'+
							    '</video>';
			container.append(videoContent);
		}
		
		//视频样式设置
		zymedia('video',{});
  }
  
  function lazyImg(){
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
  }
  
  function audioplay(id){
	  var audioEle = document.getElementById(id);
		
	  if (playStatus == 0) {
		  audioEle.play();
		  
		  playStatus = 1;
	  } else {
		  audioEle.pause();
		  
		  playStatus = 0;
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
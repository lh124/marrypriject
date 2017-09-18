$(function(){
	// 加载数据
	var map = new AMap.Map('container', {
        resizeEnable: true,
        center: [116.397428, 39.90923],
        zoom: 13
    }); 
    map.clearMap();  // 清除地图覆盖物
	$.ajax({
		type: "GET",
	    url: "../photoclass/info/" + $('#classId').val(),
	    dataType: "json",
	    success: function(r){
	    	//添加学生
			if (r.photoClass.userList.length > 0) {
				var htmlCont = "";
				for(var i = 0; i < r.photoClass.userList.length; i++){
					
					var head = r.url + r.photoClass.userList[i].headImg;
					var nickname = r.photoClass.userList[i].nickname;
					if ( GetLength(nickname) > 6) {
						nickname = cutstr(nickname, 6);
					}
					
					var longitude = r.photoClass.userList[i].longitude;
					var latitude = r.photoClass.userList[i].latitude;
					htmlCont = '<div class="mybg">'+
									'<image src="' + head + '" class="image1">'+
									nickname+
								'</div>';
					if ( longitude != null &&  longitude != '' && latitude != null &&  latitude != '') {
						new AMap.Marker({
				            map: map,
				            content: htmlCont,
				            position: [longitude, latitude],
				            offset: new AMap.Pixel(-12, -36)
				        });
					}
					
				}
			}
			
			var center = map.getCenter();
		    // 添加事件监听, 使地图自适应显示到合适的范围
		    var newCenter = map.setFitView();
		    map.plugin(["AMap.ToolBar"], function() {
				map.addControl(new AMap.ToolBar());
			});
			if(location.href.indexOf('&guide=1')!==-1){
				map.setStatus({scrollWheel:false})
			}
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
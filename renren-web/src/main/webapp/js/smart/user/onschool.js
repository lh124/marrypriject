$(function(){
	var height = screen.width;
	//页数 
	    $('.weui_panel').dropload({
	        scrollArea : window,
	        autoLoad : true,//自动加载
	        loadDownFn : function(me){//加载更多
	         window.history.pushState(null, document.title, window.location.href);
	         var result = '';
	            $.ajax({
	                type: 'GET',
	                url:'../../smart/shouye/getonschoolstudent',
	                dataType: 'json',
	                success: function(data){
	           			var arrLen = data.page.length;
	           				result += '<div class="weui_panel weui_panel_access" ><div class="weui_cell moments__post"><div class="weui_cell_bd" style="width:100%;">';
	           				result += '<table style="text-align: center;width:100%;" border="1" cellspacing="0" bordercolor="#000000" >';
	           				if(arrLen > 0){
	           					result += '<tr><td colspan="2" width="'+height+'"><b style="font-size:18px;">共有'+arrLen+'人不在校</b></td></tr>';
		           				result += '<tr ><td width="'+height/4+'"><b>姓名</b></td><td width="'+height/4*3+'"><b>出校时间</b></td></tr>';
		                    	for(var i = 0; i < arrLen; i++){
			           				result += '<tr><td width="'+height/4+'">'+data.page[i].name+'</td><td width="'+height/4*3+'">'+data.page[i].date+'</td></tr>';
			                    }
		                    }else{
		                    	result += '<tr ><td colspan="2" width="'+height+'">暂无外出人员</td></tr>';
		                    }
		                    result += '</table>';
	                    $('.weui_panel_access').html(result);  
	                    $(".dropload-load").hide();
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
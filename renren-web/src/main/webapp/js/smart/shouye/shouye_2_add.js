function forSubmit(){
	var contentNotice = document.getElementById("contentNotice").value;
	if(contentNotice == null || contentNotice == ""){
		contentNotice = "未编写任何内容就已发布了。";
	}
	var title = document.getElementById("title").value;
	if(title == null || title == ""){
		title = "未填写标题";
	}
	var classId = document.getElementById("classId").value;
	$.ajax({
		type: "POST",
	    url: "../shouye/savesmartword?content=" + contentNotice + "&title=" + title,
	    success: function(r){
	    	//window.location.href="./shouye_2.html?class="+classId;
		}
	});
}

function counts(obj){
	var content = $(obj).val();
	var length = GetLength(content);
	
	$(".weui-textarea-counter span").text(length);
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
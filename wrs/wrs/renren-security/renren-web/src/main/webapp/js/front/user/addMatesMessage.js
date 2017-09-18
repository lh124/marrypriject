function forSubmit(){
	
	if ($(".weui-textarea").val() == null || $(".weui-textarea").val() == "")
		return ;
	if ($("#userId").val() == null || $("#userId").val() == "")
		return ;
	
	$.ajax({
		type: "POST",
	    url: "../photoclassmates/save",
	    data:{"contents": $(".weui-textarea").val(),"userGetId": $("#userId").val(), "classId": $("#classId").val()},
	    dataType: "json",
	    success: function(r){
	    	
	    }
	});
};

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
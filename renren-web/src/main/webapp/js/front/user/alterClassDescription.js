$(function (){
	$.ajax({
		type: "GET",
	    url: "../photoclass/descInfo/" + $('#classId').val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				$('.weui-textarea-counter span').val(r.description);
				var length = GetLength(r.description);
				$(".weui-textarea").text(r.description);
				
				$(".weui-textarea-counter span").text(length);
			}
		}
	});
});

// 修改班级描述
function changdescription(){
	var desc = $('.weui-textarea').val();
	var id = $('#classId').val();
	$.ajax({
		type: "POST",
	    url: "../photoclass/userUpdate",
	    data: {"classDesc": desc, "id": id},
	    dataType: "json",
	    success: function(r){
	    	$('#iosDialog1').fadeOut(100);
			if (r.status == 'ok') {
				alert("修改成功");
			} else {
				alert(r.msg);
			}
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
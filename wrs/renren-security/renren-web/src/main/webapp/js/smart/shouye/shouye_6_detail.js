$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/schoolnoticedetail?id=" + $('#noticeId').val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = '<div style="font-size: 18px;text-align: center;">'+ r.schoolNotice.title + '</div>' +  
					          '<div  ><img src=" ' + r.schoolNotice.noticepic + '" style="width:100%;"></div>'+
								'<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + r.schoolNotice.content+'</p>'+
								'<div style="float:right;font-size: 20px;padding-right: 5px;padding-top: 8px;"><i> 发布时间：'+ r.schoolNotice.createTime + '</i></div>';
				$("div").html(content);
				
			}
		}
	});
});
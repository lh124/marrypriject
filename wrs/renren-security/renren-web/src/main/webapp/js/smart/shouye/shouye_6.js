$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/schoollist?order=desc&page=1&limit=100&sidx=",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				$("#classContainer").empty();
				for(var i = 0; i < r.schoolNotice.list.length; i++){
					var title = r.schoolNotice.list[i].title;
					if(title.length >= 15){
						title = title.substring(0,15) + '......';
					}
					content += '<a class="weui-cell weui-cell_access" href="./shouye_6_detail.html?noticeId='+ r.schoolNotice.list[i].id 
							  +'">'+
	                          '<div class="weui-cell__bd">'+
	                          '<p>' + title +  '</p>'+
	                           '</div>'+
	                          '<div class="weui-cell__ft"></div>'+
	                          '</a>';
				}
			$(".weui-cells").html(content);
				
			}
		}
	});
});
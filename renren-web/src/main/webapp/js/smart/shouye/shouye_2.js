$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/list_2?page=1&limit=1000&sidx=&order=desc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				for(var i = 0; i < r.page.list.length; i++){
					var title = r.page.list[i].name;
					if(title.length >10){
						title = title.substring(0,10) + "......";
					}
					content += '<a class="weui-cell weui-cell_access" href="./shouye_2_detail.html?id='+ r.page.list[i].id 
							  +'">'+
							  '<div class="weui-cell__bd">'+
							  '<div style="float:left;">' + title + '</div><div style="float:right;">【'+r.page.list[i].createtime.substring(0,10)+'】</div>'+
							  '</div>'+
							  '<div class="weui-cell__ft"></div>'+
							  '</a>';
				}
			$(".weui-cells").html(content);
				
			}
		}
	});
});
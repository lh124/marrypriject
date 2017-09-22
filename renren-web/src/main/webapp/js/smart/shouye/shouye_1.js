$(function(){
	$.ajax({
		type: "GET",
	    url: "../shouye/list_1?page=1&limit=1000&sidx=&order=desc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var content = "";
				for(var i = 0; i < r.page.list.length; i++){
					var title = r.page.list[i].title;
					if(title.length >14){
						title = title.substring(0,14) + "......";
					}
					content += '<a class="weui-cell weui-cell_access" href="./shouye_1_detail.html?id='+ r.page.list[i].id 
							  +'">'+
							  '<div class="weui-cell__bd">'+
							  '<p>' + title + '</p>'+
							  '</div>'+
							  '<div class="weui-cell__ft"></div>'+
							  '</a>';
				}
			$(".weui-cells").html(content);
				
			}
		}
	});
});
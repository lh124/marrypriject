	$(function(){
		// 加载数据
		$.ajax({
			type: "GET",
		    url: "../phototype/listClassType?type="+ $('#type').val() + "&id=" + $('#id').val(),
		    dataType: "json",
		    success: function(r){
		    	var container = $('#typeContainer');
				if(r.status == 'ok'){
					if ( r.list.length > 0) {
						for(var i = 0; i < r.list.length ; i++){
							
							var img = "";
							if (r.list[i].picList.length > 0) {
								img = r.list[i].picList[0].name;
							}
							var htmlCont = '<a href="classPhotoList.html?typeId=' + r.list[i].id + '">'+
											  '<img origile="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493810647768&di=aade1fc6e6a443ff64242d1e7071a4a2&imgtype=0&src=http%3A%2F%2Fimg3.3lian.com%2F2014%2Ff1%2F5%2Fd%2F45.jpg" ' + 
											  'src="' + r.url + img +'" class="img-border img-max img-radius">'+
										   '</a>'+
										   '<h4>' + r.list[i].title + '<h4>';
							container.append(htmlCont);
						}
					}
				}
				
			}
		});
    
	});
  

$(function(){
	getDatas();
	// 添加事件
	$(document).on("click", "#sd3", function() {
    	$.prompt("添加分类", "分类名", function(text) {
    		addType(text);
    	}, function() {
      		//取消操作
    	});
    });
});


function getDatas(){
	$.ajax({
		type: "GET",
	    url: "../phototype/querUserType",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				if ( r.list.length > 0 ) {
					$('#item').empty();
					for(var i = 0; i < r.list.length; i++){
						var htmContent = '<div class="weui_cell">'+
										        '<div class="weui_cell_bd weui_cell_primary">'+
									            '<p><a href="javascript:updatType(' + r.list[i].id + ')">' + r.list[i].title +'</a></p>'+
									        '</div>'+
									        '<div class="weui_cell_ft"><a dataId="' + r.list[i].id + '" class="delete hide" href="javascript:;"><i class="icon icon-26 f20 f-blue"></i></a></div>'+
										'</div> ';
						
						$('#item').append(htmContent);
					}
				}
				// 注册事件
				forEvents();
			}
	    }
	});
}

function forEvents(){
	$(document).on('swipe','#item .weui_cell',function(){
	 	$('.delete').hide()
  		$('.delete', this).show();
	})
	
	$(document).on('tap','.delete',function(){
		var obj = $(this);
		var id = obj.attr('dataId');
		$.confirm("您确定要删除吗?", "确认删除?", function() {
			if (deleteType(id) )
			obj.parent().parent('.weui_cell').remove();
        }, function() {
          //取消操作
        });
		
	})
}


function addType(text){
	$.ajax({
		type: "POST",
	    url: "../phototype/save",
	    data: {"title": text},
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				getDatas();
				$.alert("添加成功");
			}
	    }
	});
}

function deleteType(id){
	$.ajax({
		type: "POST",
	    url: "../phototype/delete",
	    data: {"typeId": id},
	    dataType: "json",
	    success: function(r){
			if (r.status == 'ok') {
				getDatas();
				$('#item').html("");
				$.alert("删除成功");
				return true;
			} else {
				$.alert(r.msg);
				return false;
			}
	    }
	});
}

function updatType(id) {
	
	$.prompt("修改分类", "新分类名", function(text) {
		$.ajax({
			type: "POST",
		    url: "../phototype/update",
		    data: {"typeId": id, "text":text},
		    dataType: "json",
		    success: function(r){
				if (r.status == 'ok') {
					getDatas();
					$.alert("修改成功");
				} else {
					$.alert(r.msg);
				}
		    }
		});
	}, function() {
  		//取消操作
	});
}
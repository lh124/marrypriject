$(function(){
	$.ajax({
		type: "GET",
	    url: "../photofrontuserinfo/secretInfo",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				
				var checked = '';
				if (r.perm == 2) {
					checked = 'checked="checked"';
					$('#perm').val(1);
				} else {
					$('#perm').val(2);
				}
				var content = "";
				content += '<div class="weui-cell weui-cell_switch">'+
				                '<div class="weui-cell__bd">开启密码验证</div>'+
				                '<div class="weui-cell__ft">'+
				                    '<label for="switchCP" class="weui-switch-cp">'+
				                        '<input id="switchCP" class="weui-switch-cp__input" type="checkbox" '+
				                        checked+' />'+
				                        '<div class="weui-switch-cp__box" id="box" onclick="changPerm()"></div>'+
				                    '</label>'+
				                '</div>'+
				            '</div>';
				$("#switch").html(content);
			}
		}
	});
});

function changPerm(){
	var perm = $('#perm').val();
	var id = $('#classId').val();
	$.ajax({
		type: "POST",
	    url: "../photofrontuserinfo/update",
	    data: {"perm": perm},
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				if ( perm == 2) {
					$('#perm').val(1);
				} else {
					$('#perm').val(2);
				}
			}
		}
	});
}

function changPassword(){
	var password = $('#password').val();
	var id = $('#classId').val();
	$.ajax({
		type: "POST",
	    url: "../photofrontuserinfo/update",
	    data: {"password": password},
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				alert("修改成功");
			}
		}
	});
}
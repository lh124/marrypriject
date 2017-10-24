function init(){
    $.ajax({
		type: "POST",
	    url: "../../tombstone/getrelationship",
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    		var content = '<option value="-1">请选择</option>';
	    	    $("#relation").html(content);
	    		for(var i = 0; i < result.list.length; i++){
	    			content += '<option value="'+result.list[i].name+'">'+result.list[i].name+'</option>';
	    		}
	    		$("#relation").html(content);
			}
		}
	});
    $.ajax({
		type: "POST",
	    url: "../../tombstone/getalluser",
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    		var content = '<option value="0">请选择</option>';
	    	    $("#parentid").html(content);
	    		for(var i = 0; i < result.list.length; i++){
	    			content += '<option value="'+result.list[i].id+'">'+result.list[i].name+'</option>';
	    		}
	    		$("#parentid").html(content);
			}
		}
	});
    $.ajax({
		type: "POST",
	    url: "../../tombstone/getalluser",
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    	    var content = '<option value="-1">请选择</option>';
	    	    $("#usertype").html(content);
	    		for(var i = 0; i < result.list.length; i++){
	    			content += '<option value="'+result.list[i].id+'">'+result.list[i].name+'</option>';
	    		}
	    		$("#usertype").html(content);
			}
		}
	});
}


function forSubmit(){
	var usertype = document.getElementById("usertype").value;
	var parentid = document.getElementById("parentid").value;
	var relation = document.getElementById("relation").value;
	var name = document.getElementById("name").value;
	$.ajax({
		type: "POST",
	    url: "../../tombstone/saveDeadEntity?usertype="+usertype+"&parentid="+parentid+"&relation="+relation+"&name="+name,
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    		alert('操作成功');
			}
		}
	});
}
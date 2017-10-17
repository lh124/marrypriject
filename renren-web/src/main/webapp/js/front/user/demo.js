function dataURItoBlob (base64Data) {  
	var byteString;  
	if (base64Data.split(',')[0].indexOf('base64') >= 0)  
	    byteString = atob(base64Data.split(',')[1]);  
	else  
	    byteString = unescape(base64Data.split(',')[1]);  
	var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];  
	var ia = new Uint8Array(byteString.length);  
	for (var i = 0; i < byteString.length; i++) {  
	    ia[i] = byteString.charCodeAt(i);  
	}  
	return new Blob([ia], {type: mimeString});  
}

function set_upload_param(path,id){
	$.ajax({ 
		type: "POST",
	    url: "../../publicModule/common/oss/getSign",
	    data: {"id":id,"type":5},
	    dataType:'json',
	    success: function (result) {
	    	var obj = result;
	        host = obj.host;
	        policyBase64 = obj.policy;
	        accessid = obj.accessid;
	        signature = obj.signature;
	        expire = parseInt(obj.expire);
	        callbackbody = obj.callback;
	        key = obj.dir;
	    	var formData = new FormData();
	    	var blob = dataURItoBlob(path);
	    	formData.append("name","headss.png");
	    	formData.append("key", key + Date.parse(new Date()) + ".png");
	    	formData.append("policy",policyBase64);
	    	formData.append("OSSAccessKeyId",accessid);
	    	formData.append("success_action_status","200");
	    	formData.append("callback",callbackbody);
	    	formData.append("signature",signature);
	    	formData.append("file",blob);
	    	$.ajax({ 
	    		url : host, 
	    		type : 'POST', 
	    		data : formData, 
	    		processData : false, 
	    		contentType : false,
	    		beforeSend:function(){
	    		},
	    		success : function(responseStr) { 
	    			if(responseStr.Status=='OK'){
	    				//alert("上传成功");
	    			}else{
	    				alert(responseStr);
	    			}
	    		}, 
	    		error : function(responseStr) { 
	    			console.log("error");
	    		} 
	    	});
		},
		error:function(){
			alert("123");
		}
    });
    
}
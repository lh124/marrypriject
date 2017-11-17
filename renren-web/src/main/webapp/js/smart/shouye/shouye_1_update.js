
accessid = ''
accesskey = ''
host = ''
policyBase64 = ''
signature = ''
callbackbody = ''
filename = ''
key = ''
expire = 0
g_object_name = ''
g_object_name_type = ''
now = timestamp = Date.parse(new Date()) / 1000; 
var times = 1;
var messageId = null;
function save_data(){
	$.ajax({ 
		type: "POST",
	    url: "../photoclassworkmsg/save",
	    data: {"classId":"1","userId":"1","content":$('.weui-textarea').val()},
	    dataType:'json',
	    success: function (result) {
	    	messageId = result.id;
		}
});
};

function get_signature(){
	$.ajax({ 
		type: "POST",
	    url: "../photoclassworkmsg/save",
	    data: {"classId":$('#classId').val(),"userId":$('#userId').val(),"content":$('.weui-textarea').val()},
	    dataType:'json',
	    success: function (result) {
	    	$.ajax({ 
	    		type: "POST",
	    	    url: "../../publicModule/common/oss/getSign",
	    	    data: {"id":result.id,"type":12},
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
	    	        return true;
	    		}
	        });
		}
    });
};


function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function GetRandomNum(Min,Max)
{   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
} 

function calculate_object_name(filename)
{
    /*if (g_object_name_type == 'local_name')
    {
        g_object_name += "${filename}"
    }
    else if (g_object_name_type == 'random_name')
    {
        suffix = get_suffix(filename)
        g_object_name = key + random_string(10) + suffix
    }*/
    
	suffix = get_suffix(filename)
    g_object_name = key + GetRandomNum(1,1000) + Date.parse(new Date()) + suffix
    return ''
}

function get_uploaded_object_name(filename)
{
    if (g_object_name_type == 'local_name')
    {
        tmp_name = g_object_name
        tmp_name = tmp_name.replace("${filename}", filename);
        return tmp_name
    }
    else if(g_object_name_type == 'random_name')
    {
        return g_object_name
    }
}

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

function set_upload_param(path,type,type2,id){
	$.ajax({ 
		type: "POST",
	    url: "../../publicModule/common/oss/getSign",
	    data: {"id":id,"type":type},
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
	    				alert("上传成功");
	    				if(type2 == 1){
	    					window.location.href="./shouye_1.html?classId="+document.getElementById("classId").value;
	    				}else if(type2 == 2){
	    					window.location.href="./shouye_2.html?classId="+document.getElementById("classId").value;
	    				}else if(type2 == 6){
	    					window.location.href="./shouye_6.html?schoolId="+document.getElementById("schoolId").value;
	    				}
	    			}
	    		}, 
	    		error : function(responseStr) { 
	    			console.log("error");
	    		} 
	    	});
		}
    });
    
}


var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    //multi_selection: false,
	container: document.getElementById('container'),
	flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
    url : 'http://oss.aliyuncs.com',

    filters: {
        mime_types : [ //只允许上传图片和zip文件
        { title : "Image files", extensions : "jpg,gif,png,bmp,jpeg" }, 
        { title : "Zip files", extensions : "zip,rar" }
        ],
        max_file_size : '3mb', //最大只能上传10mb的文件
        prevent_duplicates : true //不允许选取重复文件
    },

	init: {
		PostInit: function() {
			
		},

		FilesAdded: function(up, files) {
			for(var i = 0, len = files.length; i<len; i++){
				//var file_name = files[i].name; //文件名
				//构造html来更新UI
				var addElemet = '<li picId="' + files[i].id + '" id="pic_' + files[i].id + '" class="weui-uploader__file" ></li>'
				$('#uploaderFiles').append(addElemet);
				
				!function(i){
					previewImage(files[i],function(imgsrc){
						//$('#pre_img_'+files[i].id).attr('src',imgsrc);
						$('#pic_' + files[i].id).attr("style", "background-image:url(" + imgsrc + ")");
						var progress = '<div id="pro_' + files[i].id + '" class="weui-uploader__file-content"></div>';
						$('#pic_' + files[i].id).append(progress);
					})
			    }(i);
			}
			
			$("#picSize").text(files.length + "/7");
			/*plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
				+'</div>';
			});*/
		},

		BeforeUpload: function(up, file) {
            set_upload_param(up, file.name, true);
        },

		UploadProgress: function(up, file) {
			//  这个地方是循环的
			var container = $('#pic_' + file.id);
			container.attr("class", "weui-uploader__file weui-uploader__file_status");
			var progre = $('#pro_' + file.id).text(file.percent + '%');
			
			//progess.parent().parent().attr("class","file-thumb-progress");
			//progess.text(file.percent + '%');
			//progess.css("width", file.percent + '%');
			/*var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= 2*file.percent+'px';
			progBar.setAttribute('aria-valuenow', file.percent);*/
		},

		FileUploaded: function(up, file, info) {
			
			if (times == uploader.files.length ) {
				
				uploaddingShowEnd();
				//alert("上传成功");
            	/*$("#uploadedFiles").empty();
            	deleteUploadFileAll();
            	forShowData();*/
			} else {
				times++;
			}
			
            if (info.status == 200)
            {
            	
                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success, object name:' + get_uploaded_object_name(file.name) + ' 回调服务器返回的内容是:' + info.response;
            }
            else if (info.status == 203)
            {
            	//alert("回调失败");
                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:' + info.response;
            }
            else
            {
            	//alert(info.response);
                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
            } 
            
		},

		Error: function(up, err) {
			
            if (err.code == -600) {
            	alert("文件太大了,单张图片不超过3M");
                //document.getElementById('console').appendChild(document.createTextNode("\n选择的文件太大了,可以根据应用情况，在upload.js 设置一下上传的最大大小"));
            }
            else if (err.code == -601) {
            	alert("文件后缀不对");
                //document.getElementById('console').appendChild(document.createTextNode("\n选择的文件后缀不对,可以根据应用情况，在upload.js进行设置可允许的上传文件类型"));
            }
            else if (err.code == -602) {
            	alert("文件已经上传过一遍了");
                //document.getElementById('console').appendChild(document.createTextNode("\n这个文件已经上传过一遍了"));
            }
            else 
            {
            	alert(err.response);
                //document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
            }
		}
	}
});

// 图片预览
function previewImage(file,callback){//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
	if(!file || !/image\//.test(file.type)) return; //确保文件是图片
	if(file.type=='image/gif'){//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
		var fr = new mOxie.FileReader();
		fr.onload = function(){
			callback(fr.result);
			fr.destroy();
			fr = null;
		}
		fr.readAsDataURL(file.getSource());
	}else{
		var preloader = new mOxie.Image();
		preloader.onload = function() {
			preloader.downsize( 300, 300 );//先压缩一下要预览的图片,宽300，高300
			var imgsrc = preloader.type=='image/jpeg' ? preloader.getAsDataURL('image/jpeg',80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
			callback && callback(imgsrc); //callback传入的参数为预览图片的url
			preloader.destroy();
			preloader = null;
		};
		preloader.load( file.getSource() );
	}	
}

// 图片删除
function deleteUploadFile(fileId){
	var selectedfile = uploader.getFile(fileId);
	uploader.removeFile(selectedfile);
	$("#pic_" + fileId).remove();
}

function deleteUploadFileAll()
{
	uploader.splice(0,uploader.files.length);
	$('#uploaderFiles').empty();
}

var id = "";
$(function (){
	$("#uploaderFiles").on("click", "li", function(){
		id = $(this).attr("picId");
        $('#galleryImg').attr("style", this.getAttribute("style"));
        $('#gallery').fadeIn(100);
    });
    
    $('#gallery').on("click", function(){
        $('#gallery').fadeOut(100);
    });

});

// 移除照片
function forRemvePic(){
		$('#iosDialog1').fadeIn(200);
}
// 确认移除照片
function comfirRemove(){
		$('#iosDialog1').fadeOut(200);
		deleteUploadFile(id);
		$("#picSize").text(uploader.files.length + "/7");
		
}

function getUserTypes(){
	var ss = null;
	$.ajax({
		type: "GET",
	    url: "../phototype/querUserType",
	    async: false,
	    dataType: "json",
	    success: function(r){
	    	ss = r;
	    }
	});
	
	return ss;
}

$(function(){
	//初始化文件上传对象
	uploader.init();
});

function uploaddingShowStart(){
	if ($('#loadingToast').css('display') != 'none') return;

	$('#loadingToast').fadeIn(100);
}

function uploaddingShowEnd(){
	if ($('#loadingToast').css('display') != 'none')
		$('#loadingToast').fadeOut(100);
	
	if ($('#toast').css('display') != 'none') return;
		$('#toast').fadeIn(100);
    setTimeout(function () {
    	$('#toast').fadeOut(100);
    }, 2000);
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


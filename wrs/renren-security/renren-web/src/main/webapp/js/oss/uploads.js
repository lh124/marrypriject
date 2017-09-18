
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

function send_request()
{
    var xmlhttp = null;
    if (window.XMLHttpRequest)
    {
        xmlhttp=new XMLHttpRequest();
    }
    else if (window.ActiveXObject)
    {
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
  
    if (xmlhttp!=null)
    {
        serverUrl = '../publicModule/common/oss/getSign'
        xmlhttp.open( "POST", serverUrl, false );
        var myFormDatea = getMyFormDate();
        xmlhttp.send(myFormDatea);
        return xmlhttp.responseText
    }
    else
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

function GetRandomNum(Min,Max)
{   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
} 

function get_signature()
{
    //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    now = timestamp = Date.parse(new Date()) / 1000; 
    if (expire < now + 3)
    {
        body = send_request()
        var obj = eval ("(" + body + ")");
        host = obj['host']
        policyBase64 = obj['policy']
        accessid = obj['accessid']
        signature = obj['signature']
        expire = parseInt(obj['expire'])
        callbackbody = obj['callback'] 
        key = obj['dir']
        return true;
    }
    return false;
};


function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
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

function set_upload_param(up, filename, ret)
{
    if (ret == false)
    {
        ret = get_signature()
    }
    g_object_name = key;
    if (filename != '') { suffix = get_suffix(filename)
        calculate_object_name(filename)
    }
    new_multipart_params = {
        'key' : g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid, 
        'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
        'callback' : callbackbody,
        'signature': signature,
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}


var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles', 
    //multi_selection: false,
	//container: document.getElementById('container'),
	flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
    url : 'http://oss.aliyuncs.com',

    filters: {
        mime_types : [ //只允许上传图片和zip文件
        { title : "Image files", extensions : "jpg,gif,png,bmp,jpeg" }, 
        { title : "Zip files", extensions : "zip,rar" }
        ],
        max_file_size : '10mb', //最大只能上传10mb的文件
        prevent_duplicates : true //不允许选取重复文件
    },

	init: {
		PostInit: function() {
			//document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
            set_upload_param(uploader, '', false);
            return false;
			};
		},

		FilesAdded: function(up, files) {
			
			
			for(var i = 0, len = files.length; i<len; i++){
				var file_name = files[i].name; //文件名
				var addElemet = '<div class="file-preview-frame" id="preview-'+files[i].id+'" data-fileindex="0" data-template="image">' +
				'<div class="kv-file-content">' +
				'<img id="pre_img_'+files[i].id+'" src="" class="kv-preview-data file-preview-image" title="' + file_name + '" alt="' + file_name + '" style="width:auto;height:160px;">' +
				'</div>' +
				'<div class="file-thumbnail-footer">' +
					'<div class="file-footer-caption" title="' + file_name + '">' + file_name + '<br><samp>'+plupload.formatSize(files[i].size)+'</samp></div>' +
					'<div class="file-thumb-progress hide">' +
						'<div class="progress">' +
							'<div id="upload_progess_'+files[i].id+'" class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:0%;"></div>' +	
						'</div>' +
					'</div> ' +
					'<div class="file-actions">' +
					'<div class="file-footer-buttons">' +
						'<button type="button" class="kv-file-upload btn btn-xs btn-default" title="上传文件"><i class="glyphicon glyphicon-upload text-info"></i></button>' +
						'<button type="button" onclick="deleteUploadFile(\''+files[i].id+'\')" class="kv-file-remove btn btn-xs btn-default" title="删除文件"><i class="glyphicon glyphicon-trash text-danger"></i></button>' +
						'<button type="button" class="kv-file-zoom btn btn-xs btn-default" title="查看详情"><i class="glyphicon glyphicon-zoom-in"></i></button>' +     
					'</div> ' +
					'<div class="file-upload-indicator" title="没有上传"><i class="glyphicon glyphicon-hand-down text-warning"></i></div>' +
					'<div class="clearfix"></div>' +
					'</div>' +
				'</div>' +
				'</div>';
				//构造html来更新UI
				//var html = '<li id="file-' + files[i].id +'"><p class="file-name">' + file_name + '</p><p class="progress"></p></li>';
				$(addElemet).appendTo('#uploadContainer');
				!function(i){
					previewImage(files[i],function(imgsrc){
						$('#pre_img_'+files[i].id).attr('src',imgsrc);
					})
			    }(i);
			}
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
			
			var progess = $('#upload_progess_' + file.id);
			progess.parent().parent().attr("class","file-thumb-progress");
			progess.text(file.percent + '%');
			progess.css("width", file.percent + '%');
			/*var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= 2*file.percent+'px';
			progBar.setAttribute('aria-valuenow', file.percent);*/
		},

		FileUploaded: function(up, file, info) {
			
			if (times == uploader.files.length ) {
				alert
				alert("上传成功");
            	$("#uploadedFiles").empty();
            	deleteUploadFileAll();
            	forShowData();
            	times = 1;
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
            	alert("文件太大了");
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
	$("#preview-" + fileId).remove();
}

function deleteUploadFileAll()
{
	uploader.splice(0,uploader.files.length);
	$('#uploadContainer').empty();
}

uploader.init();

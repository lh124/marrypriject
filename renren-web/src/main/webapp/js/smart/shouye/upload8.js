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
var myFormDate;
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
        serverUrl = '../../publicModule/common/oss/getSign'
        xmlhttp.open( "POST", serverUrl, false );
        //var myFormDate = getMyFormDate();
        xmlhttp.send(myFormDate);
        return xmlhttp.responseText
    }
    else
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

function check_object_radio() {
    var tt = document.getElementsByName('myradio');
    for (var i = 0; i < tt.length ; i++ )
    {
        if(tt[i].checked)
        {
            g_object_name_type = tt[i].value;
            break;
        }
    }
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

function random_string(len) {
	len = len || 32;
	var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';   
	var maxPos = chars.length;
	var pwd = '';
	for (i = 0; i < len; i++) {
	    pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

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
    g_object_name = key + Date.parse(new Date()) + suffix
    return ''
}

function getFile(getFile){
	uploader.addFile(getFile, getFile.value);
	myFormDate = myFormDateF(getFile);
	//alert(getFile.value);
	//myFormDate.append("type",$(getFile).attr("dataType"));
	set_upload_param(uploader, '', false);
	
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
    /*if (ret == false)
    {
        ret = get_signature()
    }*/
	//每次都重新获取签名
	ret = get_signature();
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
	//flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
	//silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
    url : 'http://oss.aliyuncs.com',

    filters: {
        mime_types : [ //只允许上传图片和zip文件
        { title : "Image files", extensions : "jpg,gif,png,bmp,jpeg,pptx,ppt,docx,pdf,dox" }, 
        { title : "Zip files", extensions : "zip,rar" },
        { title : "Mp3 files", extensions : "mp3" },
        { title : "Mp4 files", extensions : "mp4" }
        ],
        max_file_size : '1024mb', //最大只能上传10mb的文件
        prevent_duplicates : true //不允许选取重复文件
    },

	init: {
		PostInit: function() {
			/*document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
            set_upload_param(uploader, '', false);
            return false;
			};*/
		},

		FilesAdded: function(up, files) {
			/*plupload.each(files, function(file) {
				document.getElementById('progressShow').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
				+'</div>';
			});*/
		},

		BeforeUpload: function(up, file) {
            //check_object_radio();
            set_upload_param(up, file.name, true);
        },

		UploadProgress: function(up, file) {
			//var d = document.getElementById("progressShow");
			//d.innerHTML = '<span style="color:white;"><h1>已上传' + file.percent + "%</h1></span>";
			/*d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= 2*file.percent+'px';
			progBar.setAttribute('aria-valuenow', file.percent);*/
		},

		FileUploaded: function(up, file, info) {
			//hideModal();
            if (info.status == 200)
            {
            	window.location.href="./shouye_8.html?class="+document.getElementById("classId").value;
            }
            else if (info.status == 203)
            {
            	alert("上传到OSS成功，但是oss访问用户设置的上传回调服务器失败");
                //document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:' + info.response;
            }
            else
            {
            	window.location.href="./shouye_8.html?class="+document.getElementById("classId").value;
            } 
		},

		Error: function(up, err) {
            if (err.code == -600) {
                alert("选择的文件太大了");
            }else if (err.code == -601) {
            	alert("选择的文件后缀不对");
            }else if (err.code == -602) {
            	alert("上传中...");
            }
		}
	}
});
uploader.init();

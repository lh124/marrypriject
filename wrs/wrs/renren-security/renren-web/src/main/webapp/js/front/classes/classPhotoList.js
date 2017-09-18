    
	$(function(){
		$.ajax({
			type: "GET",
		    url: "../photopicclass/list?typeId=" + $('#typeId').val(),
		    dataType: "json",
		    success: function(r){
				if(r.status == 'ok'){
					if (r.list.length > 0) {
						var content = "";
						var container = $("#photoContainer");
						for(var i = 0; i < r.list.length; i++){
							content = '<img origile="' + r.url + r.list[i].name + '" '+
										'src="' + r.url + r.list[i].name + '" class="img-border img-max img-radius">'+
										'<br>';
							container.append(content);
						}
					}
				}
				
				addLoadEvent(getPicInfo);    //监听事件
			}
		});
	});

	function addLoadEvent(func){ 
      //将函数作为参数，此函数就是 onload 触发时需要执行的某个函数
          var oldonload=window.onload; 
          //将原来的 onload 的值赋给临时变量 oldonload。
          if(typeof window.onload!="function"){
          //判断 onload 的类型是否是 function。如果已经执行window.onload=function(){...} 赋值，那么此时 onload 的类型就是 function
          //否，则说明 onload 还没有被赋值，当前任务 func 为第一个加入的任务
              window.onload=func(); 
              
              //作为第一个任务，给 onload 赋值
          }else{ 
          //是，则说明 onload 已被赋值，onload 中先前已有任务加入
              window.onload=function(){
                  oldonload();
                  func(); 
                  //作为后续任务，追加到先前的任务后面
              };
          }
    }
    var imgs=new Array();
    var nowImgurl="";
    function getPicInfo()
    {
      var imgObj=document.getElementsByTagName('img');  //获取图文中所有的img标签对象
      
      for(var i=0; i<imgObj.length; i++)
      {
        imgs.push(imgObj[i].getAttribute("origile")); 
        nowImgurl = this.src;    //获取当前点击图片url
        //下面调用微信内置图片浏览组建
        imgObj[i].onclick=function(){
          WeixinJSBridge.invoke("imagePreview",{
            "urls":imgs,
            "current":this.src
            });
        };
      }
    }

$(function (){
	loadData();
});

function loadData(){
	// 加载数据
	$.ajax({
		type: "GET",
	    url: "../phototype/querOtherUserTypePic",
	    data: "id=" + $("#id").val(),
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				// 渲染界面
				setData(r);
				// 设置懒加载图片
				lazyImg();
			} else {
				dialog("请输入密码");
			}
		}
	});
}

// 弹框
function dialog(msg){
	  $.prompt(msg, "查看密码", function(text) {
		$.ajax({
			type: "GET",
		    url: "../phototype/querOtherUserTypePic",
		    data: "id=" + $("#id").val() + "&password=" + text,
		    dataType: "json",
		    success: function(r){
				if(r.status == 'ok'){
					// 渲染界面
					setData(r);
					// 设置懒加载图片
					lazyImg();
				} else {
					dialog(r.msg);
				}
		    }
		});
	}, function() {
		window.history.go(-1);
  		//取消操作
	});
}

// 渲染界面
function setData(json){
	var r = json;
	if ( r.user.nickname != null && r.user.nickname != "") {
		$('#nickname').text(r.user.nickname);
	}
	
	if ( r.user.headImg != null && r.user.headImg != "") {
		$('#headImg').attr('src', r.url + r.user.headImg);
	} else {
		$('#headImg').attr('src', r.url + 'static-sources/headDuo.jpg');
	}
	
	for(var i = 0; i < r.list.length; i++){
		
		var pic = "";
		if(r.list[i].userPicList.length > 0) {
			pic = r.url + r.list[i].userPicList[0].name;
		}
		
		var content = '<h4>' + r.list[i].title + '<h4>' + 
		  '<a href="./userPhotoList.html?id=' + r.list[i].id +'">' + 
		  '<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAArIAAAGFBAMAAADzwA07AAAAIVBMVEXr6+vPz8/X19fp6ene3t7k5OTm5ubh4eHT09Pb29vR0dHqLrSfAAACyklEQVR42uzBgQAAAACAoP2pF6kCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGB27GdHaSiO4vgJ5e+s5lzAFlZ2Y1yWoIm6AjPJbItG3VJdmLiiOg9Aow8AybinK1/Tey9ldDttl+eTFC6w+4b+2luRNi2XcIJl8v/H6quHhf9BHiXnFlbGE6wep/D8+gm8Dp8CnEAeJePOBz4n7XKssi254gFWzEkVUWVb0uEeVklz7rxS2ZYM/PnfZ8zEz4atyrYkYOjn63emAPI5VLYtxczHe+HnbWxc7K9fVLYFxwjAIur5eVuGQLcgn6lsczkTYDNDOQb6Nma/oJWqbGMLV/EYIg6BAffIaG5iTlW2sSFXQDFGblzDAwqTIIi5VdmmRrZbYI/F3FXeDbj3uQ8q29SA1/ZYYcgtFkzeMAXQ50llmwo4RYcpRlwhj5DN4RShyjZWTHA1Bwa233Fm435w4onKNnY0yAwQ8BrFFGuezVS2sc0c6xBAPA04Rswz8+9Z7VBl61lwG48BrCd+IEQ33i36DB8eNKpsHUP+KPcAMuMvYgYXnFzS71S2jhF/8+D/mq+YIotwURh4GVOVraPHXy4dOsznfrNb3XUh9kv3nqhsHQEjlw5dFsa97qudAnK/RI8GKltLTANfkyEQlFECfOMBGDFKAaw5Vtl61lW1wifMaW7fMkpcZJr7u+fkTmXr2VRbgqM/+3slrZ+wPtP7A5WtZ8FTVfgA63VhYyZw3tGaJSrbkuDTe1Re3t1/hIiIiIiIiIiIiIj8ZQ8OBAAAAACA/F8bQVVVVVVVVVVVVVVVVVVVhT04EAAAAAAA8n9tBFVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVpT04JAAAAAAQ9P+10RMAAAAAAAAAAAAAAADAArTtXKLPR7LcAAAAAElFTkSuQmCC" '+
		  'data-src="' + pic +'"  class="img-border img-max img-radius"></a>';
		$('#container').append(content);
	}
}

// 设置图片懒加载
function lazyImg(){
	var lazyloadImg = new LazyloadImg({
        el: '.page-hd [data-src]', //匹配元素
        top: 50, //元素在顶部伸出长度触发加载机制
        right: 50, //元素在右边伸出长度触发加载机制
        bottom: 50, //元素在底部伸出长度触发加载机制
        left: 50, //元素在左边伸出长度触发加载机制
        qriginal: false, // true，自动将图片剪切成默认图片的宽高；false显示图片真实宽高
        load: function(el) {
            el.style.cssText += '-webkit-animation: fadeIn 01s ease 0.2s 1 both;animation: fadeIn 1s ease 0.2s 1 both;';
        },
        error: function(el) {

        }
	});
}

//android
$(function(){
    var $androidActionSheet = $('#showInfo');

    $("#showAndroidActionSheet").on('click', function(){
    	alert("show");
    	$androidActionSheet.attr("class", "weui_dialog weui_dialog_visible");
        //$androidActionSheet.fadeIn(200);
    	$("#mask").attr("class", "weui_mask weui_mask_visible");
    	
    	$("#mask").on('click',function () {
        	$androidActionSheet.attr("class", "weui_dialog");
        	$("#mask").attr("class", "weui_mask");
        });
    });
});
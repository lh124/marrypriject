﻿$(function() { 
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    vipspa.start({  
        view: '.weui-tab__panel',// 装载视图的dom  
        router: {  
            '/home': {  
                templateUrl: 'indexUserCenter_photo.html',  
                controller: '../js/front/constitute/indexUserCenter_photo.js'  
            },  
            '/search': {  
                templateUrl: 'indexSearch.html',  
                controller: '../js/front/constitute/indexSearch.js'  
            },
            'defaults': '/home'// 不符合上述路由时，默认跳至  
        },  
        errorTemplateId: '#error'  
    });  
});
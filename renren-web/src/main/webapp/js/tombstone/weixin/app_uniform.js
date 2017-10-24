$(function() { 
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    vipspa.start({  
        view: '.weui-tab__panel',// 装载视图的dom  
        router: {  
        	'/shouye': {  
                templateUrl: 'shouye.html',  
                controller: '../../js/tombstone/weixin/index.js'  
            },
            '/home': {  
                templateUrl: 'mine.html',  
                controller: '../../js/tombstone/weixin/mine.js'  
            },
            'defaults': '/shouye'// 不符合上述路由时，默认跳至  
        },  
        errorTemplateId: '#error'  
    });  
});
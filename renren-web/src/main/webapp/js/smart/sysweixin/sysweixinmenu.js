$(function () {
    $("#jqGrid").jqGrid({
        url: '../sysweixinmenu/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '菜单名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '系统微信id', name: 'weixinid', index: 'weixinid', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        postData:{
        	"weixinid": $("#weixinid").val(),
        	"type":"1"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysWeixinMenu: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysWeixinMenu = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			vm.sysWeixinMenu.weixinid = $("#weixinid").val();
			vm.sysWeixinMenu.menutype = 1;
			var url = vm.sysWeixinMenu.id == null ? "../sysweixinmenu/save" : "../sysweixinmenu/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysWeixinMenu),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sysweixinmenu/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../sysweixinmenu/info/"+id, function(r){
                vm.sysWeixinMenu = r.sysWeixinMenu;
            });
		},
		addMenu: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="sysweixinmenuson.html?fatherid="+ id ;
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});
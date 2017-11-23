$(function () {
    $("#jqGrid").jqGrid({
        url: '../weixinfunction/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '功能名称', name: 'name', index: 'name', width: 80 },
			{ label: '微信端路径', name: 'pcurl', index: 'pcurl', width: 80 },
			{ label: '默认图标', name: 'pic', index: 'pic', width: 80,formatter :function(r){
				return '<img src="' + r + '" style="width:80px;height:80px;" />';
			} }
        ],
		viewrecords: true,
        height: 600,
        rowNum: 15,
		rowList : [15,30,50],
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
		weixinFunction: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.weixinFunction = {};
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
			var url = vm.weixinFunction.id == null ? "../weixinfunction/save" : "../weixinfunction/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.weixinFunction),
			    success: function(r){
			    	if(r.code === 0){
			    		document.getElementById("myUserId").value = r.id;
			    		if(document.getElementById("fileimg").value != null && document.getElementById("fileimg").value != ""){
			    			getFile(document.getElementById("fileimg"));
			    			vm.reload();
			    		}else{
			    			alert('操作成功', function(index){
								vm.reload();
							});
			    		}
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
				    url: "../weixinfunction/delete",
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
			$.get("../weixinfunction/info/"+id, function(r){
                vm.weixinFunction = r.weixinFunction;
            });
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
$(function () {
    $("#jqGrid").jqGrid({
        url: '../smartapp/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '版本号', name: 'edition', index: 'edition', width: 80 }, 			
			{ label: '类型', name: 'equipmentType', index: 'equipment_type', width: 80,formatter :function(r){
				 if(r == 11 ){
					 return '安卓学生端';
				 }else if(r == 12 ){
					 return '安卓老师端';
				 }else if(r == 21 ){
					 return '苹果学生端';
				 }else if(r == 22 ){
					 return '苹果老师端';
				 }
			} }, 			
			{ label: '是否强制更新', name: 'updateType', index: 'update_type', width: 80,formatter :function(r){
				 if(r == 1 ){
					 return '是';
				 }else{
					 return '否';
				 }
			} }, 			
			{ label: '下载路径', name: 'equipmentPath', index: 'equipment_path', width: 80 }			
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
		smartApp: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.smartApp = {};
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
			var url = vm.smartApp.id == null ? "../smartapp/save" : "../smartapp/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.smartApp),
			    success: function(r){
			    	if(r.code === 0){
			    		if(document.getElementById("fileimg").value != null && document.getElementById("fileimg").value != ""){
			    			document.getElementById("myUserId").value = r.id;
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
				    url: "../smartapp/delete",
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
			$.get("../smartapp/info/"+id, function(r){
                vm.smartApp = r.smartApp;
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
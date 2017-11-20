$(function () {
    $("#jqGrid").jqGrid({
        url: '../sysremarks/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '备注值', name: 'name', index: 'name', width: 180 }, 
			{ label: '是否解决', name: 'solveIf', index: 'solveIf', width: 80 ,formatter :function(r){
				 if(r == 1 ){
					 return '已解决';
				 }else if(r == 0 ){
					 return '<span style="color:#F00">未解决</span>';
				 }
			} }, 
			{ label: '时间', name: 'createtime', index: 'createTime', width: 80 }, 			
			{ label: '操作', name: 'id', index: 'passwordd', width: 80,formatter :function(r){
				 return '<button onclick="solveIf(' + r +')">解决</button>';
			} }			
        ],
		viewrecords: true,
        height: 400,
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

function solveIf(id){
	vm.sysRemarks.solveIf = 1;
	vm.sysRemarks.id = id;
	var url = "../sysremarks/update";
		$.ajax({
			type: "POST",
		    url: url,
		    data: JSON.stringify(vm.sysRemarks),
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
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysRemarks: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysRemarks = {};
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
			var url = vm.sysRemarks.id == null ? "../sysremarks/save" : "../sysremarks/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysRemarks),
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
				    url: "../sysremarks/delete",
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
			$.get("../sysremarks/info/"+id, function(r){
                vm.sysRemarks = r.sysRemarks;
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
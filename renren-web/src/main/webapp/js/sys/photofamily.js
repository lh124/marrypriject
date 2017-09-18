$(function () {
    $("#jqGrid").jqGrid({
        url: '../photofamily/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '家庭名字', name: 'name', index: 'name', width: 80 }, 			
			{ label: '家庭描述', name: 'info', index: 'info', width: 80 }, 			
			{ label: '背景音乐', name: 'music', index: 'music', width: 80 }, 			
			{ label: '视频', name: 'vedio', index: 'vedio', width: 80 }, 			
			{ label: '', name: 'gmtCreate', index: 'gmt_create', width: 80 }, 			
			{ label: '', name: 'gmtModified', index: 'gmt_modified', width: 80 }, 			
			{ label: '创建者id', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '使用状态', name: 'status', index: 'status', width: 80 }, 			
			{ label: '查看密码', name: 'password', index: 'password', width: 80 }			
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
		photoFamily: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.photoFamily = {};
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
			var url = vm.photoFamily.id == null ? "../photofamily/save" : "../photofamily/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.photoFamily),
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
				    url: "../photofamily/delete",
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
			$.get("../photofamily/info/"+id, function(r){
                vm.photoFamily = r.photoFamily;
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
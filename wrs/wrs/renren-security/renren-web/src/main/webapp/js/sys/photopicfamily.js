$(function () {
    $("#jqGrid").jqGrid({
        url: '../photopicfamily/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '图片名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '描述信息', name: 'info', index: 'info', width: 80 }, 			
			{ label: '图片分类', name: 'picType', index: 'pic_type', width: 80 }, 			
			{ label: '家庭id', name: 'familyId', index: 'family_id', width: 80 }, 			
			{ label: '图片排序', name: 'picOrder', index: 'pic_order', width: 80 }, 			
			{ label: '', name: 'gtmCreate', index: 'gtm_create', width: 80 }, 			
			{ label: '', name: 'gtmModified', index: 'gtm_modified', width: 80 }, 			
			{ label: '所属分类id', name: 'typeId', index: 'type_id', width: 80 }			
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
		photoPicFamily: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.photoPicFamily = {};
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
			var url = vm.photoPicFamily.id == null ? "../photopicfamily/save" : "../photopicfamily/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.photoPicFamily),
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
				    url: "../photopicfamily/delete",
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
			$.get("../photopicfamily/info/"+id, function(r){
                vm.photoPicFamily = r.photoPicFamily;
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
$(function () {
    $("#jqGrid").jqGrid({
        url: '../phototype/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '内容', name: 'content', index: 'content', width: 80 }, 			
			{ label: '', name: 'gtmCreate', index: 'gtm_create', width: 80 }, 			
			{ label: '', name: 'gtmModified', index: 'gtm_modified', width: 80 }, 			
			{ label: '分类', name: 'type', index: 'type', width: 80 }, 			
			{ label: '关联id', name: 'relatedId', index: 'related_id', width: 80 }, 			
			{ label: '创建者id', name: 'createrId', index: 'creater_id', width: 80 },
			{ label: '排序', name: 'typeOrder', index: 'type_order', width: 80 }
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
        postData:{'classId': $("#myId").val(),
        	"type": $("#myType").val()},
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
		photoType: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.photoType = {};
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
			
			var id = $("#myId").val();
			var type = $("#myType").val();
			var url = vm.photoType.id == null ? "../phototype/save" : "../phototype/update";
			
			if (vm.photoType.id == null) {
				vm.photoType.type = type;
				vm.photoType.relatedId = id;
			}
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.photoType),
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
				    url: "../phototype/delete",
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
			$.get("../phototype/info/"+id, function(r){
                vm.photoType = r.photoType;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{'classId': $("#myId").val(),
                	"type": $("#myType").val()},
            }).trigger("reloadGrid");
		},
		listClassPic: function (){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			
			window.location.href = "photoUploadClassPoto.html?typeId=" + id ;
		}
	}
});
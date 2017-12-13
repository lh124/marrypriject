function shipin(id){
	$('#shipin').modal('show');
	document.getElementById("myUserId").value = id;
	document.getElementById("type2").value = 2;
}

$(function () {
    $("#jqGrid").jqGrid({
        url: '../tombstoneuser/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '登录账号', name: 'name', index: 'name', width: 80 }, 			
			{ label: '昵称', name: 'nickname', index: 'nickname', width: 80 },
			{ label: '头像', name: 'pic', index: 'pic', width: 80 }, 			
			{ label: '时间', name: 'createtime', index: 'createtime', width: 80 },
			{ label: '操作', name: 'id', index: 'id', width: 80,formatter :function(r){
				 return '<button onclick="shipin(' + r +')">视频上传</button>';
			    }
			}
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
		tombstoneUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tombstoneUser = {};
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
			var url = vm.tombstoneUser.id == null ? "../tombstoneuser/save" : "../tombstoneuser/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tombstoneUser),
			    success: function(r){
			    	if(r.code === 0){
			    		document.getElementById("myUserId").value = r.id;
			    		document.getElementById("type2").value = 1;
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
				    url: "../tombstoneuser/delete",
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
		adddead: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="tombstonedead.html?userid="+ id;
		},
		getInfo: function(id){
			$.get("../tombstoneuser/info/"+id, function(r){
                vm.tombstoneUser = r.tombstoneUser;
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
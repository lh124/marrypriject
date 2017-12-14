$(function () {
    $("#jqGrid").jqGrid({
        url: '../../marrymain/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '标题', name: 'title', index: 'title', width: 80 }, 			
			{ label: '作者', name: 'author', index: 'author', width: 80 }, 
			{ label: '价格', name: 'price', index: 'price', width: 80 },
			{ label: '图片', name: 'pic', index: 'pic', width: 80,formatter :function(r){
				 if(r != null && r != ""){
					 return '<img src="' + r + '" style="width:80px;height:80px;" />';
				 }else{
					 return '';
				 }
			} },
			{ label: '内容说明', name: 'content', index: 'content', width: 80 }			
        ],
		viewrecords: true,
        height: 650,
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
		marryMain: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.marryMain = {};
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
			var url = vm.marryMain.id == null ? "../../marrymain/save" : "../../marrymain/update";
			
			if(vm.marryMain.id == null && (document.getElementById("fileimg").value == null || document.getElementById("fileimg").value == "")){
				alert("请选择需要上传的图片");
				return false;
			}
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.marryMain),
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
				    url: "../../marrymain/delete",
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
			$.get("../../marrymain/info/"+id, function(r){
                vm.marryMain = r.marryMain;
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
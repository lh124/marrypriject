$(function () {
    $("#jqGrid").jqGrid({
        url: '../../marrywedding/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '二维码下载', name: 'id', index: 'id', width: 80,formatter: function(value, options, row){
				return '<a href="../../marrywedding/dowloadClassQrCodemb?id='+ value+'">下载二维码</a>'; } 
			},
			{ label: '新郎姓名', name: 'groomname', index: 'groomName', width: 80 }, 			
			{ label: '新娘姓名', name: 'bridename', index: 'brideName', width: 80 }, 			
			{ label: '婚礼日期', name: 'weddingdate', index: 'weddingDate', width: 80 }, 			
			{ label: '婚礼地址', name: 'weddingaddress', index: 'weddingAddress', width: 80 }, 			
			{ label: '婚礼留言', name: 'content', index: 'content', width: 80 }			
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
		marryWedding: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.marryWedding = {};
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
			var url = vm.marryWedding.id == null ? "../../marrywedding/save" : "../../marrywedding/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.marryWedding),
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
				    url: "../../marrywedding/delete",
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
			$.get("../../marrywedding/info/"+id, function(r){
                vm.marryWedding = r.marryWedding;
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
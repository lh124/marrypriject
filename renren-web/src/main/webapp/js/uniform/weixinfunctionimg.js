$(function () {
    $("#jqGrid").jqGrid({
        url: '../weixinfunctionimg/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '功能名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '功能图标', name: 'pic', index: 'pic', width: 80,formatter :function(r){
				if(r == null || r == ""){
					return "";
				}else{
					return '<img src="' + r + '" style="width:80px;height:80px;" />';
				}
			} }, 			
			{ label: '操作', name: 'id', index: 'id', width: 80,formatter :function(r){
				 return '<button onclick="updateImage(' + r +')">图标修改</button>';
			  }
			}
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
        postData:{
        	"schoolId": $("#schoolId").val()
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function updateImage(id){
	$('#tubiao').modal('show');
	document.getElementById("functionId").value = id;
}

function saveorupdate(file){
	
	var url =  "../weixinfunctionimg/saveorupdate?functionId="+$("#functionId").val()+"&schoolId=" +$("#schoolId").val();
	$.ajax({
		type: "POST",
	    url: url,
	    success: function(r){
	    	document.getElementById("myUserId").value = r.id;
	    	getFile(file);
		}
	});
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		weixinFunctionImg: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.weixinFunctionImg = {};
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
			var url = vm.weixinFunctionImg.id == null ? "../weixinfunctionimg/save" : "../weixinfunctionimg/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.weixinFunctionImg),
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
				    url: "../weixinfunctionimg/delete",
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
			$.get("../weixinfunctionimg/info/"+id, function(r){
                vm.weixinFunctionImg = r.weixinFunctionImg;
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
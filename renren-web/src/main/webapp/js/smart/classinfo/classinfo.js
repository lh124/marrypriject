$(function () {
    $("#jqGrid").jqGrid({
        url: '../classinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '姓名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '职务(1为班主任,2科任老师,3班委)', name: 'type', index: 'type', width: 80 }, 			
			{ label: '班级id', name: 'classid', index: 'classid', width: 80 }			
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
        postData:{
        	"classid": $("#classId").val()
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});
document.getElementById("calssInfocontent").style.display = "none";
function getcontent(type){
	if(type == "1"){
		document.getElementById("calssInfocontent").style.display = "block";
	}else{
		document.getElementById("calssInfocontent").style.display = "none";
		document.getElementById("calssInfocontent").value = "";
	}
}
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		classInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.classInfo = {};
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
			vm.classInfo.classid = $("#classId").val();
			var url = vm.classInfo.id == null ? "../classinfo/save" : "../classinfo/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.classInfo),
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
				    url: "../classinfo/delete",
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
			$.get("../classinfo/info/"+id, function(r){
                vm.classInfo = r.classInfo;
                if(vm.classInfo.type == "1"){
                	document.getElementById("calssInfocontent").style.display = "block";
                }else{
                	document.getElementById("calssInfocontent").style.display = "none";
                }
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
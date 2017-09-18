$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/uniform/student/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '标识', name: 'studentCode', index: 'student_code', width: 80 }, 			
			{ label: '学号', name: 'studentNo', index: 'student_no', width: 80 }, 			
			{ label: '姓名', name: 'studentName', index: 'student_name', width: 80 }, 			
			{ label: '性别', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '类型', name: 'studentType', index: 'student_type', width: 80 }, 			
			{ label: '头像', name: 'pic', index: 'pic', width: 80 }, 			
			{ label: '班级id', name: 'classId', index: 'class_id', width: 80 }, 			
			{ label: '密码', name: 'passwordd', index: 'passwordd', width: 80 }			
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
        postData:{'classId': $("#classId").val()},
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
		student: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.student = {};
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
			var url = vm.student.id == null ? "../sys/uniform/student/save" : "../sys/uniform/student/update";
			vm.student.classId = $("#classId").val();
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.student),
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
		addByExcel: function () {
			
			var name  = $("#file").val();
			if (name == null || name == ""){
				alert("请选择Excel文件");
				return ;
			}
			if (name != null ){
				var fileExtension = name.substring(name.lastIndexOf('.') + 1);
				if (fileExtension != 'xlsx' && fileExtension != 'xls') {
					alert("请勿选择非Excel文件");
					return ;
				}
			}
			
			var formData = new FormData();
			formData.append("file",$("#file")[0].files[0]);
			formData.append("classId", $("#classId").val());
			$.ajax({
				url: '../sys/uniform/student/saveByExcel' ,  
		          type: 'POST',  
		          data: formData,  
		          async: false,  
		          cache: false,  
		          contentType: false,  
		          processData: false,
		          success: function(r){
			    	if(r.code === 0){
						alert(r.msg, function(index){
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
				    url: "../sys/uniform/student/delete",
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
			$.get("../sys/uniform/student/info/"+id, function(r){
                vm.student = r.student;
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
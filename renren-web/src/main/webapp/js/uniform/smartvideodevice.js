$(function () {
    $("#jqGrid").jqGrid({
        url: '../smartvideodevice/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '学校id', name: 'schoolId', index: 'school_id', width: 80 }, 			
			{ label: '设备验证码', name: 'verificationCode', index: 'verification_code', width: 80 }, 			
			{ label: '设备序列号', name: 'serialNumber', index: 'serial_number', width: 80 }, 	
			{ label: '设备通道', name: 'cameraNo', index: 'cameraNo', width: 80 },
			{ label: '是否允许老师查看', name: 'teacherSee', index: 'teacher_see', width: 80,formatter :function(r){
				 if(r == 1 ){
					 return '是';
				 }else{
					 return '否';
				 }
			} }, 			
			{ label: '是否允许学生查看', name: 'studentSee', index: 'student_see', width: 80,formatter :function(r){
				 if(r == 1 ){
					 return '是';
				 }else{
					 return '否';
				 }
			} }			
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
        	"schoolId": $("#schoolId").val()
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
		smartVideoDevice: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.smartVideoDevice = {};
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
			vm.smartVideoDevice.school_id = $("#schoolId").val();
			var url = vm.smartVideoDevice.id == null ? "../smartvideodevice/save" : "../smartvideodevice/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.smartVideoDevice),
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
				    url: "../smartvideodevice/delete",
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
			$.get("../smartvideodevice/info/"+id, function(r){
                vm.smartVideoDevice = r.smartVideoDevice;
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
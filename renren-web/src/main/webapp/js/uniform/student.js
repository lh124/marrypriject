$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/uniform/student/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 40, key: true },
			{ label: '标识', name: 'studentCode', index: 'student_code', width: 100 }, 			
			{ label: '学号', name: 'studentNo', index: 'student_no', width: 120 }, 			
			{ label: '姓名', name: 'studentName', index: 'student_name', width: 80 }, 			
			{ label: '性别', name: 'sex', index: 'sex', width: 80 },
			{ label: '类别', name: 'userType', index: 'sex', width: 80,formatter :function(r){
				 if(r == 1 ){
					 return '学生';
				 }else{
					 return '老师';
				 }
			} }, 
			{ label: '类型', name: 'studentType', index: 'student_type', width: 80 }, 			
			{ label: '头像', name: 'pic', index: 'pic', width: 80,formatter :function(r){
				 if(r != null && r != ""){
					 return '<img src="' + r + '" style="width:80px;height:80px;" />';
				 }else{
					 return '';
				 }
			} }, 			
			{ label: '班级id', name: 'classId', index: 'class_id', width: 80 }, 			
			{ label: '操作', name: 'id', index: 'passwordd', width: 80,formatter :function(r){
				 return '<button onclick="showimage(' + r +')">头像上传</button><br><button onclick="bindEpc(' + r +')">EPC绑定</button><br><button onclick="getallclass(' + r + ')">班级修改</button>';
			} }
        ],
		viewrecords: true,
        height: 600,
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
        postData:{'classId': $("#classId").val(),'userType':1,'schoolId':null},
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function updatepassword(){
	confirm('确定要初始化密码？', function(){
		var url =  "../sys/uniform/student/update";
		$.ajax({
			type: "POST",
		    url: url,
		    data: JSON.stringify(vm.student),
		    success: function(r){
		    	if(r.code === 0){
		    		alert("密码初始化成功", function(index){
						vm.reload();
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}

function getallclass(id){
	$('#studentidupdate').val(id);
	$('#updateclassId').modal('show');
	var classid = $("#classId").val();
	$.ajax({
		type: "POST",
	    url: "../sys/uniform/class/getschoolallclass?id="+classid,
	    data: JSON.stringify(vm.student),
	    success: function(r){
	    	if(r.code === 0){
				var content = "";
				$('#classidupdates').html(content);
				for(var i = 0; i < r.classList.length; i++){
					if( r.classList[i].id == $('#classId').val()){
						content += '<option value="'+r.classList[i].id+'" selected="selected">'+r.classList[i].className+'</option>';
					}else{
						content += '<option value="'+r.classList[i].id+'">'+r.classList[i].className+'</option>';
					}
				}
				$('#classidupdates').html(content);
			}else{
				alert(r.msg);
			}
		}
	});
}

function uplaodclassidsd(){
	var url =  "../sys/uniform/student/updateclassid?id="+$('#studentidupdate').val()+"&classid="+$('#classidupdates').val();
	$.ajax({
		type: "POST",
	    url: url,
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
}

function uplaod(){
	var url = vm.student.id == null ? "../sys/uniform/student/save" : "../sys/uniform/student/update";
	vm.student.classId = $("#classId").val();
	vm.student.userType = 1;
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
}

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
		updatepw:function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
            vm.student.id = id;
            vm.student.passwordd = "000000";
            updatepassword();
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
			vm.student.userType = 1;
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.student),
			    success: function(r){
			    	if(r.code === 0){
			    		alert(r.fail, function(index){
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
				if(r.student.type == "1"){
					document.getElementById("studenttype").style.display = "block";
				}
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
document.getElementById("studentEpc").style.display = "none";
document.getElementById("studentimage").style.display = "none";
document.getElementById("updateclassId").style.display = "none";
function showimage(id){
	$('#studentimage').modal('show');
	document.getElementById("myUserId").value = id;
	vm.getInfo(id);
}
function bindEpc(id){
	$('#studentEpc').modal('show');
	document.getElementById("studentEpcuse").value = "";
	document.getElementById("epcuserid").value = id;
	var url =  "../sys/uniform/student/getstudentepc?id="+id;
	$.ajax({
		type: "POST",
	    url: url,
	    success: function(r){
	    	if(r.code === 0){
	    		if(r.epc != null){
	    			document.getElementById("yinchangepc").style.display = "block";
	    			var epc = r.epc.split(",");
	    			var content = "";
	    			$('#bindepc').html(content);
	    			for(var i = 0; i < epc.length; i++){
	    				if(epc[i] != null && epc[i] != ""){
	    					content += epc[i] + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteepc(\''+epc[i]+ '\',' + id +')">删除</a><br/>';
	    				}
	    			}
	    			console.log(content);
	    			$('#bindepc').html(content);
	    		}else{
	    			document.getElementById("yinchangepc").style.display = "none";
	    		}
			}else{
				alert(r.msg);
			}
		}
	});
}

function deleteepc(epc,id){
	var url = "http://wrs.gykjewm.com/smart/datainpution/getData?key={type:'deleteepc',epc:'" + epc +"',token:'bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a'}";
	$.ajax({
		type: "POST",
	    url: url,
	    success: function(r){
	    	if(r.code === 0){
	    		alert('删除成功', function(index){
	    			bindEpc(id);
					vm.reload();
				});
			}else{
				alert(r.msg);
			}
		}
	});
}

function uplaod(){
	$('#studentimage').modal('hide');
	var url =  "../sys/uniform/student/update";
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
}
function gettype(type){
	if(type == "1"){
		document.getElementById("studenttype").style.display = "block";
	}else{
		document.getElementById("studenttype").style.display = "none";
		document.getElementById("studenttype").value = "";
	}
}
function saveEpc(){
	var epc = document.getElementById("studentEpcuse").value;
	if(epc == null || epc == ""){
		alert("EPC不能为空");
		return;
	}else{
		var id = document.getElementById("epcuserid").value;
		$('#studentimage').modal('hide');
		var url =  "../sys/uniform/student/saveepc?epc="+epc+"&id="+document.getElementById("epcuserid").value;
		$.ajax({
			type: "POST",
		    url: url,
		    success: function(r){
		    	if(r.code === 0){
		    		if(r.faile != null){
		    			alert(r.faile);
		    		}else{
		    			alert('操作成功', function(index){
		    				document.getElementById("studentEpcuse").value = "";
							vm.reload();
						});
		    		}
				}else{
					alert(r.msg);
				}
			}
		});
	}
}
// 变量声明
var userId = 0;

$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/uniform/student/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '标识', name: 'studentCode', index: 'student_code', width: 80 }, 			
			{ label: '学号', name: 'studentNo', index: 'student_no', width: 80 }, 			
			{ label: '姓名', name: 'studentName', index: 'student_name', width: 80 }, 			
			{ label: '性别', name: 'sex', index: 'sex', width: 80 }			
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
        postData:{'classId': $("#myClassId").val()},
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
		photoFrontUser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.photoFrontUser = {};
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
			var url = vm.photoFrontUser.id == null ? "../photofrontuser/save" : "../photofrontuser/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.photoFrontUser),
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
				    url: "../photofrontuser/delete",
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
			$.get("../photofrontuser/info/"+id, function(r){
                vm.photoFrontUser = r.photoFrontUser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData:{'classId': $("#myClassId").val()}
            }).trigger("reloadGrid");
		},
		queryUserExamination: function(){
			var ids = getSelectedRows();
			if(ids.length > 1){
				alert("请勿多选");
				return ;
			}
			
			var id = getSelectedRow();
			userId = id;
			getScore(id);
			
		}
	}
});

function getScore(userId){
	$.ajax({
		type: "GET",
	    url: "../photoscore/list?limit=100&page=1&sidx=&order=asc" + "&userId=" + userId+ "&examinationId=" +  $('#examinationId').val(),
	    datatype: "json",
	    success: function(r){
			if(r.code == 0){
				if (r.page.list.length > 0) {
					var htmlBody = '';
					for(var i = 0; i < r.page.list.length; i++){
						htmlBody += '<tr>'+
										'<td>' + r.page.list[i].id + '</td>'+
										'<td>' + r.page.list[i].subjectName + '</td>'+
										'<td><input class="form-control" name="score" type="text" value="' + r.page.list[i].subjectPoint +'" /></td>'+
										'<td><input class="form-control" name="comment" type="text" value="' + r.page.list[i].teacherComment +'" /></td>'+
										'<td> <a href="javascript:void(0);" onclick="update(this)">修改</a></td>'+
									'</tr>';
					}
					
					$('#bodyContainer').html(htmlBody);
					$('#myModalStudent').modal('show');
				} else {
					allSubject();
				}
			}else{
				alert(r.msg);
			}
		}
	});
}

function allSubject(){
	$.ajax({
		type: "GET",
	    url: "../photosubject/list?limit=100&page=1&sidx=&order=asc",
	    datatype: "json",
	    success: function(r){
			if(r.code == 0){
				if (r.page.list.length > 0) {
					var htmlBody = '';
					for(var i = 0; i < r.page.list.length; i++){
						htmlBody += '<tr>'+
										'<td>' + r.page.list[i].id + '</td>'+
										'<td>' + r.page.list[i].name + '</td>'+
										'<td><input class="form-control" name="score" type="text" /></td>'+
										'<td><input class="form-control" name="comment" type="text" /></td>'+
										'<td></td>'+
									'</tr>';
					}
					
					$('#bodyContainer').html(htmlBody);
					$('#myModalStudent').modal('show');
				} else {
					
				}
			}else{
				alert(r.msg);
			}
		}
	});
}

function forSubmit(){
	var dataJson = new Array();
	var smartRanking = new Object();
	smartRanking.fractionTotal = 0;
	smartRanking.subjectTotal = length;
	smartRanking.userId = userId;
	smartRanking.classId = $("#myClassId").val();
	smartRanking.examinationId = $("#examinationId").val();
	var length = 0;
	for(var i = 0; i < $('#bodyContainer tr').length; i++){
		var id = $('#bodyContainer tr:eq(' + i+ ')').find('td:eq(0)').text();
		var name = $('#bodyContainer tr:eq(' + i+ ')').find('td:eq(1)').text();
		var point = $('#bodyContainer tr:eq(' + i+ ')').find('td:eq(2) input').val();
		var comment = $('#bodyContainer tr:eq(' + i+ ')').find('td:eq(3) input').val();
		if(point != null && point != "" && point != "null"){
			length++;
			smartRanking.fractionTotal += parseFloat(point);
			var data = {};
			data.subjectId = id;//科目id
			data.subjectName = name;//科目名称
			data.subjectPoint = point;//分数
			data.teacherComment = comment;//老师评语
			data.frontUserId = userId;//学生id
			data.examinationId = $('#examinationId').val();//考试主题id
			dataJson[i] = data;
		}
	}
	smartRanking.subjectTotal = length;
	$.ajax({
		type: "POST",
	    url: "../smartranking/fractionTotalLast?gradeId="+$("#gradeId").val()+"&userId="+userId+"&examinationId="+$('#examinationId').val(),
	    datatype: "json",
	    data: JSON.stringify(smartRanking),
	    success: function(r){
			if(r.code == 0){
				smartRanking.fractionTotalLast = r.fractionTotalLast;
				$.ajax({
					type: "POST",
				    url: "../smartranking/save",
				    datatype: "json",
				    data: JSON.stringify(smartRanking),
				    success: function(r){
						if(r.code == 0){
							$.ajax({
								type: "POST",
							    url: "../photoscore/saveBatch",
							    datatype: "json",
							    data: JSON.stringify(dataJson),
							    success: function(r){
									if(r.code == 0){
										alert('添加成功');
									}else{
										alert(r.msg);
									}
								}
							});
						}
					}
				});
			}
		}
	});
}

function update(obj){
	
	var par = $(obj).parent().parent();
	var id = par.find('td:eq(0)').text();
	var point = par.find('td:eq(2) input').val();
	var comment = par.find('td:eq(3) input').val();
	
	var data = {};
	data.id = id;
	data.subjectPoint = point;
	data.teacherComment = comment;
	$.ajax({
		type: "POST",
	    url: "../photoscore/update",
	    datatype: "json",
	    data: JSON.stringify(data),
	    success: function(r){
			if(r.code == 0){
				alert('修改成功');
			}else{
				alert(r.msg);
			}
		}
	});
}
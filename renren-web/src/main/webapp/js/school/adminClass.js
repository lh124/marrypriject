	$(function () {
	    $("#jqGrid").jqGrid({
	        url: './photoclass/adminList',
	        datatype: "json",
	        colModel: [			
				{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
				{ label: '班级名', name: 'name', index: 'name', width: 80 }, 			
				{ label: '查看权限', name: 'perm', index: 'perm', width: 80 },
				{ label: '系统功能角色', name: 'classify', index: 'classify', width: 80 },
				{ label: '班级状态', name: 'status', index: 'status', width: 80 },
				//{ label: '班级描述', name: 'classDesc', index: 'class_desc', width: 80 }, 			
				{ label: '背景音乐', name: 'music', index: 'music', width: 80 }, 			
				{ label: '班级logo', name: 'logo', index: 'logo', width: 80 }, 			
				{ label: '创建时间', name: 'gmtCreate', index: 'gmt_create', width: 80 }, 			
				{ label: '修改时间', name: 'gmtModified', index: 'gmt_modified', width: 80 }, 			
				{ label: '毕业时间', name: 'graduationTimeId', index: 'graduation_time_id', width: 80 }, 			
				{ label: '所属学校', name: 'schoolId', index: 'school_id', width: 80 }, 			
				{ label: '所属学院', name: 'collegeId', index: 'college_id', width: 80 },
				{ label: '班级视频', name: 'video', index: 'video', width: 80 }
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
	    
	    $.ajax({
			type: "GET",
		    url: "../photograduationtime/list?limit=100&page=1&sidx=&order=asc",
		    dataType: "json",
		    success: function(r){
				if(r.status == 'ok'){
					$("#myGraduateTime").empty();
					$("#graduationTimeId").empty();
					$("#graduationTimeId").append('<option  >--毕业年级--</option>');
					for(i = 0; i < r.page.list.length ; i++){
						if (i==0) {
							$("#graduationTimeId").append('<option  value="'+ r.page.list[i].id +'">' +r.page.list[i].graduationName +'</option>');
							$("#myGraduateTime").append('<option selected="selected" value="'+ r.page.list[i].id +'">' +r.page.list[i].graduationName +'</option>');
						} else {
							$("#graduationTimeId").append('<option value="'+ r.page.list[i].id +'">' +r.page.list[i].graduationName +'</option>');
							$("#myGraduateTime").append('<option value="'+ r.page.list[i].id +'">' +r.page.list[i].graduationName +'</option>');
						}
					}
				}else{
					alert(r.msg);
				}
			}
		});
	    
	 // 注册事件
	//selectTogether('province', 'city', 'school', 'college');
	//selectTogether('myprovince', 'mycity', 'mySchoolId', 'myCollegeId');
	}); 
	
	var vm = new Vue({
		el:'#rrapp',
		data:{
			showList: true,
			title: null,
			photoClass: {}
		},
		methods: {
			query: function () {
				vm.reload();
			},
			add: function(){
				vm.showList = false;
				vm.title = "新增";
				vm.photoClass = {};
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
				var url = vm.photoClass.id == null ? "./photoclass/uniformSave" : "./photoclass/update";
				$.ajax({
					type: "POST",
				    url: url,
				    data: JSON.stringify(vm.photoClass),
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
					    url: "./photoclass/delete",
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
				$.get("./photoclass/info/"+id, function(r){
	                vm.photoClass = r.photoClass;
	            });
			},
			reload: function (event) {
				vm.showList = true;
				var page = $("#jqGrid").jqGrid('getGridParam','page');
				$("#jqGrid").jqGrid('setGridParam',{ 
	                page:page
	            }).trigger("reloadGrid");
			},
			myquery: function (event) {
				vm.showList = true;
				var page = $("#jqGrid").jqGrid('getGridParam','page');
				
				var postDataa = {};
				
				if ( !isNaN($("#school").val())) {
					postDataa.schoolId = $("#school").val();
					
				}
				
				if ( !isNaN($("#college").val())) {
					postDataa.collegeId = $("#college").val();
					
				} 
				
				if ( !isNaN($("#graduationTimeId").val())) {
					postDataa.graduationTimeId = $("#graduationTimeId").val();
					
				}
				
				$("#jqGrid").jqGrid('setGridParam',{ 
	                page:page,
	                postData : postDataa
	            }).trigger("reloadGrid");
			},
			addClassMember: function(){
				var ids = getSelectedRows();
				if (ids.length >1) {
					alert("请勿多选");
					return ;
				}
				var id = getSelectedRow();
				
				window.location.href = "photofrontuserUniform.html?classId=" + id;
				return ;
				
			},
			addClassPhotoType: function(){
				var ids = getSelectedRows();
				if (ids.length >1) {
					alert("请勿多选");
					return ;
				}
				var id = getSelectedRow();
				
				window.location.href = "phototype.html?id=" + id + "&type=1";
				return ;
				
			},
			testTheme: function(){
				var ids = getSelectedRows();
				if (ids.length >1) {
					alert("请勿多选");
					return ;
				}
				var id = getSelectedRow();
				
				window.location.href = "../school/photoexamination.html?classId=" + id;
				return ;
			}
		}
	});
$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/photoschool/listAdminSchool',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'school.id', index: 'id', width: 50, key: true },
			{ label: '学校名', name: 'school.schoolName', index: 'school_name', width: 80 }, 			
			{ label: 'pw', name: 'school.pw', index: 'pw', width: 80 },
			{ label: '所属城市id', name: 'school.cityId', index: 'city_id', width: 80 }
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
 // 获取省
    $.ajax({
		type: "GET",
	    url: "../publicModule/province/list?limit=100&page=1&sidx=&order=asc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				$("#myprovince").empty();
				$("#myprovince").append('<option >---省---</option>');
				for(i = 0; i < r.page.list.length ; i++){
					$("#myprovince").append('<option value="'+ r.page.list[i].provinceid +'">' +r.page.list[i].province +'</option>')
				}
			}else{
				alert(r.msg);
			}
		}
	});
    
    // 注册事件，联动
    $("#myprovince").change(function(){
    	
    	
    	$.ajax({
    		type: "GET",
    	    url: "../publicModule/city/list",
    	    data:"provinceId=" + $("#myprovince").val() + "&limit=100&page=1&sidx=&order=asc",
    	    dataType: "json",
    	    success: function(r){
    	    	$("#mycity").empty();
    	    	$("#mycity").append('<option >---市---</option>');
				for(i = 0; i < r.list.length ; i++){
					$("#mycity").append('<option value="'+ r.list[i].cityid +'">' +r.list[i].city +'</option>')
				}
    		}
    	});
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		school: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.school = {};
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
			var url = vm.school.id == null ? "../sys/uniform/school/save" : "../sys/uniform/school/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.school),
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
				    url: "../sys/uniform/school/delete",
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
		addClass: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="class.html?schoolId="+ id;
		},
		smartvideodevice: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="smartvideodevice.html?schoolId="+ id;
		},
		smartteacher: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="smartteacher.html?schoolId="+ id;
		},
		weixinfunctionimg: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="weixinfunctionimg.html?schoolId="+ id;
		},
		downloadheadimg: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="../downloadheadimg/main?schoolId="+ id;
		},
		weixinbangdinglist: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="tongji.html?schoolId="+ id;
		},
		addActivitie: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="smartactivities.html?schoolId="+ id;
		},
		addPsychologicalcounseling: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="psychologicalcounseling.html?schoolId="+ id;
		},
		addFreshmanGuide: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="freshmanguide.html?schoolId="+ id;
		},
		addSchoolNotice: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="schoolnotice.html?schoolId="+ id;
		},
		getInfo: function(id){
			$.get("../sys/uniform/school/info/"+id, function(r){
                vm.school = r.school;
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
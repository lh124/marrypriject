$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/photoschool/listAdminSchoolphoto',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '学校名', name: 'school.name', index: 'name', width: 80 }
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
        postData:{'adminId': $("#adminId").val()},
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
				    url: "../sysphotoadminschool/delete",
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
		manageSchool: function (event) {
			$('#myModal').modal('show');
			
			selectTogether("province", "city", "school");
			
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
function selectTogether(privincId, cityId, shoolId){
	// 获取省
    $.ajax({
		type: "GET",
	    url: "../publicModule/province/list?limit=100&page=1&sidx=&order=asc",
	    dataType: "json",
	    success: function(r){
			if(r.status == 'ok'){
				var result = "<option>--省--</option>";
				//$("#myprovince").empty();
				for(i = 0; i < r.page.list.length ; i++){
					result += '<option value="'+ r.page.list[i].provinceid +'">' +r.page.list[i].province +'</option>'
					
				}
				
				$("#" + privincId).html(result);
			}else{
				alert(r.msg);
			}
		}
	});
    
    // 注册事件，联动,修改市
    $("#" + privincId).change(function(){
    	$.ajax({
    		type: "GET",
    	    url: "../publicModule/city/list",
    	    data:"provinceId=" + $("#" + privincId).val() + "&limit=100&page=1&sidx=&order=asc",
    	    dataType: "json",
    	    success: function(r){
    	    	var result = "<option >--市--</option>";
    	    	
				for(i = 0; i < r.list.length ; i++){
					result += '<option value="'+ r.list[i].cityid +'">' +r.list[i].city +'</option>'
				}
				$("#" + cityId).html(result);
    		}
    	});
    });
    
 // 注册事件，联动,修改学校
    $("#" + cityId).change(function(){
    	$.ajax({
    		type: "GET",
    	    url: "../sys/uniform/school/photolist",
    	    data:"cityId=" + $("#" + cityId).val() + "&order=asc&page=1&limit=300&sidx=",
    	    dataType: "json",
    	    success: function(r){
    	    	
    	    	var result = "<option>--学校--</option>";
    	    	
    	    	if(r.status == 'ok'){
    	    		if (r.page.list.length > 0) {
	    	    		for (var i = 0; i < r.page.list.length; i++) {
	    	    			result += '<option value="'+ r.page.list[i].id +'">' +r.page.list[i].name +'</option>';
	    	    		}
	    	    		
	    	    		$("#" + shoolId).html(result);
	    	    	}else{
	    	    		$("#" + shoolId).html('<option >--该地区无学校--</option>');
	    	    	}
    	    	}else{
    	    		alert(r.msg);
    	    	}
    		}
    	});
    });
    
}

function doManageSchool(){
	var para = "?schoolId=" + $("#school").val() + "&adminId=" + $("#adminId").val();
	confirm('确定提交', function(){
		$.ajax({
			type: "GET",
		    url: "../sys/sysadminschool/savephoto" + para,
		    success: function(r){
				if(r.code == 0){
					alert('操作成功', function(index){
						$('#myModal').modal('hide');
					});
					$("#jqGrid").trigger("reloadGrid");
				}else{
					alert(r.msg);
				}
			}
		});
	});
}
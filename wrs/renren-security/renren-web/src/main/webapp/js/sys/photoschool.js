$(function () {
    $("#jqGrid").jqGrid({
        url: 'photoschool/list',
        mtype : "get",
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '学校名', name: 'name', index: 'name', width: 80 },
			{ label: '下载学校二维码', name: 'id', index: 'name', width: 80, formatter: function(value, options, row){
				return '<a href="./photoschool/dowloadSchoolQrCode?schoolId='+ value+'">下载二维码</a>'; } 
			},
			{ label: '学校类型，1为中学，2为大学', name: 'schoolType', index: 'school_type', width: 80 }, 			
			{ label: '背景音乐', name: 'music', index: 'music', width: 80 }, 			
			{ label: '视频', name: 'vedio', index: 'vedio', width: 80 }, 			
			//{ label: '学校描述', name: 'schoolDesc', index: 'school_desc', width: 80 }, 			
			{ label: '', name: 'createrId', index: 'creater_id', width: 80 }, 			
			{ label: '', name: 'gmtCreate', index: 'gmt_create', width: 80 }, 			
			{ label: '', name: 'gmtModefied', index: 'gmt_modefied', width: 80 }, 			
			{ label: 'logo', name: 'logo', index: 'logo', width: 80 }, 			
			{ label: '省份', name: 'provinceId', index: 'province_id', width: 80 }, 			
			{ label: '城市', name: 'cityId', index: 'city_id', width: 80 }
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
		photoSchool: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.photoSchool = {};
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
			var url = vm.photoSchool.id == null ? "photoschool/save" : "photoschool/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.photoSchool),
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
				    url: "./photoschool/delete",
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
			$.get("photoschool/info/"+id, function(r){
                vm.photoSchool = r.photoSchool;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page,
                postData : {
                    schoolName: $("#mySchoolName").val()
                }
            }).trigger("reloadGrid");
		},
		addCollege: function (event){
			var ids = getSelectedRows();
			if(ids.length > 1){
				alert("只能选择一个大学");
				return ;
			}
			
			var id = getSelectedRow();
			var rowData = $("#jqGrid").jqGrid('getRowData',id);
			//alert(rowData.name);
			
			
			if(rowData.schoolType == 2){
				window.location.href = "photocollege.html?id=" + id;
			}else{
				alert("只有大学才能添加学院");
				return ;
			}
		},
		addClassPhotoType: function(){
			var ids = getSelectedRows();
			if (ids.length >1) {
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			
			window.location.href = "phototype.html?id=" + id + "&type=4";
			return ;
			
		}
	}
	
	
});

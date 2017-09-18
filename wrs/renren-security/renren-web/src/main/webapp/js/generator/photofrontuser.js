$(function () {
    $("#jqGrid").jqGrid({
        url: '../photofrontuser/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '账号', name: 'account', index: 'account', width: 80 }, 			
			{ label: '手机号码', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '用户状态', name: 'status', index: 'status', width: 80 }, 			
			{ label: '', name: 'gmtCreate', index: 'gmt_create', width: 80 }, 			
			{ label: '', name: 'gmtModified', index: 'gmt_modified', width: 80 }, 			
			{ label: '头像', name: 'headImg', index: 'head_img', width: 80 }, 			
			{ label: '昵称', name: 'nickname', index: 'nickname', width: 80 }, 			
			{ label: '用户性别', name: 'sex', index: 'sex', width: 80 }			
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
			$.ajax({
				url: '../photofrontuser/saveByExcel' ,  
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
		}
		,
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
		relatedClass: function (event) {
			
			
			var ids = getSelectedRows();
			var id = getSelectedRows();
			if(ids.length >1){
				alert("请请勿多选");
				return ;
			}
			
			$('#myModalStudent').modal('show');
			$('#userId').val(id);
			
			//获取班级
			$.ajax({
				type: "GET",
			    url: "./photoclass/theSameShchoolclasses",
			    data: "classId=" + $("#myClassId").val(),
			    success: function(r){
					if(r.code == 0){
						if (r.list.length > 0) {
							
							var result = "<option>--请选择班级--</option>";
							
							for (var i = 0; i < r.list.length; i++) {
								result += '<option value="' + r.list[i].id + '">' + r.list[i].name + '</option>';
							}
							
							$("#slClassId").html(result);
						}
					}else{
						alert(r.msg);
					}
				}
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
		}
	}
});

function doRelatedClass(){
	var para = "?classId=" + $("#slClassId").val() + "&userId=" + $("#userId").val()+ "&roleId=" + $("#slRoleId").val();
	confirm('确定提交', function(){
		$.ajax({
			type: "GET",
		    url: "../photofrontuser/saveMoreRelation" + para,
		    success: function(r){
				if(r.code == 0){
					alert('操作成功', function(index){
						$('#myModalStudent').modal('hide');
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}
$(function () {
    $("#jqGrid").jqGrid({
        url: '../tombstonedead/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '二维码下载', name: 'userid', index: 'userid', width: 80,formatter: function(value, options, row){
				return '<a href="../tombstonedead/dowloadClassQrCodemb?classId='+ value+'">下载二维码</a>'; } 
			},
			{ label: '死者姓名(第一代)', name: 'name', index: 'name', width: 80 },
			{ label: '拼音', name: 'account', index: 'account', width: 80 },
			{ label: '头像', name: 'image', index: 'image', width: 80,formatter :function(r){
				 if(r != null && r != ""){
					 return '<img src="' + r + '" style="width:80px;height:80px;" />';
				 }else{
					 return '';
				 }
			} },
			{ label: '生卒年', name: 'birthdayanddeath', index: 'birthdayanddeath', width: 80 }, 			
			{ label: '个人简介', name: 'content', index: 'content', width: 80 }, 			
			{ label: '创建时间', name: 'createtime', index: 'createtime', width: 80 }			
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
        postData:{
        	"userid": $("#userid").val() ,
        	"type":"1"
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
		tombstoneDead: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			$("#experience").val("");
			$("#content").val("");
			vm.showList = false;
			vm.title = "新增";
			vm.tombstoneDead = {};
		},
		addpersonimage: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="tombstonepic.html?userid="+$("#userid").val();
		},
		adddead: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="tombstonedead2.html?parentid="+ id + "&userid="+$("#userid").val();
		},
		update: function (event) {
			$("#experience").val("");
			$("#content").val("");
			$.ajax({
				type: "POST",
			    url: "../tombstonedead/getperson?userid="+$("#userid").val(),
			    dataType: "json",
			    success: function(result){
					if(result.status == 'ok'){
						$("#experience").val(result.tbuser.experience);
						$("#content").val(result.tbuser.content);
					}
				}
			});
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.tombstoneDead.id == null ? "../tombstonedead/save" : "../tombstonedead/update";
			vm.tombstoneDead.userid =$("#userid").val();
			vm.tombstoneDead.parentid = 0;
			vm.tombstoneDead.usertype = -1;
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.tombstoneDead),
			    success: function(r){
			    	if(r.code === 0){
			    		document.getElementById("myUserId").value = r.id;
			    		if(document.getElementById("fileimg").value != null && document.getElementById("fileimg").value != ""){
			    			getFile(document.getElementById("fileimg"));
			    			vm.reload();
			    		}else{
			    			alert('操作成功', function(index){
								vm.reload();
							});
			    		}
			    		vm.reload();
					}else{
						alert(r.msg);
					}
				}
			});
			$.ajax({
				type: "POST",
			    url: "../tombstonedead/saveorupdate?experience="+$("#experience").val()+"&userid="+$("#userid").val()+"&content="+$("#content").val(),
			    dataType: "json",
			    success: function(result){
					if(result.status == 'ok'){
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
				    url: "../tombstonedead/delete",
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
			$.get("../tombstonedead/info/"+id, function(r){
                vm.tombstoneDead = r.tombstoneDead;
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
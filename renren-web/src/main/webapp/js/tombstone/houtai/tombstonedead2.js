$(function () {
    $("#jqGrid").jqGrid({
        url: '../tombstonedead/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '姓名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '头像', name: 'image', index: 'image', width: 80 }, 			
			{ label: '关系', name: 'relation', index: 'relation', width: 80 }, 			
			{ label: '创建时间', name: 'createtime', index: 'createtime', width: 80 }			
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
        	"userid": "parentid="+$("#parentid").val()
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function init(){
    $.ajax({
		type: "POST",
	    url: "../tombstone/getrelationship",
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    	    var content = "";
	    	    $("#relation").html(content);
	    		for(var i = 0; i < result.list.length; i++){
	    			content += '<option value="'+result.list[i].name+'">'+result.list[i].name+'</option>';
	    		}
	    		$("#relation").html(content);
			}
		}
	});
}

function getdata(){
	$.ajax({
		type: "POST",
	    url: "../tombstonedead/getlist?parentid="+$("#parentid").val(),
	    dataType: "json",
	    success: function(result){
	    	if(result.status == 'ok'){
	    	    var content = '<option value="-1">请选择</option>';
	    	    $("#fuqirelation").html(content);
	    		for(var i = 0; i < result.page.length; i++){
	    			content += '<option value="'+result.page[i].id+'">'+result.page[i].name+'</option>';
	    		}
	    		$("#fuqirelation").html(content);
			}
		}
	});
}

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
			getdata();
			vm.showList = false;
			vm.title = "新增";
			vm.tombstoneDead = {};
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
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			getdata();
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			vm.tombstoneDead.parentid = $("#parentid").val();
			vm.tombstoneDead.userid =$("#userid").val();
			var url = vm.tombstoneDead.id == null ? "../tombstonedead/save" : "../tombstonedead/update";
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
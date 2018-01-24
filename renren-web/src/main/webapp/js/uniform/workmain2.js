$(function () {
    $("#jqGrid").jqGrid({
        url: '../appInterface/list',
        datatype: "json",
        colModel: [			
			{ label: '提交人员', name: 'userName', index: 'user_name', width: 80 },
			{ label: '图片', name: 'img', index: 'img', width: 80 }, 			
			{ label: '任务详情', name: 'content', index: 'content', width: 80 }, 			
			{ label: '当前状态', name: 'states', index: 'states', width: 80,formatter :function(r){
				 if(r == 0 ){
					 return '<span style="color:red;">未完成</span>';
				 }else if(r == 1 ){
					 return '已完成';
				 }
			} }, 	
			{ label: '备注说明', name: 'beizhuContent', index: 'beizhuContent', width: 80 },
			{ label: '任务开始时间', name: 'gmtModifiedtime', index: 'gmt_modifiedtime', width: 80 }, 			
			{ label: '任务结束时间', name: 'endTime', index: 'end_time', width: 80 }, 			
			{ label: '预计花费时间', name: 'estimateTime', index: 'estimate_time', width: 80 },
			{ label: '处理状态', name: 'handleStates', index: 'handleStates', width: 80,formatter :function(r){
				 if(r == 0 ){
					 return '<span style="color:red;">不合格</span>';
				 }else if(r == 1 ){
					 return '<span style="color:green;">合格</span>';
				 }else if(r == 2 ){
					 return '<span style="color:blue;">待处理</span>';
				 }
			} },
			{ label: '处理意见', name: 'handleContent', index: 'handleContent', width: 80 },
			{ label: '操作', name: 'id', index: 'id', width: 80,formatter :function(r){
				 return '<button onclick="updatestates(' + r +')">点击完成任务</button>';
			}  }
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
        postData:{'fatherId': $("#fatherId").val()},
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});
function updatestates(id){
	$("#workId").val(id);
	$("#iosDialog2").fadeIn(200);
}

function updateworkstates(){
	var id = $("#workId").val();
	confirm('确定提交？', function(){
		$.ajax({
			type: "POST",
		    url: "../appInterface/updatestates?id="+id+"&states="+$("#states").val()+"&beizhuContent="+$("#beizhuContent").val(),
		    data: JSON.stringify(vm.workMain),
		    success: function(r){
		    	if(r.code === 0){
		    		$("#iosDialog2").fadeOut(200);
		    		alert('操作成功', function(index){
						vm.reload();
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}
function closeck(){
	$("#iosDialog1").fadeOut(200);
	$("#iosDialog2").fadeOut(200);
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		workMain: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.workMain = {};
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
			vm.workMain.fatherId = $("#fatherId").val();
			var url = vm.workMain.id == null ? "../appInterface/save" : "../appInterface/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.workMain),
			    success: function(r){
			    	if(r.code === 0){
			    		if(document.getElementById("fileimg").value != null && document.getElementById("fileimg").value != ""){
			    			document.getElementById("myUserId").value = r.id;
				    		getFile(document.getElementById("fileimg"));
			    			vm.reload();
			    		}else{
			    			alert('操作成功', function(index){
								vm.reload();
							});
			    		}
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
				    url: "../appInterface/delete",
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
			$.get("../appInterface/info/"+id, function(r){
				if(r.workMain.states == 1){
					vm.showList = true;
					alert("无法修改已完成的任务");
					return false;
				}
                vm.workMain = r.workMain;
            });
		},
		addsondetail: function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			window.location.href="workmain2.html?id="+ id;
		},
		updatehandlestates:function(id){
			var ids = getSelectedRows();
			if(ids.length >1){
				alert("请勿多选");
				return ;
			}
			var id = getSelectedRow();
			$("#mainId").val(id);
			$("#iosDialog1").fadeIn(200);
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
$(function () {
	document.getElementById("weixinpic").style.display = "none";
    $("#jqGrid").jqGrid({
        url: '../businesscard/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '二维码下载', name: 'id', index: 'id', width: 80,formatter: function(value, options, row){
				return '<a href="../businesscard/dowloadClassQrCodemp?classId='+ value+'">下载二维码</a>'; } 
			},
			{ label: '姓名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '头像', name: 'pic', index: 'pic', width: 80,formatter :function(r){
				var pic = "";
				if (r == null || r == "") {
			        pic = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/business_card_pic/%5D_YEAN74E%5DC5QYA%7DEI%7BQBG5.png";
			    } else {
			        pic = r;
			    }
				 return '<img src="'+pic+'" style="width:100px;height:100px;" />';
			} }, 			
			{ label: '职务(中文)', name: 'position', index: 'position', width: 80 }, 
			{ label: '职务(英文)', name: 'positionenglish', index: 'positionenglish', width: 80 },
			{ label: '电话号码', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '个人QQ号', name: 'personqq', index: 'personqq', width: 80 },
			{ label: '个人微信二维码', name: 'weixinpic', index: 'weixinpic', width: 80,formatter: function(r){
					if(r != null && r!=""){
						return '<img src="'+r+'" style="width:100px;height:100px;" />';
					}else{
						return '暂无';
					}
			    } 
			 },
			 { label: '排序', name: 'ordercard', index: 'ordercard', width: 80 },
			{ label: '操作', name: 'id', index: 'id', width: 80,formatter: function(value, options, row){
					return '<button onclick="updateweixinpic(' + value +')">个人微信二维码上传</button>';
				} 
			}
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
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function updateweixinpic(id){
	document.getElementById("myUserId").value = id;
	document.getElementById("pictype").value = 2;
	$('#weixinpic').modal('show');
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		businessCard: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.businessCard = {};
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
			var url = vm.businessCard.id == null ? "../businesscard/save" : "../businesscard/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.businessCard),
			    success: function(r){
			    	if(r.code === 0){
			    		document.getElementById("myUserId").value = r.id;
			    		document.getElementById("pictype").value = 1;
			    		if(document.getElementById("fileimg").value != null && document.getElementById("fileimg").value != ""){
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
				    url: "../businesscard/delete",
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
			$.get("../businesscard/info/"+id, function(r){
                vm.businessCard = r.businessCard;
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
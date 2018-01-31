$(function () {
    $("#jqGrid").jqGrid({
        url: '../smartranking/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '学生名字', name: 'name', index: 'name', width: 80 }, 	
			{ label: '学生头像', name: 'pic', index: 'pic', width: 80,formatter :function(r){
				 if(r != null && r != ""){
					 return '<img src="' + r + '" style="width:80px;height:80px;" />';
				 }else{
					 return '';
				 }
			} },
			{ label: '总分', name: 'fractionTotal', index: 'fraction_total', width: 80 }, 			
			{ label: '科目数', name: 'subjectTotal', index: 'subject_total', width: 80,formatter :function(r){
				return '共考试'+r+'科';
			}  }, 			
			{ label: '平均分', name: 'fractionAverage', index: 'fraction_average', width: 80 }, 			
			{ label: '本次年级排名', name: 'gradeRanking', index: 'grade_ranking', width: 80 }, 	
			{ label: '本次班级排名', name: 'classRanking', index: 'class_ranking', width: 80 },
			{ label: '上次年级排名', name: 'gradeRankingLast', index: 'grade_ranking_last', width: 80 },
			{ label: '上次班级排名', name: 'classRankingLast', index: 'class_ranking_last', width: 80 }
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
        postData:{'classId': $("#myClassId").val(),'examinationId': $("#examinationId").val(),'gradeId':$("#gradeId").val()},
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
		smartRanking: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.smartRanking = {};
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
			var url = vm.smartRanking.id == null ? "../smartranking/save" : "../smartranking/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.smartRanking),
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
				    url: "../smartranking/delete",
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
			$.get("../smartranking/info/"+id, function(r){
                vm.smartRanking = r.smartRanking;
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
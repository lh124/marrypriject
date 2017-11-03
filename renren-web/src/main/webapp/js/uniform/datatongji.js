$(function () {
    
});
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		classe: {}
	},
	methods: {
		query: function () {
			vm.reload();
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
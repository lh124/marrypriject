function hunlihudong(){
        $.ajax({
				type: "POST",
			    url: "../weixin/me/allMeSign",
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				         if(result.list.length == 0){
				             alert("暂无");
				         }else if(result.list.length == 1){
				             window.location.href = "interact.html?weddingId="+result.list[0].id;
				         }else{
				             window.location.href = "smqdlist.html";
				         }
				     }
				}
		 });
}

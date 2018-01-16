  function init(){
          $.ajax({
				type: "POST",
			    url: "../weixin/me/allMeSign",
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				         var content = "";
				         var con = "";
				         for(var i = 0; i < result.list.length; i++){
				             con += '<a class="weui-cell weui-cell_access js_item" data-id="input" href="interact.html?weddingId='+result.list[i].id+'" style="background:#fdf7f7;margin-top:0.3rem;">'+
				                    '<div class="weui-cell__bd send">'+
				                    '<img style="display:block;width:1.2rem;height:1.2rem;border-radius:50%;" src="'+result.list[i].photo+'">'+
				                    '<p style="margin-top:0.28rem;text-indent:20px;">'+result.list[i].groomname+'&'+result.list[i].bridename+'</p>'+
				                    '</div><div class="weui-cell__ft"></div></a>';
				         }
				         $("#list").html(con);
				     }
				}
		 });
    }
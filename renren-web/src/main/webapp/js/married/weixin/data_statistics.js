	function init(){
        $.ajax({
				type: "POST",
			    url: "../weixin/me/allSign",
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				          $("#smtotal").html(result.list.smtj.length+"人");
				          $("#zftotal").html(result.list.zftotal+"条");
				          $("#videototal").html(result.list.videototal+"个");
				          $("#moneytotal").html(result.list.moneytotal.length+"个");
				          var content = 0;
				          for(var i=0; i <result.list.moneytotal.length; i++){
				              content += parseFloat(result.list.moneytotal[i].content);
				          }
				          $("#content").html("￥"+content+"元");
				          getDate(result.list.personwd,result.list.smtj.length);
				     }
				}
		 });
    }
	
	function tixian(){
		window.location.href="get_money.html";
	}
   function init(){
        $.ajax({
				type: "POST",
			    url: "../weixin/me/allattendawedding",
				dataType: "json",
				success: function(result){
				     if(result.status == 'ok'){
				          var content0 = "";
				          var content1 = "";
				          var content2 = "";
				          var content3 = "";
				          for(var i = 0; i < result.data.list0.length;i++){
				              content0 += '<dl><dt><img src="'+result.data.list0[i].pic+'"></dt>'+
				                          '<dd>'+result.data.list0[i].nickname+'</dd></dl>';
				          }
				          for(var i = 0; i < result.data.list1.length;i++){
				              content1 += '<dl><dt><img src="'+result.data.list1[i].pic+'"></dt>'+
				                          '<dd>'+result.data.list1[i].nickname+'</dd></dl>';
				          }
				          for(var i = 0; i < result.data.list3.length;i++){
				              content3 += '<dl><dt><img src="'+result.data.list3[i].pic+'"></dt>'+
				                          '<dd>'+result.data.list3[i].nickname+'</dd></dl>';
				          }
				          $("#list0").html(content0);
				          $("#list1").html(content1);
				          $("#list3").html(content3);
				          $("#total0").html("暂定<br/>"+result.data.list0.length);
				          $("#total1").html("接受<br/>"+result.data.list1.length);
				          $("#total3").html("全部<br/>"+result.data.list3.length);
				     }
				}
		 });
    }
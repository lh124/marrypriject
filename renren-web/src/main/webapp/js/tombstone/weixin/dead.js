var data="";
var data1= "";
function fadeouts(){
	$("#iosDialog2").fadeOut(200);
	$("#iosDialog22").fadeOut(200);
}

function saveliuyan(){
	document.getElementById("messagecontent").value = "";
	$("#iosDialog1").fadeIn(200);
}

function save(){
	var content = document.getElementById("messagecontent").value;
	if(content == null || content == ""){
		$("#iosDialog1").fadeOut(200);
		$("#iosDialog22").fadeIn(200);
	}else{
		$.ajax({
			type: "POST",
		    url: "../savemessage?id="+document.getElementById("userid").value+"&content="+document.getElementById("messagecontent").value,
		    dataType: "json",
		    success: function(result){
				if(result.status == 'ok'){
					init();
					$("#iosDialog1").fadeOut(200);
				}
		    }
		});
	}
}

function init(){
	$.ajax({
		type: "POST",
	    url: "../geterweima?id="+document.getElementById("userid").value,
	    dataType: "json",
	    success: function(result){
			if(result.status == 'ok'){
				var content = "";
				var messages = "";
				var length = result.list.img.length;
				if(length > 3){
					length = 3;
				}
				for(var i = 0; i < length; i++){
					content += '<img style="flex:1;margin-left:0.1rem;" src="'+ result.list.img[i].pic +'"/>';
				}
				$("#content").html(result.list.user.content);
				$("#experience").html(result.list.user.experience);
				$("#userimage").html(content);
				if(result.list.user.shipin == null || result.list.user.shipin == ""){
				}else{
					var filename = result.list.user.shipin;
					$('#videomp4').prop('src',filename);
					$('#videowebm').prop('src',filename);
					$('#videoogv').prop('src',filename);
					document.getElementById("my-video").load();
					$('#pathfile').val(filename);
				}
				if(result.list.deaduser != null){
					$("#deadname").html(result.list.deaduser.name);
					$("#deadbirdth").html(result.list.deaduser.birthdayanddeath);
					$("#deadcontent").html(result.list.deaduser.content);
					$("#deadimg").html('<img src="'+ result.list.deaduser.image +'"/>');
				}
				$("#liuyan").html(messages);
				for(var i = 0; i < result.list.messagelist.length; i++){
					messages += '<div>'+result.list.messagelist[i].createtime +'<br><p class="m-t-10">'+result.list.messagelist[i].content+'</p></div><br>';
				}
				$("#liuyan").html(messages);
				data = result.list.dead;
				data1 = result.list.dead;
				getdeaddata();
			}
		}
	});
}

function totop(){
	var speed=200;//滑动的速度
    $('body,html').animate({ scrollTop: 0 }, speed);
}

function getdeaddata(){
	$.ajax({
		type: "POST",
	    url: "../getdeaddata?id="+document.getElementById("userid").value + "&type=1",
	    dataType: "json",
	    success: function(result){
			data = result.list;
			data1 = result.list;
			/*获取后代*/
			var t=0,t1=0,_top=0,lena=0,len1=0,lena1=0,mt=0,mt1=0,grandson=0;
			var a=data.children;
			if(a.length > 0){
				$(".one").append('<div class="aa img1" title="'+data.id+'" align="'+data.parentid+'"><img src="'+data.image+'"/><p>'+data.name+'</p></div>');
			}
			for(var j=0;j< a.length;j++){
				var b=a[j].zn;
				var len2=0,len3=0,len2a=0,len3a=0;
				len2=a[j].fm.length;
				for(var m=0;m<a[j].zn.length;m++){
					len3+=a[j].zn[m].fm.length;
				}
				if(j>0){
					len2a=a[j-1].fm.length;
					for(var m=0;m<a[j-1].zn.length;m++){
						len3a+=a[j-1].zn[m].fm.length;
					}
					if(len2a>len3a){
						lena1=len2a;
					}else{
						lena1=len3a;
					}
				}
				_top+=lena1*3.1;
				if(len2>len3||len2==len3){
					lena=len2;
					t=_top;
					mt=0;
					mt1=len3*3.0/2;
					t1=lena*3.0/2+_top;
				}else{
					lena=len3;
					mt=len2*3.0/2;
					mt1=0;
					t=lena*3.0/2+_top;
					t1=_top;
				}
				grandson+=lena;
				var fm='',node='';
				for(var k = 0;k<a[j].fm.length;k++){
					var pic = "";
					if(a[j].fm[k].image == null || a[j].fm[k].image == ""){
						pic = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstone_dead_pic/1.png";
					}else{
						pic = a[j].fm[k].image;
					}
					fm+='<img title="'+a[j].fm[k].id+'" src="'+pic+'"/><p>'+a[j].fm[k].name+'</p><p>'+a[j].fm[k].relation+'<p/>';
				}
				if(fm){
					node='<div style="top:'+t+'rem;margin-top:-'+mt+'rem;" class="aa img2" title="'+a[j].id+'" align="'+a[j].parentid+'">'+fm+'</div>';
				}else{
					node="";
				}
				$(".two").append(node);
				for(var i=0;i<b.length;i++){
					var zn = "",node1="";
					var aa=0;
					if(i>0){
						aa=b[i-1].fm.length;
					}
					if(aa>1){
						t1=t1+aa*3.1;
					}else{
						t1=t1+aa*3.2;
					}
					for(var k = 0;k<b[i].fm.length;k++){
						var pic = "";
						if(b[i].fm[k].image == null || b[i].fm[k].image == ""){
							pic = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstone_dead_pic/1.png";
						}else{
							pic = b[i].fm[k].image;
						}
						zn+='<img title="'+b[i].fm[k].id+'" src="'+pic+'"/><p>'+b[i].fm[k].name+'</p><p>'+b[i].fm[k].relation+'<p/>';
					}
					if(zn){
						node1='<div class="aa img3" title="'+b[i].id+'" align="'+b[i].parentid+'"style="top:'+t1+'rem;margin-top:-'+mt1+'rem;">'+zn+'</div></div>';
					}else{
						node1='';
					}
					$(".three").append(node1);
				}
			}
		
			var h=0,h1=0;
			h=grandson*3.1+"rem";
			h1=grandson*3.1*75;
			var w=$(".one").width();
			$(".hh").height(h);
			getcanvas();
		}
	});
}

function getcanvas(){
	/*第一个canvas*/
	var w=document.getElementById("one").offsetWidth;
	var h=document.getElementById("one").offsetHeight;
	document.getElementById("canvas").width = w;  
	document.getElementById("canvas").height = h; 
	var canvas=document.getElementById("canvas");
	var ctx=canvas.getContext("2d");
	//绘制线段(直角三角形)
	var img2_top=zuobiao();
	ctx.beginPath();
	for(var i=0;i<img2_top.length;i++){
		ctx.moveTo(0,h/2);
		var a=parseFloat(img2_top[i]);
		var b=parseFloat($('.hh .img2').eq(i).css('height').substring(0,$('.hh .img2').eq(i).css('height').length-2)*0.5);
		var c=parseFloat(a+b);
		ctx.lineTo(w,c);
	}
	ctx.closePath();
	ctx.stroke();	
	/*第二个canvas*/
	document.getElementById("canvas1").width = w;  
	document.getElementById("canvas1").height = h; 
	var canvas1=document.getElementById("canvas1");
	var ctx1=canvas1.getContext("2d");
	//绘制线段(直角三角形)
	var img2=$('.hh .img2');
	var img3=$('.hh .img3');
	ctx1.beginPath();
	for(var i=0;i<img2.length;i++){
		for(var j=0;j<img3.length;j++){
			if($('.hh .img2').eq(i).attr("title")==$('.hh .img3').eq(j).attr("align")){
				var a=parseFloat(img2_top[i]);
				var b=parseFloat($('.hh .img2').eq(i).css('height').substring(0,$('.hh .img2').eq(i).css('height').length-2)*0.5);
				var c=parseFloat(a+b);
				var ctx1_hh=parseFloat($('.hh .img3').eq(j).css('top').substring(0,$('.hh .img3').eq(j).css('top').length-2));
				var ctx1_hei=parseFloat($('.hh .img3').eq(j).css('height').substring(0,$('.hh .img3').eq(j).css('height').length-2)*0.5);
				var ctx1_hh1=parseFloat($('.hh .img3').eq(j).css('margin-top').substring(0,$('.hh .img3').eq(j).css('margin-top').length-2));
				var ss=parseFloat(ctx1_hh+ctx1_hh1+ctx1_hei);
				ctx1.moveTo(0,c);
				ctx1.lineTo(w,ss);
			}
		}
	}
	ctx1.closePath();
	ctx1.stroke();		

	/*点击事件  弹框*/
	$(".hh").find('img').on("click",function(){
		var id=$(this).attr('title');
		replay(id);
		function replay(id){
			var node='<h3 style="border-bottom:1px solid #d0cece;height:1.0rem;"><button type="button" class="floatRight btn">关闭</button></h3><div class="hhs"><div id="ones" class="ones fl"></div><div class="fl"><canvas id="canvas3" style="width:100%;height:100%;"></canvas></div><div class="twos fl"></div><div class="fl"><canvas id="canvas4" style="width:100%;height:100%;"></canvas></div><div class="threes fl"></div></div>';
			$('#relation').append(node);
			/*函数*/
			$.ajax({
				type: "POST",
			    url: "../getdeaddata?id="+id,
			    dataType: "json",
			    success: function(result){
			    	if(result.list.children != null && result.list.children != ""){
			    		$(".zhezao").show();
			    		$('#relation').show();
			    		tupian(result.list);
			    		var w1=document.getElementById("ones").offsetWidth;
						var h1=document.getElementById("ones").offsetHeight;
						document.getElementById("canvas3").width = w1;  
						document.getElementById("canvas3").height = h1; 
						var canvas3=document.getElementById("canvas3");
						var ctx3=canvas3.getContext("2d");
						//绘制线段(直角三角形)
						var img2_top=[];
						var img2=$('.hhs .img2');
						for(var i=0;i<img2.length;i++){
							var s=parseFloat($('.hhs .img2').eq(i).css('top').substring(0,$('.hhs .img2').eq(i).css('top').length-2));
							var s1=parseFloat($('.hhs .img2').eq(i).css('margin-top').substring(0,$('.hhs .img2').eq(i).css('margin-top').length-2));
							var ss=parseFloat(s+s1);
							img2_top.push(ss);
						}
						var img2=$('.hhs .img2');
						ctx3.beginPath();
						for(var i=0;i<img2_top.length;i++){
							var a=parseFloat(img2_top[i]);
							var b=parseFloat($('.hhs .img2').eq(i).css('height').substring(0,$('.hhs .img2').eq(i).css('height').length-2)*0.5);
							var c=parseFloat(a+b);
							ctx3.moveTo(0,h1/2);
							ctx3.lineTo(w1,c);
						}
						ctx3.closePath();
						ctx3.stroke();	

						/*canvas*/
						document.getElementById("canvas4").width = w1;  
						document.getElementById("canvas4").height = h1; 
						var canvas4=document.getElementById("canvas4");
						var ctx4=canvas4.getContext("2d");
						//绘制线段(直角三角形)
						var imgs2=$('.hhs .img2');
						var imgs3=$('.hhs .img3');
						ctx4.beginPath();
						for(var i=0;i<img2.length;i++){
							for(var j=0;j<img3.length;j++){
								if($('.hhs .img2').eq(i).attr("title")==$('.hhs .img3').eq(j).attr("align")){
									var a=parseFloat(img2_top[i]);
									var b=parseFloat($('.hhs .img2').eq(i).css('height').substring(0,$('.hhs .img2').eq(i).css('height').length-2)*0.5);
									var c=parseFloat(a+b);
									var ctx1_hh=parseFloat($('.hhs .img3').eq(j).css('top').substring(0,$('.hhs .img3').eq(j).css('top').length-2));
									var ctx1_hei=parseFloat($('.hhs .img3').eq(j).css('height').substring(0,$('.hhs .img3').eq(j).css('height').length-2)*0.5);
									var ctx1_hh1=parseFloat($('.hhs .img3').eq(j).css('margin-top').substring(0,$('.hhs .img3').eq(j).css('margin-top').length-2));
									var ss=parseFloat(ctx1_hh+ctx1_hh1+ctx1_hei);
									ctx4.moveTo(0,c);
									ctx4.lineTo(w1,ss);
								}
							}
						}
						ctx4.closePath();
						ctx4.stroke();	
						$("#relation .aa").on("click",function(){
							var id=$(this).attr('title');
							$("#relation").html("");
							replay(id);
						});
						$(".btn").on("click",function(){
							$(".zhezao").hide();
							$("#relation").html("");
							$("#relation").hide();
						});
			    	}else{
			    		$("#iosDialog2").fadeIn(200);
			    		$(".zhezao").hide();
						$("#relation").html("");
						$("#relation").hide();
			    	}
			    	
			    }
			});
		}
		
		/* 关闭弹框*/
		$(".btn").on("click",function(){
			$(".zhezao").hide();
			$("#relation").html("");
			$("#relation").hide();
		});
	})
	
	/*点击事件  关闭弹框*/
	$(".zhezao").on("click",function(){
		$(this).hide();
		$("#relation").html("");
		$("#relation").hide();
	});
	/*重新生成canvas*/
	/*获取坐标*/
	function zuobiao(){
		var img2_top=[];
		var img2=$('.hh .img2');
		for(var i=0;i<img2.length;i++){
			var s=parseFloat($('.hh .img2').eq(i).css('top').substring(0,$('.hh .img2').eq(i).css('top').length-2));
			var s1=parseFloat($('.hh .img2').eq(i).css('margin-top').substring(0,$('.hh .img2').eq(i).css('margin-top').length-2));
			var ss=parseFloat(s+s1);
			img2_top.push(ss);
		}
		return img2_top;
	}

	/*获取坐标*/
	function zuobiaos(){
		var img2_top=[];
		var img2=$('.hhs .img2');
		for(var i=0;i<img2.length;i++){
			var s=parseFloat($('.hhs .img2').eq(i).css('top').substring(0,$('.hhs .img2').eq(i).css('top').length-2));
			var s1=parseFloat($('.hhs .img2').eq(i).css('margin-top').substring(0,$('.hhs .img2').eq(i).css('margin-top').length-2));
			var ss=parseFloat(s+s1);
			img2_top.push(ss);
		}
		return img2_top;
	}
	/*重新生成后代*/
	function tupian(data){
		/*获取后代*/
		var t=0,t1=0,_top=0,lena=0,len1=0,lena1=0,mt=0,mt1=0,grandson=0;
		var a=data.children;
		if(a.length > 0){
			$(".ones").append('<div class="aa img1" title="'+data.id+'" align="'+data.parentid+'"><img src="'+data.image+'"/><p>'+data.name+'</p></div>');
		}
		for(var j=0;j<a.length;j++){
			var b=a[j].zn;
			var len2=0,len3=0,len2a=0,len3a=0;
			len2=a[j].fm.length;
			for(var m=0;m<a[j].zn.length;m++){
				len3+=a[j].zn[m].fm.length;
			}
			if(j>0){
				len2a=a[j-1].fm.length;
				for(var m=0;m<a[j-1].zn.length;m++){
					len3a+=a[j-1].zn[m].fm.length;
				}
				if(len2a>len3a){
					lena1=len2a;
				}else{
					lena1=len3a;
				}
			}
			_top+=lena1*3.1;
			if(len2>len3||len2==len3){
				lena=len2;
				t=_top;
				mt=0;
				mt1=len3*3.0/2;
				t1=lena*3.0/2+_top;
			}else{
				lena=len3;
				mt=len2*3.0/2;
				mt1=0;
				t=lena*3.0/2+_top;
				t1=_top;
			}
			grandson+=lena;
			var fm='',node='';
			for(var k = 0;k<a[j].fm.length;k++){
				var pic = "";
				if(a[j].fm[k].image == null || a[j].fm[k].image == ""){
					pic = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstone_dead_pic/1.png";
				}else{
					pic = a[j].fm[k].image;
				}
				fm+='<img title="'+a[j].fm[k].id+'" src="'+pic+'"/><p>'+a[j].fm[k].name+'</p><p>'+a[j].fm[k].relation+'<p/>';
			}
			if(fm){
				node='<div style="top:'+t+'rem;margin-top:-'+mt+'rem;" class="aa img2" title="'+a[j].id+'" align="'+a[j].parentid+'">'+fm+'</div>';
			}else{
				node="";
			}
			$(".twos").append(node);
			for(var i=0;i<b.length;i++){
				var zn = "",node1='';
				var aa=0;
				if(i>0){
					aa=b[i-1].fm.length;
				}
				if(aa>1){
					t1=t1+aa*3.1;
				}else{
					t1=t1+aa*3.2;
				}
				for(var k = 0;k<b[i].fm.length;k++){
					var pic = "";
					if(b[i].fm[k].image == null || b[i].fm[k].image == ""){
						pic = "http://guanyukeji-static.oss-cn-hangzhou.aliyuncs.com/tombstone_dead_pic/1.png";
					}else{
						pic = b[i].fm[k].image;
					}
					zn+='<img title="'+b[i].fm[k].id+'" src="'+pic+'"/><p>'+b[i].fm[k].name+'</p><p>'+b[i].fm[k].relation+'<p/>';
				}
				if(zn){
					node1='<div class="aa img3" title="'+b[i].id+'" align="'+b[i].parentid+'"style="top:'+t1+'rem;margin-top:-'+mt1+'rem;">'+zn+'</div></div>';
				}else{
					node='';
				}
				$(".threes").append(node1);
			}
		};
		var h=0,h1=0;
		if(grandson<1){
			grandson=1;
		}
		h=grandson*3.1+"rem";
		h1=grandson*3.1*75;
		var w=$(".ones").width();
		$(".hhs").height(h);
	}

	
}
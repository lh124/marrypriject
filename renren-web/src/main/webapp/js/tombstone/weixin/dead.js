var data="";
var data1= "";
function init(){
	$.ajax({
		type: "POST",
	    url: "../geterweima?id="+document.getElementById("userid").value,
	    dataType: "json",
	    success: function(result){
			if(result.status == 'ok'){
				var content = "";
				var length = result.list.img.length;
				if(length > 3){
					length = 3;
				}
				for(var i = 0; i < length; i++){
					content += '<img class="m-r-2p" src="'+ result.list.img[i].pic +'"/>';
				}
				$("#content").html(result.list.user.content);
				$("#experience").html(result.list.user.experience);
				$("#userimage").html(content);
				if(result.list.deaduser != null){
					$("#deadname").html(result.list.deaduser.name);
					$("#deadbirdth").html(result.list.deaduser.birthdayanddeath);
					$("#deadcontent").html(result.list.deaduser.content);
					$("#deadimg").html('<img src="'+ result.list.deaduser.image +'"/>');
				}
				data = result.list.dead;
				data1 = result.list.dead;
				getdeaddata1();
			}
		}
	});
}

function getdeaddata1(){
	/*获取后代*/
	var children=[],grandson=[];
	var t=0,t1=-2.3;_top=0,len1=0;
	$(".one").append('<div class="img1 aa" title="'+data.id+'"><img src="'+data.image+'"/><p>'+data.name+'</p><p>'+data.relation+'<p/></div>');
	var a=data.children;
	for(var j=0;j< a.length;j++){
		var b=a[j].zn;
		var len=a[j].zn.length;
		if(j>0){
			var lena=a[j-1].zn.length;
			len1+=lena;
		}
		 _top=len1*2.3;
		t=len*2.3/2+_top;
		var fm='';
		for(var k = 0;k<a[j].fm.length;k++){
			fm+='<img src="'+a[j].fm[k].image+'" style="height:100px;width:100px;"/><p>'+a[j].fm[k].name+'</p><p>'+a[j].fm[k].relation+'<p/>';
		}
		$(".two").append('<div class="aa img2" title="'+a[j].id+'" align="'+a[j].parentid+'"style="top:'+t+'rem;">'+fm+'</div>');
		for(var i=0;i<b.length;i++){
			grandson.push(b[i]);
			t1+=2.3;
			var zn = "";
			for(var k = 0;k<b[i].fm.length;k++){
				zn+='<img src="'+b[i].fm[k].image+'"/><p>'+b[i].fm[k].name+'</p><p>'+b[i].fm[k].relation+'<p/>';
			}
			$(".three").append('<div class="aa img3" title="'+b[i].id+'" align="'+b[i].parentid+'"style="top:'+t1+'rem;">'+zn+'</div>');
		}
	}

	var h=grandson.length*3.0+"rem";
	var h1=grandson.length*2.3*75;
	var w=$(".one").width();
	$(".hh").height(h);
	getcanvas();
}

function getdeaddata(){
	$.ajax({
		type: "POST",
	    url: "../getdeaddata?id="+document.getElementById("userid").value,
	    dataType: "json",
	    success: function(result){
			data = result.list;
			data1 = result.list;
			/*获取后代*/
			var children=[],grandson=[];
			var t=0,t1=-2.3;_top=0,len1=0;
			$(".one").append('<div class="img1 aa" title="'+data.id+'"><img src="'+data.image+'"/><p>'+data.name+'</p><p>'+data.relation+'<p/></div>');
			var a=data.children;
			for(var j=0;j< a.length;j++){
				var b=a[j].zn;
				var len=a[j].zn.length;
				if(j>0){
					var lena=a[j-1].zn.length;
					len1+=lena;
				}
				 _top=len1*2.3;
				t=len*2.3/2+_top;
				var fm='';
				for(var k = 0;k<a[j].fm.length;k++){
					fm+='<img src="'+a[j].fm[k].image+'" style="height:100px;width:100px;"/><p>'+a[j].fm[k].name+'</p><p>'+a[j].fm[k].relation+'<p/>';
				}
				$(".two").append('<div class="aa img2" title="'+a[j].id+'" align="'+a[j].parentid+'"style="top:'+t+'rem;">'+fm+'</div>');
				for(var i=0;i<b.length;i++){
					grandson.push(b[i]);
					t1+=2.3;
					var zn = "";
					for(var k = 0;k<b[i].fm.length;k++){
						zn+='<img src="'+b[i].fm[k].image+'"/><p>'+b[i].fm[k].name+'</p><p>'+b[i].fm[k].relation+'<p/>';
					}
					$(".three").append('<div class="aa img3" title="'+b[i].id+'" align="'+b[i].parentid+'"style="top:'+t1+'rem;">'+zn+'</div>');
				}
			}
		
			var h=grandson.length*3.0+"rem";
			var h1=grandson.length*2.3*75;
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
		ctx.lineTo(w,a);
	}
	ctx.closePath();
	ctx.stroke();	

	/*第二个canvas*/
	document.getElementById("canvas1").width = w;  
	document.getElementById("canvas1").height = h; 
	var canvas1=document.getElementById("canvas1");
	var ctx1=canvas1.getContext("2d");
	//绘制线段(直角三角形)
	var img3_top=zuobiao1();
	var img2=$('.hh .img2');
	var img3=$('.hh .img3');
	ctx1.beginPath();
	for(var i=0;i<img2.length;i++){
		for(var j=0;j<img3.length;j++){
			if($('.hh .img2').eq(i).attr("title")==$('.hh .img3').eq(j).attr("align")){
				var ctx1_h=$('.hh .img2').eq(i).css('top').substring(0,$('.hh .img2').eq(i).css('top').length-2);
				var ctx1_hh=parseFloat($('.hh .img3').eq(j).css('top').substring(0,$('.hh .img3').eq(j).css('top').length-2));
				ctx1.moveTo(0,ctx1_h);
				ctx1.lineTo(w,ctx1_hh+43);
			}
		}
	}
	ctx1.closePath();
	ctx1.stroke();	

	/*点击事件  弹框*/
	$(".hh").find('img').on("click",function(){
		var id=$(this).attr('title');
		$(".zhezao").show();
		$('#relation').show();
		var node='<h3 style="border-bottom:1px solid #d0cece;height:1.0rem;"><button type="button" class="floatRight btn">关闭</button></h3><div class="hhs"><div id="ones" class="ones"></div><div><canvas id="canvas3" width="100" height="500"></canvas></div><div class="twos"></div><div><canvas id="canvas4" style="width:100%;height:100%;"></canvas></div><div class="threes"></div></div>';
		$('#relation').append(node);
		/*函数*/
		tupian();
		var w1=document.getElementById("ones").offsetWidth;
		var h1=document.getElementById("ones").offsetHeight;
		document.getElementById("canvas3").width = w1;  
		document.getElementById("canvas3").height = h1; 
		var canvas3=document.getElementById("canvas3");
		var ctx3=canvas3.getContext("2d");
		//绘制线段(直角三角形)
		var img2_top=zuobiaos();
		console.log(img2_top);
		ctx3.beginPath();
		for(var i=0;i<img2_top.length;i++){
			ctx3.moveTo(0,h1/2);
			var a=parseFloat(img2_top[i]);
			console.log(a);
			ctx3.lineTo(w,a);
		}
		ctx3.closePath();
		ctx3.stroke();	

		/*canvas*/
		document.getElementById("canvas4").width = w1;  
		document.getElementById("canvas4").height = h1; 
		var canvas4=document.getElementById("canvas4");
		var ctx4=canvas4.getContext("2d");
		//绘制线段(直角三角形)
		var img3_top=zuobiaos1();
		var imgs2=$('.hhs .img2');
		var imgs3=$('.hhs .img3');
		ctx4.beginPath();
		for(var i=0;i<imgs2.length;i++){
			for(var j=0;j<imgs3.length;j++){
				if($('.hhs .img2').eq(i).attr("title")==$('.hhs .img3').eq(j).attr("align")){
					var ctx4_h=$('.hhs .img2').eq(i).css('top').substring(0,$('.hhs .img2').eq(i).css('top').length-2);
					var ctx4_hh=parseFloat($('.hhs .img3').eq(j).css('top').substring(0,$('.hhs .img3').eq(j).css('top').length-2));
					ctx4.moveTo(0,ctx4_h);
					ctx4.lineTo(w,ctx4_hh+43);
				}
			}
		}
		ctx4.closePath();
		ctx4.stroke();	
		$("#relation img").on("click",function(){
			console.log($(this).attr('title'));
			$("#relation").html("");
			$("#relation").append("node");
		});
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
			var s=$('.hh .img2').eq(i).css('top').substring(0,$('.hh .img2').eq(i).css('top').length-2);
			img2_top.push(s);
		}
		return img2_top;
	}
	function zuobiao1(){
		var img3_top=[];
		var img3=$('.hh .img3');
		for(var i=0;i<img3.length;i++){
			var s=$('.hh .img3').eq(i).css('top').substring(0,$('.hh .img3').eq(i).css('top').length-2);
			img3_top.push(s);
		}
		return img3_top;
	}
	/*获取坐标*/
	function zuobiaos(){
		var img2_top=[];
		var img2=$('.hhs .img2');
		for(var i=0;i<img2.length;i++){
			var s=$('.hhs .img2').eq(i).css('top').substring(0,$('.hhs .img2').eq(i).css('top').length-2);
			img2_top.push(s);
		}
		return img2_top;
	}
	function zuobiaos1(){
		var img3_top=[];
		var img3=$('.hhs .img3');
		for(var i=0;i<img3.length;i++){
			var s=$('.hhs .img3').eq(i).css('top').substring(0,$('.hhs .img3').eq(i).css('top').length-2);
			img3_top.push(s);
		}
		return img3_top;
	}
	function tupian(){
		/*获取后代*/
		var children=[],grandson=[];
		var t=0,t1=-2.3;_top=0,len1=0;
		$(".ones").append('<img class="aa img1" src="'+data1.image+'">');
		var a=data1.children;
		for(var j=0;j<a.length;j++){
			var b=a[j].zn;
			var len=a[j].zn.length;
			if(j>0){
				var lena=a[j-1].zn.length;
				len1+=lena;
			}
			 _top=len1*2.3;
			t=len*2.3/2+_top;
			var fm='';
			for(var k = 0;k<a[j].fm.length;k++){
				fm+='<img src="'+a[j].fm[k].image+'" style="height:100px;width:100px;"/><p>'+a[j].fm[k].name+'</p><p>'+a[j].fm[k].relation+'<p/>';
			}
			$(".two").append('<div class="aa img2" title="'+a[j].id+'" align="'+a[j].parentid+'"style="top:'+t+'rem;">'+fm+'</div>');
			for(var i=0;i<b.length;i++){
				grandson.push(b[i]);
				t1+=2.3;
				var zn = "";
				for(var k = 0;k<b[i].fm.length;k++){
					zn+='<img src="'+b[i].fm[k].image+'"/><p>'+b[i].fm[k].name+'</p><p>'+b[i].fm[k].relation+'<p/>';
				}
				$(".three").append('<div class="aa img3" title="'+b[i].id+'" align="'+b[i].parentid+'"style="top:'+t1+'rem;">'+zn+'</div>');
			}
		}
		var h=grandson.length*2.3+"rem";
		var h1=grandson.length*2.3*75;
		var w=$(".ones").width();
		$(".hhs").height(h);
	}
}
$(function(){
	$('#gongzong').click(function (){
		$('.weui-gallery').css('display', 'block');
	});

	$('.weui-gallery__del').click(function (){
		$('.weui-gallery').css('display', 'none');
	});
});
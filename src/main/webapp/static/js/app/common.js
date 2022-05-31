$(document).ready(function(){

	$('.wrap .navigation > ul > li > span').click(function(){
		if($(this).parent().hasClass('on')){
			$(this).parent().removeClass();
		}else{
			$(this).parent().addClass('on');
		}
	});

});
$(window).load(function(){
	$('.wrap').css({'height':$(document).height() - 65});
});
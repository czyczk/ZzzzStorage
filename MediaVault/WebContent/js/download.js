$('.downloaded').click(function(){
	$('#downloaded').show();
	$('#downloading').hide();
	$(this).parent().addClass('active');
	$('.downloading').parent().removeClass('active');
});

$('.downloading').click(function(){
	$('#downloaded').hide();
	$('#downloading').show();
	$(this).parent().addClass('active');
	$('.downloaded').parent().removeClass('active');
});


$('.uploaded').click(function(){
	$('#uploaded').show();
	$('#uploading').hide();
	$(this).parent().addClass('active');
	$('.uploading').parent().removeClass('active');
});

$('.uploading').click(function(){
	$('#uploaded').hide();
	$('#uploading').show();
	$(this).parent().addClass('active');
	$('.uploaded').parent().removeClass('active');
});

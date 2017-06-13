$('#uploaded-toggle').click(function(){
	$(this).parent().addClass('active');
	$('#uploading-toggle').parent().removeClass('active');
});

$('#uploading-toggle').click(function(){
	$(this).parent().addClass('active');
	$('#uploaded-toggle').parent().removeClass('active');
});

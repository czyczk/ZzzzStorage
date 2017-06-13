$('#downloaded-toggle').click(function(){
	$(this).parent().addClass('active');
	$('#downloading-toggle').parent().removeClass('active');
});

$('#downloading-toggle').click(function(){
	$(this).parent().addClass('active');
	$('#downloaded-toggle').parent().removeClass('active');
});




$('#type').click(function(){
	var selectType = $('#type option:selected').val();
	if(selectType == 'Music'){
		$('.movie').hide();
		$('.music').show();
		$('.tvshow').hide();

		$('.imdb').hide();
		$('.releaseYear').hide();
		$('.plot').hide();
		$('.director').hide();

		$('.runtime').hide();
		$('.season').hide();

		$('.artist').show();
		$('.album').show();
		$('.track').show();
		$('.duration').show();
	}
	if(selectType == 'Movie'){
		$('.movie').show();
		$('.music').hide();
		$('.tvshow').hide();

        $('.imdb').show();
        $('.releaseYear').show();
        $('.plot').show();
        $('.director').show();
        $('.duration').show();

        $('.runtime').hide();
        $('.season').hide();

        $('.artist').hide();
        $('.album').hide();
        $('.track').hide();
	}
	if(selectType == 'TVShow'){
		$('.movie').hide();
		$('.music').hide();
		$('.tvshow').show();

        $('.releaseYear').hide();
        $('.director').hide();
        $('.duration').hide();

        $('.runtime').show();
        $('.season').show();

        $('.artist').hide();
        $('.album').hide();
        $('.track').hide();
	}
})
;

$('.movie').click(function(){
	var selectType = $('.movie option:selected').val();
	
	if(selectType == 'Others'){
		$('.category-input').show();
		selectOther = true;
	} else {
		$('.category-input').hide();
		selectOther = false;
	}
});

$('.music').click(function(){
	var selectType = $('.music option:selected').val();
	
	if(selectType == 'Others'){
		$('.category-input').show();
	} else {
		$('.category-input').hide();
	}
});

$('.tvshow').click(function(){
	var selectType = $('.tvshow option:selected').val();
	
	if(selectType == 'Others'){
		$('.category-input').show();
	} else {
		$('.category-input').hide();
	}
});

$("#desc").bind('input propertychange', function () {
    if ($(this).val().length <= 256) {
        $('.msg').text($(this).val().length + '/256 words.');

	} else {
        $(this).val($(this).val().substring(0, 256));
	}
});



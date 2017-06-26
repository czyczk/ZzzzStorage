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
});

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

// $('.upload-submit').click(function(){
// 	var fileName = $('#recipient-name').val();
// 	var type = $('#type option:selected').val();
// 	var movie;
// 	var music;
// 	var tvshow;
// 	var otherInput;
// 	if(type == 'Movie'){
// 		movie = $('.movie option:selected').val();
// 	}
// 	if(type == 'Music'){
// 		music = $('.music option:selected').val();
// 	}
// 	if(type == 'TVShow'){
// 		tvshow = $('.tvshow option:selected').val();
// 	}
// 	if(movie == 'Others' || music == 'Others' || tvshow == 'Others'){
// 		otherInput = $('.category-input').val();
// 	}
// 	if(type == 'Movie' && movie != 'Others'){
// 		$('#list').prepend("<div class='col-lg-6 col-sm-6'><div class='tag'><div class='col-sm-5'><img src='img/downloaded-TVShow.jpg' class='thumbnail' /></div><div class='col-sm-7'><div class='header'><div class='navigation'><ol class='breadcrumb'><li><a>"+movie+"</a></li></ol><a href='#'>"+fileName+"</a><p>6-9-2017 13:25:30</p><p>Adventure stories of a super hero.</p></div></div></div></div></div>");
// 	}
// 	if(type == 'Music' && music != 'Others'){
// 		$('#list').prepend("<div class='col-lg-6 col-sm-6'><div class='tag'><div class='col-sm-5'><img src='img/downloaded-TVShow.jpg' class='thumbnail' /></div><div class='col-sm-7'><div class='header'><div class='navigation'><ol class='breadcrumb'><li><a>"+music+"</a></li></ol><a href='#'>"+fileName+"</a><p>6-9-2017 13:25:30</p><p>Adventure stories of a super hero.</p></div></div></div></div></div>");
//
// 	}
// 	if(type == 'TVShow' && tvshow != 'Others'){
// 		$('#list').prepend("<div class='col-lg-6 col-sm-6'><div class='tag'><div class='col-sm-5'><img src='img/downloaded-TVShow.jpg' class='thumbnail' /></div><div class='col-sm-7'><div class='header'><div class='navigation'><ol class='breadcrumb'><li><a>"+tvshow+"</a></li></ol><a href='#'>"+fileName+"</a><p>6-9-2017 13:25:30</p><p>Adventure stories of a super hero.</p></div></div></div></div></div>");
//
// 	}
// 	if(movie == 'Others' || music == 'Others' || tvshow == 'Others'){
// 		$('#list').prepend("<div class='col-lg-6 col-sm-6'><div class='tag'><div class='col-sm-5'><img src='img/downloaded-TVShow.jpg' class='thumbnail' /></div><div class='col-sm-7'><div class='header'><div class='navigation'><ol class='breadcrumb'><li><a>"+otherInput+"</a></li></ol><a href='#'>"+fileName+"</a><p>6-9-2017 13:25:30</p><p>Adventure stories of a super hero.</p></div></div></div></div></div>");
//
// 	}
// });

$(".plot").bind('input propertychange', function () {

    if ($(this).val().length <= 256) {
        $('.msg').html($(this).val().length + '/256 words.');

	} else {
        $(this).val($(this).val().substring(0, 256));
	}
});

// $('.upload-submit').click(handleUpload);
// function handleUpload() {
// 	$.ajax({
// 		url: "UploadServlet",
// 		data: $
// 	})
// }

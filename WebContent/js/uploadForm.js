$(function(){
	var selectType;
	$('.bootstrap-tagsinput').addClass("form-control");
    $('#type').click(function(){
        selectType = $('#type option:selected').val();
        if(selectType == 'Music'){
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
            $('#genre').tags({
                tagData: ["boilerplate", "tags"],
				suggestions: ["Rock", "Jazz", "Popular", "Folk"]
            });
        }
        if(selectType == 'Movie'){
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
            $('#genre').tags({
                tagData: ["boilerplate", "tags"],
                suggestions: ["Adventure", "Thriller", "Criminal", "Love"]
            });
        }
        if(selectType == 'TVShow'){
            $('.releaseYear').hide();
            $('.director').hide();
            $('.duration').hide();

            $('.runtime').show();
            $('.season').show();

            $('.artist').hide();
            $('.album').hide();
            $('.track').hide();
            $('#genre').tags({
                tagData: ["boilerplate", "tags"],
                suggestions: ["Criminal", "Love", "Thriller", "Adventure"]
            });
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

// $('.music').click(function(){
// 	var selectType = $('.music option:selected').val();
//
// 	if(selectType == 'Others'){
// 		$('.category-input').show();
// 	} else {
// 		$('.category-input').hide();
// 	}
// });
//
// $('.tvshow').click(function(){
// 	var selectType = $('.tvshow option:selected').val();
//
// 	if(selectType == 'Others'){
// 		$('.category-input').show();
// 	} else {
// 		$('.category-input').hide();
// 	}
// });

    $("#desc").bind('input propertychange', function () {
        if ($(this).val().length <= 256) {
            $('.msg').text($(this).val().length + '/256 words.');

        } else {
            $(this).val($(this).val().substring(0, 256));
        }
    });

    var genre = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        prefetch: {
            url: 'assets/genre.json',
            filter: function(list) {
                return $.map(list, function(genre){
                    return {name: genre};
                });
            }
        }
    });
    genre.initialize();

    $("#genre").tagsinput({
        typeheadjs: {
            name: "genre",
            displayKey: "name",
            valueKey: "name",
            source: genre.ttAdapter()
        }
    });



});



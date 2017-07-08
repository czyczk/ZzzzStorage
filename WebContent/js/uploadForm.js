$(function(){
	var selectType;
	var recommendValue = [];
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
            $(".recipient-name").show();

            $(".episode").hide();
            $(".titleOfEpisode").hide();
            $(".episodeRuntime").hide();
            $(".episodeThumbnail").hide();
            $(".episodeRating").hide();
            $(".storyLine").hide();
        }
        if(selectType == 'Movie'){
            $('.imdb').show();
            $('.releaseYear').show();
            $('.plot').show();
            $('.director').show();
            $('.duration').show();
            $(".recipient-name").show();

            $('.runtime').hide();
            $('.season').hide();

            $('.artist').hide();
            $('.album').hide();
            $('.track').hide();

            $(".episode").hide();
            $(".titleOfEpisode").hide();
            $(".episodeRuntime").hide();
            $(".episodeThumbnail").hide();
            $(".episodeRating").hide();
            $(".storyLine").hide();
        }
        if(selectType == 'TV_Show'){
            $('.releaseYear').hide();
            $('.director').hide();
            $('.duration').hide();

            $('.runtime').show();
            $('.season').show();
            $(".recipient-name").show();
            $(".imdb").show();

            $('.artist').hide();
            $('.album').hide();
            $('.track').hide();

            $(".episode").hide();
            $(".titleOfEpisode").hide();
            $(".episodeRuntime").hide();
            $(".episodeThumbnail").hide();
            $(".episodeRating").hide();
            $(".storyLine").hide();
        }
        if(selectType == 'Episode'){
            $(".episode").show();
            $(".titleOfEpisode").show();
            $(".episodeRuntime").show();
            $(".episodeThumbnail").show();
            $(".episodeRating").show();
            $(".storyLine").show();
            $(".season").show();
            $(".imdb").show();

            $(".rating").hide();
            $(".thumbUrl").hide();
            $(".artist").hide();
            $(".director").hide();
            $(".runtime").hide();
            $(".duration").hide();
            $(".track").hide();
            $(".plot").hide();
            $(".album").hide();
            $(".releaseYear").hide();
            $(".recipient-name").hide();
        }
    })
    ;


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
        if ($(this).val().length <= 255) {
            $('.msg').text($(this).val().length + '/255 words.');

        } else {
            $(this).val($(this).val().substring(0, 255));
        }
    });
    $("#recipient-name").bind('input propertychange', function () {
        if ($(this).val().length <= 255) {
            $('.title').text($(this).val().length + '/255 words.');

        } else {
            $(this).val($(this).val().substring(0, 255));
        }
    });
    $("#thumbUrl").bind('input propertychange', function () {
        if ($(this).val().length <= 255) {
            $('.thumburl').text($(this).val().length + '/255 words.');

        } else {
            $(this).val($(this).val().substring(0, 255));
        }
    });

    $("#titleOfEpisode").bind('input propertychange', function () {
        if ($(this).val().length <= 255) {
            $('.msgEpisodeTitle').text($(this).val().length + '/255 words.');

        } else {
            $(this).val($(this).val().substring(0, 255));
        }
    });

    $("#episodeThumbnail").bind('input propertychange', function () {
        if ($(this).val().length <= 255) {
            $('.msgEpisodeThumb').text($(this).val().length + '/255 words.');

        } else {
            $(this).val($(this).val().substring(0, 255));
        }
    });

    $("#storyLine").bind('input propertychange', function () {
        if ($(this).val().length <= 255) {
            $('.msgStoryLine').text($(this).val().length + '/255 words.');

        } else {
            $(this).val($(this).val().substring(0, 255));
        }
    });
    // var genre = new Bloodhound({
    //     datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
    //     queryTokenizer: Bloodhound.tokenizers.whitespace,
    //     prefetch: {
    //         url: 'assets/genre.json',
    //         filter: function(list) {
    //             return $.map(list, function(genre){
    //                 return {name: genre};
    //             });
    //         }
    //     }
    // });
    // genre.initialize();
    //
    // $("#genre").tagsinput({
    //     typeaheadjs: {
    //         name: "genre",
    //         displayKey: "name",
    //         valueKey: "name",
    //         source: genre.ttAdapter()
    //     }
    // });
    //
    var genre = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        prefetch: {
            url: '././assets/genre.json',
            filter: function (list) {
                return $.map(list, function (genre) {
                    return {value: genre}
                })
            }
        }
    });
    genre.initialize();

    $('.bootstrap-tagsinput input').tagsinput({
        typeaheadjs: {
            name: 'genre',
            displayKey: 'value',
            valueKey: 'value',
            source: genre.ttAdapter()
        }
    })

    // $('#genre').bind('input propertychange', function() {
    //     var input = $('#genre').val();
    //     $.ajax({
    //         url: '../assets/genre.json',
    //         type: 'GET',
    //         dataType: 'json',
    //         success: function(data) {
    //             if(data.value.startswith(input)){
    //                 recommendValue.push(data.value);
    //             }
    //             console.log(recommend);
    //         }
    //     })
    // })




});



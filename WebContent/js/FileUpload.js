/**
 * Created by ZhangHaodong on 2017/6/24.
 */
// var fileResult;
var fileSize;
var sha256;
var mediaType;
var uploadRange = 'prohibited';
var formError = false;
var isExisting = false;

$(function () {
    $('#type').click(function () {
        // Set mediaType to the type selected
        mediaType = $('#type option:selected').val();
    });
    mediaType = $('#type option:selected').val();
    if(mediaType !== 'TV_Show'){
        document.getElementById('input-2').onchange = function () {
            var file = document.getElementById('input-2').files[0];
            if(file) {
                getAsText(file);
            }
        };
    }

    
    function getAsText(readFile) {
        var reader = new FileReader();
        reader.readAsArrayBuffer(readFile);
        // reader.readAsBinaryString(readFile);
        // reader.readAsText(readFile, "LATIN1");
        reader.onload = function (e) {
            // fileResult = this.result;
            // console.info(fileResult);
            fileSize = readFile.size;
            // sha256 = CryptoJS.SHA256(fileResult).toString();
            var wordArray = CryptoJS.lib.WordArray.create(e.target.result);
            sha256 = CryptoJS.SHA256(wordArray).toString();
            console.log(sha256);
        }

    }

    $('#imdb').bind('input propertychange', function () {
        var imdb = $('#imdb').val();
        if(imdb == "") {
            formError = true;
            $('.errorIMDB-required').show();
            $('.error-range').hide();
        } else if(imdb < 1000000 || imdb > 9999999) {
            formError = true;
            $('.error-range').show();
            $('.errorIMDB-required').hide();
        } else {
            formError = false;
            $('.error-range').hide();
            $('.errorIMDB-required').hide();
        }
    });

    $('#recipient-name').bind('input propertychange', function(){
       var title = $('#recipient-name').val();
       if(title == "") {
           formError = true;
           $('.errorTitle-required').show();
       } else {
           formError = false;
           $('.errorTitle-required').hide();
       }
    });

    $('#album').bind('input propertychange', function(){
        var title = $('#album').val();
        if(title == "") {
            formError = true;
            $('.errorAlbum-required').show();
        } else {
            formError = false;
            $('.errorAlbum-required').hide();
        }
    });

    $('#rating').bind('input propertychange', function () {
        var rating = $('#rating').val();
        if(rating < 0 || rating > 10) {
            formError = true;
            $('.error-rating').show();
        } else {
            formError = false;
            $('.error-rating').hide();
        }
    });

    $('#season').bind('input propertychange', function () {
        var season = $('#season').val();
        if(season == "") {
            formError = true;
            $('.errorSeason-required').show();
            $('.error-season').hide();
        } else if(season < 1 || season > 20) {
            formError = true;
            $('.error-season').show();
            $('.errorSeason-required').hide();
        } else {
            formError = false;
            $('.error-season').hide();
            $('.errorSeason-required').hide();
        }
    });

    $('#episode').bind('input propertychange', function () {
        var episode = $('#episode').val();
        if(episode < 1) {
            formError = true;
            $('.error-episode').show();
            $('.errorEpisode-required').hide();
        } else if(episode == "") {
            formError = true;
            $('.errorEpisode-required').show();
            $(".error-episode").hide();
        } else {
            formError = false;
            $('.error-episode').hide();
            $('.errorEpisode-required').hide();
        }
    });


    $('.upload-submit').click(handleSubmit);


    

});

function handleSubmit() {
    if(mediaType !== 'TV_Show'){
        $.ajax({
            url: 'HashCheckerServlet',
            data: "SHA256=" + sha256 + "&size=" + fileSize + "&mediaType=" + mediaType,
            type: "post",
            dataType: "json",
            async: false,
            success: uploadOrNot(),
            error: function () {
                alert("No file selected!");
            }
        });

        if (uploadRange == 'prohibited') return;

        uploadForm();
    } else {
        tvshowSubmit();
    }

};

function uploadOrNot() {
    return function (data) {
        if(data.message == 'full' || data.message == 'metadata') {
            // uploadForm(data);
            uploadRange = data.message;
        } else {
            alert('This file exists in your library.');
        }
    }
}

function uploadForm() {
    if(!formError){
        if(mediaType == 'Episode') {
            var imdb = $('#imdb').val();
            var season = $('#season').val();
            $.ajax({
                url: 'FileListGeneratorServlet',
                data: 'requestType=exists&mediaType=episode&imdb=' + imdb + "&season=" + season,
                type: 'post',
                dataType: 'json',
                async: false,
                success: function (data) {
                    isExisting = data;
                    if(!isExisting){
                        alert("Please create a corresponding TV show first.");
                    }
                },
                error: function () {
                    alert("Internal Error");
                }
            });
        }
        if(mediaType !== 'Episode' || isExisting) {
            var genre;
            genre = $('#genre').tagsinput('items');
            // if(mediaType == 'Movie') {
            //     genre = $('.movie option:selected').val();
            // } else if(mediaType == 'TVShow') {
            //     genre = $('.tvshow option:selected').val();
            // } else if(mediaType == 'Music') {
            //     genre = $('.music option:selected').val();
            // } else if(mediaType == 'Episode')
            var formData = new FormData($("#upload-form")[0]);
            formData.append("requestType", uploadRange);
            formData.append("SHA256", sha256);
            formData.append("size", fileSize);
            formData.append("genre", genre);
            $.ajax({
                url: "UploadServlet",
                data: formData,
                type: "post",
                cache: false,
                contentType: false,
                processData: false,
                error: function() {
                    alert("Internal error.");
                },
                success: function(data) {
                    if (data.messageType == "success") {
                        // data.redirect contains the string URL to redirect to
                        window.location.href = data.message;
                    }
                }
            });
        }

    }

}

function tvshowSubmit() {
    var formData = new FormData($("#upload-form")[0]);
    var genre;
    genre = $('#genre').tagsinput('items');
    formData.append("mediaType", mediaType);
    $.ajax({
        url: 'UploadServlet',
        data: formData,
        type: 'post',
        cache: false,
        contentType: false,
        processData: false,
        error: function() {
            alert("Internal error.");
        },
        success: function(data) {
            if (data.messageType === "success") {
                // data.redirect contains the string URL to redirect to
                window.location.href = data.message;
            }
        }
    });
}

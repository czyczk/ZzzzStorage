/**
 * Created by ZhangHaodong on 2017/6/24.
 */
// var fileResult;
var fileSize;
var sha256;
var mediaType;
var uploadRange = 'prihibited';
var formError = false;

$(function () {
    // function startRead() {
    //     //Obtain input element through DOM
    //     alert("something");
    //     var file = document.getElementById('input-2').files[0];
    //     if(file) {
    //       getAsText(file);
    //     }
    // }
    $('#type').click(function () {
        mediaType = $('#type option:selected').val();

    });
    mediaType = $('#type option:selected').val();
    document.getElementById('input-2').onchange = function () {
        alert("something");
        var file = document.getElementById('input-2').files[0];
        if(file) {
            getAsText(file);
        }
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

    $('.upload-submit').click(handleSubmit);
    

});

function handleSubmit() {
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
        var formData = new FormData($("#upload-form")[0]);
        formData.append("requestType", uploadRange);
        formData.append("SHA256", sha256);
        formData.append("size", fileSize);
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


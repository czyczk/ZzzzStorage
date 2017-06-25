/**
 * Created by ZhangHaodong on 2017/6/24.
 */
// var fileResult;
var fileSize;
var sha256;
var mediaType;
var uploadRange = 'prihibited';

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

    $('.upload-submit').click(handleSubmit);
    

});

function handleSubmit() {
    $.ajax({
        url: 'HashCheckerServlet',
        data: "SHA256=" + sha256 + "&size=" + fileSize + "&mediaType=" + mediaType,
        type: "post",
        dataType: "json",
        async: false,
        success: uploadOrNot()
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
    // $("#upload-form").ajaxForm({
    //     beforeSend: alert("asdlkfjwrei0fjdxclkvnaSKL"),
    //     url: 'UploadServlet',
    //     type: 'post',
    //     dataType: 'json',
    //     data: "SHA256=" + sha256 + "&size=" + fileSize + "&mediaType=" + encodeURIComponent(mediaType)
    // }).submit();
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
            alert("loser");
        }
    });
}


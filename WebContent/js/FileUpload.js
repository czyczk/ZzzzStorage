/**
 * Created by ZhangHaodong on 2017/6/24.
 */
// var fileResult;
var fileSize;
var sha256;
$(function () {
    // function startRead() {
    //     //Obtain input element through DOM
    //     alert("something");
    //     var file = document.getElementById('input-2').files[0];
    //     if(file) {
    //       getAsText(file);
    //     }
    // }
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
    
    $('.submit-button').click(handlSubmit);
});

function handleSubmit() {
    
}


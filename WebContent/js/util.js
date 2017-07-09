/**
 * Created by czyczk on 2017-7-1.
 */

// Return the size in an easy-to-read format.
function formatSize(size) {
    if ((size / 1024 / 1024) > 1000) {
        return (size / 1024 / 1024 / 1024).toFixed(2) + " GB";
    } else if ((size / 1024) > 1000) {
        return (size / 1024 / 1024).toFixed(2) + " MB";
    } else {
        return (size / 1024).toFixed(2) + " KB";
    }
}

// Return duration in an easy-to-read format according to the media type.
function formatDuration(mediaType, duration) {
    if (mediaType == "music") {
        var min = (duration / 60).toFixed(0);
        var sec = duration % 60;
        var secStr = fillWithZeroes(sec, 2);
        return min + ":" + secStr;
    } else {
        return duration + " min";
    }
}

function fillWithZeroes(number, width) {
    var oriLen = number.toString().length;
    var str = "";
    for ( ; oriLen < width; oriLen++) {
        str += "0";
    }
    return str + number;
}
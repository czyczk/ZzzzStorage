/**
 * Created by czyczk on 2017-6-26.
 */
// Media type
var mediaType = "music";
// The default thumb URL for a movie item
var defaultThumbPath = "img/sample-covers/default-music-icon-poster-size.png";
// The default SQL query statement
var sqlStatement = "requestType=list&mediaType=music&orderBy=title&start=0&range=10";

// Arrange the items onto the page
function arrangeItems() {
    var container = $("#contentRight");

    // Show info only if no music is found
    if (items == undefined || items.length == 0) {
        var html = '<div class="info"><h2 style="top: 100px; left: 500px; position:absolute;">No music.</h2></div>';
        container.html(html);
        return;
    }

    // Arrange the items
    var html = '<div>';
    items.forEach(function (it) {
        html += '\
	    <div class="col-lg-6 col-sm-6">\
	    <div class="item-card" id="'+ it.SHA256 +'">\
	    <div class="col-sm-4">\
	    <div class="thumbnail-container">\
	    <div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">\
	    <span class="circle-pattern">&#xEC61;</span>\
	    </div>\
	    <div class="thumbnail-image">\
	    ';

        // Append cover image (if available) or the default icon (if not available)
        html += '<img src="';
        if (it.thumbUrl != undefined) {
            html += it.thumbUrl;
        } else {
            html += defaultThumbPath;
        }
        html += '" class="thumbnail" />';

        html += '\
	    </div>\
	    </div>\
	    </div>\
	    <div class="col-sm-7">\
	    <div class="item-info-container">\
	    ';

        // Append hidden fields (structural requirement)
        // : SHA256, size, artist, title
        html += '<span class="item-sha256" style="display: none;">' + it.SHA256 + '</span>';
        html += '<span class="item-size" style="display: none;">' + it.size + '</span>';
        html += '<span class="item-artist" style="display: none;">';
        if (it.duartion != undefined || it.duration != 0) {
            html += '<span class="item-duration" style="display: none;">' + it.duration + '</span>';
        }
        var artists = it.artist;
        if (artists != undefined && artists.length > 0) {
            var len = artists.length;
            for (i = 0; i < len; i++) {
                html += artists[i];
                if (i < len - 1) {
                    html += '`';
                }
            }
        }
        html += '</span>';
        html += '<span class="item-title" style="display: none;">' + it.title + '</span>';

        // Append artist (if available) and title
        var header = "";
        if (artists != undefined && artists.length > 0) {
            var len = artists.length;
            for (i = 0; i < len; i++) {
                header += artists[i];
                if (i < len - 1) {
                    header += ' & ';
                }
            }
            header += ' - ';
        }
        header += it.title;
        html += '<div><span class="item-header">' + header + '</span>';
        html += '</div>';

        // Append duration if available
        if (it.duration != undefined && it.duration != 0) {
            html += '<p>Duration: ' + formatDuration(mediaType, it.duration) + '</p>';
        }

        // Append album
        html += '<p class="item-album">Album: ' + it.album + '</p>';

        // Append track if available
        if (it.track != undefined && it.track != 0) {
            html += '<p class="item-track">Track: ' + it.track + '</p>';
        }

        // Append genres if available
        var genres = it.genre;
        if (genres != undefined && genres.length > 0) {
            html += '<p class="item-genre">Genre: ';
            for (i = 0; i < genres.length; i++) {
                html += genres[i];
                if (i < genres.length - 1) {
                    html += ', ';
                }
            }
            html += "</p>"
        }

        // Append rating if available
        if (it.rating != undefined) {
            html += '<p class="item-rating">Rating: ' + it.rating + "</p>";
        }
        // Append a hidden checkbox (structural requirement)
        html += '<input name="selected-items" type="checkbox" class="item-checkbox" value="' +it.SHA256+ '" /> <!-- the hidden checkbox -->';
        // Endings
        html += '</div></div></div></div>';
    });

    html += '</div>';
    container.html(html);
}

// Bind properties in the new property form for validation
function bindProperties() {
    $('#title').bind('input propertychange', function(){
        var title = $('#title').val();
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
}

function triggerEdit(it) {
    var size = $('#'+it).find('.item-size').text();
    var title = $('#'+it).find('.item-title').text();
    var album = $('#'+it).find('.item-album').text().substring(6).trim();
    var duration = $('#'+it).find('.item-duration').text().split(" ")[1];
    var genre = $('#'+it).find('.item-genre').text().substring(6).trim();
    console.log(genre);
    $('#size').text((size/1024/1024).toFixed(2)+'MB');
    $('#title').val(title);
    $('#album').val(album);
    $('#genre').val(genre);
    if(duration != 'undefined')
        $('#duration').val(duration);


    oldItem = {
        "SHA256": $('#'+it).find('.item-sha256').text(),
        "size": size
    };
}

function triggerDelete(SHA256) {
    var size = $('#'+SHA256).find('.item-size').text();

    $.ajax({
        url: "DeleteServlet",
        data: "mediaType=" + encodeURIComponent(mediaType) + "&SHA256=" + SHA256 + "&size=" + size,
        type: "post",
        success: function() {
            loadItems();
        },
        error: function(data) {
            alert(data);
        }
    });
}

// Submit the new property form. Invoked by the "update-submit" button.
function submitUpdate() {
    // TODO: Incomplete properties
    // Collect new info
    newItem = {
        "SHA256": oldItem.SHA256,
        "size": oldItem.size,
        "title": $('#title').val(),
        "album": $('#album').val(),
        "thumbUrl": $('#thumbUrl').val()
    };
    if($('#duration').val() != "") {
        newItem.duration = $('#duration').val();
    }
    // Send update request
    $.ajax({
        url: "UpdateServlet",
        type: "post",
        data: "mediaType=" + encodeURIComponent(mediaType) + "&oldItem=" + JSON.stringify(oldItem) + "&newItem=" + JSON.stringify(newItem),
        success: handleUpdateSuccess,
        error: function() {
            alert("Internal error.");
        }
    });
}
// Media type
var mediaType = "movie";
// The default thumb URL for a movie item
var defaultThumbPath = "img/sample-covers/default-movie-icon-poster-size.png";
// The default SQL query statement
var sqlStatement = "requestType=list&mediaType=movie&orderBy=title&start=0&range=10";

// Arrange the items onto the page
function arrangeItems() {
    var container = $("#contentRight");

    // Show info only if no movie is found
    if (items == undefined || items.length == 0) {
        var html = '<div class="info"><div style="text-align: center; margin: 10.5rem 0;"><h2>No movie.</h2></div></div>';
        container.html(html);
        return;
    }

    // Arrange the items
    var html = '<div>';
    items.forEach(function (it) {
        html += '\
	    <div class="col-lg-6 col-sm-6">\
	    <div class="item-card" id="'+ it.imdb +'">\
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

        // Header = title + (release year)
        // Append title and release year (if available)
        // Append title
        html += '<div>';
        // Append header
        html += '<div><span class="item-header">' + it.title + '</span>';
        // Append hidden properties
        html += '<span class="item-sha256" style="display: none;">' + it.SHA256 + '</span>';
        html += '<span class="item-size" style="display: none;">' + it.size + '</span>';
        html += '<span class="item-title" style="display: none;">' + it.title + '</span>';
        if (it.duartion != undefined || it.duration != 0) {
            html += '<span class="item-duration" style="display: none;">' + it.duration + '</span>';
        }
        // Append year if available
        if (it.releaseYear != 0) {
            html += '<span class="item-releaseYear" style="margin-left: 1rem;">(' + it.releaseYear + ')</span>';
        }
        html += '</div>';

        // Append IMDB and duration (if available)
        // Append IMDB
        html += '<div><span class="item-imdb">IMDB: ' + it.imdb + "</span>";
        // Append duration if available
        if (it.duration != undefined && it.duration != 0) {
            html += '<span style="margin-left: 2rem;">Duration: ' + formatDuration(mediaType, it.duration) + '</span>';
        }
        html += '</div>';

        // Append genres if available
        var genres = it.genre;
        if (genres != undefined && genres.length > 0) {
            html += '<p>Genre: '
            for (i = 0; i < genres.length; i++) {
                html += genres[i];
                if (i < genres.length - 1) {
                    html += ', ';
                }
            }
            html += "</p>";
        }

        // Append directors if available
        var directors = it.director;
        if (directors != undefined && directors.length > 0) {
            html += '<p>Director: ';
            for (i = 0; i < directors.length; i++) {
                html += directors[i];
                if (i < directors.length - 1) {
                    html += ', ';
                }
            }
            html += "</p>";
        }

        // Append rating if available
        if (it.rating != undefined) {
            html += '<p class="item-rating">Rating: ' + it.rating + "</p>";
        }
        // Append plot if available
        if (it.plot != undefined) {
            html += '<p class="item-plot">Plot: ' + it.plot + '</p>';
        }
        // Append a hidden checkbox (structural requirement)
        html += '<input name="selected-items" type="checkbox" class="item-checkbox" value="' +it.imdb+ '"/> <!-- the hidden checkbox -->';
        // Endings
        html += '</div></div></div></div></div>';
    });

    container.html(html);
}

// Bind properties in the new property form for validation
function bindProperties() {
    // TODO: Incomplete properties
    $(".plot").bind('input propertychange', function() {
        if ($(this).val().length <= 256) {
            $('.msg').html($(this).val().length + '/256 words.');

        } else {
            $(this).val($(this).val().substring(0, 256));
        }
    });

    $('#title').bind('input propertychange', function() {
        var title = $('#title').val();
        if(title == "") {
            formError = true;
            $('.errorTitle-required').show();
        } else {
            formError = false;
            $('.errorTitle-required').hide();
        }
    });

    $('#imdb').bind('input propertychange', function() {
        var imdb = $('#imdb').val();
        if(imdb == "") {
            formError = true;
            $('.errorIMDB-required').show();
            $('.error-range').hide();
        } else if(imdb < 0 || imdb > 9999999) {
            formError = true;
            $('.error-range').show();
            $('.errorIMDB-required').hide();
        } else {
            formError = false;
            $('.error-range').hide();
            $('.errorIMDB-required').hide();
        }
    });
}

function triggerDelete(imdb) {
    console.info(imdb);

    var SHA256 = $('#'+imdb).find('.item-sha256').text();
    var size = $('#'+imdb).find('.item-size').text();

    $.ajax({
        url: "DeleteServlet",
        data: "mediaType=" + encodeURIComponent(mediaType) + "&SHA256=" + SHA256 + "&size=" + size + "&imdb=" + imdb,
        type: "post",
        success: function() {
            loadItems();
        },
        error: function(data) {
            alert(data);
        }
    });
}

function triggerEdit(it) {
    // TODO: Incomplete properties
    var size = $('#'+it).find('.item-size').text();
    var title = $('#'+it).find('.item-title').text();
    var imdb = $('#'+it).find('.item-imdb').text().substring(5).trim();
    var releaseYear = $('#'+it).find('.item-releaseYear').text().substring(1,5);
    var duration = $('#'+it).find('.item-duration').text();
    var plot = $('#'+it).find('.item-plot').text().substring(6);
    var thumbUrl = $('#'+it).find('.thumbnail').attr('src');
    $('#size').text(formatSize(size));
    $('#title').val(title);
    $('#imdb').val(imdb);
    $('#releaseYear').val(releaseYear);
    if (duration != 'undefined')
        $('#duration').val(duration);
    if (plot != undefined)
        $('#plot').val(plot);
    if (thumbUrl != defaultThumbPath)
        $('#thumbUrl').val(thumbUrl);
    else
        $('#thumbUrl').val("");

    oldItem = {
        "SHA256": $('#'+it).find('.item-sha256').text(),
        "size": size,
        "imdb": imdb
    };
}

// Submit the new property form. Invoked by the "update-submit" button.
function submitUpdate() {
    // TODO: Incomplete properties
    // Collect new info
    newItem = {
        "SHA256": oldItem.SHA256,
        "size": oldItem.size,
        "title": $('#title').val(),
        "imdb": $('#imdb').val(),
        "releaseYear": $('#releaseYear').val(),
        "plot": $('#plot').val(),
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

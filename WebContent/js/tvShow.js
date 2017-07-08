/**
 * Created by czyczk on 2017-7-8.
 */
// Media type
var mediaType = "tv_show"; // Automatically changed to "episode" after one is tapped
// The default thumb URL for a TV show item
var defaultThumbPath = "img/sample-covers/default-tv-show-icon-poster-size.png";
// The default SQL query statement
var sqlStatement = "requestType=list&mediaType=tv_show&orderBy=title&start=0&range=10";

// Arrange the items onto the page
function arrangeItems() {
    var container = $("#contentRight");

    // Show info only if no TV show is found
    if (items == undefined || items.length == 0) {
        var html = '<div class="info"><div style="text-align: center; margin: 10.5rem 0;"><h2>No TV show.</h2></div></div>';
        container.html(html);
        return;
    }

    // Arrange the items
    var html = '<div>';
    if (items.first().mediaType == "TV_SHOW") {
        // TV Shows
        items.forEach(function (it) {
            html += '\
            <div class="col-lg-6 col-sm-6">\
            <div class="item-card" id="'+ it.imdb + it.season +'">\
            ';
            html += '<div class="col-sm-4">\
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
            /*
             * Target header format:
             * Title (Season x, 20xx)
             */
            html += '<div><span class="item-header">' + it.title + '</span>';
            // Append hidden properties
            // : title, season, runtime, releaseYear
            html += '<span class="item-title" style="display: none;">' + it.title + '</span>';
            html += '<span class="item-season" style="display: none;">' + it.season + '</span>';
            if (it.duartion != undefined || it.duration != 0) {
                html += '<span class="item-runtime" style="display: none;">' + it.runtime + '</span>';
            }
            if (it.releaseYear != 0) {
                html += '<span class="item-releaseYear" style="display: none;">' + it.releaseYear + '</span>';
            }
            // Append season
            html += '<span style="margin-left: 1rem;">(Season ' + it.season;
            // Append year if available
            if (it.releaseYear != 0) {
                html += ', ' + it.releaseYear;
            }
            html += ')</span></div>';

            // Append IMDB and runtime (if available)
            /*
             * Target format:
             * IMDB: xxxxxxx    Runtime: xx min
             */
            // Append IMDB
            html += '<div><span class="item-imdb">IMDB: ' + it.imdb + "</span>";
            // Append runtime if available
            if (it.runtime != undefined && it.runtime != 0) {
                html += '<span style="margin-left: 2rem;">Runtime: ' + formatDuration(mediaType, it.runtime) + '</span>';
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

            // Append rating if available
            if (it.rating != undefined) {
                html += '<p class="item-rating">Rating: ' + it.rating + "</p>";
            }
            // Append plot if available
            if (it.plot != undefined) {
                html += '<p class="item-plot">Plot: ' + it.plot + '</p>';
            }
            // Append a hidden checkbox (structural requirement)
            html += '<input name="selected-items" type="checkbox" class="item-checkbox" value="' + it.imdb + it.season + '"/> <!-- the hidden checkbox -->';
            // Endings
            html += '</div></div></div>';

            // Mask for entering the TV show
            // TODO: Mask!!!
            html += '<div class="enter-tv-show-mask">\
            </div>\
            ';

            // Ending for col-6
            html += "</div>";
        });
    } else if (items.first().mediaType == "EPISODE") {
        items.forEach(function (it) {
            // Episodes
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
            // : SHA256, size, episodeNo, title, runtime, imdb (of TV show), season (of TV show)
            html += '<span class="item-sha256" style="display: none;">' + it.SHA256 + '</span>';
            html += '<span class="item-size" style="display: none;">' + it.size + '</span>';
            html += '<span class="item-episodeNo" style="display: none;">' + it.episodeNo + '</span>';
            if (it.title != undefined) {
                html += '<span class="item-title" style="display: none;">' + it.title + '</span>';
            }
            if (it.runtime != undefined && it.runtime != 0) {
                html += '<span class="item-runtime" style="display: none;">' + it.runtime + '</span>';
            }
            html += '</span>';
            html += '<span class="item-imdb" style="display: none;">' + it.tvShow.imdb + '</span>';
            html += '<span class="item-season" style="display: none;">' + it.tvShow.season + '</span>';

            // Append episode number (+ title) as header
            var header = "";
            header += it.episodeNo;
            if (it.title != undefined) {
                header += ' - ' + it.title;
            }
            html += '<div><span class="item-header">' + header + '</span>';
            html += '</div>';

            // Append runtime if available
            if (it.runtime != undefined && it.runtime != 0) {
                html += '<p>Runtime: ' + formatDuration(mediaType, it.runtime) + '</p>';
            }

            // Append rating if available
            if (it.rating != undefined) {
                html += '<p class="item-rating">Rating: ' + it.rating + '</p>';
            }

            // Append storyline if available
            if (it.storyline != undefined) {
                html += '<p class="item-storyline">Storyline: ' + it.storyline + '</p>';
            }

            // Append a hidden checkbox (structural requirement)
            html += '<input name="selected-items" type="checkbox" class="item-checkbox" value="' +it.SHA256+ '" /> <!-- the hidden checkbox -->';
            // Endings
            html += '</div></div></div></div>';
        });
    }

    html += "</div>";
    container.html(html);
}

// Bind properties in the new property form for validation
function bindProperties() {
    // TODO
}

function triggerEdit(it) {
    // TODO
}

function triggerDelete(det) {
    var SHA256 = $("#"+det).find(".item-SHA256").text();
    var size = $('#'+det).find('.item-size').text();

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
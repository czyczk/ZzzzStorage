/**
 * Created by czyczk on 2017-7-8.
 */
// Media type
var mediaType = "tv_show"; // Automatically changed to "episode" after new items are received
// The default thumb URL for a TV show item
var defaultThumbPath = "img/sample-covers/default-tv-show-icon-poster-size.png";
// The default SQL query statement
var sqlStatement = "requestType=list&mediaType=tv_show&orderBy=title&start=0&range=10";
var lastSqlStatement;

// Arrange the items onto the page
function arrangeItems() {
    var container = $("#contentRight");

    // Show info only if no TV show is found
    if (items == undefined || items.length == 0) {
        var html;
        if (mediaType == "tv_show") {
            html = '<div class="info"><div style="text-align: center; margin: 10.5rem 0;"><h2>No TV show.</h2></div></div>';
        } else if (mediaType == "episode") {
            html = '<div class="info"><div style="text-align: center; margin: 10.5rem 0;"><h2>No episode.</h2></div></div>';
        }
        container.html(html);
        return;
    }

    // Arrange the items
    var html = '<div>';
    if (items[0].mediaType == "TV_SHOW") {
        // TV Shows
        items.forEach(function (it) {
            html += '\
            <div class="col-lg-6 col-sm-6">\
            <div class="item-card" id="'+ fillWithZeroes(it.imdb, 7) + it.season +'">\
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
            <div class="col-sm-8">\
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
            if (it.runtime != undefined && it.runtime != 0) {
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
            html += '<div><span class="item-imdb">IMDB: ' + fillWithZeroes(it.imdb, 7) + "</span>";
            // Append runtime if available
            if (it.runtime != undefined && it.runtime != 0) {
                html += '<span style="margin-left: 2rem;">Runtime: ' + formatDuration(mediaType, it.runtime) + '</span>';
            }
            html += '</div>';

            // Append genres if available
            var genres = it.genre;
            if (genres != undefined && genres.length > 0) {
                html += '<p>Genre: ';
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
            html += '<input name="selected-items" type="checkbox" class="item-checkbox" value="' + fillWithZeroes(it.imdb, 7) + it.season + '"/> <!-- the hidden checkbox -->';
            // Endings
            html += '</div></div></div>';

            // Mask for entering the TV show
            html += '<div class="enter-tv-show-mask">\
            <a class="enter-tv-show-button" href="#" title="See the episodes of this show." value=' + fillWithZeroes(it.imdb, 7) + it.season + '>&#xE26B;</a>\
            </div>\
            ';

            // Ending for col-6
            html += "</div></div>";
        });
    } else if (items[0].mediaType == "EPISODE") {
        // Append a bar to show TV show info and the back button
        html += '<div class="tv-show-info-bar">\
                    <div class="item-title" style="display: none;">' + items[0].tvShow.title + '</div>\
                    <div class="item-imdb" style="display: none;">' + items[0].tvShow.imdb + '</div>\
                    <div class="item-season" style="display: none;">' + items[0].tvShow.season + '</div>\
                    <div>\
                        <a href="#">&#xE96F;</a>\
                        <span>' + items[0].tvShow.title + '</span>\
                        <span style="font-size: 1.4rem; color: rgb(51, 51, 51); margin-left: 0.6rem;">(Season ' + items[0].tvShow.season + ')</span>\
                    </div>\
                 </div>\
            ';
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
            <div class="col-sm-8">\
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

    if (mediaType == "tv_show") {
        // Bind hover handler for TV shows
        $(".item-card").hover(
            function() {
                $(this).find($(".enter-tv-show-mask")).fadeIn(200);
            },
            function() {
                $(this).find($(".enter-tv-show-mask")).fadeOut(200);
            }
        );
        // Bind click handler for TV shows
        $(".enter-tv-show-button").click(
            function() {
                var det = $(this).parent().parent().attr("id");
                var imdb = det.substr(0, 7);
                var season = det.substr(7);
                enterTVShow(imdb, season);
            }
        );
    } else if (mediaType == "episode") {
        // Bind click handler for back button
        $(".tv-show-info-bar").find("a").click(backToTVShow);
    }
}

// An override of loadItems() in mediaPage.js. Click handler for entering masks of TV shows.
function enterTVShow(imdb, season) {
    // Change media type to "episode"
    mediaType = "episode";
    // First query for the total number of episodes of that TV show
    $.ajax({
        url: "FileListGeneratorServlet",
        data: "requestType=count&mediaType=episode&imdb=" + imdb + "&season=" + season,
        type: "post",
        async: false,
        success: updateNumItemsInTotal
    });
    // Then, query for items
    lastSqlStatement = sqlStatement;
    sqlStatement = "requestType=list&mediaType=episode&orderBy=episode_no&imdb=" + imdb + "&season=" + season + "&start=0&range=10";
    $.ajax({
        url: "FileListGeneratorServlet",
        data: sqlStatement,
        type: "post",
        async: false,
        success: updateItems
    });
    // Arrange the items onto the page
    arrangeItems();

    if (items != undefined && items.length > 0)
    // Register a handler for items. If one item card is tapped, invoke the handler.
        $("div.item-card").click(selectAnItem);

    // Reset right sidebar
    resetRightSidebar();
}

// An override of loadItems() in mediaPage.js. Click handler for back button on the top bar of the Episode page.
function backToTVShow() {
    // Change media type back to "tv_show"
    mediaType = "tv_show";
    // Resend the last SQL statement
    sqlStatement = lastSqlStatement;
    $.ajax({
        url: "FileListGeneratorServlet",
        data: sqlStatement,
        type: "post",
        async: false,
        success: updateItems
    });
    // Arrange the items onto the page
    arrangeItems();

    if (items != undefined && items.length > 0)
    // Register a handler for items. If one item card is tapped, invoke the handler.
        $("div.item-card").click(selectAnItem);

    // Reset right sidebar
    resetRightSidebar();
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
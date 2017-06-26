/**
 * Created by czyczk on 2017-6-26.
 */
// 记录当前页面选中项目个数的整数
// Holds the number of items selected in this page
numItemsSelected = 0;
numItemsInTotal = 0;
var items;
var formError = false;

// Register handlers on load
$(function () {
    // Query the servlet for items
    loadItems();
    // If the item card is tapped, invoke the handler.
    $("div.tag").click(selectAnItem);
    // Hover on the right sidebar to reveal the labels
    $("#right-sidebar").hover(revealSidebarLabel, hideSidebarLabel);
    // Download button handler
    $("#download-button").click(handleDownloadButton);
    $("#edit-button").click(handleEditButton);


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
});

// Query the servlet for items
function loadItems() {
    var container = $("#contentRight");
    // First query for the total number of movies
    $.ajax({
        url: "FileListGeneratorServlet",
        data: "requestType=count&mediaType=music",
        type: "post",
        async: false,
        success: updateNumTotal
    });
    // Then, query for items
    $.ajax({
        url: "FileListGeneratorServlet",
        data: "requestType=list&mediaType=music&orderBy=title&start=0&range=10",
        type: "post",
        async: false,
        success: updateItems
    });

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
	    <div class="tag" id="'+ it.SHA256 +'">\
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
            html += "img/sample-covers/default-music-icon-poster-size.png";
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
        if (it.duration != undefined || it.duration != 0) {
            html += '<p>Duration: ' + parseDuration(it.duration) + '</p>';
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
            html += '<p class="item-genre">Genre: '
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
            html += '<p>Rating: ' + it.rating + "</p>";
        }
        // Append a hidden checkbox (structural requirement)
        html += '<input name="selected-items" type="checkbox" class="item-checkbox" value="' +it.SHA256+ '" /> <!-- the hidden checkbox -->';
        // Endings
        html += '</div></div></div></div>';
    });
    html += '</div>';
    container.html(html);
}

function updateNumTotal(data) {
    numItemsInTotal = data;
    console.info("Total number: " + data);
}

function updateItems(data) {
    items = data;
    console.info(data.length + " item(s) returned.");
}

// The handler for item cards
function selectAnItem() {
    // Find the mask (containing the visible checkbox) and toggle it
    var mask = $(this).find(".thumbnail-checkbox-mask").first();
    toggleMask(mask);

    // 找到这个项目的隐藏复选框，改变其勾选状态，维护 numItemsSelected 并更新侧边栏状态
    // Find the hidden checkbox of the item, toggle the checkbox, update numItemsSelected and the sidear
    var hiddenCb = $(this).find(".item-checkbox").first();
    if (hiddenCb.prop("checked")) {
        hiddenCb.prop("checked", false);
        numItemsSelected--;
        updateSidebar("-");
    } else {
        hiddenCb.prop("checked", true);
        numItemsSelected++;
        updateSidebar("+");
    }
}

function updateSidebar(trend) {
    var sidebar = $(".right-sidebar").first();
    if (trend == "+") {
        // 从 0 到 1 则显示侧边栏
        // From 0 to 1: Show the sidebar
        if (numItemsSelected == 1) {
            sidebar.animate({
                opacity: 1.00,
                right: '-18rem'
            }, "fast");
        }
        // 从 1 加至更多则隐藏 Play 选项
        // From 1 to more: Hide "Play"
        else {
            sidebar.find("li").first().slideToggle("normal");
        }
    } else if (trend == "-") {
        // 从更多减少至 1 则显示 Play 选项
        // From more to 1: Show "Play"
        if (numItemsSelected == 1) {
            sidebar.find("li").first().slideToggle("normal");
        }
        // 从 1 减小至 0 则隐藏侧边栏
        // From 1 to 0: Hide the sidebar
        else if (numItemsSelected == 0) {
            sidebar.animate({
                opacity: 0.25,
                right: '-30rem'
            });
        }
    }
}

function toggleMask(mask) {
    if (mask.hasClass("thumbnail-checkbox-mask-visible")) {
        mask.removeClass("thumbnail-checkbox-mask-visible").addClass("thumbnail-checkbox-mask-invisible");
    } else {
        mask.removeClass("thumbnail-checkbox-mask-invisible");
        mask.addClass("thumbnail-checkbox-mask-visible");
    }
}

function revealSidebarLabel() {
    $(this).animate({
        right: '0'
    }, 150);
}

function hideSidebarLabel() {
    $(this).animate({
        right: '-18rem'
    });
}

function parseDuration(duration) {
    var str = "";
    var min = (duration / 60).toFixed(0);
    var sec = duration % 60;
    var secStr = sec;
    if (sec < 10) {
        secStr = "0" + sec;
    }
    return min + ":" + secStr;
}

function handleDownloadButton(){
    $("input:checkbox[name='selected-items']:checked").each(function () {
        triggerDownload($(this).val());
    });
}

function handleEditButton() {
    $("input:checkbox[name='selected-items']:checked").each(function () {
        triggerEdit($(this).val());
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
}

function triggerDownload(it) {
    var SHA256 = $('#'+it).find('.item-sha256').text();
    var size = $('#'+it).find('.item-size').text();
    var title = $('#'+it).find('.item-title').text();

    console.log(SHA256);
    console.log(size);
    console.log(title);
    window.open("DownloadServlet?SHA256=" + SHA256 + "&size=" + size + "&indicatedFilename=" + title);
}
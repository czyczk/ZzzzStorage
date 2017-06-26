// The default thumb URL for a movie item
var defaultThumbPath = "img/sample-covers/default-movie-icon-poster-size.png";
// The default SQL query statement
var sqlStatement = "requestType=list&mediaType=movie&orderBy=title&start=0&range=10";

// 记录当前页面选中项目个数的整数
// Holds the number of items selected in this page
numItemsSelected = 0;
numItemsInTotal = 0;
var items;
var formError = false;

// For editing form
var oldItem;
var newItem;

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
    $('.update-submit').click(editSubmit);

    $(".plot").bind('input propertychange', function () {
        if ($(this).val().length <= 256) {
            $('.msg').html($(this).val().length + '/256 words.');

        } else {
            $(this).val($(this).val().substring(0, 256));
        }
    });

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
});


// Query the servlet for items
function loadItems() {
    var container = $("#contentRight");
    // First query for the total number of movies
    $.ajax({
        url: "FileListGeneratorServlet",
        data: "requestType=count&mediaType=movie",
        type: "post",
        async: false,
        success: updateNumTotal
    });
    // Then, query for items
    $.ajax({
        url: "FileListGeneratorServlet",
        data: sqlStatement,
        type: "post",
        async: false,
        success: updateItems
    });

    // Show info only if no movie is found
    if (items == undefined || items.length == 0) {
        var html = '<div class="info"><h2 style="top: 100px; left: 500px; position:absolute;">No movie.</h2></div>';
        container.html(html);
        return;
    }

    // Arrange the items
    var html = '<div>';
    items.forEach(function (it) {
        html += '\
	    <div class="col-lg-6 col-sm-6">\
	    <div class="tag" id="'+ it.imdb +'">\
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
        html += '<span class="item-sha256" style="display: none;">' + it.SHA256 + '</span>';
        html += '<span class="item-size" style="display: none;">' + it.size + '</span>';
        html += '<span class="item-title" style="display: none;">' + it.title + '</span>';
        // Append year if available
        if (it.releaseYear != 0) {
            html += '<span class="item-releaseYear" style="margin-left: 1rem;">(' + it.releaseYear + ')</span>';
        }
        html += '</div>';

        //Append IMDB and duration (if available)
        // Append IMDB
        html += '<div><span class="item-imdb">IMDB: ' + it.imdb + "</span>";
        // Append duration if available
        if (it.duration != undefined || it.duration != 0) {
            html += '<span class="item-duration" style="margin-left: 2rem;">Duration: ' + it.duration + ' min</span>';
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
            html += '<p>Director: '
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
        html += '</div></div></div></div>';
        html += '</div>';
    });

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

function handleDownloadButton() {
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
    var imdb = $('#'+it).find('.item-imdb').text().substring(5).trim();
    var releaseYear = $('#'+it).find('.item-releaseYear').text().substring(1,5);
    var duration = $('#'+it).find('.item-duration').text().split(" ")[1];
    var plot = $('#'+it).find('.item-plot').text().substring(6);
    var thumbUrl = $('#'+it).find('.thumbnail').attr('src');
    console.log(duration);
    $('#size').text((size/1024/1024).toFixed(2)+' MB');
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
        $('#thumbUrl').val(defaultThumbPath);

    oldItem = {
        "SHA256": $('#'+it).find('.item-sha256').text(),
        "size": size,
        "imdb": imdb
    };
}

function triggerDownload(it) {
    console.info(it);
    // var contextData = it.getParent().getParent();
    // var title = it.find("span.item-title").html;
    // var SHA256 = it.findByName("SHA256").val();
    // var size = it.findByName("size").val();

    var SHA256 = $('#'+it).find('.item-sha256').text();
    var size = $('#'+it).find('.item-size').text();
    var title = $('#'+it).find('.item-title').text();

    console.log(SHA256);
    console.log(size);
    console.log(title);
    window.open("DownloadServlet?SHA256=" + SHA256 + "&size=" + size + "&indicatedFilename=" + title);
}

function editSubmit() {
    // Collect media type
    var mediaType = $('#type').text();

    // Collect new info
    newItem = {
        "SHA256": oldItem.SHA256,
        "size": oldItem.size,
        "title": $('#title').val(),
        "imdb": $('#imdb').val(),
        "releaseYear": $('#releaseYear').val(),
        "duration": $('#duration').val(),
        "plot": $('#plot').val(),
        "thumbUrl": $('#thumbUrl').val()
    };

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

function handleUpdateSuccess(data) {
    if (data.messageType == "success") {
        // Reload the content
        $('#Update').modal('hide');
        loadItems();
    } else {
        // Alert error
        alert(data.message);
    }
}
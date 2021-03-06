var tasks;
var keepQuerying = false;

$( function() {
	loadDownloadedTasks();
});

$('.downloaded').click(function(){
	$('#downloaded').show();
	$('#downloading').hide();
	$(this).parent().addClass('active');
	$('.downloading').parent().removeClass('active');
});

$('.downloading').click(function(){
	$('#downloaded').hide();
	$('#downloading').show();
	$(this).parent().addClass('active');
	$('.downloaded').parent().removeClass('active');
	loadDownloadingTasks();
});

function loadDownloadedTasks() {
	$.ajax({
		url: "TransferTaskServlet",
		data: "requestType=downloaded",
		type: "post",
		success: handleUpDownloadedTasks
	})
}

function loadDownloadingTasks() {
	$.ajax({
		url: "TransferTaskServlet",
		data: "requestType=downloading",
		type: "post",
		success: handleUpDownloadingTasks
	})
}



function handleUpDownloadedTasks(data) {
    tasks = data;
    container = $("#list");

    if(tasks === undefined || tasks.length === 0) {
        container.html("<div style='text-align: center; color: rgb(119,119,119); margin: 10% 0;'><h2>No downloaded task.</h2></div>");
        return;
    }

    keepQuerying = true;
    var html = "";
    var numTasksUnfolded = 0;
    tasks.forEach(function(it){
        var item = it.item;
        html += '\
        <div class="row">\
        <div class="col-sm-offset-1 col-sm-2">\
        ';
        // Append cover
        html += '<img src="';
        if (item.thumbUrl === undefined) {
            html += 'img/sample-covers/default-upload-icon-poster-size.png';
        } else {
            html += item.thumbUrl;
        }
        html += '" alt="Loading..." class="img-responsive thumbnail" />';

        html += '\
        </div>\
        <div class="col-sm-8">\
        <div style="margin-top: 2rem;">\
        ';
        // Append indicated filename
        html += '<h4 style="display: inline-block;text-decoration: none;font-size: 2.0rem;color: rgb(51, 122, 183);">' + it.indicatedFilename + '</h4><br />';
        // Append media type
        html += '<span>' + item.mediaType.charAt(0) + item.mediaType.substr(1).toLowerCase() + '</span><sapn style="margin-left: 1rem;">' + formatSize(item.size) + '</sapn><br />';
        html += '\
        </div>\
        </div>\
        </div>\
        ';
        if (++numTasksUnfolded < tasks.length) {
            html += '<hr />';
        }
    });
    container.html(html);
}

function handleUpDownloadingTasks(data) {
    tasks = data;
    container = $("#downloading");

    if (tasks === undefined || tasks.length === 0) {
        container.html("<div style='text-align: center; color: rgb(119,119,119); margin: 10% 0;'><h2>No uploading task.</h2></div>");
        return;
    }

    keepQuerying = true;
    var html = "";
    var numTasksUnfolded = 0;

    tasks.forEach(function(it) {
        var item = it.item;
        html += '\
        <div class="row">\
        <div class="col-sm-2">\
        ';
        // Append cover
        html += '<img src="';
        if (item.thumbUrl === undefined) {
            html += 'img/sample-covers/default-upload-icon-poster-size.png';
        } else {
            html += item.thumbUrl;
        }
        html += '" alt="Loading..." class="img-responsive thumbnail" />';

        html += '\
        </div>\
        <div class="col-sm-9">\
        <div style="margin-top: 2rem;">\
        ';
        // Append title
        html += '<h4>' + item.title + '</h4>';
        // Append media type
        html += '<p>' + item.mediaType.charAt(0) + item.mediaType.substr(1).toLowerCase() + '</p>';

        html += '\
        </div>\
        </div>\
        <div class="col-sm-9">\
        <div class="progress" style="margin-top: 20px;">\
        ';

        // Calculate progress and append the progress bar
        var progress = it.bytesTransferred / item.size * 100;
        var progressBarClass = parseProgress(progress);
        html += '<div class="' + progressBarClass + '" role="progressbar" aria-valuenow="' + progress +
            '"aria-valuemin="0" aria-valuemax="100" style="width: ' + progress + '%;">';
        html += '<span>' + progress.toFixed(2) + '%</span>';
        html += '</div></div></div></div>';

        if (++numTasksUnfolded < tasks.length) {
            html += '<hr />';
        }
    });

    container.html(html);
}

function parseProgress(progress) {
    if (progress < 20) {
        return "progress-bar progress-bar-warning";
    } else if (progress < 90) {
        return "progress-bar";
    } else {
        return "progress-bar progress-bar-success";
    }
}
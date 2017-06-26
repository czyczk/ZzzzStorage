var keepQuerying = false;
var tasks;

$(function () {
    loadUploadedTasks();
});

$('.uploaded').click(function(){
	$('#uploaded').show();
	$('#uploading').hide();
	$(this).parent().addClass('active');
	$('.uploading').parent().removeClass('active');


});

$('.uploading').click(function(){
    keepQuerying = false;

	$('#uploaded').hide();
	$('#uploading').show();
	$(this).parent().addClass('active');
	$('.uploaded').parent().removeClass('active');
    loadUploadingTasks();

});

function loadUploadedTasks() {
    $.ajax({
        url: "TransferTaskServlet",
        data: "requestType=uploaded",
        type: "post",
        success: handleUploadedTasks
    });
}

function loadUploadingTasks() {
    $.ajax({
        url: "TransferTaskServlet",
        data: "requestType=uploading",
        type: "post",
        success: handleUploadingTasks
    });
}

function handleUploadedTasks(data) {
    tasks = data;
    container = $("#list");

    if(tasks == undefined || tasks.length == 0) {
        container.html("<div style='text-align: center; color: rgb(119,119,119); margin: 10% 0;'><h2>No uploaded task.</h2></div>");
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
        if (item.thumbUrl == undefined) {
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
        // Append title
        html += '<h4 style="display: inline-block;text-decoration: none;font-size: 2.0rem;color: rgb(51, 122, 183);">' + item.title + '</h4><span style="margin-left: 1rem;">(' + item.releaseYear + ')</span><br>';
        // Append media type
        html += '<span>' + item.mediaType.charAt(0) + item.mediaType.substr(1).toLowerCase() + '</span><sapn style="margin-left: 1rem;">' + (item.size / 1024 / 1024).toFixed(2) + 'MB</sapn><br>';
        html += '<br><p>Description: ';
        if(item.plot == undefined) {
            html += 'No description.';
        } else {
            html += item.plot;
        }
        html += '</p>';
        html += '<p>Duration: ';
        if(item.duration == undefined) {
            html += '';
        } else {
            html += item.duration;
        }
        html += '</p>';
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

function handleUploadingTasks(data) {
    tasks = data;
    container = $("#uploading");

    if (tasks == undefined || tasks.length == 0) {
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
        if (item.thumbUrl == undefined) {
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
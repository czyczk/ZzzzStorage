<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<script src="jQuery/jquery.min.js"></script>
	<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="css/nav.css">
	<link rel="stylesheet" href="css/main-page.css">
	<link rel="stylesheet" href="css/media-page.css" />
	<script src="js/bootstrap/bootstrap.min.js"></script>
	<script src="js/prefixfree.min.js"></script>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Music</title>
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top navigation" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" style="margin: 0; padding: 0;">
				<h3 style="margin-top: 13px;">Media Vault</h3></a>
			<button type="button" data-toggle="collapse" class="navbar-toggle" data-target="#navigation">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse" id="navigation">
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a class="glyphicon glyphicon-home" href="main.jsp">&nbsp;Home</a>
				</li>
				<li>
					<a href="movie.jsp" class="glyphicon glyphicon-film">&nbsp;Movie</a>
				</li>
				<li>
					<a href="TVShow.jsp" class="glyphicon glyphicon-facetime-video">&nbsp;TV<span style="color: rgb(248,248,248);">-</span>Show</a>
				</li>
				<li class="active">
					<a href="music.jsp" class="glyphicon glyphicon-music">&nbsp;Music</a>
				</li>
				<li>
					<a href="upload.jsp" class="glyphicon glyphicon-open">&nbsp;Upload</a>
				</li>
				<li>
					<a href="download.jsp" class="glyphicon glyphicon-save">&nbsp;Download</a>
				</li>
				<li>
					<a class="glyphicon glyphicon-log-out" href="welcome.jsp">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a>
				</li>
			</ul>
		</div>
	</div>
</nav>

<div id="contentWrapper">
	<div id="contentLeft">
		<div class="leftNav">
			<%--<dl>--%>
				<%--<dt><a href="#">Comedy</a></dt>--%>
				<%--<dt><a href="#">Action</a></dt>--%>
				<%--<dt><a href="#">Fantasy</a></dt>--%>
				<%--<dt><a href="#">Thriller</a></dt>--%>
				<%--<dt><a href="#">Adventure</a></dt>--%>
				<%--<dt><a href="#">Other</a></dt>--%>
			<%--</dl>--%>
				<div class="orderWrapper">
					<div class="styled-select">
						<select class="orderby" title="Order by..." id="orderby">
							<option value="">Order By...</option>
							<option value="title">Title</option>
							<option value="album">Album</option>
						</select>
					</div>
				</div>
		</div>
	</div>

	<div id="contentRight">

	</div>
</div>

<!-- Sidebar -->
<div class="right-sidebar" id="right-sidebar">
	<ul>
		<li>
			<a href="play.jsp" id="play-button"><span class="glyphicon glyphicon-play right-sidebar-icon"></span>Play</a>
		</li>
		<li>
			<a href="#" id="download-button"><span class="glyphicon glyphicon-cloud-download right-sidebar-icon"></span>Download</a>
		</li>
		<li>
			<a href="#" id="delete-button"><span class="glyphicon glyphicon-trash right-sidebar-icon"></span>Delete</a>
		</li>
		<li>
			<a id="edit-button" href data-toggle="modal" data-target="#property-form"><span class="glyphicon glyphicon-info-sign right-sidebar-icon"></span>Edit properties</a>
		</li>
	</ul>
</div>

<div class="modal fade" id="property-form" tabindex="-1" role="dialog" aria-labelledby="UpdateLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 class="modal-title" id="UpdateLabel" style = "text-align: center; color: black">Edit</h3>
			</div>
			<div class="modal-body">
				<form>
					<div class="basic-info">
						<h4 style = "color: black">Basic Information</h4>
						<div class="form-group">
							<label for="title" class="control-label" style="color: #909090;">Title: </label>
							<input type="text" class="form-control" id="title" name="title">
							<div class="errorTitle-required error">Title is required.</div>
						</div>
						<div class="form-group">
							<label for="type" class="control-label" style="color:#909090;">Type: </label>
							<span id="type" name="type" class="text-primary">Music</span>
						</div>
						<div class="form-group">
							<label for="size" class="control-label" style="color: #909090;">Size: </label>
							<span class="text-primary" id="size" name="size"></span>
						</div>
						<div class="form-group">
							<label for="album" class="control-label" style="color: #909090;">Album: </label>
							<input type="text" class="form-control" id="album" name="album">
							<div class="errorAlbum-required error">Album is required.</div>
						</div>
					</div>
					<hr>
					<div class="optional-info">
						<h4 style = "color: black">Optional Information</h4>
						<div class="form-group">
							<label for="track" class="control-label" style="color: #909090;">Track: </label>
							<input type="number" class="form-control" id="track" name="track">
						</div>
						<div class="form-group artist">
							<label for="artist" class="control-label" style="color: #909090;">Artist: </label>
							<input type="text" class="form-control" id="artist" name="artist">
						</div>
						<div class="form-group">
							<label class="control-label" style="color: #909090;">Select genre: </label>
							<select class="form-control" id="genre">
								<option value="Rock">Rock</option>
								<option value="Action">Action</option>
								<option value="Fantasy">Fantasy</option>
								<option value="Thriller">Thriller</option>
								<option value="Adventure">Adventure</option>
								<option value="Others">Other</option>
							</select>
						</div>
						<div class="form-group">
							<label for="duration" class="control-label" style="color: #909090;">Duration: </label>
							<input type="time" id="duration" class="form-control" name="duration">
						</div>
						<div class="form-group">
							<label for="thumbUrl" class="control-label" style="color: #909090;">Thumbnail URL: </label>
							<input type="text" class="form-control" id="thumbUrl" name="thumbUrl">
						</div>
					</div>
					<hr>
					<div class="form-group">
						<div class="row">
							<button type="button" class="update-submit btn btn-primary col-lg-10 col-lg-offset-1 col-sm-offset-1 col-sm-10 col-xs-offset-1 col-xs-10">OK</button>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</div>
<script src="js/music.js"></script>
<script src="js/mediaPage.js"></script>
<script src="js/util.js"></script>
<script src="js/mediaPageRightSidebar.js"></script>

</body>
<!--
    Authors:
        2684232910@qq.com, 564108186@qq.com, 601347015@qq.com
    Date: 2017-06-12
    Desc: Movie Page
-->
</html>
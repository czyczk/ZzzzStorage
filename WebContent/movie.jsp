<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<script src="jQuery/jquery.min.js"></script>
		<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="css/nav.css">
		<link rel="stylesheet" href="css/main-page.css">
		<link rel="stylesheet" href="css/movie.css" />
		<script src="js/bootstrap/bootstrap.min.js"></script>
		<script src="js/prefixfree.min.js"></script>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Movie</title>
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
						<li class="active">
							<a href="movie.jsp" class="glyphicon glyphicon-film">&nbsp;Movie</a>
						</li>
						<li>
							<a href="TVShow.jsp" class="glyphicon glyphicon-facetime-video">&nbsp;TV<span style="color: rgb(248,248,248);">-</span>Show</a>
						</li>
						<li>
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
				<div class="left-sidebar leftNav">
					<dl>
						<dt><a href="#">Comedy</a></dt>
						<dt><a href="#">Action</a></dt>
						<dt><a href="#">Fantasy</a></dt>
						<dt><a href="#">Thriller</a></dt>
						<dt><a href="#">Adventure</a></dt>
						<dt><a href="#">Other</a></dt>
					</dl>
				</div>
			</div>

			<div id="contentRight"></div>
		</div>
		<!-- Sidebar -->
		<div class="right-sidebar" id="right-sidebar">
			<ul>
				<li>
					<a href="play.jsp"><span class="glyphicon glyphicon-play right-sidebar-icon"></span>Play</a>
				</li>
				<li>
					<a href="#" id="download-button"><span class="glyphicon glyphicon-cloud-download right-sidebar-icon"></span>Download</a>
				</li>
				<li>
					<a href="#" id="delete-button"><span class="glyphicon glyphicon-trash right-sidebar-icon"></span>Delete</a>
				</li>
				<li>
					<a href data-toggle="modal" data-target="#Update"><span class="glyphicon glyphicon-info-sign right-sidebar-icon"></span>Edit properties</a>
				</li>
			</ul>
		</div>
		<script src="js/movie.js"></script>

	</body>
	<!--
		Authors:
			2684232910@qq.com, 564108186@qq.com, 601347015@qq.com
		Date: 2017-06-12
		Desc: Movie Page
	-->
</html>
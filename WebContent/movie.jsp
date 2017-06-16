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
							<a href="movie.jsp" data-toggle="tab" class="glyphicon glyphicon-film">&nbsp;Movie</a>
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
							<a data-toggle="tab" class="glyphicon glyphicon-log-out" href="#">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>

		<div id="contentWrapper">
			<div id="contentLeft">
				<div class="leftNav">
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

			<div id="contentRight">
				<div class="container">

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-movie1.png" class="thumbnail" />
									</div>
								</div>
							</div>
							<!-- End of .tag -->
							<div class="col-sm-7">
								<div class="header">
									<a href="#">Pirates of Caribbean</a>
									<p>Comedy</p>
									<p>6-12-2017 13:25:30</p>
									<p>An adventure story of a infamous pirates.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-movie2.jpg" class="thumbnail" />
									</div>
								</div>
							</div>
							<div class="col-sm-7">
								<div class="header">
									<a href="#">Midnight Cowboy</a>
									<p>Romantic</p>
									<p>6-11-2017 13:25:30</p>
									<p>An adventure story of a cowboy.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-movie3.jpg" class="thumbnail" />
									</div>
								</div>
							</div>
							<div class="col-sm-7">
								<div class="header">
									<a href="#">Musician</a>
									<p>Music</p>
									<p>6-10-2017 13:25:30</p>
									<p>A story of a musician.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-TVShow.jpg" class="thumbnail" />
									</div>
								</div>
							</div>
							<div class="col-sm-7">
								<div class="header">
									<a href="#">The Flash</a>
									<p>Action</p>
									<p>6-9-2017 13:25:30</p>
									<p>Adventure stories of a super hero.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-TVShow2.jpg" class="thumbnail" />
									</div>
								</div>
							</div>
							<div class="col-sm-7">
								<div class="header">
									<a href="#">In the Flesh</a>
									<p>Thriller</p>
									<p>6-11-2017 13:25:30</p>
									<p>A story of a someone.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-TVShow3.jpg" class="thumbnail" />
									</div>
								</div>
							</div>
							<div class="col-sm-7">
								<div class="header">
									<a href="#">Red Oak</a>
									<p>Story</p>
									<p>6-10-2017 13:25:30</p>
									<p>A story of boys and girls.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-6 col-sm-6">
						<div class="tag">
							<div class="col-sm-5">
								<div class="thumbnail-container">
									<div class="thumbnail-checkbox-mask thumbnail-checkbox-mask-invisible">
										<span class="circle-pattern">&#xEC61;</span>
									</div>
									<div class="thumbnail-image">
										<img src="img/sample-covers/downloaded-TVShow4.jpg" class="thumbnail" />
									</div>
								</div>
							</div>
							<div class="col-sm-7">
								<div class="header">
									<a href="#">Vinyl</a>
									<p>6-9-2017 13:25:30</p>
									<p>A story of a man.</p>
									<input name="selected-items" type="checkbox" class="item-checkbox" /> <!-- the hidden checkbox -->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Sidebar -->
		<div class="right-sidebar" id="right-sidebar">
			<ul>
				<li>
					<a href="play.jsp"><span class="glyphicon glyphicon-play right-sidebar-icon"></span>Play</a>
				</li>
				<li>
					<a href="#"><span class="glyphicon glyphicon-cloud-download right-sidebar-icon"></span>Download</a>
				</li>
				<li>
					<a href="#"><span class="glyphicon glyphicon-trash right-sidebar-icon"></span>Delete</a>
				</li>
				<li>
					<a href="#"><span class="glyphicon glyphicon-info-sign right-sidebar-icon"></span>Edit properties</a>
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
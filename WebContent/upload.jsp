<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="jQuery/jquery.min.js"></script>
    	<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
    	<link rel="stylesheet" href="css/nav.css">
    	<link rel="stylesheet" href="css/main-page.css">
    	<link rel="stylesheet" href="css/upload-page.css" />
    	<link rel="stylesheet" href="css/bootstrap/fileinput.min.css">
    	<link rel="stylesheet" href="css/uploading.css" />
		<link rel="stylesheet" href="css/bootstrap/bootstrap-tagsinput.css">
    	<script src="js/bootstrap/bootstrap.min.js"></script>
    	<script src="node_modules/bootstrap-fileinput/js/fileinput.js"></script>
    	<script src="node_modules/crypto-js/crypto-js.js"></script>
		<script src="node_modules/jquery.form.min.js"></script>
		<script src="js/bootstrap/bootstrap-tagsinput.js"></script>
		<script src="bower_components/typeahead.js/dist/typeahead.bundle.js"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload</title>
    </head>
    <body>
    	<nav class="navbar navbar-default navbar-fixed-top navigation" role="navigation">
    		<div class="container-fluid">
    			<div class="navbar-header">
    				<a class="navbar-brand" style="margin: 0; padding: 0;"><h3 style="margin-top: 13px;">Media Vault</h3></a>
    				<button type="button" data-toggle="collapse" class="navbar-toggle" data-target="#navigation">
    					<span class="icon-bar"></span>
    					<span class="icon-bar"></span>
    					<span class="icon-bar"></span>
    				</button>
    			</div>
    			<div class="collapse navbar-collapse" id="navigation">
    				<ul class="nav navbar-nav navbar-right">
    					<li><a class="glyphicon glyphicon-home" href="main.jsp">&nbsp;Home</a></li>
    					<li><a href="movie.jsp" class="glyphicon glyphicon-film">&nbsp;Movie</a></li>
    					<li><a href="TVShow.jsp" class="glyphicon glyphicon-facetime-video">&nbsp;TV<span style="color: rgb(248,248,248);">-</span>Show</a></li>
    					<li><a href="music.jsp" class="glyphicon glyphicon-music">&nbsp;Music</a></li>
    					<li class="active"><a data-toggle="tab" href="upload.jsp" class="glyphicon glyphicon-open">&nbsp;Upload</a></li>
    					<li><a href="download.jsp" class="glyphicon glyphicon-save">&nbsp;Download</a></li>
    					
    					<li><a class="glyphicon glyphicon-log-out" href="welcome.jsp">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a></li>
    				</ul>
    			</div>
    		</div>
    	</nav>
    	
    	<div class="container">
	    	<div class="main-content">
	    		<div class="header">
	    			<div class="navigation">
	    				<ol class="breadcrumb">
	    					<li><a href data-toggle="modal" data-target="#myModal" class="glyphicon glyphicon-plus pull-right"></a></li>
	    					<li><a href="#" class="uploading">Uploading</a></li>
	    					<li class="active"><a href="#" class="uploaded">Uploaded</a></li>	
	    				</ol>
	    			</div>
	    		</div>
	    		
	    		<div id="uploaded">
					<div id="list">

					</div>
                </div>
                
                <div id="uploading">

                </div> <!-- End of Uploading -->

	    	</div> <!-- End of MainContent -->
    	</div>
    	
    	<!--
        	作者：601347015@qq.com
        	时间：2017-06-12
        	描述：modal
        -->
    	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    		<div class="modal-dialog">
    			<div class="modal-content">
    				<div class="modal-header">
    					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    					<h4 class="modal-title" id="myModalLabel" style="text-align: center; color: black;">Upload</h4>
    				</div>
    				<div class="modal-body">
    					<form id="upload-form" enctype="multipart/form-data">
							<div class="form-group">
								<label class="control-label" for="input-2" style="color: #909090;">Select file...</label>
								<input id="input-2" name="inputFile" type="file" class="file" multiple data-show-upload="false" data-show-caption="true" style="background-color: transparent; border: rgb(192, 211, 226) 1px solid; color: black;">
							</div>
							<div class="form-group">
								<label for="type" class="control-label" style="color: #909090;">Type: </label>
								<select class="form-control" id="type" name="mediaType">
									<option value="Movie" select="selected">Movie</option>
									<option value="Music" >Music</option>
									<option value="TVShow">TV Show</option>
									<option value="Episode">Episode</option>
								</select>
							</div>
							<div class="form-group imdb">
								<label for="imdb" class="control-label" style="color: #909090;">IMDB: </label>
								<input type="number" class="form-control" name="imdb" id="imdb">
								<div class="errorIMDB-required error">IMDB is required.</div>
								<div class="error-range error">IMDB should be a 7-digit number.</div>
							</div>
	                        <div class="form-group recipient-name">
	                            <label for="recipient-name" class="control-label" style="color: #909090;">Title: </label>
	                            <input type="text" class="form-control" id="recipient-name" name="title">
								<div class="title" style="color: #909090"></div>
								<div class="errorTitle-required error">Title is required.</div>
							</div>
							<div class="form-group season">
								<label for="season" class="control-label" style="color: #909090;">Season: </label>
								<input type="number" class="form-control" id="season" name="season">
								<div class="error-season error">Season is from 1 to 20.</div>
								<div class="errorSeason-required error">Season is required.</div>
							</div>
							<div class="form-group releaseYear">
								<label for="year" class="control-label" style="color: #909090;">Release year: </label>
								<select name="releaseYear" id="year" class="form-control">
									<% for(int year = 2017; year > 1900; year --) {
									 	%><option value="<%=year%>"><%=year%></option>
									<%}%>
								</select>
							</div>
							<div class="form-group album">
								<label for="album" class="control-label" style="color: #909090;">Album: </label>
								<input type="text" class="form-control" id="album" name="album">
								<div class="errorAlbum-required error">Album is required.</div>
							</div>
							<div class="form-group plot">
								<label for="desc" class="control-label" style="color: #909090;">Plot: </label>
								<textarea class="form-control plot" id="desc" name="plot"></textarea>
								<div class="msg" style="color: #909090"></div>
							</div>
							<div class="form-group track">
								<label for="track" class="control-label" style="color: #909090;">Track: </label>
								<input type="number" class="form-control" id="track" name="track">
							</div>
							<div class="form-group duration">
								<label for="duration" class="control-label" style="color: #909090;">Duration: </label>
								<input type="time" class="form-control" id="duration" name="duration">
							</div>
							<div class="form-group runtime">
								<label for="runtime" class="control-label" style="color: #909090;">Runtime (in minutes): </label>
								<input type="time" class="form-control" id="runtime" name="runtime">
							</div>
							<div class="form-group director">
								<label for="director" class="control-label" style="color: #909090;">Director: </label>
								<input type="text" class="form-control" id="director" name="director">
							</div>
							<div class="form-group artist">
								<label for="artist" class="control-label" style="color: #909090;">Artist: </label>
								<input type="text" class="form-control" id="artist" name="artist">
							</div>
							<div class="form-group thumbUrl">
								<label for="thumbUrl" class="control-label" style="color: #909090;">Thumbnail URL: </label>
								<input type="text" class="form-control" id="thumbUrl" name="thumbUrl">
								<div class="thumburl" style="color: #909090"></div>
							</div>
							<div class="form-group rating">
								<label for="rating" class="control-label" style="color: #909090;">Rating: </label>
								<input type="number" class="form-control" id="rating" name="rating">
								<div class="error-rating error">Rating is from 1 to 10.</div>
							</div>


							<div class="form-group episode">
								<label for="episode" class="control-label" style="color: #909090">Episode Number: </label>
								<input type="number" class="form-control" id="episode" name="episode">
								<div class="error-episode error">Episode is begin from 1.</div>
							</div>

							<div class="form-group titleOfEpisode">
								<label for="titleOfEpisode" class="control-label" style="color: #909090">Title of This Episode: </label>
								<input type="text" class="form-control" id="titleOfEpisode" name="titleOfEpisode">
								<div class="msgEpisodeTitle" style="color:#909090;"></div>
							</div>

							<div class="form-group episodeRuntime">
								<label for="episodeRuntime" class="control-label" style="color: #909090">Runtime (in minutes): </label>
								<input type="number" class="form-control" id="episodeRuntime" name="episodeRuntime">
							</div>

							<div class="form-group episodeThumbnail">
								<label for="episodeThumbnail" class="control-label" style="color:#909090;">Thumb URL: </label>
								<input type="text" class="form-control" id="episodeThumbnail" name="episodeThumbnail">
								<div class="msgEpisodeThumb" style="color:#909090;"></div>
							</div>

							<div class="form-group episodeRating">
								<label for="episodeRating" class="control-label" style="color:#909090;">Rating: </label>
								<input type="text" class="form-control" id="episodeRating" name="episodeRating">
							</div>

							<div class="form-group storyLine">
								<label for="storyLine" class="control-label" style="color: #909090;">Story Line:</label>
								<textarea class="form-control" id="storyLine" name="storyLine"></textarea>
								<div class="msgStoryLine" style="color: #909090"></div>
							</div>

	                       <div class="form-group genre">
	                       		<label for="genre" class="control-label" style="color: #909090;">Enter genre: </label>
	                       		<%--<select class="form-control category movie">--%>
	                       			<%--<option value="Comedy" selected="selected">Comedy</option>--%>
	                       			<%--<option value="Action">Action</option>--%>
	                       			<%--<option value="Fantasy">Fantasy</option>--%>
	                       			<%--<option value="Thriller">Thriller</option>--%>
	                       			<%--<option value="Adventure">Adventure</option>--%>
	                       			<%--<option value="Others">Other</option>--%>
	                       		<%--</select>--%>
	                       		<%--<select class="form-control category tvshow">--%>
	                       			<%--<option value="Criminal" selected="selected">Criminal</option>--%>
	                       			<%--<option value="Action">Action</option>--%>
	                       			<%--<option value="Fantasy">Fantasy</option>--%>
	                       			<%--<option value="Thriller">Thriller</option>--%>
	                       			<%--<option value="Adventure">Adventure</option>--%>
	                       			<%--<option value="Others">Other</option>--%>
	                       		<%--</select>--%>
	                       		<%--<select class="form-control category music">--%>
	                       			<%--<option value="Rock" selected="selected">Rock</option>--%>
	                       			<%--<option value="Action">Action</option>--%>
	                       			<%--<option value="Fantasy">Fantasy</option>--%>
	                       			<%--<option value="Thriller">Thriller</option>--%>
	                       			<%--<option value="Adventure">Adventure</option>--%>
	                       			<%--<option value="Others">Other</option>--%>
	                       		<%--</select>--%>
							    <input type="text" name="genre" id="genre" data-role="tagsinput"/>
	                       </div>
	                       <br />
	                       <br />
	                       <br />
							<div class="form-group">
								<div class="row">
		                        	<button type="button" class="upload-submit btn btn-primary col-lg-10 col-lg-offset-1 col-sm-offset-1 col-sm-10 col-xs-offset-1 col-xs-10">Upload Now</button>
								</div>
							</div>
                    	</form>
    				</div>
    			</div>
    		</div>
    	</div>
    	
		<script src="js/upload.js"></script>
    	<script src="js/uploadForm.js"></script>
    	<script src="js/FileUpload.js"></script>
 	</body>
</html>
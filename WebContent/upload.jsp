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
    	<script src="js/bootstrap/bootstrap.min.js"></script>
    	<script src="node_modules/bootstrap-fileinput/js/fileinput.js"></script>
    	<script src="node_modules/crypto-js/crypto-js.js"></script>
		<script src="node_modules/jquery.form.min.js"></script>
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
						<%--<div class="row">--%>
							<%--<div class="col-sm-2">--%>
								<%--<img src="img/sample-covers/downloaded-movie1.png" alt="Loading..." class="img-responsive thumbnail" />--%>
							<%--</div>--%>
							<%--<div class="col-sm-9">--%>
								<%--<div>--%>
									<%--<h4>Title</h4>--%>
									<%--<p>Media Type</p>--%>
								<%--</div>--%>
							<%--</div>--%>

						<%--</div>--%>

						<%--<hr />--%>

						<%--<div class="row">--%>
							<%--<div class="col-sm-2">--%>
								<%--<img src="img/sample-covers/downloaded-movie2.jpg" alt="Loading..." class="img-responsive thumbnail" />--%>
							<%--</div>--%>
							<%--<div class="col-sm-9">--%>
								<%--<div style="margin-top: 20px;">--%>
									<%--<h4>Title</h4>--%>
									<%--<p>Media Type</p>--%>
								<%--</div>--%>
							<%--</div>--%>
						<%--</div>--%>
					</div>
                </div>
                
                <div id="uploading">
                    <%--<div class="row">--%>
                        <%--<div class="col-sm-2">--%>
                            <%--<img src="img/sample-covers/downloaded-movie1.png" alt="Loading..." class="img-responsive thumbnail" />--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div>--%>
                                <%--<h4>Title</h4>--%>
                                <%--<p>Media Type</p>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div class="progress" style="margin-top: 20px;">--%>
                                <%--<div class="progress-bar" role="progressbar" aria-valuenow="40"--%>
                                    <%--aria-valuemin="0" aria-valuemax="100" style="width: 40%;">--%>
                                    <%--<span>40%</span>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<hr />--%>

                    <%--<div class="row">--%>
                        <%--<div class="col-sm-2">--%>
                            <%--<img src="img/sample-covers/downloaded-movie2.jpg" alt="Loading..." class="img-responsive thumbnail" />--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div style="margin-top: 20px;">--%>
                                <%--<h4>Title</h4>--%>
                                <%--<p>Media Type</p>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div class="progress" style="margin-top: 20px;">--%>
                                <%--<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="20"--%>
                                    <%--aria-valuemin="0" aria-valuemax="100" style="width: 20%;">--%>
                                    <%--<span>20%</span>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <%--<hr />--%>

                    <%--<div class="row">--%>
                        <%--<div class="col-sm-2">--%>
                            <%--<img src="img/sample-covers/downloaded-movie3.jpg" alt="Loading..." class="img-responsive thumbnail" />--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div style="margin-top: 20px;">--%>
                                <%--<h4>Title</h4>--%>
                                <%--<p>Media Type</p>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<div class="progress" style="margin-top: 20px;">--%>
                                <%--<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="90"--%>
                                <%--aria-valuemin="0" aria-valuemax="100" style="width: 90%;">--%>
                                    <%--<span>90%</span>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
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
								<input id="input-2" name="inputFile" type="file" class="file" multiple data-show-upload="false" data-show-caption="true">
							</div>
							<div class="form-group">
								<label for="type" class="control-label" style="color: #909090;">Type: </label>
								<select class="form-control" id="type" name="mediaType">
									<option value="Movie" select="selected">Movie</option>
									<option value="Music">Music</option>
									<option value="TVShow">TV Show</option>
								</select>
							</div>
							<div class="form-group">
								<label for="imdb" class="control-label" style="color: #909090;">IMDB: </label>
								<input type="number" class="form-control" name="imdb" id="imdb">
							</div>
	                        <div class="form-group">
	                            <label for="recipient-name" class="control-label" style="color: #909090;">Title: </label>
	                            <input type="text" class="form-control" id="recipient-name" name="title">
	                        </div>
							<div class="form-group">
								<label for="year" class="control-label" style="color: #909090;">Release year: </label>
								<select name="releaseYear" id="year" class="form-control">
									<% for(int year = 2017; year > 1900; year --) {
									 	%><option value="<%=year%>"><%=year%></option>
									<%}%>
								</select>
							</div>
							<div class="form-group">
								<label for="desc" class="control-label" style="color: #909090;">Plot: </label>
								<textarea class="form-control plot" id="desc" name="plot"></textarea>
								<div class="msg"></div>
							</div>
							<div class="form-group">
								<label for="duration" class="control-label" style="color: #909090;">Duration: </label>
								<input type="time" class="form-control" id="duration" name="duration">
							</div>
							<div class="form-group">
								<label for="thumbUrl" class="control-label" style="color: #909090;">Thumbnail url: </label>
								<input type="text" class="form-control" id="thumbUrl" name="thumbUrl">
							</div>
							<div class="form-group">
								<label for="rating" class="control-label" style="color: #909090;">Rating: </label>
								<input type="number" class="form-control" id="rating" name="rating">
							</div>

	                       <div class="form-group">
	                       		<label class="control-label" style="color: #909090;">Select genre: </label>
	                       		<select class="form-control category movie">
	                       			<option value="Comedy">Comedy</option>
	                       			<option value="Action">Action</option>
	                       			<option value="Fantasy">Fantasy</option>
	                       			<option value="Thriller">Thriller</option>
	                       			<option value="Adventure">Adventure</option>
	                       			<option value="Others">Other</option>
	                       		</select>
	                       		<select class="form-control category tvshow">
	                       			<option value="Criminal">Criminal</option>
	                       			<option value="Action">Action</option>
	                       			<option value="Fantasy">Fantasy</option>
	                       			<option value="Thriller">Thriller</option>
	                       			<option value="Adventure">Adventure</option>
	                       			<option value="Others">Other</option>
	                       		</select>
	                       		<select class="form-control category music">
	                       			<option value="Rock">Rock</option>
	                       			<option value="Action">Action</option>
	                       			<option value="Fantasy">Fantasy</option>
	                       			<option value="Thriller">Thriller</option>
	                       			<option value="Adventure">Adventure</option>
	                       			<option value="Others">Other</option>
	                       		</select>
	                       </div>
	                       <div class="form-group">
	                       		<input type="text" class="form-control category-input col-sm-10" placeholder="others...">
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
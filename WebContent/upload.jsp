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
    	<script src="js/bootstrap/fileinput.min.js"></script>
    	
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
                	<div class="container" id="list">
                		<!--<div class="row">-->
                			<div class="col-lg-6 col-sm-6">
                				<div class="tag">
                				<div class="col-sm-5">
                				
	                				<img src="img/sample-covers/downloaded-movie1.png" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
												<a href="#">Pirates of Caribbean</a>
												<p>Comedy</p>
	                							<p>6-12-2017 13:25:30</p>
	                							<p>An adventure story of a infamous pirates.</p>
	                							<br />
	                							<br />
	                							
	                							
	                							<!--<div class="button">
	                								<input type="checkbox" id="cbtest" />
    												<label for="cbtest" class="check-box"></label> 
	                							</div>-->
	                							<!--<input type="radio" name="name" />-->
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
									

	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="tag">
                				<!--<div class="col-sm-1">
                					<form action=""> 
                						<input type="checkbox" name="movie" value="Vinyl"><br>
                					</form>
                				</div>-->
                				<div class="col-sm-5">
	                				<img src="img/sample-covers/downloaded-movie2.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
												<a href="#">Midnight Cowboy</a>
												<p>Romantic</p>
	                							<p>6-11-2017 13:25:30</p>
	                							<p>An adventure story of a cowboy.</p>
	                							<br />
	                							<br />
	                							
	                							
	                							<!--<input type="radio" name="name" />-->
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="tag">
                				<!--<div class="col-sm-1">
                					<form action=""> 
                						<input type="checkbox" name="movie" value="Vinyl"><br>
                					</form>
                				</div>-->
                				<div class="col-sm-5">
	                				<img src="img/sample-covers/downloaded-movie3.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
												<a href="#">Musician</a>
												<p>Music</p>
	                							<p>6-10-2017 13:25:30</p>
	                							<p>A story of a musician.</p>
	                							<br />
	                							<br />
	                						
	                							
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                		<!--</div>-->
                		
                		<!--<div class="row">-->
                			<div class="col-lg-6 col-sm-6">
                				<div class="tag">
                				<!--<div class="col-sm-1">
                					<form action=""> 
                						<input type="checkbox" name="movie" value="Vinyl"><br>
                					</form>
                				</div>-->
                				<div class="col-sm-5">
	                				<img src="img/sample-covers/downloaded-TVShow.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<!--<div class="tag">-->
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
												<a href="#">The Flash</a>
												<p>Action</p>
	                							<p>6-9-2017 13:25:30</p>
	                							<p>Adventure stories of a super hero.</p>
	                							<br />
	                							<br />
	                							
	                							
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="tag">
                				<!--<div class="col-sm-1">
                					<form action=""> 
                						<input type="checkbox" name="movie" value="Vinyl"><br>
                					</form>
                				</div>-->
                				<div class="col-sm-5">
	                				<img src="img/sample-covers/downloaded-TVShow2.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<!--<div class="tag">-->
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
													<a href="#">In the Flesh</a>
													<p>Thriller</p>
					                				<p>6-11-2017 13:25:30</p>
					                				<p>A story of a someone.</p>
					                				<br />
	                							<br />
	                							
	                							
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<!--<div class="col-sm-1">
                					<form action=""> 
                						<input type="checkbox" name="movie" value="Vinyl"><br>
                					</form>
                				</div>-->
                				<div class="tag">
                				<div class="col-sm-5">
	                				<img src="img/sample-covers/downloaded-TVShow3.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<!--<div class="tag">-->
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
													<a href="#">Red Oak</a>
													<p>Story</p>
					                				<p>6-10-2017 13:25:30</p>
					                				<p>A story of boys and girls.</p>
					                				<br />
	                							
	                							<br />
	                							
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                		<!--</div>-->
                		
                		<!--<div class="row">-->
                			<div class="col-lg-6 col-sm-6">
                				<!--<div class="col-sm-1">
                					<form action=""> 
                						<input type="checkbox" name="movie" value="Vinyl"><br>
                					</form>
                				</div>-->
                				<div class="tag">
                			
                				<div class="col-sm-5">
	                				<img src="img/sample-covers/downloaded-TVShow4.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<!--<div class="tag">-->
	                					<div class="header">
	                						<!--<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>-->
													<a href="#">Vinyl</a>
					                				<p>6-9-2017 13:25:30</p>
					                				<p>A story of a man.</p>
					                				<br />
	                							<br />
	                							
	                							
	                						<!--</div>-->
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                		<!--</div>-->
                	</div>
                </div>
                
                <div id="uploading">
	    			<div class="row">
	    				<div class="col-sm-2">
	    					<img src="img/sample-covers/downloaded-movie1.png" alt="downloading" class="img-responsive thumbnail" />
	    				</div>
	    				
	    				<div class="col-sm-9">
		    				<div style="margin-top: 20px;">
		    					<h4>File Name: </h4>
		    				</div>
	    				</div>
	    				
	    				<div class="col-sm-9">
	    					<div class="progress" style="margin-top: 20px;">
							    <div class="progress-bar" role="progressbar" aria-valuenow="60" 
							        aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
							        <span class="sr-only">40% 完成</span>
							    </div>
							</div>
							<p>40%/100%</p>
	    				</div>
	    				
	    				
	    				
	    			</div>
	    			<hr />
	    			<div class="row">
	    				<div class="col-sm-2">
	    					<img src="img/sample-covers/downloaded-movie2.jpg" alt="downloading" class="img-responsive thumbnail" />
	    				</div>
	    				
	    				<div class="col-sm-9">
		    				<div style="margin-top: 20px;">
		    					<h4>File Name: </h4>
		    				</div>
	    				</div>
	    				
	    				<div class="col-sm-9">
	    					<div class="progress" style="margin-top: 20px;">
							    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" 
							        aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
							        <span class="sr-only">20% 完成</span>
							    </div>
							</div>
							<p>20%/100%</p>
	    				</div>
	    				
	    				
	    				
	    			</div>
	    			
	    			<hr />
	    			<div class="row">
	    				<div class="col-sm-2">
	    					<img src="img/sample-covers/downloaded-movie3.jpg" alt="downloading" class="img-responsive thumbnail" />
	    				</div>
	    				
	    				<div class="col-sm-9">
		    				<div style="margin-top: 20px;">
		    					<h4>File Name: </h4>
		    				</div>
	    				</div>
	    				
	    				<div class="col-sm-9">
	    					<div class="progress" style="margin-top: 20px;">
							    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" 
							        aria-valuemin="0" aria-valuemax="100" style="width: 90%;">
							        <span class="sr-only">90% 完成</span>
							    </div>
							</div>
							<p>90%/100%</p>
	    				</div>
	    				
	    				
	    				
	    			</div>
	    		</div>
                
                
	    	</div>
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
    					<h4 class="modal-title" id="myModalLabel">Upload Form</h4>
    				</div>
    				<div class="modal-body">
    					<form id="upload-form">
	                        <div class="form-group">
	                            <label for="recipient-name" class="control-label" style="color: #909090;">Title: </label>
	                            <input type="text" class="form-control" id="recipient-name" name="fileName">
	                        </div>
							<div class="form-group">
								<label for="year" class="control-label" style="color: #909090;">Realease Year: </label>
								<select name="releaseYear" id="year" class="form-control">
									<% for(int year = 2017; year > 1900; year --) {
									 	%><option value="<%=year%>"><%=year%></option>
									<%}%>
								</select>
							</div>
							<div class="form-group">
								<label for="desc" class="control-label" style="color: #909090;">Plot: </label>
								<textarea class="form-control" id="desc" name="plot"></textarea>
							</div>
							<div class="form-group">
								<label for="duration" class="control-label" style="color: #909090;">Duration: </label>
								<input type="time" class="form-control" id="duration" name="duration">
							</div>
							<div class="form-group">
								<label for="thumbUrl" class="control-label" style="color: #909090;">Thumb Url: </label>
								<input type="text" class="form-control" id="thumbUrl" name="thumbUrl">
							</div>
							<div class="form-group">
								<label for="rating" class="control-label" style="color: #909090;">Rating: </label>
								<input type="number" class="form-control" id="rating" name="rating">
							</div>
	                        <div class="form-group">
	                            <label for="type" class="control-label" style="color: #909090;">Type: </label>
	                            <select class="form-control" id="type">
	                            	<option value="Movie">Movie</option>
	                            	<option value="Music">Music</option>
	                            	<option value="TVShow">TV Show</option>
	                            </select>
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label" for="input-2" style="color: #909090;">Select File</label>
								<input id="input-2" name="input2[]" type="file" class="file" multiple data-show-upload="false" data-show-caption="true">
	                       </div>
	                       <div class="form-group">
	                       		<label class="control-label" style="color: #909090;">Select Categories</label>
	                       		<select class="form-control category movie">
	                       			<option value="Comedy">comedy</option>
	                       			<option value="Action">action</option>
	                       			<option value="Fantasy">fantasy</option>
	                       			<option value="Thriller">thriller</option>
	                       			<option value="Adventure">adventure</option>
	                       			<option value="Others">others</option>
	                       		</select>
	                       		<select class="form-control category tvshow">
	                       			<option value="Criminal">criminal</option>
	                       			<option value="Action">action</option>
	                       			<option value="Fantasy">fantasy</option>
	                       			<option value="Thriller">thriller</option>
	                       			<option value="Adventure">adventure</option>
	                       			<option value="Others">others</option>
	                       		</select>
	                       		<select class="form-control category music">
	                       			<option value="Rock">rock</option>
	                       			<option value="Action">action</option>
	                       			<option value="Fantasy">fantasy</option>
	                       			<option value="Thriller">thriller</option>
	                       			<option value="Adventure">adventure</option>
	                       			<option value="Others">others</option>
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
    	
 	</body>
</html>
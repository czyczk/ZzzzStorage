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
    	<script src="js/bootstrap/bootstrap.min.js"></script>
    	<script src="js/bootstrap/fileinput.min.js"></script>
    	
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    					
    					<li><a data-toggle="tab" class="glyphicon glyphicon-log-out" href="#">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a></li>
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
	    					<li><a href="uploading.jsp" id="uploading-toggle">Uploading</a></li>
	    					<li  class="active"><a href="upload.jsp" id="uploaded-toggle">Uploaded</a></li>	
	    				</ol>
	    			</div>
	    		</div>
	    		
	    		<div id="uploaded">
                	<div class="container">
                		<!--<div class="row">-->
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-movie1.png" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
												<a href="#">Pirates of Caribbean</a>
	                							<p>6-12-2017 13:25:30</p>
	                							<p>An adventure story of a infamous pirates.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
									

	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-movie2.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
												<a href="#">Midnight Cowboy</a>
	                							<p>6-11-2017 13:25:30</p>
	                							<p>An adventure story of a cowboy.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-movie3.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
												<a href="#">Musician</a>
	                							<p>6-10-2017 13:25:30</p>
	                							<p>A story of a musician.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                		<!--</div>-->
                		
                		<!--<div class="row">-->
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-TVShow.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
												<a href="#">The Flash</a>
	                							<p>6-9-2017 13:25:30</p>
	                							<p>Adventure stories of a super hero.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-TVShow2.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
													<a href="#">In the Flesh</a>
					                				<p>6-11-2017 13:25:30</p>
					                				<p>A story of a someone.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-TVShow3.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
													<a href="#">Red Oak</a>
					                				<p>6-10-2017 13:25:30</p>
					                				<p>A story of boys and girls.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                		<!--</div>-->
                		
                		<!--<div class="row">-->
                			<div class="col-lg-6 col-sm-6">
                				<div class="col-sm-5">
	                				<img src="img/downloaded-TVShow4.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-7">
	                				<div class="tag">
	                					<div class="header">
	                						<div class="navigation">
	                							<ol class="breadcrumb">
										    		<li><a>Comedy</a></li>
										    		<li><a>Action</a></li>
												</ol>
													<a href="#">Vinyl</a>
					                				<p>6-9-2017 13:25:30</p>
					                				<p>A story of a man.</p>
	                						</div>
	                						
	                					</div>
	                					
	                				</div>
	                				
	                			</div>
                			</div>
                		<!--</div>-->
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
    					<form action="">
	                        <div class="form-group">
	                            <label for="recipient-name" class="control-label" style="color: #909090;">File Name: </label>
	                            <input type="text" class="form-control" id="recipient-name">
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label" for="input-2" style="color: #909090;">Select File</label>
								<input id="input-2" name="input2[]" type="file" class="file" multiple data-show-upload="false" data-show-caption="true">
	                        </div>
	                        <div class="form-group">
	                            <label for="category" class="control-label" style="color: #909090;">Categories: </label>
	                            <select class="form-control">
	                            	<option>Movie</option>
	                            	<option>Music</option>
	                            	<Option>TV Show</Option>
	                            </select>
	                        </div>
							<div class="form-group">
								<div class="row">
		                        	<button type="submit" class="btn btn-primary col-lg-10 col-lg-offset-1 col-sm-offset-1 col-sm-10 col-xs-offset-1 col-xs-10">Upload Now</button>
								</div>
							</div>
                    	</form>
    				</div>
    			</div>
    		</div>
    	</div>
    	
    	<script src="js/upload.js"></script>
    	
    	
 	</body>
</html>
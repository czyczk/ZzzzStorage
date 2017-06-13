<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    	<script src="jQuery/jquery.min.js"></script>
    	<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
    	<link rel="stylesheet" href="css/nav.css">
    	<link rel="stylesheet" href="css/main-page.css">
    	<link rel="stylesheet" href="css/download.css" />
    	<link rel="stylesheet" href="css/bootstrap/fileinput.min.css">
    	<script src="js/bootstrap/bootstrap.min.js"></script>
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
    					<li><a href="upload.jsp" class="glyphicon glyphicon-open">&nbsp;Upload</a></li>
    					<li  class="active"><a  data-toggle="tab" href="download.jsp" class="glyphicon glyphicon-save">&nbsp;Download</a></li>
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
	    					<li class="active"><a id="downloading-toggle" href="#">Downloading</a></li>
	    					<li><a id="downloaded-toggle" href="#">Downloaded</a></li>	
	    				</ol>
	    			</div>
	    		</div>
	    		<!--
                	作者：601347015@qq.com
                	时间：2017-06-13
                	描述：page content
                -->
                <div id="downloaded">
                	<div class="container">
                		<div class="row">
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-movie1.png" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">Pirates of Caribbean</a>
	                				<p>6-12-2017 13:25:30</p>
	                				<p>An adventure story of a infamous pirates.</p>
	                			</div>
                			</div>
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-movie2.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">Midnight Cowboy</a>
	                				<p>6-11-2017 13:25:30</p>
	                				<p>An adventure story of a cowboy.</p>
	                			</div>
                			</div>
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-movie3.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">Musician</a>
	                				<p>6-10-2017 13:25:30</p>
	                				<p>A story of a musician.</p>
	                			</div>
                			</div>
                		</div>
                		
                		<div class="row">
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-TVShow.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">The Flash</a>
	                				<p>6-9-2017 13:25:30</p>
	                				<p>Adventure stories of a super hero.</p>
	                			</div>
                			</div>
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-TVShow2.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">In the Flesh</a>
	                				<p>6-11-2017 13:25:30</p>
	                				<p>A story of a someone.</p>
	                			</div>
                			</div>
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-TVShow3.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">Red Oak</a>
	                				<p>6-10-2017 13:25:30</p>
	                				<p>A story of boys and girls.</p>
	                			</div>
                			</div>
                		</div>
                		
                		<div class="row">
                			<div class="col-lg-4 col-sm-6">
                				<div class="col-sm-6">
	                				<img src="img/downloaded-TVShow4.jpg" class="thumbnail" />
	                			</div>
	                			<div class="col-sm-6">
	                				<a href="#">Vinyl</a>
	                				<p>6-9-2017 13:25:30</p>
	                				<p>A story of a man.</p>
	                			</div>
                			</div>
                		</div>
                	</div>
                </div>
                
                <div id="downloading">
	    			<div class="row">
	    				<div class="col-sm-2">
	    					<img src="img/downloaded-movie1.png" alt="downloading" class="img-responsive thumbnail" />
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
	    					<img src="img/downloaded-movie2.jpg" alt="downloading" class="img-responsive thumbnail" />
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
	    					<img src="img/downloaded-movie3.jpg" alt="downloading" class="img-responsive thumbnail" />
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
    	
    	<script src="js/download.js"></script>
 	</body>
</html>
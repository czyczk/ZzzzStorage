<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    	<script src="jQuery/jquery.min.js"></script>
    	<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
    	<link rel="stylesheet" href="css/nav.css">
    	<link rel="stylesheet" href="css/main-page.css">
    	<script src="js/bootstrap/bootstrap.min.js"></script>
    	
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
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
    					<li class="active"><a data-toggle="tab" class="glyphicon glyphicon-home" href="main.jsp">&nbsp;Home</a></li>
    					<li><a href="movie.jsp" class="glyphicon glyphicon-film">&nbsp;Movie</a></li>
    					<li><a href="TVShow.jsp" class="glyphicon glyphicon-facetime-video">&nbsp;TV<span style="color: rgb(248,248,248);">-</span>Show</a></li>
    					<li><a href="music.jsp" class="glyphicon glyphicon-music">&nbsp;Music</a></li>
    					<li><a href="upload.jsp" class="glyphicon glyphicon-open">&nbsp;Upload</a></li>
    					<li><a href="download.jsp" class="glyphicon glyphicon-save">&nbsp;Download</a></li>
    					<li><a class="glyphicon glyphicon-log-out" href="welcome.jsp">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a></li>
    				</ul>
    			</div>
    		</div>
    	</nav>
    	
    	<!--
        	Author: 601347015@qq.com
        	Date: 2017-06-10
        	Desc: main content
        -->
        <div class="container panel1">
		   	<div class="jumbotron">
				<div class="row">		   
		   		<div class="pull-left col-sm-8">
		   			<br/>
		   			<h1>Movie</h1>
		   			<br/>
		   			<br/>
			        <p class="go-button-container"><a href="movie.jsp" class="btn btn-primary btn-lg" role="button"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;GO</a></p>
		   		</div>
		      	<div id="myCarousel" class="col-sm-4 carousel slide" data-ride="carousel" data-interval="3000">
		      		<ol class="carousel-indicators">
		      			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		      			<li data-target="#myCarousel" data-slide-to="1"></li>
		      			<li data-target="#myCarousel" data-slide-to="2"></li>
		      		</ol>
		      		<div class="carousel-inner">
		      			<div class="item active">
		      				<img src="img/sample-covers/movie-jumbotron1.png">
		      			</div>
		      			<div class="item">
		      				<img src="img/sample-covers/movie-jumbotron2.jpg">
		      			</div>
		      			<div class="item">
		      				<img src="img/sample-covers/movie-jumbotron3.png">
		      			</div>
		      		</div>
		      	</div>
		      	</div>
		   </div>
		</div>
		
		<div class="container panel2">
		   	<div class="jumbotron">
				<div class="row">		   
		   		<div class="pull-right col-sm-7">
		   			<br/>
		   			<h1>TV Show</h1>
		   			<br/>
		   			<br/>
			        <p class="go-button-container"><a href="TVShow.jsp" class="btn btn-primary btn-lg" role="button"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;GO</a></p>
		   		</div>
		      	<div id="myCarousel2" class="col-sm-4 carousel slide pull-left" data-ride="carousel" data-interval="3000">
		      		<ol class="carousel-indicators">
		      			<li data-target="#myCarousel2" data-slide-to="0" class="active"></li>
		      			<li data-target="#myCarousel2" data-slide-to="1"></li>
		      			<li data-target="#myCarousel2" data-slide-to="2"></li>
		      		</ol>
		      		<div class="carousel-inner">
		      			<div class="item active">
		      				<img src="img/sample-covers/tvshow-jumbotron.png">
		      			</div>
		      			<div class="item">
		      				<img src="img/sample-covers/tvshow-jumbotron2.png">
		      			</div>
		      			<div class="item">
		      				<img src="img/sample-covers/tvshow-jumbotron3.png">
		      			</div>
		      		</div>
		      	</div>
		      	</div>
		   </div>
		</div>
		
		
		<div class="container panel3">
		   	<div class="jumbotron">
				<div class="row">		   
		   		<div class="pull-left col-sm-8">
		   			<br/>
		   			<h1>Music</h1>
		   			<br/>
		   			<br/>
			        <p class="go-button-container"><a href="music.jsp" class="btn btn-primary btn-lg" role="button"><span class="glyphicon glyphicon-hand-right"></span>&nbsp;GO</a></p>
		   		</div>
		      	<div id="myCarousel3" class="col-sm-4 carousel slide" data-ride="carousel" data-interval="3000">
		      		<ol class="carousel-indicators">
		      			<li data-target="#myCarousel3" data-slide-to="0" class="active"></li>
		      			<li data-target="#myCarousel3" data-slide-to="1"></li>
		      			<li data-target="#myCarousel3" data-slide-to="2"></li>
		      		</ol>
		      		<div class="carousel-inner">
		      			<div class="item active">
		      				<img src="img/sample-covers/music-jumbotron.png">
		      			</div>
		      			<div class="item">
		      				<img src="img/sample-covers/music-jumbotron2.png">
		      			</div>
		      			<div class="item">
		      				<img src="img/sample-covers/music-jumbotron3.png">
		      			</div>
		      		</div>
		      	</div>
		      	</div>
		   </div>
		</div>
 	</body>
</html>
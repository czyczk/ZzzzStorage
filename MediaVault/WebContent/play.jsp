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
        <link rel="stylesheet" href="css/video-js.css" />
        <script src="js/video.min.js"></script>
        <title>Play Online</title>
        <script>
    		videojs.options.flash.swf = "video-js.swf";
		</script>
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
    					<li><a href="download.jsp" class="glyphicon glyphicon-save">&nbsp;Download</a></li>
    					<li><a class="glyphicon glyphicon-log-out" href="welcome.jsp">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a></li>
    				</ul>
    			</div>
    		</div>
    	</nav>
    	<div class="video" style="margin-top: 50px; margin-bottom: 50px;">
    		<video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" width="1000" height="500"
	      	data-setup="{}" style="margin: 0 auto;">
			    <source src="sample-media/sample-mp4.mp4" type="video/mp4" />
			    <source src="http://video-address-2.webm" type='video/webm' />
			    <source src="http://video-address-3.ogv" type='video/ogg' />
			    <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track>
			    <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track> <!-- Tracks need an ending tag thanks to IE9 -->
			</video>
    	</div>
    	
		
		<script type="text/javascript">
		    var myPlayer = videojs('example_video_1');
		    	videojs("example_video_1").ready(function(){
		        var myPlayer = this;
		        myPlayer.play();
		   });
		</script>
		
		
 	</body>
</html>
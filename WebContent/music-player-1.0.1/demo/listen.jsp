<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
     <script src="../../jQuery/jquery.min.js"></script>
	<link rel="stylesheet" href="../../css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="../../css/nav.css">
	<link rel="stylesheet" href="../../css/main-page.css">
	<script src="../../js/bootstrap/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="../plugin/css/style.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <script type="text/javascript" src="js/jquery-1.6.1.min.js"></script>
    <script type="text/javascript" src="../plugin/jquery-jplayer/jquery.jplayer.js"></script>
    <script type="text/javascript" src="../plugin/ttw-music-player-min.js"></script>
    <script type="text/javascript" src="js/myplaylist.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            var description = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id tortor nisi. Aenean sodales diam ac lacus elementum scelerisque. Suspendisse a dui vitae lacus faucibus venenatis vel id nisl. Proin orci ante, ultricies nec interdum at, iaculis venenatis nulla. ';

            $('body').ttwMusicPlayer(myPlaylist, {
                autoPlay:false, 
                description:description,
                jPlayer:{
                    swfPath:'../plugin/jquery-jplayer' //You need to override the default swf path any time the directory structure changes
                }
            });
        });
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
    					<li><a class="glyphicon glyphicon-home" href="../../main.jsp">&nbsp;Home</a></li>
    					<li><a href="../../movie.jsp" class="glyphicon glyphicon-film">&nbsp;Movie</a></li>
    					<li><a href="../../TVShow.jsp" class="glyphicon glyphicon-facetime-video">&nbsp;TV<span style="color: rgb(248,248,248);">-</span>Show</a></li>
    					<li><a href="../../music.jsp" class="glyphicon glyphicon-music">&nbsp;Music</a></li>
    					<li><a href="../../upload.jsp" class="glyphicon glyphicon-open">&nbsp;Upload</a></li>
    					<li><a href="../../download.jsp" class="glyphicon glyphicon-save">&nbsp;Download</a></li>
    					<li><a class="glyphicon glyphicon-log-out" href="../../welcome.jsp">&nbsp;Log<span style="color: rgb(248,248,248);">-</span>Out</a></li>
    				</ul>
    			</div>
    		</div>
    	</nav>

</body>
</html>
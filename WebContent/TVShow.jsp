<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="jQuery/jquery.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/main-page.css">
    <link rel="stylesheet" href="css/media-page.css"/>
    <link rel="stylesheet" href="css/tv-show.css" />
    <script src="js/bootstrap/bootstrap.min.js"></script>
    <script src="js/prefixfree.min.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TV Show</title>
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
                <li>
                    <a href="movie.jsp" class="glyphicon glyphicon-film">&nbsp;Movie</a>
                </li>
                <li class="active">
                    <a href="TVShow.jsp" class="glyphicon glyphicon-facetime-video">&nbsp;TV<span
                            style="color: rgb(248,248,248);">-</span>Show</a>
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
                    <a class="glyphicon glyphicon-log-out" href="welcome.jsp">&nbsp;Log<span
                            style="color: rgb(248,248,248);">-</span>Out</a>
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
            <a href="play.jsp" id="play-button"><span class="glyphicon glyphicon-play right-sidebar-icon"></span>Play</a>
        </li>
        <li>
            <a href="#" id="download-button"><span class="glyphicon glyphicon-cloud-download right-sidebar-icon"></span>Download</a>
        </li>
        <li>
            <a href="#" id="delete-button"><span class="glyphicon glyphicon-trash right-sidebar-icon"></span>Delete</a>
        </li>
        <li>
            <%--<a id="edit-button" href data-toggle="modal" data-target="#property1-form"><span--%>
                    <%--class="glyphicon glyphicon-info-sign right-sidebar-icon"></span>Edit properties</a>--%>
            <a id="edit-button" href data-toggle="modal" data-target="#property-form"><span
                    class="glyphicon glyphicon-info-sign right-sidebar-icon"></span>Edit properties</a>
        </li>
    </ul>
</div>

<div class="modal fade" id="property-form" tabindex="-1" role="dialog" aria-labelledby="UpdateLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title" id="UpdateLabel" style="text-align: center; color: black">Edit</h3>
            </div>
            <div class="modal-body">
                <form>
                    <div class="basic-info">
                        <h4 style="color: black">Basic Infomation</h4>
                        <div class="form-group title">
                            <label for="title" class="control-label" style="color: #909090;">Title: </label>
                            <input type="text" class="form-control" id="title" name="title">
                            <div class="errorTitle-required error">Title is required.</div>
                        </div>
                        <div class="form-group tvtitle">
                            <label for="tvtitle" class="control-label" style="color: #909090;">Title of TV Show: </label>
                            <input type="text" class="form-control" id="tvtitle" name="title">
                            <div class="errorTitle-required error">Title is required.</div>
                        </div>
                        <div class="form-group type">
                            <label for="type" class="control-label" style="color:#909090;">Type: </label>
                            <span id="type" name="type" class="text-primary">TV Show</span>
                        </div>
                        <div class="form-group etype">
                            <label for="etype" class="control-label" style="color:#909090;">Type: </label>
                            <span id="etype" name="etype" class="text-primary">Episode</span>
                        </div>
                        <%--<div class="form-group">--%>
                            <%--<label for="size" class="control-label" style="color: #909090;">Size: </label>--%>
                            <%--<span class="text-primary" id="size" name="size"></span>--%>
                        <%--</div>--%>
                        <div class="form-group season">
                            <label for="season" class="control-label" style="color: #909090;">Season: </label>
                            <input type="number" class="form-control" id="season" name="season">
                            <div class="errorSeason-required error">Season is required.</div>
                            <div class="error-season error">Season is from 1 to 20.</div>
                        </div>
                        <div class="form-group imdb">
                            <label for="imdb" class="control-label" style="color: #909090;">IMDB: </label>
                            <input type="number" id="imdb" name="imdb" class="form-control">
                            <div class="errorIMDB-required error">IMDB is required.</div>
                            <div class="error-range error">IMDB should be a 7-digit number.</div>
                        </div>
                        <div class="form-group tvimdb">
                            <label for="tvimdb" class="control-label" style="color: #909090;">TV Show IMDB: </label>
                            <input type="number" id="tvimdb" name="tvimdb" class="form-control">
                            <div class="errorIMDB-required error">IMDB is required.</div>
                            <div class="error-range error">IMDB should be a 7-digit number.</div>
                        </div>
                    </div>
                    <hr>
                    <div class="optional-info">
                        <h4 style="color: black">Optional Information</h4>
                        <div class="form-group episode">
                            <label for="episode" class="control-label" style="color: #909090">Episode Number: </label>
                            <input type="number" class="form-control" id="episode" name="episodeNo">
                        </div>
                        <div class="form-group titleOfEpisode">
                            <label for="titleOfEpisode" class="control-label" style="color: #909090">Title of This Episode: </label>
                            <input type="text" class="form-control" id="titleOfEpisode" name="titleOfEpisode">
                            <div class="msgEpisodeTitle" style="color:#909090;"></div>
                        </div>

                        <div class="form-group releaseYear">
                            <label for="releaseYear" class="control-label" style="color: #909090;">Release
                                Year: </label>
                            <select name="releaseYear" id="releaseYear" class="form-control">
                                <% for (int year = 2017; year > 1900; year--) {
                                %>
                                <option value="<%=year%>"><%=year%>
                                </option>
                                <%}%>
                            </select>
                        </div>
                        <div class="form-group runtime">
                            <label for="runtime" class="control-label" style="color: #909090;">Runtime (in minutes): </label>
                            <input type="time" id="runtime" class="form-control" name="runtime">
                        </div>
                        <div class="form-group episodeRuntime">
                            <label for="episodeRuntime" class="control-label" style="color: #909090">Runtime (in minutes): </label>
                            <input type="number" class="form-control" id="episodeRuntime" name="episodeRuntime">
                        </div>
                        <div class="form-group plot">
                            <label for="plot" class="control-label" style="color: #909090;">Plot: </label>
                            <textarea class="form-control plot" id="plot" name="plot"></textarea>
                            <div class="msg"></div>
                        </div>
                        <div class="form-group rating">
                            <label for="rating" class="control-label" style="color: #909090;">Rating: </label>
                            <input type="number" class="form-control" id="rating" name="rating">
                            <div class="error-rating error">Rating is from 1 to 10.</div>
                        </div>
                        <div class="form-group episodeRating">
                            <label for="episodeRating" class="control-label" style="color:#909090;">Rating: </label>
                            <input type="text" class="form-control" id="episodeRating" name="episodeRating">
                        </div>
                        <div class="form-group url">
                            <label for="thumbUrl" class="control-label" style="color: #909090;">Thumbnail URL: </label>
                            <input type="text" class="form-control" id="thumbUrl" name="thumbUrl">
                        </div>
                        <div class="form-group episodeThumbnail">
                            <label for="episodeThumbnail" class="control-label" style="color:#909090;">Thumb URL: </label>
                            <input type="text" class="form-control" id="episodeThumbnail" name="episodeThumbnail">
                            <div class="msgEpisodeThumb" style="color:#909090;"></div>
                        </div>
                        <div class="form-group storyLine">
                            <label for="storyLine" class="control-label" style="color: #909090;">Storyline:</label>
                            <textarea class="form-control" id="storyLine" name="storyline"></textarea>
                            <div class="msgStoryLine" style="color: #909090"></div>
                        </div>
                    </div>

                    <hr>
                    <div class="form-group">
                        <div class="row">
                            <button type="button"
                                    class="update-submit btn btn-primary col-lg-10 col-lg-offset-1 col-sm-offset-1 col-sm-10 col-xs-offset-1 col-xs-10">
                                OK
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>


<script src="js/tvShow.js"></script>
<script src="js/mediaPage.js"></script>
<script src="js/util.js"></script>
<script src="js/mediaPageRightSidebar.js"></script>
<script src="js/modal.js"></script>


</body>
<!--
    Authors:
        2684232910@qq.com, 564108186@qq.com, 601347015@qq.com
    Date: 2017-06-12
    Desc: TV Show Page
-->
</html>
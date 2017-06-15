<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="css/welcome.css">
    <link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
    <script src="jQuery/jquery.min.js"></script>
    <script src="jQuery/jquery.validate.min.js"></script>
    <script src="js/bootstrap/bootstrap.js"></script>
</head>
<body>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container" id="nav">
            <div class="navbar-header">
                <a href="javascript: ;" class="navbar-brand" style="margin: 0px; padding: 0px"><h3 style="margin-top: 13px">Media Vault</h3></a>
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav navbar-right nav-tabs">
                    <li class="active"><a href data-toggle="tab"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                    <li><a href data-toggle="modal" data-target="#myModal1"><span class="glyphicon glyphicon-pencil"></span> Sign Up</a></li>
                    <li><a href data-toggle="modal" data-target="#myModal2"><span class="glyphicon glyphicon-user"></span> Log In</a></li>
                    <li><a href data-toggle="modal" data-target="#myModal3"><span class="glyphicon glyphicon-question-sign"></span> Help</a></li>
                </ul>
            </div>


        </div>
    </nav>
    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 400px; margin: 0 auto">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel1" style = "text-align: center; color: gray"><b>Media Vault Sign Up</b></h4>
                </div>
                <div class="modal-body">
                    <form action="" id="register">
                        <div class="form-group">
                                <label for="recipient-name" class="control-label" style="color: gray;">Username: </label>
                                <input type="text" class="form-control" id="recipient-name" name="username">

                        </div>
                        <div class="form-group">
                                <label for="email" class="control-label" style="color: gray;">Email: </label>
                                <input type="email" class="form-control" id="email" name="email">
                        </div>
                        <div class="form-group">
                                <label for="password" class="control-label" style="color: gray;">Password: </label>
                                <input type="password" class="form-control" id="password" name="password">

                        </div>
                        <div class="form-group">
                            <label for="password" class="control-label" style="color: gray;">Confirm Password: </label>
                            <input type="password" class="form-control" id="cpassword" name="cpassword">
                        </div>
                        <div>
                            <button type="submit" class="btn btn-default" style="width: 345px; margin-top: 10px">Sign Up</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 400px; margin: 0 auto">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel2" style = "text-align: center; color: gray"><b>Media Vault Log In</b></h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="recipient-name" class="control-label" style="color: gray;">Username: </label>
                            <input type="text" class="form-control" id="recipient-name1">
                        </div>
                        <div class="form-group">
                            <label for="password" class="control-label" style="color: gray;">Password: </label>
                            <input type="password" class="form-control" id="password1">
                        </div>
                        <div>
                            <button type="button" onclick="window.location.href='main.jsp'" class="btn btn-default" style="width: 345px; margin-top: 10px">Log In</button>
                            <!--<button type="reset" class="btn btn-default">Reset</button>-->
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="myModal3" tabindex="-1" aria-hidden="true" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel3" style="text-align: center; color: gray"><b>About Media Vault</b></h4>
                </div>
                <div class="modal-body">
                    <h4>What is Media Vault?</h4>
                    <p>Media Vault is an online storage site that holds TV shows, movies and music uploaded from registered users, who can choose either to use them online or download them for later use. Users need to log in to explore the features.</p>
                    <h4>How to Sign Up?</h4>
                    <p>Click on "Sign Up" at the top of the page and fill in the form. Click on "Sign Up" button in the form again and you're done if all the information needed is valid.</p>
                	<h4>How to upload a file?</h4>
                    <p>After logging in, hover your mouse on the suspending "+" button and select "Upload a file". In the form popped up, fill in the information as much as possible. The more details you fill in, the more possibility that your files will be organized properly and reasonably. Specifically, new items will be placed along with those with the same type.</p>
                	<h4>What can I do with the files?</h4>
                	<p>In any page, you can see a list containing items of the same type. The display order of the items can be changed as you like. For example, in the "Music" page, you can choose to sort the list by name, artist or album.After selecting one or multiple items in the list, a side bar appears with a few options. Usually, you can choose either to play it online, download it, delete it, or edit its properties. Some operations may not be available if you select multiple items or items of different types.</p>
                </div>
                <div class="modal-footer">
                    <h4 style="text-align: left;">Contact Us: </h4>
                    <p style="text-align: left;">Zenas Chen: 564108186(QQ)</p>
                    <p style="text-align: left;">Violet Zhou: 2684232910(QQ)</p>
                    <p style="text-align: left;">Soda Zhang: 601347015(QQ)</p>
                </div>
            </div>
        </div>
    </div>
    <!--<div class="container">-->
            <!--<div class=" col-lg-12">-->
    <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="7000">

        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
            <li data-target="#myCarousel" data-slide-to="3"></li>
        </ol>
<!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
            <div class="item active">
                <!--<img src="images/Business_Incubation_Startup.jpg" alt="First slide">-->
                <img src="img/banners/1.jpg" style="width:100%" alt="First slide">
            </div>
            <div class="item">
                <img src="img/banners/2.jpg" style="width:99.9%" alt="Second slide">
            </div>
            <div class="item">
                <img src="img/banners/3.jpg" style="width:99.83%" alt="Third slide">
            </div>
            <div class="item">
                <img src="img/banners/4.jpg" style="width:99.63%" alt="Fourth slide">
            </div>
         </div>
        <!-- 轮播（Carousel）导航 -->
    </div>
            <!--</div>-->
    <!--</div>-->
    <!--<br>-->
    <!--<br>-->
    <!--<div class="container">-->
    <!--<div class="jumbotron">-->
        <!--<div class="container">-->
            <!--<h1>欢迎登陆页面！</h1>-->
            <!--<p>这是一个超大屏幕（Jumbotron）的实例。</p>-->
        <!--</div>-->
    <!--</div>-->

    <!--<div class="tab1">-->
        <!--<div class="container">-->
            <!--<h2 class="tab-h2">What is Media Vault ?</h2>-->
            <!--<p class="tab-p">mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm</p>-->
        <!--</div>-->
    <!--</div>-->

    <div id="client-download">
        <ul class="tab-download clearfix" id="tab-download">
            <li><a class="windows download-target" hidefocus="true" href="javascript:;" id="windows-download">
                    <ol>
                        <li><img src="img/client-logos/windows.png" alt="Windows"></li>
                        <li>Windows</li>
                    </ol>
                </a>
            </li>
            <li><a class="android download-target" hidefocus="true" href="javascript:;" id="android-download">
                <ol>
                    <li><img src="img/client-logos/android.png" alt="Android"></li>
                    <li>Android</li>
                </ol>
            </a></li>
            <li><a class="iphone download-target" hidefocus="true" href="javascript:;" id="iphone-download">
                <ol>
                    <li><img src="img/client-logos/iphone.png" alt="iPhone"></li>
                    <li>iPhone</li>
                </ol>
            </a></li>
            <li><a class="uwp download-target" hidefocus="true" href="javascript:;" id="uwp-download">
                <ol>
                    <li><img src="img/client-logos/uwp.png" alt="UWP"></li>
                    <li>UWP</li>
                </ol>
            </a></li>
            <li><a class="mac download-target" hidefocus="true" href="javascript:;" id="mac-download">
                <ol>
                    <li><img src="img/client-logos/mac.png" alt="Mac"></li>
                    <li>Mac</li>
                </ol>
            </a></li>
        </ul>
    </div>

    <footer id="footer">
        <div class="container">
            <p>© 2017 Zzzz</p>
        </div>
    </footer>
    <script src="js/validator.js"></script>

</body>
</html>
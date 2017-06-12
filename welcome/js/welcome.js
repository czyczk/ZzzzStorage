/**
 * Created by vjvio_000 on 2017/6/13.
 */
$(function(){
    $("nav .container #navbar-collapse ul li").click(function(){
        $("nav .container #navbar-collapse ul li").removeClass("active");
        $(this).addClass("active");
    });
})
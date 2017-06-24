/**
 * Created by czyczk on 2017-6-23.
 */
$(function() {
    // The submit button in sign up form
    $("#sign-up-button").click(handleSignUp);
    // The submit button in log in form
    $("#log-in-button").click(handleLogIn);
    // $('.email-ajax-input').bind('input propertychange', function(){
    //     $.ajax({
    //         url: "CheckEmailServlet",
    //         data: $("#sign-up-form").serialize(),
    //         type: "get",
    //         dataType: "json",
    //         success: showMessage($("#sign-up-error-label"))
    //     });
    // })
});

function handleSignUp() {
    $.ajax({
        url: "SignUpServlet",
        data: $("#sign-up-form").serialize(),
        type: "post",
        dataType: "json",
        success: showMessage($("#sign-up-error-label"))
    });
}

function handleLogIn() {
    $.ajax({
        url: "LogInServlet",
        data: $("#log-in-form").serialize(),
        type: "post",
        dataType: "json",
        success: showMessage($("#log-in-error-label"))
    });
}

function showMessage(elementToShow) {
    return function (data) {
        if (data.requestStatus == "success") {
            // data.redirect contains the string URL to redirect to
            window.location.href = data.message;
        } else {
            elementToShow.css("display", "inline").html(data.message);
            // $('.submit-button').addClass("disabled");
        }
    }
}


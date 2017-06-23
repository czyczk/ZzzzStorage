/**
 * Created by czyczk on 2017-6-23.
 */
$(function() {
    // The submit button in sign up form
    $("#sign-up-button").click(handleSignUp);
    // The submit button in log in form
    $("#log-in-button").click(handleLogIn);
});

function rssToTarget(element) {

}

function handleSignUp() {
    $.ajax({
        url: "SignUpServlet",
        data: $("#sign-up-form").serialize(),
        type: "post",
        success: showMessage($("#sign-up-error-label"))
    });
}

function handleLogIn() {
    $.ajax({
        url: "LogInServlet",
        data: $("#log-in-form").serialize(),
        type: "post",
        success: showMessage($("#log-in-error-label"))
    });
}

function showMessage(elementToShow) {
    return function (data) {
        elementToShow.css("display", "inline").html(data);
    }
}
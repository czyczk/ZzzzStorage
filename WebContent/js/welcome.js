/**
 * Created by czyczk on 2017-6-23.
 */
$(function() {
    // The submit button in sign up form
    $("#sign-up-button").click(handleSignUp);
    // The submit button in log in form
    $("#log-in-button").click(handleLogIn);
    // Check if the email exists on focus loss
    $('.sign-up-email-ajax-input').bind('input propertychange', handleSignUpEmailChange);
    $(".log-in-email-ajax-input").bind("input propertychange", handleLogInEmailChange);
    // $(".sign-up-email-ajax-input").blur(handleSignUpEmailChange());
    // $(".log-in-email-ajax-input").blur(handleLogInEmailChange());
});

$(document).ready(function () {
    $("#myModal1 #sign-up-form input").keydown(function(e) {
        e=e||event;
        if(e.keyCode=='13' || e.which=='13' || e.charCode=='13') {
            $("#myModal1 #sign-up-form #sign-up-button").trigger('click');
        }
        // alert('sss');
    });
});

$(document).ready(function () {
    $("#myModal2 #log-in-form input").keydown(function(e) {
        e=e||event;
        if(e.keyCode=='13' || e.which=='13' || e.charCode=='13') {
            $("#myModal2 #log-in-form #log-in-button").trigger('click');
        }
    });
});

function handleSignUp() {
    $.ajax({
        url: "SignUpServlet",
        data: $("#sign-up-form").serialize(),
        type: "post",
        dataType: "json",
        success: showMessageOnSubmit($("#sign-up-error-label"))
    });
}

function handleLogIn() {
    $.ajax({
        url: "LogInServlet",
        data: $("#log-in-form").serialize(),
        type: "post",
        dataType: "json",
        success: showMessageOnSubmit($("#log-in-error-label"))
    });
}

function handleSignUpEmailChange() {
    $.ajax({
        url: "EmailCheckerServlet",
        // data: $("#sign-up-form").serialize(),
        data: "email=" + encodeURIComponent($(".sign-up-email-ajax-input").val()),
        type: "post",
        dataType: "json",
        success: showMessageOnFocusLoss("sign up")
    });
}

function handleLogInEmailChange() {
    $.ajax({
        url: "EmailCheckerServlet",
        // data: $("#sign-up-form").serialize(),
        data: "email=" + encodeURIComponent($(".log-in-email-ajax-input").val()),
        type: "post",
        dataType: "json",
        success: showMessageOnFocusLoss("log in")
    });
}


function showMessageOnSubmit(elementToShow) {
    return function (data) {
        if (data.messageType == "success") {
            // data.redirect contains the string URL to redirect to
            window.location.href = data.message;
        } else {
            elementToShow.css("display", "inline").html(data.message);
            // $('.submit-button').addClass("disabled");
        }
    }
}

function showMessageOnFocusLoss(mode) {
    return function (data) {
        if (mode == "sign up") {
            if (data.messageType == "match found") {
                $("#sign-up-error-label").css("display", "inline").html(data.message);
                // $(".sign-up-email-ajax-input").prop("aria-invalid", "true");
            }
            else {
                $("#sign-up-error-label").css("display", "none");
                // $(".sign-up-email-ajax-input").prop("aria-invalid", "false");
            }
        } else if (mode == "log in") {
            if (data.messageType == "match not found") {
                $("#log-in-error-label").css("display", "inline").html(data.message);
                // $(".log-in-email-ajax-input").prop("aria-invalid", "true");
            }
            else {
                $("#log-in-error-label").css("display", "none");
                // $(".log-in-email-ajax-input").prop("aria-invalid", "false");
            }
        }
    }
}


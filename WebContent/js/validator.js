$(document).ready(function(){
    // The validator for #sign-up-form
	$('#sign-up-form').validate({
		rules: {
			username: {
				required: true,
				minlength: 2,
				maxlength: 10
			},
			email: {
				required: true,
				email: true
			},
			password: {
				required: true,
				minlength: 2,
				maxlength: 16
			},
			cpassword: {
				equalTo: '#password'
			}
		},
		messages: {
			username: {
				required: 'Please enter a username.',
				minlength: 'The username should not be less than 2 words.',
				maxlength: 'The username should not be more than 10 words.'
			},
			email: {
				required: 'Please enter an email.',
				email: 'Email format incorrect.'
			},
			password: {
				required: 'Please enter a password.',
				minlength: 'The password should not be less than 2 words.',
				maxlength: 'The password should not be more than 16 words.'
			},
			cpassword: {
				equalTo: 'Passwords inconsistent.'
			}
		},
		highlight: function(element, errorClass, validClass){
			$(element).parent().addClass("has-error");
		},
		unhighlight: function(element,errorClass){
			$(element).parent().removeClass("has-error");
		}
	});

	// The validator for #log-in-form
	$("#log-in-form").validate({
        // TODO
    });


	// The validator for upload form
	// $("#upload-form").validate({
	// 	rules: {
	// 		fileName: {
	// 			required: true
	// 		}
	// 	}
	// });


	$('#upload-form').validate({
		rules: {
			title: {
				required:true
			}
		},
		message: {
			title: {
				required: "Pleas enter a title."
			}
		},
		highlight: function (element, errorClass, validClass) {
			$(element).parent().addClass('has-error');
        },
		unhighlight: function (element, errorClass) {
			$(element).parent().removeClass('has-error');
        }
	});
});

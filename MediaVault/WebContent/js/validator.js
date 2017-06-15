$(document).ready(function(){
	$('#register').validate({
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
				required: 'Please enter a Username.',
				minlength: 'Username should not be less than 2 words.',
				maxlength: 'Username should not be more than 10 words'
			},
			email: {
				required: 'Please enter an email.',
				email: 'Please enter a correct email format.'
			},
			password: {
				required: 'Please enter password.',
				minlength: 'Password should not be less than 2 words.',
				maxlength: 'Password should not be more than 2 words'
			},
			cpassword: {
				equalTo: 'Two input password must be consistent.'
			}
		},
		highlight: function(element, errorClass, validClass){
			$(element).parent().addClass("has-error");
		},
		unhighlight: function(element,errorClass){
			$(element).parent().removeClass("has-error");
		}
	})
})

$(document).ready(function() {
	// Live email validation
	$('#email').on('input', function() {
		var input=$(this);
		var re = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var is_email=re.test(input.val());
		if(is_email) {
			input.removeClass("is-invalid").addClass("is-valid");
		} else {
			input.removeClass("is-valid").addClass("is-invalid");
		}
	});

	// Show/hide password
	$('#togglePassword').click(function() {
		var input = $('#password');
		if(input.attr('type') == 'password') {
			input.attr('type', 'text');
		} else {
			input.attr('type', 'password');
		}
	});

	// Custom form submission
	$('#submitButton').click(function(e){
		// Assume the form is valid initially
		var isFormValid = true;

		// Check if first name is filled
		if ($('#firstname').val() == '') {
			alert('Please enter your first name');
			isFormValid = false;
		}

		// Check if last name is filled
		if ($('#lastname').val() == '') {
			alert('Please enter your last name');
			isFormValid = false;
		}

		// Check if email is valid
		var emailInput = $('#email');
		var emailRe = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var isEmailValid = emailRe.test(emailInput.val());
		if (!isEmailValid) {
			alert('Please enter a valid email');
			isFormValid = false;
		}

		// Check if password and confirm password match
		if ($('#password').val() != $('#confirmPassword').val()) {
			alert('Passwords do not match');
			isFormValid = false;
		}

		// If form is not valid, prevent its submission
		if (!isFormValid) {
			e.preventDefault();
		}
	});
});

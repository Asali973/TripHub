$(document).ready(function() {


	//CUSTOMER
	// Live email validation
	$('#email').on('input', function() {
		var input = $(this);
		var re = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var is_email = re.test(input.val());
		if (is_email) {
			input.removeClass("is-invalid").addClass("is-valid");
		} else {
			input.removeClass("is-valid").addClass("is-invalid");
		}
	});

	// Show/hide password
	$('#togglePassword').click(function() {
		var input = $('#password');
		if (input.attr('type') == 'password') {
			input.attr('type', 'text');
		} else {
			input.attr('type', 'password');
		}
	});

	// Custom form submission
	$('#submitButton').click(function(e) {
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

	//ORGANIZER
	// Live email validation
	$('#emailO').on('input', function() {
		var input = $(this);
		var re = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var is_emailO = re.test(input.val());
		if (is_emailO) {
			input.removeClass("is-invalid").addClass("is-valid");
		} else {
			input.removeClass("is-valid").addClass("is-invalid");
		}
	});

	// Show/hide password
	$('#togglePasswordO').click(function() {
		var input = $('#passwordO');
		if (input.attr('type') == 'passwordO') {
			input.attr('type', 'text');
		} else {
			input.attr('type', 'passwordO');
		}
	});

	// Custom form submission
	$('#submitButtonO').click(function(e) {
		// Assume the form is valid initially
		var isFormValid = true;

		// Check if first name is filled
		if ($('#firstnameO').val() == '') {
			alert('Please enter your first name');
			isFormValid = false;
		}

		// Check if last name is filled
		if ($('#lastnameO').val() == '') {
			alert('Please enter your last name');
			isFormValid = false;
		}

		// Check if email is valid
		var emailInputO = $('#emailO');
		var emailReO = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var isEmailValidO = emailReO.test(emailInputO.val());
		if (!isEmailValidO) {
			alert('Please enter a valid email');
			isFormValid = false;
		}

		// Check if password and confirm password match
		if ($('#passwordO').val() != $('#confirmPasswordO').val()) {
			alert('Passwords do not match');
			isFormValid = false;
		}

		// If form is not valid, prevent its submission
		if (!isFormValid) {
			e.preventDefault();
		}
	});

	//PROVIDER
	// Live email validation
	$('#emailP').on('input', function() {
		var input = $(this);
		var re = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var is_emailP = re.test(input.val());
		if (is_emailP) {
			input.removeClass("is-invalid").addClass("is-valid");
		} else {
			input.removeClass("is-valid").addClass("is-invalid");
		}
	});

	// Show/hide password
	$('#togglePasswordP').click(function() {
		var input = $('#passwordP');
		if (input.attr('type') == 'passwordP') {
			input.attr('type', 'text');
		} else {
			input.attr('type', 'passwordP');
		}
	});

	// Custom form submission
	$('#submitButtonP').click(function(e) {
		// Assume the form is valid initially
		var isFormValid = true;

		// Check if first name is filled
		if ($('#firstnameP').val() == '') {
			alert('Please enter your first name');
			isFormValid = false;
		}

		// Check if last name is filled
		if ($('#lastnameP').val() == '') {
			alert('Please enter your last name');
			isFormValid = false;
		}

		// Check if email is valid
		var emailInputP = $('#emailP');
		var emailReP = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var isEmailValidP = emailReP.test(emailInputP.val());
		if (!isEmailValidP) {
			alert('Please enter a valid email');
			isFormValid = false;
		}

		// Check if password and confirm password match
		if ($('#passwordP').val() != $('#confirmPasswordP').val()) {
			alert('Passwords do not match');
			isFormValid = false;
		}

		// If form is not valid, prevent its submission
		if (!isFormValid) {
			e.preventDefault();
		}
	});
	
	//SUPERADMIN
	// Live email validation
	$('#emailS').on('input', function() {
		var input = $(this);
		var re = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var is_emailS = re.test(input.val());
		if (is_emailS) {
			input.removeClass("is-invalid").addClass("is-valid");
		} else {
			input.removeClass("is-valid").addClass("is-invalid");
		}
	});

	// Show/hide password
	$('#toggleSasswordS').click(function() {
		var input = $('#passwordS');
		if (input.attr('type') == 'passwordS') {
			input.attr('type', 'text');
		} else {
			input.attr('type', 'passwordS');
		}
	});

	// Custom form submission
	$('#submitButtonS').click(function(e) {
		// Assume the form is valid initially
		var isFormValid = true;

		// Check if first name is filled
		if ($('#firstnameS').val() == '') {
			alert('Please enter your first name');
			isFormValid = false;
		}

		// Check if last name is filled
		if ($('#lastnameS').val() == '') {
			alert('Please enter your last name');
			isFormValid = false;
		}

		// Check if email is valid
		var emailInputS = $('#emailS');
		var emailReS = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
		var isEmailValidS = emailReS.test(emailInputS.val());
		if (!isEmailValidS) {
			alert('Please enter a valid email');
			isFormValid = false;
		}

		// Check if password and confirm password match
		if ($('#passwordS').val() != $('#confirmPasswordS').val()) {
			alert('Passwords do not match');
			isFormValid = false;
		}

		// If form is not valid, prevent its submission
		if (!isFormValid) {
			e.preventDefault();
		}
	});
});

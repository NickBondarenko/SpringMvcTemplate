define(['module', 'jquery', 'utilities'], function(module, $, utils) {
	utils.setActiveNav('#registrationLink');
	$('#firstName').focus();

	var hasBindingErrors = module.config().hasBindingErrors;
	if (hasBindingErrors && Boolean.parse(hasBindingErrors)) {
		$('.alert-error').show();
	}

	$('#username').on('change', function() {
		"use strict";
		var self = this;
		$.getJSON('/registration/checkUsername', {username: this.value}).done(function(data) {
			var $usernameMessage = $('#usernameMessage');
			if (data.usernameExists) {
				if (!$usernameMessage.exists()) {
					$usernameMessage = $('<span />', {id: 'usernameMessage', 'class': 'help-block', style: 'display: none;'}).text('Invalid Username. Username already exists');
					$(self).after($usernameMessage);
				}
				$usernameMessage.fadeIn().parent('div.form-group').addClass('has-error');
			} else {
				if ($usernameMessage.exists()) {
					$usernameMessage.fadeOut().parent('div.form-group').removeClass('has-error');
				}
			}
		});
	});
});
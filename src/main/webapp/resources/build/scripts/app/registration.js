define(['module', 'jquery', 'utilities'], function(module, $, utils) {
	"use strict";

	utils.setActiveNav('#registrationLink');
	$('#firstName').focus();

	var hasBindingErrors = module.config().hasBindingErrors;
	if (Boolean.parse(hasBindingErrors)) {
		$('.alert-error').show();
	}

	var $username = $('#username');
	var $formGroup = $username.closest('div.form-group');
	var $usernameMessage = $formGroup.find('span.help-block');
	if (!$usernameMessage.exists()) {
		$usernameMessage = $('<span />', {id: 'usernameMessage', 'class': 'help-block', style: 'display: none;'});
	}

	var $icon = $username.siblings('span.input-group-addon').children();

	$username.on('change', function() {
		if (this.value.isEmpty()) {
			_resetFormGroup();
			return;
		}

		$.getJSON('/registration/checkUsername', {username: this.value}).done(function(data) {
			$icon.removeClass('glyphicon-user glyphicon-check glyphicon-remove');
			_setValidationState(!data.usernameExists);
		}).fail(_resetFormGroup);
	}).parent().after($usernameMessage);

	function _resetFormGroup() {
		$icon.swapClass('glyphicon-user', 'glyphicon-remove glyphicon-check');
		$formGroup.removeClass('has-success has-error');
		$usernameMessage.hide().empty();
	}

	function _setValidationState(isValid) {
		if (isValid) {
			$usernameMessage.text('Username is available').fadeIn('fast');
			$icon.swapClass('glyphicon-check', 'glyphicon-remove');
			$formGroup.swapClass('has-success', 'has-error');
		} else {
			$usernameMessage.text('Username already exists').fadeIn('fast');
			$icon.swapClass('glyphicon-remove', 'glyphicon-check');
			$formGroup.swapClass('has-error', 'has-success');
		}
	}
});
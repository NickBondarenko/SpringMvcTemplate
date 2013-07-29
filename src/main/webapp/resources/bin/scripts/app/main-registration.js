define(['module', 'jquery', 'utilities'], function(module, $, utils) {
	utils.setActiveNav('#registrationLink');
	$('#firstName').focus();

	var hasBindingErrors = module.config().hasBindingErrors;
	if (hasBindingErrors && Boolean.parse(hasBindingErrors)) {
		$('.alert-error').show();
	}
});
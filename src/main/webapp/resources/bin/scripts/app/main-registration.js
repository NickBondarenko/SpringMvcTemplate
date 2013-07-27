define(['module', 'jquery'], function(module, $) {
	$('li.active').removeClass('active');
	$('#registrationLink').addClass('active');
	$('#firstName').focus();

	var hasBindingErrors = module.config().hasBindingErrors;
	if (hasBindingErrors && Boolean.parse(hasBindingErrors)) {
		$('.alert-error').show();
	}
});
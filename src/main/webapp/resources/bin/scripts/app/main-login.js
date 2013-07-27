define(['module', 'jquery'], function(module, $) {
	$('li.active').removeClass('active');
	$('#loginLink').addClass('active');
	$('#username').focus();
});
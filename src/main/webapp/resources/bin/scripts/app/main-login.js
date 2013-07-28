define(['jquery'], function($) {
	$('li.active').removeClass('active');
	$('#loginLink').addClass('active');
	$('#username').focus();
});
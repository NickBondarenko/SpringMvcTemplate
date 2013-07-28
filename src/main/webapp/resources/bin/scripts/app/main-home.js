define(['jquery'], function($) {
	$('li.active').removeClass('active');
	$('#homeLink').addClass('active');
	$('#username').focus();
});
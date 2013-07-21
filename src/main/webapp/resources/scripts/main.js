define(['modernizr', 'modernizr.tests', 'jquery', 'jquery-ui', 'bootstrap', 'jquery.showtime', 'utilities'], function(Modernizr, modernizrTests, $) {
	console.log('loaded and ready');
	$(document).ready(function() {
		var message = 'Heavy is the head that wears the crown. From the manger to the morgue, strangers are born and reborn.';
		var instance = $('#anotherAction').showtime().on('click', function() {
			instance.showtime('confirm', 'text:' + message);
		});
		if (window.ready) {
			ready();
		}
	});
});
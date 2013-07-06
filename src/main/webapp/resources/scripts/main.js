define(['jquery', 'jquery-ui', 'bootstrap', 'jquery.showtime-2.0'], function($) {
	console.log('loaded and ready');
	$(document).ready(function() {
		var instance = $('#anotherAction').showtime({title: 'My Dialog'}).on('click', function() {
			instance.showtime('dialog', 'text:Content section...');
		});
		if (window.ready) {
			ready();
		}
	});
});
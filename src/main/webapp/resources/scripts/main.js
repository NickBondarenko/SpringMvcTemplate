define(['jquery', 'jquery-ui', 'bootstrap', 'jquery.showtime-2.0'], function($) {
	console.log('loaded and ready');
	$(document).ready(function() {
		var instance = $('#anotherAction').showtime({title: 'My Dialog'});
		instance.showtime('option', 'fixed', true);
		if (window.ready) {
			ready();
		}
	});
});
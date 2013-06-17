define(['jquery', 'jquery-ui', 'bootstrap', 'jquery.showtime'], function($) {
	console.log('loaded and ready');
	$(document).ready(function() {
		$('#anotherAction').showtime('Hello', {title: 'My Dialog'});
		if (window.ready) {
			ready();
		}
	});
});
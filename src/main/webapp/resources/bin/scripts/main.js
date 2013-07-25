define(['es5', 'es6', 'js.extensions', 'modernizr', 'modernizr.tests', 'jquery', 'jquery-ui', 'jquery.extensions', 'bootstrap', 'jquery.showtime', 'utilities', 'domReady!'], function(es5, es6, jsExtensions, Modernizr, modernizrTests, $, $ui, $extensions, bootstrap, $showtime, utils, document) {
	console.log('loaded and ready');
	var message = 'Heavy is the head that wears the crown. From the manger to the morgue, strangers are born and reborn.';
	var instance = $('#anotherAction').showtime().on('click', function() {
		instance.showtime('confirm', 'text:' + message);
	});
	if (window.ready) {
		ready();
	}
});
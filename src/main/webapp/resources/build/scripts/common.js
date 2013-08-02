requirejs.config({
	baseURL: 'resources/build/scripts',
	paths: {
		'require.lib': 'require-js/require',
		domReady: 'require-js/domReady',
		font: 'require-js/font',
		propertyParser: 'require-js/propertyParser',
		es5: 'shims/es5-shim',
		'js.extensions': 'shims/js.extensions-shim',
		bootstrap: 'shims/bootstrap',
		modernizr: 'modernizr/modernizr',
		'modernizr.tests': 'modernizr/modernizr.tests',
		jquery: 'jquery/jquery',
		'jquery-ui': 'jquery/jquery-ui',
		'jquery.extensions': 'jquery/jquery.extensions',
		'jquery.buildr': 'jquery/jquery.buildr',
		'jquery.showtime': 'jquery/jquery.showtime',
		webFont: '//ajax.googleapis.com/ajax/libs/webfont/1/webfont'
	},
	shim: {
		modernizr: {
			exports: 'Modernizr'
		},
		'jquery-ui': {
			deps: ['jquery'],
			exports: 'jQuery.ui'
		},
		bootstrap: {
			deps: ['jquery'],
			exports: 'Bootstrap'
		},
		'jquery.buildr': {
			deps: ['jquery'],
			exports: 'jQuery.fn.buildr'
		},
		webFont: {
			init: function() {
				return WebFont;
			}
		}
	},
	deps: ['js.extensions', 'jquery'],
	onError: function(error) {
		console && console.log(error);
	}
});

requirejs([
	'jquery',
	'jquery-ui',
	'modernizr.tests',
	'jquery.extensions',
	'bootstrap',
	'jquery.showtime',
	'utilities',
	'font',
	'domReady!'
],
function($) {
	var message = 'Heavy is the head that wears the crown. From the manger to the morgue, strangers are born and reborn.';
	var instance = $('#anotherAction').showtime().on('click', function() {
		instance.showtime('confirm', 'text:' + message);
	});
});
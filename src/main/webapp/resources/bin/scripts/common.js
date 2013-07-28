requirejs.config({
	baseURL: 'resources/bin/scripts',
	paths: {
		'require.lib': 'require',
		'font': 'requirejs/font',
		'propertyParser': 'requirejs/propertyParser',
		es5: 'shims/es5-shim',
		'js.extensions': 'shims/js.extensions-shim',
		modernizr: 'modernizr/modernizr-latest',
		'modernizr.tests': 'modernizr/modernizr.tests',
		jquery: 'jquery/jquery-1.10.2',
		'jquery-ui': 'jquery/jquery-ui-1.10.3',
		'jquery.extensions': 'jquery/jquery.extensions',
		'jquery.buildr': 'jquery/jquery.buildr',
		'jquery.showtime': 'jquery/jquery.showtime-2.0'
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
		}
	},
	deps: ['js.extensions', 'jquery'],
	onError: function(error) {
		console && console.log(error);
	}
});

requirejs([
	'modernizr',
	'modernizr.tests',
	'jquery',
	'jquery-ui',
	'jquery.extensions',
	'bootstrap',
	'jquery.showtime',
	'utilities',
	'font',
	'domReady!'
],
function(Modernizr, modernizrTests, $, $ui, $extensions, bootstrap, $showtime, utilities, document) {
	var message = 'Heavy is the head that wears the crown. From the manger to the morgue, strangers are born and reborn.';
	var instance = $('#anotherAction').showtime().on('click', function() {
		instance.showtime('confirm', 'text:' + message);
	});
});
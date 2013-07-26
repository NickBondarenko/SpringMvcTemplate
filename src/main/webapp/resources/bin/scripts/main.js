requirejs.config({
	baseURL: 'resources/bin/scripts',
	paths: {
		'require.lib': 'require',
		es5: 'shims/es5-shim',
		es6: 'shims/es6-shim',
		'js.extensions': 'shims/js.extensions-shim',
		modernizr: 'modernizr/modernizr-latest',
		'modernizr.tests': 'modernizr/modernizr.tests',
		jquery: 'jquery-1.10.2',
		'jquery-ui': 'jquery-ui-1.10.3',
		'jquery.showtime': 'jquery.showtime-2.0'
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
	deps: ['es5', 'es6', 'js.extensions', 'jquery'],
	onError: function(error) {
		console && console.log(error);
	}
});
requirejs(
	[
		'modernizr',
		'modernizr.tests',
		'jquery',
		'jquery-ui',
		'jquery.extensions',
		'bootstrap',
		'jquery.showtime',
		'utilities',
		'domReady!'
	],
	function(Modernizr, modernizrTests, $, $ui, $extensions, bootstrap, $showtime, utils, document) {
		console.log('loaded and ready');
		var message = 'Heavy is the head that wears the crown. From the manger to the morgue, strangers are born and reborn.';
		var instance = $('#anotherAction').showtime().on('click', function() {
			instance.showtime('confirm', 'text:' + message);
		});
	}
);
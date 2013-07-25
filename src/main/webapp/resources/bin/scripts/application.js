requirejs.config({
	baseURL: 'resources/bin/scripts',
	paths: {
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
		'jquery-ui': ['jquery'],
		bootstrap: ['jquery'],
		'jquery.buildr': ['jquery']
	}
});
requirejs(['es5', 'es6', 'js.extensions', 'main']);
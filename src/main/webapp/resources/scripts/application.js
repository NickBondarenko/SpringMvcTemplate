requirejs.config({
	baseURL: 'resources/scripts',
	paths: {
		es5: 'es5-shim.min',
		es6: 'es6-shim.min',
		'js.extensions': 'js.extensions-shim.min',
		jquery: 'jquery-1.10.2.min',
		'jquery-ui': 'jquery-ui/js/jquery-ui-1.10.3.min',
		'jquery.showtime': 'jquery.showtime-2.0',
		bootstrap: 'bootstrap.min'
	},
	shim: {
		'jquery-ui': ['jquery'],
		'jquery.buildr': ['jquery'],
		bootstrap: ['jquery']
	}
});
requirejs(['es5', 'es6', 'js.extensions', 'main']);
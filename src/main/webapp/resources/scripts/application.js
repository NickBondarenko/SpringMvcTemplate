requirejs.config({
	baseURL: 'resources/scripts',
	paths: {
		es5: 'es5-shim.min',
		jquery: 'jquery-1.10.1',
		'jquery-ui': 'jquery-ui/js/jquery-ui-1.10.3.min'
	},
	shim: {
		'jquery-ui': ['jquery'],
		'jquery.buildr': ['jquery'],
		bootstrap: ['jquery']
	}
});
requirejs(['es5', 'main']);
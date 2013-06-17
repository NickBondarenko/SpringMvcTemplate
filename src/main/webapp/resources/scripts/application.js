requirejs.config({
	baseURL: 'resources/scripts',
	paths: {
		jquery: 'jquery-1.10.1',
		'jquery-ui': 'jquery-ui/js/jquery-ui-1.10.3.min'
	},
	shim: {
		'jquery-ui': ['jquery'],
		'jquery.buildr': ['jquery'],
		bootstrap: ['jquery']
	}
});
requirejs(['main']);
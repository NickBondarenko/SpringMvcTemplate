requirejs.config({
	baseURL: 'resources/scripts',
	paths: {
		es5: 'shims/es5-shim.min',
		es6: 'shims/es6-shim.min',
		'js.extensions': 'shims/js.extensions-shim.min',
		main: 'main.min'
	}
});
requirejs(['es5', 'es6', 'js.extensions', 'main']);
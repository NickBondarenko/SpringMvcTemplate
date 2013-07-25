({
	"appDir": ".",
	"baseUrl": "scripts",
	"dir": "../build",
	"modules": [
		{
			"name": "main",
			"include": ["require.lib", "es5", "es6", "js.extensions"]
		}
	],
	"optimize": "uglify2",
	"optimizeCss": "standard",
	"preserveLicenseComments": true,
	"useSourceUrl": false,
	"paths": {
		"require.lib": "require",
		"es5": "shims/es5-shim",
		"es6": "shims/es6-shim",
		"js.extensions": "shims/js.extensions-shim",
		"modernizr": "modernizr/modernizr-latest",
		"modernizr.tests": "modernizr/modernizr.tests",
		"jquery": "jquery-1.10.2",
		"jquery-ui": "jquery-ui-1.10.3",
		"jquery.showtime": "jquery.showtime-2.0"
	},
	"shims": {
		"modernizr": {
			"exports": "Modernizr"
		},
		"modernizr.tests": {
			"deps": ["modernizr"],
			"exports": ""
		},
		'jquery-ui': ['jquery'],
		bootstrap: ['jquery'],
		"jquery.buildr": ["jquery"]
	},
	"fileExclusionRegExp": /^bin$|([\w.]+)?build.js$|application.js/
})
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
	"paths": {
		"require.lib": "require",
		"es5": "shims/es5-shim",
		"es6": "shims/es6-shim",
		"js.extensions": "shims/js.extensions-shim",
		"modernizr": "modernizr/modernizr-latest",
		"modernizr.tests": "modernizr/modernizr.tests",
		"jquery": "jquery-1.10.2",
		"jquery.buildr": "jquery.buildr",
		"jquery-ui": "jquery-ui-1.10.3",
		"jquery.extensions": "jquery.extensions",
		"jquery.showtime": "jquery.showtime-2.0",
		"bootstrap": "bootstrap"
//		"theme": "../themes/default/scripts/theme"
	},
	"shims": {
		"modernizr": {
			"exports": "Modernizr"
		},
		"modernizr.tests": ["modernizr"],
		"jquery.buildr": ["jquery"]
	},
	"fileExclusionRegExp": /^bin$|([\w.]+)?build.js$/
})
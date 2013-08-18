({
	"appDir": ".",
	"baseUrl": "scripts",
	"dir": "../bin",
	"mainConfigFile": "scripts/common.js",
	"modules": [
		{
			"name": "common",
			"include": ["require.lib"]
		},
		{
			"name": "app/home",
			"exclude": ["common"]
		},
		{
			"name": "app/registration",
			"exclude": ["common"]
		} ,
		{
			"name": "app/login",
			"exclude": ["common"]
		},
		{
			"name": "app/admin",
			"exclude": ["common"]
		}
	],
	"optimize": "uglify2",
	"optimizeCss": "standard.keepLines",
	"preserveLicenseComments": false,
	"useSourceUrl": false,
	"fileExclusionRegExp": /^bin$|([\w.]+)?build.js$|([\w.]+).less$|^mixins$/
})
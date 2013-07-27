({
	"appDir": ".",
	"baseUrl": "scripts",
	"dir": "../build",
	"mainConfigFile": "scripts/common.js",
	"modules": [
		{
			"name": "common",
			"include": ["require.lib"]
		},
		{
			"name": "app/main-registration",
			"exclude": ["common"]
		} ,
		{
			"name": "app/main-login",
			"exclude": ["common"]
		},
		{
			"name": "app/main-admin",
			"exclude": ["common"]
		}
	],
	"optimize": "uglify2",
	"optimizeCss": "standard",
	"preserveLicenseComments": false,
	"useSourceUrl": false,
	"fileExclusionRegExp": /^bin$|([\w.]+)?build.js$/
})
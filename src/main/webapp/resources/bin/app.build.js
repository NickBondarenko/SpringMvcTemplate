({
	"appDir": ".",
	"baseUrl": "scripts",
	"dir": "../build",
	"mainConfigFile": "scripts/main.js",
	"modules": [
		{
			"name": "main",
			"include": ["require.lib"]
		}
	],
	"optimize": "uglify2",
	"optimizeCss": "standard",
	"preserveLicenseComments": false,
	"useSourceUrl": false,
	"fileExclusionRegExp": /^bin$|([\w.]+)?build.js$/
})
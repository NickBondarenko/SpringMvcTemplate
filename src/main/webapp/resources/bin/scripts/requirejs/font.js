/** @license
 * RequireJS plugin for loading web fonts using the WebFont Loader
 * Author: Miller Medeiros
 * Version: 0.2.0 (2011/12/06)
 * Released under the MIT license
 */
define(['propertyParser'], function (propertyParser) {
	var parts = /^([^,]+),([^\|]+)\|?/;

	function parseName(name) {
		var vendors = name.split('|');
		var data = {}, match, properties, property;

		for (var i = 0, length = vendors.length; i < length; i++) {
			match = parts.exec(vendors[i]);
			property = match[2].split(':', 1);
			properties = propertyParser.parseProperties(match[2])[property].join(',');
			data[match[1]] = {};
			data[match[1]][property] = [properties];
		}
		return data;
	}

	// API
	return {
		//example: font!google,families:[Tangerine,Cantarell,Yanone Kaffeesatz:700]
		load : function(name, req, onLoad, config){
			if (config.isBuild) {
				onLoad(null); //avoid errors on the optimizer
			} else {
				var data = parseName(name);
				data.active = onLoad;
				data.inactive = function() {
					onLoad(false);
				};
				req([(document.location.protocol === 'https:'? 'https' : 'http') +'://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js'], function() {
					WebFont.load(data);
				});
			}
		}
	};
});
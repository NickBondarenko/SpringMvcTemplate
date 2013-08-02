/** @license
 * RequireJS plugin for loading web fonts using the WebFont Loader
 * Author: Miller Medeiros
 * Version: 0.2.0 (2011/12/06)
 * Released under the MIT license
 */
define(['webFont', 'propertyParser'], function (WebFont, propertyParser) {
	var parts = /^([^,]+),([^\|]+)\|?/;

	function parseName(name) {
		var data = {}, match;
		var vendors = name.split('|');

		vendors.forEach(function(vendor) {
			match = parts.exec(vendor);
			data[match[1]] = propertyParser.parseProperties(match[2]);
		});
		return data;
	}

	// API
	return {
		//example: font!google,families:[Tangerine,Cantarell,Yanone Kaffeesatz:700]
		load : function(name, require, onLoad, config){
			if (config.isBuild) {
				onLoad(null); //avoid errors on the optimizer
			} else {
				var data = parseName(name);

				data.active = onLoad;
				data.inactive = function() {
					onLoad(false);
				};

				WebFont.load(data);
			}
		}
	};
});
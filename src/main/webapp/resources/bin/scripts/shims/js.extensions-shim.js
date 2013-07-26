define(['es5', 'es6'], function(es5, es6, undefined) {
	var arePropertyDescriptorsSupported = function () {
		var attempt = function () {
			Object.defineProperty({}, 'x', {});
			return true;
		};
		var supported = false;
		try { supported = attempt(); }
		catch (e) { /* this is IE 8. */ }
		return supported;
	};

	var supportsDescriptors = !!Object.defineProperty && arePropertyDescriptorsSupported();

	function defineProperties(object, map) {
		Object.keys(map).forEach(function(name) {
			var method = map[name];
			if (name in object) { return; }

			if (supportsDescriptors) {
				Object.defineProperty(object, name, {
					configurable: true,
					enumerable: false,
					writable: true,
					value: method
				});
			} else {
				object[name] = method;
			}
		});
	}

	defineProperties(Array.prototype, {
		isEmpty: function() {
			if (this == null) { throw new TypeError(); }
			return this.length == 0;
		},
		contains: function(valueToFind) {
			if (this == null) { throw new TypeError(); }
			return this.indexOf(valueToFind) > -1;
		},
		remove: function(from, to) {
			if (this == null) { throw new TypeError(); }

			var remainingElements = this.slice((to || from) + 1 || this.length);
			this.length = from < 0 ? this.length + from : from;
			return this.push.apply(this, remainingElements);
		},
		flatten: function(filter) {
			if (this == null) { throw new TypeError(); }
			if (filter && typeof filter != "function") { throw new TypeError(); }

			var flatArray = [];
			for (var i = 0, value, length = Object(this).length >>> 0; i < length; i++) {
				if ((value = this[i]) != null && (!filter || filter.call(value, i, value))) {
					flatArray.push(Array.isArray(value) ? value.flatten(filter) : value);
				}
			}
			return flatArray.concat.apply([], flatArray);
		}
	});

	defineProperties(Boolean, {
		parse: function(value) {
			switch (value.toLowerCase()) {
				case 'true': return true;
				case 'false': return false;
				default: throw new TypeError('Cannot parse ' + value + ' to boolean.');
			}
		}
	});

	defineProperties(Object, {
		instanceOf: function(obj, type) {
			return !!obj && obj.hasOwnProperty && obj instanceof type;
		}
	});

	defineProperties(Object.prototype, {
		/**
		 * Function values. Retrieves the values from an object.
		 * @author jason.dimeo
		 * @returns {Array} An array containing the values from the object
		 */
		values: function() {
			if (this == null) { throw new TypeError(); }

			var values = [];
			for (var i = 0, keys = Object.keys(this), length = keys.length; i < length; i++) {
				values[values.length] = this[keys[i]];
			}
			return values;
		},
		size: function() {
			return Object.keys(this).length;
		}
	});

	defineProperties(String.prototype, {
		isEmpty: function() {
			if (this == null) { throw new TypeError(); }
			return this.length == 0;
		}
	});
});
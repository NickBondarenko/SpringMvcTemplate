/**
 * jQuery Extensions
 * @param {jQuery} $ - The jQuery object
 * @param {undefined} undefined
 */
define('jquery.extensions', ['jquery'], function($, undefined) {
	window.$html = jQuery('html');
	window.$win = jQuery(window);
	window.$doc = jQuery(document).ready(function() {
		window.$body = jQuery('body');
	});

	/** @classDescription regex - Various regex objects. */
	var regex = {
		/** @type {RegExp} script - Check for instance of <script> tag. */
		script: /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi,
		/** @type {RegExp} brackets - Check for instance of []. */
		brackets: /\[(\w+)\]/g,
		/** @type {RegExp} nameBrackets - Matches instances of an array delimeter, '[]', in a input name attribute. */
		nameBrackets: /\[(\D+)\]/g,
		/** @type {RegExp} arrayBrackets - Matches instances of an array delimeter, '[]', in a flattened object. */
		arrayBrackets: /\[(\d+)\]/g,
		/** @type {RegExp} numberInBrackets - Matches instances of a number inside of an array delimeter '[]'. */
		numberInBrackets: /((?!\[)\d+(?=\]))/g,
		/** @type {RegExp} escapable - Check for instance of escapable characters. */
		escapable: /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
		/** @type {RegExp} html - Check for HTML node */
		html: /^(?:html)$/i
	},
	/** @type {string} gap - Used internally in the stringify functions. */
  gap = undefined,
  /** @type {string} indent - Used internally in the stringify functions to determine the number of spaces to indent at each level. */
  indent = undefined,
  /** @type {string} indent - Used internally in the stringify functions that determines how object values are stringified for objects. */
  rep = undefined,
  /** @classDescription meta - Various meta characters. */
  meta = {
	  /**
	   * @type {string} \b - Matches at the position between a word character and a non-word character in a regex.
	   * 		Used internally in the stringify functions.
	   */
    '\b': '\\b',
	  /** @type {string} \t - Matches a tab character using a regex. Used internally in the stringify functions. */
    '\t': '\\t',
	  /** @type {string} \n - Matches a LF character using a regex. Used internally in the stringify functions. */
    '\n': '\\n',
	  /** @type {string} \f - Matches a form feed character using a regex. Used internally in the stringify functions. */
    '\f': '\\f',
	  /** @type {string} \r - Matches a CR character using a regex. Used internally in the stringify functions. */
    '\r': '\\r',
	  /** @type {string} " - Matches a double quote character using a regex. Used internally in the stringify functions. */
    '"': '\\"',
	  /** @type {string} \\ - Matches a backspace character using a regex. Used internally in the stringify functions. */
    '\\': '\\\\'
  },
  /** @type {function} */
  _clone = $.fn.clone;

	function _hasOwnProperty(obj, key) {
		return Object.prototype.hasOwnProperty.call(obj, key);
	}

	function _keys(obj) {
		if ($.isFunction(Object.keys)) {
			return Object.keys(obj);
		}

		var keys = [];
		for (var key in obj) {
			if (_hasOwnProperty(obj, key)) { keys[keys.length] = key; }
		}
		return keys;
	}

	function _instanceOf(obj, type) {
		return !!obj && obj.hasOwnProperty && obj instanceof type;
	}

	// Returns true if it is a DOM node
	function _isNode(elem) {
	  return typeof Node === 'object' ? _instanceOf(elem, Node) : elem && typeof elem === 'object' && typeof elem.nodeType === 'number' && typeof elem.nodeName === 'string';
	}

	// Returns true if it is a DOM element
	function _isElement(elem) {
	  return typeof HTMLElement === 'object' ? _instanceOf(elem, HTMLElement) : elem && typeof elem === 'object' && elem.nodeType === 1 && typeof elem.nodeName === 'string';
	}

	// Returns true if obj is a jQuery object
	function _is$(obj) {
		return _instanceOf(obj, jQuery);
	}

	function _isCheckable(elem) {
		return _isElement(elem) && (elem.type == 'radio' || elem.type == 'checkbox');
	}

	function _cleanValue(value) {
		return typeof value === 'string' ? value.replace(/\r/g, '') : value === null || value === undefined ? '' : value;
	}

	function _valueChanged(elem, originalValue, newValue) {
		if (elem.type == 'radio' || elem.type == 'checkbox') { !!elem.checked; }
	}

	function _getInputValue(elem) {
		if (elem === undefined) { return undefined; }
		var returnValue, type = elem.type,
			nodeName = elem.nodeName.toLowerCase(),
			hooks = $.valHooks[type] || $.valHooks[nodeName];

		if (hooks && 'get' in hooks && (returnValue = hooks.get(elem, 'value')) !== undefined) {
			return returnValue;
		}

		//noinspection FallthroughInSwitchStatementJS
		switch (nodeName) {
			case 'select': case 'textarea': return _cleanValue(elem.value);
			case 'input': return type == 'radio' || type == 'checkbox' ? !!elem.checked ? _cleanValue(elem.value) : undefined : _cleanValue(elem.value);
		}
	}

	function _setInputValue(elem, value) {
		var type = elem.type,
			nodeName = elem.nodeName.toLowerCase(),
			hooks = $.valHooks[type] || $.valHooks[nodeName];

		// If set returns undefined, fall back to normal setting
		if (!hooks || !('set' in hooks) || hooks.set(elem, value, 'value') === undefined) {
			switch (nodeName) {
				case 'select': case 'textarea':
					elem.value = value; break;
				case 'input':
					if (type == 'radio' || type == 'checkbox') {
						if (elem.value === value) { elem.checked = true; }
					} else {
						elem.value = value;
					}
			}
		}
	}

	function _revertValue(elem) {
		if (_isCheckable(elem) && !elem.defaultChecked) {
			return;
		}
		_setInputValue(elem, elem.defaultValue);
	}

	function _getDefaultValue(elem) {
		return _cleanValue(elem.defaultValue);
	}

	function _calculateScrollableOffsets($elem, $scrollableElem) {
		var elem = $elem[0],
  	offsets = {};

  	if ($scrollableElem.exists() && !$scrollableElem.equals($html)) {
  		offsets.containerTop = $scrollableElem.scrollTop();
  		offsets.containerBottom = offsets.containerTop + $scrollableElem.height();
  		offsets.elementTop = elem.offsetTop;
  		offsets.elementBottom = offsets.elementTop + $elem.height();
		}
  	return offsets;
	}

	function _flattenArray(array, arrayFilter) {
		var flatObj = {};
		for (var i = 0, keyName = undefined, arrayEntry = undefined, entryType = undefined, length = array.length; i < length; i++) {
			keyName = '[' + i + ']';
			entryType = $.type(arrayEntry = array[i]);
			if (!arrayFilter || arrayFilter.call(arrayEntry, i, arrayEntry)) {
				if (entryType == 'object' || entryType == 'array') {
					var subObj = entryType == 'array' ? _flattenArray(arrayEntry, arrayFilter) : $.object.flatten(arrayEntry, arrayFilter);
					for (var x = 0, subKeys = $.object.keys(subObj), subKey = undefined, subLength = subKeys.length; x < subLength; x++) {
						subKey = subKeys[x];
						flatObj[entryType == 'object' ? keyName + '.' + subKey : keyName + subKey] = subObj[subKey];
					}
				} else {
					flatObj[keyName] = arrayEntry;
				}
			}
		}
		return flatObj;
	}

	/**
	 * Extensions to jQuery's CSS3 selectors
	 */
	$.extend($.expr[':'], {
		/**
		 * Function attached. Selector used to determine if element is currently attached to the DOM.
		 * @author "Cowboy" Ben Alman
		 * @see http://benalman.com/projects/jquery-misc-plugins/
		 * @param {DOMElement} elem - The DOMElement to check.
		 * @returns {boolean} true if the element exists in the DOM, false otherwise.
		 */
		attached: function(elem, index, meta, stack) {
	    return $.contains(document.documentElement, elem);
		},
		/**
		 * Function detached. Selector used to determine if element is currently detached to the DOM.
		 * @author "Cowboy" Ben Alman
		 * @see http://benalman.com/projects/jquery-misc-plugins/
		 * @param {DOMElement} elem - The DOMElement to check.
		 * @returns {boolean} true if the element does not exist in the DOM, false otherwise.
		 */
		detached: function(elem, index, meta, stack) {
			return !$.contains(document.documentElement, elem);
		},
		/**
		 * Function emptyInput. Selector used to determine if element is an empty input.
		 * @author jason.dimeo
		 * @param {DOMElement} elem - The DOMElement to check.
		 * @returns {boolean} true if the input is empty, false otherwise.
		 */
	  emptyInput: function(elem, index, meta, stack) {
	  	return $(elem).isEmpty();
	  },
	  /**
	   * Function checkable. Selector used to determine if element is a checkable input i.e. radio, checkbox.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the the element is checkable, false otherwise.
	   */
	  checkable: function(elem, index, meta, stack) {
		  return _isCheckable(elem);
	  },
	  /**
	   * Function active. Selector used to determine if element is enabled and visible.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the element is active, false otherwise.
	   */
	  active: function(elem, index, meta, stack) {
	  	return $(elem).is(':enabled:visible');
	  },
	  /**
	   * Function inactive. Selector used to determine if element is not enabled and visible.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the element is inactive, false otherwise.
	   */
	  inactive: function(elem, index, meta, stack) {
	  	return !$(elem).is(':active');
	  },
	  /**
	   * Fucntion activeInput. Selector used to determine if input element is enabled and visible.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the input element is active, false otherwise.
	   */
	  activeInput: function(elem, index, meta, stack) {
	  	return $(elem).is(':input:enabled:visible');
	  },
	  /**
	   * Fucntion activeTextInput. Selector used to determine if text input element is enabled and visible.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the text input element is enabled and visible, false otherwise.
	   */
	  activeTextInput: function(elem, index, meta, stack) {
	  	return $(elem).is('input:text:enabled:visible');
	  },
	  /**
	   * Function exists. Selector used to determine if element exists.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the element exists, false otherwise.
	   */
	  exists: function(elem, index, meta, stack) {
	  	return $(elem).exists();
	  },
	  /**
	   * Function viewable. Selector used to determine if an element is in the viewport and visible to the user.
	   * @author jason.dimeo
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the element is viewable, false otherwise.
	   */
	  viewable: function(elem, index, meta, stack) {
	    var $elem = $(elem),
    	scrollableOffsets = _calculateScrollableOffsets($elem, $elem.closest(':scrollable')),
    	scrollTop = (document.documentElement.scrollTop || document.body.scrollTop),
    	offsetTop = $elem.offset().top,
    	viewable = offsetTop > scrollTop && ($elem.height() + offsetTop) < (scrollTop + $.clientHeight());

	  	if (viewable && !$.isEmptyObject(scrollableOffsets)) {
				viewable = scrollableOffsets.elementTop >= scrollableOffsets.containerTop && scrollableOffsets.elementBottom <= scrollableOffsets.containerBottom;
			}
	    return viewable;
	  },
	  /**
	   * Function external. Selector used to determine if href attribute of an anchor tag is an external link.
	   * @author ?
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @returns {boolean} true if the anchor element's href attribute is external, false otherwise.
	   */
	  external: function(elem, index, meta, stack) {
	    if (!elem.href) { return false; }
	    return elem.hostname && elem.hostname !== window.location.hostname;
	  },
	  /**
	   * Selector used to determine of an element is scrollable. By scrollable it means that its content
	   * exceeds its view area and that is also displays scrollbars (using <code>overflow:hidden</code> won't select
	   * element as scrollable). This checking is therefore implemented as a <code>:scrollable</code> selector filter that anyone can use.
	   * @author Robert Koritnik
	   * @see https://github.com/litera/jquery-scrollintoview
	   * @param {DOMElement} elem - The DOMElement to check.
	   * @param {} index
	   * @param {} meta
	   * @param {} stack
	   * @returns {boolean} true if the element is scrollable, false otherwise.
	   */
		scrollable: function (element, index, meta, stack) {
			var converter = {
				vertical: { x: false, y: true },
				horizontal: { x: true, y: false },
				both: { x: true, y: true },
				x: { x: true, y: false },
				y: { x: false, y: true }
			},
			scrollValue = {
				auto: true,
				scroll: true,
				visible: false,
				hidden: false
			},
			direction = converter[typeof (meta[3]) === "string" && meta[3].toLowerCase()] || converter.both,
			styles = (document.defaultView && document.defaultView.getComputedStyle ? document.defaultView.getComputedStyle(element, null) : element.currentStyle),
			overflow = {
				x: scrollValue[styles.overflowX.toLowerCase()] || false,
				y: scrollValue[styles.overflowY.toLowerCase()] || false,
				isRoot: regex.html.test(element.nodeName)
			};

			// check if completely unscrollable (exclude HTML element because it's special)
			if (!overflow.x && !overflow.y && !overflow.isRoot)	{	return false;	}

			var size = {
				height: {
					scroll: element.scrollHeight,
					client: element.clientHeight
				},
				width: {
					scroll: element.scrollWidth,
					client: element.clientWidth
				},
				// check overflow.x/y because iPad (and possibly other tablets) don't dislay scrollbars
				scrollableX: function () {
					return (overflow.x || overflow.isRoot) && this.width.scroll > this.width.client;
				},
				scrollableY: function () {
					return (overflow.y || overflow.isRoot) && this.height.scroll > this.height.client;
				}
			};
			return direction.y && size.scrollableY() || direction.x && size.scrollableX();
		}
	});

	/* Extensions */
	$.fn.extend({
		/**
		 * Function each$. Iterate over a jQuery object, executing a function for each matched element.
		 * Similar to the jQuery .each function. The only difference is the second argument passed to the iterator
		 * function is a jQuery object instead of the DOMElement. Execution speed is much better than instantiating
		 * a new jQuery object on each iteration. The `this` keyword still points to the DOMElement.
		 * Inspired by Ben Alman's each2 jQuery plugin.
		 * @author jason.dimeo
		 * @see http://benalman.com/projects/jquery-misc-plugins/
		 * @param {function} iterator - Function to call at each iteration. function(index, $elem)
		 * @returns {jQuery} The jQuery object passed to the function.
		 */
	  each$: function(iterator) {
			for (var i = 0, length = this.length, $elem = $([1]); i < length; i++) {
				if (iterator.call($elem.context = $elem[0] = this[i], i, $elem) === false) { break; }
			}
			return this;
	  },
		exists: function() {
			return this.length > 0;
		},
	  /**
	   * Function value. Gets or sets the value of an input.
	   * @param {any} value - The value to set. (optional)
	   * @returns {jQuery|any} If a value is passed to the function, the jQuery element, else the value of the input.
	   */
	  value: function(value) {
			if (!arguments.length || value === undefined) {
				var elemLength = this.length;
				var elem = undefined,	returnValue = undefined;
				if (!elemLength) { return; }
				for (var i = 0; i < elemLength; i++) {
					if (_isCheckable(elem = this[i])) {
						if (!!!elem.checked) { continue; }
						returnValue = elem;
					} else {
						returnValue = this[0];
						break;
					}
				}
				return _getInputValue(returnValue);
			}

			var val = undefined,
				isFunction = $.isFunction(value);

			return this.each(function(index, elem) {
				if (!_isElement(this)) { return; }
				val = isFunction ? value.call(this, index, _getInputValue(this)) : value;

				// Treat null/undefined as ''; convert numbers to string
				if (val == null) {
					val = '';
				} else if (typeof val === 'number') {
					val += '';
				} else if ($.isArray(val)) {
					val = $.map(val, function(value) {
						return value == null ? '' : value + '';
					});
				}
				_setInputValue(this, val);
			});
	  },
//	  defaultValue: function(value) {
//
//	  },
//	  revert: function() {
//
//	  },
	  root: function() {
	  	var $elem = undefined;
	  	this.each$(function(index, $this) {
	  		if ($this.parent().length === 0) {
	  			$elem = $this;
	  			return false;
	  		}
	  	});

	  	return $elem || this;
	  },
	  /**
	   * Function changeValue. Sets the value of the input and triggers the change
	   * event if the value parameter is different than the existing value.
	   * @param {any} value - The value to set.
	   * @param {boolean} force - Force the value to be updated and the event triggered.
	   * @returns {jQuery} The jQuery object passed to the function.
	   */
	  changeValue: function(value, force) {
	  	if (value === undefined) { return this; }

	  	var originalValue;
	  	return this.each$(function(index, $elem) {
		  	originalValue = _getInputValue(this);

		  	if (force || originalValue !== _getInputValue(this)) {
					$elem.value(value).trigger('change');
				}
	  	});
	  },
	  /**
	   * Function mapInputs. Creates an Object from input values.
	   * @author jason.dimeo
	   * @param {object} customFieldMap - Custom field mapping Object.
	   * @returns {object} An Object representation of input fields.
	   */
		mapInputs: function(customFieldMap) {
			var value = undefined, name = undefined, returnObj = {};
			this.find(':input[name]').not(':button').each(function(index) {
				value = _getInputValue(this);
				if (value !== undefined) {
					name = this.name;
					if (customFieldMap && customFieldMap[name]) {
						returnObj[customFieldMap[name]] = value;
					} else {
						returnObj[name] = value;
					}
				}
			});
			return returnObj;
		},
		/**
		 * Function mapModel. Fills input values from an Object using the name attribute as the key.
		 * Functionally oposite to the mapInputs function.
		 * @author jason.dimeo
		 * @param {object} dataModel - The Object that holds the name and values of the inputs to fill.
		 * @param {object} inputMap - Optional key mapping to use instead of the name in the dataModel.
		 * @returns {jQuery} The jQuery object passed to the function.
		 */
		mapModel: function(dataModel, inputMap) {
			var inputName = undefined;
			this.find(':input[name]').not(':button').each(function(index, elem) {
				inputName = inputMap ? inputMap[this.name] : this.name,
					inputValue = $.object.getProperty(inputName, dataModel);

				if (inputValue) {
					_setInputValue(this, inputValue);
					this.defaultValue = inputValue;
				}
			});
			return this;
		},
		clone: function(withDataAndEvents, deepWithDataAndEvents) {
			var inputSelectors = 'input, select, textarea';
			var $clonedElements = _clone.apply(this, arguments);
			var $originalInputs = this.find(inputSelectors).add(this.filter(inputSelectors));
			var $clonedInputs = $clonedElements.find(inputSelectors).add($clonedElements.filter(inputSelectors));
			$clonedInputs.each$(function(index, $elem) {
				switch (this.nodeName.toLowerCase()) {
					case 'input': case 'textarea':
						$elem.attr('value', $elem.val());
						break;
					case 'select':
						$elem.prop('selectedIndex', $originalInputs[index].selectedIndex);
						break;
				}
			});
			return $clonedElements;
		},
		/**
		 * function outerHTML. Retrieves the HTML of selected element. Uses the outerHTML property if it exists.
		 * @returns {string} The HTML of the first element in the set of matched elements.
		 */
		outerHTML: function() {
			if (!this.exists()) { return this; }
			var elem = this[0];
			if (elem.outerHTML) {	return elem.outerHTML; }
			return $('<div />').append(this.clone()).html();
		},
		scrollIntoView: function(options) {
			if (this.exists()) {
				if ($.isFunction(options)) {
					options = {complete: options};
				}

				var $scrollableElement = this.closest(':scrollable'),
				scrollableOffsets = _calculateScrollableOffsets(this, $scrollableElement);
				if (scrollableOffsets.elementTop < scrollableOffsets.containerTop) {
					$scrollableElement.animate({scrollTop: scrollableOffsets.elementTop});
				} else if (scrollableOffsets.elementBottom > scrollableOffsets.containerBottom) {
					$scrollableElement.animate({scrollTop: scrollableOffsets.elementBottom - $scrollableElement.height()}, $.extend({duration: 'slow', queue: false}, options));
				}
			}
			return this;
		}
	});

	$.extend({
		preloadImages: function(imageArray) {
			return $.Deferred(function(dfd) {
				if (!$.isArray(imageArray) || $.array.isEmpty(imageArray)) { dfd.resolve(); }
				for (var i = 0, numberOfImages = imageArray.length, numberOfLoadedImages = 0; i < numberOfImages; i++) {
					var $image = $(new Image()).on('load', function() {
						if (++numberOfLoadedImages === numberOfImages) { dfd.resolve(); }
					}).attr('src', imageArray[i]);

					if (dfd.state() == 'pending' && $image.prop('complete')) {
						$image.trigger('load');
					}
				}
			}).promise();
		},
	  isNode: _isNode,
	  isElement: _isElement,
		/**
		 * Function is$ - Determines if an object is a jQuery object.
		 * Since every jQuery object has a .jquery property, it's usually safe to test
		 * the existence of that property. Of course, this only works as long as you
		 * know that any non-jQuery object you might be testing has no .jquery property.
		 * So.. what do you do when you need to test an external object whose properties you don't know?
		 *
		 * If you currently use instanceof, read this Ajaxian article:
		 * http://ajaxian.com/archives/working-aroung-the-instanceof-memory-leak
		 *
		 * jQuery isjQuery - v0.4 - 2/13/2010
		 * Renamed to is$ - v0.4.1 10/23/2011
		 * http://benalman.com/projects/jquery-misc-plugins/
		 * Copyright (c) 2010 "Cowboy" Ben Alman
		 * Dual licensed under the MIT and GPL licenses.
		 * http://benalman.com/about/license/
		 *
		 * @author Ben Alman
		 * @param {object} obj - The object to test
		 * @returns {boolean} true if the object is a jQuery object
		 */
		is$: _is$,
	  checkedValue: function(name, value) {
	  	return $('input:checkable[name="' + name + '"]').value(value);
	  },
	  documentHeight: function() {
	  	if ($.support.boxModel) { return $doc.height(); }
			var scrollHeight = Math.max(document.documentElement.scrollHeight, document.body.scrollHeight),
				offsetHeight = Math.max(document.documentElement.offsetHeight, document.body.offsetHeight);
			return scrollHeight < offsetHeight ? $win.height() : scrollHeight;
	  },
	  documentWidth: function() {
	  	if ($.support.boxModel) { return $doc.width(); }
			var scrollWidth = Math.max(document.documentElement.scrollWidth, document.body.scrollWidth),
				offsetWidth = Math.max(document.documentElement.offsetWidth, document.body.offsetWidth);
			return scrollWidth < offsetWidth ? $win.width() : scrollWidth;
	  },
	  clientHeight: function() {
	  	return $.support.boxModel ? document.documentElement.clientHeight : Math.max(document.documentElement.clientHeight, document.body.clientHeight);
	  },
	  clientWidth: function() {
	  	return $.support.boxModel ? document.documentElement.clientWidth : Math.max(document.documentElement.clientWidth, document.body.clientWidth);
	  },
	  /**
	   * Function scrollbarWidth. Calculates the scrollbar width dynamically.
	   *
	   * jQuery scrollbarWidth - v0.2 - 2/11/2009
	   * http://benalman.com/projects/jquery-misc-plugins/
	   * Copyright (c) 2010 "Cowboy" Ben Alman
	   * Dual licensed under the MIT and GPL licenses.
	   * http://benalman.com/about/license/
	   *
	   * @author Ben Alman
	   * @returns {int} The width of the scrollbar
	   */
		scrollbarWidth: function() {
	    var parent = $('<div style="width:50px;height:50px;overflow:auto"><div /></div>').appendTo($body),
		    child = parent.children(),
		    width = child.innerWidth() - child.height(99).innerWidth();
	    parent.remove();

		  return width;
		},
	  object: {
	  	/**
	  	 * Function keys. Retrieves the keys of an object.
	  	 * Uses the browsers native keys() function if it exists.
	  	 * @author jason.dimeo
	  	 * @param {object} obj - The object to retrieve the keys from
	  	 * @returns {array} An array contaning the keys from the object
	  	 */
			keys: _keys,
			/** @see _instanceOf */
			instanceOf: _instanceOf,
			/** @see _hasOwnProperty */
			hasOwnProperty: _hasOwnProperty,
			/**
			 * Function values. Retrieves the values from an object.
			 * @author jason.dimeo
			 * @param {object} obj - The object to retrieve the values from
			 * @returns {array} An array contaning the values from the object
			 */
			values: function(obj) {
				var values = [];
				for (var i = 0, keys = _keys(obj), length = keys.length; i < length; i++) {
					values[values.length] = obj[keys[i]];
				}
				return values;
			},
			/**
			 * Function filter. Creates and returns a new object with all the objects that satisfies the predicate filter.
			 * @author jason.dimeo
			 * @param {object} obj - The object to apply the filter to
			 * @param {function} filter - The predicate function to apply
			 * @returns {object} A new object with the values that satisfy the predicate
			 */
			filter: function(obj, filter) {
				var newObj = {};
				for (var i = 0, keys = _keys(obj), key = undefined, value = undefined, length = keys.length; i < length; i++) {
					if (filter.call(value = obj[key = keys[i]], key, value)) {
						newObj[key] = value;
					}
				}
				return newObj;
			},
			/**
			 * Function flatten. Takes any nested properties in the Object and returns a new Object with all the properties
			 * in the root of the Object. Nested key names are delimited with a '.' character. The original Object is unmodified.
			 * @author jason.dimeo
			 * @param {object} obj - The Object to flatten.
			 * @param {function} filter - An optional filter function.
			 * @returns {object} A new Object with all properties in the root.
			 */
			flatten: function(obj, filter) {
				var newObj = {};

				for (var i = 0, keys = _keys(obj), key = undefined, entry = undefined, type = undefined, length = keys.length; i < length; i++) {
					type = $.type(entry = obj[key = keys[i]]);
					if (!filter || filter.call(entry, key, entry)) {
						switch (type) {
							case 'object':
								if (!$.isPlainObject(entry) || $.isEmptyObject(entry)) {
									newObj[key] = entry;
								} else {
									for (var x = 0, subObj = $.object.flatten(entry, filter), subKeys = _keys(subObj), subKey = undefined, subLength = subKeys.length; x < subLength; x++) {
										newObj[key + '.' + (subKey = subKeys[x])] = subObj[subKey];
									}
								}
								break;
							case 'array':
								if (entry.length == 0) {
									newObj[key] = entry;
								} else {
									for (var y = 0, arrayLength = entry.length; y < arrayLength; y++) {
										for (var x = 0, subObj = _flattenArray(entry, filter), subKeys = _keys(subObj), subLength = subKeys.length; x < subLength; x++) {
											newObj[key + (subKey = subKeys[x])] = subObj[subKey];
										}
									}
								}
								break;
							default:
								newObj[key] = entry;
								break;
						}
					}
				}
				return newObj;
			},
			/**
			 * Function expand. Expands a flattened object back to it's original state.
			 * A flattened object is one that has no nested properties. The key will be a period delimeted string. i.e. (obj.key.property)
			 * @param {object} obj - The object to expand
			 * @returns {object} A new object with the expanded properties
			 */
			expand: function(obj) {
				var newObj = {};
				for (var i = 0, keys = _keys(obj), key = undefined, len = keys.length; i < len; i++) {
					$.object.setProperty(newObj, key = keys[i], obj[key]);
				}
				return newObj;
			},
			/**
			 * Function getProperty. Gets a property from an Object using a path String.
			 * @param {string} path - A period delimiter is used for object key. i.e. (obj.key).
			 * 	A bracket with an index number is used as a delimeter for an array. i.e. (obj[index])
			 * @param {object} object - The source Object.
			 * @returns {?} The property value.
			 */
			getProperty: function(path, object) {
				if (!path) { return undefined; }

				var property = undefined, arrayIndexes = undefined, arrayIndex = undefined;
				for (var index = 0, entries = path.replace(regex.nameBrackets, '.$1').split('.'), length = entries.length, entry = undefined; index < length; index++) {
					property = property || object;

					if (arrayIndexes = (entry = entries[index]).match(regex.numberInBrackets)) {
						property = (entry = entry.substring(0, entry.indexOf('['))) in property ? property[entry] : property[entry] = [];
						for (var i = 0, arrayLength = arrayIndexes.length, indexStop = arrayLength -1; i < arrayLength; i++) {
							if (!((arrayIndex = arrayIndexes[i]) in property)) { return undefined; }

							if (i < indexStop) {
								property = property[arrayIndex];
							}
						}
					}
					property = (arrayIndex || entry) in property ? property[arrayIndex || entry] : undefined;
				}
				return property;
			},
			/**
			 * Function setProperty. Sets a property on an Object using a path String. If property does not exist, it will be added.
			 * @param {object} object - The target Object.
			 * @param {string} path - A period delimiter is used for object key. i.e. (obj.key).
			 * 	A bracket with an index number is used as a delimeter for an array. i.e. (obj[index])
			 * @param {any} value - The value to set in the target Object.
			 * @returns {Boolean} true if the property was set, false otherwise.
			 */
			setProperty: function(object, path, value) {
				if (!path) { return false; }

				var property = undefined, arrayIndexes = undefined, arrayIndex = undefined;
				for (var index = 0, entries = path.replace(regex.nameBrackets, '.$1').split('.'), length = entries.length, entry = undefined; index < length; index++) {
					property = property || object;
					entry = entries[index];

					if (arrayIndexes = entry.match(regex.numberInBrackets)) {
						property = (entry = entry.substring(0, entry.indexOf('['))) in property ? property[entry] : property[entry] = [];
						for (var i = 0, arrayLength = arrayIndexes.length; i < arrayLength; i++) {
							arrayIndex = arrayIndexes[i];
							if (i < arrayLength - 1) {
								property = arrayIndex in property ? property[arrayIndex] : property[arrayIndex] = [];
							}
						}
					}

					if (index < length - 1) {
						property = (arrayIndex || entry) in property ? property[arrayIndex || entry] : property[arrayIndex || entry] = {};
					} else {
						property[arrayIndexes ? arrayIndex : entry] = value;
					}
					arrayIndex = undefined;
				}
				return property !== undefined;
			},
			/**
			 * Function serialize. Serializes the object according to the options.
			 * @author jason.dimeo
			 * @param obj
			 * @param options
			 * @returns {string}
			 */
			serialize: function(obj, options) {
				var params = [], opts = options || {};
				for (var i = 0, keys = _keys($.object.flatten(obj, opts.filter)), key = undefined, value = undefined, length = keys.length; i < length; i++) {
					key = keys[i], value = obj[key];
					if (opts.fieldMap && key in opts.fieldMap) {
						key = opts.fieldMap[key];
					}
					params[params.length] = encodeURIComponent(key) + '=' + encodeURIComponent(value);
				}
				return params.join('&').replace(/%20/g, '+');
			},
			/**
			 * Function toArray. Converts an Object to an Array of the Object's key/value pairs.
			 * @param {object} obj - Object to convert.
			 * @returns {Array} The converted Array.
			 */
			toArray: function(obj) {
				var array = [];
				for (var i = 0, keys = _keys(obj), key = undefined, length = keys.length; i < length; i++) {
					array[array.length] = {}[key = keys[i]] = obj[key];
				}
				return array;
			},
			/**
			 * Function size. Obtains the number of properties in the Object.
			 * @param {object} object - The Object to inspect
			 * @returns {Number} The number of properties in the Object.
			 */
			size: function(object) {
				return _keys(object).length;
			},
			transform: function(obj, iterator) {
				var newObj = {};
				for (var i = 0, keys = $.object.keys(obj), key = undefined, value = undefined, len = keys.length; i < len; i++) {
					$.extend(newObj, iterator.call(value = obj[key = keys[i]], key, value));
				}
				return newObj;
			}
	  },
	  array: {
		  isEmpty: function(array) {
			  return array.length === 0;
		  },
			flatten: function(array, filter) {
				var flatArray = [];
				for (var i = 0, value = undefined, len = array.length; i < len; i++) {
					if ((value = array[i]) != null && (!filter || filter.call(value, i, value))) {
						flatArray.push($.isArray(value) ? $.array.flatten(value, filter) : value);
					}
				}
				return flatArray.concat.apply([], flatArray);
			}
	  },
//	  string: {
//
//	  },
//	  date: {
//
//	  },
	  /**
	   * Namespace bool. Holds all the boolean extensions
	   * @namespace
	   */
	  bool: {
	  	/**
	  	 * Function parse. Parses a string into boolean equivalent value.
	  	 * @param {string} value - The value to be parsed
	  	 * @returns {Boolean} The boolean equivalent.
	  	 * @throws {TypeError} Unable to parse value.
	  	 */
			parse: function(value) {
			  switch (value.toLowerCase()) {
			    case 'true': return true;
			    case 'false': return false;
			    default: throw new TypeError('Cannot parse ' + value + ' to boolean.');
			  }
			}
	  }
	});
});

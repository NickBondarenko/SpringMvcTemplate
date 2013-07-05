(function(Modernizr, window) {
	Modernizr.addTest('positionfixed', function () {
		var test  = document.createElement('div'),
				control = test.cloneNode(false),
				fake = false,
				root = document.body || (function () {
					fake = true;
					return document.documentElement.appendChild(document.createElement('body'));
				}());

		var oldCssText = root.style.cssText;
		root.style.cssText = 'padding:0;margin:0';
		test.style.cssText = 'position:fixed;top:42px';
		root.appendChild(test);
		root.appendChild(control);

		var ret = test.offsetTop !== control.offsetTop;

		root.removeChild(test);
		root.removeChild(control);
		root.style.cssText = oldCssText;

		if (fake) {
			document.documentElement.removeChild(root);
		}

		return ret;
	});

	Modernizr.addTest('iospositionfixed', function () {
		var test  = document.createElement('div'),
				ret,
				fake = false,
				root = document.body || (function () {
					fake = true;
					return document.documentElement.appendChild(document.createElement('body'));
				}());

		if (typeof document.body.scrollIntoViewIfNeeded === 'function') {

			var oldCssText = root.style.cssText,
					testScrollTop = 20,
					originalScrollTop = window.pageYOffset;

			root.appendChild(test);

			test.style.cssText = 'position:fixed;top:0px;height:10px;';

			root.style.height="3000px";

			/* avoided hoisting for clarity */
			var testScroll = function() {
				if (ret === undefined) {
					test.scrollIntoViewIfNeeded();
					ret = window.pageYOffset === testScrollTop;
				}
				window.removeEventListener('scroll', testScroll, false);
			};

			window.addEventListener('scroll', testScrollTop, false);
			window.setTimeout(testScroll, 20); // ios 4 doesn't publish the scroll event on scrollto
			window.scrollTo(0, testScrollTop);
			testScroll();

			root.removeChild(test);
			root.style.cssText = oldCssText;
			window.scrollTo(0, originalScrollTop);

		} else {
			ret = Modernizr.positionfixed; // firefox and IE doesn't have document.body.scrollIntoViewIfNeeded, so we test with the original modernizr test
		}

		if (fake) {
			document.documentElement.removeChild(root);
		}

		return ret;
	});

	Modernizr.addTest('compliantzindex', function(){
		var test  = document.createElement('div'),
				fake = false,
				root = document.body || (function () {
					fake = true;
					return document.documentElement.appendChild(document.createElement('body'));
				}());

		root.appendChild(test);
		test.style.position = 'relative';
		var ret = (test.style.zIndex !== 0);
		root.removeChild(test);

		if (fake) {
			document.documentElement.removeChild(root);
		}

		return ret;
	});

	Modernizr.addTest('cssfilters', function() {
		var el = document.createElement('div');
		el.style.cssText = Modernizr._prefixes.join('filter' + ':blur(2px); ');
		return !!el.style.length && ((document.documentMode === undefined || document.documentMode > 9));
	});
})(Modernizr, window);
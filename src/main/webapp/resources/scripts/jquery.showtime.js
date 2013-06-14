(function($, undefined) {
  var plugin = {
	  $st: undefined,
		$showtime: undefined,
		$container: undefined,
		$content: undefined,
	  $header: undefined,
		$footer: undefined,
		$loading: undefined,
		$closeBtnImage: undefined,
		initialized: false,
		open: false,
		settings: undefined,
		imageCache: undefined,
		currentScrollTop: undefined,
		pluginErrorText: 'Showtime Error',
		bypassFixed: false,
		mode: 'dialog',
		eventMappings: {
			'showtime.open': function(e, content, opts) {
				e.preventDefault();
				events.open(e, content, opts);
			},
			'showtime.complete': function(e) {
				events.complete(e);
			},
			'keydown.showtime': function(e) {
				events.escape(e);
			},
			'showtime.close': function(e) {
				events.close(e);
			},
			'showtime.button-click': function(e, elem, buttonName, buttonValue) {
				events.buttonClick(e, elem, buttonName, buttonValue);
			}
		}
	};
	$.fn.showtime = function(content, options) {
		if ($.isPlainObject(content)) {
			options = content;
		}

		if (!plugin.initialized) {
			events.init.apply(this, [options]);
			options = null;
		}

		return this.each$(function(index, $this) {
	  	if (!$this.exists()) {
	  		return;
	  	}

	  	if (options) {
				$this.data('showtime-options', options);
			}

			var helpText = $this.hasClass('helpTextField');
	  	if (helpText) {
	  		$this.on('click', function(e) {
		  		e.preventDefault();
				  plugin.mode = 'helpText';
		  		plugin.$showtime.trigger('showtime.open', [{$elem: $(this)}]);
	  		});
			} else {
		  	$this.on('click', function(e) {
		  		e.preventDefault();
				  plugin.mode = 'dialog';
		  		plugin.$showtime.trigger('showtime.open', [content, {$elem: $doc}]);
		  	});
			}
		});
	};

	$.showtime = {
		defaults: {
			showOverlay: true,
			modalOverlay: false,
			opacity: 0.75,
			minWidth: 350,
			minHeight: 32,
			maxWidth: 550,
			fixed: true,
			autoSizeAfterOpen: false,
			ctxPath: '../../resources/',
			speed: 200,
			title: '',
			font: {families: ['Average+Sans::latin'], url: '://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js'},
			content: undefined,
			helpAttribute: 'for',
			draggable: {cursor: 'move', handle: '#showtimeHeader, #showtimeFooter', opacity: 0.40, cancel: '#showtimeContent, .showtime-button, #closeBtn', initialized: false},
			resizable: {alsoResize: '#showtimeContent', autoHide: true, handles: 'e, se, s'},
			buttons: {close: null},
    	loadingMessage: 'Loading, please wait...',
      loadingImage: 'images/loading.gif',
			callback: $.noop,
			ajaxOpts: {}
		},
		open: function(content, options) {
			if ($.isPlainObject(content)) {
				options = content;
			}

	  	if (options) {
			  $doc.data('showtime-options', options);
			} else {
			  $doc.removeData('showtime-options');
		  }

			if (!plugin.initialized) {
				events.init.apply(this, [options]);
				options = null;
			}
			plugin.mode = 'dialog';
			plugin.$showtime.trigger('showtime.open', [content, {$elem: $doc}]);
		},
		showLoading: function(options) {
			var currentSettings;
			if (!plugin.initialized) {
				events.init(options || {});
			} else {
				currentSettings = plugin.settings;
				plugin.settings = $.extend({}, $.showtime.defaults, options || {});
				plugin.settings.callback = function() {
					plugin.settings = $.extend({}, $.showtime.defaults, currentSettings);
				};
			}

			plugin.settings.modalOverlay = true;
			plugin.mode = 'loading';
			plugin.$showtime.trigger('showtime.open', [{mode: 'loading'}]);
		},
	  close: function() {
			if (plugin.initialized) { plugin.$showtime.trigger('showtime.close'); }
		},
		isOpen: function() {
			return plugin.open;
		}
	};

  /*
   * Private Functions
   */
	var events = {
		init: function(options) {
			try {
				plugin.settings = $.extend({}, $.showtime.defaults, options);
				plugin.$showtime = buildHtml();
				$body.append(plugin.$showtime);

				loadFont(plugin.settings.font);

				plugin.$container = $('#showtimeContainer');
				plugin.$content = $('#showtimeContent');
				plugin.$footer = $('#showtimeFooter');
				plugin.$closeBtnImage = $('#closeBtnImage');

				// Bind custom events
				if (!plugin.initialized) {
					addShowtimeEvents();
				}

		    // cache images
		    plugin.imageCache = {loadingImage: new Image()};
		    plugin.imageCache.loadingImage.src = plugin.settings.ctxPath + plugin.settings.loadingImage;

		    plugin.initialized = true;
			} catch (e) {
				plugin.initialized = false;
			}
		},
		open: function(e, content, opts) {
			try {
				if (plugin.open) { return; }
				plugin.open = true;

				plugin.settings = $.extend({}, $.showtime.defaults, opts || {});

				addDocumentEvents();

				var $this = opts.$elem,
				runtimeOptions;
				if ($this) {
					runtimeOptions = $this.data('showtime-options');
					if (runtimeOptions) {
						plugin.cachedSettings = plugin.settings;
						plugin.settings = $.extend({}, $.showtime.defaults, runtimeOptions);
					}
				}

				$.each(plugin.settings.buttons, function(key, value) {
					$('#showtimeButtons').build(function(buildr) {
						buildr.button(key.charAt(0).toUpperCase() + key.slice(1), {id: key + 'ShowtimeButton', 'class': 'showtime-button default'}).on('click.showtime', function(e) {
							plugin.$showtime.trigger('showtime.button-click', [this, key, value]);
						});
					});

					if ($.support.quirksMode) {
						$('#' + key + 'ShowtimeButton').on('mouseenter mouseup', function(e) {
							$(this).toggleClasses({hover: true, 'default': false});
						}).on('mouseleave mousedown', function(e) {
							$(this).toggleClasses({hover: false, 'default': true});
						});
					}
				});

				switch (plugin.mode) {
					case 'loading':
						launchShowtime();
						break;
					case 'dialog':
						var href = plugin.settings.href;
						if (href) {
					    if (href.match(/^#/)) {
					      getHTML(href.substring(href.indexOf('#')));
						  } else if (href.match(/.(jpg|jpeg|png|gif|bmp)$/i)) {
							  launchShowtime($('<img />', {src: href}));
					    } else if (href.match(/.pdf$/i)) {
					      getPDF({url: href, width: 900, height: 600});
							} else {
								getExternalData(plugin.settings.ctxPath + href, plugin.settings.ajaxOpts);
					    }
						} else if (content) {
							launchShowtime(content);
						}
						break;
					case 'helpText':
						plugin.settings.title = 'Help Text';
						var field = opts.$elem.attr(plugin.settings.helpAttribute).toUpperCase();
						getHelpText(field);
						break;
				}
			} catch (e) {
				plugin.open = false;
				$.error('Error Opening Showtime Plugin<br />' + e.message);
			}
		},
		buttonClick: function(e, elem, buttonName, callback) {
			if (callback) {
				var inputArray = plugin.$showtime.find('form').serializeArray();
				callback.apply(this, [elem, buttonName, inputArray]);
			}
			switch (buttonName) {
				case 'ok':
					plugin.$showtime.triggerHandler('showtime.close');
					break;
				case 'cancel': case 'close':
					plugin.$showtime.triggerHandler('showtime.close');
					break;
			}
		},
		close: function(e) {
			try {
				if (plugin.open) {
		  		hideDialog(function() {
		  			showAjaxLoading = true;
	  				plugin.$loading.remove();
	  				$('#showtimeButtons').empty();
	  				plugin.$content.css({width: 'auto', height: 'auto'}).empty();
	  				plugin.$showtime.css({width: 'auto', height: 'auto', top: '', left: ''});
	  				plugin.$container.css({width: plugin.settings.minWidth + 'px', height: plugin.settings.minHeight + 'px'});
	      		if (plugin.settings.title) {
	  					$('#showtimeHeader').remove();
	  				}
//	      		if (plugin.settings.draggable && plugin.settings.draggable.initialized) {
//	      			plugin.$showtime.draggable('destroy');
//						}
	      		if (plugin.settings.fixed && !Modernizr.positionfixed) {
							plugin.$showtime.removeClass('fixed-pos-hack');
						}
	  				plugin.$showtime.trigger('showtime.complete');
		  		});
	  			hideOverlay();
				}
			} catch (e) {
				$.error('Error Closing Showtime Plugin<br />' + e.message);
			}
  	},
  	complete: function(e) {
  		try {
    		plugin.open = false;
			  var func = plugin.settings.fixed && !plugin.bypassFixed ? $.noop : scrollBackToPosition;
			  $.when(func()).then(function() {
				  plugin.settings.callback.call();
				  removeDocumentEvents();
			  });

    		if (plugin.cachedSettings) {
    			plugin.settings = plugin.cachedSettings;
    			delete plugin.cachedSettings;
    		}
			} catch (e) {
			  $.error('Error in complete event<br />' + e.message);
			}
		},
		escape: function(e) {
      if (e.keyCode === 27) {
      	this.close(e);
      }
    }
	};

	function loadFont(options) {
		window.WebFontConfig = {
			google: { families: options.families }
		};
		(function() {
			var wf = document.createElement('script');
			wf.src = ('https:' == document.location.protocol ? 'https' : 'http') + options.url;
			wf.type = 'text/javascript';
			wf.async = 'true';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(wf, s);
		})();
	}

	function buildHtml() {
		return $('<div />', {id: 'showtime'}).build(function(buildr) {
			buildr.div({id: 'showtimeContainer'}, function() {
				buildr.div({id: 'showtimeContent'});
				buildr.div({id: 'showtimeFooter'}, function() {
					buildr.div({id: 'showtimeButtons'});
				})
			});
		});
	}

	function scrollToTop() {
		return $.Deferred(function(dfd) {
			var $elem = $('body, html');

			plugin.currentScrollTop = $body.scrollTop();

			$elem.animate({scrollTop: 0}, {
				duration: plugin.settings.speed,
				complete: dfd.resolve
			});
		}).promise();
	}

	function scrollBackToPosition() {
		return $.Deferred(function(dfd) {
			var $elem = $('body, html');
			$elem.animate({scrollTop: plugin.currentScrollTop}, {
				duration: plugin.settings.speed,
				complete: dfd.resolve
			});
		}).promise();
	}

	function addFixedSupport() {
		if (!Modernizr.positionfixed) {
			plugin.$showtime.addClass('fixed-pos-hack');
		} else {
			plugin.$showtime.css('position', 'fixed');
		}
	}

	function removeFixedSupport() {
		if (!Modernizr.positionfixed) {
			plugin.$showtime.removeClass('fixed-pos-hack');
			plugin.$showtime.get('0').style.removeExpression('top');
		}
		plugin.$showtime.css({position: 'absolute'});
	}

  function showOverlay(callback) {
    if (!plugin.settings.showOverlay) {
    	if (callback) { callback.call(); }
    	return;
    }

    var $overlay,
      cssAttributes;

    if (Modernizr.positionfixed) {
    	cssAttributes = {opacity: plugin.settings.opacity};
    } else {
    	cssAttributes = {
    		position: 'absolute',
    		opacity: plugin.settings.opacity,
    		height: $.documentHeight() + 'px',
    		width: $.clientWidth() + 'px'
    	};
    }

    if (!$('#overlay').exists()) {
    	if (Modernizr.compliantzindex) {
		    $overlay = $('<div />', {
			    id: 'overlay'
		    });
			} else {
		    $overlay = $('<iframe />', {
			    id: 'overlay',
			    src: plugin.settings.ctxPath + '../../resources/empty.html'
		    });
			}
      $body.append($overlay);
    }

    $overlay.hide().css(cssAttributes).fadeIn('fast', callback);

	  var closeFunction = plugin.settings.modalOverlay ? $.noop : $.showtime.close;
	  if (Modernizr.compliantzindex) {
		  $overlay.on('click', closeFunction);
	  } else {
		  $overlay.contents().on('click', closeFunction);
	  }
  }

  function hideOverlay(callback) {
  	if (!plugin.settings.showOverlay) {
  		if (callback) { callback.call(); }
  		return;
  	}

  	var $overlay = $('#overlay');
  	$overlay.fadeOut(plugin.settings.speed, function() {
  		$overlay.remove();
      if (callback) { callback.call(); }
    });
  }

  function showLoading() {
	  return $.Deferred(function(dfd) {
		  addFixedSupport();
		  if (plugin.$loading === undefined) {
			  plugin.$loading = $('<div />', {id: 'showtimeLoading'}).build(function(buildr) {
				  buildr.img({
					  alt: 'Loading Image',
					  src: plugin.imageCache.loadingImage.src,
					  height: plugin.imageCache.loadingImage.height,
					  width: plugin.imageCache.loadingImage.width
				  });
				  buildr.span(plugin.settings.loadingMessage);
			  });
		  }

		  plugin.$container.children().hide().end().append(plugin.$loading.show());
		  plugin.$showtime.css({marginLeft: ((plugin.$showtime.outerWidth() / 2) * -1)}).fadeIn(plugin.settings.speed, dfd.resolve);
	  }).promise();
  }

	function deferImageLoad($image) {
		return $.Deferred(function(dfd) {
			if ($.is$($image) && $image.tagName() == 'img') {
				$image.load(function() {
					dfd.resolve();
				});
				if ($image.prop('complete')) {
					$image.trigger('load');
				}
			} else {
				dfd.resolve();
			}
		}).promise();
	}

  function launchShowtime(content) {
	  if (plugin.mode == 'loading') {
		  if (plugin.settings.showOverlay) { showOverlay(); }
		  showLoading();
	  } else {
		  if (content === undefined) { return; }

		  if (plugin.settings.showOverlay) { showOverlay(); }
		  $.when(showLoading(), deferImageLoad(content)).then(function() {
			  var contentWidth,
				  containerWidth,
				  containerHeight,
				  leftMargin;

			  if (!plugin.$content.children().exists()) {
				  plugin.$content.html(content);
			  }
			  plugin.$content.appendTo($body);
			  contentWidth = plugin.$content.outerWidth(true);

			  if (plugin.settings.draggable) {
				  plugin.$showtime.draggable(plugin.settings.draggable);
				  plugin.settings.draggable.initialized = true;
			  }

			  if (contentWidth > plugin.settings.maxWidth) {
				  plugin.$content.css('width', plugin.settings.maxWidth);
			  } else if (contentWidth < plugin.settings.minWidth) {
				  plugin.$content.css('width', plugin.settings.minWidth);
			  }

			  containerWidth = plugin.$content.outerWidth(true);
			  containerHeight = plugin.$content.outerHeight(true) + plugin.$footer.outerHeight(true);

			  plugin.bypassFixed = plugin.settings.fixed && containerHeight > $.clientHeight();

			  $.when($.Deferred(function(dfd) {
				  if (plugin.settings.fixed && !plugin.bypassFixed) {
					  addFixedSupport();
					  dfd.resolve();
				  } else {
					  $.when(scrollToTop()).then(dfd.resolve).then(removeFixedSupport);
				  }
			  }).promise()).then(function() {
				  plugin.$content.prependTo(plugin.$container);
				  if (plugin.settings.title) {
					  var $header = $('<div />', {id: 'showtimeHeader'}).build(function(buildr) {
						  buildr.h1(plugin.settings.title, {id: 'showtimeTitle', 'class': 'content-seperator'});
						  buildr.button({id: 'closeBtn'}).on('click', function() {
							  plugin.$showtime.trigger('showtime.close');
						  });
					  }).prependTo(plugin.$container);

					  containerHeight += $header.outerHeight(true);
				  }

				  if (plugin.settings.resizable) {
					  plugin.settings.resizable = $.extend(true, {minHeight: containerHeight, minWidth: containerWidth}, plugin.settings.resizable);
					  plugin.$container.resizable(plugin.settings.resizable);
				  }

				  leftMargin = (containerWidth + parseInt(plugin.$showtime.css('borderLeftWidth'), 10) + parseInt(plugin.$showtime.css('borderRightWidth'), 10)) / 2 * -1;

				  plugin.$loading.hide().remove();
				  plugin.$showtime.animate({marginLeft: leftMargin + 'px'}, plugin.settings.speed).fadeIn(plugin.settings.speed);
				  plugin.$container.animate({width: containerWidth + 'px', height: containerHeight + 'px'}, {
					  duration: plugin.settings.speed,
					  complete: function() {
						  plugin.$container.children().fadeIn(plugin.settings.speed);
						  if (plugin.settings.autoSizeAfterOpen) {
							  plugin.$container.css('height', 'auto');
						  }
						  if (plugin.settings.callback) { plugin.settings.callback.call(); }
					  }
				  });
			  });
		  });
	  }
  }

  function hideDialog(callback) {
    plugin.$showtime.fadeOut(plugin.settings.speed, function() {
      if (callback) { callback.call(); }
    });
  }

  function addDocumentEvents() {
		// Document Events
		$doc.bind('keydown.showtime', plugin.eventMappings['keydown.showtime']);
  }

  function removeDocumentEvents() {
  	$doc.unbind('.showtime');
  }

  function addShowtimeEvents() {
  	plugin.$showtime.on({
  		'showtime.open': plugin.eventMappings['showtime.open'],
  		'showtime.button-click': plugin.eventMappings['showtime.button-click'],
  		'showtime.close': plugin.eventMappings['showtime.close'],
  		'showtime.complete': plugin.eventMappings['showtime.complete']
  	});
  }

  function getHTML(id) {
  	var $elem = $(id);
    if ($elem.exists()) {
    	launchShowtime($elem.html());
    }
  }

  function getExternalData(href, opts) {
  	showAjaxLoading = false;
	  return $.ajax(href, opts).done(function(data, textStatus, jqXHR) {
		  launchShowtime(data);
	  });
  }

  function getPDF(pdfOpts) {
//  	try {
//  		if ($.pdfObject.pluginFound()) {
//  			launchShowtime(plugin.$content.pdfObject({
//	  			url: pdfOpts.url,
//	  			width: pdfOpts.width || '100%',
//	  			height: pdfOpts.height || '100%',
//	  			openParams: pdfOpts.openParams
//	  		}).embedPDF());
//  		} else {
//  			var content = $('<p />').html('No PDF plugin detected. Please visit <a href="http://www.adobe.com/" target="_new">www.adobe.com.</a>')
//  			.append($('<p />').html('Click <a href="' + pdfOpts.url + '" target="_download">here</a> to download your document.'));
//  			launchShowtime(content);
//  		}
//		} catch (e) {
//			var content = $('<p />').html('No PDF plugin detected. Please visit <a href="http://www.adobe.com/" target="_new">www.adobe.com.</a>')
//  			.append($('<p />').html('Click <a href="' + pdfOpts.url + '" target="_download">here</a> to download your document.'));
//			launchShowtime(content);
//		}
  }

	function getHelpText(field) {
	  var url = plugin.settings.ctxPath + '/AjaxServlet';
	  showAjaxLoading = false;
	  $.ajax({
	    type: 'GET',
	    dataType: 'text',
	    url: url,
	    data: {command: 'getHelp', product: '*ALL', fieldName: field},
	    success: function(resp) {
	      try {
	      	if (resp === '') {
						resp = 'No help text associated with this item';
					}
					launchShowtime(resp);
	      } catch (e) {
		      $.error('Error on page<br />' + e.message);
	      }
	    },
	    error: function(XMLHttpRequest, textStatus) {
	      try {
					launchShowtime(XMLHttpRequest.responseText);
	      } catch (e) {
		      $.error('Error on page<br />' + e.message);
	      }
	    }
	  });
	}
})(jQuery);

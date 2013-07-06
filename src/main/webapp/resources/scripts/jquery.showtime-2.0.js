define('jquery.showtime-2.0', ['jquery', 'jquery-ui', 'jquery.extensions', 'jquery.buildr'], function($, undefined) {
	var elements = {
		$elem: undefined,
		$showtime: undefined,
		$container: undefined,
		$content: undefined,
		$header: undefined,
		$footer: undefined,
		$loading: undefined,
		$overlay: undefined,
		$closeBtnImage: undefined
	};
	var defaults = {
		modal: true,
		opacity: 0.75,
		minWidth: 350,
		minHeight: 32,
		maxWidth: 550,
		fixed: true,
		autoResize: false,
		imagesPath: '../../resources/images/',
		imageTypes: ['jpg', 'jpeg', 'png', 'gif', 'bmp'],
		speed: 200,
		title: '',
		fontFamilies: ['Ubuntu:400,500,700,400italic,500italic,700italic:latin'],
		content: undefined,
		draggable: {
			cursor: 'move',
			handle: '#showtimeHeader, #showtimeFooter',
			opacity: 0.40,
			cancel: '#showtimeContent, .Showtime-button, #closeBtn',
			initialized: false
		},
		resizable: {alsoResize: '#showtimeContent', autoHide: true, handles: 'e, se, s'},
		buttons: {close: null},
		loadingMessage: 'Loading, please wait...',
		loadingImage: 'roller.gif',
		callback: $.noop,
		ajaxOpts: {
			type: 'GET'
		}
	};

	var Showtime = function(options, element) {
		this.$obj = $(this);
		this.open = false;
		this.name = 'Showtime';
		this.options = options;
		this.element = element;
		this.isDraggable = false;
		this.isResizable = false;
		this._init();
	};

	Showtime.prototype = {
		_init: function() {
			this.options = $.extend(true, {}, defaults, this.options);

			elements.$elem = $(this.element);
			this._addEvents();

			if (elements.$showtime === undefined) {
				this._buildHtml();
				$.preloadImages([
					this.options.imagesPath + this.options.loadingImage,
					this.options.imagesPath + 'close-button.png',
					this.options.imagesPath + 'b.png'
				]);
			}

			this.options.imageFilter = this._generateImageFilter(this.options.imageTypes);

			if ($.isArray(this.options.fontFamilies)) {
				this._loadFont(this.options.fontFamilies);
			}
		},
		_generateImageFilter: function(imageTypes) {
			if (!$.isArray(imageTypes)) { return new RegExp(); }

			var regExp = '.(';
			for (var i = 0, length = imageTypes.length; i < length; i++) {
				regExp = regExp + imageTypes[i] + '|';
			}
			return new RegExp(regExp.substring(0, regExp.length - 1) + ')$', 'i');
		},
		option: function(key, value) {
			if ($.isPlainObject(key)) {
				this.options = $.extend(true, this.options, key);
			} else if (key && typeof value === 'undefined') {
				return this.options[key];
			} else {
				this.options[key] = value;
			}

			/* required: option must return the current instance. When re-initializing an instance on elements,
			 * option is called first and is then chained to the _init method. */
			return this;
		},
		loading: function() {
			this.$obj.triggerHandler('showtime.open');
		},
		dialog: function(content, opts) {
			if (content) {
				this.$obj.triggerHandler('showtime.open', [content, opts]);
			}
		},
		confirm: function() {

		},
		lightbox: function() {

		},
		remove: function() {
			var self = this;
			var $destroyQueue = $({});
			if (this.open) {
				$destroyQueue.queue('showtime.destroy', function(next) {
					$.when(self._close()).then(next);
				});
			}

			$destroyQueue.queue('showtime.destroy', function(next) {
				self._removeEvents();
				elements.$showtime.remove();
			});

			$destroyQueue.dequeue('showtime.destroy');
		},
		_open: function(content, opts) {
			try {
				if (this.open) { return; }

				opts = $.isPlainObject(opts) ? $.extend(true, {}, this.options, opts) : this.options;
				content = opts.content || content;

				var self = this;
				if (opts.modal) {
					this.$obj.queue('showtime.open', function(next) {
						$.when(self._showOverlay()).then(next);
					});
				}

				this.$obj.queue('showtime.open', function(next) {
					$.when(self._showLoading()).then(next);
				});

				if (content) {
					this.$obj.queue('showtime.open', function(next) {
						$.when(self._getContent(content, opts)).then(function(cont) {
							content = cont;
							next();
						});
					});

					this.$obj.queue('showtime.open', function(next) {
						var contentWidth,	containerWidth,	containerHeight, leftMargin;

						this._addButtons();

						elements.$content.if$(!elements.$content.children().exists()).html(content).end().appendTo($body);
						contentWidth = elements.$content.outerWidth(true);

						if (opts.draggable && $.fn.draggable) {
							elements.$showtime.draggable(opts.draggable);
							self.isDraggable = true;
						}

						if (contentWidth > opts.maxWidth) {
							elements.$content.css('width', opts.maxWidth);
						} else if (contentWidth < opts.minWidth) {
							elements.$content.css('width', opts.minWidth);
						}

						containerWidth = elements.$content.outerWidth(true);
						containerHeight = elements.$content.outerHeight(true) + elements.$footer.outerHeight(true);

						opts.overrideFixed = opts.fixed && containerHeight > $.clientHeight();

						if (!opts.fixed || opts.overrideFixed) {
							self.$obj.queue('showtime.openChild', function(nextChild) {
								$.when(self._scrollToTop()).then(nextChild);
							});
						}

						self.$obj.queue('showtime.openChild', function(nextChild) {
							elements.$content.prependTo(elements.$container);
							if (opts.title) {
								elements.$header = $('<div />', {id: 'showtimeHeader'}).build(function (buildr) {
									buildr.h1(opts.title, {id: 'showtimeTitle', 'class': 'content-separator'});
									buildr.div({id: 'buttonContainer'}, function() {
										buildr.a({id: 'rollUpBtn', 'class': 'showtime-button'}, function() {
											buildr.span({'class': 'icon-chevron-up'});
										});
										buildr.a({id: 'closeBtn', 'class': 'showtime-button'}, function() {
											buildr.span({'class': 'icon-remove'});
										}).on('click', function () {
											self.$obj.triggerHandler('showtime.close');
										});
									});
								}).prependTo(elements.$container);

								containerHeight += elements.$header.outerHeight(true);
							}

							if (opts.resizable && $.fn.resizable) {
								opts.resizable = $.extend(true, opts.resizable, {minHeight: containerHeight, minWidth: containerWidth});
								elements.$container.resizable(opts.resizable);
								self.isResizable = true;
							}

							leftMargin = (containerWidth + parseInt(elements.$showtime.css('borderLeftWidth'), 10) + parseInt(elements.$showtime.css('borderRightWidth'), 10)) / 2 * -1;

							elements.$loading.hide().remove();
							elements.$showtime.animate({marginLeft: leftMargin + 'px'}, opts.speed).fadeIn(opts.speed);
							elements.$container.animate({width: containerWidth + 'px', height: containerHeight + 'px'}, opts.speed)
								.promise().then(function () {
									elements.$container.children().fadeIn(opts.speed);
									if (opts.autoResize) {
										elements.$container.css('height', 'auto');
									}
								}).then(opts.callback).then(nextChild).then(next);
						}).dequeue('showtime.openChild');
					});
				}

				this.$obj.queue('showtime.open', function() {
					self.open = true;
				}).dequeue('showtime.open');
			} catch (e) {
				this.$obj.clearQueue('showtime');
				$.error(e.name + '\n' + e.message);
			}
		},
		_showOverlay: function() {
			var self = this;
			return $.Deferred(function(dfd) {
				if (!elements.$overlay) {
					if (Modernizr.compliantzindex) {
						elements.$overlay = $('<div />', {id: 'overlay'});
					} else {
						elements.$overlay = $('<iframe />', {
							id: 'overlay',
							src: 'javascript: false;',
							tabindex: '-1',
							frameborder: '0',
							style: 'top: expression(((parseInt(this.parentNode.currentStyle.borderTopWidth) || 0) * -1) + \'px\');' +
								'left: expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth) || 0) * -1) + \'px\');' +
								'width: expression(this.parentNode.offsetWidth + \'px\');' +
								'height: expression(this.parentNode.offsetHeight + \'px\')'
						});
					}

					$body.append(elements.$overlay);
				} else {
					elements.$overlay.appendTo($body);
				}

				(Modernizr.compliantzindex ? elements.$overlay : elements.$overlay.contents()).on('click', function() {
					self.$obj.triggerHandler('showtime.close');
				});

				if (Modernizr.cssfilters) {
					$body.children().not('#showtime, #overlay, #showtimeLoading').removeClass('no-blur').addClass('blur');
				}
				elements.$overlay.fadeIn(100, dfd.resolve);
			}).promise();
		},
		_showLoading: function() {
			var self = this;
			if (!elements.$loading) {
				elements.$loading = $('<div />', {id: 'showtimeLoading'}).build(function(buildr) {
					buildr.img({
						alt: 'Loading Image',
						src: self.options.imagesPath + self.options.loadingImage
					});
					buildr.span(self.options.loadingMessage);
				});
			}

			elements.$container.children().hide().end().append(elements.$loading.show());
			return elements.$showtime.css({marginLeft: ((elements.$showtime.outerWidth() / 2) * -1)}).fadeIn(this.options.speed).promise();
		},
		_getContent: function(content, opts) {
			var self = this;
			return $.Deferred(function(dfd) {
				var type = $.type(content);
				if (type == 'string') {
					if (content.indexOf(':') >= 0) {
						var contentComponents = content.split(':');
						switch (contentComponents[0]) {
							case 'text':
								dfd.resolve(contentComponents[1]);
								break;
							case 'image':
								if (!content.match(opts.imageFilter)) {
									$.error('Image type of ' + contentComponents[1] + ' is not allowed. Please check the imageTypes option.');
								}
								$.when($.preloadImages(opts.imagesPath + [contentComponents[1]])).then(function() {
									dfd.resolve($('<img />', {src: opts.imagesPath + contentComponents[1]}));
								});
								break;
							case 'href':
								$.ajax(contentComponents[1], opts.ajaxOpts).done(function(data) {
									dfd.resolve(data);
								});
								break;
							default: break;
						}
					}
				}
			}).promise();
		},
		_scrollToTop: function() {
			this.options.currentScrollTop = $body.scrollTop();
			return $('body, html').animate({scrollTop: 0}, this.options.speed).promise();
		},
		_close: function() {
			try {
				if (!this.open) { return; }

				var self = this;
				$.when(elements.$showtime.fadeOut(this.options.speed, function() {
					self._resetStyles();
				}).promise()).then(function() {
					self._hideOverlay();
				}).then(function() {
					self.open = false;
				});
			} catch (e) {
				$.error('Error Closing Showtime Plugin\n' + e.message);
			}
		},
		_hideOverlay: function() {
			var options = this.options;
			if (!options.modal) { return $.Deferred().promise(); }

			if (Modernizr.cssfilters) {
				$body.children().not('#showtime, #overlay').toggleClass('blur no-blur');
			}

			return elements.$overlay.fadeOut(100, function() {
				elements.$overlay.remove();
			}).promise();
		},
		_resetStyles: function() {
			var self = this;
			return $.Deferred(function(dfd) {
				elements.$loading.remove();
				$('#showtimeButtons').empty();
				elements.$content.css({width: 'auto', height: 'auto'}).empty();
				elements.$showtime.css({width: 'auto', height: 'auto', top: '', left: ''});
				elements.$container.css({width: self.options.minWidth + 'px', height: self.options.minHeight + 'px'});

				if (elements.$header) {
					elements.$header.remove();
				}

        if (self.isDraggable) {
          elements.$showtime.draggable('destroy');
				}

				if (self.isResizable) {
					elements.$container.resizable('destroy');
				}
				dfd.resolve();
			}).promise();
		},
		_addEvents: function() {
			var self = this;
			$doc.off('keydown.showtime').on('keydown.showtime', function(event) {
				if (event.keyCode === 27) {
					self._close();
				}
			});

			this.$obj.off('showtime').on({
				'showtime.open': function(event, content, opts) {
					self._open.apply(self, [content, opts]);
				},
				'showtime.button-click': function(event, key, value) {
					self._buttonClick.apply(self, [key, value]);
				},
				'showtime.close': function() {
					self._close.apply(self);
				}
			});
		},
		_removeEvents: function() {
			$doc.off('keydown.showtime');
			this.$obj.off('showtime');
		},
		_buildHtml: function() {
			elements.$showtime = $('<div />', {id: 'showtime'}).build(function(buildr) {
				buildr.div({id: 'showtimeContainer'}, function() {
					buildr.div({id: 'showtimeContent'});
					buildr.div({id: 'showtimeFooter'}, function() {
						buildr.div({id: 'showtimeButtons'});
					})
				});
			}).appendTo($body);

			elements.$container = $('#showtimeContainer');
			elements.$content = $('#showtimeContent');
			elements.$footer = $('#showtimeFooter');
			elements.$closeBtnImage = $('#closeBtnImage');
		},
		_addButtons: function() {
			var self = this;
			$.each(this.options.buttons, function(key, value) {
				$('#showtimeButtons').build(function(buildr) {
					buildr.a(key.charAt(0).toUpperCase() + key.slice(1), {id: key + 'ShowtimeButton'}).on('click.showtime', function() {
						self.$obj.trigger('showtime.button-click', [key, value]);
					});
				});
			});
		},
		_loadFont: function(fontFamilies) {
			window.WebFontConfig = {
				google: {families: fontFamilies}
			};

			var wf = document.createElement('script');
			wf.src = ('https:' == document.location.protocol ? 'https' : 'http') + '://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js';
			wf.type = 'text/javascript';
			wf.async = 'true';

			var script = document.getElementsByTagName('script')[0];
			script.parentNode.insertBefore(wf, script);
		},
		_buttonClick: function(buttonName, callback) {
			if (callback && $.isFunction(callback)) {
				callback.call();
				return;
			}

			switch (buttonName) {
				case 'ok':
					this.$obj.triggerHandler('showtime.close');
					break;
				case 'cancel': case 'close':
					this.$obj.triggerHandler('showtime.close');
					break;
				default: break;
			}
		}
	};

	// Register plugin
	$.widget.bridge('showtime', Showtime);
});
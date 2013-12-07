/**
 * Showtime jQuery Plugin
 * Version 2.0
 */
define(['jquery', 'jquery-ui', 'modernizr', 'jquery.extensions', 'jquery.buildr', 'utilities'], function($, $ui, Modernizr, $extensions, $builder, utilities, undefined) {
	var elements = {
		$elem: undefined,
		$showtime: undefined,
		$container: undefined,
		$content: undefined,
		$header: undefined,
		$footer: undefined,
		$footerButtons: undefined,
		$loading: undefined,
		$overlay: undefined,
		$closeBtnImage: undefined
	};

	var defaults = {
		modal: true,
		opacity: 0.75,
		minWidth: 350,
		minHeight: 32,
		maxWidth: 450,
		fixed: true,
		imagesPath: '../../resources/images/',
		imageTypes: ['jpg', 'jpeg', 'png', 'gif', 'bmp'],
		speed: 200,
		title: '',
		fontFamilies: ['Ubuntu:400,700,400italic,700italic:latin'],
		content: undefined,
		draggable: {
			cursor: 'move',
			handle: '#showtimeHeader, #showtimeFooter',
			opacity: 0.50,
			cancel: '#showtimeContent, .Showtime-button, #closeBtn',
			initialized: false
		},
		resizable: {alsoResize: '#showtimeContent', autoHide: true},
		buttons: undefined,
		loadingMessage: 'Loading, please wait...',
		loadingImage: 'roller-light.gif',
		callback: $.noop,
		ajaxOpts: {
			type: 'GET'
		}
	};

	var Mode = {
		LOADING: {
			name: 'loading',
			options: {modal: true, buttons: undefined}
		},
		INFO: {
			name: 'info',
			options: {modal: false, title: 'Info', buttons: undefined}
		},
		DIALOG: {
			name: 'dialog',
			options: {modal: false, buttons: {ok: null}}
		},
		CONFIRM: {
			name: 'confirm',
			options: {modal: true, title: 'Confirm Action', buttons: {cancel: null, ok: null}}
		},
		CONFIRM_OK: {
			name: 'confirmOk',
			options: {modal: true, title: 'Confirm Action', buttons: {ok: null, cancel: null}}
		},
		LIGHTBOX: {
			name: 'lightbox',
			options: {
				modal: true,
				buttons: undefined
			}
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
		this.mode = undefined;
		this._init();
	};

	var buttonIndex = 1;

	Showtime.prototype = {
		_init: function() {
			this.options = $.extend(true, {}, defaults, this.options);

			elements.$elem = $(this.element);
			this._addEvents();

			if (elements.$showtime === undefined) {
				this._buildHtml();
				$.preloadImages([
					this.options.imagesPath + this.options.loadingImage
				]);
			}

			this.options.imageFilter = this._generateImageFilter(this.options.imageTypes);

			var fontFamilies = this.options.fontFamilies;
			if ($.isArray(fontFamilies)) {
				var fonts = [];
				fontFamilies.forEach(function(value) {
					fonts.push('font!google,families:[' + value + ']');
				});
				require(fonts);
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
			this._launch(null, null, Mode.LOADING);
		},
		info: function(content, opts) {
			this._launch(content, opts, Mode.INFO);
		},
		dialog: function(content, opts) {
			this._launch(content, opts, Mode.DIALOG);
		},
		confirm: function(content, opts) {
			this._launch(content, opts, Mode.CONFIRM);
		},
		lightbox: function(content, opts) {
			this._launch(content, opts, Mode.LIGHTBOX);
		},
		_launch: function(content, opts, mode) {
			if (content || mode == Mode.LOADING) {
				this.mode = mode;
				this.$obj.triggerHandler('showtime.open', [content, $.extend(true, mode.options, opts || {})]);
			}
		},
		remove: function() {
			var self = this;
			var $destroyQueue = $({});
			if (this.open) {
				$destroyQueue.queue('showtime.destroy', function(next) {
					$.when(self._close()).then(next);
				});
			}

			$destroyQueue.queue('showtime.destroy', function() {
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

				if (opts.modal) {
					this.$obj.queue('showtime.open', function(next) {
						$.when(self._showOverlay()).then(next);
					});
				}

				var self = this;
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
						containerHeight = elements.$content.outerHeight(true);

						opts.overrideFixed = opts.fixed && containerHeight > $.clientHeight();

						if (!opts.fixed || opts.overrideFixed) {
							self.$obj.queue('showtime.openChild', function(nextChild) {
								$.when(self._scrollToTop()).then(nextChild);
							});
						}

						self.$obj.queue('showtime.openChild', function(nextChild) {
							elements.$content.prependTo(elements.$container);
							elements.$header = $('<div />', {id: 'showtimeHeader'}).build(function (buildr) {
								if (opts.title) {
									buildr.h1(opts.title, {id: 'showtimeTitle', 'class': 'content-separator'});
								}
							}).prependTo(elements.$container);

							containerHeight += elements.$header.outerHeight(true);

							this._addButtons(opts.buttons);
							if (elements.$footerButtons && elements.$footerButtons.exists()) {
								// TODO: Add function to add to container height.
								containerHeight += elements.$footer.outerHeight(true);
							} else {
								// TODO: Figure out a better way to do this! Added to lessen the contents bottom border.
								elements.$content.css('borderBottomColor', 'rgba(0, 0, 0, 0.5)');
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
										elements.$container.css('height', 'auto');
								}).then(opts.callback).then(nextChild).then(next);
						}).dequeue('showtime.openChild');
					});
				}

				this.$obj.queue('showtime.open', function() {
					self.open = true;
					if (elements.$footerButtons && elements.$footerButtons.exists()) {
						var $buttons = elements.$footerButtons.children('a.showtime-button');
						if (!$buttons.isEmpty()) {
							$buttons.first().on('keydown', self._shiftTabKeydown).focus();
						}
					}
				}).dequeue('showtime.open');
			} catch (e) {
				this.$obj.clearQueue('showtime');
				$.error(e.name + '\n' + e.message);
			}
		},
		_showOverlay: function() {
			var self = this;
			elements.$overlay.appendTo($body);

			(Modernizr.compliantzindex ? elements.$overlay : elements.$overlay.contents()).on('click', function() {
				self.$obj.triggerHandler('showtime.close');
			});

			return elements.$overlay.fadeIn(100).promise();
		},
		_showLoading: function() {
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

			return elements.$overlay.fadeOut(100, function() {
				elements.$overlay.remove();
			}).promise();
		},
		_resetStyles: function() {
			var self = this;
			return $.Deferred(function(dfd) {
				elements.$loading.remove();
				elements.$content.css({width: 'auto', height: 'auto'}).empty();
				elements.$showtime.css({width: 'auto', height: 'auto', top: '', left: ''});
				elements.$container.css({width: self.options.minWidth + 'px', height: self.options.minHeight + 'px'});

				if (elements.$header) {
					elements.$header.remove();
				}

				if (elements.$footer) {
					elements.$footer.remove();
				}

        if (self.isDraggable) {
          elements.$showtime.draggable('destroy');
	        self.isDraggable = false;
				}

				if (self.isResizable) {
					elements.$container.resizable('destroy');
					self.isResizable = false;
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
			var self = this;

			if (Modernizr.compliantzindex) {
				elements.$overlay = $('<div />', {id: 'overlay'});
			} else {
				elements.$overlay = $('<iframe />', {
					id: 'overlay',
					src: 'javascript:"";',
					tabindex: '-1',
					frameborder: '0',
					style: 'top: expression(((parseInt(this.parentNode.currentStyle.borderTopWidth) || 0) * -1) + \'px\');' +
						'left: expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth) || 0) * -1) + \'px\');' +
						'width: expression(this.parentNode.offsetWidth + \'px\');' +
						'height: expression(this.parentNode.offsetHeight + \'px\')'
				});
			}

			elements.$loading = $('<div />', {id: 'showtimeLoading'}).build(function(buildr) {
				buildr.img({
					alt: 'Loading Image',
					src: self.options.imagesPath + self.options.loadingImage
				});
				buildr.span(self.options.loadingMessage);
			});

			elements.$showtime = $('<div />', {id: 'showtime'}).build(function(buildr) {
				buildr.div({id: 'showtimeContainer'}, function() {
					buildr.div({id: 'showtimeContent'});
				});
			}).appendTo($body.append(elements.$overlay));

			elements.$container = $('#showtimeContainer');
			elements.$content = $('#showtimeContent');
			elements.$closeBtnImage = $('#closeBtnImage');
		},
		_addButtons: function(buttons) {
			var self = this;
			var buttonIndex = 1;

			if ($.isPlainObject(buttons)) {
				elements.$footer = $('<div />', {id: 'showtimeFooter'}).build(function(buildr) {
					buildr.div({id: 'footerButtons'});
				}).appendTo(elements.$container);

				elements.$footerButtons = $('#footerButtons');

				$.each(buttons, function(key, value) {
					elements.$footerButtons.build(function(buildr) {
						buildr.a(key.charAt(0).toUpperCase() + key.slice(1), {id: key + 'ShowtimeButton', 'class': 'showtime-button', tabindex: buttonIndex++}).on('click.showtime', function() {
							self.$obj.trigger('showtime.button-click', [key, value]);
						}).on('keydown', function(event) {
							self._keydown(event, function() {
								self.$obj.trigger('showtime.button-click', [key, value]);
							});
						});
					});
				});
			}

			elements.$header.build(function(buildr) {
				buildr.div({id: 'titleButtons'}, function() {
					buildr.a({id: 'closeBtn', 'class': 'showtime-button', tabindex: buttonIndex++}, function() {
						buildr.span({'class': 'glyphicon glyphicon-remove'});
					}).on('click', function () {
						self.$obj.triggerHandler('showtime.close');
					}).on('keydown', function(event) {
						self._keydown(event, function() {
							self.$obj.triggerHandler('showtime.close');
						});
						self._tabKeydown(event);
					});
				});
			});
		},
		_buttonClick: function(buttonName, callback) {
			if ($.isFunction(callback)) {
				callback.call();
			} else {
				this.$obj.triggerHandler('showtime.close');
			}
		},
		_keydown: function(event, callback) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if(keycode == '13') {
				callback();
			}
		},
		_tabKeydown: function(event) {
			if (!event.shiftKey && (event.keyCode ? event.keyCode : event.which) == 9) {
				event.preventDefault();
			}
		},
		_shiftTabKeydown: function(event) {
			if (event.shiftKey && (event.keyCode ? event.keyCode : event.which) == 9) {
				event.preventDefault();
			}
		}
	};

	// Register plugin
	$.widget.bridge('showtime', Showtime);
});
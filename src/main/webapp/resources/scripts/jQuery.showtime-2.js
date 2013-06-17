(function($) {
	var $body,
	$doc = $(document),
	$showtime = $('<div />', {
    id: 'showtime'
  }),
  $container = $('<div />', {
    id: 'showtimeContainer'
  }),
  $content = $('<div />', {
  	id: 'showtimeContent'
  }),
  $overlay = $('<div />', {
  	id: 'overlay'
  }),
  $header = $('<div />', {
		id: 'showtimeHeader'
	}),
  $title = $('<h1 />', {
		id: 'showtimeTitle',
		'class': 'content-seperator'
	}),
	$closeButton = $('<span />', {
		id: 'closeBtn'
	}),
	$footer = $('<div />', {
    id: 'showtimeFooter'
  }),
	$buttons = $('<div />', {
    id: 'showtimeButtons'
  }),
  $indicator = $('<div />', {
  	id: 'showtimeIndicator'
  }),
  $iframe = $('<iframe />', {
		id: 'iframeOverlay',
		src: 'javascript: false;',
		tabindex: '-1',
		frameborder: '0',
		style: 'top: expression(((parseInt(this.parentNode.currentStyle.borderTopWidth) || 0) * -1) + \'px\');' +
					 'left: expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth) || 0) * -1) + \'px\');' +
					 'width: expression(this.parentNode.offsetWidth + \'px\');' + 
					 'height: expression(this.parentNode.offsetHeight + \'px\')'
	}),
  $win = $(window),
	initialized = false,
	imageCache = {},
	settings = {},
	quirksMode = document.compatMode == 'BackCompat',
	cssFixedSupport = !$.browser.msie || $.browser.msie && document.compatMode == 'CSS1Compat' && window.XMLHttpRequest,
	pluginErrorText = 'Showtime Error';
	
	$.fn.showtime = function(method, options) {
		if (this.length === 0) {
			return this;
		}

		var $this = this,
		mainQueue = $doc.queue('showtime.main', function(nextMain) {
			if (typeof method === 'object') {
				options = method;
				method = undefined;
			}
	    
	    if (method in methods) {
				$showtime.queue('showtime.method', function(next) {
					console.log('calling method: ' + method);
					settings = $.extend(true, {}, $.showtime.defaults, options || {});
			    if (!initialized || $.isEmptyObject(settings)) {	    	
			    	methods.init.apply($this, [function() {
			    		methods[method].apply($this, [next]);
			    	}]);
					} else {
						methods[method].apply($this, [next]);
					}
				});
				
				if (settings.callback) {
					$showtime.queue('showtime.method', function(next) {
						settings.callback.call();
						settings.callback = undefined;
						next();
					});
				}
				
				$showtime.queue('showtime.method', function() {
					nextMain();
				});

				$showtime.dequeue('showtime.method');
				return $this;
			} else {
				staticMethods.clearQueues();
				$.error('Method: ' +  method + ' does not exist on jQuery.showtime');
			}
		});
		
		if (mainQueue.length === 1) {
			$doc.dequeue('showtime.main');
		}
	};

	$.showtime = {
		defaults: {
			overlay: {
				modal: false,
				iframe: true,
				opacity: 0.8,
				onOpen: function() {
					
				},
				onClose: function() {
					
				}
			},
			dialog: {
				minWidth: 350,
				minHeight: 32,
				maxWidth: 550,
				fixed: true,
				title: '',
				content: '',
	    	indicatorMessage: 'Loading, please wait...',
	      indicatorImage: '../images/loading_throb.gif',
				autoResize: false,
				buttons: {
					ok: {text: 'OK', click: function(event) {
						staticMethods.close();
					}},
					cancel: {text: 'Cancel', click: function(event) {
						staticMethods.close();
					}}
				},
				draggable: {
					enabled: true,
					cursor: 'move',
					handle: 'h1, #showtimeFooter',
					opacity: 0.40,
					cancel: '#showtimeButtons, #showtimeContent',
					initialized: false
				},
				resizable: {
					enabled: true,
					autoHide: true,
					alsoResize: '#showtimeContainer, #showtimeContent',
					minWidth: 350,
					minHeight: 50,
					initialized: false
				},
				onOpen: function() {
					
				},
				onClose: function() {
					
				}
			},
			contextPath: '',
			speed: 200,
			cssPie: {
				enabled: false,
				path: ''
			},
			onError: undefined,
			callback: undefined
		},
		setDefaults: function(key, value) {
			staticMethods.setDefaults.apply(this, arguments);
		},
		getDefaults: function(key) {
			return staticMethods.getDefaults.apply(this, arguments);
		},
		isOpen: function() {
			return components.overlay.status === 'open' && components.dialog.status === 'open' && $showtime.is(':visible');
		},
		close: function() {
			if ($doc.queue('showtime.main').length > 0) {
				$doc.queue('showtime.main', function(next) {
					staticMethods.close(next);
				});
			} else {
				staticMethods.close();
			}
		}
	};
	
	var methods = {
		init: function(callback) {
			$body = $('body');
	    if (initialized) {
	    	staticMethods.close();
	    	components.overlay.destroy();
	    	components.dialog.destroy();
			}
	    
	    staticMethods.addRuntimeEvents();
			
			if ($showtime.closest('body').length === 0) {
				$showtime.html($container.append($content)).appendTo($body);
				
		    if ($.browser.msie && settings.cssPie.enabled) {
					$showtime.css({behavior: 'url(' + settings.contextPath + settings.cssPie.path});
				}
			}
			
	    // cache images
			staticMethods.cacheImage('indicatorImage', settings.contextPath + settings.dialog.indicatorImage, false);
	    
	    cssFixedSupport ? $showtime.css('position', 'fixed') : $showtime.addClass('fixed-pos-hack');
			
			initialized = true;
			if (callback) { callback.call(); }
		},
		showOverlay: function(callback) {
			if ($.inArray(components.overlay.status, ['closed', 'destroyed']) > -1) {
				components.overlay.create().show.apply(this, arguments);
			}
		},
		hideOverlay: function(callback) {
			components.overlay.hide.apply(this, arguments);
		},
		showDialog: function(callback) {
	    if ($.inArray(components.overlay.status, ['closed', 'destroyed']) > -1) {
	    	methods.showOverlay.apply(this, []);
			}
	    
	    if (components.dialog.status === 'closed') {
	    	components.dialog.create().show.apply(this, arguments);
			}
		},
		hideDialog: function(callback) {
			components.dialog.hide.apply(this, arguments);
		},
		showStatusIndicator: function(callback) {
			if (components.dialog.status === 'closed') {
				methods.showDialog.apply(this, [callback]);
			} else {
				if (callback) { callback.call(); }
			}
		},
		hideStatusIndicator: function(callback) {
			if (components.dialog.status === 'open' && $indicator.closest('body').length > 0) {
				$.showtime.close();
			}
			if (callback) { callback.call(); }
		},
		autoResize: function(callback) {
			components.dialog.autoResize.apply(this, arguments);
		},
		destroy: function(callback) {
			staticMethods.close(function() {
				components.overlay.destroy.apply(this, []);
				components.dialog.destroy.apply(this, []);
				initialized = false;
			});
		}
	},
	staticMethods = {
		clearQueues: function() {
			$doc.clearQueue('showtime.main');
			if ($showtime.closest('body').length > 0) {
				var queues = ['showtime.method', 'showtime.dialog.show', 'showtime.dialog.resize', 'showtime.dialog.hide', 'showtime.dialog.scroll', 'showtime.close'];
				$.each(queues, function(index, queue) {
					$showtime.clearQueue(queue);
				});
			}			
		},
		getContent: function($elem, url, data, callback) {
			$elem.load(url, data, function(responseText, textStatus, xhr) {				
				if (responseText === '') {
					$elem.html('No data returned from getContent()');
				}
				if (callback) { callback.call(); }
			});
		},
		cacheImage: function(imageName, imagePath, forceUpdate, callback) {
			if (forceUpdate || !(imageName in imageCache)) {
		    imageCache[imageName] = new Image();
		    imageCache[imageName].src = imagePath;
			}
		},
		setDefaults: function(key, value) {
			var options = {};
			if (typeof key === 'string') {
				if (key in $.showtime.defaults) {
					options[key] = value;
				} else {
					$.error('Default ' + key + ' not found for method: setDefaults(key, value)');
				}
			} else if (typeof key === 'object') {
				options = key;
			} else {
				$.error('Invalid argument for method: setDefaults(key, value)');
			}
			return $.extend(true, $.showtime.defaults, options);
		},
		getDefaults: function(key) {
			var val = $.showtime.defaults;
			if (key in $.showtime.defaults) {
				return $.showtime.defaults[key];
			} else {
				$.error('Default ' + key + ' not found');
			}
		},
	  addRuntimeEvents: function() {
			// Document Events
			$doc.bind('keydown.showtime', function(event) {
				events.escape(event);
			});
	  },
	  removeRuntimeEvents: function() {
	  	$doc.unbind('.showtime');  	
	  },
		close: function(callback) {
			if (components.overlay.status === 'open') {
				$showtime.queue('showtime.close', function(next) {
					components.overlay.hide();
					next();
				});
			}			
				
			if (components.dialog.status === 'open') {
				$showtime.queue('showtime.close', function(next) {
					components.dialog.hide(next);
				});
			}			

			$showtime.queue('showtime.close', function(next) {
				staticMethods.removeRuntimeEvents();
				if (callback) { callback.call(); }
			});
			
			$showtime.dequeue('showtime.close');
		},
		getDocumentHeight: function() {
			var scrollHeight,
			offsetHeight;
			if ($.browser.msie && $.browser.version < 7 || quirksMode) {
				scrollHeight = Math.max(
					document.documentElement.scrollHeight,
					document.body.scrollHeight
				);
				offsetHeight = Math.max(
					document.documentElement.offsetHeight,
					document.body.offsetHeight
				);
		
				if (scrollHeight < offsetHeight) {
					return $(window).height() + 'px';
				} else {
					return scrollHeight + 'px';
				}
			} else {
				return $doc.height() + 'px';
			}
		},
		getDocumentWidth: function() {
			var scrollWidth,
			offsetWidth;
			if ($.browser.msie && $.browser.version < 7 || quirksMode) {
				scrollWidth = Math.max(
					document.documentElement.scrollWidth,
					document.body.scrollWidth
				);
				offsetWidth = Math.max(
					document.documentElement.offsetWidth,
					document.body.offsetWidth
				);
		
				if (scrollWidth < offsetWidth) {
					return $(window).width() + 'px';
				} else {
					return scrollWidth - 21 + 'px';
				}
			} else {
				return $doc.width() + 'px';
			}
		}
	},
	components = {
		overlay: {
			status: 'closed',
			create: function() {
				this.setStatus('creating');
				this.events.add();
				return this;
			},
			show: function(callback) {
				components.overlay.setStatus('showing');
		    var $this = this,
		    cssAttributes,
		    closeFunction;
				
				if ($.browser.msie && $.browser.version < 7 && settings.overlay.iframe) {
					$.fn.bgiframe ? $overlay.bgiframe() : $overlay.append($iframe);
				}
				
				if (this[0].tagName.toLowerCase() === 'body') {
		    	cssAttributes = {
		    		position: cssFixedSupport ? 'fixed' : 'absolute',
		    		height: cssFixedSupport ? '100%' : staticMethods.getDocumentHeight(),
		  	    width: cssFixedSupport ? '100%' : staticMethods.getDocumentWidth()
			    };
		    	
		    	if (!cssFixedSupport) {
			    	$win.bind('resize.showtimeOverlay', function(event) {
			    		components.overlay.resize.apply($this, []);
			    	});
					}
				} else {
					//var offsets = $this.offset();
		    	cssAttributes = {
		    		position: 'absolute',
		    		height: this.outerHeight(),
		  	    width: this.outerWidth()
			    };
		    	$win.bind('resize.showtimeOverlay', function(event) {
		    		components.overlay.resize.apply($this, []);
		    	});
				}
		    
		    closeFunction = settings.overlay.modal ? $.noop : function() {
		    	staticMethods.close();
	      };
	      
	      $overlay.hide().click(closeFunction).css(cssAttributes).appendTo(this).fadeTo(settings.speed, settings.overlay.opacity, function() {
	      	components.overlay.setStatus('open');
	      	if (callback) { callback.call(); }
		    });
			},
			hide: function(callback) {
				components.overlay.setStatus('hiding');
				$overlay.fadeTo(settings.speed, 0, function() {
					components.overlay.setStatus('closed');
					components.overlay.destroy(function() {
						$win.unbind('.showtimeOverlay');
						if (callback) { callback.call(); }
					});
				});
			},
			resize: function(callback) {
				components.overlay.setStatus('resizing');
				if (this[0].tagName.toLowerCase() === 'body') {
					$overlay.css({
						width: 0,
						height: 0
					}).css({
						width: staticMethods.getDocumentWidth(),
						height: staticMethods.getDocumentHeight(),
						left: this.offset().left
			    });
				} else {
					$overlay.css({
						left: this.offset().left
			    });
				}
				components.overlay.setStatus('open');
				if (callback) { callback.call(); }
			},
			destroy: function(callback) {				
				if (components.overlay.status === 'open') {
					$showtime.queue('showtime.overlay.destroy', function(next) {
						components.overlay.hide.apply(this, [next]);
					});
				}
				
				$showtime.queue('showtime.overlay.destroy', function(next) {
					components.overlay.events.remove();
					$overlay.remove().css({top: '0px', left: '0px'});
					components.overlay.setStatus('destroyed');
					next();
					if (callback) { callback.call(); }
				});
				
				$showtime.dequeue('showtime.overlay.destroy');
			},
			setStatus: function(status, callback) {
				this.status = status;
				console.log('overlay status: ' + status);
				switch (status) {
				case 'open':					
					$overlay.trigger('onOpen.showtimeOverlay');
					break;
				case 'closed':
					$overlay.trigger('onClose.showtimeOverlay');
					break;
				}
				if (callback) { callback.call(); }
			},
			events: {
				add: function() {
					var self = this;
					this.onOpen = settings.overlay.onOpen;
					this.onClose = settings.overlay.onClose;
					$overlay.bind('onOpen.showtimeOverlay', function(event) {
						console.log('triggering onOpen.showtimeOverlay');
						self.onOpen.call(event);
					});
					$overlay.bind('onClose.showtimeOverlay', function(event) {
						console.log('triggering onClose.showtimeOverlay');
						self.onClose.call(event);
					});
				},
				remove: function() {
					$overlay.unbind('.showtimeOverlay');
					this.onOpen = $.noop;
					this.onClose = $.noop;
				},
				onOpen: function(event) {
					
				},
				onClose: function(event) {

				}
			}
		},
		dialog: {
			status: 'closed',
			create: function() {
				this.setStatus('creating');
				this.events.add();
				return this;
			},
			show: function(callback) {
				components.dialog.setStatus('showing');
				var $this = this;
        $container.children().hide().end().append($indicator.html($('<img />', {
          alt: 'Loading Image',
          src: imageCache.indicatorImage.src,
          height: imageCache.indicatorImage.height,
          width: imageCache.indicatorImage.width
        }).after($('<span />').html(settings.dialog.indicatorMessage))).fadeIn(settings.speed));
		    
		    var cssAttributes;
		    if (this[0].tagName.toLowerCase() !== 'body') {
		    	var offsets = this.offset();
		    	
		    	cssAttributes = {
		    		top: offsets.top + 50 + 'px',
		    		left: offsets.left,
		    		marginLeft: this.outerWidth() / 2 - $showtime.outerWidth() / 2
		    	};
		    	$win.bind('resize.showtimeDialog', function(event) {
		    		components.dialog.position.apply($this, []);
		    	});
				} else {
					cssAttributes = {
						top: $this.scrollTop() + 50 + 'px',
						left: '50%',
						marginLeft: $showtime.outerWidth() / 2 * -1
					};
				}
		    
		    $showtime.queue('showtime.dialog.show', function(next) {
			    $showtime.css(cssAttributes).fadeIn(settings.speed, function() {
			    	next();
			    });			    
		    });
		    
		    if (settings.content) {		    	
		    	$showtime.queue('showtime.dialog.show', function(next) {
			  		if (settings.dialog.fixed) {
							if (!cssFixedSupport) {
								$showtime.addClass('fixed-pos-hack');
							} else {
								$showtime.css('position', 'fixed');
							}
							next();
			  		} else {
			  			var $scrollElem = $.browser.msie ? $body : $('html');
			  			if ($scrollElem.scrollTop() > 0) {
				  			$showtime.queue('showtime.dialog.scroll', function(scrollNext) {
				  				$showtime.data('currentScrollTop', $scrollElem.scrollTop());
				  				$scrollElem.animate({scrollTop: 0}, {
				  					duration: settings.speed,
				  					complete: scrollNext
				  				});
				  			});
							}
			  			
			  			$showtime.queue('showtime.dialog.scroll', function(scrollNext) {
								if (!cssFixedSupport) {
									$showtime.removeClass('fixed-pos-hack');
									$showtime[0].style.removeExpression('top');
								}
								$showtime.css({position: 'absolute'});
								scrollNext();
								next();
			  			});
			  			
			  			$showtime.dequeue('showtime.dialog.scroll');
			  		}
		    	});
			  		
		    	$showtime.queue('showtime.dialog.show', function(next) {
		    		$content.appendTo($body);
		    		if ($.isPlainObject(settings.content)) {
							staticMethods.getContent($content, settings.contextPath + settings.content.url, settings.content.data, next);
						} else {
							$content.append(settings.content);
							next();
						}
		    	});
		    	
		    	$showtime.queue('showtime.dialog.show', function(next) {
						if (settings.dialog.title) {
							$title.text(settings.dialog.title);
							$header.hide().append($title).append($closeButton.bind('click', function() {
								console.log('calling close');
								staticMethods.close();
							})).prependTo($container);
						}
						
						if (settings.dialog.buttons) {
							$.each(settings.dialog.buttons, function(key, button) {
								components.button.create(key, button).appendTo($buttons);
							});
							$footer.hide().html($buttons).appendTo($container);
						}
						
						if (settings.dialog.draggable.enabled && $.ui) {
							$showtime.draggable(settings.dialog.draggable);
							settings.dialog.draggable.initialized = true;
						}
						
						if (settings.dialog.resizable.enabled && $.ui) {
							$showtime.resizable(settings.dialog.resizable);
							settings.dialog.resizable.initialized = true;
						}
						
						components.dialog.resize.apply($this, [next]);
		    	});
				}
		    
		    $showtime.queue('showtime.dialog.show', function(next) {
		    	//$container.add($content).css('height', 'auto');
		    	components.dialog.setStatus('open', function() {
		    		next();
		    		if (callback) { callback.call(); }
		    	});
		    });
		    
		    $showtime.dequeue('showtime.dialog.show');
			},
			hide: function(callback) {
				components.dialog.setStatus('hiding');
				$showtime.fadeOut(settings.speed, function() {					
					components.dialog.events.remove();
					
					// Reset CSS
					$content.css({width: 'auto', height: 'auto'}).empty();
					$showtime.css({width: 'auto', height: 'auto', top: '', left: ''});
					$container.css({width: settings.dialog.minWidth + 'px', height: settings.dialog.minHeight + 'px'});
					
					// Remove Elements
					if ($indicator.closest('body').length) {
						$indicator.detach();
					}
					
					if ($buttons.closest('#showtime').length) {
						$buttons.empty().detach();
					};
	    		
					if (settings.dialog.title && $header.closest('#showtime').length) {
						$header.detach();
					}
	    		
	    		if ($footer.closest('#showtime').length) {
	    			$footer.children().remove().end().detach();
					}
	    		
	    		if (settings.dialog.draggable.enabled && settings.dialog.draggable.initialized) {
	    			$showtime.draggable('destroy');
					}
	    		
	    		if (settings.dialog.resizable.enabled && settings.dialog.resizable.initialized) {
	    			$showtime.resizable('destroy');
	    		}
	    		
	    		if (settings.fixed && !cssFixedSupport) {
						$showtime.removeClass('fixed-pos-hack');
					}
						    		
	    		var scrollTop = $showtime.data('currentScrollTop');
	    		if (!settings.dialog.fixed && scrollTop) {
	    			$showtime.queue('showtime.dialog.hide', function(next) {
	    				var $scrollElem = $.browser.msie ? $body : $('html');
	  					$scrollElem.animate({scrollTop: scrollTop}, {
	    					duration: settings.speed,
	    					complete: function() {
	    						$showtime.removeData('currentScrollTop');
	    						next();
	    					}
	    				});
	    			});
	    		}
	    		
	    		$showtime.queue('showtime.dialog.hide', function(next) {
	    			components.dialog.setStatus('closed', function() {
		    			cssFixedSupport ? $showtime.css('position', 'fixed') : $showtime.addClass('fixed-pos-hack');
		    			next();
		    			if (callback) { callback.call(); }
	    			});
	    		});
	    		
	    		$showtime.dequeue('showtime.dialog.hide');
				});
				
				$win.unbind('.showtimeDialog');
			},
			resize: function(callback) {
				components.dialog.setStatus('resizing');
				var contentWidth = $content.outerWidth(true),
				containerWidth,
				containerHeight,
				leftMargin;
				
				if (contentWidth > settings.dialog.maxWidth) {
					contentWidth = settings.dialog.maxWidth;
				} else if (contentWidth < settings.dialog.minWidth) {
					contentWidth = settings.dialog.minWidth;
				}
				$content.css('width', contentWidth);
				
				containerWidth = $content.outerWidth(true);
				containerHeight = $content.outerHeight(true) + $footer.outerHeight(true) + $header.outerHeight(true);
				
				leftMargin = (containerWidth + parseInt($showtime.css('borderLeftWidth'), 10) + parseInt($showtime.css('borderRightWidth'), 10)) / 2 * -1;
				if (this[0].tagName.toLowerCase() !== 'body') {
					leftMargin = this.outerWidth() / 2 - leftMargin * -1;
				}
		  	
				$indicator.hide().detach();
				$header.length > 0 ? $content.insertAfter($header) : $content.prependTo($container);
				
				$showtime.animate({marginLeft: leftMargin + 'px'}, settings.speed).fadeIn(settings.speed);  	
		  	$container.animate({width: containerWidth + 'px', height: containerHeight + 'px'}, {
		  		duration: settings.speed,
		  		complete: function() {
		  			if ($header.length > 0) {
			  			$showtime.queue('showtime.dialog.resize', function(next) {
			  				$header.fadeIn(settings.speed);
			  				next();
			  			});
		  			}
		  			
		  			if ($footer.length > 0) {
		  				$showtime.queue('showtime.dialog.resize', function(next) {
		  					$footer.fadeIn(settings.speed);
		  					next();
		  				});
						}
		  			
		  			$showtime.queue('showtime.dialog.resize', function(next) {
		  				$content.fadeIn(settings.speed, function() {
		  					next();
		  				});
		  			});
		  			
		  			$showtime.queue('showtime.dialog.resize', function(next) {
		  				if (settings.dialog.resizable.enabled && settings.dialog.resizable.initialized && $.ui) {
		  					$showtime.resizable('option', {minWidth: $showtime.width(), minHeight: $showtime.height()});
		  				}
		  				if (callback) { callback.call(); }
		  				next();
		  			});
		  			
		  			$showtime.dequeue('showtime.dialog.resize');
		  		}
		  	});
			},
			autoResize: function(callback) {
				var currentStatus = components.dialog.status;
				components.dialog.setStatus('auto-resizing');
				$showtime.add('#showtimeContainer, #showtimeContent').css('height', 'auto');
				if (callback) { callback.call(); }
				components.dialog.setStatus(currentStatus);
			},
			position: function(callback) {
				var currentStatus = components.dialog.status;
				components.dialog.setStatus('positioning');
				$showtime.css('left', this.offset().left);
				if (callback) { callback.call(); }
				components.dialog.setStatus(currentStatus);
			},
			destroy: function(callback) {
				components.dialog.setStatus('destroying');
				methods.hideDialog(function() {
					$showtime.clearQueue('showtime.dialog.show').clearQueue('showtime.dialog.hide').clearQueue('showtime.dialog.scroll');
				});
				methods.hideOverlay();
				if (callback) { callback.call(); }
				components.dialog.setStatus('destroyed');
			},
			setStatus: function(status, callback) {
				this.status = status;
				console.log('dialog status: ' + status);
				switch (status) {
				case 'open':					
					$showtime.trigger('onOpen.showtimeDialog');
					break;
				case 'closed':
					$showtime.trigger('onClose.showtimeDialog');
					break;
				case 'destroyed':
					$showtime.trigger('onDestroy.showtimeDialog');
					break;
				}
				if (callback) { callback.call(); }
			},
			events: {
				add: function() {
					var self = this;
					this.onOpen = settings.dialog.onOpen;
					this.onClose = settings.dialog.onClose;
					this.onDestroy = settings.dialog.onDestroy;
					$showtime.bind('onOpen.showtimeDialog', function(event) {
						self.onOpen.call(event);
					});
					$showtime.bind('onClose.showtimeDialog', function(event) {
						self.onClose.call(event);
					});
					$showtime.bind('onDestroy.showtimeDialog', function(event) {
						self.onDestroy.call(event);
					});
				},
				remove: function() {
					$showtime.unbind('.showtimeDialog');
					this.onOpen = $.noop();
					this.onClose = $.noop();
					this.onDestroy = $.noop();
				},
				onOpen: function(event) {
					
				},
				onClose: function(event) {

				},
				onDestroy: function(event) {
					
				}
			}
		},
		button: {		  
		  create: function(buttonName, options) {
		  	var btn = $.extend(true, {}, this, options),
		  	$button = $('<div />', {
			    id: buttonName + 'ShowtimeButton',
			    'class': 'showtime-button default'
			  });
				
				$button.html(btn.text).bind('click.showtime.' + buttonName, function(event) {
					options.click ? options.click(event) : $.noop();
			  });
		  	return $button;
		  },
		  text: '',
		  click: undefined,
		  destroy: function() {
		  	
		  }
		}
	},
	events = {
		escape: function(event) {
      if (event.keyCode === 27) {
      	event.preventDefault();
      	staticMethods.close();
      }
    }
	};
})(jQuery);
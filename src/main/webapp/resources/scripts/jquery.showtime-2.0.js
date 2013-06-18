define('jquery.showtime-2.0', ['jquery', 'jquery-ui', 'jquery.extensions', 'jquery.buildr'], function($, undefined) {
	var open = false;
	var imageCache = undefined;
	var currentScrollTop = undefined;
	var pluginErrorText = 'Showtime Error';
	var bypassFixed = false;
	var mode = 'dialog';
	var elements = {
		$st: undefined,
		$showtime: undefined,
		$container: undefined,
		$content: undefined,
		$header: undefined,
		$footer: undefined,
		$loading: undefined,
		$closeBtnImage: undefined
	};
	var defaults = {
		showOverlay: true,
		modalOverlay: false,
		opacity: 0.75,
		minWidth: 350,
		minHeight: 32,
		maxWidth: 550,
		fixed: true,
		autoSizeAfterOpen: false,
		contextPath: '../../resources/',
		speed: 200,
		title: '',
		fontFamilies: ['Ubuntu:400,500,700,400italic,500italic,700italic:latin'],
		content: undefined,
		helpAttribute: 'for',
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
		loadingImage: 'images/roller.gif',
		callback: $.noop,
		ajaxOpts: {}
	};

	var Showtime = function(options, element) {
		this.name = 'Showtime';
		this.options = options;
		this.element = element;
		this._init();
	};

	Showtime.prototype = {
		_init: function() {
			this.options = $.extend(true, {}, defaults, this.options);
			this._buildHtml();

			if (this.options.font) {
				this._loadFont(this.options.font);
			}

			$(this.element).on('click', function(e) {
				e.preventDefault();
//				plugin.$showtime.trigger('showtime.open', [content, $.extend({}, options, {$elem: $(this)})]);
			});
		},
		_create: function() {
			console.log('Creating showtime plugin');
		},
		option: function(key, value) {
			// optional: get/change options post initialization ignore if you don't require them.
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
		dialog: function() {
			console.log('Dialog method');
		},
		confirm: function() {

		},
		lightbox: function() {

		},
		_destroy: function() {

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
		}
	};
	$.widget.bridge('showtime', Showtime);
});
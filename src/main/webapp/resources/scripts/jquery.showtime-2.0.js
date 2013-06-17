define('jquery.showtime-2.0', ['jquery', 'jquery.extensions', 'jquery.buildr'], function($, undefined) {
	var initialized = false;
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

	$.widget('showtime', {
		options: {
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
			draggable: {cursor: 'move', handle: '#showtimeHeader, #showtimeFooter', opacity: 0.40, cancel: '#showtimeContent, .showtime-button, #closeBtn', initialized: false},
			resizable: {alsoResize: '#showtimeContent', autoHide: true, handles: 'e, se, s'},
			buttons: {close: null},
			loadingMessage: 'Loading, please wait...',
			loadingImage: 'images/roller.gif',
			callback: $.noop,
			ajaxOpts: {}
		},
		_create: function() {

		},
		_destroy: function() {

		},
		_setOption: function(key, value) {
			this.options[key] = value;
		}
	});
});
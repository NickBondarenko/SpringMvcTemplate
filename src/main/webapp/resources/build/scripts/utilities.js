define(['require', 'jquery'], function(require, $, undefined) {
	return {
		setActiveNav: function(selector) {
			$('li.active').removeClass('active');
			$(selector).addClass('active');
		},
		setTitle: function(title) {
			document.title = title + '::' + document.title;
		}
	};
});
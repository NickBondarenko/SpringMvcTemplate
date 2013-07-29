define(['require', 'jquery'], function(require, $, undefined) {
	return {
		setActiveNav: function(selector) {
			$('li.active').removeClass('active');
			$(selector).addClass('active');
		}
	};
});
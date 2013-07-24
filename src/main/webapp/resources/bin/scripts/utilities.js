define(['jquery'], function(undefined) {
	return {
		loadWebFont: function(fontFamilies) {
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
});
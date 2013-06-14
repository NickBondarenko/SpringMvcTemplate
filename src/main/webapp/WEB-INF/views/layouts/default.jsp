<jsp:directive.page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>
		<meta charset="UTF-8" />
		<meta name="description" content="" />
		<meta name="robots" content="all" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/bootstrap.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/bootstrap-responsive.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/scripts/jquery-ui-1.10.3/css/ui-darkness/jquery-ui-1.10.3.custom.min.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/showtime.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/<spring:theme code="styles" />/theme.css" />
		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic" />
		<link type="text/css" rel="canonical" href="" />
		<link type="image/x-icon" rel="icon" href="../../../resources/<spring:theme code="images" />/favicon.ico" />
		<style type="text/css">
			html,	body {
				height: 100%;
				/* The html and body elements cannot have any padding or margin. */
			}

			body {
				-webkit-font-smoothing: antialiased;
				font-size: 16px;
				font-family: 'Lato', sans-serif !important;
			}

				/* Wrapper for page content to push down footer */
			#wrap {
				min-height: 100%;
				height: auto !important;
				height: 100%;
				/* Negative indent footer by it's height */
				margin: 0 auto -60px;
			}

				/* Set the fixed height of the footer here */
			#push, #footer {
				height: 60px;
			}
			#footer {
				background-color: #f5f5f5;
			}

				/* Lastly, apply responsive CSS fixes as necessary */
			@media (max-width: 767px) {
				#footer {
					margin-left: -20px;
					margin-right: -20px;
					padding-left: 20px;
					padding-right: 20px;
				}
			}

			#wrap > .container {
				padding-top: 60px;
			}
			.container .credit {
				margin: 20px 0;
			}

			code {
				font-size: 80%;
			}
		</style>
		<script src="../../../resources/scripts/modernizr.custom.60227.js"></script>
		<script src="../../../resources/scripts/modernizr.tests.js"></script>
		<%--<!--[if lt IE 9]>--%>
		<%--<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>--%>
		<%--<![endif]-->--%>
		<script type="text/javascript">
			Modernizr.load([{
				load: '//code.jquery.com/jquery-1.10.1.min.js',
				complete: function() {
					if (!window.jQuery) {
						Modernizr.load('../../../resources/scripts/jquery-1.10.1.js');
					}
				}
			}, {
				load: [
					'../../../resources/scripts/bootstrap.js',
					'../../../resources/scripts/jquery-ui-1.10.3/js/jquery-ui-1.10.3.custom.js',
					'../../../resources/scripts/jquery.buildr.js',
					'../../../resources/scripts/jquery.extensions.js',
					'../../../resources/scripts/jquery.showtime.js',
					'../../../resources/<spring:theme code="scripts" />/theme.js'
				],
				complete: function() {
					console.log('loaded and ready');
					$(document).ready(function() {
						$('#anotherAction').showtime('Hello', {title: 'My Dialog'});
						if (window.ready) {
							ready();
						}
					});
				}
			}]);
		</script>
	</head>
	<body>
	  <div id="wrap">
		  <tiles:insertAttribute name="header" />
		  <div id="content" class="container">
			  <tiles:insertAttribute name="body" />
		  </div>
		  <div id="push"></div>
	  </div>
		<tiles:insertAttribute name="footer" />
	</body>
</html>
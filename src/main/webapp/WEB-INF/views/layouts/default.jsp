<jsp:directive.page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Alphatek</title>
		<meta charset="utf-8" />
		<meta name="description" content="" />
		<meta name="robots" content="all" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%--<link type="text/css" rel="stylesheet" href="../../../resources/styles/reset.css" />--%>
		<%--<link type="text/css" rel="stylesheet" href="../../../resources/styles/bootstrap.css" />--%>
		<%--<link type="text/css" rel="stylesheet" href="../../../resources/<spring:theme code="styles" />/theme.css" />--%>
		<%--<link type="text/css" rel="stylesheet" href="../../../resources/styles/animate.css" />--%>
		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic" />
		<link type="text/css" rel="canonical" href="" />
		<link type="application/rss+xml" rel="alternate" title="" href="" />
		<link type="image/x-icon" rel="icon" href="../../../resources/images/favicon.ico" />
		<style type="text/css">

		</style>

		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		<!--<script>-->
		<!--/*-->
		<!--The ieshiv takes care of our ui.directives, bootstrap module directives and-->
		<!--AngularJS's ng-view, ng-include, ng-pluralize and ng-switch directives.-->
		<!--However, IF you have custom directives (yours or someone else's) then-->
		<!--enumerate the list of tags in window.myCustomTags-->
		<!--*/-->
		<!--window.myCustomTags = [ 'yourDirective', 'somebodyElsesDirective' ]; // optional-->
		<!--</script>-->
		<!--<script src="build/angular-ui-ieshiv.js"></script>-->
		<![endif]-->

		<!--[if lt IE 9]>
		<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<%--<script src="../../../resources/scripts/jquery-1.9.1.js"></script>--%>
		<%--<script src="../../../resources/scripts/bootstrap.js"></script>--%>
		<%--<script src="../../../resources/scripts/angular.js"></script>--%>
		<%--<script src="../../../resources/scripts/jquery.buildr.js"></script>--%>
		<%--&lt;%&ndash;<script src="../../../resources/scripts/jquery.extensions.a01.js"></script>&ndash;%&gt;--%>
		<%--<script src="../../../resources/scripts/jquery.toolTips.js"></script>--%>
		<%--<script src="../../../resources/<spring:theme code="scripts" />/theme.js"></script>--%>
		<%--<script type="text/javascript">--%>
				<%--$(document).ready(function() {--%>
<%--//				$('form').find(':input:first').focus();--%>
				<%--});--%>
		<%--</script>--%>
	</head>
	<body>
		<div id="content" class="container-fluid">
			<tiles:insertAttribute name="page" />
		</div>
	</body>
</html>
<jsp:directive.page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<!--[if lt IE 7]><html class="lt-ie9 lt-ie8 lt-ie7"><![endif]-->
<!--[if IE 7]><html class="lt-ie9 lt-ie8"><![endif]-->
<!--[if IE 8]><html class="lt-ie9"><![endif]-->
<!--[if gt IE 8]><!--><html lang="en-us"><!--<![endif]-->
	<head>
		<title>Spring MVC Template</title>
		<meta charset="UTF-8" />
		<meta name="description" content="" />
		<meta name="robots" content="all" />
		<meta name="_csrf" content="${requestScope._csrf.token}"/>
		<meta name="_csrf_header" content="${requestScope._csrf.headerName}"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<c:set var="themeName" scope="session"><spring:theme code="name" /></c:set>
		<c:set var="fontFamilies" scope="session"><spring:theme code="fontFamilies" /></c:set>
		<c:set var="isProductionEnvironment" value="${pageContext.request.serverPort < 1000}" scope="session" />
		<c:set var="scriptEnvironment" value="${isProductionEnvironment ? 'bin' : 'build'}" scope="session" />

		<c:if test="${not empty fontFamilies}">
			<link type="text/css" rel="stylesheet" href="//fonts.googleapis.com/css?family=${fontFamilies}" />
		</c:if>
		<link type="text/css" rel="stylesheet" href="../../../resources/${scriptEnvironment}/styles/main.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/themes/${themeName}/styles/theme.css" />
		<link type="image/x-icon" rel="icon" href="../../../resources/themes/${themeName}/images/favicon.ico" />

		<!--[if lt IE 9]>
			<script type="text/javascript" src="../../../resources/${scriptEnvironment}/scripts/shims/respond.js"></script>
		<![endif]-->
	</head>
	<body>
	  <div id="contentWrapper">
		  <tiles:insertAttribute name="header" />
		  <div id="content" class="container">
			  <script type="text/javascript" src="../../../resources/${scriptEnvironment}/scripts/${isProductionEnvironment ? 'common.js' : 'require-js/require.js'}" data-main="../../../resources/${scriptEnvironment}/scripts/common"></script>
			  <script type="text/javascript">
				  requirejs.config({
					  paths: { theme: '../../themes/${themeName}/scripts/theme' }
				  });
				  require(['common'], function() {
					  require(['theme']);
				  });
			  </script>
			  <tiles:insertAttribute name="body" />
		  </div>
	  </div>
	  <div id="push">
		  <tiles:insertAttribute name="footer" />
	  </div>
	</body>
</html>
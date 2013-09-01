<jsp:directive.page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="isProductionEnvironment" value="${pageContext.request.serverPort < 1000}" scope="session" />
<c:set var="scriptEnvironment" value="${isProductionEnvironment ? 'bin' : 'build'}" scope="session" />
<!DOCTYPE html>
<html lang="en-us">
	<head>
		<title>Spring MVC Template</title>
		<meta charset="UTF-8" />
		<meta name="description" content="" />
		<meta name="robots" content="all" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:400,700,900,400italic" />
		<link type="text/css" rel="stylesheet" href="../../../resources/${scriptEnvironment}/styles/main.css" />
		<link type="image/x-icon" rel="icon" href="../../../favicon.ico" />
	</head>
	<body>
	  <div id="contentWrapper">
		  <tiles:insertAttribute name="header" />
		  <div id="content" class="container">
			  <script type="text/javascript" src="../../../resources/${scriptEnvironment}/scripts/${isProductionEnvironment ? 'common.js' : 'require-js/require.js'}" data-main="../../../resources/${scriptEnvironment}/scripts/common"></script>
			  <tiles:insertAttribute name="body" />
		  </div>
		  <div id="push"></div>
		  <tiles:insertAttribute name="footer" />
	  </div>
	</body>
</html>
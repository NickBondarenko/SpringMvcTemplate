<jsp:directive.page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>
		<meta charset="UTF-8" />
		<meta name="description" content="" />
		<meta name="robots" content="all" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<c:set var="themeName"><spring:theme code="name" /></c:set>
		<c:set var="isProductionEnvironment" value="${pageContext.request.serverPort > 1000}" />
		<c:set var="scriptEnvironment" value="${isProductionEnvironment ? 'build' : 'bin'}" />

		<link type="text/css" rel="stylesheet" href="../../../resources/${scriptEnvironment}/styles/main.css" />
		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic" />
		<link type="text/css" rel="stylesheet" href="../../../resources/themes/${themeName}/styles/theme.css" />
		<link type="image/x-icon" rel="icon" href="../../../resources/themes/${themeName}/images/favicon.ico" />

		<script type="text/javascript" src="../../../resources/${scriptEnvironment}/scripts/${isProductionEnvironment ? 'main.js' : 'require.js'}" data-main="../../../resources/${scriptEnvironment}/scripts/main"></script>
		<script type="text/javascript">
			require.config({
				paths: {
					theme: '../../themes/${themeName}/scripts/theme'
				}
			});
			require(['theme']);
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
<jsp:directive.page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>
		<meta charset="UTF-8" />
		<meta name="description" content="" />
		<meta name="robots" content="all" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<c:set var="themeName" scope="session"><spring:theme code="name" /></c:set>
		<c:set var="isProductionEnvironment" value="${pageContext.request.serverPort > 1000}" scope="session" />
		<c:set var="scriptEnvironment" value="${isProductionEnvironment ? 'build' : 'bin'}" scope="session" />

		<link type="text/css" rel="stylesheet" href="../../../resources/${scriptEnvironment}/styles/main.css" />
		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic" />
		<link type="text/css" rel="stylesheet" href="../../../resources/themes/${themeName}/styles/theme.css" />
		<link type="image/x-icon" rel="icon" href="../../../resources/themes/${themeName}/images/favicon.ico" />
	</head>
	<body>
	  <div id="wrap">
		  <tiles:insertAttribute name="header" />
		  <div id="content" class="container">
			  <script type="text/javascript" src="../../../resources/${scriptEnvironment}/scripts/${isProductionEnvironment ? 'common.js' : 'require.js'}" data-main="../../../resources/${scriptEnvironment}/scripts/common"></script>
			  <script type="text/javascript">
				  requirejs.config({
					  paths: {theme: '../../themes/${themeName}/scripts/theme'},
					  common: {
						  themeName: '${themeName}',
						  isProductionEnvironment: '${isProductionEnvironment}',
						  scriptEnvironment: '${scriptEnvironment}'
					  }
				  });
				  require(['theme']);
			  </script>
			  <tiles:insertAttribute name="body" />
		  </div>
		  <div id="push"></div>
	  </div>
		<tiles:insertAttribute name="footer" />
	</body>
</html>
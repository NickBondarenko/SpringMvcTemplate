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

		<link type="text/css" rel="stylesheet" href="../../../resources/build/styles/main.css" />
		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic" />
		<link type="text/css" rel="stylesheet" href="../../../resources/<spring:theme code="styles" />/theme.css" />
		<link type="text/css" rel="canonical" href="" />
		<link type="image/x-icon" rel="icon" href="../../../resources/<spring:theme code="images" />/favicon.ico" />

		<c:set var="themeScriptPath"><spring:theme code="scripts" /></c:set>
		<c:choose>
			<c:when test="${pageContext.request.serverPort < 1000}">
				<script src="../../../resources/build/scripts/main.js" data-main="../../../resources/bin/scripts/main"></script>
			</c:when>
			<c:otherwise>
				<script src="../../../resources/bin/scripts/require.js" data-main="../../../resources/bin/scripts/application"></script>
			</c:otherwise>
		</c:choose>
		<script>
			require.config({
				paths: {
					theme: '../../${themeScriptPath}/theme'
				}
			});
			require(['theme'], function(theme) {

			});
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
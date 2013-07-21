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
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/base.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/bootstrap-responsive.min.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/jquery-ui/ui-darkness/jquery-ui-1.10.3.min.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/styles/showtime.css" />
		<link type="text/css" rel="stylesheet" href="../../../resources/<spring:theme code="styles" />/theme.css" />
		<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:300,400,700,900,300italic,400italic,700italic,900italic" />
		<link type="text/css" rel="canonical" href="" />
		<link type="image/x-icon" rel="icon" href="../../../resources/<spring:theme code="images" />/favicon.ico" />

		<script src="../../../resources/scripts/require.min.js" data-main="../../../resources/scripts/application.js"></script>
		<%--<!--[if lt IE 9]>--%>
		<%--<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>--%>
		<%--<![endif]-->--%>
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
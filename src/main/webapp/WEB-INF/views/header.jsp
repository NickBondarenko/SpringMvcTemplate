<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="authenticated" var="authenticated" />
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- Fixed navbar -->
<header class="navbar navbar-default navbar-fixed-top" role="banner">
	<div class="container">
		<nav class="collapse navbar-collapse" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Tylt</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li id="homeLink"><a href="${contextPath}/home">Home</a></li>
					<c:choose>
						<c:when test="${authenticated}">
							<li id="accountLink"><a class="btn-header" href="${contextPath}/account">Account</a></li>
							<li><a class="btn-header" href="${contextPath}/logout">Logout</a></li>
						</c:when>
						<c:otherwise>
							<li id="loginLink"><a href="${contextPath}/login">Login</a></li>
							<li id="registrationLink"><a href="${contextPath}/registration">Register</a></li>
						</c:otherwise>
					</c:choose>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<c:if test="${isAdmin}">
								<li><a href="${contextPath}/admin">Admin</a></li>
							</c:if>
							<li><a id="anotherAction" href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					</li>
				</ul>
				<c:if test="${authenticated}">
					<div id="welcomeMessage" class="pull-right">
						Welcome <sec:authentication property="principal.firstName" />&nbsp;<sec:authentication property="principal.lastName" />
					</div>
				</c:if>
			</div>
		</nav>
	</div>
</header>
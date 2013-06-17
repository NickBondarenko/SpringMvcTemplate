<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="authenticated" var="authenticated" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- Fixed navbar -->
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="brand" href="#">Spring MVC Template</a>
			<div class="nav-collapse collapse">
				<ul id="navMenu" class="nav">
					<li class="active"><a href="${contextPath}/home">Home</a></li>
					<c:choose>
						<c:when test="${authenticated}">
							<li><a class="btn-header" href="${contextPath}/account">Account</a></li>
							<li><a class="btn-header" href="${contextPath}/logout">Logout</a></li>
						</c:when>
						<c:otherwise>
							<li id="loginLink"><a href="${contextPath}/login">Login</a></li>
							<li id="registrationLink"><a href="${contextPath}/registration">Register</a></li>
						</c:otherwise>
					</c:choose>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">More <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${contextPath}/admin">Admin</a></li>
							<li><a id="anotherAction" href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li class="nav-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					</li>
				</ul>
				<c:if test="${authenticated}">
					<div id="welcomeMessage" class="pull-right">Welcome <sec:authentication property="principal.firstName" /> <sec:authentication property="principal.lastName" /></div>
        </c:if>
			</div>
		</div>
	</div>
</div>
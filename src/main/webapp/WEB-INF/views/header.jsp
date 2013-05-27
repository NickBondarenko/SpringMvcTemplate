<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="authenticated" var="authenticated" />

<!-- begins navbar -->
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand scroller" data-section="body" href="/home">
				<img src="../../resources/images/logo.png" alt="logo" />
			</a>
			<%--<c:if test="${authenticated}">--%>
				<%--<div class="pull-right">Welcome <sec:authentication property="name" /></div>--%>
			<%--</c:if>--%>
			<div class="nav-collapse collapse">
				<ul class="nav pull-right">
					<li><a href="/features" class="scroller" data-section="#features">Features</a></li>
					<li><a href="/pricing" class="scroller" data-section="#pricing">Pricing</a></li>
					<li><a href="/contact" class="scroller" data-section="#footer">Contact us</a></li>
					<li><a href="/blog">Blog</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							External Pages
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/features">Features</a></li>
							<li><a href="/pricing">Pricing</a></li>
							<li><a href="/portfolio">Portfolio</a></li>
							<li><a href="/coming-soon">Coming Soon</a></li>
							<li><a href="/about">About us</a></li>
							<li><a href="/contact">Contact us</a></li>
							<li><a href="/faq">FAQ</a></li>
						</ul>
					</li>
					<c:choose>
						<c:when test="${authenticated}">
							<li>
								<a class="btn-header" href="">Account</a>
								<a class="btn-header" href="/logout">Logout</a>
							</li>
						</c:when>
						<c:otherwise>
							<li><a class="btn-header" href="${pageContext.request.contextPath}/registration">Sign up</a></li>
							<li><a class="btn-header" href="/signin">Sign in</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</div>
</div>
<a href="#" class="scrolltop">
	<span>up</span>
</a>
<%--<div id="nav-bar" class="navbar navbar-fixed-top">--%>
	<%--<div class="navbar-inner">--%>
		<%--<div class="container">--%>
			<%--<c:url var="welcomeUrl" value="/home" />--%>
			<%--<a class="brand" href="${welcomeUrl}">Alphatek</a>--%>
			<%--<div class="nav-collapse">--%>
				<%--<ul class="nav">--%>
					<%--<li><a id="navWelcomeLink" href="${welcomeUrl}">Welcome</a></li>--%>
					<%--<li><a id="navEventsLink" href="${pageContext.request.contextPath}">About Us</a></li>--%>
					<%--<li><a id="navMyEventsLink" href="${pageContext.request.contextPath}">Account</a></li>--%>
					<%--<li><a id="navCreateEventLink" href="${pageContext.request.contextPath}/registration/show">Register</a></li>--%>
					<%--<li><a id="navH2Link" href="">Sign In</a></li>--%>
				<%--</ul>--%>
			<%--</div>--%>
			<%--<div id="nav-account" class="nav-collapse pull-right">--%>
				<%--<form class="navbar-form pull-right">--%>
					<%--<div class="input-append">--%>
						<%--<input id="search" name="search" type="text" placeholder="Search" />--%>
						<%--<div class="btn-group">--%>
							<%--<button type="submit" class="btn"><i class="icon-search"></i>&nbsp;Search</button>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</form>--%>
				<%--&lt;%&ndash;<ul class="nav">&ndash;%&gt;--%>
					<%--&lt;%&ndash;<sec:authorize access="authenticated" var="authenticated"/>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<c:when test="${authenticated}">&ndash;%&gt;--%>
							<%--&lt;%&ndash;<li id="greeting"><div>Welcome <sec:authentication property="name" /></div></li>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<c:url var="logoutUrl" value="/logout"/>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<li><a id="navLogoutLink" href="${logoutUrl}">Logout</a></li>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
						<%--&lt;%&ndash;<c:otherwise>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<c:url var="signupUrl" value="/signup/form"/>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<li><a id="navSignupLink" href="${signupUrl}">Signup</a></li>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<c:url var="loginUrl" value="/login/form"/>&ndash;%&gt;--%>
							<%--&lt;%&ndash;<li><a id="navLoginLink" href="${loginUrl}">Login</a></li>&ndash;%&gt;--%>
						<%--&lt;%&ndash;</c:otherwise>&ndash;%&gt;--%>
					<%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
				<%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>
<%--</div>--%>
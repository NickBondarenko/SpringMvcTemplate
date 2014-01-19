<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authorize access="authenticated" var="authenticated" />
<spring:eval var="loginUrl" expression="@environment.getProperty('login.url')" />
<spring:eval var="authenticationUrl" expression="@environment.getProperty('authentication.url')" />
<spring:eval var="registrationUrl" expression="@environment.getProperty('registration.url')" />
<div id="loginContainer">
	<div class="page-header">
		<h1>${authenticated ? 'Password Required' : 'Login'}</h1>
	</div>
	<div>
		<c:if test="${param.error ne null}">
			<div id="errorAlert" class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				Failed to login.
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					Reason: ${SPRING_SECURITY_LAST_EXCEPTION.message}
				</c:if>
			</div>
		</c:if>
		<c:if test="${param.logout ne null}">
			<div class="alert alert-success inline">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				You have been logged out.
			</div>
		</c:if>
	</div>
	<div id="formContainer" class="section col-lg-4">
		<div>
			<form id="signinForm" action="${contextPath}${authenticationUrl}" method="post">
				<fieldset>
					<c:choose>
						<c:when test="${authenticated}">
							<sec:authentication property="principal.username" var="username" />
							<legend>You must provide your password to access the requested resource</legend>
							<div class="form-group">
								<label for="authenticatedPassword"></label>
								<input type="password" id="authenticatedPassword" name="password" class="form-control input-xxlarge" />
								<input type="hidden" name="username" value="${username}" />
							</div>
							<button type="submit" class="btn btn-primary">Sign In</button>
						</c:when>
						<c:otherwise>
							<legend>Enter your credentials</legend>
							<div class="form-group">
								<label for="username">Username</label>
								<input type="text" id="username" name="username" class="form-control input-xxlarge" />
							</div>
							<div class="form-group">
								<label for="password">Password</label>
								<input type="password" id="password" name="password" class="form-control input-xxlarge" />
							</div>
							<div class="checkbox">
								<label for="rememberMeToken">
									<input id="rememberMeToken" type="checkbox" name="rememberMeToken" />Remember me
								</label>
							</div>
							<button type="submit" class="btn btn-primary">Sign In</button>
							<div class="right">
								<a href="${contextPath}/reset">Forgot password?</a>
							</div>
						</c:otherwise>
					</c:choose>
				</fieldset>
				<input type="hidden" name="${requestScope._csrf.parameterName}" value="${requestScope._csrf.token}" />
			</form>
			<p class="already">Don't have an account? <a href="${contextPath}${registrationUrl}">Register</a></p>
		</div>
	</div>
</div>
<script type="text/javascript">
	require(['common'], function() {
		require(['app/login']);
	});
</script>
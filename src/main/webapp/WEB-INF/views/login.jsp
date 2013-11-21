<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="loginContainer" class="container">
	<div class="page-header">
		<h1 class="inline">Login</h1>
		<c:if test="${param.error ne null}">
			<div class="alert alert-error inline">
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
	<div id="formContainer">
			<form id="signinForm" action="${contextPath}/login/authenticate" method="post">
				<fieldset>
					<legend>Enter your credentials</legend>
					<div id="fieldsetContent" class="form-signin pull-left">
						<label for="username">Username</label>
						<input type="text" id="username" name="username" class="input-xlarge" />
						<label for="password">Password</label>
						<input type="password" id="password" name="password" class="input-xlarge" />
						<div class="remember">
							<div class="left">
								<label for="rememberMe" class="checkbox">
									<input id="rememberMe" type="checkbox" />Remember me
								</label>
							</div>
						</div>
						<button type="submit" class="btn btn-primary">Sign In</button>
						<div class="right">
							<a href="/reset">Forgot password?</a>
						</div>
					</div>
				</fieldset>
			</form>
			<p class="already">Don't have an account? <a href="${contextPath}/registration">Register</a></p>
		</div>
	</div>
<script type="text/javascript">
	require(['common'], function() {
		require(['app/login']);
	});
</script>
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="loginContainer" class="container">
	<div class="row">
		<h2 class="section_header">
			<hr class="left visible-desktop">
			<span>Login</span>
			<hr class="right visible-desktop">
		</h2>
		<c:if test="${param.error ne null}">
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				Failed to login.
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					Reason: ${SPRING_SECURITY_LAST_EXCEPTION}
				</c:if>
			</div>
		</c:if>
		<c:if test="${param.logout ne null}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				You have been logged out.
			</div>
		</c:if>
		<div class="well cell">
			<form id="signinForm" action="/signin/authenticate" method="post">
				<fieldset>
					<legend>Log in to your account</legend>
					<label>Username</label>
					<input type="text" id="username" name="username" />
					<label>Password</label>
					<input type="password" id="password" name="password" />
					<div class="remember">
						<div class="left">
							<input id="remember_me" type="checkbox">
							<label for="remember_me">Remember me</label>
						</div>
						<div class="right">
							<a href="/reset">Forgot password?</a>
						</div>
					</div>
					<button type="submit" class="default-button">Sign In</button>
				</fieldset>
			</form>
			<p class="already">Don't have an account? <a href="/signup"> Sign up</a></p>
		</div>
	</div>
</div>
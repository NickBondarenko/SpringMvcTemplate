<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
	#formContainer {
		max-width: 350px;
	}

	.form-signin {
		max-width: 500px;
		padding: 19px 29px 29px;
		margin: 0 auto 20px;
		background-color: #fafafa;
		border: 1px solid #e5e5e5;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		-moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		box-shadow: 0 1px 2px rgba(0,0,0,.05);
	}

	.form-signin input[type="text"],
	.form-signin input[type="password"] {
		font-size: 16px;
		height: auto;
		margin-bottom: 15px;
		padding: 7px 9px;
	}

	.inline-block {
		display: inline-block;
	}

	.page-header h1 {
		margin-right: 15px;
	}

	.page-header .alert {
		margin-bottom: 0;
	}

	#rememberMe {
		vertical-align: text-top;
		margin-right: 5px;
	}
</style>
<script type="text/javascript">
	function ready() {
		$('li.active').removeClass('active');
		$('#loginLink').addClass('active');
		$('#username').focus();
	}
</script>
<div id="loginContainer" class="container">
	<div class="page-header">
		<h1 class="inline-block">Login</h1>
		<c:if test="${param.error ne null}">
			<div class="alert alert-error inline-block">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				Failed to login.
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					Reason: ${SPRING_SECURITY_LAST_EXCEPTION.message}
				</c:if>
			</div>
		</c:if>
		<c:if test="${param.logout ne null}">
			<div class="alert alert-success inline-block">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				You have been logged out.
			</div>
		</c:if>
	</div>
	<div id="formContainer">
		<div class="form-signin pull-left">
			<form id="signinForm" action="${contextPath}/login/authenticate" method="post">
				<fieldset>
					<legend>Enter your credentials</legend>
					<label for="username">Username</label>
					<input type="text" id="username" name="username" class="input-xlarge" />
					<label for="password">Password</label>
					<input type="password" id="password" name="password" class="input-xlarge" />
					<div class="remember">
						<div class="left">
							<input id="rememberMe" type="checkbox" class="inline-block" />
							<label for="rememberMe" class="inline-block">Remember me</label>
						</div>
					</div>
					<button type="submit" class="btn btn-primary">Sign In</button>
					<div class="right">
						<a href="/reset">Forgot password?</a>
					</div>
				</fieldset>
			</form>
			<p class="already">Don't have an account? <a href="${contextPath}/registration">Register</a></p>
		</div>
	</div>
</div>
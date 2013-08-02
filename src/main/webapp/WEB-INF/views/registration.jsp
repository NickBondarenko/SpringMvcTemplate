<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div id="registrationFragment" class="container">
	<div class="page-header">
		<h1>Register</h1>
	</div>
	<div class="section hero-unit">
		<sf:form id="registrationForm" modelAttribute="user" action="/registration" method="post">
			<spring:hasBindErrors name="user">
				<div class="alert alert-block alert-error">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>We were unable to complete your registration request due to errors on the form.</h4>
					Please fix the inputs in red before submitting your registration request.
				</div>
			</spring:hasBindErrors>
			<div class="row-fluid table form-table">
				<div class="cell pull-left span6">
					<fieldset>
						<legend>User Info&nbsp;<i class="icon-user"></i></legend>
						<div>
							<tags:input-control-group path="firstName" label="First Name" />
							<tags:input-control-group path="lastName" label="Last Name" />
							<tags:input-control-group path="emailAddress" label="Email Address" icon="envelope" cssClass="input-xlarge" />
							<tags:input-control-group path="username" cssClass="input-xlarge" />
							<tags:password-control-group path="password" cssClass="input-xlarge" />
							<tags:password-control-group path="confirmPassword" label="Confirm Password" cssClass="input-xlarge" />
						</div>
					</fieldset>
				</div>
				<div class="cell pull-right span6">
					<fieldset>
						<legend>Address Info&nbsp;<i class="icon-home"></i></legend>
						<div>
							<tags:select-control-group path="address.country" label="Country" items="${requestScope.countries}" itemValue="code" itemLabel="name" />
							<tags:input-control-group path="address.street" label="Street" />
							<tags:input-control-group path="address.additionalInfo" label="Additional Info" />
							<tags:input-control-group path="address.city" label="City" />
							<tags:select-control-group path="address.state" label="State" items="${requestScope.states}" itemValue="abbreviation" itemLabel="name" />
							<tags:input-control-group path="address.zipCode" label="Zip Code" maxLength="10" placeholder="XXXXX-XXXX" cssClass="span3" />
						</div>
					</fieldset>
				</div>
				<div id="buttonContainer">
					<button type="submit" class="btn btn-primary">Register</button>
				</div>
			</div>
		</sf:form>
	</div>
</div>
<script type="text/javascript">
	requirejs.config({
		config: {
			'app/main-registration': {hasBindingErrors: '${requestScope.hasBindingErrors}'}
		}
	});
	require(['common'], function() {
		require(['app/registration']);
	});
</script>
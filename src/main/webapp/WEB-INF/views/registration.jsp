<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div id="registrationFragment">
	<div class="page-header">
		<h1>Register</h1>
	</div>
	<sf:form id="registrationForm" modelAttribute="registrationForm" action="/registration" method="post">
		<spring:hasBindErrors name="registrationForm">
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<h4>We were unable to complete your registration request due to errors on the form.</h4>
				Please fix the inputs in red before submitting your registration request.
			</div>
		</spring:hasBindErrors>
		<div class="section">
			<div class="col-lg-6">
				<fieldset>
					<legend>User Info&nbsp;<i class="glyphicon glyphicon-user"></i></legend>
					<div>
						<tags:form-group-input path="firstName" label="First Name" cssClass="input-large" />
						<tags:form-group-input path="lastName" label="Last Name" cssClass="input-large" />
						<tags:form-group-input-icon path="emailAddress" label="Email Address" icon="envelope" cssClass="input-xlarge" />
						<tags:form-group-input path="username" cssClass="input-large" />
						<tags:form-group-password path="password" cssClass="input-large" />
						<tags:form-group-password path="confirmPassword" label="Confirm Password" cssClass="input-large" />
					</div>
				</fieldset>
			</div>
			<div class="col-lg-6">
				<fieldset>
					<legend>Address Info&nbsp;<i class="glyphicon glyphicon-home"></i></legend>
					<div>
						<tags:form-group-select path="address.country" label="Country" items="${requestScope.countries}" itemValue="code" itemLabel="name" />
						<tags:form-group-input path="address.street" label="Street" cssClass="input-large" />
						<tags:form-group-input path="address.additionalInfo" label="Additional Info" cssClass="input-large" />
						<tags:form-group-input path="address.city" label="City" cssClass="input-large" />
						<tags:form-group-select path="address.state" label="State" items="${requestScope.states}" itemValue="abbreviation" itemLabel="name" />
						<tags:form-group-input path="address.zipCode" label="Zip Code" size="11" maxLength="10" placeholder="XXXXX-XXXX" />
					</div>
				</fieldset>
			</div>
			<div id="buttonContainer">
				<button type="submit" class="btn btn-primary">Register</button>
			</div>
		</div>
	</sf:form>
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
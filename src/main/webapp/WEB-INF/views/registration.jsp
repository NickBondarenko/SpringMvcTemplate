<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cg" tagdir="/WEB-INF/tags" %>

<style type="text/css">
	.form-table {
		background-color: #fafafa;
		border: 1px solid #e5e5e5;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		-moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		box-shadow: 0 1px 2px rgba(0,0,0,.05);
		box-sizing: border-box;
		padding: 20px;
	}

	fieldset legend [class^="icon-"], .cell fieldset legend [class*=" icon-"] {
		vertical-align: baseline;
	}

	#buttonContainer {
		clear: both;
		padding: 10px 0;
	}

	.control-group .controls input, .control-group .controls select, .control-group .controls .input-prepend {
		margin-bottom: 0;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('li.active').removeClass('active');
		$('#registrationLink').addClass('active');
		$('#firstName').focus();
		if (${requestScope.hasBindingErrors eq true}) {
			$('.alert-error').show();
		}
	});
</script>
<div id="registrationFragment" class="container">
	<div class="page-header">
		<h1>Register</h1>
	</div>
	<div>
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
							<cg:input-control-group path="firstName" label="First Name" />
							<cg:input-control-group path="lastName" label="Last Name" />
							<cg:input-control-group path="emailAddress" label="Email Address" icon="envelope" cssClass="input-xlarge" />
							<cg:input-control-group path="username" cssClass="input-xlarge" />
							<cg:password-control-group path="password" cssClass="input-xlarge" />
							<cg:password-control-group path="confirmPassword" label="Confirm Password" cssClass="input-xlarge" />
						</div>
					</fieldset>
				</div>
				<div class="cell pull-right span6">
					<fieldset>
						<legend>Address Info&nbsp;<i class="icon-home"></i></legend>
						<div>
							<cg:input-control-group path="address.street" label="Street" />
							<cg:input-control-group path="address.additionalInfo" label="Additional Info" />
							<cg:input-control-group path="address.city" label="City" />
							<cg:select-control-group path="address.state" label="State" items="${requestScope.states}" itemValue="abbreviation" itemLabel="name" />
							<cg:input-control-group path="address.zipCode" label="Zip Code" maxLength="10" placeholder="XXXXX-XXXX" cssClass="span3" />
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
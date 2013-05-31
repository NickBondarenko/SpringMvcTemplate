<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style type="text/css">
	.form-registration {
		padding: 19px 29px 29px;
		background-color: #fafafa;
		border: 1px solid #e5e5e5;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		-moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		box-shadow: 0 1px 2px rgba(0,0,0,.05);
	}

	fieldset legend [class^="icon-"], .cell fieldset legend [class*=" icon-"] {
		vertical-align: baseline;
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
	<!-- header -->
	<div class="page-header">
		<h1>Register</h1>
	</div>
	<div>
		<sf:form id="registrationForm" modelAttribute="user" action="/registration" method="post" cssClass="form-registration">
			<spring:hasBindErrors name="user">
				<div class="alert alert-block alert-error">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>Error(s)!</h4>
					<sf:errors path="*" element="div" />
				</div>
			</spring:hasBindErrors>
			<div class="row-fluid table">
				<div class="cell pull-left span6">
					<fieldset>
						<legend>User Info&nbsp;<i class="icon-user"></i></legend>
						<div>
							<sf:label path="firstName">First Name</sf:label>
							<sf:input path="firstName" id="firstName" cssClass="txt" />
							<sf:label path="lastName">Last Name</sf:label>
							<sf:input path="lastName" id="lastName" />
							<div class="control-group">
								<sf:label path="emailAddress" cssClass="control-label">Email Address</sf:label>
								<div class="controls">
									<div class="input-prepend">
										<span class="add-on"><i class="icon-envelope"></i></span>
										<sf:input path="emailAddress" id="emailAddress" cssClass="" />
									</div>
								</div>
							</div>
							<sf:label path="username">Username</sf:label>
							<sf:input path="username" id="username" cssClass="span8" />
							<sf:label path="password">Password</sf:label>
							<sf:password path="password" id="password" cssClass="span8" />
						</div>
					</fieldset>
				</div>
				<div class="cell pull-right span6">
					<fieldset>
						<legend>Address Info&nbsp;<i class="icon-home"></i></legend>
						<div>
							<sf:label path="address.street">Street</sf:label>
							<sf:input path="address.street" id="address.street" />
							<sf:label path="address.additionalInfo">Additional Info</sf:label>
							<sf:input path="address.additionalInfo" id="address.additionalInfo" />
							<sf:label path="address.city">City</sf:label>
							<sf:input path="address.city" id="address.city" />
							<sf:label path="address.state">State</sf:label>
							<sf:select path="address.state" id="address.state">
								<sf:option value="">Select</sf:option>
								<sf:options items="${requestScope.states}" itemValue="code" itemLabel="displayName" />
							</sf:select>
							<sf:label path="address.zipCode">Zip Code</sf:label>
							<sf:input path="address.zipCode" id="address.zipCode" cssClass="span3" maxlength="10" placeholder="12345-1234" />
						</div>
					</fieldset>
				</div>
			</div>
			<div id="buttonContainer" class="">
				<button type="submit" class="btn btn-primary">Register</button>
			</div>
		</sf:form>
	</div>
</div>
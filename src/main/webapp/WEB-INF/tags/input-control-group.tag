<%@ tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="path" required="true" type="java.lang.String"%>
<%@ attribute name="cssClass" required="false" type="java.lang.String"%>
<%@ attribute name="label" required="false" type="java.lang.String"%>
<%@ attribute name="required" required="false" type="java.lang.Boolean"%>
<%@ attribute name="icon" required="false" type="java.lang.String"%>
<%@ attribute name="maxLength" required="false" type="java.lang.String" %>
<%@ attribute name="placeholder" required="false" type="java.lang.String" %>

<c:if test="${empty label}">
	<c:set var="label" value="${fn:toUpperCase(fn:substring(path, 0, 1))}${fn:toLowerCase(fn:substring(path, 1, fn:length(path)))}" />
</c:if>
<spring:bind path="${path}">
	<div class="control-group ${status.error ? 'error' : ''}">
		<label class="control-label" for="${path}">${label}<c:if test="${required}"><span class="required">*</span></c:if></label>
		<div class="controls">
			<c:choose>
				<c:when test="${not empty icon}">
					<div class="input-prepend">
						<span class="add-on"><i class="icon-${icon}"></i></span>
						<form:input path="${path}" cssClass="${empty cssClass ? '' : cssClass}" maxlength="${maxLength}" placeholder="${placeholder}" />
					</div>
				</c:when>
				<c:otherwise>
					<form:input path="${path}" cssClass="${empty cssClass ? '' : cssClass}" maxlength="${maxLength}" placeholder="${placeholder}" />
				</c:otherwise>
			</c:choose>
			<c:if test="${status.error}">
				<span class="help-block">${status.errorMessage}</span>
			</c:if>
		</div>
	</div>
</spring:bind>
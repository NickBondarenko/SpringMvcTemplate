<jsp:directive.page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
	<c:choose>
		<c:when test="${pageContext.response.status eq 403}">
			<h1>${requestScope.responseEntity.statusCode.reasonPhrase}</h1>
			You don't have access the requested resource.
		</c:when>
		<c:otherwise>
			<h1>Oops... An error occurred.</h1>
			${requestScope.responseEntity.body}
		</c:otherwise>
	</c:choose>
</div>
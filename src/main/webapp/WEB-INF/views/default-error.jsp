<jsp:directive.page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<h1>Oops... An error occurred.</h1>
	<c:if test="${not empty requestScope.responseEntity}">
		${requestScope.responseEntity.body}
	</c:if>
</div>
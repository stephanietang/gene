<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>

<c:if test="${not empty message}">
	<div class="alert alert-danger">
		<h2>${code}</h2>
		<p>message: ${message}</p>
		<p>developerMessage: ${developerMessage}</p>
		<p><a href="${moreInfoUrl}">${moreInfoUrl}</a></p>
	</div>
</c:if>

<%@ include file="common/footer.jsp" %>
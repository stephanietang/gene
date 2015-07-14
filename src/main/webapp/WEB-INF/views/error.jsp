<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>

<c:if test="${not empty message}">
	<div class="alert alert-warning">
		<h2>${status}</h2>
		<p><span class="glyphicon glyphicon-alert" aria-hidden="true"></span> ${message}</p>
	</div>
</c:if>

<%@ include file="common/footer.jsp" %>
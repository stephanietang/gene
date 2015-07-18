<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>

<div class="container">
<c:if test="${baseForm.logined and not baseForm.user.userEnabled}">
	<div class="well">
		<p><spring:message code="msg.user.verifyConfirm" /> ${baseForm.user.email}</p>
	</div>
</c:if>
</div>

<%@ include file="common/footer.jsp" %>
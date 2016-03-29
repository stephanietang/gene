<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<script>
	function sendEmailSubmit() {
		$("#sendEmailForm").submit();
	}
</script>

<div class="container">
<div class="jumbotron">
	<h2>请验证邮箱完成注册</h2>
	<p>我们已经将验证邮件发送至邮箱: <a href="${mailDomain}" target="_blank">${email}</a></p>
	<p><a class="btn btn-lg btn-primary" href="${mailDomain}" role="button" target="_blank">点击进入邮箱激活</a></p>
	<p>没有收到验证邮件？</p>
	<div>
		<ul>
		  <li>填错邮箱了？<a href="<c:url value="/register" />">换个邮箱。</a></li>
		  <li>看看邮件是不是进了垃圾箱。</li>
		  <li>还是没有收到验证邮件。<a href="javascript:sendEmailSubmit()">重新发送验证邮件。</a></li>
		</ul>
	</div>
</div>
</div>

<form:form action="send_mail" method="post" id="sendEmailForm" modelAttribute="registerForm">
	<form:hidden path="email" />
</form:form>

<%@ include file="../common/footer.jsp" %>
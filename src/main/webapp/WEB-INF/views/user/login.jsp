<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<div class="container">
<form:form class="form-horizontal" method="post" commandName="loginForm">
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label"><spring:message code="label.common.email" /></label>
		<div class="col-sm-10"><form:input path="email" class="form-control" placeholder="example@example.com" /></div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label"><spring:message code="label.common.password" /></label>
		<div class="col-sm-10"><form:password path="password" class="form-control" placeholder="password"/></div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-8">
			<div class="checkbox">
				<label><input id="rememberMe" type="checkbox" name="rememberMe" checked="checked"/><spring:message code="label.common.rememberMe" /></label>
			</div>
		</div>
		<div class="col-sm-2">
			<div class="fr"><a href="${contextPath}/forget_password"><spring:message code="label.common.forgetPassword" /></a></div>
		</div>	
	</div>
	<div class="form-group">
    	<div class="col-sm-offset-2 col-sm-10">
    		<spring:message code="button.login" var="loginButton"/>
			<input type="submit" class="btn btn-primary" id="loginButton" value="${loginButton}"></input>
		</div>
	</div>
	
</form:form>
</div>
	
<script>
jQuery(document).ready(function() { 
	$("#loginForm").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			email: {
				required: true,
				cemail: true
			},
			password: {
				required: true,
				minlength: 4
			}
		},
		submitHandler:function(form){
			$('#loginButton').attr('disabled','disabled');
			// use native form not $(form) to avoid recursive validation
			form.submit();
		}
	});
	
	$("#email").focus(function() {
		$('#msg').text("").hide();
		$('#errorMessage').text("").hide();
	});
	
	$("#password").focus(function() {
		$('#msg').text("").hide();
		$('#errorMessage').text("").hide();
	});
});
</script>

<%@ include file="../common/footer.jsp" %>
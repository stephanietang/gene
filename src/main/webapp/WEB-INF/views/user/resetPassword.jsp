<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<div class="container">
<form:form class="form-horizontal" commandName="resetPasswordForm">
	<div id="resetPassword_error" style="display:none;" class="alert alert-warning" role="alert"></div>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label"><spring:message code="label.updatePwd.email" /></label>
		<div class="col-sm-10"><p class="form-control-static">${resetPasswordForm.email}</p></div>
		<form:hidden id="email" path="email"/>
		<form:hidden id="token" path="token"/>
	</div>
	<div class="form-group">
		<label for="newPassword" class="col-sm-2 control-label"><spring:message code="label.updatePwd.newPassword" /></label>
		<div class="col-sm-10"><form:password path="newPassword" class="form-control"/></div>
	</div>
	<div class="form-group">
		<label for="confirmNewPassword" class="col-sm-2 control-label"><spring:message code="label.updatePwd.confirmPassword" /></label>
		<div class="col-sm-10"><form:password path="confirmNewPassword" class="form-control"/></div>
	</div>
	<div class="form-group">
    	<div class="col-sm-offset-2 col-sm-10">
    		<spring:message code="button.user.save" var="saveButton"/>
			<input type="submit" class="btn btn-primary" value="${saveButton}">
		</div>
	</div>
</form:form>

<div style="display:none"> 
	<div class="jumbotron" id="resetPasswordDiv">
		<h3><spring:message code="msg.user.resetPassword.success" /></h3>
		<h4><spring:message code="msg.user.resetPassword.login" /></h4>
		<p><a href="${contextPath}/login"><spring:message code="menu.login" /></a></p>
	</div>
</div>
</div>

<spring:message code="title.user.resetPassword.success" var="resetPasswordSuccessTitle"/>
<script>
var resetPasswordsuccessTitle = "${resetPasswordSuccessTitle}";
$(document).ready(function() {
	$("#resetPasswordForm").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			newPassword: {
				required: true,
				minlength: 4
			},
			confirmNewPassword: {
				required: true,
				minlength: 4,
				equalTo: "#newPassword"
			}
		},
		submitHandler:function(form){
			var email = $('#email').val();
			var newPassword = $('#newPassword').val();
			var token = $('#token').val();

		    var json = JSON.stringify({email:email, token: token, newPassword:newPassword});
		    $.ajax({
		    	url: ctx + "/reset_password.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == '200'){
		        		$.colorbox({inline:true, href:$("#resetPasswordDiv"),title: resetPasswordsuccessTitle});
		        		setCountdown(4,'resetPasswordDiv h4 span',ctx+"/login");
		        	}else{
		        		$("#resetPassword_error").text(result.message).show();
		        	}
		        },
		        error : function(){
		            $(this).html("Error!");
		        }
		    });
		}
	});
	
});
</script>
<%@ include file="../common/footer.jsp" %>
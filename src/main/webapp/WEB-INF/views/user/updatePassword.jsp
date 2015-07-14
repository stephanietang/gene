<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<form:form class="form-horizontal" commandName="updatePasswordForm">
	<div id="updatePwd_error" style="display:none;" class="alert alert-warning" role="alert"></div>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label"><spring:message code="label.updatePwd.email" /></label>
		<div class="col-sm-10"><p class="form-control-static">${updatePasswordForm.email}</p></div>
		<form:hidden id="email" path="email"/>
	</div>
	<div class="form-group">
		<label for="oldPassword" class="col-sm-2 control-label"><spring:message code="label.updatePwd.oldPassword" /></label>
		<div class="col-sm-10"><form:password path="oldPassword" class="form-control"/></div>
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
	<div class="jumbotron" id="updatePasswordDiv">
		<h3><spring:message code="msg.user.updatePassword.success" /></h3>
		<h4><spring:message code="msg.user.updatePassword.logout" /></h4>
		<p><a href="${contextPath}/logout"><spring:message code="menu.logout" /></a></p>
	</div>
</div>

<spring:message code="label.updatePwd.success" var="updatePwdSuccessTitle"/>
<script>
var updatePwdsuccessTitle = "${updatePwdSuccessTitle}";
$(document).ready(function() {
	$("#updatePasswordForm").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			oldPassword: {
				required: true,
				minlength: 4
			},
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
			var oldPassword = $('#oldPassword').val();
			var newPassword = $('#newPassword').val();

		    var json = JSON.stringify({email:email, oldPassword:oldPassword,newPassword:newPassword});
		    $.ajax({
		    	url: ctx + "/update_password.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == '200'){
		        		$.colorbox({inline:true, href:$("#updatePasswordDiv"),title: updatePwdsuccessTitle});
		        		setCountdown(4,'updatePasswordDiv h4 span',ctx+"/logout");
		        	}else{
		        		$("#updatePwd_error").text(result.message).show();
		        	}
		        },
		        error : function(){
		            $(this).html("Error!");
		        }
		    });
		}
	});
	
	$("#oldPassword").focus(function() {
		$('#updatePwd_error').text('').hide();
	});
	
});
</script>
<%@ include file="../common/footer.jsp" %>
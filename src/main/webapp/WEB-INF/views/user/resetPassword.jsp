<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<div class="container">
<form:form class="form-horizontal" commandName="resetPasswordForm">
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
    		<spring:message code="button.save" var="saveButton"/>
			<input type="submit" class="btn btn-primary" value="${saveButton}">
		</div>
	</div>
</form:form>

<div style="display:none"> 
	<div class="jumbotron" id="resetPasswordDiv">
		
	</div>
</div>
</div>

<div id="modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="title.user.resetPassword.success" /></h4>
	      	</div>
			<div class="modal-body">
				<div id="countdown-wrapper">
					<h3><spring:message code="msg.user.resetPassword.success" /></h3>
					<h4><spring:message code="msg.user.resetPassword.login" /></h4>
					<p><a href="<c:url value="/login" />"><spring:message code="menu.login" /></a></p>
				</div>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>

<script>
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
			csrfAjaxSetup();
			
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
		        	if(result.status == 'success'){
		        		$('#modal').modal('show');
		        		setCountdown(4,'countdown-wrapper h4 span',redirectIndex);
		        	}else if(result.status == 'error'){
		        		displayErrorList(result);
		        	}
		        },
		        error : function(){
		        	$("#errorMessage").text("Error!").show();
		        }
		    });
		}
	});
	
});
</script>
<%@ include file="../common/footer.jsp" %>
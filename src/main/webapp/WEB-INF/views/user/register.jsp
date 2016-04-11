<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<div class="container">
<form:form class="form-horizontal" method="post" commandName="registerForm">
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label"><spring:message code="label.common.email" /></label>
		<div class="col-sm-10"><form:input path="email" class="form-control" placeholder="example@example.com" /></div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label"><spring:message code="label.common.password" /></label>
		<div class="col-sm-10"><form:password path="password" class="form-control" placeholder="password" /></div>
	</div>
	<div class="form-group">
    	<div class="col-sm-offset-2 col-sm-10">
    		<spring:message code="button.register" var="registerButton"/>
			<input type="submit" class="btn btn-primary" value="${registerButton}">
		</div>
	</div>
	
</form:form>
</div>

<form:form id="confirm-form" method="post" commandName="registerForm">
	<form:hidden id="hidden-email" path="email" />
</form:form>

<script>
jQuery(document).ready(function() { 
	$("#registerForm").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			password: {
				required: true,
				minlength: 4
			},
			email: {
				required: true,
				cemail: true
			}
		},
		submitHandler:function(form){
			
			csrfAjaxSetup();
			
			var email = $('#email').val();
			var password = $('#password').val();
		    var json = JSON.stringify({email:email, password:password});
		    
		    $.ajax({
		    	url: ctx + "/register.json",
		    	type: "POST",
		    	contentType: "application/json; charset=utf-8",
		    	dataType: "json",
		        data: json,
		        success : function(result){
		        	if(result.status == 'success'){
		        		$('#hidden-email').val(email);
		        		$('#confirm-form').prop('action', ctx+"/confirm_mail");
		        		$('#confirm-form').submit();
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
	
	$("#email").focus(function() {
		resetGlobalMessage();
	});
});
</script>
<%@ include file="../common/footer.jsp" %>
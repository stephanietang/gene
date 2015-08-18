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
    		<spring:message code="button.user.register" var="registerButton"/>
			<input type="submit" class="btn btn-primary" value="${registerButton}">
		</div>
	</div>
	
</form:form>
</div>

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
			var email = $('#email').val();
			var password = $('#password').val();
		    var json = JSON.stringify({email:email, password:password});
		    
		    $.ajax({
		    	url: ctx + "/register.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == '200'){
		        		$(location).attr('href',ctx+"/confirm_mail?email="+email); 
		        	}else{
		        		$("#errorMessage").text(result.message).show();
		        	}
		        },
		        error : function(){
		        	$("#errorMessage").text("Error!").show();
		        }
		    });
		}
	});
	
	$("#email").focus(function() {
		$('#errorMessage').text("").hide();
	});
});
</script>
<%@ include file="../common/footer.jsp" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<form:form class="form-horizontal" method="post" commandName="loginForm">
	<div id="login_error" style="display:none;" class="alert alert-warning" role="alert"></div>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label"><spring:message code="label.common.email" /></label>
		<div class="col-sm-10"><form:input path="email" class="form-control" placeholder="example@example.com" /></div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label"><spring:message code="label.common.password" /></label>
		<div class="col-sm-10"><form:password path="password" class="form-control" placeholder="password"/></div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<div class="checkbox">
				<label><input id="rememberMe" type="checkbox" name="rememberMe"/><spring:message code="label.common.rememberMe" /></label>
			</div>
		</div>
	</div>
	<div class="form-group">
    	<div class="col-sm-offset-2 col-sm-10">
    		<spring:message code="button.user.login" var="loginButton"/>
			<input type="submit" class="btn btn-primary" value="${loginButton}"></input>
		</div>
	</div>
</form:form>	
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
			var email = $('#email').val();
			var password = $('#password').val();
			var rememberMe = false;
			if($("#rememberMe").is(':checked')){
				rememberMe = true;
			}
		    var json = JSON.stringify({email:email, password:password, rememberMe: rememberMe});
		    $.ajax({
		    	url: ctx + "/login.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == '200'){
		        		$(location).attr('href',ctx + "/index"); 
		        	}else{
		        		$("#login_error").text(result.message).show();
		        	}
		        },
		        error : function(){
		            $(this).html("Error!");
		        }
		    });
		}
	});
	
	$("#email").focus(function() {
		$('#login_error').text('').hide();
	});
	
	$("#password").focus(function() {
		$('#login_error').text('').hide();
	});
});
</script>

<%@ include file="../common/footer.jsp" %>
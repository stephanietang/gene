<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<form class="form-horizontal" id="forgetPasswordForm">
	<div class="jumbotron">
		<h2><spring:message code="title.user.resetPassword" /></h2>
		<div id="forgetPassword_error" style="display:none;" class="alert alert-warning" role="alert"></div>
		<div class="form-group">
			<label for="email" class="col-sm-2 control-label"><spring:message code="label.common.email" /></label>
			<div class="col-sm-10"><input id="email" name="email" placeholder="example@example.com" class="form-control" value="${email}"/></div>
		</div>
		<div class="form-group">
	    	<div class="col-sm-offset-2 col-sm-10">
	    		<spring:message code="button.user.resetPassword" var="forgetPassword"/>
				<input type="submit" class="btn btn-primary" value="${forgetPassword}"></input>
			</div>
		</div>
	</div>
</form>

<div style="display:none">
	<div class="well" id="forgetPasswordDiv">
		<h2><spring:message code="title.user.resetPassword" /></h2>
		<p>我们已经将重置密码的邮件发送至邮箱: <a id="mail-domain" href=""></a></p>
		<p><a class="btn btn-lg btn-primary" href="" role="button">点击进入邮箱重置密码</a></p>
	</div>
</div>

<script>
jQuery(document).ready(function() { 
	$("#forgetPasswordForm").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			email: {
				required: true,
				cemail: true
			}
		},
		submitHandler:function(form){
			var email = $('#email').val();
		    var json = JSON.stringify({email:email});
		    $.ajax({
		    	url: ctx + "/forget_password.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == '200'){
		        		var mailDomain = result.data.mailDomain;
		        		var email = result.data.email;
		        		$("#forgetPasswordDiv #mail-domain").attr("href", mailDomain).text(email);
		        		$("#forgetPasswordDiv .btn").attr("href", mailDomain);
		        		$.colorbox({inline:true, href:$("#forgetPasswordDiv"),title: "重置密码"});
		        		
		        	}else{
		        		$("#forgetPassword_error").text(result.message).show();
		        	}
		        },
		        error : function(){
		            $(this).html("Error!");
		        }
		    });
		}
	});
	
	$("#email").focus(function() {
		$('#forgetPassword_error').text('').hide();
	});
	
});
</script>

<%@ include file="../common/footer.jsp" %>
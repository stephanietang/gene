<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<form class="form-horizontal" id="sendEmailForm">
	<div class="jumbotron">
		<h2>重新发送激活邮件</h2>
		<div id="sendEmail_error" style="display:none;" class="alert alert-warning" role="alert"></div>
		<div class="form-group">
			<label for="email" class="col-sm-2 control-label"><spring:message code="label.common.email" /></label>
			<div class="col-sm-10"><input id="email" name="email" placeholder="example@example.com" class="form-control" value="${email}"/></div>
		</div>
		<div class="form-group">
	    	<div class="col-sm-offset-2 col-sm-10">
	    		<spring:message code="button.user.sendVerifyEmail" var="sendVerifyEmail"/>
				<input type="submit" class="btn btn-primary" value="${sendVerifyEmail}"></input>
			</div>
		</div>
	</div>
</form>

<div style="display:none">
	<div class="well" id="sendEmailDiv">
		<h2><spring:message code="msg.user.verifyConfirm" /></h2>
		<p>我们已经将验证邮件发送至邮箱: <a id="mail-domain" href=""></a></p>
		<p><a class="btn btn-lg btn-primary" href="" role="button">点击进入邮箱激活</a></p>
	</div>
</div>

<script>
jQuery(document).ready(function() { 
	$("#sendEmailForm").validate({
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
		    	url: ctx + "/send_mail.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == '200'){
		        		var mailDomain = result.data.mailDomain;
		        		var email = result.data.email;
		        		$("#sendEmailDiv #mail-domain").attr("href", mailDomain).text(email);
		        		$("#sendEmailDiv .btn").attr("href", mailDomain);
		        		$.colorbox({inline:true, href:$("#sendEmailDiv"),title: "重新发送激活邮件"});
		        		
		        	}else{
		        		$("#sendEmail_error").text(result.message).show();
		        	}
		        },
		        error : function(){
		            $(this).html("Error!");
		        }
		    });
		}
	});
	
	$("#email").focus(function() {
		$('#sendEmail_error').text('').hide();
	});
	
});
</script>

<%@ include file="../common/footer.jsp" %>
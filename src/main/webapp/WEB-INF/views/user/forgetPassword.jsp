<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<div class="container">
<form class="form-horizontal" id="forgetPasswordForm">
	<div class="jumbotron">
		<h2><spring:message code="title.user.resetPassword" /></h2>
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

</div>

<div id="modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">重置密码</h4>
	      	</div>
			<div class="modal-body">
				<h2><spring:message code="title.user.resetPassword" /></h2>
				<p>我们已经将重置密码的邮件发送至邮箱: <a id="mail-domain" href=""></a></p>
				<p><a class="btn btn-lg btn-primary" href="" role="button">点击进入邮箱重置密码</a></p>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
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
		        		$("#modal #mail-domain").attr("href", mailDomain).text(email);
		        		$("#modal .btn").attr("href", mailDomain);
		        		$('#modal').modal('show');
		        		
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
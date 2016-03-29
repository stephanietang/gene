<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<div class="container">
<form class="form-horizontal" id="sendEmailForm">
	<div class="jumbotron">
		<h2><spring:message code="title.user.sendVerifyEmail" /></h2>
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

</div>

<div id="modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="title.user.sendVerifyEmail" /></h4>
	      	</div>
			<div class="modal-body">
				<p>我们已经将验证邮件发送至邮箱: <a id="mail-domain" href="" target="_blank"></a></p>
				<p><a class="btn btn-lg btn-primary" href="" role="button" target="_blank">点击进入邮箱激活</a></p>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
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
			csrfAjaxSetup();
			
			var email = $('#email').val();
		    var json = JSON.stringify({email:email});
		    $.ajax({
		    	url: ctx + "/send_mail.json",
		    	type: "post",
		    	contentType: "application/json; charset=utf-8",
		        dataType : 'json',
		        data: json,
		        success : function(result){
		        	if(result.status == 'success'){
		        		var mailDomain = result.data.mailDomain;
		        		var email = result.data.email;
		        		$("#modal #mail-domain").attr("href", mailDomain).text(email);
		        		$("#modal .btn").attr("href", mailDomain);
		        		$('#modal').modal('show');
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
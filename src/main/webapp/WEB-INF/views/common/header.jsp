<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>
<%@ page import="com.bolehunt.gene.common.AppBeans" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!-- Development version: Force the browser the retrieve new version of css and js-->
<c:set var="version"><%= java.util.UUID.randomUUID() %></c:set>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
	<title>BoleHunt</title>
	
	<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-3.3.5.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-theme-3.3.5.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/docs.min.css" />
	<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-datepicker3.standalone.min.css" />
	<link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-markdown.min.css" />
	<link rel="stylesheet" href="${contextPath}/resources/css/fileinput.css" />
	<link rel="stylesheet" href="${contextPath}/resources/css/jquery.fileupload-9.12.1.css" />
	<link rel="stylesheet" href="${contextPath}/resources/css/main.css?v=${version}">
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<script>var ctx = "${contextPath}"</script>
	<script src="${contextPath}/resources/js/jquery-1.11.3.min.js"></script>
	<script src="${contextPath}/resources/js/jquery.validate-1.14.0.min.js"></script>
	<script src="${contextPath}/resources/js/additional-methods-1.14.0.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap-3.3.5.min.js"></script>
	<script src="${contextPath}/resources/js/docs.min.js"></script>
	<script src="${contextPath}/resources/js/additional-methods.js"></script>
	<script src="${contextPath}/resources/js/message_zh.js"></script>
	<script src="${contextPath}/resources/js/bootstrap-datepicker.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap-markdown.js"></script>
	<script src="${contextPath}/resources/js/markdown.js"></script>
	<script src="${contextPath}/resources/js/to-markdown.js"></script>
	
	<!-- jquery file upload support -->
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/vendor/jquery.ui.widget.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/jquery.fileupload.js"></script>
	
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/load-image.all.min.js"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/canvas-to-blob.min.js"></script>
	<!-- The File Upload processing plugin -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/jquery.fileupload-process.js"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/jquery.fileupload-image.js"></script>
	<!-- The File Upload validation plugin -->
	<script src="${contextPath}/resources/js/jquery-file-upload-9.12.1/jquery.fileupload-validate.js"></script>
	
	
	<script src="${contextPath}/resources/js/main.js?v=${version}"></script>
</head>
<body>
	<a id="skippy" class="sr-only sr-only-focusable" href="#content"><div class="container"><span class="skiplink-text">Skip to main content</span></div></a>
	<!-- master nav -->
	<header class="navbar navbar-static-top bs-docs-nav" id="top" role="banner">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Logo</a>
	        </div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="${contextPath}/index"><spring:message code="menu.index" /></a></li>
					<sec:authorize access="hasRole('ROLE_TALENT')">
						<li><a href="${contextPath}/talent/profile"><spring:message code="menu.myResume" /></a></li>
					</sec:authorize>
					<li><a href="http://v3.bootcss.com/css/#code-block">Bootstrap</a></li>
					<c:choose><c:when test="${pageContext.request.userPrincipal.name != null}">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="menu.accountSetting" /> <span class="caret"></span></a>
						<ul class="dropdown-menu">
              				<li><a href="<c:url value="/updatePassword" />"><spring:message code="menu.updatePassword" /></a></li>
              				<li><a href="#"><spring:message code="menu.alertSetting" /></a></li>
              				<li><a href="#"><spring:message code="menu.postFilter" /></a></li>
              				<li><a href="#"><spring:message code="menu.messageList" /></a></li>
              				<li class="divider"></li>
              				<li><a href="javascript:logoutSubmit()"><spring:message code="menu.logout" /></a></li>
						</ul>
					</li>
					</c:when><c:otherwise>
					<li><a href="<c:url value="/login" />"><spring:message code="menu.login" /></a></li>
					<li><a href="<c:url value="/register" />"><spring:message code="menu.register" /></a></li>
					</c:otherwise></c:choose>
				</ul>
			</div>
		</div>
	</header>
	
	<form:form action="logout" method="post" id="logoutForm"></form:form>
	
	<div id="errorMessage" class="col-md-offset-2 col-md-8 alert alert-danger" role="alert" style="display:none">${errorMessage}</div>
	<div id="msg" class="col-md-offset-2 col-md-8 alert alert-info" role="alert" style="display:none">${msg}</div>

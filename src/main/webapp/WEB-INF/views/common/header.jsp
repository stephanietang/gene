<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="${contextPath}/resources/css/colorbox.css" />
	<!-- <link rel="stylesheet" href="${contextPath}/resources/css/main.css"> -->
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src="${contextPath}/resources/js/jquery.colorbox-min.js"></script>
	<script src="${contextPath}/resources/js/additional-methods.js"></script>
	<script src="${contextPath}/resources/js/message_zh.js"></script>
	<!-- <script src="${contextPath}/resources/js/main.js"></script>-->
	<script>var ctx = "${contextPath}"</script>

	<!-- Development version: Force the browser the retrieve new version of css and js-->
	<c:set var="version"><%= java.util.UUID.randomUUID() %></c:set>
	<link rel="stylesheet" href="${contextPath}/resources/css/main.css?v=${version}">
	<script src="${contextPath}/resources/js/main.js?v=${version}"></script>
	
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Logo</a>
	        </div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="${contextPath}/index"><spring:message code="menu.index" /></a></li>
					<li><a href="#">我要招人</a></li>
					<li><a href="#">如何使用</a></li>
					<li><a href="http://v3.bootcss.com/css/#code-block">Bootstrap帮助</a></li>
					<c:choose><c:when test="${not baseForm.logined}">
					<li><a href="${contextPath}/login"><spring:message code="menu.login" /></a></li>
					<li><a href="${contextPath}/register"><spring:message code="menu.register" /></a></li>
					</c:when><c:otherwise>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><spring:message code="menu.myResume" /> <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${contextPath}/profile"><spring:message code="menu.myResume" /></a></li>
							<li><a href="${contextPath}/profile/edit">修改简历</a></li>
              				<li class="divider"></li>
              				<li><a href="${contextPath}/account"><spring:message code="menu.accountSetting" /></a></li>
              				<li><a href="${contextPath}/logout"><spring:message code="menu.logout" /></a></li>
						</ul>
					</li>
					</c:otherwise></c:choose>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container">
		

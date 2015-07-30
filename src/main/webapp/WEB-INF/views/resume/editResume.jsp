<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<!-- Docs page layout -->
<div class="container bs-docs-container">

	<div class="row">
		<div class="col-md-3" role="complementary">
			<nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top">
				<ul class="nav bs-docs-sidenav">
					<li><a href="#basic-info">基本信息</a></li>
					<li><a href="#education">教育经历</a></li>
					<li><a href="#work-experience">工作经历</a></li>
				</ul>
				<a class="back-to-top" href="#top">回到顶部</a>
            
			</nav>
		</div>
		
		
		<div class="col-md-9" role="main">
			<form:form id="resumeForm" class="form-horizontal" method="post" action="${contextPath}/profile/edit" commandName="resumeForm">
			<div class="bs-docs-section">
				<form:hidden path="basicInfo.userId" />
				<h1 class="page-header">${resumeForm.basicInfo.name}</h1>
				<h2 id="basic-info" class="page-header"><a class="anchorjs-link " href="#basic-info" ></a>基本信息</h2>
				
				<div class="form-group">
					<div class="col-sm-6"><form:input path="basicInfo.name" class="form-control" placeholder="姓名" /></div>
					<div class="col-sm-2"><ct:options list="${AppBeans.countryList}" name="basicInfo.countryId" key="${resumeForm.basicInfo.countryId}"/></div>
					<div class="col-sm-4"><form:input path="basicInfo.telNo" class="form-control" placeholder="手机号码" /></div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><ct:options list="${AppBeans.experienceList}" name="basicInfo.expId" key="${resumeForm.basicInfo.expId}" /></div>
					<div class="col-sm-6"><ct:options list="${AppBeans.degreeList}" name="basicInfo.degreeId" key="${resumeForm.basicInfo.degreeId}" /></div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><ct:options list="${AppBeans.sexList}" name="basicInfo.sexId" key="${resumeForm.basicInfo.sexId}" /></div>
				<div class="col-sm-6">
					<div class="input-group date" data-date-format="yyyy-mm" data-date="1985-01">
						<form:input type="text" path="basicInfo.bornYear" class="form-control" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><form:input path="basicInfo.city" class="form-control" placeholder="所在城市" /></div>
					<div class="col-sm-6"><input class="form-control" value="${user.email}" disabled/></div>
				</div>
				<div class="form-group">
			    	<div class="col-sm-10">
			    		<spring:message code="button.user.save" var="saveButton"/>
						<input type="submit" class="btn btn-primary" value="${saveButton}"></input>
					</div>
				</div>
			</div>
			</form:form>
			
			<div class="bs-docs-section">
				<h1 id="education" class="page-header"><a class="anchorjs-link " href="#education" ></a>教育经历</h1>
				<form:form id="educationForm" class="form-horizontal" commandName="educationForm">
				<div class="form-group">
					<label for="schoolName" class="col-sm-2 control-label">学校名称</label>
					<div class="col-sm-10"><form:input path="schoolName" class="form-control" placeholder="example@example.com" /></div>
				</div>
				<div class="form-group">
					<label for="schoolName" class="col-sm-2 control-label">学历</label>
					<div class="col-sm-10"><ct:options list="${AppBeans.degreeList}" name="degreeId" key="${educationForm.degreeId}" /></div>
				</div>
				<div class="form-group">
					<spring:message code="button.user.add" var="addButton"/>
					<input type="submit" class="btn btn-primary" value="${addButton}"></input>
				</div>
				</form:form>
				<c:forEach var="education" items="${resumeForm.educationList}" varStatus="item"> 
					<div class="bs-callout bs-callout-info">
						<h2>${education.schoolName}</h2>
						<p>${education.department}</p>
						<p><ct:label list="${AppBeans.degreeList}" key="${education.degreeId}"/></p>
						<p>${education.startYear}~${education.endYear}</p>
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
						<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
						<input type="hidden" name="educationId_${item.index}" value="${education.id}"/>
					</div>
				</c:forEach>
			</div>

			<div class="bs-docs-section">
				<h1 id="work-experience" class="page-header"><a class="anchorjs-link " href="#work-experience"></a>工作经验</h1>
				
				<div class="bs-callout bs-callout-info">
					<h2>百度</h2>
					<p class="text-left">Java工程师</p>
					<p class="text-left">2013~2014</p>
				</div>
				<div class="bs-callout bs-callout-info">
					<h2>谷歌</h2>
					<p class="text-left">Java工程师</p>
					<p class="text-left">2009~2013</p>
				</div>
			</div>
		</div>

	</div>
</div>

	
<script>
jQuery(document).ready(function() { 
	
	$('.input-group.date').datepicker({
		format: "yyyy",
		startView: "years",
		minViewMode: "years",
		startDate: new Date(1950, 00, 01),
		endDate: "-20y",
		autoclose: true
	});
	
	$("#resumeForm").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			"basicInfo.name": {
				required: true
			},
			"basicInfo.telNo": {
				required: true,
				digits: true
			},
			"basicInfo.city": {
				required: true
			}
		}
	});

	
});
</script>

<%@ include file="../common/footer.jsp" %>
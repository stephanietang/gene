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
			<div class="bs-docs-section">
				<h1 class="page-header">${resumeForm.basicInfo.name}</h1>
				<h2 id="basic-info" class="page-header"><a class="anchorjs-link " href="#basic-info" ></a>基本信息</h2>
				<div>
					<ct:label list="${AppBeans.sexList}" key="${resumeForm.basicInfo.sex}"/> | 
        			<ct:label list="${AppBeans.countryList}" key="${resumeForm.basicInfo.country}"/> |
        			<ct:label list="${AppBeans.degreeList}" key="${resumeForm.basicInfo.degree}"/> | 
        			<ct:label list="${AppBeans.experienceList}" key="${resumeForm.basicInfo.experience}"/> | 
        			${user.email} 
				</div>
				<div class="bs-callout bs-callout-warning">
					<ul>
						<li>目前所在城市</li>
						<li>期望工作地点</li>
						<li>最擅长技能</li>
					</ul>
				</div>
			</div>
			
			<div class="bs-docs-section">
				<h1 id="education" class="page-header"><a class="anchorjs-link " href="#education" ></a>教育经历</h1>
				<c:forEach var="education" items="${resumeForm.educationList}"> 
					<div class="bs-callout bs-callout-info">
						<h2>${education.schoolName}</h2>
						<p>${education.department}</p>
						<p><ct:label list="${AppBeans.degreeList}" key="${education.degreeId}"/></p>
						<p>${education.startYear}~${education.endYear}</p>
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

<%@ include file="../common/footer.jsp" %>
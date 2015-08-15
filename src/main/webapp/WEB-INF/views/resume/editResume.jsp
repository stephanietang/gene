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
					<div class="col-sm-2"><ct:options list="${AppBeans.countryList}" name="basicInfo.country" key="${resumeForm.basicInfo.country}"/></div>
					<div class="col-sm-4"><form:input path="basicInfo.telNo" class="form-control" placeholder="手机号码" /></div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><ct:options list="${AppBeans.experienceList}" name="basicInfo.experience" key="${resumeForm.basicInfo.experience}" /></div>
					<div class="col-sm-6"><ct:options list="${AppBeans.degreeList}" name="basicInfo.degree" key="${resumeForm.basicInfo.degree}" /></div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><ct:options list="${AppBeans.sexList}" name="basicInfo.sex" key="${resumeForm.basicInfo.sex}" /></div>
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
					<div class="col-sm-10"><form:input path="schoolName" class="form-control" placeholder="请填写学校" /></div>
				</div>
				<div class="form-group">
					<label for="schoolName" class="col-sm-2 control-label">学历</label>
					<div class="col-sm-10"><ct:options list="${AppBeans.degreeList}" name="degreeId" key="${educationForm.degreeId}" /></div>
				</div>
				<div class="form-group">
					<spring:message code="button.user.add" var="addButton"/>
					<button type="button" class="btn btn-primary" onClick="educationCrudAction('add','')">${addButton}</button>
				</div>
				</form:form>
				<c:forEach var="education" items="${resumeForm.educationList}" varStatus="item"> 
					<div class="bs-callout bs-callout-info text" id="education_text_${item.index}">
						<h2>${education.schoolName}</h2>
						<p>${education.department}</p>
						<p><ct:label list="${AppBeans.degreeList}" key="${education.degreeId}"/></p>
						<p>${education.startYear}~${education.endYear}</p>
						<button type="button" class="btn btn-primary" onClick="showEducationEditBox(this,${item.index})"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</button>
						<button type="button" class="btn btn-primary" onClick="educationCrudAction('delete',${item.index})"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</button>
						<input id="education_id_${item.index}" type="hidden" value="${education.id}"/>
					</div>
					<div class="form-horizontal editBox" id="education_edit_${item.index}">
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label">学校名称</label>
							<div class="col-sm-10"><input id="school_name_${item.index}" value="${education.schoolName}" class="form-control" placeholder="请填写学校" /></div>
						</div>
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label">专业</label>
							<div class="col-sm-10"><input value="${education.department}" class="form-control" placeholder="请填写专业" /></div>
						</div>
						<button type="button" class="btn btn-primary" onClick="educationCrudAction('save',${item.index})">Save</button>
						<button type="button" class="btn btn-primary" onClick="cancelEducationEdit(${item.index})">Cancel</button>
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
	
	$(".input-group.date").datepicker({
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

function showEducationEditBox(editobj, id){
	$("#education_text_" + id).hide();
	$("#education_edit_" + id).show();
}

function cancelEducationEdit(id){
	$("#education_text_" + id).show();
	$("#education_edit_" + id).hide();
}

function educationCrudAction(action,id) {
	var queryString;
	//TODO
	switch(action) {
		case "add":
			queryString = 'action='+action+'&school_name='+ $('input[name="schoolName"]').val();
		break;
		case "save":
			queryString = 'action='+action+'&education_id='+ $("education_id_"+id).val() + '&school_name='+ $("#school_name_"+id).val();
		break;
		case "delete":
			queryString = 'action='+action+'&education_id='+ $("education_id_"+id).val();
		break;
	}
	jQuery.ajax({
		url: "educationCrudAction",
		data: queryString,
		type: "POST",
		success:function(data){
			switch(action) {
				case "add":
					
				break;
				case "save":
						
				break;
				case "delete":
					
				break;
			}
		},
		error:function (){}
	});
}
</script>

<%@ include file="../common/footer.jsp" %>
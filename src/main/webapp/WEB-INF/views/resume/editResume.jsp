<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<script src="${contextPath}/resources/js/fileupload.js"></script>

<spring:message code="button.save" var="saveButton"/>
<spring:message code="button.cancel" var="cancelButton"/>
<spring:message code="button.edit"  var="editButton"/>
<!-- Docs page layout -->
<div class="container bs-docs-container">

	<div class="row">
		<%-- <div class="col-md-3" role="complementary">
			<nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top">
				<ul class="nav bs-docs-sidenav">
					<li><a href="#basic-info"><spring:message code="label.resume.basicInfo" /></a></li>
					<li><a href="#education"><spring:message code="label.resume.educationExperience" /></a></li>
				</ul>
				<a class="back-to-top" href="#top"><spring:message code="label.common.goToTop" /></a>
            
			</nav>
		</div> --%>
		
		<input type="hidden" id="basicInfoId" value="${resumeForm.basicInfo.id}" />
		<div class="col-md-8" role="main">
			<div class="row">
				<div class="avatar-container">
					<c:choose>
					<c:when test="${not empty resumeForm.avatar}">
						<img id="avatar-img" src="${contextPath}/image/${resumeForm.avatar.uuid}"/>
					</c:when>
					<c:otherwise>
						<img id="avatar-img" src="${contextPath}/resources/img/defaultAvatar.png"/>
					</c:otherwise>
					</c:choose>
					<div class="avatar-shadow"><img src="${contextPath}/resources/img/shadow.png"></div>
					<input id="fileupload" type="file" name="avatar" >
				</div>
			</div>
			
			<div class="row mr-blk-name">
				<div class="col-md-8 col-md-offset-2">
					<p class="mr-name">${resumeForm.basicInfo.name}</p>
				</div>
  				<div class="col-md-2 mr-button hidden">
  					<input type="button" class="btn btn-default btn-xs edit" value="${editButton}" data-name="${resumeForm.basicInfo.name}">
  				</div>
			</div>
			
			<div class="row">
				<form id="name-form" class="mr-edit-name hidden">
					<div class="col-md-8 col-md-offset-2">
						<input class="form-control mr-name" name="name" placeholder="" autocomplete="off"/>
					</div>
					<div class="col-md-2 mr-button">
						<input type="button" class="btn btn-default btn-xs save" value="${saveButton}">
						<input type="button" class="btn btn-default btn-xs cancel" value="${cancelButton}">
					</div>
				</form>
			</div>
			
			<div class="row mr-blk-info">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="mr-info">
						<span><ct:label list="${sexList}" key="${resumeForm.basicInfo.sex}"/></span>
						<span>${resumeForm.basicInfo.bornYear}</span>
						<span><ct:label list="${degreeList}" key="${resumeForm.basicInfo.degree}"/></span>
						<span><ct:label list="${workExpList}" key="${resumeForm.basicInfo.experience}"/></span>
						<span><ct:label list="${cityList}" key="${resumeForm.basicInfo.city}"/></span>
      				</div>
      				<div class="mr-info">
						<span>${resumeForm.basicInfo.telNo}</span>
						<span>${user.email}</span>
					</div>
				</div>
				<div class="col-md-2 mr-button">
					<input type="button" class="btn btn-default btn-xs edit" value="${editButton}">
				</div>
			</div>
			
			<div class="row">
				<form id="resume-form" class="form-horizontal mr-edit-info hidden">
					<div class="form-group">
						<div class="col-md-6">
							<label for="sex">Sex</label>
							<select class="form-control" id="sex">
								<c:forEach var="l" items="${sexList}">
									<option value="${l.labelKey}">${l.labelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label for="birthYear">Birth Year</label>
							<select class="form-control" id="birthYear">
								<c:forEach var="l" items="${sexList}">
									<option value="${l.labelKey}">${l.labelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label for="degree">Degree</label>
							<select class="form-control" id="degree">
								<c:forEach var="l" items="${degreeList}">
									<option value="${l.labelKey}">${l.labelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label for="workExp">Experience</label>
							<select class="form-control" id="workExp">
								<c:forEach var="l" items="${workExpList}">
									<option value="${l.labelKey}">${l.labelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label for="city">City</label>
							<select class="form-control" id="city">
								<c:forEach var="l" items="${cityList}">
									<option value="${l.labelKey}">${l.labelName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label for="telNo">Telephone</label>
							<input class="form-control" placeholder="" id="telNo" autocomplete="off"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6">
							<label for="email">Email</label>
							<input class="form-control" id="email" value="${user.email}" disabled/>
						</div>
					</div>
					<div class="form-group">
				    	<div class="col-md-12">
							<input type="button" class="btn btn-default btn-sm save" value="${saveButton}"></input>
							<input type="button" class="btn btn-default btn-sm cancel" value="${cancelButton}"></input>
						</div>
					</div>
				</form>
			</div>
			
			<div id="education-section" class="bs-docs-section" >
				<h4 class="page-header"><a class="anchorjs-link" href="#education" ></a><spring:message code="label.resume.educationExperience" /></h4>
				<form:form id="eduAddForm" class="form-horizontal hidden" commandName="educationForm">
					<div class="form-group">
						<label for="schoolName" class="col-sm-2 control-label"><spring:message code="label.resume.school" /></label>
						<div class="col-sm-10"><input name="schoolName" class="form-control" placeholder="<spring:message code="label.resume.school.placeholder" />" /></div>
					</div>
					<div class="form-group">
						<label for="schoolName" class="col-sm-2 control-label"><spring:message code="label.resume.degree" /></label>
						<div class="col-sm-10"><%-- <ct:options list="${degreeList}" name="degree" /> --%></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="label.resume.timeRange" /></label>
						<div class="col-sm-10">
							<div class="input-daterange">
								<input type="text" name="startYear" class="input-small year-date" readonly />
						    	<span class="add-on"><spring:message code="label.resume.timeRangeTo" /></span>
						    	<input type="text" name="endYear" class="input-small year-date" readonly />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="department" class="col-sm-2 control-label"><spring:message code="label.resume.department" /></label>
						<div class="col-sm-10"><input name="department" class="form-control" placeholder="<spring:message code="label.resume.department.placeholder" />" /></div>
					</div>
					<div class="form-group">
						<spring:message code="button.add" var="addButton"/>
						<div class="btn btn-primary edu-add" >${addButton}</div>
					</div>
				</form:form>
				
				<div id="education-items-container">
				<c:forEach var="education" items="${resumeForm.educationList}" varStatus="item"> 
					<div class="education-item" id="education-item_${item.index}">
						<div class="bs-callout bs-callout-info text-container">
							<h2>${education.schoolName}</h2>
							<p>${education.department}</p>
							<p><ct:label list="${degreeList}" key="${education.degree}"/></p>
							<p>${education.startYear}~${education.endYear}</p>
							<div class="btn btn-primary edu-edit" data-eduid="${education.id}" data-schoolname="${education.schoolName}" data-degree="${education.degree}" data-department="${education.department}" data-startyear="${education.startYear}" data-endyear="${education.endYear}"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</div>
							<div class="btn btn-primary edu-delete" data-toggle="modal" data-target="#modal" data-eduid="${education.id}">
								<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
							</div>
						</div>
					</div>
				</c:forEach>
				</div>
				
				<div id="edu-hidden-template" class="hidden">
					<form class="form-horizontal">
						<input type="hidden" id="eduId" name="eduId" value="" />
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label"><spring:message code="label.resume.school" /></label>
							<div class="col-sm-10"><input name="schoolName" class="form-control" placeholder="<spring:message code="label.resume.school.placeholder" />" /></div>
						</div>
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label"><spring:message code="label.resume.degree" /></label>
							<div class="col-sm-10">
								<select name="degree" class="form-control">
									<c:forEach items="${degreeList}" var="d">
										<option value="${d.labelKey}">${d.labelName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message code="label.resume.timeRange" /></label>
							<div class="col-sm-10">
								<div class="input-daterange">
									<input type="text" name="startYear" class="input-small year-date" readonly />
							    	<span class="add-on"><spring:message code="label.resume.timeRangeTo" /></span>
							    	<input type="text" name="endYear" class="input-small year-date" readonly />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="department" class="col-sm-2 control-label"><spring:message code="label.resume.department" /></label>
							<div class="col-sm-10"><input name="department" class="form-control" placeholder="<spring:message code="label.resume.department.placeholder" />" /></div>
						</div>
						<div class="form-group">
							<div class="btn btn-primary edu-save" >${saveButton}</div>
							<div class="btn btn-primary edu-edit-cancel" >${cancelButton}</div>
						</div>
					</form>
				</div>
			</div>
		</div>

	</div>
</div>

<div id="modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="title.resume.confirmDelete" /></h4>
	      	</div>
			<div class="modal-body">
				<input type="hidden" id="modal-item-id" >
				<input type="hidden" id="modal-refer-item-id" >
				<button type="button" class="btn btn-default delete-ok" data-dismiss="modal"><spring:message code="button.confirmDelete" /></button>
				<button type="button" class="btn btn-default delete-cancel" data-dismiss="modal"><spring:message code="button.cancel" /></button>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>

<div id="avatar-modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="title.upload.avatar" /></h4>
	      	</div>
			<div class="modal-body">
				<div id="avatar-container"></div>
				<button type="button" class="btn btn-default upload-ok" data-dismiss="modal"><spring:message code="button.upload" /></button>
				<button type="button" class="btn btn-default upload-cancel" data-dismiss="modal"><spring:message code="button.cancel" /></button>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>

<div id="avatar-error-modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><spring:message code="title.error" /></h4>
	      	</div>
			<div class="modal-body">
				<spring:message code="errMsg.upload.avatar.notSupport" />
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>
	
<script src="${contextPath}/resources/js/resume.js"></script>

<%@ include file="../common/footer.jsp" %>
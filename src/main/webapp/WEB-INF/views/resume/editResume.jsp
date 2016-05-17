<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<script src="${contextPath}/resources/js/fileupload.js"></script>

<spring:message code="button.save" var="saveButton"/>
<spring:message code="button.cancel" var="cancelButton"/>
<spring:message code="button.edit"  var="editButton"/>
<spring:message code="button.delete"  var="deleteButton"/>
<spring:message code="button.add"  var="addButton"/>
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
						<span class="sex"><ct:label list="${sexList}" key="${resumeForm.basicInfo.sex}"/></span> · 
						<span class="age">${resumeForm.ageStr}</span> · 
						<span class="degree"><ct:label list="${degreeList}" key="${resumeForm.basicInfo.degree}"/></span> · 
						<span class="workExp"><ct:label list="${workExpList}" key="${resumeForm.basicInfo.workExp}"/></span> · 
						<span class="city"><ct:label list="${cityList}" key="${resumeForm.basicInfo.city}"/></span>
      				</div>
      				<div class="mr-info">
						<span class="telNo" aria-hidden="true">${resumeForm.basicInfo.telNo}</span> · 
						<span>${user.email}</span>
					</div>
				</div>
				<div class="col-md-2 mr-button hidden">
					<input type="button" class="btn btn-default btn-xs edit" value="${editButton}" data-sex="${resumeForm.basicInfo.sex}" 
					data-birthyear="${resumeForm.basicInfo.birthYear}" data-degree="${resumeForm.basicInfo.degree}"
					data-workexp="${resumeForm.basicInfo.workExp}" data-city="${resumeForm.basicInfo.city}"
					data-telno="${resumeForm.basicInfo.telNo}">
				</div>
			</div>
			
			<div class="row">
				<form id="info-form" class="form-horizontal mr-edit-info hidden">
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
								<c:forEach var="l" items="${birthYearList}">
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
							<input class="form-control" placeholder="" id="telNo" autocomplete="off" name="telNo"/>
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
			
			<div class="mr-educations-container">
				<div class="row">
					<div class="col-md-10">
						<h4 class="page-header"><a class="anchorjs-link" href="#education" ></a><spring:message code="label.resume.educationExperience" /></h4>
					</div>
					<div class="col-md-2 add-div">
						<input type="button" class="btn btn-success btn-xs add" value="${addButton}"></input>
					</div>
				</div>
				<div class="mr-educations">
					<c:forEach var="education" items="${resumeForm.educationList}" varStatus="item">
						<div class="mr-education-item" id="edu-${item.index}">
							<div class="row mr-blk-education">
								<div class="col-md-10">
									<p><span class="glyphicon glyphicon-pushpin school-name" aria-hidden="true"> ${education.schoolName}</span></p>
									<p><span class="department">${education.department}</span> · <span class="degree"><ct:label list="${degreeList}" key="${education.degree}"/></span></p>
									<p><span class="time-range">${education.startYear} - ${education.endYear}</span></p>
								</div>
								<div class="col-md-2" >
									<input type="button" class="btn btn-default btn-xs edit" value="${editButton}" 
									 data-eduid="${education.id}" data-schoolname="${education.schoolName}"
									 data-degree="${education.degree}" data-department="${education.department}" 
									 data-startyear="${education.startYear}" data-endyear="${education.endYear}" 
									 data-refid="edu-${item.index}"/>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				
				<div class="row">
					<form id="education-template-form" class="form-horizontal hidden mr-edit-education">
						<input type="hidden" class="edu-id" value="" />
						<input type="hidden" class="edu-ref-id" value="" />
						<div class="form-group">
							<label for="schoolName" class="col-md-2 control-label"><spring:message code="label.resume.school" /></label>
							<div class="col-md-10"><input name="schoolName" class="form-control" autocomplete="off" placeholder="<spring:message code="label.resume.school.placeholder" />" /></div>
						</div>
						<div class="form-group">
							<label for="degree" class="col-md-2 control-label"><spring:message code="label.resume.degree" /></label>
							<div class="col-md-10">
								<select name="degree" class="form-control">
									<c:forEach var="l" items="${degreeList}">
										<option value="${l.labelKey}">${l.labelName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label"><spring:message code="label.resume.timeRange" /></label>
							<div class="col-md-10">
								<div class="input-daterange">
									<input type="text" name="startYear" class="input-small year-date" readonly />
							    	<span class="add-on"><spring:message code="label.resume.timeRangeTo" /></span>
							    	<input type="text" name="endYear" class="input-small year-date" readonly />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="department" class="col-md-2 control-label"><spring:message code="label.resume.department" /></label>
							<div class="col-md-10"><input name="department" class="form-control" autocomplete="off" placeholder="<spring:message code="label.resume.department.placeholder" />" /></div>
						</div>
						<div class="form-group">
							<div class="col-md-10">
								<input type="button" class="btn btn-default btn-sm save" value="${saveButton}"></input>
								<input type="button" class="btn btn-default btn-sm cancel" value="${cancelButton}"></input>
							</div>
							<div class="col-md-2">
								<input type="button" class="btn btn-danger btn-sm delete" value="${deleteButton}"></input>
							</div>
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
				<input type="hidden" id="modal-ref-item-id" >
				<button type="button" class="btn btn-danger btn-sm delete-ok" data-dismiss="modal"><spring:message code="button.confirmDelete" /></button>
				<button type="button" class="btn btn-default btn-sm delete-cancel" data-dismiss="modal"><spring:message code="button.cancel" /></button>
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
				<button type="button" class="btn btn-default btn-sm upload-ok" data-dismiss="modal"><spring:message code="button.upload" /></button>
				<button type="button" class="btn btn-default btn-sm upload-cancel" data-dismiss="modal"><spring:message code="button.cancel" /></button>
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
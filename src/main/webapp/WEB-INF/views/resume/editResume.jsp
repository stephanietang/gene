<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

<!-- Docs page layout -->
<div class="container bs-docs-container">

	<div class="row">
		<div class="col-md-3" role="complementary">
			<nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top">
				<ul class="nav bs-docs-sidenav">
					<li><a href="#basic-info"><spring:message code="label.resume.basicInfo" /></a></li>
					<li><a href="#education"><spring:message code="label.resume.educationExperience" /></a></li>
					<li><a href="#work-experience"><spring:message code="label.resume.workExperience" /></a></li>
					<li><a href="#self-intro"><spring:message code="label.resume.selfIntro" /></a></li>
					<li><a href="#works"><spring:message code="label.resume.works" /></a></li>
				</ul>
				<a class="back-to-top" href="#top"><spring:message code="label.common.goToTop" /></a>
            
			</nav>
		</div>
		
		<div class="col-md-9" role="main">
			<c:if test="${not empty resumeForm.avatar}">
				<img src="${contextPath}/image/${resumeForm.avatar.uuid}"/>
			</c:if>
			<form id="uploadAvatarForm" method="post" action="${contextPath}/uploadAvatar" enctype="multipart/form-data">   
				<input name="avatar" id="avatar-upload" type="file" />
				<input type="submit"  />
				<input type="hidden" name="userId" value="${resumeForm.userId}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
			
			<form:form id="resumeForm" class="form-horizontal" method="post" action="${contextPath}/talent/profile/edit" commandName="resumeForm">
			<div class="bs-docs-section">
				<form:hidden path="basicInfo.id" />
				<form:hidden path="userId" />
				<h1 class="page-header">${resumeForm.basicInfo.name}</h1>
				<h2 id="basic-info" class="page-header"><a class="anchorjs-link " href="#basic-info" ></a><spring:message code="label.resume.basicInfo" /></h2>
				<div class="form-group">
					<div class="col-sm-6"><form:input path="basicInfo.name" class="form-control" placeholder="" /></div>
					<div class="col-sm-2">
						<form:select path="basicInfo.country" class="form-control">
							<form:options items="${countryList}" itemValue="labelKey" itemLabel="labelName" />
						</form:select>
					</div>
					<div class="col-sm-4"><form:input path="basicInfo.telNo" class="form-control" placeholder="" /></div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><ct:options list="${workExpList}" name="basicInfo.experience" /></div>
					<div class="col-sm-6"><ct:options list="${degreeList}" name="basicInfo.degree" /></div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><ct:options list="${sexList}" name="basicInfo.sex" /></div>
				<div class="form-group">
					<div class="col-sm-6">
						<div class="input-group date year-date">
							<form:input type="text" path="basicInfo.bornYear" class="form-control" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						</div>
					</div>
				</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6"><form:input path="basicInfo.city" class="form-control" placeholder="" /></div>
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
			
			<div id="education-section" class="bs-docs-section" >
				<h1 class="page-header"><a class="anchorjs-link " href="#education" ></a><spring:message code="label.resume.educationExperience" /></h1>
				<form:form id="eduAddForm" class="form-horizontal" commandName="educationForm">
					<div class="form-group">
						<label for="schoolName" class="col-sm-2 control-label"><spring:message code="label.resume.school" /></label>
						<div class="col-sm-10"><input name="schoolName" class="form-control" placeholder="<spring:message code="label.resume.school.placeholder" />" /></div>
					</div>
					<div class="form-group">
						<label for="schoolName" class="col-sm-2 control-label"><spring:message code="label.resume.degree" /></label>
						<div class="col-sm-10"><ct:options list="${degreeList}" name="degree" /></div>
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
						<spring:message code="button.user.add" var="addButton"/>
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
				
				<div id="edu-hidden-template" class="hide">
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
							<spring:message code="button.user.save" var="saveButton"/>
							<spring:message code="button.cancel" var="cancelButton"/>
							<div class="btn btn-primary edu-save" >${saveButton}</div>
							<div class="btn btn-primary edu-edit-cancel" >${cancelButton}</div>
						</div>
					</form>
				</div>
			</div>

			<div class="bs-docs-section">
				<h1 id="self-intro" class="page-header"><a class="anchorjs-link " href="#self-intro"></a><spring:message code="label.resume.selfIntro" /></h1>
				
				<form id="selfIntroForm">
					<input name="title" type="text" placeholder="Title?" />
					<textarea name="content" data-provide="markdown" rows="10"></textarea>
					<label class="checkbox">
						<input name="publish" type="checkbox"> Publish
					</label>
			    	<hr/>
			    	<button type="submit" class="btn">Submit</button>
			  	</form> 
			  	
			</div>
			
			<div class="bs-docs-section">
				<h1 id="works" class="page-header"><a class="anchorjs-link " href="#works"></a><spring:message code="label.resume.works" /></h1>
				
	            <label class="control-label">Upload Works</label>
				<input id="works-upload" type="file" name="files" multiple>
				
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
	
<script>
$(document).on('ready', function() {
	
	/* $.validator.setDefaults({
		  debug: true,
		  success: "valid"
		}); */
	
	$("#uploadAvatarForm").validate({
		rules: {
			avatar: {
				required: true,
				accept: "image/*"
			}
		},
		submitHandler:function(form){
			var formData = new FormData($(this)[0]);
			alert('aaaa');
			 $.ajax({
			        url: $(this).attr("action"),
			        type: "POST",
			        data: formData,
			        async: false,
			        cache: false,
			        contentType: false,
			        proccessData: false,
			        success: function() {
			        	alert('bbb');
			        }
			});
		}
	});
	
	
	$("#checkAll").click(function () {
	    $(".check").prop('checked', $(this).prop('checked'));
	});

	
	$(document).on("click", ".year-date", function() {
		$(this).datepicker({
			format: "yyyy",
			startView: "years",
			minViewMode: "years",
			startDate: new Date(1950, 00, 01),
			endDate: "-0y",
			autoclose: true
		}).datepicker('show');
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
	
	$("#eduAddForm").validate({
		rules: {
			schoolName: {
				required: true
			},
			degree : {
				required: true
			},
			department: {
				required: true
			},
			startYear: {
				required: true,
				digits: true
			},
			endYear: {
				required: true,
				digits: true
			}
		}
	});
	
	$("#education-section").on("click",".edu-edit",function(){
		var html = "";
		html = $("#edu-hidden-template").html();
		$(this).parents(".text-container").hide().after(html);
		$(this).parents(".text-container").next().attr("id", "eduEditForm");
		
		var oForm = $("#eduEditForm");
		$("#eduId",oForm).val($(this).data("eduid"));
		$('input[name="schoolName"]',oForm).val($(this).data("schoolname"));
		$('select[name="degree"]',oForm).val($(this).data("degree"));
		$('input[name="startYear"]',oForm).val($(this).data("startyear"));
		$('input[name="endYear"]',oForm).val($(this).data("endyear"));
		$('input[name="department"]',oForm).val($(this).data("department"));
		
		$("#eduAddForm").addClass("disabled");
		
		$(".edu-edit").attr("disabled",true);
		$(".edu-delete").attr("disabled",true);
		
		initValidationEducationForm(oForm);
		
	});
	
	$("#education-section").on("click",".edu-edit-cancel",function(){
		$("#eduEditForm").prev().show();
		$("#eduEditForm").remove();
		
		$("#eduAddForm").removeClass("disabled");
		
		$(".edu-edit").attr("disabled",false);
		$(".edu-delete").attr("disabled",false);
	});
	
	$("#education-section").on("click",".edu-add",function(){
		var oForm = $("#eduAddForm");
		
		var valid = $(oForm).valid();
		
		if(! valid){
			return ;
		}
		
		csrfAjaxSetup();
		
		var json;
		
		json = JSON.stringify({
			action : "add", 
			schoolName : $('input[name="schoolName"]',oForm).val(),
			degree : $('select[name="degree"]',oForm).val(),
			startYear : $('input[name="startYear"]',oForm).val(),
			endYear : $('input[name="endYear"]',oForm).val(),
			department : $('input[name="department"]',oForm).val()
			});
		
		$.ajax({
			url: ctx + "/talent/profile/educationCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=UTF-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == 'success'){
					// clear add form
					$(":input","#eduAddForm")
					  .removeAttr("checked")
					  .removeAttr("selected")
					  .not(":button, :submit, :reset, :hidden, :radio, :checkbox")
					  .val("");
					
					var educationItems = result.data;
					initEducationList(educationItems);
				}else if(result.status == 'error'){
					displayErrorList(result);
				}
			},
			error:function (){
				alert("Error!");
			}
		});
	});
	
	$("#education-section").on("click",".edu-save",function(){
		
		var oForm = $("#eduEditForm");
		
		var valid = $(oForm).valid();
		
		if(! valid){
			return ;
		}
		
		csrfAjaxSetup();
		
		var json;
		
		json = JSON.stringify({
			action : "save", 
			educationId : $("#eduId",oForm).val(),
			schoolName : $('input[name="schoolName"]',oForm).val(),
			degree : $('select[name="degree"]',oForm).val(),
			startYear : $('input[name="startYear"]',oForm).val(),
			endYear : $('input[name="endYear"]',oForm).val(),
			department : $('input[name="department"]',oForm).val()
			});
		
		$.ajax({
			url: ctx + "/talent/profile/educationCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == 'success'){
					var eduItems = result.data;
					initEducationList(eduItems);
				}else if(result.status == 'error'){
					displayErrorList(result);
				}
			},
			error:function (){
				alert("Error!");
			}
		});
		
		$("#eduAddForm").removeClass("disabled");
	});
	
	$("#education-section").on("click",".edu-delete",function(){
		var deleteBtn = $(this);
		var id = $(deleteBtn).data("eduid");
		var referId = $(deleteBtn).parents(".education-item").attr("id");
		$("#modal-item-id").val(id);
		$("#modal-refer-item-id").val(referId);
	});
	
	$(document).on("click",".delete-ok",function(){
		csrfAjaxSetup();
		var referId = $("#modal-refer-item-id").val();
		var referItem = $("#"+referId);
		var json = JSON.stringify({
			action : "delete",
			educationId : $("#modal-item-id").val()
			});
		jQuery.ajax({
			url: ctx + "/talent/profile/educationCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == 'success'){
					$(referItem).remove();
				}else if(result.status == 'error'){
					displayErrorList(result);
				}
			},
			error:function (){
				alert("Error!");
			}
		});
	});
	
});

//validate on newly created form
function initValidationEducationForm(oForm){
	
	$(oForm).validate({
		onfocusin: function(element) { // validate immediately when focus on the input box
	        $(element).valid();
	    },
		rules: {
			schoolName: {
				required: true
			},
			degree : {
				required: true
			},
			department: {
				required: true
			},
			startYear: {
				required: true,
				digits: true
			},
			endYear: {
				required: true,
				digits: true
			}
		}
	});
}

function initEducationList(eduItems){
	$("#education-items-container").empty();
	var html = "";
	for(var i = 0; i < eduItems.length; i++){
		var educationId = eduItems[i].id;
		var schoolName = eduItems[i].schoolName;
		var degree = eduItems[i].degree;
		var department = eduItems[i].department;
		var startYear = eduItems[i].startYear;
		var endYear = eduItems[i].endYear;
		html += '<div class="education-item" id="education-item_'+i+'">';
		html +=		'<div class="bs-callout bs-callout-info text-container">';
		html +=			'<h2>'+schoolName+'</h2>';
		html +=			'<p>'+department+'</p>';
		html +=			'<p>'+getDegreeTxt(degree)+'</p>';
		html +=			'<p>'+startYear+'~'+endYear+'</p>';
		html +=			'<div class="btn btn-primary edu-edit" data-eduid="'+educationId+'" data-schoolname="'+schoolName+'" data-degree="'+degree+'" data-department="'+department+'" data-startyear="'+startYear+'" data-endyear="'+endYear+'"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</div>&nbsp;';
		html +=			'<div class="btn btn-primary edu-delete" data-eduid="'+educationId+'" data-toggle="modal" data-target="#modal" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</div>';
		html +=		'</div>';
		html +=	'</div>';
	}
	$("#education-items-container").append(html);
}


function getDegreeTxt(degree){
	var degreeTxt = "";
	if(degree == 1){
		degreeTxt = "本科";
	}else if(degree == 2){
		degreeTxt = "硕士";
	}else if(degree == 3){
		degreeTxt = "博士";
	}else if(degree == 4){
		degreeTxt = "大专";
	}
	return degreeTxt;
}

</script>

<%@ include file="../common/footer.jsp" %>
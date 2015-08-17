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
				<div class="form-group">
					<div class="col-sm-6">
						<div class="input-group date" data-date-format="yyyy-mm" data-date="1985-01">
							<form:input type="text" path="basicInfo.bornYear" class="form-control" readonly="true" /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
						</div>
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
				<div class="new-form-container">
					<h1 id="education" class="page-header"><a class="anchorjs-link " href="#education" ></a>教育经历</h1>
					<form:form id="educationForm" class="form-horizontal" commandName="educationForm">
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label">学校名称</label>
							<div class="col-sm-10"><input name="schoolName" name="schoolName" class="form-control" placeholder="请填写学校" /></div>
						</div>
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label">学历</label>
							<div class="col-sm-10"><ct:options list="${AppBeans.degreeList}" name="degree" key="${educationForm.degree}" /></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">就读时间</label>
							<div class="col-sm-5">
								<div class="input-group date" data-date-format="yyyy-mm" >
									<input type="text" id="startYear" name="startYear" class="form-control" readonly /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
							<div class="col-sm-5">
								<div class="input-group date" data-date-format="yyyy-mm" >
									<input type="text" id="endYear" name="endYear" class="form-control" readonly /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="department" class="col-sm-2 control-label">专业</label>
							<div class="col-sm-10"><input id="department" name="department" class="form-control" placeholder="请填写专业" /></div>
						</div>
						<div class="form-group">
							<spring:message code="button.user.add" var="addButton"/>
							<button type="button" class="btn btn-primary" onClick="educationCrudAction('add','')">${addButton}</button>
						</div>
					</form:form>
				</div>
				
				<div id="education-experience">
				<c:forEach var="education" items="${resumeForm.educationList}" varStatus="item"> 
					<div class="exp-item">
						<input id="educationId_${item.index}" type="hidden" value="${education.id}" >
						<div class="bs-callout bs-callout-info text-container">
							<h2>${education.schoolName}</h2>
							<p>${education.department}</p>
							<p><ct:label list="${AppBeans.degreeList}" key="${education.degree}"/></p>
							<p>${education.startYear}~${education.endYear}</p>
							<button type="button" class="btn btn-primary" onClick="showEditContainer(this)"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</button>
							<button type="button" class="btn btn-primary" onClick="educationCrudAction('delete',${item.index})"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</button>
						</div>
						<div class="form-horizontal edit-form-container dn">
							<div class="form-group">
								<label for="schoolName" class="col-sm-2 control-label">学校名称</label>
								<div class="col-sm-10"><input name="schoolName_${item.index}" value="${education.schoolName}" class="form-control" placeholder="请填写学校" /></div>
							</div>
							<div class="form-group">
								<label for="schoolName" class="col-sm-2 control-label">学历</label>
								<div class="col-sm-10"><ct:options list="${AppBeans.degreeList}" name="degree_${item.index}" key="${education.degree}" /></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">就读时间</label>
								<div class="col-sm-5">
									<div class="input-group date" data-date-format="yyyy-mm" >
										<input type="text" name="startYear_${item.index}" value="${education.startYear}" class="form-control" readonly /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</div>
								<div class="col-sm-5">
									<div class="input-group date" data-date-format="yyyy-mm" >
										<input type="text" name="endYear_${item.index}" value="${education.endYear}" class="form-control" readonly /><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="department" class="col-sm-2 control-label">专业</label>
								<div class="col-sm-10"><input name="department_${item.index}" value="${education.department}" class="form-control" placeholder="请填写专业" /></div>
							</div>
							<button type="button" class="btn btn-primary" onClick="educationCrudAction('save',${item.index})">Save</button>
							<button type="button" class="btn btn-primary" onClick="cancelEdit(this)">Cancel</button>
						</div>
					</div>
				</c:forEach>
				</div>
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

function showEditContainer(obj){
	$(obj).parents(".text-container").hide();
	$(obj).parents(".exp-item").children(".edit-form-container").show();
}

function cancelEdit(obj){
	$(obj).parents(".edit-form-container").hide();
	$(obj).parents(".exp-item").children(".text-container").show();
}

function educationCrudAction(action,id) {
	var json;
	switch(action) {
		case "add":
			json = JSON.stringify({
				action : action, 
				schoolName : $('input[name="schoolName"]').val(),
				degree : $('select[name="degree"]').val(),
				startYear : $('input[name="startYear"]').val(),
				endYear : $('input[name="endYear"]').val(),
				department : $('input[name="department"]').val(),
				token : globals.token
				});
		break;
		case "save":
			json = JSON.stringify({
				action : action, 
				educationId : $("#educationId_"+id).val(),
				schoolName : $('input[name="schoolName_'+ id +'"]').val(),
				degree : $('select[name="degree_'+ id +'"]').val(),
				startYear : $('input[name="startYear_'+ id +'"]').val(),
				endYear : $('input[name="endYear_'+ id +'"]').val(),
				department : $('input[name="department_'+ id +'"]').val(),
				token : globals.token
				});
		break;
		case "delete":
			json = JSON.stringify({
				action : action,
				educationId : $("#educationId_"+id).val(),
				token : globals.token
				});
		break;
	}
	alert(json);
	jQuery.ajax({
		url: ctx + "/profile/educationCrudAction.json",
		type: "POST",
		contentType: "application/json; charset=utf-8",
		dataType : "json",
		data: json,
		success:function(result){
			// TODO
			globals.token = 1;
			
			switch(action) {
				case "add":
					var returnEducation = result.data.education;
					var html = "";
					html += '<div class="exp-item">';
					html += 	'<input id="educationId_'+id+'" type="hidden" value="'+returnEducation.id+'" >';
					html +=		'<div class="bs-callout bs-callout-info text-container">';
					html += 		'<h2>'+ returnEducation.schoolName +'</h2>';
					html += 		'<p>'+ returnEducation.department +'</p>';
					html += 		'<p>' + getDegreeTxt(returnEducation.degree) + '</p>';
					html += 		'<p>'+ returnEducation.startYear + '~' + returnEducation.endYear + '</p>';
					html += 		'<button type="button" class="btn btn-primary" onClick="showEditContainer(this)"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</button>';
					html += 		'<button type="button" class="btn btn-primary" onClick="educationCrudAction(\'delete\','+ id +')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</button>';
					html +=		'</div>';
					html +=	'</div>';
					$("#education-experience").append(html);
					
				break;
				case "save":
					var returnEducation = result.data.education;
					var textObj = $("#educationId_"+id).parents(".exp-item").children(".text-container");
					textObj.empty();
					var html = "";
					html += '<h2>'+ returnEducation.schoolName +'</h2>';
					html += '<p>'+ returnEducation.department +'</p>';
					html += '<p>' + getDegreeTxt(returnEducation.degree) + '</p>';
					html += '<p>'+ returnEducation.startYear + '~' + returnEducation.endYear + '</p>';
					html += '<button type="button" class="btn btn-primary" onClick="showEditContainer(this)"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</button>';
					html += '<button type="button" class="btn btn-primary" onClick="educationCrudAction(\'delete\','+ id +')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</button>';
					textObj.append(html);
					
					textObj.show();
					$("#educationId_"+id).parents(".exp-item").children(".edit-form-container").hide();
				break;
				case "delete":
					$("#educationId_"+id).parents(".exp-item").remove();
				break;
			}
		},
		error:function (){}
	});
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

window.globals = {
	
	token : "f432be451d8144fb8928a242fee7c51b"
};
</script>

<%@ include file="../common/footer.jsp" %>
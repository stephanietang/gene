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
					<li><a href="#self-intro">工作经历</a></li>
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
						<div class="input-group date year-date">
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
			
			<div id="education-section" class="bs-docs-section" >
				<h1 class="page-header"><a class="anchorjs-link " href="#education" ></a>教育经历</h1>
				<form id="eduAddForm" class="form-horizontal">
					<div class="form-group">
						<label for="schoolName" class="col-sm-2 control-label">学校名称</label>
						<div class="col-sm-10"><input name="schoolName" class="form-control" placeholder="请填写学校" /></div>
					</div>
					<div class="form-group">
						<label for="schoolName" class="col-sm-2 control-label">学历</label>
						<div class="col-sm-10"><ct:options list="${AppBeans.degreeList}" name="degree" /></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">就读时间</label>
						<div class="col-sm-10">
							<div class="input-daterange">
								<input type="text" name="startYear" class="input-small year-date" readonly />
						    	<span class="add-on">至</span>
						    	<input type="text" name="endYear" class="input-small year-date" readonly />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="department" class="col-sm-2 control-label">专业</label>
						<div class="col-sm-10"><input name="department" class="form-control" placeholder="请填写专业" /></div>
					</div>
					<div class="form-group">
						<spring:message code="button.user.add" var="addButton"/>
						<div class="btn btn-primary edu-add" >${addButton}</div>
					</div>
				</form>
				
				<div id="education-items-container">
				<c:forEach var="education" items="${resumeForm.educationList}" varStatus="item"> 
					<div class="education-item" id="education-item_${item.index}">
						<div class="bs-callout bs-callout-info text-container">
							<h2>${education.schoolName}</h2>
							<p>${education.department}</p>
							<p><ct:label list="${AppBeans.degreeList}" key="${education.degree}"/></p>
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
					<form class="form-horizontal" >
						<input type="hidden" id="eduId" name="eduId" value="" />
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label">学校名称</label>
							<div class="col-sm-10"><input name="schoolName" class="form-control" placeholder="请填写学校" /></div>
						</div>
						<div class="form-group">
							<label for="schoolName" class="col-sm-2 control-label">学历</label>
							<div class="col-sm-10"><ct:options list="${AppBeans.degreeList}" name="degree" /></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">就读时间</label>
							<div class="col-sm-10">
							    <div class="input-daterange">
									<input type="text" name="startYear" class="input-small year-date" readonly />
							    	<span class="add-on">至</span>
							    	<input type="text" name="endYear" class="input-small year-date" readonly />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="department" class="col-sm-2 control-label">专业</label>
							<div class="col-sm-10"><input name="department" class="form-control" placeholder="请填写专业" /></div>
						</div>
						<button type="button" class="btn btn-primary edu-save">Save</button>
						<button type="button" class="btn btn-primary edu-edit-cancel">Cancel</button>
					</form>
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
			
			<div class="bs-docs-section">
				<h1 id="self-intro" class="page-header"><a class="anchorjs-link " href="#self-intro"></a>自我描述</h1>
				
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
		</div>

	</div>
</div>

<div id="modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">确认删除</h4>
	      	</div>
			<div class="modal-body">
				<input type="hidden" id="modal-item-id" >
				<input type="hidden" id="modal-refer-item-id" >
				<button type="button" class="btn btn-default delete-ok" data-dismiss="modal">确认删除</button>
				<button type="button" class="btn btn-default delete-cancel" data-dismiss="modal">取消</button>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>

	
<script>
jQuery(document).ready(function() { 
	
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
		
		var json;
		
		json = JSON.stringify({
			action : "add", 
			schoolName : $('input[name="schoolName"]',oForm).val(),
			degree : $('select[name="degree"]',oForm).val(),
			startYear : $('input[name="startYear"]',oForm).val(),
			endYear : $('input[name="endYear"]',oForm).val(),
			department : $('input[name="department"]',oForm).val()
			});
		
		jQuery.ajax({
			url: ctx + "/profile/educationCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == '200'){
					// clear add form
					$(":input","#eduAddForm")
					  .removeAttr("checked")
					  .removeAttr("selected")
					  .not(":button, :submit, :reset, :hidden, :radio, :checkbox")
					  .val("");
					
					var educationItems = result.data.educations;
					initEducationList(educationItems);
				}else{
					alert(result.message);
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
		
		jQuery.ajax({
			url: ctx + "/profile/educationCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == '200'){
					var eduItems = result.data.educations;
					initEducationList(eduItems);
				}else{
					alert(result.message);
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
		var referId = $("#modal-refer-item-id").val();
		var referItem = $("#"+referId);
		var json = JSON.stringify({
			action : "delete",
			educationId : $("#modal-item-id").val()
			});
		jQuery.ajax({
			url: ctx + "/profile/educationCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == '200'){
					$(referItem).remove();
				}else{
					alert(result.message);
				}
			},
			error:function (){
				alert("Error!");
			}
		});
	});
	
});

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
		html +=			'<div class="btn btn-primary edu-edit" data-eduid="'+educationId+'" data-schoolname="'+schoolName+'" data-degree="'+degree+'" data-department="'+department+'" data-startyear="'+startYear+'" data-endyear="'+endYear+'"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</div>';
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
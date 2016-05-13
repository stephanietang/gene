var jsonObj;
$(function() {
	
	'use strict';
    $.getJSON(ctx+"/talent/profile/displayArray.json",function(result){
    	jsonObj = result.data;              
    });
    
    $('.upload-ok').on('click', function() {
		var data = $(this).data();
		data.submit();
	});
    
    /*$(".mr-b-name").hover(function() {
        $(this).addClass("mr-active").find(".mr-edit").removeClass("hidden")
    }, function() {
        $(this).removeClass("mr-active").find(".mr-edit").addClass("hidden")
    });*/
    
    $(".mr-b-name .mr-edit").bind("click", function() {
        $(".mr-b-name").hide();
        $(".mr-name-edit").removeClass("hidden");
    });
    
    $(".mr-name-edit .cancel").bind("click", function() {
        $(".mr-b-name").show();
        $(".mr-name-edit").addClass("hidden");
    });
    
	$("#checkAll").click(function () {
	    $(".check").prop('checked', $(this).prop('checked'));
	});
	
	$(document).on("click", ".year-date", function() {
		$(this).datepicker({
			format: "yyyy",
			startView: "years",
			minViewMode: "years",
			startDate: new Date('1950', '00', '01'),
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
		html +=			'<p>'+getListLabel(degree, 'degrees')+'</p>';
		html +=			'<p>'+startYear+'~'+endYear+'</p>';
		html +=			'<div class="btn btn-primary edu-edit" data-eduid="'+educationId+'" data-schoolname="'+schoolName+'" data-degree="'+degree+'" data-department="'+department+'" data-startyear="'+startYear+'" data-endyear="'+endYear+'"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</div>&nbsp;';
		html +=			'<div class="btn btn-primary edu-delete" data-eduid="'+educationId+'" data-toggle="modal" data-target="#modal" ><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete</div>';
		html +=		'</div>';
		html +=	'</div>';
	}
	$("#education-items-container").append(html);
}

function getListLabel(key, listName){
	var labelText = "";
	$.each(jsonObj, function (index, array) {
		if(index == listName) {
			$.each(array, function(i, element){
				if(element.labelKey == key) {
					labelText = element.labelName;
					return false;
				}
			});
			return false;
		}
	});
	return labelText;
}
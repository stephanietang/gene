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
    
    /**Update name**/
    $("#name-form").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			name: {
				required: true
			}
		}
	});
    
    $(".mr-blk-name").hover(function() {
        $(this).addClass("mr-active").find(".mr-button").removeClass("hidden")
    }, function() {
        $(this).removeClass("mr-active").find(".mr-button").addClass("hidden")
    });
    
    $(".mr-blk-name .edit").on("click", function() {
        $(".mr-blk-name").hide();
        $(".mr-edit-name").removeClass("hidden");
        var name = $(this).data("name");
        $(".mr-edit-name").find(".mr-name").val(name);
    });
    
    $(".mr-edit-name .cancel").on("click", function() {
        $(".mr-blk-name").show();
        $(".mr-edit-name").addClass("hidden");
    });
    
    $(".mr-edit-name .save").on("click", function() {
    	var oForm = $("#name-form");
		var valid = $(oForm).valid();
		if(! valid){
			return ;
		}
		
		csrfAjaxSetup();
		
		var json;
		
		json = JSON.stringify({
			id : $("#basicInfoId").val(),
			name : $("input[name='name']",oForm).val()
		});
		
		$.ajax({
			url: ctx + "/talent/profile/saveBasicInfoAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == 'success'){
					var name = result.data.name;
					$(".mr-blk-name .edit").data("name", name);
					$(".mr-blk-name .mr-name").text(name);
					$(".mr-blk-name").show();
			        $(".mr-edit-name").addClass("hidden");
				}
			},
			error:function (){
				alert("Error!");
			}
		});
    });
    
    /**Update basic info**/
    $("#info-form").validate({
		onfocusin: function(element) {
	        $(element).valid();
	    },
		rules: {
			telNo: {
				required: true,
				digits: true
			}
		}
	});
    
    $(".mr-blk-info").hover(function() {
        $(this).addClass("mr-active").find(".mr-button").removeClass("hidden")
    }, function() {
        $(this).removeClass("mr-active").find(".mr-button").addClass("hidden")
    });
    
    $(".mr-blk-info .edit").on("click", function() {
        $(".mr-blk-info").hide();
        $(".mr-edit-info").removeClass("hidden");
        $("#sex").val($(this).data("sex"));
        $("#birthYear").val($(this).data("birthyear"));
        $("#degree").val($(this).data("degree"));
        $("#workExp").val($(this).data("workexp"));
        $("#city").val($(this).data("city"));
        $("#telNo").val($(this).data("telno"));
    });
    
    $(".mr-edit-info .cancel").on("click", function() {
        $(".mr-blk-info").show();
        $(".mr-edit-info").addClass("hidden");
    });
    
    $(".mr-edit-info .save").on("click", function() {
    	var oForm = $("#info-form");
		var valid = $(oForm).valid();
		if(! valid){
			return ;
		}
		
		csrfAjaxSetup();
		
		var json;
		
		json = JSON.stringify({
			id : $("#basicInfoId").val(),
			sex : $("#sex").val(),
			birthYear : $("#birthYear").val(),
			degree : $("#degree").val(),
			workExp : $("#workExp").val(),
			city : $("#city").val(),
			telNo : $("#telNo").val(),
		});
		
		$.ajax({
			url: ctx + "/talent/profile/saveBasicInfoAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == 'success'){
					var sex = result.data.sex;
					var birthYear = result.data.birthYear;
					var degree = result.data.degree;
					var workExp = result.data.workExp;
					var city = result.data.city;
					var telNo = result.data.telNo;
					$(".mr-blk-info .edit").data("sex", sex);
					$(".mr-blk-info .edit").data("birthyear", birthYear);
					$(".mr-blk-info .edit").data("degree", degree);
					$(".mr-blk-info .edit").data("workexp", workExp);
					$(".mr-blk-info .edit").data("city", city);
					$(".mr-blk-info .edit").data("telno", telNo);
					$(".mr-blk-info .sex").text(getListLabel(sex, "sexList"));
					$(".mr-blk-info .degree").text(getListLabel(degree, "degreeList"));
					$(".mr-blk-info .workExp").text(getListLabel(workExp, "workExpList"));
					$(".mr-blk-info .city").text(getListLabel(city, "cityList"));
					$(".mr-blk-info .telNo").text(telNo);
					$(".mr-blk-info").show();
			        $(".mr-edit-info").addClass("hidden");
				}
			},
			error:function (){
				alert("Error!");
			}
		});
    });
    
    /**Update work experience **/
    function validateWorkExpForm(oForm) {
		$(oForm).validate({
			onfocusin: function(element) {
		        $(element).valid();
		    },
			rules: {
				companyName: {
					required: true
				},
				positionName: {
					required: true
				},
				positionDesc: {
					required: true
				}
			}
		});
	}
    
    $(document).on("click",".mr-works-container .add", function(){
		$(this).parents(".mr-blk-work").hide();
		var oForm = $("#work-form-template").clone().attr("id", "work-form").removeClass("hidden");
		$(".delete", oForm).hide();
		validateWorkExpForm(oForm);
		$(".mr-works").prepend(oForm);
		
		$(".mr-works-container .add").attr("disabled",true);
		$(".mr-works-container .edit").attr("disabled",true);
		
	});
    
    $(document).on("click",".mr-blk-work .edit", function(){
		$(this).parents(".mr-blk-work").hide();
		var oForm = $("#work-form-template").clone().attr("id", "work-form").removeClass("hidden");
		validateWorkExpForm(oForm);
		$(this).parents(".mr-work-item").append(oForm);
		
		$(".work-id",oForm).val($(this).data("workexpid"));
		$(".work-ref-id",oForm).val($(this).data("refid"));
		$("input[name='companyName']",oForm).val($(this).data("companyname"));
		$("input[name='positionName']",oForm).val($(this).data("positionname"));
		$("input[name='startYear']",oForm).val($(this).data("startyear"));
		$("input[name='endYear']",oForm).val($(this).data("endyear"));
		$("textarea[name='positionDesc']",oForm).val($(this).data("positiondesc"));
		
		$(".mr-works-container .add").attr("disabled",true);
		$(".mr-works-container .edit").attr("disabled",true);
		
		$("textarea[name='positionDesc']", oForm).wysihtml5({
			  toolbar: {
				  "blockquote": false, //Blockquote  
				  "font-styles": false, //Font styling, e.g. h1, h2, etc. Default true
				  "emphasis": false, //Italics, bold, etc. Default true
				  "lists": true, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
				  "html": false, //Button which allows you to edit the generated HTML. Default false
				  "link": false, //Button to insert a link. Default true
				  "image": false, //Button to insert an image. Default true,
				  "color": false //Button to change color of font  
			  }
		});
		$('a[data-wysihtml5-command=Outdent]').hide();
		$('a[data-wysihtml5-command=Indent]').hide();
	});
    
    $(document).on("click", "#work-form .save", function(){
		
		var oForm = $("#work-form");
		
		var valid = $(oForm).valid();
		
		if(! valid){
			return ;
		}
		
		csrfAjaxSetup();
		
		var json;
		
		json = JSON.stringify({
			action : "save", 
			workExpId : $(".work-id",oForm).val(),
			companyName : $('input[name="companyName"]',oForm).val(),
			positionName : $('input[name="positionName"]',oForm).val(),
			startYear : $('input[name="startYear"]',oForm).val(),
			endYear : $('input[name="endYear"]',oForm).val(),
			positionDesc : $('textarea[name="positionDesc"]',oForm).val()
		});
		
		$.ajax({
			url: ctx + "/talent/profile/workExpCrudAction.json",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType : "json",
			data: json,
			success:function(result){
				if(result.status == 'success'){
					var workExpList = result.data;
					initWorkExpList(workExpList);
					$(".mr-works-container .add").attr("disabled",false);
				}else if(result.status == 'error'){
					displayErrorList(result);
				}
			},
			error:function (){
				alert("Error!");
			}
		});
		
	});
	
	$(document).on("click","#work-form .cancel", function(){
		$("#work-form").prev().show();
		$("#work-form").remove();
		
		$(".mr-works-container .add").attr("disabled",false);
		$(".mr-works-container .edit").attr("disabled",false);
	});
	
	$(document).on("click", "#work-form .delete", function(){
		var oForm = $("#work-form");
		var id = $(".work-id", oForm).val();
		var referId = $(".work-ref-id", oForm).val();
		$("#modal .delete-ok").data("mod", "work");
		$("#modal .delete-ok").data("itemid", id);
		$("#modal .delete-ok").data("refid", referId);
		$('#modal').modal('show');
	});
	
    /**Update education**/
	function validateEducationForm(oForm) {
		$(oForm).validate({
			onfocusin: function(element) {
		        $(element).valid();
		    },
			rules: {
				department: {
					required: true
				},
				schoolName: {
					required: true
				}
			}
		});
	}
	
	$(document).on("focus",".input-daterange input", function(){
	    $(this).datepicker({
	    	format: " yyyy",
	    	viewMode: "years",
	    	minViewMode: "years",
	    	startDate: '-46y', // from 1970
	        endDate: '+0y',
	        autoclose: true
	    });
	});
	
	$(document).on("click",".mr-educations-container .add", function(){
		$(this).parents(".mr-blk-education").hide();
		var oForm = $("#education-form-template").clone().attr("id", "education-form").removeClass("hidden");
		$(".delete", oForm).hide();
		validateEducationForm(oForm);
		$(".mr-educations").prepend(oForm);
		
		$(".mr-educations-container .add").attr("disabled",true);
		$(".mr-educations-container .edit").attr("disabled",true);
		
	});
	
	$(document).on("click",".mr-blk-education .edit", function(){
		$(this).parents(".mr-blk-education").hide();
		var oForm = $("#education-form-template").clone().attr("id", "education-form").removeClass("hidden");
		validateEducationForm(oForm);
		$(this).parents(".mr-education-item").append(oForm);
		
		$(".edu-id",oForm).val($(this).data("eduid"));
		$(".edu-ref-id",oForm).val($(this).data("refid"));
		$('input[name="schoolName"]',oForm).val($(this).data("schoolname"));
		$('select[name="degree"]',oForm).val($(this).data("degree"));
		$('input[name="startYear"]',oForm).val($(this).data("startyear"));
		$('input[name="endYear"]',oForm).val($(this).data("endyear"));
		$('input[name="department"]',oForm).val($(this).data("department"));
		
		$(".mr-educations-container .add").attr("disabled",true);
		$(".mr-educations-container .edit").attr("disabled",true);
		
	});
	
	$(document).on("click", "#education-form .save", function(){
		
		var oForm = $("#education-form");
		
		var valid = $(oForm).valid();
		
		if(! valid){
			return ;
		}
		
		csrfAjaxSetup();
		
		var json;
		
		json = JSON.stringify({
			action : "save", 
			educationId : $(".edu-id",oForm).val(),
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
					$(".mr-educations-container .add").attr("disabled",false);
				}else if(result.status == 'error'){
					displayErrorList(result);
				}
			},
			error:function (){
				alert("Error!");
			}
		});
		
	});
	
	$(document).on("click","#education-form .cancel", function(){
		$("#education-form").prev().show();
		$("#education-form").remove();
		
		$(".mr-educations-container .add").attr("disabled",false);
		$(".mr-educations-container .edit").attr("disabled",false);
	});
	
	$(document).on("click", "#education-form .delete", function(){
		var oForm = $("#education-form");
		var id = $(".edu-id", oForm).val();
		var referId = $(".edu-ref-id", oForm).val();
		$("#modal .delete-ok").data("mod", "education");
		$("#modal .delete-ok").data("itemid", id);
		$("#modal .delete-ok").data("refid", referId);
		$('#modal').modal('show');
	});
	
	$(".delete-ok").on("click",function(){
		csrfAjaxSetup();
		var mod = $("#modal .delete-ok").data("mod")
		var itemId = $("#modal .delete-ok").data("itemid");
		var referId = $("#modal .delete-ok").data("refid");
		
		switch (mod) {
		case "education":
			var json = JSON.stringify({
				action : "delete",
				educationId : itemId
			});
			
			$.ajax({
				url: ctx + "/talent/profile/educationCrudAction.json",
				type: "POST",
				contentType: "application/json; charset=utf-8",
				dataType : "json",
				data: json,
				success:function(result){
					if(result.status == 'success'){
						$("#"+referId).remove();
						$(".mr-educations-container .add").attr("disabled",false);
						$(".mr-educations-container .edit").attr("disabled",false);
					}else if(result.status == 'error'){
						displayErrorList(result);
					}
				},
				error:function (){
					alert("Error!");
				}
			});
			break;
		case "work":
			var json = JSON.stringify({
				action : "delete",
				workExpId : itemId
			});
			
			$.ajax({
				url: ctx + "/talent/profile/workExpCrudAction.json",
				type: "POST",
				contentType: "application/json; charset=utf-8",
				dataType : "json",
				data: json,
				success:function(result){
					if(result.status == 'success'){
						$("#"+referId).remove();
						$(".mr-works-container .add").attr("disabled",false);
						$(".mr-works-container .edit").attr("disabled",false);
					}else if(result.status == 'error'){
						displayErrorList(result);
					}
				},
				error:function (){
					alert("Error!");
				}
			});
			break;
		}
		
	});
	
});

function initWorkExpList(workExpList){
	$(".mr-works").empty();
	var html = "";
	for(var i = 0; i < workExpList.length; i++){
		var workExpId = workExpList[i].id;
		var companyName = workExpList[i].companyName;
		var positionName = workExpList[i].positionName;
		var positionDesc = workExpList[i].positionDesc;
		var startYear = workExpList[i].startYear;
		var endYear = workExpList[i].endYear;
		html += '<div class="mr-work-item" id="edu-'+i+'">';
		html += 	'<div class="row mr-blk-work">';
		html +=			'<div class="col-md-10">';
		html +=				'<p><span class="glyphicon glyphicon-pushpin company-name" aria-hidden="true"> '+companyName+'</p>';
		html +=				'<p>'+startYear+' - '+endYear+'</p>';
		html +=				'<p><span class="position-name">'+positionName+'</span>';
		html +=				'<p><span class="position-desc">'+positionDesc+'</span>';
		html +=			'</div>';
		html +=			'<div class="col-md-2">';
		html +=				'<input type="button" class="btn btn-default btn-xs edit" value="Edit" data-workexpid="'+workExpId+'" data-companyname="'+companyName+'" data-positionname="'+positionName+'" data-positiondesc="'+positionDesc+'" data-startyear="'+startYear+'" data-endyear="'+endYear+'" data-refid="work-'+i+'" />';
		html +=			'</div>';
		html +=		'</div>';
		html +=	'</div>';
	}
	$(".mr-works").append(html);
}

function initEducationList(eduItems){
	$(".mr-educations").empty();
	var html = "";
	for(var i = 0; i < eduItems.length; i++){
		var educationId = eduItems[i].id;
		var schoolName = eduItems[i].schoolName;
		var degree = eduItems[i].degree;
		var department = eduItems[i].department;
		var startYear = eduItems[i].startYear;
		var endYear = eduItems[i].endYear;
		html += '<div class="mr-education-item" id="edu-'+i+'">';
		html += 	'<div class="row mr-blk-education">';
		html +=			'<div class="col-md-10">';
		html +=				'<p><span class="glyphicon glyphicon-pushpin school-name" aria-hidden="true"> '+schoolName+'</p>';
		html +=				'<p><span class="department">'+department+'</span> · <span class="degree">'+ getListLabel(degree, 'degreeList') +'</span></p>';
		html +=				'<p>'+startYear+' - '+endYear+'</p>';
		html +=			'</div>';
		html +=			'<div class="col-md-2">';
		html +=				'<input type="button" class="btn btn-default btn-xs edit" value="Edit" data-eduid="'+educationId+'" data-schoolname="'+schoolName+'" data-degree="'+degree+'" data-department="'+department+'" data-startyear="'+startYear+'" data-endyear="'+endYear+'" data-refid="edu-'+i+'" />';
		html +=			'</div>';
		html +=		'</div>';
		html +=	'</div>';
	}
	$(".mr-educations").append(html);
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

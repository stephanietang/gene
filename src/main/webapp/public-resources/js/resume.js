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
    
    /**Update education**/
	$("#education-form").validate({
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
	
	$(document).on("click",".mr-blk-education .edit", function(){
		$(this).parents(".mr-blk-education").hide();
		var oForm = $("#education-template-form").clone().attr("id", "education-form").removeClass("hidden");
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
		$("#modal-item-id").val(id);
		$("#modal-refer-item-id").val(referId);
		$('#modal').modal('show');
	});
	
	$(".delete-ok").on("click",function(){
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
		html += '<div class="education-item" id="education-item_'+i+'">';
		html +=		'<div class="bs-callout bs-callout-info text-container">';
		html +=			'<h2>'+schoolName+'</h2>';
		html +=			'<p>'+department+'</p>';
		html +=			'<p>'+getListLabel(degree, 'degreeList')+'</p>';
		html +=			'<p>'+startYear+' - '+endYear+'</p>';
		html +=			'<div class="btn btn-primary edit" data-eduid="'+educationId+'" data-schoolname="'+schoolName+'" data-degree="'+degree+'" data-department="'+department+'" data-startyear="'+startYear+'" data-endyear="'+endYear+'"></div>&nbsp;';
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
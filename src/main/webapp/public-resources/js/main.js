$(document).ready(function() {
	//$( "#dropdownMenu" ).menu();
	initGlobalMessage();
});

function logoutSubmit() {
	$("#logoutForm").submit();
}

function redirectIndex() {
	top.location.href = ctx + '/index';
}

function setCountdown(time, id, callback){
	var count = setTimeout(function(){$("#"+id).html(time); setCountdown(time-1,id,callback)},1000);
	if(time == 0){
		clearTimeout(count);
		callback();
	}
}

function csrfAjaxSetup() {
	$.ajaxSetup({
        headers:
        { 'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content') }
    });
	
}

function displayErrorList(result) {
	$("#errorMessage").text('');
	var msgDom = '<ul>';
	$.each(result.error, function(i, item){
		msgDom += '<li>' + item + '</li>';
	});
	msgDom += '</ul>'
	$("#errorMessage").append(msgDom).show();
}

function initGlobalMessage() {
	var errorMsg = $('#errorMessage').text();
	var msg = $('#msg').text();
	
	if(errorMsg != '') {
		$('#errorMessage').show();
	}else{
		$('#errorMessage').hide();
	}
	
	if(msg != '') {
		$('#msg').show();
	}else{
		$('#msg').hide();
	}
}

function resetGlobalMessage() {
	$('#errorMessage').text("").hide();
	$('#msg').text("").hide();
}
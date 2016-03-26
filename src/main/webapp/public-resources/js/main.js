$(document).ready(function() {
	//$( "#dropdownMenu" ).menu();
});

function setCountdown(time, id, url){
	var count = setTimeout(function(){$("#"+id).html(time); setCountdown(time-1,id,url)},1000);
	if(time == 0){
		clearTimeout(count);
		top.location.href=url;
	}
}

function csrfAjaxSetup() {
	$.ajaxSetup({
        headers:
        { 'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content') }
    });
	
}

function displayError(data) {
	var msg = '';
	$.each(data.error, function(i, item){
		msg += item;
	});
	$("#errorMessage").text(msg).show();
}
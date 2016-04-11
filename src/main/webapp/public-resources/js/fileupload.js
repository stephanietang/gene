$(function(){
	'use strict';
	$(".avatar-shadow").click(function () {
	    $('#fileupload').trigger('click');
	});
	
	$('#fileupload').fileupload({
		url: ctx+'/uploadAvatar',
        dataType: 'json',
        autoUpload: false,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i, // only accept images
        maxFileSize: 2097152, // 2M
        formData: {
        	_csrf: $('meta[name="_csrf"]').attr('content'),
        	userId: $('#hiddenUserId').val()
        },
     	// Enable image resizing, except for Android and Opera,
        // which actually support image resizing, but fail to
        // send Blob objects via XHR requests:
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator && navigator.userAgent),
		imageMaxWidth: 120,
		imageMaxHeight: 120,
		imageCrop: true,// crop size to 100X100
		previewMaxWidth: 300,
		previewMaxHeight: 300,
		previewCrop: true // crop preview size to 300X300
	}).on('fileuploadadd', function(e, data) {
        $('.upload-ok').data(data);
	}).on('fileuploadprocessalways', function (e, data) {
        var file = data.files[0];
	    if (file.preview) {
	    	$('#avatar-container').empty();
	    	$('#avatar-container').append('<div />').append('<p />').prepend(file.preview);
	        $("#avatar-modal").modal();
	    }
	    if (file.error) {
	        $("#avatar-error-modal").modal();
	    }
	}).on('fileuploaddone', function (e, data) {
    	var imagePath = data.result.data.path;
    	$('#avatar-img').prop('src', imagePath);
    });
});

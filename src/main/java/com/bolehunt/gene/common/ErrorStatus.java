package com.bolehunt.gene.common;

public enum ErrorStatus {
	
	UNKNOWN_EXCEPTION("errMsg.unknownException"),
	
	USER_PASSWORD_NOT_MATCH("errMsg.user.password.notMatch"),
	USER_EMAIL_OR_PASSWORD_INCORRECT("errMsg.user.email.or.password.incorrect"),
	USER_PASSWORD_DUPLICATE("errMsg.user.password.duplicate"),
	USER_PASSWORD_INCORRECT_FORMAT("errMsg.user.password.incorrect"),
	USER_EMAIL_NOT_EXIST("errMsg.user.email.not.exist"),
	USER_EMAIL_EXIST("errMsg.user.email.existing"),
	USER_EMAIL_INCORRECT_FORMAT("errMsg.user.email.incorrect"),
	USER_ALREADY_ENABLED("errMsg.user.already.enalbed"),
	USER_FORBIDDEN_ACCESS("errMsg.user.forbidden.access"),
	
	TOKEN_ALREADY_VERIFIED("errMsg.token.already.verified"),
	TOKEN_ALREADY_EXPIRED("errMsg.token.already.expired"),
	TOKEN_NOT_FOUND("errMsg.token.notFound"),
	
	UPDATE_PASSWORD_ERROR("errMsg.updatePassword.error"),
	
	UPLOAD_EXCEED_MAX_SIZE("errMsg.upload.exceed.max.size"),
	UPLOAD_FILE_EMPTY("errMsg.upload.file.empty"),
	UPLOAD_FILE_EXCEPTION("errMsg.upload.error");
	
	private ErrorStatus(String value){
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
}

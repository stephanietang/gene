package com.bolehunt.gene.common;

public enum Status {
	
	COMMON_SUCCESS("200", "200", "msg.success"),
	
	UNKNOWN_EXCEPTION("404", "404", "errMsg.unknownException"),
	
	USER_PASSWORD_NOT_MATCH("201", "20101", "errMsg.user.password.notMatch"),
	USER_EMAIL_OR_PASSWORD_INCORRECT("201", "20102", "errMsg.user.email.or.password.incorrect"),
	USER_PASSWORD_DUPLICATE("201", "20103", "errMsg.user.password.duplicate"),
	USER_PASSWORD_INCORRECT_FORMAT("201", "20104", "errMsg.user.password.incorrect"),
	USER_EMAIL_NOT_EXIST("201", "20105", "errMsg.user.email.not.exist"),
	USER_EMAIL_EXIST("201", "20106", "errMsg.user.email.existing"),
	USER_EMAIL_INCORRECT_FORMAT("201","20107","errMsg.user.email.incorrect"),
	USER_ALREADY_ENABLED("201", "20108", "errMsg.user.already.enalbed"),
	USER_FORBIDDEN_ACCESS("201", "20109", "errMsg.user.forbidden.access"),
	
	TOKEN_ALREADY_VERIFIED("202", "20201", "errMsg.token.already.verified"),
	TOKEN_ALREADY_EXPIRED("202", "20202", "errMsg.token.already.expired"),
	TOKEN_NOT_FOUND("202", "20203", "errMsg.token.notFound"),
	
	
	UPDATE_PASSWORD_ERROR("420", "42001", "errMsg.updatePassword.error");
	
	private Status(String status, String code, String message){
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	private String status;
	private String code;
	private String message;
	
	public String getStatus() {
		return status;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
	
}

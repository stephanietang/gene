package com.bolehunt.gene.common;

public enum CommonMessage {
	
	USER_LOGOUT_SUCCESS("msg.user.logout.success");
	
	
	private CommonMessage(String message){
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return message;
	}

}

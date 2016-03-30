package com.bolehunt.gene.common;

public enum CommonMessage {
	
	USER_LOGOUT_SUCCESS("msg.user.logout.success");
	
	private CommonMessage(String value){
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}

}

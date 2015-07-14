package com.bolehunt.gene.common;

import java.util.Map;

public class JsonResponse {
	
	public final static String DEFAULT_SUCCESS_STATUS = Status.COMMON_SUCCESS.getStatus();
	
	private String status;
	private String message;
	private Map<String, String> data;
	private boolean hasErrors = false;
	 
	public JsonResponse(String status, String message) {
		this.status = status;
		this.message = message;
		if(! DEFAULT_SUCCESS_STATUS.equals(status)){
			hasErrors = true;
		}
	}
	
	public JsonResponse(String status, String message, Map<String, String> data) {
		this(status, message);
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public boolean hasErrors() {
		return hasErrors;
	}
	
}

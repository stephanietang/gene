package com.bolehunt.gene.common;

import java.util.List;

public final class RestMessage {
	
	private final static String STATUS_SUCCESS = "success";
	private final static String STATUS_ERROR = "error";

	private String status;
	private List<Object> data;
	private List<String> error;
	
	public RestMessage() {
	}
	
	public RestMessage(List<Object> data) {
		this.status = STATUS_SUCCESS;
		this.data = data;
	}

	
	public static RestMessage getErrorMessage(final List<String> error) {
		RestMessage  errorMsg = new RestMessage();
		errorMsg.setStatus(STATUS_ERROR);
		errorMsg.setError(error);
		return errorMsg;
	}

	public static RestMessage getSuccessMessage(final List<Object> data) {
		RestMessage  errorMsg = new RestMessage();
		errorMsg.setStatus(STATUS_SUCCESS);
		errorMsg.setData(data);
		return errorMsg;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

}

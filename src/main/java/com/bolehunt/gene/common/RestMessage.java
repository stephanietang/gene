package com.bolehunt.gene.common;


public final class RestMessage<T> {
	
	private final static String STATUS_SUCCESS = "success";
	private final static String STATUS_ERROR = "error";

	private String status;
	
	private T data;
	private T error;
	
	public RestMessage() {
		
	}
	
	public RestMessage<T> getErrorMessage(T error) {
		this.status = STATUS_ERROR;
		this.error = error;
		return this;
	}
	
	public RestMessage<T> getSuccessMessage(T data) {
		this.status = STATUS_SUCCESS;
		this.data = data;
		return this;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getError() {
		return error;
	}

	public void setError(T error) {
		this.error = error;
	}

}

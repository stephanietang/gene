package com.bolehunt.gene.common;

import java.util.List;
import java.util.Map;

public class JsonResponse {
	
	public final static String DEFAULT_SUCCESS_STATUS = Status.COMMON_SUCCESS.getStatus();
	
	private String status;
	private List<Errors> errors;
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
	
	public List<Errors> getErrors() {
		return errors;
	}

	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}



	public static class Errors {
		private String field;
		private String errMsg;
		
		public Errors(String field, String errMsg) {
			super();
			this.field = field;
			this.errMsg = errMsg;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
		
	}
	
}

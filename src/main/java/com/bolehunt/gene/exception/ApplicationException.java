package com.bolehunt.gene.exception;

import java.util.List;

import com.bolehunt.gene.common.ErrorStatus;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -8175936104094875229L;
	
	public ApplicationException(List<ErrorStatus> errorList) {
		super();
		this.errorList = errorList;
	}
	
	public ApplicationException(ErrorStatus error) {
		super();
		this.error = error;
	}

	private List<ErrorStatus> errorList;
	
	private ErrorStatus error;

	public List<ErrorStatus> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorStatus> errorList) {
		this.errorList = errorList;
	}

	public ErrorStatus getError() {
		return error;
	}

	public void setError(ErrorStatus error) {
		this.error = error;
	}
	
 
}

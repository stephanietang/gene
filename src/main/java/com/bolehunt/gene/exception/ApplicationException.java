package com.bolehunt.gene.exception;

import com.bolehunt.gene.common.Status;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -8175936104094875229L;
	
	public ApplicationException(Status status) {
		super();
		this.status = status;
	}

	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
 
}

package com.bolehunt.gene.common;

public class FileUploadResponse {
	
	private String error;
	private String[] initialPreview;
	private boolean append;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String[] getInitialPreview() {
		return initialPreview;
	}

	public void setInitialPreview(String[] initialPreview) {
		this.initialPreview = initialPreview;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}
	
	
	
}

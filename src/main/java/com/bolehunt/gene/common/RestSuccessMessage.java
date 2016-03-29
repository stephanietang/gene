package com.bolehunt.gene.common;

/***
 * Singleton Class
 */
public class RestSuccessMessage {
	
	private static class RestSuccessMessageHolder {  
        private static final RestSuccessMessage INSTANCE = new RestSuccessMessage();  
    } 
	
	private final static String STATUS_SUCCESS = "success";

	private String status;
	
	private RestSuccessMessage() {
		this.status = STATUS_SUCCESS;
	}
	
	public static final RestSuccessMessage getInstance() {  
        return RestSuccessMessageHolder.INSTANCE;
    } 
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

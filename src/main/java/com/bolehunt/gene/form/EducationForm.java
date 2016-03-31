package com.bolehunt.gene.form;


public class EducationForm {
	
	private String action;
	private Integer educationId;
	private Integer degree;
	private String schoolName;
	private Integer startYear;
	private Integer endYear;
	private String department;
	private String token; 
	
	
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public void setEducationId(Integer educationId) {
		this.educationId = educationId;
	}
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getEducationId() {
		return educationId;
	}
	public void setEducationId(int educationId) {
		this.educationId = educationId;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

}

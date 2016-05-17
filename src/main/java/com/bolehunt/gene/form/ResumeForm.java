package com.bolehunt.gene.form;

import java.util.List;

import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;

public class ResumeForm {

	private Integer userId;
	private BasicInfo basicInfo;
	private List<Education> educationList;
	private Avatar avatar;
	
	private String ageStr;

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public List<Education> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<Education> educationList) {
		this.educationList = educationList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public String getAgeStr() {
		return ageStr;
	}

	public void setAgeStr(String ageStr) {
		this.ageStr = ageStr;
	}
	
	
}

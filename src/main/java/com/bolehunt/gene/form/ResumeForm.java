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
	
	private Integer basicInfoId;
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBasicInfoId() {
		return basicInfoId;
	}

	public void setBasicInfoId(Integer basicInfoId) {
		this.basicInfoId = basicInfoId;
	}
	
}

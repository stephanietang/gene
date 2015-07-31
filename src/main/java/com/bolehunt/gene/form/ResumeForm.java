package com.bolehunt.gene.form;

import java.util.List;

import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;

public class ResumeForm {

	private BasicInfo basicInfo;
	private List<Education> educationList;

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
	
	
	
}

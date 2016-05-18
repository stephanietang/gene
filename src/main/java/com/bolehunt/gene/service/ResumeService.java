package com.bolehunt.gene.service;

import java.util.List;

import com.bolehunt.gene.common.Label;
import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.domain.WorkExperience;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.form.WorkExperienceForm;

public interface ResumeService {
	
	public ResumeForm retrieveResume(User user);
	
	public BasicInfo saveBasicInfo(BasicInfo basicInfo);
	
	public Avatar retrieveAvatar(User user);
	
	public BasicInfo retrieveBasicInfo(User user);
	
	public List<Education> retrieveEducationList(int basicInfoId);
	
	public void validateEducationForm(EducationForm educationForm);
	
	public void proceedEducationForm(EducationForm educationForm, User user);
	
	public List<WorkExperience> retrieveWorkExpList(int basicInfoId);
	
	public void proceedWorkExperienceForm(WorkExperienceForm workExperienceForm, User user);
	
	public List<Label> getCityList();
	
	public List<Label> getCountryList();
	
	public List<Label> getSexList();
	
	public List<Label> getDegreeList();
	
	public List<Label> getWorkExpList();
	
	public List<Label> getBirthYearList();
	

}

package com.bolehunt.gene.service;

import java.util.List;

import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;

public interface ResumeService {
	
	public ResumeForm retrieveResume(User user);
	
	public BasicInfo saveName(ResumeForm resumeForm);
	
	public void saveResume(ResumeForm resumeForm);
	
	public Avatar retrieveAvatar(User user);
	
	public BasicInfo retrieveBasicInfo(User user);
	
	public List<Education> retrieveEducationList(int basicInfoId);
	
	public void validateEducationForm(EducationForm educationForm);
	
	public void proceedEducationForm(EducationForm educationForm, User user);

}

package com.bolehunt.gene.service;

import java.util.List;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;

public interface ResumeService {
	
	public ResumeForm retrieveResume(User user);
	
	public void saveResume(ResumeForm resumeForm);
	
	public List<Education> retrieveEducationList(int userId);
	
	public JsonResponse validateEducationForm(EducationForm educationForm);
	
	public JsonResponse proceedEducationForm(EducationForm educationForm, User user);

}

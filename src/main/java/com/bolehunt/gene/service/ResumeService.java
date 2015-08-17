package com.bolehunt.gene.service;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;

public interface ResumeService {
	
	public ResumeForm retrieveResume(User user);
	
	public void saveResume(ResumeForm resumeForm);
	
	public JsonResponse validateEducationForm(EducationForm educationForm);
	
	public void proceedEducationForm(EducationForm educationForm, User user, JsonResponse jsonResponse);

}
package com.bolehunt.gene.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.RestMessage;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.Avatar;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.service.FileService;
import com.bolehunt.gene.service.ResumeService;

@Controller
public class ResumeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/talent/profile", method = RequestMethod.GET)
	public String viewResumePage(ModelMap model) {
		
		ResumeForm resumeForm = resumeService.retrieveResume(getUser());
		
		model.put("resumeForm", resumeForm);
		return "resume/viewResume";
	}
	
	@RequestMapping(value = "/talent/profile/edit", method = RequestMethod.GET)
	public String editResumePage(@ModelAttribute ModelMap model, HttpServletRequest request) {
		
		if (super.isRememberMeAuthenticated()) { // requires authentication
			super.setRememberMeTargetUrlToSession(request, "/talent/profile/edit");
			return "redirect:/login";
		}

		ResumeForm resumeForm = resumeService.retrieveResume(getUser());
		model.put("resumeForm", resumeForm);
		
		EducationForm educationForm = new EducationForm();
		model.put("educationForm", educationForm);
		
		Avatar avatar = fileService.getAvatarByUserId(getUser().getId());
		if(avatar != null){
			model.put("avatar", avatar.getUuid());
		}
		
		return "resume/editResume";
	}
	
	@RequestMapping(value = "/talent/profile/edit", method = RequestMethod.POST)
	public String editResumePageSubmit(@ModelAttribute("resumeForm") ResumeForm resumeForm) {
		
		resumeService.saveResume(resumeForm);
		
		log.info("Save resume successfully [{}]", getUser().getEmail());
		
		return "redirect:/profile/edit";
		
	}
	
	@RequestMapping(value = "/talent/profile/educationCrudAction.json", method = RequestMethod.POST)
	public ResponseEntity<RestMessage> educationCrudAction(@RequestBody EducationForm educationForm) {
		
		resumeService.validateEducationForm(educationForm);
		
		resumeService.proceedEducationForm(educationForm, getUser());
		
		log.info("Proceed education form successfully [{}]", getUser().getEmail());
		
		List<Education> educationList = resumeService.retrieveEducationList(getUser().getId());
		
		return new ResponseEntity<RestMessage>(RestMessage.getSuccessMessage(null), HttpStatus.OK);
	}
}

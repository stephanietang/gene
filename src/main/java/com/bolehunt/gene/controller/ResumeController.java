package com.bolehunt.gene.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;

@Controller
public class ResumeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewResumePage(ModelMap model) {
		User user = getUser();
		if(!user.userLogin){
			return "redirect:/index";
		}
		
		ResumeForm resumeForm = userService.retrieveResume(user);
		
		model.put("resumeForm", resumeForm);
		return "resume/viewResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String editResumePage(ModelMap model) {
		User user = getUser();
		if(!user.userLogin){
			return "redirect:/index";
		}
		
		ResumeForm resumeForm = userService.retrieveResume(user);
		model.put("resumeForm", resumeForm);
		
		EducationForm educationForm = new EducationForm();
		model.put("educationForm", educationForm);
		return "resume/editResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
	public String editResumePageSubmit(@ModelAttribute("resumeForm") ResumeForm resumeForm) {
		User user = getUser();
		if(!user.userLogin){
			return "redirect:/index";
		}
		
		userService.saveResume(resumeForm);
		
		log.info("Save resume successfully [{}]", user.getEmail());
		
		return "redirect:/profile/edit";
		
	}
}

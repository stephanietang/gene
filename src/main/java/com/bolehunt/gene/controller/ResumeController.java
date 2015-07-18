package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolehunt.gene.form.ResumeForm;

@Controller
public class ResumeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewResumePage(ModelMap model, HttpServletRequest request) {
		ResumeForm resumeForm = userService.retrieveResume(getUser());
		
		model.put("resumeForm", resumeForm);
		return "resume/viewResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String editResumePage(ModelMap model, HttpServletRequest request) {
		
		return "resume/editResume";
	}
	
}

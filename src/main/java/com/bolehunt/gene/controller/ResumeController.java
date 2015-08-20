package com.bolehunt.gene.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.service.ResumeService;
import com.bolehunt.gene.util.WebUtil;

@Controller
public class ResumeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
	
	@Autowired
	private ResumeService resumeService;
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewResumePage(ModelMap model) {
		User user = getUser();
		if(!user.userLogin){
			return "redirect:/index";
		}
		
		ResumeForm resumeForm = resumeService.retrieveResume(user);
		
		model.put("resumeForm", resumeForm);
		return "resume/viewResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String editResumePage(ModelMap model) {
		User user = getUser();
		if(!user.userLogin){
			return "redirect:/index";
		}
		
		ResumeForm resumeForm = resumeService.retrieveResume(user);
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
		
		resumeService.saveResume(resumeForm);
		
		log.info("Save resume successfully [{}]", user.getEmail());
		
		return "redirect:/profile/edit";
		
	}
	
	@RequestMapping(value = "/profile/educationCrudAction.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse educationCrudAction(@RequestBody EducationForm educationForm) {
		
		User user = getUser();
		if(! user.isUserLogin()){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
		JsonResponse jsonResponse = resumeService.validateEducationForm(educationForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}
		
		JsonResponse returnResponse = resumeService.proceedEducationForm(educationForm, user);
		
		log.info("Proceed education form successfully [{}]", user.getEmail());
	    return returnResponse;
	}
}

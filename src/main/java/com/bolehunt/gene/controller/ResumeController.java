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
import com.bolehunt.gene.domain.Avatar;
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
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewResumePage(ModelMap model) {
		
		ResumeForm resumeForm = resumeService.retrieveResume(user);
		
		model.put("resumeForm", resumeForm);
		return "resume/viewResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String editResumePage(@ModelAttribute ModelMap model) {

		ResumeForm resumeForm = resumeService.retrieveResume(user);
		model.put("resumeForm", resumeForm);
		
		EducationForm educationForm = new EducationForm();
		model.put("educationForm", educationForm);
		
		Avatar avatar = fileService.getAvatarByUserId(user.getId());
		if(avatar != null){
			model.put("avatar", avatar.getUuid());
		}
		
		return "resume/editResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
	public String editResumePageSubmit(@ModelAttribute("resumeForm") ResumeForm resumeForm) {
		
		resumeService.saveResume(resumeForm);
		
		log.info("Save resume successfully [{}]", user.getEmail());
		
		return "redirect:/profile/edit";
		
	}
	
	@RequestMapping(value = "/profile/educationCrudAction.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse educationCrudAction(@RequestBody EducationForm educationForm) {
		
		JsonResponse jsonResponse = resumeService.validateEducationForm(educationForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}
		
		JsonResponse returnResponse = resumeService.proceedEducationForm(educationForm, user);
		
		log.info("Proceed education form successfully [{}]", user.getEmail());
	    return returnResponse;
	}
}

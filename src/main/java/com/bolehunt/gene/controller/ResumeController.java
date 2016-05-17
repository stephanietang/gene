package com.bolehunt.gene.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bolehunt.gene.common.Label;
import com.bolehunt.gene.common.RestMessage;
import com.bolehunt.gene.domain.BasicInfo;
import com.bolehunt.gene.domain.Education;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.EducationForm;
import com.bolehunt.gene.form.ResumeForm;
import com.bolehunt.gene.service.ResumeService;

@Controller
public final class ResumeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
	
	@Autowired
	private ResumeService resumeService;
	
	@RequestMapping(value = "/talent/profile", method = RequestMethod.GET)
	public String editResumePage(ModelMap model, HttpServletRequest request) {
		
		if (super.isRememberMeAuthenticated()) { // requires authentication
			super.setRememberMeTargetUrlToSession(request, "/talent/profile");
			return "redirect:/login";
		}

		User user = getUser();
		model.put("user", user);
		
		ResumeForm resumeForm = resumeService.retrieveResume(user);
		model.put("resumeForm", resumeForm);
		
		EducationForm educationForm = new EducationForm();
		model.put("educationForm", educationForm);
		
		model.put("countryList", resumeService.getCountryList());
		model.put("cityList", resumeService.getCityList());
		model.put("sexList", resumeService.getSexList());
		model.put("degreeList", resumeService.getDegreeList());
		model.put("workExpList", resumeService.getWorkExpList());
		model.put("birthYearList", resumeService.getBirthYearList());
		
		return "resume/editResume";
	}
	
	@RequestMapping(value = "/talent/profile/saveBasicInfoAction.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<BasicInfo> saveBasicInfoSubmit(@RequestBody BasicInfo basicInfo) {
		
		BasicInfo rtn = resumeService.saveBasicInfo(basicInfo);
		
		log.info("Save basic info successfully [{}]", getUser().getEmail());
		
		return new RestMessage<BasicInfo>().getSuccessMessage(rtn);
	}
	
	@RequestMapping(value = "/talent/profile/educationCrudAction.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<List<Education>> educationCrudAction(@RequestBody EducationForm educationForm) {
		
		log.info("Start to proceed education form user = {}, action = {} ", getUser().getEmail(), educationForm.getAction());
		
		resumeService.validateEducationForm(educationForm);
		
		resumeService.proceedEducationForm(educationForm, getUser());
		
		log.info("Proceed education form successfully, user = {}", getUser().getEmail());
		
		BasicInfo basicInfo = resumeService.retrieveBasicInfo(getUser());
		
		List<Education> educationList = resumeService.retrieveEducationList(basicInfo.getId());
		
		return new RestMessage<List<Education>>().getSuccessMessage(educationList);
	}
	
	@RequestMapping(value = "/talent/profile/displayArray.json", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<Map<String, List<Label>>> displayArray() {
		Map<String, List<Label>> map = new HashMap<String, List<Label>>();
		map.put("degreeList", resumeService.getDegreeList());
		map.put("sexList", resumeService.getSexList());
		map.put("workExpList", resumeService.getWorkExpList());
		map.put("cityList", resumeService.getCityList());
		return new RestMessage<Map<String, List<Label>>>().getSuccessMessage(map);
	}
	
}

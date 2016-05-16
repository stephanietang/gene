package com.bolehunt.gene.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bolehunt.gene.common.Label;
import com.bolehunt.gene.common.LabelEnum;
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
		
		model.put("countryList", getCountryList());
		model.put("cityList", getCityList());
		model.put("sexList", getSexList());
		model.put("degreeList", getDegreeList());
		model.put("workExpList", getWorkExpList());
		
		return "resume/editResume";
	}
	
	@RequestMapping(value = "/talent/profile", method = RequestMethod.POST)
	public String editResumePageSubmit(@ModelAttribute("resumeForm") ResumeForm resumeForm) {
		
		resumeService.saveResume(resumeForm);
		
		log.info("Save resume successfully [{}]", getUser().getEmail());
		
		return "redirect:/talent/profile/edit";
		
	}
	
	
	@RequestMapping(value = "/talent/profile/saveNameAction.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<BasicInfo> saveNameSubmit(@RequestBody ResumeForm resumeForm) {
		
		BasicInfo basicInfo = resumeService.saveName(resumeForm);
		
		return new RestMessage<BasicInfo>().getSuccessMessage(basicInfo);
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
		map.put("countrys", getDegreeList());
		map.put("sexs", getSexList());
		map.put("degrees", getDegreeList());
		map.put("workExps", getWorkExpList());
		return new RestMessage<Map<String, List<Label>>>().getSuccessMessage(map);
	}
	
	private List<Label> getCityList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.CITY_SHENZHEN));
		list.add(getLabel(LabelEnum.CITY_BEIJING));
		list.add(getLabel(LabelEnum.CITY_GUANGZHOU));
		list.add(getLabel(LabelEnum.CITY_HANGZHOU));
		list.add(getLabel(LabelEnum.CITY_HONGKONG));
		list.add(getLabel(LabelEnum.CITY_TAIPEI));
		return list;
	}
	
	private List<Label> getCountryList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.COUNTRY_CHINA));
		list.add(getLabel(LabelEnum.COUNTRY_US));
		return list;
	}
	
	private List<Label> getSexList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.SEX_MALE));
		list.add(getLabel(LabelEnum.SEX_FEMALE));
		return list;
	}
	
	private List<Label> getDegreeList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.DEGREE_BACHELOR));
		list.add(getLabel(LabelEnum.DEGREE_MASTER));
		list.add(getLabel(LabelEnum.DEGREE_PHD));
		list.add(getLabel(LabelEnum.DEGREE_ASSOCIATE));
		return list;
	}
	
	private List<Label> getWorkExpList(){
		List<Label> list = new ArrayList<Label>();
		list.add(getLabel(LabelEnum.WORK_EXP_GRADUATE));
		list.add(getLabel(LabelEnum.WORK_EXP_ABOVE_ONE_YEAR));
		list.add(getLabel(LabelEnum.WORK_EXP_ABOVE_THREE_YEAR));
		list.add(getLabel(LabelEnum.WORK_EXP_ABOVE_FIVE_YEAR));
		return list;
	}
}

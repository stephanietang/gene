package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolehunt.gene.service.UserService;

@Controller
public class ResumeController {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewResumePage(ModelMap model, HttpServletRequest request) {
		
		model.put("baseForm", userService.initBaseForm());
		
		return "resume/viewResume";
	}
	
	@RequestMapping(value = "/profile/edit", method = RequestMethod.GET)
	public String editResumePage(ModelMap model, HttpServletRequest request) {
		
		model.put("baseForm", userService.initBaseForm());
		
		return "resume/editResume";
	}
	
}

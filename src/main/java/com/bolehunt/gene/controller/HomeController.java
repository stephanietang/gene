package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.form.LoginForm;
import com.bolehunt.gene.service.UserService;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
	//@Autowired
	//LoginFormValidator loginFormValidator;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request) {
		
		model.put("baseForm", userService.initBaseForm());
		
		return "index";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	 public String login(ModelMap model) {
		model.put("baseForm", userService.initBaseForm());
		model.addAttribute("loginForm", new LoginForm());
		return "user/login";
	}
	
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse registerSubmit(@RequestBody LoginForm loginForm) {
		
		JsonResponse jsonResponse = userService.validateLoginForm(loginForm);
		
	    return jsonResponse;
	  }
}

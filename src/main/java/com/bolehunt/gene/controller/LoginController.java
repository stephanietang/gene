package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolehunt.gene.form.LoginForm;

@Controller
public class LoginController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	 public String login(ModelMap model, 
			 @RequestParam(value = "error", required = false) String error,
			 @RequestParam(value = "logout", required = false) String logout,
			 HttpServletRequest request) {
		
		model.addAttribute("loginForm", new LoginForm());
		
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid username and password!");
		}

		if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}
		
		return "user/login";
	}
	
}

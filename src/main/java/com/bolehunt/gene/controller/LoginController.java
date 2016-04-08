package com.bolehunt.gene.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolehunt.gene.common.CommonMessage;
import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.form.LoginForm;

@Controller
public final class LoginController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	 public String login(ModelMap model, 
			 @RequestParam(value = "error", required = false) String error,
			 @RequestParam(value = "logout", required = false) String logout) {
		
		model.addAttribute("loginForm", new LoginForm());
		
		if (error != null) {
			model.addAttribute("errorMessage", getMessage(ErrorStatus.USER_EMAIL_OR_PASSWORD_INCORRECT.getValue()));
		}

		if (logout != null) {
			model.addAttribute("msg", getMessage(CommonMessage.USER_LOGOUT_SUCCESS.getValue()));
		}
		
		return "user/login";
	}
	
}

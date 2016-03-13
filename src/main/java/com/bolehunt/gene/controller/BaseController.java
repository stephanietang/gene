package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bolehunt.gene.domain.Menu;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.service.UserService;

public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	protected User user;
	
	@Autowired
	protected UserService userService;
	
	@ModelAttribute("user")
	public User getSessionUser(ModelMap model, HttpServletRequest request) {
		log.debug("BaseController getSessionUser");
		this.user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
		
		return user;
	}
	
	@ModelAttribute("menu")
	public Menu initMenu(ModelMap model, HttpServletRequest request) {
		log.debug("BaseController initMenu");
		if(this.user == null) {
			this.user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
		}
		return userService.getUserMenu(this.user);
	}
	
	
	public User getUser() {
		return user;
	}
	
	
}

package com.bolehunt.gene.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.service.UserService;

public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected UserService userService;
	
	@ModelAttribute("user")
	public User getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		User user = new User();
		if (currentUser.isRemembered()) {
	    	log.info("Remembered PRINCIPAL: " + currentUser.getPrincipal());
	    	user = userService.getUserByEmail(currentUser.getPrincipal().toString());
	    	user.setUserLogin(true);
		} else if (currentUser.isAuthenticated()) {
	    	log.info("Authenticated PRINCIPAL: " + currentUser.getPrincipal());
	    	user = userService.getUserByEmail(currentUser.getPrincipal().toString());
	    	user.setUserLogin(true);
	    } else{
	    	log.info("No login user");
	    	user.setUserLogin(false);
	    }
		return user;
	}
	
}

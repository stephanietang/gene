package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.service.UserService;

public class BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected UserService userService;
	
	protected User getUser() {
		String userName = this.getPrincipal();
		log.info("Principal:" + userName);
		User user = new User();
		return user;
	}
	
	protected String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	
	protected boolean isRememberMeAuthenticated() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
			
		boolean isRememberMeAuthenticated = false;
		if(RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			isRememberMeAuthenticated = true;
		}
			
		log.debug("isRememberMeAuthenticated = " + isRememberMeAuthenticated);

		return isRememberMeAuthenticated;
	}
		
	protected void setRememberMeTargetUrlToSession(HttpServletRequest request, String targetUrl){
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.setAttribute("targetUrl", targetUrl);
		}
	}	
}

package com.bolehunt.gene.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
	
	@Autowired
	private MessageSource messageSource;
	
	protected User getUser() {
		String email = this.getPrincipal();
		User user = userService.getUserByEmail(email);
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
	
	protected String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();                        
        return messageSource.getMessage(key, new Object[0], locale);
    }
}

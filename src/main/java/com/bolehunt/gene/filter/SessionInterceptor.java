package com.bolehunt.gene.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.service.UserService;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(SessionInterceptor.class);
	
	private static final String INDEX_URL = "/login";
	
	private static final String LOGGEDIN_USER = "LOGGEDIN_USER";
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if(! uri.endsWith("login") || ! uri.endsWith("logout")) {
			Subject currentUser = SecurityUtils.getSubject();
			User user = new User();
			if (currentUser.isRemembered()) {
		    	log.info("Remembered PRINCIPAL: " + currentUser.getPrincipal());
		    	user = userService.getUserByEmail(currentUser.getPrincipal().toString());
			} else if (currentUser.isAuthenticated()) {
		    	log.info("Authenticated PRINCIPAL: " + currentUser.getPrincipal());
		    	user = userService.getUserByEmail(currentUser.getPrincipal().toString());
		    } else{
		    	log.info("No login user");
		    	user = null;
		    }
			
			if(user != null) {
				request.getSession().setAttribute(LOGGEDIN_USER, user);
				return true;
			} else {
				response.sendRedirect(INDEX_URL);
			}
		}
		
		return true;
	}
	
}

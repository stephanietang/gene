package com.bolehunt.gene.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.exception.UnknownResourceException;

@ControllerAdvice
@Controller
public class ExceptionHandlerController {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	public ExceptionHandlerController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler(ApplicationException.class)
	public ModelAndView handleCustomException(ApplicationException ex) {
 
		log.debug("handleCustomException status = {}, message = {}", ex.getStatus().getStatus(), ex.getStatus().getMessage());
		
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage(ex.getStatus().getMessage(), null, locale);
		
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", ex.getStatus().getStatus());
		model.addObject("message", message);
 
		return model;
 
	}
	
	// ignore path for static resources
	@RequestMapping(value = { "{path:(?!resources).*$}", "{path:(?!resources).*$}/**" }, headers = "Accept=text/html")
	public void unmappedPath(HttpServletRequest request) {
		String uri = request.getRequestURI();

		log.debug("Unmapped uri = {} ", uri);
		
		throw new UnknownResourceException("There is no resource for path " + uri);
 
	}
 
	/*@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
 
		log.debug("handleAllException");
		
		ModelAndView model = new ModelAndView("error");
		model.addObject("errMsg", "Unknown Exception!");
 
		return model;
 
	}*/

}

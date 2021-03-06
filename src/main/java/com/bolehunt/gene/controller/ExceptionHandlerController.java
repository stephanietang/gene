package com.bolehunt.gene.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.common.RestMessage;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.exception.UnknownResourceException;

@ControllerAdvice
@Controller
public final class ExceptionHandlerController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<List<String>> handleCustomException(ApplicationException ex) {
 
		log.debug("handleCustomException errorList = {}, error = {}", ex.getErrorList(), ex.getError());
		
		List<String> errorList = new ArrayList<String>();
		
		if(ex.getErrorList() != null && ex.getErrorList().size() > 0) {
			
			for (ErrorStatus error: ex.getErrorList()) {
				String message = getMessage(error.getValue());
				
				errorList.add(message);
			}
			
		}else if(ex.getError() != null){
			String message = getMessage(ex.getError().getValue());
			
			errorList.add(message);
		}
		
		RestMessage<List<String>> restError = new RestMessage<List<String>>().getErrorMessage(errorList);
        return restError; 
		
	}
	
	// ignore path for static resources
	@RequestMapping(value = { "{path:(?!resources).*$}", "{path:(?!resources).*$}/**" }, headers = "Accept=text/html")
	public void unmappedPath(HttpServletRequest request) {
		String uri = request.getRequestURI();

		log.debug("Unmapped uri = {} ", uri);
		
		throw new UnknownResourceException("There is no resource for path " + uri);
 
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
 
		log.debug("handleAllException: ", ex);
		
		ModelAndView model = new ModelAndView("error");
		model.addObject("errMsg", "Unknown Exception!");
 
		return model;
 
	}

}

package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolehunt.gene.common.ErrorStatus;
import com.bolehunt.gene.exception.ApplicationException;

@Controller
public final class HomeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request) {
		
		return "index";
	}
 
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model, 
    		@RequestParam(value = "uploadExceedMaxSize", required = false) String uploadExceedMaxSize) {
    	
    	if (uploadExceedMaxSize != null) {
			model.addAttribute("errorMessage", getMessage(ErrorStatus.UPLOAD_EXCEED_MAX_SIZE.getValue()));
		}
        return "403";
    }


}

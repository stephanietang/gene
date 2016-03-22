package com.bolehunt.gene.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request) {
		
		return "index";
	}
 
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "403";
    }

}

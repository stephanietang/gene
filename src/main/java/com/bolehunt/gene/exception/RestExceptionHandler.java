package com.bolehunt.gene.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;


public class RestExceptionHandler extends AbstractHandlerExceptionResolver {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    private RestErrorResolver errorResolver;

    public RestExceptionHandler() {
        this.errorResolver = new DefaultRestErrorResolver();
    }

    public void setErrorResolver(RestErrorResolver errorResolver) {
        this.errorResolver = errorResolver;
    }

    public RestErrorResolver getErrorResolver() {
        return this.errorResolver;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        RestErrorResolver resolver = getErrorResolver();

        RestError error = resolver.resolveError(webRequest, handler, ex);
        if (error == null) {
            return null;
        }
        
        ModelAndView mav = new ModelAndView("devError");
        mav.addObject("status", error.getStatus());
        mav.addObject("code", error.getCode());
        mav.addObject("message", error.getMessage());
        mav.addObject("developerMessage", error.getDeveloperMessage());
        mav.addObject("moreInfoUrl", error.getMoreInfoUrl());
 
        return mav;
    }

}
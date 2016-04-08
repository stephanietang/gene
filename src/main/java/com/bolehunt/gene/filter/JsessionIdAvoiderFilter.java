package com.bolehunt.gene.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bolehunt.gene.controller.RegisterController;

public final class JsessionIdAvoiderFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		boolean allowFilterChain = redirectToAvoidJsessionId((HttpServletRequest) req, (HttpServletResponse) res);
		// if its redirected, then no need to continue processing the request
		if (allowFilterChain) {
			chain.doFilter(req, res);
		}
	}
	
	private static boolean redirectToAvoidJsessionId(HttpServletRequest req, HttpServletResponse res) {
		String requestURI = req.getRequestURI();
		if (requestURI.indexOf(";JSESSIONID=") > 0) {
			try {
				// TODO
				res.sendRedirect("/gene/");
				return false;
			} catch (IOException e) {
				log.error("JsessionIdAvoiderFilter Exception : " + e);
			}
		}
		return true;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}

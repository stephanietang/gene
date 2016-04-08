package com.bolehunt.gene.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public final class CharsetEncodingFilter implements Filter {

    private String encoding;
    

    @Override
    public void destroy() {
    	this.encoding = null;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        request.setCharacterEncoding(encoding);
        
        response.setContentType("text/html;charset="+ encoding);
        
        chain.doFilter(request, response);
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

}

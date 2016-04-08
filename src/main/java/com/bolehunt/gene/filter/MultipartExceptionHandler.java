package com.bolehunt.gene.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

// see the post on stackoverflow
// https://stackoverflow.com/questions/23856254/how-to-nicely-handle-file-upload-maxuploadsizeexceededexception-with-spring-secu/23957580#23957580
public final class MultipartExceptionHandler extends OncePerRequestFilter {
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MaxUploadSizeExceededException e) {
            handle(request, response, e);
        } catch (ServletException e) {
            if(e.getRootCause() instanceof MaxUploadSizeExceededException) {
                handle(request, response, (MaxUploadSizeExceededException) e.getRootCause());
            } else {
                throw e;
            }
        }
    }
	
	private void handle(HttpServletRequest request,
            HttpServletResponse response, MaxUploadSizeExceededException e) throws ServletException, IOException {

        String redirect = "/gene/403?uploadExceedMaxSize";
        response.sendRedirect(redirect);
    }

}

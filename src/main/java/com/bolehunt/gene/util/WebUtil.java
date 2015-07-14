package com.bolehunt.gene.util;

import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.Status;


/**
* Static convenience methods for common web-related tasks.
*/
@Component
public final class WebUtil {
	
	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);
	
	private static MessageSource messageSource;
	
	@Autowired
	public WebUtil(MessageSource messageSource) {
		WebUtil.messageSource = messageSource;
	}
	
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}

	/**
	 * • The password must be at least 6 characters long.
	 * • The password must have at least one uppercase and one lowercase letter
	 * • The password must have at least one digit.
	 * @param password
	 * @return
	 */
	public static boolean isValidPassword(String password){
		
	    Boolean atleastOneUpper = false;
	    Boolean atleastOneLower = false;
	    Boolean atleastOneDigit = false;

	    if (password.length() < 6) { // If its less then 6 characters, its automatically not valid
	        return false;
	    }

	    for (int i = 0; i < password.length(); i++) { // Lets iterate over only once. Saving time
	        if (Character.isUpperCase(password.charAt(i))) {
	            atleastOneUpper = true;
	        }
	        else if (Character.isLowerCase(password.charAt(i))) {
	            atleastOneLower = true;
	        }
	        else if (Character.isDigit(password.charAt(i))) {
	            atleastOneDigit = true;
	        }
	    }

	    return (atleastOneUpper && atleastOneLower && atleastOneDigit);
	}
	
	public static JsonResponse formatJsonResponse(Status status){
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage(status.getMessage(), null, locale);
		return new JsonResponse(status.getStatus(), message);
	}
	
	public static JsonResponse formatJsonResponse(Status status, Map<String, String> data){
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage(status.getMessage(), null, locale);
		return new JsonResponse(status.getStatus(), message, data);
	}
	
}

package com.bolehunt.gene.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bolehunt.gene.common.AppBeans;
import com.bolehunt.gene.common.CommonMessage;
import com.bolehunt.gene.common.ErrorStatus;


/**
* Static convenience methods for common web-related tasks.
*/
@Component
public class WebUtil {
	
	private static final Logger log = LoggerFactory.getLogger(WebUtil.class);
	
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}

	/**
	 * The password must be at least 6 characters long.
	 * The password must have at least one uppercase and one lowercase letter
	 * The password must have at least one digit.
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
	
	public static String resolveMailDomainFromEmail(String email){
		String[] temp;
		String delimiter = "@";

		temp = email.split(delimiter);
		String domain = AppBeans.getDomainMap().get(temp[1]);
		if(StringUtils.isBlank(domain)){
			return "http://"+temp[1];
		}

		return domain;
	}
	
	public static String formatErrorMessage(ErrorStatus error){
		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		MessageSource messageSource = (MessageSource) webAppContext.getBean("messageSource");
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage(error.getMessage(), null, locale);
		
		return errorMessage;
	}
	
	public static String formatMessage(CommonMessage message){
		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		MessageSource messageSource = (MessageSource) webAppContext.getBean("messageSource");
		Locale locale = LocaleContextHolder.getLocale();
		String msg = messageSource.getMessage(message.getMessage(), null, locale);
		
		return msg;
	}
	
	
}

package com.bolehunt.gene.util;

import org.apache.commons.lang3.StringUtils;

import com.bolehunt.gene.common.AppBeans;


/**
* Static convenience methods for common web-related tasks.
*/
public final class WebUtil {
	
	private WebUtil() {
		super();
	}

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
	
}

package com.bolehunt.gene.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolehunt.gene.form.RegisterForm;
import com.bolehunt.gene.service.UserService;
import com.bolehunt.gene.util.WebUtil;

@Component
public class RegisterFormValidator implements Validator {
	
	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
        return RegisterForm.class.isAssignableFrom(clazz);
    }
	
	@Override
	public void validate(Object target, Errors errors){
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "errMsg.field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "errMsg.field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "errMsg.field.required");
		
		
		RegisterForm registerForm = (RegisterForm) target;
		/*if(registerForm != null && StringUtils.isNotBlank(registerForm.getUsername())){
			if(userService.isExistingUser(registerForm.getUsername())){
				errors.rejectValue("username", "errMsg.user.username.existing");
			}
		}*/
		
		// TODO enabled on production
		// for development, comment just for easy testing
		/*if(registerForm != null && StringUtils.isNotBlank(registerForm.getPassword())){
			if(! WebUtil.isValidPassword(registerForm.getPassword())){
				errors.rejectValue("password", "errMsg.user.password.incorrect");
			}
		}*/
		
		if(registerForm != null && StringUtils.isNotBlank(registerForm.getEmail())){
			if(userService.isExistingEmail(registerForm.getEmail())){
				errors.rejectValue("email", "errMsg.user.email.existing");
			}
			if(! WebUtil.isValidEmailAddress(registerForm.getEmail())){
				errors.rejectValue("email", "errMsg.user.email.incorrect");
			}
		}
	}
}

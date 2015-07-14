package com.bolehunt.gene.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolehunt.gene.form.LoginForm;

@Component
public class LoginFormValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
    }
	
	@Override
	public void validate(Object target, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "errMsg.field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "errMsg.field.required");
	}
}

package com.bolehunt.gene.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bolehunt.gene.form.UpdatePasswordForm;
import com.bolehunt.gene.service.UserService;

@Component
public class UpdatePasswordFormValidator implements Validator {
	
	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
        return UpdatePasswordForm.class.isAssignableFrom(clazz);
    }
	
	@Override
	public void validate(Object target, Errors errors){
		
		UpdatePasswordForm form = (UpdatePasswordForm) target;
		if(form != null && StringUtils.isNotBlank(form.getEmail())){
			if(! userService.isEmailPasswordMathed(form.getEmail(), form.getOldPassword())){
				errors.rejectValue("oldPassword", "errMsg.user.password.notMatch");
			}
		}
		
		if(form != null && StringUtils.isNotBlank(form.getOldPassword())){
			if(form.getOldPassword().equals(form.getNewPassword())){
				errors.rejectValue("newPassword", "errMsg.user.password.duplicate");
			}
		}
		
		// TODO enabled on production
		// for development, comment just for easy testing
		/*if(form != null && StringUtils.isNotBlank(form.getNewPassword())){
			if(! WebUtil.isValidPassword(form.getNewPassword())){
				errors.rejectValue("newPassword", "errMsg.user.password.incorrect");
			}
		}*/
		
	}
}

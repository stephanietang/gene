package com.bolehunt.gene.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bolehunt.gene.common.Constant.VerifyTokenType;
import com.bolehunt.gene.common.RestMessage;
import com.bolehunt.gene.common.RestSuccessMessage;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.RegisterForm;
import com.bolehunt.gene.form.UpdatePasswordForm;
import com.bolehunt.gene.service.VerifyTokenService;
import com.bolehunt.gene.util.WebUtil;

@Controller
public class RegisterController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private VerifyTokenService verifyTokenService;
	
	// TODO: for development
	@RequestMapping(value="/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public User getUserById(@PathVariable(value = "userId") int userId) {
		User user = userService.getUserById(userId);
		return user;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String registerPage(ModelMap model) {
		model.addAttribute("registerForm", new RegisterForm());
	
		return "user/register";
	}
	
	@RequestMapping(value = "/register.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestSuccessMessage registerSubmit(@RequestBody RegisterForm registerForm) {
		
		userService.validateRegisterForm(registerForm);
		
		User user = new User();
		try{
			BeanUtils.copyProperties(user, registerForm);
		}catch(Exception e){
			log.error("copy bean properties error : ", e);
		}
			
		userService.registerUser(user);
			
		log.info("Register [{}] new user successfully", user.getEmail());
		
		return RestSuccessMessage.getInstance();
	}
	
	/**
	 * Redirect to confirm page of sending verification email after registration
	 */
	@RequestMapping(value="/confirm_mail", method = RequestMethod.POST)
	public String confirmMailPage(@ModelAttribute("registerForm") RegisterForm form, ModelMap model){
		String email = form.getEmail();
		
		model.addAttribute("email", email);
		model.addAttribute("mailDomain", WebUtil.resolveMailDomainFromEmail(email));
		
		return "user/confirmEmail";
	}
	
	/**
	 *  Re-send verification email page
	 */
	@RequestMapping(value="/send_mail", method = RequestMethod.POST)
	public String sendMailPage(@ModelAttribute("registerForm") RegisterForm registerForm, ModelMap model){
		String email = registerForm.getEmail();
		model.addAttribute("email", email);
		
		return "user/sendEmail";
	}
	
	@RequestMapping(value="/send_mail.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<Map<String, Object>> sendEmail(@RequestBody RegisterForm registerForm){
		
		userService.validateSendEmailForm(registerForm);
		
		verifyTokenService.insertVerifyToken(registerForm.getEmail(), VerifyTokenType.VERIFICATION_EMAIL);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", registerForm.getEmail());
		map.put("mailDomain", WebUtil.resolveMailDomainFromEmail(registerForm.getEmail()));
		
		return new RestMessage<Map<String, Object>>().getSuccessMessage(map);
		
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public String validateVerificationToken(@RequestParam("token") String base64EncodedToken) {
		log.info("Validate verification token");
		
		verifyTokenService.validateVerificationToken(base64EncodedToken);
		return "user/validateSuccess";
	}
	
	@RequestMapping(value = "/forget_password", method = RequestMethod.GET)
	public String forgetPasswordPage(){
		
		return "user/forgetPassword";
	}
	
	@RequestMapping(value="/forget_password.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestMessage<Map<String, Object>> forgetPasswordSubmit(@RequestBody RegisterForm registerForm){
		
		userService.validateSendEmailForm(registerForm);
		
		verifyTokenService.insertVerifyToken(registerForm.getEmail(), VerifyTokenType.LOST_PASSWORD_EMAIL);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", registerForm.getEmail());
		map.put("mailDomain", WebUtil.resolveMailDomainFromEmail(registerForm.getEmail()));
		
		return new RestMessage<Map<String, Object>>().getSuccessMessage(map);
		
	}
	
	@RequestMapping(value = "/reset_password", method = RequestMethod.GET)
	public String resetPasswordPage(@RequestParam("token") String base64EncodedToken, ModelMap model){
		log.debug("token = {}", base64EncodedToken);
		
		User user = verifyTokenService.validateLostPassToken(base64EncodedToken);
		
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		updatePasswordForm.setEmail(user.getEmail());
		updatePasswordForm.setToken(base64EncodedToken);
		
		model.put("resetPasswordForm", updatePasswordForm);
		return "user/resetPassword";
	}
	
	@RequestMapping(value = "/reset_password.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestSuccessMessage resetPasswordSubmit(@RequestBody UpdatePasswordForm resetPasswordForm) {
		
		userService.validateResetPasswordForm(resetPasswordForm);
		
		User user = userService.getUserByEmail(resetPasswordForm.getEmail());
		userService.resetUserPassword(user, resetPasswordForm.getNewPassword(), resetPasswordForm.getToken());
		
		log.info("Reset [{}] new password successfully", user.getEmail());
			
		return RestSuccessMessage.getInstance();
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	public String accountSetting(ModelMap model) {
		
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		updatePasswordForm.setEmail(getUser().getEmail());
		
		model.put("updatePasswordForm", updatePasswordForm);
		
		return "user/updatePassword";
	}
	
	@RequestMapping(value = "/update_password.json", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RestSuccessMessage updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
		userService.validateUpdatePasswordForm(updatePasswordForm);
		
		User user = userService.getUserByEmail(updatePasswordForm.getEmail());
		userService.updateUserPassword(user, updatePasswordForm.getNewPassword());
		
		log.info("Update [{}] new password successfully", user.getEmail());
		
		return RestSuccessMessage.getInstance();
	}
	
	
}
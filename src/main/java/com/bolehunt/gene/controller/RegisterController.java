package com.bolehunt.gene.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolehunt.gene.common.Constant.VerifyTokenType;
import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.BaseForm;
import com.bolehunt.gene.form.RegisterForm;
import com.bolehunt.gene.form.UpdatePasswordForm;
import com.bolehunt.gene.service.UserService;
import com.bolehunt.gene.service.VerifyTokenService;
import com.bolehunt.gene.util.WebUtil;

@Controller
public class RegisterController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
	
	//@Autowired
	//RegisterFormValidator registerFormValidator;
	
	//@Autowired
	//UpdatePasswordFormValidator updatePasswordFormValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VerifyTokenService verifyTokenService;
	
	// TODO: for development
	@RequestMapping(value="/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable(value = "userId") int userId) {
		User user = userService.getUserById(userId);
		return user;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		RegisterForm registerForm = new RegisterForm();
		BaseForm baseForm = userService.initBaseForm();
		model.put("baseForm", baseForm);
		model.put("registerForm", registerForm);
		return "user/register";
	}
	
	/* Replace with post json
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registerSubmit(@ModelAttribute("registerForm") RegisterForm registerForm, BindingResult result, ModelMap model) {
		registerFormValidator.validate(registerForm, result);
		
		if(result.hasErrors()){
			return "user/register";
		}
		
		User user = new User();
		try{
			BeanUtils.copyProperties(user, registerForm);
		}catch(Exception e){
			log.error("copy bean properties error : " + e);
		}
		
		userService.registerUser(user);
		
		model.put("user", user);
		return "user/registerActive";
	}*/
	
	@RequestMapping(value = "/register.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse registerSubmit(@RequestBody RegisterForm registerForm) {
		JsonResponse jsonResponse = userService.validateRegisterForm(registerForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}else{
			User user = new User();
			try{
				BeanUtils.copyProperties(user, registerForm);
			}catch(Exception e){
				log.error("copy bean properties error : " + e);
			}
			
			userService.registerUser(user);
			
			log.info("Register [{}] new user successfully", user.getEmail());
			
			return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
			
		}
	}
	
	// Redirect to confirm page of sending verification email after registration
	@RequestMapping(value="/confirm_mail", method = RequestMethod.GET)
	public String confirmMailPage(@RequestParam(value = "email") String email, ModelMap model){
		model.put("email", email);
		model.put("mailDomain", "http://mail.google.com");
		return "user/confirmEmail";
	}
	
	// Re-send verification email page
	@RequestMapping(value="/send_mail", method = RequestMethod.GET)
	public String sendMailPage(@RequestParam(value = "email") String email, ModelMap model){
		model.put("email", email);
		return "user/sendEmail";
	}
	
	@RequestMapping(value="/send_mail.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse sendEmail(@RequestBody RegisterForm registerForm){
		JsonResponse jsonResponse = userService.validateSendEmailForm(registerForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}else{
			verifyTokenService.sendTokenEmail(registerForm.getEmail(), VerifyTokenType.VERIFICATION_EMAIL);
			Map<String, String> data = new HashMap<String, String>();
			data.put("email", registerForm.getEmail());
			data.put("mailDomain", "http://mail.google.com");
			return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS, data);
		}
		
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public String validateVerificationToken(@RequestParam("token") String base64EncodedToken) {
		log.debug("token = {}", base64EncodedToken);
		
		verifyTokenService.validateVerificationToken(base64EncodedToken);
		return "user/validateSuccess";
	}
	
	@RequestMapping(value = "/forget_password", method = RequestMethod.GET)
	public String resetPasswordPage(){
		
		return "user/forgetPassword";
	}
	
	@RequestMapping(value="/forget_password.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse resetPasswordSubmit(@RequestBody RegisterForm registerForm){
		JsonResponse jsonResponse = userService.validateSendEmailForm(registerForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}else{
			verifyTokenService.sendTokenEmail(registerForm.getEmail(), VerifyTokenType.LOST_PASSWORD_EMAIL);
			Map<String, String> data = new HashMap<String, String>();
			data.put("email", registerForm.getEmail());
			data.put("mailDomain", "http://mail.google.com");
			return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS, data);
		}
		
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
	public JsonResponse resetPasswordSubmit(@RequestBody UpdatePasswordForm resetPasswordForm) {
		JsonResponse jsonResponse = userService.validateResetPasswordForm(resetPasswordForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}else{
			try{
				User user = userService.getUserByEmail(resetPasswordForm.getEmail());
				userService.resetUserPassword(user, resetPasswordForm.getNewPassword(), resetPasswordForm.getToken());
			
				log.info("Reset [{}] new password successfully", user.getEmail());
				return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
			}catch(Exception e){
				log.error("Reset new password error, {}", e);
				return WebUtil.formatJsonResponse(Status.UPDATE_PASSWORD_ERROR);
			}
			
		}
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String accountSetting(ModelMap model) {
		
		UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();
		BaseForm baseForm = userService.initBaseForm();
		if(! baseForm.isLogined()){
			return "redirect:/index";
		}else{
			updatePasswordForm.setEmail(baseForm.getUser().getEmail());
		}
		
		model.put("baseForm", baseForm);
		model.put("updatePasswordForm", updatePasswordForm);
		
		return "user/updatePassword";
	}
	
	/* Replaced by post json to updatePassword method
	@RequestMapping(value = "/user/updatePwd", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("updatePasswordForm") UpdatePasswordForm updatePasswordForm, BindingResult result, ModelMap model) {
		updatePasswordFormValidator.validate(updatePasswordForm, result);
		
		if(result.hasErrors()){
			return "user/updatePwd";
		}
		User user = userService.getUserByEmail(updatePasswordForm.getEmail());
		userService.updateUserPassword(user, updatePasswordForm.getNewPassword());
		
		log.info("Update [{}] new password successfully", user.getUsername());
		
		//return "redirect:/user/logout";
		return "user/updatePwd";
	}
	*/
	
	@RequestMapping(value = "/update_password.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
		JsonResponse jsonResponse = userService.validateUpdatePasswordForm(updatePasswordForm);
		if(jsonResponse.hasErrors()){
			return jsonResponse;
		}else{
			try{
				User user = userService.getUserByEmail(updatePasswordForm.getEmail());
				userService.updateUserPassword(user, updatePasswordForm.getNewPassword());
			
				log.info("Update [{}] new password successfully", user.getEmail());
				return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
			}catch(Exception e){
				log.error("Update new password error, {}", e);
				return WebUtil.formatJsonResponse(Status.UPDATE_PASSWORD_ERROR);
			}
			
		}
	}
	
	
}
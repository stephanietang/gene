package com.bolehunt.gene.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.common.Constant;
import com.bolehunt.gene.common.Constant.UserEnableType;
import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.Role;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.domain.UserExample;
import com.bolehunt.gene.domain.VerifyToken;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.exception.UnknownResourceException;
import com.bolehunt.gene.form.RegisterForm;
import com.bolehunt.gene.form.UpdatePasswordForm;
import com.bolehunt.gene.persistence.RoleMapper;
import com.bolehunt.gene.persistence.UserMapper;
import com.bolehunt.gene.service.UserService;
import com.bolehunt.gene.service.VerifyTokenService;
import com.bolehunt.gene.util.WebUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private AppConfig config;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private VerifyTokenService verifyTokenService;
	
	@Override
	public List<User> getAllUsers() {
		return userMapper.selectByExample(new UserExample());
	}
	
	@Override
	@Cacheable(value="userFindCache", key="#id")
	public User getUserById(int id) throws UnknownResourceException {
		User user = userMapper.selectByPrimaryKey(id);
		if(user == null){
			throw new UnknownResourceException("Unable to find user with id '" + id + "'");
		}
		
		return user;
	}
	
	@Override
	public User getUserByEmail(String email) throws UnknownResourceException{
		UserExample userEx = new UserExample();
		userEx.createCriteria().andEmailEqualTo(email);
		List<User> userList = userMapper.selectByExample(userEx);
		
		if(userList != null && userList.size() > 0){
			return userList.get(0);
		}else{
			throw new UnknownResourceException("Unable to find user with email '" + email + "'");
		}
	}
	
	@Override
	public void updateUserSelective(User user){
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	public void insertUser(User user){
		userMapper.insert(user);
	}
	
	@Override
	@Transactional
	public User registerUser(User user){
		user.setEnabled(Constant.UserEnableType.NOT_ENABLED.getValue());
		user.setPassword(this.encodePassword(user.getPassword()));
		userMapper.insert(user);
		
		log.debug("Insert User {} success", user.getEmail());
		
		//verifyTokenService.sendTokenEmail(user.getEmail(), VerifyTokenType.VERIFICATION_EMAIL);
		
		return user;
	}
	
	@Override
	public User enableUser(User user){
        if(user.getEnabled().equals(UserEnableType.ENABLED.getValue())){
        	throw new ApplicationException(Status.USER_ALREADY_ENABLED);
        }
        user.setEnabled(Constant.UserEnableType.ENABLED.getValue());
        this.updateUserSelective(user);
        
        log.debug("User is enabled succssfully");
        return user;
	}
	
	private String encodePassword(String rawPassword) {
		// use bCrypt to hash password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(rawPassword);
		return hashedPassword;
	}
	
	
	@Override
	public void updateUserPassword(User user, String newPassword){
		user.setPassword(this.encodePassword(newPassword));
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	@Transactional
	public void resetUserPassword(User user, String newPassword, String base64EncodedToken){
		verifyTokenService.verifyToken(base64EncodedToken);
		this.updateUserPassword(user, newPassword);
	}
	
	@Override
	public boolean isExistingEmail(String email){
		UserExample userEx = new UserExample();
		userEx.createCriteria().andEmailEqualTo(email);
		
		int count = userMapper.countByExample(userEx);
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean isEmailPasswordMathed(String email, String password){
		boolean isMatched = false;
		User user = this.getUserByEmail(email);
		if(user != null && password != null){
			String encodePassword = this.encodePassword(password);
			if(encodePassword.equals(user.getPassword())){
				isMatched = true;
			}
		}
		return isMatched;
	}
	
	private boolean isEmailTokenMatched(String email, String base64EncodedToken){
		boolean isMatched = false;
		VerifyToken verifyToken = verifyTokenService.loadToken(base64EncodedToken);
		User user = this.getUserByEmail(email);
		if(user.getId().equals(verifyToken.getUserId())){
			isMatched = true;
		}
		return isMatched;
	}
	
	@Override
	public boolean isNewPasswordSameAsOldPassword(String email, String newPassword){
		boolean isSame = false;
		User user = this.getUserByEmail(email);
		String encodeNewPassword = this.encodePassword(newPassword);
		if(encodeNewPassword.equals(user.getPassword())){
			isSame = true;
		}
		return isSame;
	}
	
	@Override
	public JsonResponse validateRegisterForm(RegisterForm registerForm){
		
		if(registerForm == null){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
				
		if(registerForm != null && StringUtils.isNotBlank(registerForm.getEmail())){
			if(this.isExistingEmail(registerForm.getEmail())){
				return WebUtil.formatJsonResponse(Status.USER_EMAIL_EXIST);
			}
			/* Use Jquery to validate
			 * if(! WebUtil.isValidEmailAddress(registerForm.getEmail())){
				return WebUtil.formatJsonResponse(Status.USER_EMAIL_INCORRECT_FORMAT);
			}*/
		}
		
		// TODO enabled on production
		// for development, comment just for easy testing
		/*if(registerForm != null && StringUtils.isNotBlank(registerForm.getPassword())){
			if(! WebUtil.isValidPassword(registerForm.getPassword())){
				return WebUtil.formatJsonResponse(Status.USER_PASSWORD_INCORRECT_FORMAT);
			}
		}*/
		
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
	}
	
	@Override
	public JsonResponse validateResetPasswordForm(UpdatePasswordForm form){
		if(form == null || StringUtils.isBlank(form.getEmail()) || StringUtils.isBlank(form.getToken())){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
		
		if(form != null && StringUtils.isNotBlank(form.getEmail()) && StringUtils.isNotBlank(form.getToken())){
			if(! this.isEmailTokenMatched(form.getEmail(), form.getToken())){
				log.debug("Email and encoded token is not matched");
				return WebUtil.formatJsonResponse(Status.TOKEN_NOT_FOUND);
			}
		}
		
		if(form != null && StringUtils.isNotBlank(form.getNewPassword())){
			if(this.isNewPasswordSameAsOldPassword(form.getEmail(), form.getNewPassword())){
				log.debug("new password is same as old password");
				return WebUtil.formatJsonResponse(Status.USER_PASSWORD_DUPLICATE);
			}
			
			// TODO enabled on production
			// for development, comment just for easy testing
			/*
			if(! WebUtil.isValidPassword(form.getNewPassword())){
				return new JsonResponse(Status.USER_PASSWORD_INCORRECT_FORMAT);
			}*/
		}
		
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
	}

	@Override
	public JsonResponse validateUpdatePasswordForm(UpdatePasswordForm form){
		if(form == null || StringUtils.isBlank(form.getEmail())){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
		
		if(form != null && StringUtils.isNotBlank(form.getEmail()) && StringUtils.isNotBlank(form.getOldPassword())){
			if(! this.isEmailPasswordMathed(form.getEmail(), form.getOldPassword())){
				log.debug("Email and old password is not matched");
				return WebUtil.formatJsonResponse(Status.USER_PASSWORD_NOT_MATCH);
			}
		}
		
		if(form != null && StringUtils.isNotBlank(form.getNewPassword())){
			if(this.isNewPasswordSameAsOldPassword(form.getEmail(), form.getNewPassword())){
				log.debug("new password is same as old password");
				return WebUtil.formatJsonResponse(Status.USER_PASSWORD_DUPLICATE);
			}
			
			// TODO enabled on production
			// for development, comment just for easy testing
			/*
			if(! WebUtil.isValidPassword(form.getNewPassword())){
				return new JsonResponse(Status.USER_PASSWORD_INCORRECT_FORMAT);
			}*/
		}
		
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
	}
	
	@Override
	public JsonResponse validateSendEmailForm(RegisterForm form){
		if(form == null || StringUtils.isBlank(form.getEmail())){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
		
		if(! this.isExistingEmail(form.getEmail())){
			return WebUtil.formatJsonResponse(Status.USER_EMAIL_NOT_EXIST);
		}
		
		return WebUtil.formatJsonResponse(Status.COMMON_SUCCESS);
	}
	
	@Override
	public List<Role> getRoleListByUser(User user) {
		return roleMapper.getRoleListByUser(user);
	}
}

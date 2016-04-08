package com.bolehunt.gene.service.impl;

import java.util.ArrayList;
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
import com.bolehunt.gene.common.Constant.VerifyTokenType;
import com.bolehunt.gene.common.ErrorStatus;
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

@Service
public final class UserServiceImpl implements UserService {
	
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
	public User getUserByEmail(String email) {
		UserExample userEx = new UserExample();
		userEx.createCriteria().andEmailEqualTo(email);
		List<User> userList = userMapper.selectByExample(userEx);
		
		if(userList != null && userList.size() > 0){
			return userList.get(0);
		}else{
			log.error("Unable to find user with email = {}", email);
			return null;
		}
	}
	
	@Override
	@Transactional
	public void updateUserSelective(User user){
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	@Transactional
	public void insertUser(User user){
		userMapper.insert(user);
	}
	
	@Override
	@Transactional
	public User registerUser(User user) {
		user.setEnabled(Constant.UserEnableType.NOT_ENABLED.getValue());
		user.setPassword(this.encodePassword(user.getPassword()));
		userMapper.insert(user);
		
		log.debug("Insert User {} success", user.getEmail());
		
		verifyTokenService.insertVerifyToken(user.getEmail(), VerifyTokenType.VERIFICATION_EMAIL);
		
		return user;
	}
	
	@Override
	@Transactional
	public User enableUser(User user){
        if(user.getEnabled().equals(UserEnableType.ENABLED.getValue())){
        	throw new ApplicationException(ErrorStatus.USER_ALREADY_ENABLED);
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
	@Transactional
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
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String oldPassword =  user.getPassword();
			if(encoder.matches(password, oldPassword)) {
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
		String oldPassword =  user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(newPassword, oldPassword)) {
			isSame = true;
		}
		return isSame;
	}
	
	@Override
	public void validateRegisterForm(RegisterForm registerForm){
		List<ErrorStatus> errorList = new ArrayList<ErrorStatus>();
		
		if(registerForm == null){
			errorList.add(ErrorStatus.UNKNOWN_EXCEPTION);
		}
		
		if(registerForm != null && StringUtils.isNotBlank(registerForm.getEmail())){
			if(this.isExistingEmail(registerForm.getEmail())){
				errorList.add(ErrorStatus.USER_EMAIL_EXIST);
			}
		}
		
		// TODO enabled on production
		// for development, comment just for easy testing
		/*if(registerForm != null && StringUtils.isNotBlank(registerForm.getPassword())){
			if(! WebUtil.isValidPassword(registerForm.getPassword())){
				errorList.add(Status.USER_PASSWORD_INCORRECT_FORMAT);
			}
		}*/
		
		if(errorList.size() > 0) {
			throw new ApplicationException(errorList);
		}
		
	}
	
	@Override
	public void validateResetPasswordForm(UpdatePasswordForm form){
		List<ErrorStatus> errorList = new ArrayList<ErrorStatus>();
		
		if(form == null || StringUtils.isBlank(form.getEmail()) || StringUtils.isBlank(form.getToken())){
			errorList.add(ErrorStatus.UNKNOWN_EXCEPTION);
		}
		
		if(form != null && StringUtils.isNotBlank(form.getEmail()) && StringUtils.isNotBlank(form.getToken())){
			if(! this.isEmailTokenMatched(form.getEmail(), form.getToken())){
				log.debug("Email and encoded token is not matched");
				errorList.add(ErrorStatus.TOKEN_NOT_FOUND);
			}
		}
		
		if(form != null && StringUtils.isNotBlank(form.getNewPassword())){
			if(this.isNewPasswordSameAsOldPassword(form.getEmail(), form.getNewPassword())){
				log.debug("new password is same as old password");
				errorList.add(ErrorStatus.USER_PASSWORD_DUPLICATE);
			}
			
			// TODO enabled on production
			// for development, comment just for easy testing
			/*
			if(! WebUtil.isValidPassword(form.getNewPassword())){
				errorList.add(Status.USER_PASSWORD_INCORRECT_FORMAT);
			}*/
		}
		
		if(errorList.size() > 0) {
			throw new ApplicationException(errorList);
		}
		
	}

	@Override
	public void validateUpdatePasswordForm(UpdatePasswordForm form){
		List<ErrorStatus> errorList = new ArrayList<ErrorStatus>();
		
		if(form == null || StringUtils.isBlank(form.getEmail())){
			errorList.add(ErrorStatus.UNKNOWN_EXCEPTION);
		}
		
		if(form != null && StringUtils.isNotBlank(form.getEmail()) && StringUtils.isNotBlank(form.getOldPassword())){
			if(! this.isEmailPasswordMathed(form.getEmail(), form.getOldPassword())){
				log.debug("Email and old password is not matched");
				errorList.add(ErrorStatus.USER_PASSWORD_NOT_MATCH);
			}
		}
		
		if(form != null && StringUtils.isNotBlank(form.getNewPassword())){
			if(this.isNewPasswordSameAsOldPassword(form.getEmail(), form.getNewPassword())){
				log.debug("new password is same as old password");
				errorList.add(ErrorStatus.USER_PASSWORD_DUPLICATE);
			}
			
			// TODO enabled on production
			// for development, comment just for easy testing
			/*
			if(! WebUtil.isValidPassword(form.getNewPassword())){
				errorList.add(Status.USER_PASSWORD_INCORRECT_FORMAT);
			}*/
		}
		
		if(errorList.size() > 0) {
			throw new ApplicationException(errorList);
		}
		
	}
	
	@Override
	public void validateSendEmailForm(RegisterForm form){
		List<ErrorStatus> errorList = new ArrayList<ErrorStatus>();
		
		if(form == null || StringUtils.isBlank(form.getEmail())){
			errorList.add(ErrorStatus.UNKNOWN_EXCEPTION);
		}
		
		if(! this.isExistingEmail(form.getEmail())){
			errorList.add(ErrorStatus.USER_EMAIL_NOT_EXIST);
		}
		
		if(errorList.size() > 0) {
			throw new ApplicationException(errorList);
		}
	}
	
	@Override
	public List<Role> getRoleListByUser(User user) {
		return roleMapper.getRoleListByUser(user);
	}
}

package com.bolehunt.gene.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.common.Constant;
import com.bolehunt.gene.common.Constant.VerifyTokenType;
import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.domain.Menu;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.domain.UserExample;
import com.bolehunt.gene.domain.VerifyToken;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.exception.UnknownResourceException;
import com.bolehunt.gene.form.LoginForm;
import com.bolehunt.gene.form.RegisterForm;
import com.bolehunt.gene.form.UpdatePasswordForm;
import com.bolehunt.gene.persistence.UserMapper;
import com.bolehunt.gene.util.WebUtil;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private AppConfig config;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private VerifyTokenService verifyTokenService;
	
	@Override
	public Menu getUserMenu(User user) {
		
		Menu menu = new Menu();
		if(user != null) {
			menu.setUserLogin(true);
			menu.setTalent(true); // TODO
		}
		
		return menu;
	}
	
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
		user.setSalt(getSalt());
		user.setPassword(this.encodePassword(user.getPassword(), user.getSalt()));
		userMapper.insert(user);
		
		log.debug("Insert User {} success", user.getEmail());
		
		verifyTokenService.sendTokenEmail(user.getEmail(), VerifyTokenType.VERIFICATION_EMAIL);
		
		return user;
	}
	
	@Override
	public User enableUser(User user){
        if(user.isUserEnabled()){
        	throw new ApplicationException(Status.USER_ALREADY_ENABLED);
        }
        user.setEnabled(Constant.UserEnableType.ENABLED.getValue());
        this.updateUserSelective(user);
        
        log.debug("User is enabled succssfully");
        return user;
	}
	
	public static String getSalt() {
	    return new SecureRandomNumberGenerator().nextBytes().toBase64();
	}
	
	public String encodePassword(String rawPassword, String salt) {
	    return new Sha512Hash(rawPassword, this.getCombinedSalt(salt), this.getIterations()).toBase64();
	}
	
	public String getCombinedSalt(String salt) {
		return config.getShiroApplicationSalt() + ":" + salt;
	}
	
	public Integer getIterations() {
		return Integer.parseInt(config.getShiroHashIteration());
	}
	
	@Override
	public void updateUserPassword(User user, String newPassword){
		user.setPassword(this.encodePassword(newPassword, user.getSalt()));
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
			String encodePassword = this.encodePassword(password, user.getSalt());
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
		String encodeNewPassword = this.encodePassword(newPassword, user.getSalt());
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
	public JsonResponse validateLoginForm(LoginForm form){
		if(form == null){
			return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
		}
		
		String email = form.getEmail();
		String password = form.getPassword();
		boolean rememberMe = form.isRememberMe();
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if(! currentUser.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(email, password);
			token.setRememberMe(rememberMe);
			try{
	            currentUser.login(token);
	            log.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
			} catch ( UnknownAccountException uae ) {
				log.error("There is no user with email [{}] ", email);
				return WebUtil.formatJsonResponse(Status.USER_EMAIL_OR_PASSWORD_INCORRECT);
			} catch ( IncorrectCredentialsException ice ) {
				log.error("password doesn't match, email = {}, password = {}", email, password);
				return WebUtil.formatJsonResponse(Status.USER_EMAIL_OR_PASSWORD_INCORRECT);
			} catch ( LockedAccountException lae ) {
				log.error("email [{}] is locked");
				return WebUtil.formatJsonResponse(Status.UNKNOWN_EXCEPTION);
			}
		}
		
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
	
}

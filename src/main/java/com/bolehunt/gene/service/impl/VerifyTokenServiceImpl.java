package com.bolehunt.gene.service.impl;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.common.Constant;
import com.bolehunt.gene.common.Status;
import com.bolehunt.gene.common.Constant.VerifyTokenType;
import com.bolehunt.gene.domain.EmailServiceTokenModel;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.domain.VerifyToken;
import com.bolehunt.gene.domain.VerifyTokenExample;
import com.bolehunt.gene.exception.ApplicationException;
import com.bolehunt.gene.gateway.EmailServiceGateway;
import com.bolehunt.gene.persistence.VerifyTokenMapper;
import com.bolehunt.gene.service.UserService;
import com.bolehunt.gene.service.VerifyTokenService;

@Service
public class VerifyTokenServiceImpl implements VerifyTokenService {
	
	private static Logger log = LoggerFactory.getLogger(VerifyTokenServiceImpl.class);
	
	@Autowired
	private AppConfig config;

	@Autowired
	VerifyTokenMapper verifyTokenMapper; 
	
	@Autowired
	UserService userService;
	
	@Autowired
	private EmailServiceGateway emailServiceGateway;
	
	@Override
	@Transactional
	public void sendTokenEmail(String email, VerifyTokenType verifyTokenType){
		User user = userService.getUserByEmail(email);
		
		VerifyToken verifyToken = new VerifyToken(user.getId(), verifyTokenType, config.getTokenVerificationLiveMinutes());
			
		verifyTokenMapper.insert(verifyToken);
			
		log.debug("Insert verify token id = " + verifyToken.getId() + ", token type = " + verifyToken.getTokenType() + ", token = " + verifyToken.getToken() + " success");
		
		// send to email send gateway
		emailServiceGateway.sendVerificationToken(new EmailServiceTokenModel(user, verifyToken, config.getHostNameUrl()));
		
	}
	
	@Override
	@Transactional
    public VerifyToken validateVerificationToken(String base64EncodedToken) throws ApplicationException{
		log.debug("token = {}", base64EncodedToken);
		
        VerifyToken token = this.verifyToken(base64EncodedToken);
        
        User user = userService.getUserById(token.getUserId());
        
        userService.enableUser(user);
        
        this.sendTokenEmail(user.getEmail(), VerifyTokenType.REGISTRATION_EMAIL);
        
        return token;
    }
	
	@Transactional
	@Override
	public User validateLostPassToken(String base64EncodedToken){
		VerifyToken token = this.loadToken(base64EncodedToken);
		if (token.isTokenVerified()) {
            throw new ApplicationException(Status.TOKEN_ALREADY_VERIFIED);
        }
		if (token.hasExpired()) {
            throw new ApplicationException(Status.TOKEN_ALREADY_EXPIRED);
        }
        
        User user = userService.getUserById(token.getUserId());
        return user;
	}
	
	@Override
	public VerifyToken loadToken(String base64EncodedToken) throws ApplicationException{
        Assert.notNull(base64EncodedToken);
        String rawToken = new String(Base64.decodeBase64(base64EncodedToken));
        
        VerifyTokenExample ex = new VerifyTokenExample();
        ex.createCriteria()
        	.andTokenEqualTo(rawToken);
        List<VerifyToken> tokenList = verifyTokenMapper.selectByExample(ex);
        VerifyToken token = null;
        if(tokenList != null && tokenList.size() > 0){
        	token = tokenList.get(0);
        }
        
        if (token == null) {
            throw new ApplicationException(Status.TOKEN_NOT_FOUND);
        }
        if (token.hasExpired()) {
            throw new ApplicationException(Status.TOKEN_ALREADY_EXPIRED);
        }
        return token;
    }
	
	@Override
	public VerifyToken verifyToken(String base64EncodedToken){
		VerifyToken token = this.loadToken(base64EncodedToken);
        if (token.isTokenVerified()) {
            throw new ApplicationException(Status.TOKEN_ALREADY_VERIFIED);
        }
        token.setVerified(Constant.TokenVerifyType.VERIFIED.getValue());
        verifyTokenMapper.updateByPrimaryKey(token);

        log.debug("Token is verified successfully");
        return token;
	}
	
	
}

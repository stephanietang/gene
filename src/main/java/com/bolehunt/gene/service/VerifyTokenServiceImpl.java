package com.bolehunt.gene.service;

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
	public VerifyToken sendEmailVerifyToken(int userId){
		User user = userService.getUserById(userId);
		
		VerifyToken verifyToken = new VerifyToken(userId, VerifyTokenType.VERIFICATION_EMAIL, config.getTokenVerificationLiveMinutes());
		
		verifyTokenMapper.insert(verifyToken);
		
		log.debug("Insert verify token id = {}, token = {} success", verifyToken.getId(), verifyToken.getToken());
		
		// send to email send gateway
		emailServiceGateway.sendVerificationToken(new EmailServiceTokenModel(user, verifyToken, config.getHostNameUrl()));
		
		return verifyToken;
	}
	
	@Override
	public void sendVerificationEmail(String email){
		
		User user = userService.getUserByEmail(email);
		this.sendEmailVerifyToken(user.getId());
	}
	
	@Transactional
    public VerifyToken verifyToken(String base64EncodedToken) throws ApplicationException{
        VerifyToken token = this.loadToken(base64EncodedToken);
        if (token.isTokenVerified()) {
            throw new ApplicationException(Status.TOKEN_ALREADY_VERIFIED);
        }
        token.setVerified(Constant.TokenVerifyType.VERIFIED.getValue());
        verifyTokenMapper.updateByPrimaryKey(token);

        log.debug("Token is verified successfully");
        
        User user = userService.getUserById(token.getUserId());
        if(user.isUserEnabled()){
        	throw new ApplicationException(Status.USER_ALREADY_ENABLED);
        }
        user.setEnabled(Constant.UserEnableType.ENABLED.getValue());
        userService.updateUserSelective(user);
        
        log.debug("User is enabled succssfully");
        return token;
    }
	
	private VerifyToken loadToken(String base64EncodedToken) throws ApplicationException{
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
	
	
}

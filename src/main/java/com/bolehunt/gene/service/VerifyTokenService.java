package com.bolehunt.gene.service;

import com.bolehunt.gene.common.Constant.VerifyTokenType;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.domain.VerifyToken;
import com.bolehunt.gene.exception.ApplicationException;

public interface VerifyTokenService {
	
	public void sendTokenEmail(String email, VerifyTokenType verifyTokenType);
	
	public VerifyToken validateVerificationToken(String base64EncodedToken);
	
	public User validateLostPassToken(String base64EncodedToken);
	
	public VerifyToken loadToken(String base64EncodedToken) throws ApplicationException;
	
	public VerifyToken verifyToken(String base64EncodedToken);

}

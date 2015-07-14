package com.bolehunt.gene.service;

import com.bolehunt.gene.domain.VerifyToken;

public interface VerifyTokenService {
	
	public VerifyToken sendEmailVerifyToken(int userId);
	
	public void sendVerificationEmail(String email);
	
	public VerifyToken verifyToken(String base64EncodedToken);

}

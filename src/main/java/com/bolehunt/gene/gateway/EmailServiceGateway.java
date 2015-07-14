package com.bolehunt.gene.gateway;

import com.bolehunt.gene.domain.EmailServiceTokenModel;

public interface EmailServiceGateway {

	public void sendVerificationToken(EmailServiceTokenModel model);
}

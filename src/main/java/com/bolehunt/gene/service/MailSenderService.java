package com.bolehunt.gene.service;

import com.bolehunt.gene.domain.EmailServiceTokenModel;

public interface MailSenderService {

    public EmailServiceTokenModel sendVerificationEmail(EmailServiceTokenModel emailServiceTokenModel);

    public EmailServiceTokenModel sendRegistrationEmail(EmailServiceTokenModel emailServiceTokenModel);

    public EmailServiceTokenModel sendLostPasswordEmail(EmailServiceTokenModel emailServiceTokenModel);

    public void sendEmail() throws Exception; 

}

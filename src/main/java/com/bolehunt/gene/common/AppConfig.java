package com.bolehunt.gene.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({"classpath:application.properties"})
public class AppConfig {
	
	private final static String HOST_NAME_Url = "hostNameUrl";
	
	private final static String EMAIL_FROM_ADDRESS = "email.services.fromAddress";
	private final static String EMAIL_VERIFICATION_SUBJECT = "email.services.emailVerificationSubjectText";
	private final static String EMAIL_REGISTRATION_SUBJECT = "email.services.emailRegistrationSubjectText";
	private final static String EMAIL_LOST_PASSWORD_SUBJECT = "email.services.lostPasswordSubjectText";
	
	private final static String TOKEN_VERIFICATION_LIVE_MINUTES = "token.emailVerification.timeToLive.inMinutes";
	private final static String TOKEN_REGISTRATION_LIVE_MINUTES = "token.emailRegistration.timeToLive.inMinutes";
	private final static String TOKEN_LOST_PASSWORD_LIVE_MINUTES = "token.lostPassword.timeToLive.inMinutes";
	
	private final static String FILE_UPLOAD_PATH = "fileupload.path";
	
	@Autowired
    protected Environment env;

	public String getHostNameUrl(){
		return env.getProperty(HOST_NAME_Url);
	}
	
	public String getEmailFromAddress(){
		return env.getProperty(EMAIL_FROM_ADDRESS);
	}
	
	public String getVerificationEmailSubject(){
		return env.getProperty(EMAIL_VERIFICATION_SUBJECT);
	}
	
	public String getRegistrationEmailSubject(){
		return env.getProperty(EMAIL_REGISTRATION_SUBJECT);
	}
	
	public String getLostPasswordEmailSubject(){
		return env.getProperty(EMAIL_LOST_PASSWORD_SUBJECT);
	}
	
	public int getTokenVerificationLiveMinutes(){
		return Integer.parseInt(env.getProperty(TOKEN_VERIFICATION_LIVE_MINUTES));
	}
	
	public int getTokenRegistrationLiveMinutes(){
		return Integer.parseInt(env.getProperty(TOKEN_REGISTRATION_LIVE_MINUTES));
	}
	
	public int getTokenLostPasswordLiveMinutes(){
		return Integer.parseInt(env.getProperty(TOKEN_LOST_PASSWORD_LIVE_MINUTES));
	}
	
	public String getFileUploadPath(){
		return env.getProperty(FILE_UPLOAD_PATH);
	}
	
	
}

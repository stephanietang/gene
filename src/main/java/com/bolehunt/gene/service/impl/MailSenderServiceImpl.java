package com.bolehunt.gene.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.bolehunt.gene.common.AppConfig;
import com.bolehunt.gene.domain.EmailServiceTokenModel;
import com.bolehunt.gene.service.MailSenderService;

@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService {

    private static Logger log = LoggerFactory.getLogger(MailSenderServiceImpl.class);
    
    @Autowired
	private AppConfig config;
    
    private final JavaMailSender mailSender;
    
    private final VelocityEngine velocityEngine;
    
    @Autowired
    public MailSenderServiceImpl(JavaMailSender mailSender, VelocityEngine velocityEngine) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    public EmailServiceTokenModel sendVerificationEmail(final EmailServiceTokenModel emailVerificationModel) {
        Map<String, String> resources = new HashMap<String, String>();
          return sendEmail(emailVerificationModel, config.getVerificationEmailSubject(),
                  "VerificationEmail.vm", resources);
    }

    public EmailServiceTokenModel sendRegistrationEmail(final EmailServiceTokenModel emailVerificationModel) {
        Map<String, String> resources = new HashMap<String, String>();
        return sendEmail(emailVerificationModel, config.getRegistrationEmailSubject(),
                 "RegistrationEmail.vm", resources);
    }

    public EmailServiceTokenModel sendLostPasswordEmail(final EmailServiceTokenModel emailServiceTokenModel) {
        Map<String, String> resources = new HashMap<String, String>();
         return sendEmail(emailServiceTokenModel, config.getLostPasswordEmailSubject(),
                 "LostPasswordEmail.vm", resources);
    }

    private void addInlineResource(MimeMessageHelper messageHelper, String resourcePath, String resourceIdentifier) throws MessagingException {
        Resource resource = new ClassPathResource(resourcePath);
        messageHelper.addInline(resourceIdentifier, resource);
    }
    

    private EmailServiceTokenModel sendEmail(final EmailServiceTokenModel emailServiceTokenModel, final String emailSubject,
                                                         final String template, final Map<String, String> resources) {
    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
    		public void prepare(MimeMessage mimeMessage) throws Exception {
    			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
    			messageHelper.setTo(emailServiceTokenModel.getEmailAddress());
    			messageHelper.setFrom(config.getEmailFromAddress());
    			messageHelper.setSubject(emailSubject);
    			Map<String, Object> model = new HashMap<String ,Object>();
                model.put("model", emailServiceTokenModel);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
                messageHelper.setText(new String(text.getBytes(), "UTF-8"), true);
                for(String resourceIdentifier: resources.keySet()) {
                	addInlineResource(messageHelper, resources.get(resourceIdentifier), resourceIdentifier);
                }
            }
        };
        
        log.debug("Sending token to : {}, token type = {}", emailServiceTokenModel.getEmailAddress(), emailServiceTokenModel.getTokenType());
        
        try{
        	this.mailSender.send(preparator);
        }catch(MailException e){
        	log.error("Sending email error: " + e);
        }
        return emailServiceTokenModel;
    }
    
    // http client basic authentication
    // refer to https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientAuthentication.java
    public void sendEmail() throws Exception {
    	CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    	credentialsProvider.setCredentials(AuthScope.ANY, 
    	    new UsernamePasswordCredentials("api", "key-e583d89ebc8d4437e5f51d85867dea3d"));
    	
    	String url = "https://api.mailgun.net/v3/sandbox4f6869a6285c4e999372f9ccefe1d917.mailgun.org/messages";
    	
    	CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
    	
    	try{
    		HttpPost httpPost = new HttpPost(url);
        	
        	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        	urlParameters.add(new BasicNameValuePair("from", "Mailgun Sandbox <postmaster@sandbox4f6869a6285c4e999372f9ccefe1d917.mailgun.org>"));
        	urlParameters.add(new BasicNameValuePair("to", "BoleHunt <stephanie.capricorn@gmail.com>"));
        	urlParameters.add(new BasicNameValuePair("subject", "Hello BoleHunt"));
        	urlParameters.add(new BasicNameValuePair("text", "Congratulations BoleHunt, you just sent an email with Mailgun!  You are truly awesome!  You can see a record of this email in your logs: https://mailgun.com/cp/log .  You can send up to 300 emails/day from this sandbox server.  Next, you should add your own domain so you can send 10,000 emails/month for free."));
            
            HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
            httpPost.setEntity(postParams);
            
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            
            try{
            	log.debug("Response Code : " + httpResponse.getStatusLine().getStatusCode());
            	
            	log.debug("Entity : " + EntityUtils.toString(httpResponse.getEntity()));
            } finally {
            	httpResponse.close();
            }
    	} finally{
    		httpClient.close();
    	}
    }
    
}
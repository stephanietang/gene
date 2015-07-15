package com.bolehunt.gene.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
}
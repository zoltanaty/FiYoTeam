package com.fiyoteam.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
	
	private static final Logger log = LoggerFactory.getLogger(Email.class);
	
	private static final String SMTP_HOSTNAME = "smtp.gmail.com";
	private static final int SMTP_PORT = 587;
	private static final String AUTH_EMAIL = "***";
	private static final String AUTH_PASSWORD = "***";
	
	private SimpleEmail email;
	
	public Email(){
		this.email = new SimpleEmail();
		this.email.setHostName(SMTP_HOSTNAME);
		this.email.setSmtpPort(SMTP_PORT);
		this.email.setAuthentication(AUTH_EMAIL, AUTH_PASSWORD);
		this.email.setDebug(false);
		this.email.setStartTLSEnabled(true);
	}
	
	public void send(String recipient, String message){
		try {
			log.info("Trying to send Registration Confirmation email to: " + recipient);
			
			this.email.setFrom(AUTH_EMAIL);
			this.email.addTo(recipient);
			this.email.setSubject("Registration Confirmation");
			this.email.setMsg(message);
			this.email.send();
			
			log.info("Registration Confirmation email sent to: " + recipient);
		} catch (EmailException e) {
			log.debug("Failed to send Registration Confirmation email to: " + recipient + " - " + e);
		}	
	}
}

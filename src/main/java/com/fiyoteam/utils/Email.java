package com.fiyoteam.utils;

import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
	
	private static final Logger log = LoggerFactory.getLogger(Email.class);
	
	private static final String SMTP_HOSTNAME = "smtp.gmail.com";
	private static final int SMTP_PORT = 587;
	private static final String AUTH_EMAIL = "auth.fiyoteam@gmail.com";
	private static final String AUTH_PASSWORD = "gusto-lsi-data-sooth";
	
	private HtmlEmail email;
	
	public Email(){
		this.email = new HtmlEmail();
		this.email.setHostName(SMTP_HOSTNAME);
		this.email.setSmtpPort(SMTP_PORT);
		this.email.setAuthentication(AUTH_EMAIL, AUTH_PASSWORD);
		this.email.setDebug(false);
		this.email.setStartTLSEnabled(true);
	}
	
	public void send(String recipient, String subject,  String message){
		try {
			log.info("Trying to send Registration Confirmation email to: " + recipient);
			
			this.email.setFrom(AUTH_EMAIL, "FiYoTeam");
			this.email.addTo(recipient);
			this.email.setSubject(subject);
			this.email.setHtmlMsg(message);
			this.email.send();
			
			log.info("Registration Confirmation email sent to: " + recipient);
		} catch (Exception e) {
			log.debug("Failed to send Registration Confirmation email to: " + recipient + " - " + e);
		}	
	}
	
	public String composeActivationEmail(String firstName, String randomString){	
		String emailBody = "<p style='font-size:125%;'>Dear <b>" + firstName + "</b>,"
						 + "<br><br>Thank you for registering to <b>FiYoTeam</b>."
						 + "<br><br>Please activate your account in 48h with the following code: <b><i>" + randomString + "</i></b>"
						 + "<br><br>All the best,<br>The FiYoTeam</p>";
		
		return emailBody;
	}
}

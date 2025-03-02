package com.Social11.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class JavaMailService implements IjavaMailService {

	@Override
	public String SendMailToEmail(String email,long otp) {
		String result;
		String message="<span style='font-weight:560'>Dear User</span>,<br>"
				+ "Thankyou for signing up for our service. To complete the registeration<br>"
				+ "process, please enter the following verification code:<br>"
				+ "VERIFICATION CODE: "+"<span style='font-weight:560'>"+otp+"</span><br>"
				+ "Please note that this code will expire. if you have any issues with<br>"
				+ "the verification process or did not request this code, please contact our support team immediately.<br>"
				+ "Thank you for choosing our service.<br>"
				+ "<span style='font-weight:560'>Best regards</span>,<br>"
				+ "<span style='color:red;font-weight:550'>Jaiyto</span>";
        String subject= "Verification Code: "+otp;
        String to = email;
        String from= "jaiyto9@gmail.com";
        sendmail(message,subject,from,to);	
        if(isMessageSend) {
        	result="Message send Succesfully";
        }
        else{
        	result="";
        }
        return result;
	}
	public static boolean isMessageSend=false;
	public static void sendmail(String message,String subject,String from,String to) {
		String host="smtp.gmail.com";
    	
		/* get the system properties */
    	Properties properties = System.getProperties();
    	System.out.println("properties is:"+properties);
    	
		/* host set */
    	properties.put("mail.smtp.host", host);
    	properties.put("mail.smtp.port", "465");
    	properties.put("mail.smtp.ssl.enable", "true");
    	properties.put("mail.smtp.auth", "true");
    	
		/* Step:1 to get the session object */
    	Session session=Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("jaiyto9@gmail.com","khffbtvwoyngkwhj");
//				khffbtvwoyngkwhj Jaiyto
//				kufqrgsybkwzkmgo roshal awal
				
			}
    		
    	});
    	session.setDebug(true);
    	
		/* step:2 compose the message */
    	MimeMessage m =new MimeMessage(session);
    	 try {
    		 m.setFrom(from);
    		 m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    		 m.setSubject(subject);
    		 m.setContent(message, "text/html");
    		 Transport.send(m);
    		 System.out.println("send successfully");
    		 isMessageSend=true;
    	 }
    	 catch(Exception e) {
    		 System.out.println("incorrect something");
    	 }
  
	}
}

package sitha.rupp.MainTest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
 
public class EmailAttachmentSender {
 
	private static final String SMTP_HOST = "mail.lyhourinsurance.com";
	  private static final int SMTP_PORT = 465;
	  
	  private static final String USERNAME = "sitha.sopheap@lyhourinsurance.com";
	  private static final String PASSWORD = "Sophearom@636398";

	  public static void main(String[] args) throws EmailException
	  {
//	    SimpleEmail email = new SimpleEmail();
//	    email.setHostName(SMTP_HOST);
//	    email.setAuthentication(USERNAME, PASSWORD);
//	    email.setDebug(true);
//	    email.setSmtpPort(SMTP_PORT);
//	    email.setSSLOnConnect(true);
//
//	    email.addTo("sitha.sopheap@lyhourinsurance.com");
//
//	    email.setFrom(USERNAME, "Name of thesender");
//	    email.setSubject("Test message");
//	    email.setMsg("Hey, this is a simple text");
//	    email.send();
//	    
//	    EmailAttachment attachment = new EmailAttachment();
//	    attachment.setPath("mypictures/john.jpg");
//	    attachment.setDisposition(EmailAttachment.ATTACHMENT);
//	    attachment.setDescription("Picture of John");
//	    attachment.setName("John");
	    /*
	    MultiPartEmail email = new MultiPartEmail();
	    email.setHostName(SMTP_HOST);
	    email.setAuthentication(USERNAME, PASSWORD);
	    email.setDebug(true);
	    email.setSmtpPort(SMTP_PORT);
	    email.setSSLOnConnect(true);
	        
	    email.attach(new File("c:/upload/P01TV0020001800000.pdf"));

	    email.addTo("sitha.sopheap@lyhourinsurance.com");

	    email.setFrom(USERNAME, "Sender name");
	    email.setSubject("Test message with attachment");
	    email.setMsg("Hey there, here is my file");
	    email.send();
	    */
	    HtmlEmail email = new HtmlEmail();
	    email.setHostName(SMTP_HOST);
	    email.setAuthentication(USERNAME, PASSWORD);
	    email.setDebug(false);
	    email.setSmtpPort(SMTP_PORT);
	    email.setSSLOnConnect(true);
	    email.attach(new File("c:/upload/P01TV0020001800000.pdf"));
	    email.setHtmlMsg("<html>Test message with <b>HTML</b></html>");

	    email.addTo("sitha.sopheap@lyhourinsurance.com");
	    email.setFrom(USERNAME, "Sender");
	    email.setSubject("Test message with HTML");
	    email.send();

	  }
        
	  /*
		<dependency>
		  <groupId>org.apache.commons</groupId>
		  <artifactId>commons-email</artifactId>
		  <version>1.4</version>
		</dependency>
	   * */
    
}
package sitha.rupp.service;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.HtmlEmail;

import com.sun.mail.util.MailSSLSocketFactory;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;
public class SendEmailServices extends GenericDaSupport{
	PrincebankComponent component=PrincebankComponent.getInstance();
//	private static ExchangeService service;
//    private static Integer NUMBER_EMAILS_FETCH = 5; // only latest 5 emails/appointments are fetched.
   
    /* not use after mail server change to postfix
    static {
        try {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
            //ExchangeService 
            //service = new ExchangeService(ExchangeVersion.Exchange2010);
            //service.setUrl(new URI("https://outlook.office365.com/EWS/exchange.asmx"));
            
//            service.setUrl(new URI("https://mail.princeplc.com.kh/ews/Exchange.asmx"));
//            service.setUrl(new URI("https://mail.princebank.com.kh/ews/Exchange.asmx"));
            service.setUrl(new URI("https://192.168.1.88/ews/Exchange.asmx"));// Working on Server
            //service.setUrl(new URI("https://mail.princeplc.com.kh/ews/Exchange.asmx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public SendEmailServices() {
        //ExchangeCredentials credentials = new WebCredentials("prince_aml@princeplc.com.kh", "$PB@2k18", "mail.princeplc.com.kh");
        ExchangeCredentials credentials = new WebCredentials("isea", "$pb@2k18", "princeplc.com.kh");
        service.setCredentials(credentials);
    }
   */ 
    public void sendEmailNoAttachedFile(ArrayList<String> recipientsList,String subject,String ContentMessage) {
    	String host = "mail.lyhourinsurance.com";
		final String user = "sitha.sopheap@lyhourinsurance.com";	// change accordingly
		final String password = "Sophearom@636398";				// change accordingly
//		String to = "sopheap.sitha@princebank.com.kh";	// change accordingly

// 		Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");

		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.ssl.socketFactory", sf);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		session.setDebug(true);
		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			for(int i=0;i<recipientsList.size();i++) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientsList.get(i)));
			}
			message.setSubject(subject);
//			message.setText(ContentMessage); ok
			message.setText(ContentMessage,"UTF-8","HTML"); //OK

			// send the message
			Transport.send(message);
			System.out.println("...message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    public String emailContent(String User_Name, String resetCode, String HashLink) {
		String strEmail = "";
		strEmail = "<html>";
		strEmail += "<body>";
		strEmail += "<table>";
		// strEmail+="<tr>";
		strEmail += "<tr><td>Dear " + User_Name + ",</td></tr>";
		strEmail += "<tr><td><br>has received a request to purchase. If you did not initiate this request, please disregard.</td></tr>";
		strEmail += "<tr><td>Your PIN code is:" + resetCode + "</td></tr>";
		strEmail += "<tr><td>you can follow this <a href=" + HashLink
				 + ">link</a> and complete the form within 24 hours.</td></tr>";
		strEmail += "<tr><td>If you don't want to register or didn't request this, please ignore and delete this message.</td></tr>";
		strEmail += "<tr><td>Thank You,</td></tr>";
		strEmail += "</table>";
		strEmail += "</body>";
		strEmail += "</html>";

		return strEmail;
	}

	public int sendEmail(String Receiver, String subject, String ContentMessage,String attachedFilePath,String policyNumber) {
		final String sender = "online@lyhourinsurance.com";// change accordingly
		final String password = "LHI@2020!!@@";// change accordingly
		String host = "mail.lyhourinsurance.com";
		int port = 465;
		String sentStatus="";
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(host);
			email.setSmtpPort(port);
			email.setAuthentication(sender, password);
			email.setDebug(false);			
			email.setSmtpPort(port);
			email.setSSLOnConnect(true);
			email.setFrom(sender,"LY HOUR INSURANCE");
			email.setSubject(subject);
			
//			email.attach(new File("c:/upload/P01TV0020001800000.pdf")); //1
//			email.attach(new File("c:/upload/P01TV00200018-000-00.pdf"));//2
			if(!attachedFilePath.isEmpty()) {
				email.attach(new File(attachedFilePath));//2
			}
			email.setHtmlMsg(ContentMessage);
			email.addTo(Receiver);			
//			email.send();
			System.out.println("mail sent.....");
			sentStatus="SENT";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("mail sent failed.");
			sentStatus="FAILED";
		}
		String SQL="INSERT INTO APIREPORT.EMAIL_LOG(ID,IP_ADDRESS,SENDER,RECEIVER,POLH_POLNUM,DISPLAY_POLOCY,FILE_NAME,CREATE_DATE,STATUS)"
				+ " VALUES(APIREPORT.EMAIL_LOG_SEQ.NEXTVAL,'','"+sender+"', \r\n"
				+ "'"+Receiver+"',\r\n"
				+ "'"+policyNumber+"',\r\n"
				+ "'"+policyNumber+"',\r\n"
				+ "'"+attachedFilePath+"',\r\n"
				+ "sysdate,\r\n"
				+ "'"+sentStatus+"'\r\n"
				+ ");";

		System.out.println(SQL);
		return getJdbcTemplate().update(ClsHelper.Begin()+ SQL +ClsHelper.End());
	}
    
}


/*
 CREATE TABLE APIREPORT.EMAIL_LOG(
ID NUMBER NOT NULL
,IP_ADDRESS VARCHAR2(50)
,SENDER VARCHAR2(50)
,RECEIVER VARCHAR2(50)
,POLH_POLNUM VARCHAR2(50)
,DISPLAY_POLOCY VARCHAR2(50)
,FILE_NAME VARCHAR2(100)
,CREATE_DATE DATE
,STATUS VARCHAR2(50)
)

CREATE SEQUENCE APIREPORT.EMAIL_LOG_SEQ
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER
  NOKEEP
  GLOBAL;
 */

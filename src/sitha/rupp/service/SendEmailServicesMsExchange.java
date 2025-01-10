package sitha.rupp.service;

import microsoft.exchange.webservices.data.*;
import java.net.URI;
import java.util.*;

public class SendEmailServicesMsExchange {

	private static ExchangeService service;
    private static Integer NUMBER_EMAILS_FETCH = 5; // only latest 5 emails/appointments are fetched.
   
    static {
        try {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
            //service.setUrl(new URI("https://mail.princeplc.com.kh/ews/Exchange.asmx"));
            service.setUrl(new URI("https://192.168.1.67/ews/Exchange.asmx"));
            //service.setUrl(new URI("https://mail.princeplc.com.kh/ews/Exchange.asmx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public SendEmailServicesMsExchange() {
        //ExchangeCredentials credentials = new WebCredentials("prince_aml@princeplc.com.kh", "$PB@2k18", "mail.princeplc.com.kh");
        ExchangeCredentials credentials = new WebCredentials("isea", "$pb@2k18", "princeplc.com.kh");
        service.setCredentials(credentials);
    }
    
    public void sendEmail(ArrayList<String> recipientsList,String subject,String ContentMessage) {
        try {
            StringBuilder strBldr = new StringBuilder();
            //strBldr.append("<html>");
            //strBldr.append("<body>");
            strBldr.append(ContentMessage);
            //strBldr.append("<h3> hello prince bank.</h3>");
            //strBldr.append("</body>");
            //strBldr.append("</html>");
            //strBldr.append(Calendar.getInstance().getTime().toString() + " .");
            //strBldr.append("Thanks and Regards\\r\\n");
            //strBldr.append("MR. SOPHEAP SITHA");
            EmailMessage message = new EmailMessage(service);
            //MessageBody messageBody = new MessageBody();
            message.setSubject(subject);
            message.setBody(new MessageBody(strBldr.toString()));
            for (String string : recipientsList) {
                message.getToRecipients().add(string);
            }
            message.send(); // not copy in sent item of outlook when mail sent
            //message.sendAndSaveCopy(); // will sent and copy in sentitem of outlook if user login
            
            System.out.println("message sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    public static void main(String[] args) {
        SendEmailServices msees = new SendEmailServices();
        //msees.readEmails();
        //msees.readAppointments();
        ArrayList<String> recipientsList = new ArrayList<String>();
        //recipientsList.add("sopheap.sitha@gmail.com");
        recipientsList.add("sopheap.sitha@princebank.com.kh");
        recipientsList.add("sambath.veasna@princebank.com.kh");
        //recipientsList.add("sopheap.sitha@gmail.com");
        msees.sendEmail(recipientsList,"dear sir </p> your test here");
    }
    */
}

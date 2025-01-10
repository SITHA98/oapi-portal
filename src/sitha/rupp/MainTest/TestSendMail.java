package sitha.rupp.MainTest;

import java.util.ArrayList;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.service.SendEmailServices;

public class TestSendMail {

	public static void main(String[] args) {
		SendEmailServices emailService = new SendEmailServices();
		ArrayList<String> recipientsList= new ArrayList<String>();
		recipientsList.add("sopheap.sitha@gmail.com");
		String ContentMessage="Dear Sitha <br> How have you been.";
//		emailService.sendEmailNoAttachedFile(recipientsList, "Hello Sitha", ContentMessage);
		//emailService.sendEmail("sitha.sopheap@lyhourinsurance.com","subject","Dear Sitha <br> How have you <b><i>been</i></b>.","c:/upload/P01TV00200018-000-00.pdf");
		Application_Properties.SERIAL = 1;
		int x = emailService.sendEmail("sitha.sopheap@lyhourinsurance.com","subject","Dear Sitha <br> How have you <b><i>been</i></b>.","","");

		System.out.println("x:="+x);
	}

}

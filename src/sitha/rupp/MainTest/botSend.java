package sitha.rupp.MainTest;

import org.springframework.http.ResponseEntity;

//import sitha.rupp.bot.botLyHourInsurance;
import sitha.rupp.model.rpt_0003_policy_model;
import sitha.rupp.service.telegramServices;

public class botSend {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		https://api.telegram.org/bot1274689426:AAEEIfRVIpvJFPLHnYC7TLm1WGaJ5-w2SnI/sendMessage?chat_id=-417655431&text=bot send --group test my bot
//		https://api.telegram.org/bot1274689426:AAEEIfRVIpvJFPLHnYC7TLm1WGaJ5-w2SnI/sendMessage?chat_id=-431455375&text=***bot send*** --group LHI-IT
		telegramServices svr = new telegramServices();
		
		ResponseEntity<String> str = null;
		String strTextSend="hi send from bot</br>";
		strTextSend += "POLICY:01266666 \r\n";
		strTextSend += "INVOICE:01266666 \r\n";
		strTextSend += "INSURED NAME:ABCDEFADFDFJDFJDJFDJFD\r\n";
		strTextSend += "AMOUNT:000.15\r\n";
		//str = svr.sendTotelegram(strTextSend);
		System.out.println(str);
		/*
		botLyHourInsurance sn=new botLyHourInsurance();
		rpt_0003_policy_model policy =new rpt_0003_policy_model();
		policy.setPOLICY_NUMBER("123");
		policy.setINSURED_NAME("bot");
		policy.setINVOICE("1233");
		policy.setInvoice_amount(12.00);
		String str2 = sn.sendTele(policy);
		*/
		
	}

}

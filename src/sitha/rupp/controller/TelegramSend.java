//package sitha.rupp.controller;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import sitha.rupp.bot.botLyHourInsurance;
//import sitha.rupp.configuration.Application_Properties;
//import sitha.rupp.model.rpt_0003_policy_model;
//import sitha.rupp.service.rpt_0003_policyServices;
//
//@Configuration
//@EnableScheduling
//public class TelegramSend {
////	@Scheduled(fixedRate = 1200000) // 20min =1200000/1000/60
//	@Scheduled(fixedRate = 1000) // 20min =1200000/1000/60
//	public void autoRunSelect() {
//		Application_Properties.SERIAL=1;
//		rpt_0003_policyServices rptPolicy = new rpt_0003_policyServices();
//		List<rpt_0003_policy_model> alist = new ArrayList<rpt_0003_policy_model>();
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");  
//		LocalDateTime now = LocalDateTime.now();  
////		System.out.println(dtf.format(now));
//		String today=dtf.format(now);
////		alist = rptPolicy.getPolicyAndEndorsmentList("01-DEC-2020","01-DEC-2020");
//		alist = rptPolicy.getPolicyAndEndorsmentList(today,today);
//		System.out.println("....Scheduling run....");
//		try {
//			int x;
//			for(int i=0;i<alist.size();i++) {
//				int a=rptPolicy.checkExistedPolicyInvoiceNumber(alist.get(i).getPOLICY_NUMBER(),alist.get(i).getINVOICE());
//				if(a>0) {
//					System.out.println(alist.get(i).getPOLICY_NUMBER()+" And "+alist.get(i).getINVOICE()+ "*********no action*******");
//					continue;
//				}else {
//					// insert into table and send to telegram
//					rpt_0003_policy_model policy=new rpt_0003_policy_model();
//					policy.setPOLICY_NUMBER(alist.get(i).getPOLICY_NUMBER());
//					policy.setINSURED_NAME(alist.get(i).getINSURED_NAME());
//					policy.setINVOICE(alist.get(i).getINVOICE());
//					policy.setInvoice_amount(alist.get(i).getInvoice_amount());
//					x=rptPolicy.insertPolicy(policy);
////					System.out.println("x:"+x+"  -> "+policy.toString());
//					botLyHourInsurance sendTele=new botLyHourInsurance();
//					String x1=sendTele.sendTele(policy);
//					System.out.println("x:"+x1);
//					//send to telegram
//				}
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
////			logger.info("error "+e.getMessage());
//		}
//	}
//}

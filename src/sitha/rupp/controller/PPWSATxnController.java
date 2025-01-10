package sitha.rupp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.model.SYS_EVENT_LOG;
import sitha.rupp.service.PPWSATxnDa;
import sitha.rupp.service.PrincebankComponent;

@Controller
@RequestMapping(value = "/partnership")
public class PPWSATxnController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	Gson gson = new Gson();
	private final Logger logger = LoggerFactory.getLogger(PPWSATxnController.class);

	@ResponseBody
	@RequestMapping(value = "/casa-txn", method = RequestMethod.GET)
	String casaTxn(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("account_no") String account_no,
			@RequestParam("dateFrom") String dateFrom,
			@RequestParam("dateTo") String dateTo) 
	throws Exception {
		if (account_no == null || dateFrom == null || dateTo == null) { return "0"; }
		
		try {			
			String OAUTH2_URI_SERVER = "https://10.80.80.46:8246/princebankservice/1.0.0/getCASAStmt?";
			String param = "account_no="+account_no+"&dateFrom="+dateFrom+"&dateTo="+dateTo;
			System.out.println("request to: "+OAUTH2_URI_SERVER+param);
			String data = PPWSATxnDa.getCustInfo(OAUTH2_URI_SERVER,param).toString();
			if (data!="" || data!=null) {
				int USER_ID = component.USER_ID(request);
				SYS_EVENT_LOG event = new SYS_EVENT_LOG();
				event = event.initEvent(0, USER_ID,"View Report PPWSA Account No :" + account_no + ",from date :" + dateFrom + " to :" + dateTo);
				Application_Properties.SERIAL = 1;
				int i = component.getJdbcTemplate().update(event.strSQL(event));
				System.out.println("eventLog: "+i);
			}
			return gson.toJson(data);
		}catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}
	
}

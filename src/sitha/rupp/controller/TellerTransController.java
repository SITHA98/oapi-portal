package sitha.rupp.controller;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import sitha.rupp.configuration.Application_Properties;
//import sitha.rupp.service.FinJournalService;
//import sitha.rupp.service.LoandrawdownService;
import sitha.rupp.service.PrincebankComponent;
//import sitha.rupp.service.TellerTransService;
import sitha.rupp.service.UserDa;
import sitha.rupp.view.ReportEngine;

@Controller
public class TellerTransController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/DailyTellerTotalTxn", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView loanpaid(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
//			 ,@RequestParam("branch_id") String branch_id	
//			 ,@RequestParam("curr_id") String curr_id	
//			 ,@RequestParam("dateFrom") String dateFrom
//			 ,@RequestParam("dateTo") String dateTo
			 ,@RequestParam("reportType") int reportType
//			 ,@RequestParam("user_name") String user_name	
	 
	) throws Exception {
		Application_Properties.SERIAL=1;
//		System.out.println("Calling Teller Transaction Report user :"+user_name+" , from date :"+dateFrom+" to :"+dateTo);
		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
//		int USER_ID=component.USER_ID(request);
//		String user_name1 = null;
//		if(!user_name.trim().equalsIgnoreCase("")){
//			user_name1=component.getUSER_NAME(Integer.parseInt( user_name ));
//		}
//		
		
		Application_Properties.SERIAL=1;
		ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
		reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/khmer-unicode/KhmerUnicode.jasper"), para);
		if ( reportType == 1 ) {
			response.setContentType("Application/pdf");
			reportEngine.exportPDF(outputStream);
		} else if ( reportType == 0 ) {
			response.setContentType("text/html");
			reportEngine.exportHtml(outputStream, request);
		} else if ( reportType == 2 ) {
			reportEngine.exportExcel(outputStream, request, response,"Teller_Trans");
		}
		return null;

	}
}

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
import sitha.rupp.model.SYS_EVENT_LOG;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;
import sitha.rupp.view.ReportEngine;

@Controller
public class PaymentClaimController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/PAYMENT_CLAIM_HC00", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("PaymentNumber") String PaymentNumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//Application_Properties.SERIAL=3;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		try {
			System.out.println("PaymentNumber:" + PaymentNumber);
			para.put("parPAYMENT_NUMBER", PaymentNumber);			
			//para.put("parCondictionAndClauses", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/PAYMENT_HCXX.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Payment");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", Payment number : " + PaymentNumber);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	@RequestMapping(value = "/PAYMENT_CLAIM_CV00", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView PAYMENT_CLAIM_CV00(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("PaymentNumber") String PaymentNumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//Application_Properties.SERIAL=3;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		try {
			System.out.println("PaymentNumber:" + PaymentNumber);
			para.put("parPaymentNumber", PaymentNumber);			
			//para.put("parCondictionAndClauses", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/Payment-Report-Claim-CV00.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Payment");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", Payment number : " + PaymentNumber);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/PAYMENT_CLAIM_PA00", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView PAYMENT_CLAIM_PA00(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("PaymentNumber") String PaymentNumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//Application_Properties.SERIAL=3;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		try {
			System.out.println("PaymentNumber:" + PaymentNumber);
			para.put("parPaymentNumber", PaymentNumber);			
			//para.put("parCondictionAndClauses", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/Payment-Report-Claim-PA00.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Payment");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", Payment number : " + PaymentNumber);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
}

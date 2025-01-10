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

import groovyjarjarcommonscli.HelpFormatter;
import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.SYS_EVENT_LOG;
import sitha.rupp.platform.collection.PData;
import sitha.rupp.service.InvoiceServices;
//import sitha.rupp.service.FinJournalService;
//import sitha.rupp.service.LoandrawdownService;
import sitha.rupp.service.PrincebankComponent;
//import sitha.rupp.service.TellerTransService;
import sitha.rupp.service.UserDa;
import sitha.rupp.view.ReportEngine;

@Controller
public class PrintInvoiceController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/PrintInvoice", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
//			 ,@RequestParam("branch_id") String branch_id	
//			 ,@RequestParam("curr_id") String curr_id	
//			 ,@RequestParam("dateFrom") String dateFrom
//			 ,@RequestParam("dateTo") String dateTo
			 ,@RequestParam("reportType") int reportType
//			 ,@RequestParam("user_name") String user_name	
			 ,@RequestParam("policynumber") String policynumber
			 ,@RequestParam("vattin") String vattin
	 
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
		
//		Application_Properties.SERIAL=1;
		try {
			System.out.println("parPolicyNumber:" + policynumber);
			String policyNotFormat=ClsHelper.PolicyNumber(policynumber);
			para.put("parPolicyNumber", policyNotFormat);
			para.put("parVattin", vattin);
			para.put("parImageHeader", servletContext.getRealPath("images/1.png"));
			para.put("parImageDetail", servletContext.getRealPath("images/2.png"));
			para.put("parImageFooter", servletContext.getRealPath("images/3.png"));
			para.put("parCEOSignature", servletContext.getRealPath(ClsHelper.img_ceoSignature));
			para.put("parStamp", servletContext.getRealPath(ClsHelper.img_stamp));
			para.put("parCeoKHName", servletContext.getRealPath(ClsHelper.img_ceo_kh_name));
			ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/khmer-unicode/Print-Invoice.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Teller_Trans");
			}
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +",policynumber :" + policynumber );
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@RequestMapping(value = "/Summary", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicySummary(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
//			 ,@RequestParam("branch_id") String branch_id	
//			 ,@RequestParam("curr_id") String curr_id	
//			 ,@RequestParam("dateFrom") String dateFrom
//			 ,@RequestParam("dateTo") String dateTo
			 ,@RequestParam("reportType") int reportType
//			 ,@RequestParam("user_name") String user_name	
			 ,@RequestParam("policynumber") String policynumber
			 //,@RequestParam("vattin") String vattin
	 
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
		
//		Application_Properties.SERIAL=1;
		try {
			System.out.println("parPolicyNumber:" + policynumber);
			String policyNotFormat=ClsHelper.PolicyNumber(policynumber);
			para.put("parPolicyNumber", policyNotFormat);			
			ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/khmer-unicode/Summary.jasper"),
					para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Teller_Trans");
			}
//			int USER_ID = component.USER_ID(request);
//			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
//			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +",policynumber :" + policynumber );
//			Application_Properties.SERIAL = 1;
//			int i = component.getJdbcTemplate().update(event.strSQL(event));
//			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/detailCondClause", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView detailCondClause(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			,@RequestParam("reportType") int reportType	
			,@RequestParam("policynumber") String policynumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;
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
		try {
			System.out.println("parPolicyNumber:" + policynumber);
			String policyNotFormat=ClsHelper.PolicyNumber(policynumber);
			para.put("parDisplayPolicyNumber",policynumber);
			para.put("parPolicyNumber", policyNotFormat);			
			ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/DETAIL-COND-CLAUSE/DetailConditionAndClauses.jasper"),
					para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "condiction and clause");
			}
//			int USER_ID = component.USER_ID(request);
//			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
//			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +",policynumber :" + policynumber );
//			Application_Properties.SERIAL = 1;
//			int i = component.getJdbcTemplate().update(event.strSQL(event));
//			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/PrintInvoiceCovid19", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoiceCovid19(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
//			 ,@RequestParam("branch_id") String branch_id	
//			 ,@RequestParam("curr_id") String curr_id	
//			 ,@RequestParam("dateFrom") String dateFrom
//			 ,@RequestParam("dateTo") String dateTo
			 ,@RequestParam("reportType") int reportType
//			 ,@RequestParam("user_name") String user_name	
			 ,@RequestParam("policynumber") String policynumber
			 ,@RequestParam("vattin") String vattin
	 
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
//		Application_Properties.SERIAL=1;
		try {
			System.out.println("parPolicyNumber:" + policynumber);
			String policyNotFormat=ClsHelper.PolicyNumber(policynumber);
			para.put("parPolicyNumber", policyNotFormat);
			para.put("parVattin", vattin);
			para.put("parImageHeader", servletContext.getRealPath("WEB-INF/reports/Invoice/1.png"));
			para.put("parImageDetail", servletContext.getRealPath("WEB-INF/reports/Invoice/2.png"));
			para.put("parImageFooter", servletContext.getRealPath("WEB-INF/reports/Invoice/3.png"));
			para.put("parCEOSignature", servletContext.getRealPath(ClsHelper.img_ceoSignature));
			para.put("parStamp", servletContext.getRealPath(ClsHelper.img_stamp));
			para.put("parCeoKHName", servletContext.getRealPath(ClsHelper.img_ceo_kh_name));
			//covid policy data
			InvoiceServices invSvr=new InvoiceServices();
			PData data=invSvr.getHealthCareCovid19(policynumber);
			para.put("PRODUCT",data.getString("PRODUCT"));
			para.put("POLICY_NO",data.getString("POLICY_NO"));
			para.put("EFFECTIVE_DATE",data.getString("EFFECTIVE_DATE"));
			para.put("EXPIRY_DATE",data.getString("EXPIRY_DATE"));
			para.put("ADMINFEE",data.getString("adminfee"));
			para.put("TOTAL_PREMIUM",data.getString("total_premium"));
			para.put("GROSS_PREMIUM",data.getString("gross_premium"));
			
			ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/Invoice/Invoice_covid_19.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Teller_Trans");
			}
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +",policynumber :" + policynumber );
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

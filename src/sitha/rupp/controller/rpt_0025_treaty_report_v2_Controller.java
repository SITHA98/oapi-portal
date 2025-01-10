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
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.SYS_EVENT_LOG;
//import sitha.rupp.service.FinJournalService;
//import sitha.rupp.service.LoandrawdownService;
import sitha.rupp.service.PrincebankComponent;
//import sitha.rupp.service.TellerTransService;
import sitha.rupp.service.UserDa;
import sitha.rupp.view.ReportEngine;

@Controller
public class rpt_0025_treaty_report_v2_Controller extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/rpt0025TreatyReport_v2", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView TreatyReport_v2(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
//			 ,@RequestParam("branch_id") String branch_id	
//			 ,@RequestParam("curr_id") String curr_id
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("productcode") String productcode	
			 ,@RequestParam("fromdate") String fromdate
			 ,@RequestParam("todate") String todate
	 
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
			Application_Properties.SERIAL=1;	
						
			String product=component.getString("SELECT PRODUCT_VALUE FROM APIREPORT.PRODUCTS WHERE ID='"+productcode+"'", "PRODUCT_VALUE");
			//			
//			if (productcode.equals("1")) {
//				product="'MC','MC00','CV','CV00','PV','PV00'";
//			}else if(productcode.equals("2")) {
//				product="'PA00','PA01','HC00','HC01'";
//			}else {
//				product="'MC','MC00','CV','CV00','PV','PV00','PA00','PA01','HC00','HC01'";
//			}
//			
			System.out.println("productcode:"+productcode+",product: "+product);
			para.put("parProductCode",product);
			para.put("parFromDate", fromdate);
			para.put("parToDate", todate);
			ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/LIABILITY/TREATY_REPORT_V2.jasper"),
					para);
			
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				String productCate=component.getString("SELECT PRODUCT_NAME FROM APIREPORT.PRODUCTS WHERE ID='"+productcode+"'", "PRODUCT_NAME");
				reportEngine.exportExcel(outputStream, request, response, "TREATY_V2_"+productCate+"_REPORT_FROM_"+fromdate.toUpperCase()+"_TO_"+todate.toUpperCase());
			}
			
			//if (data!="" || data!=null) {
				int USER_ID = component.USER_ID(request);
				SYS_EVENT_LOG event = new SYS_EVENT_LOG();
				event = event.initEvent(reportType, USER_ID,this.getClass().getName() +",from date :" + fromdate + " to :" + todate);
				Application_Properties.SERIAL = 1;
				int i = component.getJdbcTemplate().update(event.strSQL(event));
				System.out.println("eventLog: "+i);
			//}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	@RequestMapping(value = "/rpt0025TreatyReport_v2_groupby", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView TreatyReport_v2_groupby(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap

			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("productcode") String productcode	
			 ,@RequestParam("fromdate") String fromdate
			 ,@RequestParam("todate") String todate
	 
	) throws Exception {
		Application_Properties.SERIAL=1;
//		System.out.println("Calling Teller Transaction Report user :"+user_name+" , from date :"+dateFrom+" to :"+dateTo);
		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();

		try {
			Application_Properties.SERIAL=1;	
						
			String product=component.getString("SELECT PRODUCT_VALUE FROM APIREPORT.PRODUCTS WHERE ID='"+productcode+"'", "PRODUCT_VALUE");
			System.out.println("productcode:"+productcode+",product: "+product);
			para.put("parProductCode",product);
			para.put("parFromDate", fromdate);
			para.put("parToDate", todate);
			ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/LIABILITY/TREATY_REPORT_V3_GROUPBY.jasper"),
					para);
			
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				String productCate=component.getString("SELECT PRODUCT_NAME FROM APIREPORT.PRODUCTS WHERE ID='"+productcode+"'", "PRODUCT_NAME");
				reportEngine.exportExcel(outputStream, request, response, "TREATY_V2_"+productCate+"_REPORT_FROM_"+fromdate.toUpperCase()+"_TO_"+todate.toUpperCase());
			}
			
			//if (data!="" || data!=null) {
				int USER_ID = component.USER_ID(request);
				SYS_EVENT_LOG event = new SYS_EVENT_LOG();
				event = event.initEvent(reportType, USER_ID,this.getClass().getName() +",from date :" + fromdate + " to :" + todate);
				Application_Properties.SERIAL = 1;
				int i = component.getJdbcTemplate().update(event.strSQL(event));
				System.out.println("eventLog: "+i);
			//}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}

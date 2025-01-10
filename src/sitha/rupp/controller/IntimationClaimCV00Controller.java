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
public class IntimationClaimCV00Controller extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/IntimationClaimCV00", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("claimnumber") String claimnumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//Application_Properties.SERIAL=3;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		try {
			System.out.println("claimnumber:" + claimnumber);
			para.put("parClaimNumber", claimnumber);			
			//para.put("parCondictionAndClauses", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/Intimation-CV00-Claim.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "PA00-claim-intimation");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", claim number : " + claimnumber);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	@RequestMapping(value = "/IntimationClaimPA00", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView IntimationClaimPA00(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("claimnumber") String claimnumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//Application_Properties.SERIAL=3;

		try {
			System.out.println("claimnumber:" + claimnumber);
			para.put("parClaimNumber", claimnumber);			
			//para.put("parCondictionAndClauses", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/Intimation-PA00-Claim.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "PA00-claim-intimation");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", claim number : " + claimnumber);
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

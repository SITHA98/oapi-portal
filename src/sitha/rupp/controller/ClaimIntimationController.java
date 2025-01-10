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
public class ClaimIntimationController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/ClaimIntimation", method = RequestMethod.GET, headers = "Accept=*/*")
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
		String product_id=claimnumber.substring(3,5);
		try {
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			if(product_id.equalsIgnoreCase("hc")) {
				System.out.println("claimnumber:" + claimnumber);
				para.put("parClaimNumber", claimnumber);			
				//para.put("parCondictionAndClauses", strSpecialCondition);
				reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/CLAIM-HCXX-INTIMATION-REPORT.jasper"),para);
				if (reportType == 1) {
					response.setContentType("Application/pdf");
					reportEngine.exportPDF2(outputStream,request);
				} else if (reportType == 0) {
					response.setContentType("text/html");
					reportEngine.exportHtml(outputStream, request);
				} else if (reportType == 2) {
					reportEngine.exportExcel(outputStream, request, response, "PA00-claim-intimation");
				}
			}
			if(product_id.equalsIgnoreCase("CV") || product_id.equalsIgnoreCase("CV00") || 
				product_id.equalsIgnoreCase("PV") || product_id.equalsIgnoreCase("PV00") ||
				product_id.equalsIgnoreCase("MC") || product_id.equalsIgnoreCase("MC00")
			){
				System.out.println("claimnumber:" + claimnumber);
				para.put("parClaimNumber", claimnumber);			
				//para.put("parCondictionAndClauses", strSpecialCondition);
				reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/CLAIM-REPORTS/Claim-MOTOR-Intimation.jasper"),para);
			
				if (reportType == 1) {
					response.setContentType("Application/pdf");
					reportEngine.exportPDF2(outputStream,request);
				} else if (reportType == 0) {
					response.setContentType("text/html");
					reportEngine.exportHtml(outputStream, request);
				} else if (reportType == 2) {
					reportEngine.exportExcel(outputStream, request, response, product_id);
				}
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

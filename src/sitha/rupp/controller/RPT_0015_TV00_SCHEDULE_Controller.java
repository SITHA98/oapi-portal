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
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.SendEmailServices;
import sitha.rupp.service.UserDa;
import sitha.rupp.service.rpt_0015_TV00_ScheduleServices;
import sitha.rupp.view.ReportEngine;

@Controller
public class RPT_0015_TV00_SCHEDULE_Controller extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();
		
	@RequestMapping(value = "/0015Schedule", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 //,@RequestParam("extNum") String extNum	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		
		rpt_0015_TV00_ScheduleServices tv00Service=new rpt_0015_TV00_ScheduleServices();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();		
		String excess_detail = tv00Service.getExcessDetail(req_policynumber);		
		
		try {
			String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
			para.put("parPolicyNumber", policyNumber);
			para.put("parExcessDetail", excess_detail);						
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/TRAVEL-INSURANCE-TV00/TV00-SCHEDULE-REPORT.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
				
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "TV00-SCHEDULE");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policy number : " + req_policynumber);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/0015TV00TravelCertificate", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printTravelCertificate(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 //,@RequestParam("extNum") String extNum	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0015_TV00_ScheduleServices tv00Service=new rpt_0015_TV00_ScheduleServices();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();		
//		
		String path="";
		try {
			
			String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
			para.put("parPolicyNumber", policyNumber);		
			para.put("parLHILogo", servletContext.getRealPath("images/LHI_LOGO.jpg"));
			para.put("parBackground", servletContext.getRealPath("images/TV00-CERTIFICATE.png"));
			para.put("parEmailIcon", servletContext.getRealPath("images/mail.png"));
			para.put("par24h", servletContext.getRealPath("images/24h.png"));
			para.put("parWorld", servletContext.getRealPath("images/world.png"));
			para.put("parCamflag", servletContext.getRealPath("images/cam_flag.png"));
			para.put("parStamp", servletContext.getRealPath("images/Stamp_LHI.png"));
			
			String plan12 = tv00Service.getTravelPlan(req_policynumber);
			if(plan12.equalsIgnoreCase("PLAN1")) {
				para.put("parPlan1", servletContext.getRealPath("images/plan1.png"));
			}else {
				para.put("parPlan2", servletContext.getRealPath("images/plan2.png"));
			}
//			para.put("parPlan2", servletContext.getRealPath("images/plan2.png"));
			para.put("parCEOSignature", servletContext.getRealPath("images/CEO_SIGNATURE.png"));
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/TRAVEL-INSURANCE-TV00/TV00-CERTIFICATE-REPORT.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream, request); //OK
//				String path="img/"+policyNumber.replace("/", "")+".pdf";
				
				if (ClsHelper.checkOS()==0){
					path="C:/Upload/"+policyNumber.replace("/", "-")+".pdf";	
				}else {
					path="/usr/mycore/agile-images/"+policyNumber.replace("/", "-")+".pdf";	
				}

				//reportEngine.exportPDFCreatePdfFile(outputStream,request,path);
				
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "TV00-SCHEDULE");
			}
			
			//SendEmailServices sendMail = new SendEmailServices();
			//int x=sendMail.sendEmail("sitha.sopheap@lyhourinsurance.com","subject","Dear Sitha <br> How have you <b><i>been</i></b>.",path,policyNumber);
			
			//System.out.println("insert to mail log "+x);
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policy number : " + req_policynumber);
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

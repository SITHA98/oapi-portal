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
import sitha.rupp.model.PA00TotalBenefit_model;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;
import sitha.rupp.service.rpt_0006_PA00Services;
import sitha.rupp.view.ReportEngine;

@Controller
public class PA00Controller extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/PAController", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType	
			 ,@RequestParam("policynumber") String policynumber
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0006_PA00Services rpt_pa00 = new rpt_0006_PA00Services();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
	
		//Application_Properties.SERIAL=3;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		policynumber=ClsHelper.PolicyNumber(policynumber);
		String POLH_SYS_ID=rpt_pa00.getSysIdByPolicyNumber(policynumber);
		String strSpecialCondition = rpt_pa00.getFireEngineerList(POLH_SYS_ID);
		PA00TotalBenefit_model pa00TotalBen=new PA00TotalBenefit_model();
		pa00TotalBen=rpt_pa00.getTotalBenefitByPolicyNumber(policynumber);
		try {

			System.out.println("parPolicyNumber:" + policynumber);
			para.put("parPolicyNumber", policynumber);
			para.put("parTotalRecord",Integer.parseInt(pa00TotalBen.getTotalRecord()));
			para.put("parTotalBenA",Double.parseDouble(pa00TotalBen.getTotalBenA()));
			para.put("parTotalBenB",Double.parseDouble(pa00TotalBen.getTotalBenB()));
			para.put("parTotalBenC",Double.parseDouble(pa00TotalBen.getTotalBenC()));
			para.put("parCondictionAndClauses", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/PA00-Report-Main.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "PA00");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/PAController_attachedlist", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPA00PolicyDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String policynumber	 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0006_PA00Services rpt_pa00 = new rpt_0006_PA00Services();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
	
//		Application_Properties.SERIAL=2;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		policynumber=ClsHelper.PolicyNumber(policynumber);
		String POLH_SYS_ID=rpt_pa00.getSysIdByPolicyNumber(policynumber);
		String Product_Code=rpt_pa00.getProductCodeByPolNum(policynumber);
		String strSpecialCondition = rpt_pa00.getFireEngineerList(POLH_SYS_ID);
		PA00TotalBenefit_model pa00TotalBen=new PA00TotalBenefit_model();
		pa00TotalBen=rpt_pa00.getTotalBenefitByPolicyNumber(policynumber);
		try {

			System.out.println("parPolicyNumber:" + policynumber);
			para.put("parPolicyNumber", policynumber);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			if(Product_Code.equalsIgnoreCase("PA00")) {
				reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/PA00-Report-Detail.jasper"),para);
			}else if(Product_Code.equalsIgnoreCase("PA01")) {
				reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/PA01-Report-Detail.jasper"),para);
			}
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, Product_Code);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/PAHC_CondAndClause", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPAHC_CondictionAndClause(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String policynumber 
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0006_PA00Services rpt_pa00 = new rpt_0006_PA00Services();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
	
		String ori_policynumber=ClsHelper.PolicyNumber(policynumber);
		String POLH_SYS_ID=rpt_pa00.getSysIdByPolicyNumber(ori_policynumber);

		try {
			
			System.out.println("parPolicyNumber:" + policynumber);
			para.put("parPolicyNumber", policynumber);
			para.put("parPOLH_SYS_ID",POLH_SYS_ID);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/ConditionAndClauses.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "PA00");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

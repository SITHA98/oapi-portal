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
import sitha.rupp.model.SYS_EVENT_LOG;
import sitha.rupp.model.rpt_0014_PA00_EndorsementModel;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;
import sitha.rupp.service.rpt_0006_PA00Services;
import sitha.rupp.service.rpt_0014_PA00_endorsementServices;
import sitha.rupp.view.ReportEngine;

@Controller
public class rpt_0008_NewPolicyScheduleController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();
	private String strImgHospital_Loc_QR = "images/hospital_loc.jpg";
	private String strImgLHI_GPSAddress_QR = "images/LHI_GPS.jpg";
	
	@RequestMapping(value = "/Schedule", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView Schedule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 //,@RequestParam("product_id") String product_id	 
	) throws Exception {
		String product_id=ClsHelper.getProductCode(req_policynumber);
		System.out.println("product_id="+product_id);
		if(product_id.equalsIgnoreCase("HC00")|| product_id.equalsIgnoreCase("HC01")) {
			HCXX_Schedule(request, response, modelMap, reportType, req_policynumber, product_id);
		}else if(product_id.equalsIgnoreCase("PA00") || product_id.equalsIgnoreCase("PA01")) {
			PAXX_Schedule(request, response, modelMap, reportType, req_policynumber, product_id);
		}
		return null;
	}
	
	@RequestMapping(value = "/AttachedList", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView AttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 //,@RequestParam("product_id") String product_id
	) throws Exception {
		String product_id=ClsHelper.getProductCode(req_policynumber);
		System.out.println("product_id="+product_id);
		if(product_id.equalsIgnoreCase("HC00")||product_id.equalsIgnoreCase("HC01")) {
			HCXX_AttachedList(request, response, modelMap, reportType, req_policynumber, product_id);
		}else if(product_id.equalsIgnoreCase("PA00") || product_id.equalsIgnoreCase("PA01")) {
			PAXX_AttachedList(request, response, modelMap, reportType, req_policynumber, product_id);
		}
		return null;
	}
	
	ModelAndView HCXX_Schedule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0006_PA00Services rpt_pa00 = new rpt_0006_PA00Services();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		String POLH_SYS_ID=rpt_pa00.getSysIdByPolNum(ClsHelper.PolicyNumber(req_policynumber));
		String strSpecialCondition = rpt_pa00.getFireEngineerList(POLH_SYS_ID);

		try {
			System.out.println("parPolicyNumber:" + req_policynumber);
			para.put("parPolicyNumber", ClsHelper.PolicyNumber(req_policynumber));			
			para.put("parCondictionAndClauses", strSpecialCondition);
			para.put("parPolh_sys_id",POLH_SYS_ID);
			para.put("parHospital_loc",servletContext.getRealPath(strImgHospital_Loc_QR));
			para.put("parLHIAddress",servletContext.getRealPath(strImgLHI_GPSAddress_QR));
			para.put("parCEOSignature", servletContext.getRealPath(ClsHelper.img_ceoSignature));
			para.put("parStamp", servletContext.getRealPath(ClsHelper.img_stamp));
			para.put("parLetterHead", servletContext.getRealPath(ClsHelper.img_letter_head));
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
				reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/HC00-SCHEDULE.jasper"),para);
			if (reportType == 1){
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, product_id);
			}
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policynumber : "+req_policynumber);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	ModelAndView HCXX_AttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0014_PA00_endorsementServices endor0014Service=new rpt_0014_PA00_endorsementServices();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
		String POLH_SYS_ID=endor0014Service.getSysIdByPolNum(req_policynumber);
		
		try {
			para.put("parPolh_sys_id", POLH_SYS_ID);						
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/HC00-New-AttachedList.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "HC00-ATTACHED-LIST");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	ModelAndView PAXX_Schedule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String product_id	
	) throws Exception {
	
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0006_PA00Services rpt_pa00 = new rpt_0006_PA00Services();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		//Application_Properties.SERIAL=3;
		String policynumber=ClsHelper.PolicyNumber(req_policynumber);
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
			para.put("parPolh_sys_id",POLH_SYS_ID);
			para.put("parHospital_loc",servletContext.getRealPath(strImgHospital_Loc_QR));
			para.put("parLHIAddress",servletContext.getRealPath(strImgLHI_GPSAddress_QR));
			para.put("parCEOSignature", servletContext.getRealPath(ClsHelper.img_ceoSignature));
			para.put("parStamp", servletContext.getRealPath(ClsHelper.img_stamp));
			para.put("parLetterHead", servletContext.getRealPath(ClsHelper.img_letter_head));
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

	ModelAndView PAXX_AttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0006_PA00Services rpt_pa00 = new rpt_0006_PA00Services();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
	
//		Application_Properties.SERIAL=2;
		String policynumber=ClsHelper.PolicyNumber(req_policynumber);
		String POLH_SYS_ID=rpt_pa00.getSysIdByPolicyNumber(policynumber);
		String Product_Code=rpt_pa00.getProductCodeByPolNum(policynumber);
		String strSpecialCondition = rpt_pa00.getFireEngineerList(POLH_SYS_ID);
		PA00TotalBenefit_model pa00TotalBen=new PA00TotalBenefit_model();
		pa00TotalBen=rpt_pa00.getTotalBenefitByPolicyNumber(policynumber);
		try {
			System.out.println("parPolicyNumber:" + policynumber);
			para.put("parPolicyNumber", policynumber);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/PA00-Report-Detail.jasper"),para);
			
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

}

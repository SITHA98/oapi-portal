package sitha.rupp.controller;

import java.io.IOException;
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
import sitha.rupp.model.rpt_0008_EndorsementModel;
import sitha.rupp.model.rpt_0014_PA00_EndorsementModel;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;
import sitha.rupp.service.rpt_0014_PA00_endorsementServices;
import sitha.rupp.service.RPT_0010_Endorsement;
import sitha.rupp.view.ReportEngine;

@Controller
public class RPT_0010_EndorsementController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();
	
	@RequestMapping(value = "/0010EndorsementNote", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView EndorsementNote(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum
			 ,@RequestParam("product_id") String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;		
//		HttpSession session = request.getSession();
//		ServletContext servletContext = session.getServletContext();
//		ServletOutputStream outputStream = response.getOutputStream();
//		RPT_0010_Endorsement endor0008Service=new RPT_0010_Endorsement();
//		HashMap<Object, Object> para = new HashMap<Object, Object>();
//		rpt_0008_EndorsementModel rpt_0008_model = new rpt_0008_EndorsementModel();	
		product_id=ClsHelper.getProductCode(req_policynumber);
		System.out.println("product_id="+product_id);
		if(product_id.equalsIgnoreCase("HC00")||product_id.equalsIgnoreCase("HC01")) {
			//endorsementNoteHC00(reportType,req_policynumber,extNum,product_id);
			HC00EndorsementNote(request, response, modelMap, reportType, req_policynumber.trim(), extNum.trim(), product_id);
		}else if(product_id.equalsIgnoreCase("MC00")||product_id.equalsIgnoreCase("CV00")||product_id.equalsIgnoreCase("PV00")||product_id.equalsIgnoreCase("MC")||product_id.equalsIgnoreCase("CV")||product_id.equalsIgnoreCase("PV")) {
			MotorEndorsementSchedule(request, response, modelMap, reportType, req_policynumber.trim(), extNum.trim(), product_id);			
		}else if(product_id.equalsIgnoreCase("PA00")||product_id.equalsIgnoreCase("PA01")) {
			PA00EndorsementNote(request, response, modelMap, reportType, req_policynumber, extNum, product_id);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/0010EndorsementAttachedList", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView EndorsementAttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum	
			 ,@RequestParam("product_id") String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;	
		product_id=ClsHelper.getProductCode(req_policynumber);
		if(product_id.equalsIgnoreCase("HC00")||product_id.equalsIgnoreCase("HC01")) {
			//endorsementNoteHC00(reportType,req_policynumber,extNum,product_id);
			HC00AttachedList(request, response, modelMap, reportType, req_policynumber.trim(), extNum.trim(), product_id);
		}else if(product_id.equalsIgnoreCase("MC00")||product_id.equalsIgnoreCase("CV00")||product_id.equalsIgnoreCase("PV00")||product_id.equalsIgnoreCase("MC")||product_id.equalsIgnoreCase("CV")||product_id.equalsIgnoreCase("PV")) {
			System.out.println("motor attached list open.");
			MotorAttachedList(request, response, modelMap, reportType, req_policynumber.trim(), extNum.trim(),product_id);			
		}else if(product_id.equalsIgnoreCase("PA00")||product_id.equalsIgnoreCase("PA01")) {
			PA00EndorsementAttachedList(request, response, modelMap, reportType, req_policynumber, extNum, product_id);
		}
		return null;
	}
	@RequestMapping(value = "/0010EndorsementInvoice", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView EndorsementInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum	
			 ,@RequestParam("product_id") String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		RPT_0010_Endorsement endor0008Service=new RPT_0010_Endorsement();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		rpt_0008_EndorsementModel rpt_0008_model = new rpt_0008_EndorsementModel();
		String policyNumber=ClsHelper.PolicyNumber(req_policynumber.trim());
		String POLH_SYS_ID=endor0008Service.getSysIdByPolNum(policyNumber);
		rpt_0008_model=endor0008Service.getMainData(policyNumber, extNum.trim());
		double amount =Double.parseDouble(rpt_0008_model.getPENDR_AMT_BC());
		product_id=ClsHelper.getProductCode(req_policynumber);
		
		rpt_0014_PA00_endorsementServices endor0014Service=new rpt_0014_PA00_endorsementServices();
		
		rpt_0014_PA00_EndorsementModel rpt_0014_model = new rpt_0014_PA00_EndorsementModel();
		rpt_0014_model=endor0014Service.getMainData(req_policynumber, extNum);
		String minEffectiveDate=endor0014Service.getMinDateOfEndorsment(POLH_SYS_ID, extNum);
		
			try {
				para.put("parSysID", POLH_SYS_ID);
				para.put("parExtNum", extNum);
				para.put("parAccount_Code", rpt_0008_model.getPOLH_DEBIT_PARTY());
//				para.put("parInvoiceDate", rpt_0008_model.getEFFECTIVE_DATE());
				para.put("parInvoiceDate", rpt_0014_model.getPENDR_DATE());
//				para.put("parAccMonthYear", rpt_0008_model.getPOLH_POST_PERIOD());
				para.put("parAccMonthYear", rpt_0014_model.getPOLH_POST_PERIOD());
				para.put("parINSURED_NAME", rpt_0008_model.getINSURED_NAME());
				para.put("parADDRESS", rpt_0008_model.getADDRESS());
				para.put("parVattin", "");
				para.put("parProductDesc", rpt_0008_model.getPRODUCT_DESC());
				//para.put("parPENDR_POLICY_NUMBER", rpt_0008_model.getPENDR_POLICY_NUMBER());
				para.put("parPENDR_POLICY_NUMBER", rpt_0008_model.getPOLH_POLNUM());
//				para.put("parEffictiveDate", rpt_0008_model.getEFFECTIVE_DATE());
				para.put("parEffictiveDate", minEffectiveDate);
				para.put("parExpiryDate", rpt_0008_model.getEXPIRY_DATE());
				
				//String invoiceNumber=endor0008Service.getInvoiceNumberByPolNumber(rpt_0008_model.getPENDR_POLICY_NUMBER(),product_id);
				//para.put("parInvoiceNumber",invoiceNumber);
				
				String invoiceNumber=endor0014Service.getInvoiceNumberByPolNumber(req_policynumber,extNum,POLH_SYS_ID);
				para.put("parInvoiceNumber",invoiceNumber);
				double adminFee=0.0;
				adminFee = Double.parseDouble(rpt_0008_model.getAdmin_fee());
//				adminFee=0.0;
				if(amount>=0) {
					para.put("parInvoideDRCR",invoiceNumber);
					para.put("parPENDR_PREMIUM_AMOUNT", ClsHelper.formatNumber(amount));
					para.put("parAdminFee", ClsHelper.formatNumber("0.00"));
					para.put("parPENDR_AMT_BC", ClsHelper.formatNumber(amount+adminFee));
					para.put("parImageHeader", servletContext.getRealPath("images/1.png"));
					para.put("parImageFooter", servletContext.getRealPath("images/3.png"));
				}else {
					para.put("parPENDR_PREMIUM_AMOUNT", "(" +ClsHelper.formatNumber(amount * -1)+")");
					if (adminFee==0) {
						para.put("parAdminFee", "(0.00)");
					}else if(adminFee<0) {
						para.put("parAdminFee", "("+ClsHelper.formatNumber(adminFee * -1)+")");
					}else if(adminFee>0) {
						para.put("parAdminFee", "("+ClsHelper.formatNumber(adminFee)+")");
					}
					para.put("parPENDR_AMT_BC", "("+ClsHelper.formatNumber((amount + adminFee) * -1)+")");
					para.put("parImageHeader", servletContext.getRealPath("images/1_CR_NOTE.png"));
					para.put("parImageFooter", servletContext.getRealPath("images/4_CR_NOTE_FOOTER.png"));
				}
				
	//			para.put("parImageDetail", servletContext.getRealPath("images/2.png"));
				para.put("parImageDetail", servletContext.getRealPath("images/2_ENDORSEMENT.png"));
				
				
				ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
				reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/CreditNote.jasper"),para);
				if (reportType == 1) {
					response.setContentType("Application/pdf");
					reportEngine.exportPDF(outputStream);
				} else if (reportType == 0) {
					response.setContentType("text/html");
					reportEngine.exportHtml(outputStream, request);
				} else if (reportType == 2) {
					reportEngine.exportExcel(outputStream, request, response, req_policynumber);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
		return null;
	}
	
	
	
	ModelAndView MotorEndorsementSchedule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String extNum
			 ,String product_id	
	) throws Exception {
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		
		HashMap<Object, Object> para = new HashMap<Object, Object>();
			
		String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
		RPT_0010_Endorsement endor_0010Service=new RPT_0010_Endorsement();
		String polh_sys_id=endor_0010Service.getSysIdByPolNum(policyNumber);
		String endorsement_Text=endor_0010Service.getEndorsmentText(polh_sys_id, extNum);
		try {
			//admin_fee=rpt_0008_model.getAdmin_fee();
			para.put("parPolh_extnum", extNum);
			para.put("parPolicyNumber", policyNumber);			
			para.put("parEndorsementText",endorsement_Text);
			//para.put("parRefundPremium", rpt_0008_model.getPENDR_AMT_BC());
						
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/MOTOR/MOTOR-SCHEDULE.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, req_policynumber);
			}
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policy number : " + policyNumber + " extNum: "+ extNum);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	ModelAndView MotorAttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String extNum	
			 ,String product_id	
	) throws IOException {
		
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
		try {
			para.put("parPolicyNumber", policyNumber);
			para.put("parPolh_extnum", extNum);			
			para.put("parDisplayPolicy",req_policynumber);
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/MOTOR/MOTOR-ENDORSEMENT-ATTACHED-LIST.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "AttacheList_"+req_policynumber.replace("/", "-"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	ModelAndView HC00EndorsementNote(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String extNum
			 ,String product_id	
	) throws Exception {
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		RPT_0010_Endorsement endor0008Service=new RPT_0010_Endorsement();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		rpt_0008_EndorsementModel rpt_0008_model = new rpt_0008_EndorsementModel();	
		String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
		rpt_0008_model=endor0008Service.getMainData(policyNumber, extNum);
		double amount=0.0;
		double admin_fee=0.00;
		
		String POLH_SYS_ID=endor0008Service.getSysIdByPolNum(policyNumber);
		rpt_0014_PA00_endorsementServices endor0014Service=new rpt_0014_PA00_endorsementServices();
		rpt_0014_PA00_EndorsementModel rpt_0014_model = new rpt_0014_PA00_EndorsementModel();
		String minEffectiveDate=endor0014Service.getMinDateOfEndorsment(POLH_SYS_ID, extNum);
		rpt_0014_model=endor0014Service.getMainData(req_policynumber, extNum);
		String InvoiceDate = endor0014Service.getInvoiceDateByPolNumber(req_policynumber, extNum, POLH_SYS_ID);
		
		try {
			
			admin_fee = Double.parseDouble(rpt_0008_model.getAdmin_fee());
			
			para.put("parProductDesc", rpt_0008_model.getPRODUCT_DESC());
			para.put("parPolicyNumber", rpt_0008_model.getPENDR_POLICY_NUMBER());			
			para.put("parINT_CODE", rpt_0008_model.getINT_CODE());	
			para.put("parEXPIRY_DATE", rpt_0008_model.getEXPIRY_DATE());	
			para.put("parINSURED_NAME", rpt_0008_model.getINSURED_NAME());	
			para.put("parADDRESS", rpt_0008_model.getADDRESS());	
			para.put("parAdmin_fee",admin_fee);
			para.put("parIssue_By",rpt_0008_model.getIssue_by());
			//para.put("parRefundPremium", rpt_0008_model.getPENDR_AMT_BC());
			
			amount=Double.parseDouble(rpt_0008_model.getPENDR_AMT_BC());
			
			
			if(amount>0) {
				para.put("parDRPremium", ClsHelper.formatNumber(rpt_0008_model.getPENDR_AMT_BC()));
				para.put("parDRAdminFee","0.00");
				para.put("parDRTotal", ClsHelper.formatNumber(rpt_0008_model.getPENDR_AMT_BC()));
				
				para.put("parCRPremium", "0.00");
				para.put("parCRAdminFee","0.00");
				para.put("parCRTotal", "0.00");
				para.put("parInConsideration","In consideration thereof, an additional premium of USD "+ClsHelper.formatNumber(rpt_0008_model.getPENDR_AMT_BC())+" is charged from the insured.");
			}else if(amount==0) {
				para.put("parDRPremium","0.00");
				para.put("parDRAdminFee","0.00");
				para.put("parDRTotal","0.00");
				
				para.put("parCRPremium", "(0.00)");
				para.put("parCRAdminFee","(0.00)");
				para.put("parCRTotal", "(0.00)");
				
			}else {
				//dr
				para.put("parDRPremium","0.00");
				para.put("parDRAdminFee","0.00");
				para.put("parDRTotal","0.00");
				//end dr
				
				para.put("parCRPremium", "("+ClsHelper.formatNumber(amount*-1)+")");
//				admin_fee="0.00";
				
				if (admin_fee==0) {
					para.put("parCRAdminFee", "(0.00)");
				}else if(admin_fee<0) {
					para.put("parCRAdminFee","("+ClsHelper.formatNumber(admin_fee*-1)+")");
				}
				amount =(amount + admin_fee)* -1;
				para.put("parCRTotal", "("+ClsHelper.formatNumber(amount)+")");
				para.put("parInConsideration","In consideration thereof, a refund premium of USD ("+ClsHelper.formatNumber(amount)+") is made to the insured.");
			}
			
//			para.put("parEffictiveDate", minEffectiveDate);
			if(!rpt_0014_model.getPendr_type().equalsIgnoreCase("C")) {
				para.put("parEffictiveDate", minEffectiveDate);
			} else {
				para.put("parEffictiveDate", rpt_0014_model.getEFFECTIVE_DATE());
			}
			
			String detailAttached=endor0008Service.getEndorsementCount(policyNumber, extNum);
			para.put("pardetailAttached", detailAttached);			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/HC00-Endorsement-Note-Report.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, req_policynumber);
			}
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policy number : " + policyNumber + " extNum: "+ extNum);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	ModelAndView HC00AttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String extNum	
			 ,String product_id	
	) throws Exception {
		
		HttpSession session = request.getSession();
		RPT_0010_Endorsement endor0008Service=new RPT_0010_Endorsement();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		rpt_0008_EndorsementModel rpt_0008_model = new rpt_0008_EndorsementModel();
		String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
		String POLH_SYS_ID=endor0008Service.getSysIdByPolNum(policyNumber);
		rpt_0008_model=endor0008Service.getMainData(policyNumber, extNum);
		double amount=0.0;
		try {
			para.put("parSysID", POLH_SYS_ID);
			para.put("parExtNum", extNum);			
			para.put("parINSURED_NAME", rpt_0008_model.getINSURED_NAME());
			para.put("parPeriodStart", rpt_0008_model.getEFFECTIVE_DATE());
			para.put("parExpiryDate", rpt_0008_model.getEXPIRY_DATE());
//			para.put("parPENDR_AMT_BC", rpt_0008_model.getPENDR_AMT_BC());
			para.put("parProductDesc", rpt_0008_model.getPRODUCT_DESC());	
			para.put("parPENDR_POLICY_NUMBER", rpt_0008_model.getPENDR_POLICY_NUMBER());	
			
			amount=Double.parseDouble(rpt_0008_model.getPENDR_AMT_BC());
			if(amount>0) {
				para.put("parPENDR_AMT_BC", ClsHelper.formatNumber(rpt_0008_model.getPENDR_AMT_BC()));				
			}else {
				amount =amount * -1;
				para.put("parPENDR_AMT_BC", "("+ClsHelper.formatNumber(amount)+")");				
			}
			
//			para.put("parRefundPremium", rpt_0008_model.getPENDR_AMT_BC());
//			para.put("parEffictiveDate", rpt_0008_model.getEFFECTIVE_DATE());
//			
//			String detailAttached=endor0008Service.getEndorsementCount(policyNumber, extNum);
//			para.put("pardetailAttached", detailAttached);
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/HC00-Endorsement-AttachedList.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, req_policynumber);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	ModelAndView HC00EndorsementInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		RPT_0010_Endorsement endor0008Service=new RPT_0010_Endorsement();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		rpt_0008_EndorsementModel rpt_0008_model = new rpt_0008_EndorsementModel();
		String policyNumber=ClsHelper.PolicyNumber(req_policynumber.trim());
		String POLH_SYS_ID=endor0008Service.getSysIdByPolNum(policyNumber);
		rpt_0008_model=endor0008Service.getMainData(policyNumber, extNum.trim());
		double amount =Double.parseDouble(rpt_0008_model.getPENDR_AMT_BC());
		String product_id=ClsHelper.getProductCode(req_policynumber);
		
		rpt_0014_PA00_endorsementServices endor0014Service=new rpt_0014_PA00_endorsementServices();
		rpt_0014_PA00_EndorsementModel rpt_0014_model = new rpt_0014_PA00_EndorsementModel();
		String minEffectiveDate=endor0014Service.getMinDateOfEndorsment(POLH_SYS_ID, extNum);
		rpt_0014_model=endor0014Service.getMainData(req_policynumber, extNum);
		String InvoiceDate = endor0014Service.getInvoiceDateByPolNumber(req_policynumber, extNum, POLH_SYS_ID);
		//System.out.println("InvoiceDate:"+InvoiceDate+"rpt_0014_model.getPOLH_POST_PERIOD()"+rpt_0014_model.getPOLH_POST_PERIOD());
		try {
			para.put("parSysID", POLH_SYS_ID);
			para.put("parExtNum", extNum);
			para.put("parAccount_Code", rpt_0008_model.getPOLH_DEBIT_PARTY());
			para.put("parInvoiceDate", InvoiceDate);
			para.put("parAccMonthYear", InvoiceDate.substring(3));
			para.put("parINSURED_NAME", rpt_0008_model.getINSURED_NAME());
			para.put("parADDRESS", rpt_0008_model.getADDRESS());
			para.put("parVattin", "");
			para.put("parProductDesc", rpt_0008_model.getPRODUCT_DESC());
			//para.put("parPENDR_POLICY_NUMBER", rpt_0008_model.getPENDR_POLICY_NUMBER());
			para.put("parPENDR_POLICY_NUMBER", rpt_0008_model.getPENDR_POLICY_NUMBER());
			para.put("parEffictiveDate", minEffectiveDate);
			para.put("parExpiryDate", rpt_0008_model.getEXPIRY_DATE());
			
			String invoiceNumber=endor0008Service.getInvoiceNumberByPolNumber(rpt_0008_model.getPENDR_POLICY_NUMBER(),product_id);
			para.put("parInvoiceNumber",invoiceNumber);
			
			double adminFee = Double.parseDouble(rpt_0008_model.getAdmin_fee());
			adminFee=0.0;
			if(amount>=0) {
				para.put("parInvoideDRCR",invoiceNumber);
				para.put("parPENDR_PREMIUM_AMOUNT", ClsHelper.formatNumber(amount));
				para.put("parAdminFee", ClsHelper.formatNumber("0.00"));
				para.put("parPENDR_AMT_BC", ClsHelper.formatNumber(amount));
				para.put("parImageHeader", servletContext.getRealPath("images/1.png"));
				para.put("parImageFooter", servletContext.getRealPath("images/3.png"));
			}else {
				para.put("parPENDR_PREMIUM_AMOUNT", "(" +ClsHelper.formatNumber(amount * -1)+")");
				if (adminFee==0) {
					para.put("parAdminFee", "(0.00)");
				}else {
					para.put("parAdminFee", "("+ClsHelper.formatNumber(adminFee * -1)+")");
				}
				para.put("parPENDR_AMT_BC", "("+ClsHelper.formatNumber(amount * -1)+")");
				para.put("parImageHeader", servletContext.getRealPath("images/1_CR_NOTE.png"));
				para.put("parImageFooter", servletContext.getRealPath("images/4_CR_NOTE_FOOTER.png"));
			}
			
//			para.put("parImageDetail", servletContext.getRealPath("images/2.png"));
			para.put("parImageDetail", servletContext.getRealPath("images/2_ENDORSEMENT.png"));
			
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/CreditNote.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF(outputStream);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, req_policynumber);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	ModelAndView PA00EndorsementNote(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String extNum
			 ,String product_id	
	) throws Exception {
		
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0014_PA00_endorsementServices endor0014Service=new rpt_0014_PA00_endorsementServices();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		rpt_0014_PA00_EndorsementModel rpt_0014_model = new rpt_0014_PA00_EndorsementModel();
		//String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
//		String POLH_SYS_ID=endor0008Service.getSysIdByPolNum(policyNumber);
		rpt_0014_model=endor0014Service.getMainData(req_policynumber, extNum);
		String POLH_SYS_ID=endor0014Service.getSysIdByPolNum(req_policynumber);
		String minEffectiveDate=endor0014Service.getMinDateOfEndorsment(POLH_SYS_ID, extNum);
		double amount=0.0;
		try {
			para.put("parProductDesc", rpt_0014_model.getPRODUCT_DESC());
			para.put("parPolicyNumber", rpt_0014_model.getPOLICY_DISPLAY());
			para.put("parINT_CODE", rpt_0014_model.getINT_CODE());
			para.put("parEXPIRY_DATE", rpt_0014_model.getEXPIRY_DATE());	
			para.put("parINSURED_NAME", rpt_0014_model.getINSURED_NAME());	
			para.put("parADDRESS", rpt_0014_model.getADDRESS());	
			para.put("parIssuedBy",rpt_0014_model.getISSUE_BY());
			//para.put("parRefundPremium", rpt_0008_model.getPENDR_AMT_BC());
			
			amount=Double.parseDouble(rpt_0014_model.getPENDR_INVOICE_AMOUNT());
			double adminFee = Double.parseDouble(rpt_0014_model.getPENDR_ADMIN_FEE_BC());
			if(amount>=0) {
				//para.put("parRefundPremium", ClsHelper.formatNumber(rpt_0014_model.getPENDR_AMT_BC()));	
				para.put("parPENDR_PREMIUM_AMOUNT_L", ClsHelper.formatNumber(rpt_0014_model.getPENDR_PREMIUM_AMOUNT()));
				para.put("parPENDR_ADMIN_FEE_BC_L", ClsHelper.formatNumber(rpt_0014_model.getPENDR_ADMIN_FEE_BC()));
				para.put("parPENDR_INVOICE_AMOUNT_L", ClsHelper.formatNumber(rpt_0014_model.getPENDR_INVOICE_AMOUNT()));
				para.put("parPENDR_PREMIUM_AMOUNT_R", "(0.00)");
				para.put("parPENDR_ADMIN_FEE_BC_R", "(0.00)");
				para.put("parPENDR_INVOICE_AMOUNT_R", "(0.00)");
				String InCondText="In consideration thereof, an additional premium of USD "+ClsHelper.formatNumber(amount)+" is charged from the insured.";
				para.put("parInConsideration",InCondText);
			}else {
				amount =amount * -1;
//				para.put("parRefundPremium", "("+ClsHelper.formatNumber(amount)+")");			
				para.put("parPENDR_PREMIUM_AMOUNT_L", "0.00");
				para.put("parPENDR_ADMIN_FEE_BC_L", "0.00");
				para.put("parPENDR_INVOICE_AMOUNT_L", "0.00");
				para.put("parPENDR_PREMIUM_AMOUNT_R", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_PREMIUM_AMOUNT())*-1)+")");
				if (adminFee==0){
					para.put("parPENDR_ADMIN_FEE_BC_R", "(0.00)");
				}else{
					para.put("parPENDR_ADMIN_FEE_BC_R", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_ADMIN_FEE_BC())*-1)+")");
				}
				para.put("parPENDR_INVOICE_AMOUNT_R", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_INVOICE_AMOUNT())*-1)+")");
				
				String InCondText="In consideration thereof, a refund premium of USD ("+ClsHelper.formatNumber(amount)+") is made to the insured.";
				para.put("parInConsideration",InCondText);
				
			}
//			para.put("parEffictiveDate", rpt_0014_model.getEFFECTIVE_DATE());
			if(!rpt_0014_model.getPendr_type().equalsIgnoreCase("C")) {
				para.put("parEffictiveDate", minEffectiveDate);
			} else {
				para.put("parEffictiveDate", rpt_0014_model.getEFFECTIVE_DATE());
			}
			
			String detailAttached=endor0014Service.getEndorsementCount(req_policynumber, extNum);
			para.put("pardetailAttached", detailAttached);
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/PA00-Endorsement-Note-Report.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "PA00-ENDORSEMENT-NOTE");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policy number : " + req_policynumber + " extNum: "+ extNum);
			Application_Properties.SERIAL = 1;
			int i = component.getJdbcTemplate().update(event.strSQL(event));
			System.out.println("eventLog: "+i);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	ModelAndView PA00EndorsementAttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,int reportType
			 ,String req_policynumber
			 ,String extNum	
			 ,String product_id	
	) throws Exception {
		Application_Properties.SERIAL=1;		
		HttpSession session = request.getSession();
		rpt_0014_PA00_endorsementServices endor0014Service=new rpt_0014_PA00_endorsementServices();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		rpt_0014_PA00_EndorsementModel rpt_0014_model = new rpt_0014_PA00_EndorsementModel();
		//String policyNumber=ClsHelper.PolicyNumber(req_policynumber);
		String POLH_SYS_ID=endor0014Service.getSysIdByPolNum(req_policynumber);
		rpt_0014_model=endor0014Service.getMainData(req_policynumber, extNum);
		double amount=0.0;
		try {
			para.put("parSysID", POLH_SYS_ID);
			para.put("parExtNum", extNum);			
			para.put("parINSURED_NAME", rpt_0014_model.getINSURED_NAME());
			para.put("parPeriodStart", rpt_0014_model.getEFFECTIVE_DATE());
			para.put("parExpiryDate", rpt_0014_model.getEXPIRY_DATE());
//			para.put("parPENDR_AMT_BC", rpt_0008_model.getPENDR_AMT_BC());
			para.put("parProductDesc", rpt_0014_model.getPRODUCT_DESC());	
			para.put("parPENDR_POLICY_NUMBER", rpt_0014_model.getPOLICY_DISPLAY());	
			
			amount=Double.parseDouble(rpt_0014_model.getPENDR_INVOICE_AMOUNT());
			System.out.println("amount: "+amount);
			if(amount>=0) {
				para.put("parPENDR_AMT_BC", ClsHelper.formatNumber(amount));
				
			}else {
				amount =amount * -1;
				para.put("parPENDR_AMT_BC", "("+ClsHelper.formatNumber(amount)+")");
			}
			
//			para.put("parRefundPremium", rpt_0008_model.getPENDR_AMT_BC());
//			para.put("parEffictiveDate", rpt_0008_model.getEFFECTIVE_DATE());
//			
//			String detailAttached=endor0008Service.getEndorsementCount(policyNumber, extNum);
//			para.put("pardetailAttached", detailAttached);
			
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/PA-HC-REPORTS/PA00-Endorsement-AttachedList.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "PA00-ENDORSEMENT-ATTACHED-LIST");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

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

import sitha.rupp.model.rpt_0014_PA00_EndorsementModel;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;

import sitha.rupp.service.rpt_0014_PA00_endorsementServices;
import sitha.rupp.view.ReportEngine;

@Controller
public class RPT_0014_PA00_ENDOR_Controller extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	//private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();
		
	@RequestMapping(value = "/0014EndorsementNote", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView EndorsementNote(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum	
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
				if (adminFee==0) {
					para.put("parPENDR_ADMIN_FEE_BC_R", "(0.00)");
				}else {
					para.put("parPENDR_ADMIN_FEE_BC_R", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_ADMIN_FEE_BC())*-1)+")");
				}
				para.put("parPENDR_INVOICE_AMOUNT_R", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_INVOICE_AMOUNT())*-1)+")");
				
				String InCondText="In consideration thereof, a refund premium of USD ("+ClsHelper.formatNumber(amount)+") is made to the insured.";
				para.put("parInConsideration",InCondText);
				
			}
//			para.put("parEffictiveDate", rpt_0014_model.getEFFECTIVE_DATE());
			para.put("parEffictiveDate", minEffectiveDate);
			
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
	
	@RequestMapping(value = "/0014EndorsementAttachedList", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView EndorsementAttachedList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum	
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
	@RequestMapping(value = "/0014EndorsementInvoice", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView EndorsementInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("reportType") int reportType
			 ,@RequestParam("policynumber") String req_policynumber
			 ,@RequestParam("extNum") String extNum	
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
		String minEffectiveDate=endor0014Service.getMinDateOfEndorsment(POLH_SYS_ID, extNum);
		
		rpt_0014_model=endor0014Service.getMainData(req_policynumber, extNum);
		double amount =0.0;
		try {
						
			para.put("parSysID", POLH_SYS_ID);
			para.put("parExtNum", extNum);
			
			para.put("parAccount_Code", rpt_0014_model.getPOLH_DEBIT_PARTY());
			para.put("parInvoiceDate", rpt_0014_model.getPENDR_DATE());
			para.put("parAccMonthYear", rpt_0014_model.getPOLH_POST_PERIOD());
			para.put("parINSURED_NAME", rpt_0014_model.getINSURED_NAME());
			para.put("parADDRESS", rpt_0014_model.getADDRESS());
			para.put("parVattin", "");
			para.put("parProductDesc", rpt_0014_model.getPRODUCT_DESC());
			para.put("parPENDR_POLICY_NUMBER", rpt_0014_model.getPOLICY_DISPLAY());
			para.put("parEffictiveDate", minEffectiveDate);
			para.put("parExpiryDate", rpt_0014_model.getEXPIRY_DATE());
			
			
			
			//String invoiceNumber=endor0014Service.getInvoiceNumberByPolNumber(rpt_0014_model.getPENDR_ENDRNUM(),extNum,POLH_SYS_ID);
			String invoiceNumber=endor0014Service.getInvoiceNumberByPolNumber(req_policynumber,extNum,POLH_SYS_ID);
			para.put("parInvoiceNumber",invoiceNumber);
			
			String invoiceDate=endor0014Service.getInvoiceDateByPolNumber(req_policynumber,extNum,POLH_SYS_ID);
			para.put("parAccMonthYear",invoiceDate.substring(3));
			
			amount=Double.parseDouble(rpt_0014_model.getPENDR_INVOICE_AMOUNT());
			double adminFee = Double.parseDouble(rpt_0014_model.getPENDR_ADMIN_FEE_BC());
			if(amount>=0) {
				para.put("parInvoideDRCR",invoiceNumber);
				para.put("parPENDR_PREMIUM_AMOUNT", ClsHelper.formatNumber(rpt_0014_model.getPENDR_PREMIUM_AMOUNT()));
				para.put("parAdminFee", ClsHelper.formatNumber(rpt_0014_model.getPENDR_ADMIN_FEE_BC()));
				para.put("parPENDR_AMT_BC", ClsHelper.formatNumber(amount));
				para.put("parImageHeader", servletContext.getRealPath("images/1.png"));
				para.put("parImageFooter", servletContext.getRealPath("images/3.png"));
				
			}else {
				para.put("parPENDR_PREMIUM_AMOUNT", "(" +ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_PREMIUM_AMOUNT())*-1)+")");
				if (adminFee==0) {
					para.put("parAdminFee", "(0.00)");
				}else {
					para.put("parAdminFee", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_ADMIN_FEE_BC())*-1)+")");
				}
				para.put("parPENDR_AMT_BC", "("+ClsHelper.formatNumber(Double.parseDouble(rpt_0014_model.getPENDR_INVOICE_AMOUNT())*-1)+")");
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
				reportEngine.exportExcel(outputStream, request, response, "");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

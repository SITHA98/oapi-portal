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
import sitha.rupp.model.MTUser;
import sitha.rupp.model.SYS_EVENT_LOG;
import sitha.rupp.service.CasaStmtService;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;
import sitha.rupp.view.ReportEngine;

@Controller
public class CasaCASAstmtController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/casastatement", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView loanpaid(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
			 ,@RequestParam("accountno") String accountno	
			 ,@RequestParam("dateFrom") String dateFrom
			 ,@RequestParam("dateTo") String dateTo
			 ,@RequestParam("reportType") int reportType
	 
	) throws Exception {
		Application_Properties.SERIAL=1;
		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
		
		
		int USER_ID=component.USER_ID(request);
		MTUser user=userDa.getUserInfo(USER_ID);
		String printedBy=component.USER_DISPLAY_NAME(USER_ID);
		String printedDateTime=component.dateTime();
		String curr_code="";
		
		//==== Tracking user's event ====//
		SYS_EVENT_LOG event=new SYS_EVENT_LOG();
		event=event.initEvent(reportType, USER_ID, "View Report CASA Statement Account No :"+accountno+",from date :"+dateFrom+" to :"+dateTo );
		Application_Properties.SERIAL=1;
		component.getJdbcTemplate().update(event.strSQL(event));
		
		
		
		//==== End Tracking ====//
		CasaStmtService astmtService=new CasaStmtService();
		boolean help=astmtService.isStaffAC(accountno);
		System.out.println("Is staff account : "+help);
		if(USER_ID!=3){
			if(help){
				String sql="SELECT CUSTOMER_NO FROM STTM_CUSTOMER WHERE CUSTOMER_CATEGORY = 'STAFF'";
				para.put("parSQL", sql);
				response.sendRedirect("nopermission");
			}
		}
		para.put("parImagePath", servletContext.getRealPath(strImagePath));
		para.put("parPrintedBy", printedBy);
		para.put("parPrintedDateTime", printedDateTime);
		para.put("parCurr_Code", curr_code);
		para.put("parAccountNo", accountno);
		para.put("parDateFrom", dateFrom);
		para.put("parDateTo", dateTo);
		
		para.put("parExchRate", "4,026.00");
		
		Application_Properties.SERIAL=1;
		ReportEngine reportEngine = (ReportEngine) context.getBean("reportEngine");
		reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/teller-reports/casa_stmt.jasper"), para);
		if (reportType == 1) {
			response.setContentType("Application/pdf");
			reportEngine.exportPDF(outputStream);
		} else if (reportType == 0) {
			response.setContentType("text/html");
			reportEngine.exportHtml(outputStream, request);
		} else if (reportType == 2) {
			reportEngine.exportExcel(outputStream, request, response,"ledger");
		}
		return null;
	}
}

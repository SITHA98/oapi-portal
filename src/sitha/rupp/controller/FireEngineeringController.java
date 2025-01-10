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
import sitha.rupp.service.rpt_0005_fire_and_engineeringServices;
import sitha.rupp.view.ReportEngine;

@Controller
public class FireEngineeringController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa=(UserDa) context.getBean("userDa");
	private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/FireEngineer", method = RequestMethod.GET, headers = "Accept=*/*")
	ModelAndView printPolicyInvoice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap
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
		rpt_0005_fire_and_engineeringServices rpt_0005_fireEng = new rpt_0005_fire_and_engineeringServices();
		ServletContext servletContext = session.getServletContext();
		ServletOutputStream outputStream = response.getOutputStream();
		HashMap<Object, Object> para = new HashMap<Object, Object>();
//		int USER_ID=component.USER_ID(request);
//		String user_name1 = null;
//		if(!user_name.trim().equalsIgnoreCase("")){
//			user_name1=component.getUSER_NAME(Integer.parseInt( user_name ));
//		}
//		
		
//		
		Application_Properties.SERIAL=1;
		//String strSpecialCondition=" Hospitalroom,       surgery \r room,meals,        medicalc \n Hospitalroom,surgery        room,meals,medicalc \n abcded";
		policynumber=ClsHelper.PolicyNumber(policynumber);
		String POLH_SYS_ID=rpt_0005_fireEng.getSysIdByPolicyNumber(policynumber);
		String strSpecialCondition = rpt_0005_fireEng.getFireEngineerList(POLH_SYS_ID);
		try {
			//P/01/FI00/20/0248/000/00
			System.out.println("parPolicyNumber:" + policynumber);
			para.put("parPolicyNumber", policynumber);
			//para.put("parVattin", vattin);
//			para.put("parImageHeader", servletContext.getRealPath("images/1.png"));
//			para.put("parImageDetail", servletContext.getRealPath("images/2.png"));
			para.put("SpecialCondition", strSpecialCondition);
			ReportEngine reportEngine = (ReportEngine)context.getBean("reportEngine");
			reportEngine.initReport(servletContext.getRealPath("WEB-INF/reports/rpt-0005-fire-and-engineering/FireSchedule.jasper"),para);
			if (reportType == 1) {
				response.setContentType("Application/pdf");
				reportEngine.exportPDF2(outputStream,request);
			} else if (reportType == 0) {
				response.setContentType("text/html");
				reportEngine.exportHtml(outputStream, request);
			} else if (reportType == 2) {
				reportEngine.exportExcel(outputStream, request, response, "Fire-And-Engineering");
			}
			
			int USER_ID = component.USER_ID(request);
			SYS_EVENT_LOG event = new SYS_EVENT_LOG();
			event = event.initEvent(reportType, USER_ID,this.getClass().getName() +", policy number : " + policynumber);
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

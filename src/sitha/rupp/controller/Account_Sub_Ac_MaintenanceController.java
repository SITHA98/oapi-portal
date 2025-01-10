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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.model.Account_sub_ac_maintenance;
import sitha.rupp.model.SYS_EVENT_LOG;
import sitha.rupp.service.Account_sub_ac_maintenanceService;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;
import sitha.rupp.view.ReportEngine;

@Controller
@RequestMapping(value="/Account_sub_ac_maintenance")
public class Account_Sub_Ac_MaintenanceController extends GenericController {
	
	PrincebankComponent component=PrincebankComponent.getInstance();
//	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
//	FAInformationService faInfoDa=(FAInformationService)context.getBean("faInfoDa");
//	NonBizdayService nonBizdayService=(NonBizdayService)context.getBean("nonBizdayService");
//	AuthorLevelService authorLevelService=(AuthorLevelService)context.getBean("authorLevelService");
//	ApprovalService approvalService=(ApprovalService)context.getBean("approvalService");
	
	 
	/*
	@ResponseBody
	@RequestMapping(value = "/insertTestModal", method = RequestMethod.POST)
	String insertFAInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("TestId") String TestId
			,@RequestParam("TestName") String TestName
			,@RequestParam("ValueDate") String ValueDate
			,@RequestParam("branch_code") String branch_code
			,@RequestParam("userid") int userid
			) throws Exception, Exception {
			String retString="";
			Account_sub_ac_maintenanceService acService =new Account_sub_ac_maintenanceService();
			Account_sub_ac_maintenance acModel =new Account_sub_ac_maintenance();
			try {
			testmodal.setTest_id(TestId);
			testmodal.setTest_name(TestName);
			testmodal.setValue_date(ValueDate);
			testmodal.setBranch_code(branch_code);
			testmodal.setUserid(userid);
			
			test_ModalService.insertTestModal(testmodal);
			System.out.println(TestId+"|"+TestName+"|"+ValueDate);
			retString=TestId+"|"+TestName+"|"+ValueDate;
			retString="0";
			}catch(Exception e) {
				e.printStackTrace();
				retString="error";
			}
		return retString;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/EditTestModal", method = RequestMethod.POST)
	String editTestModal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("TestId") String TestId
			,@RequestParam("TestName") String TestName
			,@RequestParam("ValueDate") String ValueDate
			,@RequestParam("branch_code") String branch_code
			) throws Exception, Exception {
		
			String retString="";
			Account_sub_ac_maintenanceService acService =new Account_sub_ac_maintenanceService();
			Account_sub_ac_maintenance acModel =new Account_sub_ac_maintenance();
			try {	
				acModel.setBATCH_NUMBER(bATCH_NUMBER);
				acModel.setTest_name(TestName);
				acModel.setValue_date(ValueDate.replace(".0",""));
				acModel.setBranch_code(branch_code);
				System.out.println(" get data : "+testmodal.toString());
				test_ModalService.editTestModal(testmodal);
				System.out.println(TestId+"|"+TestName+"|"+ValueDate);
				retString=TestId+"|"+TestName+"|"+ValueDate;
				retString="0";
			}catch(Exception e) {
				e.printStackTrace();
				retString="1";
			}
		return retString;
	}
	*/
//	http://localhost:8080/CoreTemplate4/TestModal/findOneTestModal
	@ResponseBody
	@RequestMapping(value = "/viewByAccountCode", method = RequestMethod.POST)
	Account_sub_ac_maintenance viewByAccountCode(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("TestId") String TestId
			) throws Exception, Exception {
			
		Account_sub_ac_maintenanceService acService =new Account_sub_ac_maintenanceService();
		Account_sub_ac_maintenance acModel =new Account_sub_ac_maintenance();
		acModel=acService.getAccountDetailbyAccountCode(TestId);
			
			System.out.println(acModel.toString());
		return acModel;
	}
	
	/*
	@ResponseBody
	@RequestMapping(value = "/deleteTestModal", method = RequestMethod.POST)
	String deleteTestModal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("TestId") String TestId
//			,@RequestParam("TestName") String TestName
//			,@RequestParam("ValueDate") String ValueDate
//			,@RequestParam("branch_code") String branch_code
			) throws Exception, Exception {
		
			String retString="";
			test_ModalService=new Test_ModalService();
			testmodal=new Test_Modal();
			try {	
				testmodal.setTest_id(TestId);
//				testmodal.setTest_name(TestName);
//				testmodal.setValue_date(ValueDate.replace(".0",""));
//				testmodal.setBranch_code(branch_code);
				System.out.println(" get data : "+testmodal.toString());
				test_ModalService.deleteTestModal(testmodal);
				retString="0";
			}catch(Exception e) {
				e.printStackTrace();
				retString="1";
			}
		return retString;
	}
	*/
}

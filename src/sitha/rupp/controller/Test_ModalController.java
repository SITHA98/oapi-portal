package sitha.rupp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sitha.rupp.model.Test_Modal;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.Test_ModalService;

@Controller
@RequestMapping(value="/TestModal")
public class Test_ModalController extends GenericController{
	PrincebankComponent component=PrincebankComponent.getInstance();
//	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
//	FAInformationService faInfoDa=(FAInformationService)context.getBean("faInfoDa");
//	NonBizdayService nonBizdayService=(NonBizdayService)context.getBean("nonBizdayService");
//	AuthorLevelService authorLevelService=(AuthorLevelService)context.getBean("authorLevelService");
//	ApprovalService approvalService=(ApprovalService)context.getBean("approvalService");
	
	 Test_ModalService test_ModalService=new Test_ModalService();
	 Test_Modal testmodal=new Test_Modal();
	
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
			test_ModalService=new Test_ModalService();
			testmodal=new Test_Modal();
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
			test_ModalService=new Test_ModalService();
			testmodal=new Test_Modal();
			try {	
				testmodal.setTest_id(TestId);
				testmodal.setTest_name(TestName);
				testmodal.setValue_date(ValueDate.replace(".0",""));
				testmodal.setBranch_code(branch_code);
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
	
//	http://localhost:8080/CoreTemplate4/TestModal/findOneTestModal
	@ResponseBody
	@RequestMapping(value = "/findOneTestModal", method = RequestMethod.POST)
	Test_Modal findOneTestModal(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("TestId") String TestId
			) throws Exception, Exception {
			
			test_ModalService=new Test_ModalService();
			testmodal=new Test_Modal();
			testmodal=test_ModalService.getOneTestModal(TestId);
			
			System.out.println(testmodal.toString());
		return testmodal;
	}
	
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
	
}

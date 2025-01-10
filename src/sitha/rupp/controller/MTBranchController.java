package sitha.rupp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sitha.rupp.model.MTBranch;
import sitha.rupp.service.BranchDa;

@Controller
@RequestMapping(value = "/branch")
public class MTBranchController extends GenericController{
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	BranchDa brDa=(BranchDa)context.getBean("branchDa");
	
	@ResponseBody
	@RequestMapping(value = "/insertBranch", method = RequestMethod.POST)	
	String getInsertBranch(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("txtBrCode") String brcode,@RequestParam("txtBrNameKH") String brnamekh,
			@RequestParam("txtBrNameEN") String brnameen,@RequestParam("txtBrDesKH") String brdesckh,
			@RequestParam("txtBrDesEN") String brdesen,@RequestParam("txtBrAddr") String braddress,
			@RequestParam("txtBrPhone") String brphone,@RequestParam("txtBrWebsite") String brweb,
			@RequestParam("txtBrEmail") String bremail,
			@RequestParam("txtbr_Id") int brId) throws Exception, Exception {		
			MTBranch br=new MTBranch();
			br.setBranchCode(brcode);
			br.setBranchNamekh(brnamekh);
			br.setBranchNameen(brnameen);
			br.setBranchDesKH(brdesckh);
			br.setBranchDesen(brdesen);
			br.setBranchAddress(braddress);
			br.setBranchPhone(brphone);
			br.setBranchWebsite(brweb);
			br.setBranchEmail(bremail);
			int i=0;
			String retString="";
			if(brId>0){
				if(brDa.getBranchNameCode(brnameen, brcode,brId)>0){
					i = brDa.updateBranch(br, brId);
					if(i>0){
						retString="2";
					}
				}
				else{
					if(brDa.getBranchCodeId(brcode,brId)>0){
						if(brDa.getBranchName(brnameen)>0){
							retString="4";							
						}
						else{
							i = brDa.updateBranch(br, brId);
							if(i>0){
								retString="2";
							}
						}
					}
					else if(brDa.getBranchNameId(brnameen, brId)>0){
							if(brDa.getBranchCode(brcode)>0){
								retString="3";
							}
							else{
								i = brDa.updateBranch(br, brId);
								if(i>0){
									retString="2";
								}
							}
						}
					else{
						if(brDa.getBranchCode(brcode)>0){
							retString="3";
						}
						else{
							if(brDa.getBranchName(brnameen)>0){
								retString="4";							
							}
							else{
								i = brDa.updateBranch(br, brId);
								if(i>0){
									retString="2";
								}
							}
						}
					}					
				}				
			}
			else{
				if(brDa.getBranchCode(brcode)>0){
					retString="3";
				}
				else if(brDa.getBranchName(brnameen)>0){
					retString="4";
				}
				else{
					i = brDa.insertBranch(br);
					if(i>0){
						retString="1";
					}
				}				
			}			
		return retString;	
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	String getUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("usertId") int usertId) throws Exception, Exception {
			//ModelAndView model=new ModelAndView("");
		return usertId + "";
	}
	@ResponseBody
	@RequestMapping(value = "/closeBranch", method = RequestMethod.POST)
	String closeBranch(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("id") int brId) throws Exception, Exception {		
		int i=brDa.closeBranch(brId);
		if(i>0){
			return "1";
		}
		else{
			return "0";
		}		
	}
	@ResponseBody
	@RequestMapping(value = "/openBranch", method = RequestMethod.POST)
	String openBranch(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("id") int brId) throws Exception, Exception {		
		int i=brDa.openBranch(brId);
		if(i>0){
			return "1";
		}
		else{
			return "0";
		}		
	}
}

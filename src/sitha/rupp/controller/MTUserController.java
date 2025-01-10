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

import sitha.rupp.model.MTUser;
import sitha.rupp.password.GenerateHashPassword;
import sitha.rupp.service.UserDa;


@Controller
@RequestMapping(value = "/user")
public class MTUserController extends GenericController{
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	UserDa userDa=(UserDa)context.getBean("userDa");
	GenerateHashPassword genHashPass=new GenerateHashPassword();
	@ResponseBody
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)	
	String insertUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("username") String username,
			@RequestParam("password") String passWord,
			@RequestParam("branch") int branchId,/*@RequestParam("allowbusinessday") String noneBus,*/
			@RequestParam("selGroup") int gr_id,
			@RequestParam("user_name") String user_name,
			@RequestParam("userId") int id,
			@RequestParam("user_trn") int user_trn,
			@RequestParam("user_selAprove") int user_selAprove,
			@RequestParam("email") String email,
			@RequestParam("till_id") String till_id,
			@RequestParam("remark") String remark,
			@RequestParam("approval_role") int approval_role,
			@RequestParam("phone") String phone,
			@RequestParam("created_by") int created_by			
			) throws Exception, Exception {
			MTUser u=new MTUser();
			u.setUserName(username);
			u.setPassWord(passWord);
			u.setBrachId(branchId);
			u.setAllowBusinessday("0");
			u.setUser_Name(user_name);
			u.setGrId(gr_id);
			u.setAuthorize_by(id);
			u.setUseraprove(user_selAprove);
			u.setEmail(email);
			u.setTill_id(till_id);
			u.setUserId(id);
			u.setRemark(remark);
			u.setApproval_role(approval_role);
			u.setPhone(phone);
			u.setCreated_by(created_by);
			System.out.println("web user:"+ u.toString());
			String retString="";
			//String hashPassword=genHashPass.generateStorngPasswordHash(u.getPassWord());
			//boolean help=userDa.isAlreadyUsed(id, passWord);
			//boolean help=userDa.isUsersExist(id, passWord);
			//if(help==true){
				//return "555";
			//}else{
				if(id>0){
						if(userDa.getUserNameId(user_name, id)>0){
							
							int i=userDa.updatetUser(u, id);
							if(i>0){
								retString="2";
							}
						}
						else{
							if(userDa.getUserName(user_name)>0){
								retString="3";						
							}
							else{
								int i=userDa.updatetUser(u, id);
								if(i>0){
									retString="2";
								}
							}
						}
				}
				else{
					if(userDa.getUserName(user_name)>0){
						retString="3";
					}
					else{
						int i=userDa.insertUser(u);
						if(i>0){
							retString="1";
						}
					}				
				}
			//}
		System.out.println("retString:"+retString);
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
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	String deleteUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("id") int usertId) throws Exception, Exception {		
		int i=userDa.deleteUser(usertId);
		if(i>0){
			return "1";
		}
		else{
			return "0";
		}
	}
	@ResponseBody
	@RequestMapping(value = "/enableUser", method = RequestMethod.POST)
	String enableUser(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("id") int usertId) throws Exception, Exception {		
		int i=userDa.enableUser(usertId);
		if(i>0){
			return "1";
		}
		else{
			return "0";
		}
	}

	
	
}

package sitha.rupp.controller;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.xml.ws.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sitha.rupp.model.Currency;
import sitha.rupp.model.MTUser;
import sitha.rupp.service.CurrencyService;
import sitha.rupp.service.LoginDa;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;

@Controller
public class MTHomeController extends GenericController{
	
	ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
	LoginDa logDa=(LoginDa)context.getBean("loginDa");
	UserDa userDa=(UserDa)context.getBean("userDa");
	PrincebankComponent component=new PrincebankComponent();
	int count=0;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	ModelAndView home(){
		
		/*CurrencyService c=new CurrencyService();
		
		List<Currency>ls=c.getCcy();
		System.out.println(ls);*/
		
		ModelAndView model=new ModelAndView("index");
		return model;
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	void userLogin(HttpServletRequest request,HttpServletResponse respone, ModelMap ModelMap,
			@RequestParam("txtUsername") String userName,
			@RequestParam("txtPassword") String passWord) throws Exception{
			HttpSession session = request.getSession();			
			
			try {
				//int userId=logDa.getUserLogin(userName, passWord);
				int userId=logDa.getUserLoginHashPassword(userName, passWord);
				if(userId>0){
					String lock=userDa.isUserLocked(userId);
					if(lock.trim().equalsIgnoreCase("Y")){
						respone.sendRedirect("./?log=111");
					}else if(lock.trim().equalsIgnoreCase("S")){
						respone.sendRedirect("./?log=222");
					}
				}
				if(userId>0){
					
					MTUser user=userDa.getUserInfo(userId);
				
					session.setAttribute("PRINCE_USER_ID", userId+"");
					session.setAttribute("PRINCE_GR_ID", user.getGrId()+"");
					session.setAttribute("PRINCE_GR_NAME", user.getGorup_name()+"");
					session.setAttribute("APPROVAL_ROLE", user.getApproval_role()+"");
					session.setAttribute("PHONE_NUMBER", user.getPhone_number()+"");
					
					logDa.updateUserStatusOnline(userId, 1);
					if(logDa.insertLoginLogout(userId)>0){
						System.out.println("success : username " + userName);
						session.setAttribute("NOLOGIN","1");
						if(user.getFirst_login().trim().equalsIgnoreCase("Y")){
							System.out.println("+++++This user is first login +++++++");
							respone.sendRedirect("firstlogin");
						}else{
							userDa.checkUserExpiry();
							session.setAttribute("UserID",userId);
							System.out.println("++++ Success login -> Redirect to log ++++");
							respone.sendRedirect("log");
						}
						//respone.sendRedirect("log");
					}
				}
				else{				
					//session.invalidate();
					session.setAttribute("NOLOGIN","0");
					if(logDa.getUserName(userName)>0){
						System.out.println("Login Failed 1");
						session.setAttribute("userName", userName);
						session.setAttribute("passWord", passWord);	
						session.setAttribute("nologinp","p");
						session.setAttribute("nologinn","");
						//respone.sendRedirect("loginfailed");
					}
					else if(logDa.getPassWord(passWord)>0){
						System.out.println("Login Failed 2");
						session.setAttribute("nologinn","n");
						session.setAttribute("nologinp","");
						session.setAttribute("userName", userName);
						session.setAttribute("passWord", passWord);
						//respone.sendRedirect("./");
					}
					else{
						System.out.println("Login Failed 3");
						session.setAttribute("nologinn","n");
						session.setAttribute("nologinp","p");
						session.setAttribute("userName", userName);					
						session.setAttribute("passWord", passWord);
						//respone.sendRedirect("./");
					}
					//System.out.println("Redirect done.");
					//respone.sendRedirect("./");
					count++;
					if(count>=5){
						int i=0;// userDa.lockedUser(userName);
						if(i>0){
							System.out.println("++++++++ This user account was locked ++++++++");
						}
					}
					respone.sendRedirect("./?log=0");
				}
				} catch (IOException e) {
					try {
						respone.sendRedirect("./");
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					e.printStackTrace();
				}
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	void userLogout(HttpServletRequest request,
			HttpServletResponse respone, 
			ModelMap ModelMap)throws Exception,Exception{
			HttpSession session = request.getSession();	
			System.out.println(session.getAttribute("UserID") +" has log out");
			session.invalidate();
			respone.sendRedirect("./");
	}
}

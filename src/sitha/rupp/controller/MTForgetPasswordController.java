package sitha.rupp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

import sitha.rupp.model.MTUser;
import sitha.rupp.model.UserForgetPassword;
import sitha.rupp.password.GenerateHashPassword;
import sitha.rupp.password.VerifyHashPassword;
import sitha.rupp.service.ForgetPasswordService;
import sitha.rupp.service.LoginDa;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;

@Controller
public class MTForgetPasswordController {

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	LoginDa logDa = (LoginDa) context.getBean("loginDa");
	UserDa userDa=(UserDa)context.getBean("userDa");
	PrincebankComponent component = new PrincebankComponent();

	@RequestMapping(value = "/forgetPasswordForm", method = RequestMethod.GET)
	ModelAndView loginFailed() {
		ModelAndView model = new ModelAndView("password/forgetPasswordForm");
		// ModelAndView model=new ModelAndView("");
		return model;
	}
	// user enter forget password form
	@RequestMapping(value = "/forgetPasswordProcess", method = RequestMethod.POST)
	ModelAndView forgetPasswordProcess(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("txtUsername") String userName, @RequestParam("txtEmail") String Email,
			@RequestParam("txtPhone") String Phone) throws Exception {
		ForgetPasswordService forgetPass = new ForgetPasswordService();
		ModelAndView model = null;
		int i = 0;
		UserDa uda = new UserDa();
		// User u = new User();
		i = uda.getUserByNameEmail(userName, Email);
		boolean checkEmailAddress = forgetPass.checkEmailDomain(Email);
		System.out.println(Email);
		if (checkEmailAddress==false) {
			System.out.println("invalid email address, we cannot send to this email");
			model = new ModelAndView("password/forgetPasswordFailed");
			System.out.println("Invalid Information you entered.");
		} else {
			if (i > 0) {
				forgetPass.processUserForgetPassword(userName, Email, Phone);
				model = new ModelAndView("password/forgetPasswordSuccess");
				System.out.println("forgetPasswordSuccess");
			} else {
				model = new ModelAndView("password/forgetPasswordFailed");
				System.out.println("Invalid Information you entered.");
			}
		}
		// ModelAndView model=new ModelAndView("");
		return model;
	}

	// user valid and verify reset code and enter new password
	@ResponseBody
	@RequestMapping(value = "/forgetPasswordVerify1", method = RequestMethod.GET)
	String forgetPasswordVerify(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int uid,
			@RequestParam("key") String key) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		HttpSession session = request.getSession();
		// 1- check username.
		// 2- check key
		// 3- check open change password form.
		UserDa uda = new UserDa();
		MTUser u = new MTUser();
		// int uid = uda.getUserName(userName);
		u = uda.getUserById(uid);
		String s = "";
		// VerifyHashPassword vhash = new VerifyHashPassword();
		// boolean matched = vhash.validatePassword(u.getEmail(),
		// generatedSecuredPasswordHash);
		UserForgetPassword ufp = new UserForgetPassword();
		ForgetPasswordService fps = new ForgetPasswordService();

		ufp = fps.getForgetPasswordInfo(uid);
		// System.out.println("password null :" + ufp.toString());
		if (ufp.getUSER_ID() > 0) {
			if (uid > 0) {
				System.out.println(u.getUserName() + "|" + u.getEmail());
				// verify email with hash mail and key from url
				if (ufp.getHASH_LINK().equals(key)) {
					System.out.println("key is correct");
					session.setAttribute("uid", uid);
					session.setAttribute("key", key);
					response.sendRedirect("forgetPasswordVerify2");
				} else {
					System.out.println(ufp.getHASH_LINK());
					System.out.println(key);
					s = javascriptBox();
					System.out.println("wrong key");
				}

			} else {
				s = javascriptBox();
				System.out.println("wrong user id");
			}
		} else {
			System.out.println(ufp.getUSER_ID());
			s = javascriptBox();
		}
		// System.out.println("here i am");

		// ModelAndView model=new ModelAndView("");
		return s;
	}

	@RequestMapping(value = "/forgetPasswordVerify2", method = RequestMethod.GET)
	ModelAndView forgetPasswordVerify2(HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		ModelAndView model = null;
		HttpSession session = request.getSession();
		try {
			String sessionUid = session.getAttribute("uid").toString();
			String sessionKey = session.getAttribute("key").toString();
			System.out.println(sessionUid + "|" + sessionKey);
			if (!sessionUid.equals("") || !sessionKey.equals("")) {
				model = new ModelAndView("password/forgetPasswordVerify2");
				return model;
			} else {
				// String s=javascriptBox();
				// System.out.println(s);
				System.out.println("error reset password session control" + this.getClass());
				response.sendRedirect("./");
				model = null;
			}
		} catch (Exception e) {
			System.out.println("error reset password session control" + e.getClass());
			e.printStackTrace();
			response.sendRedirect("./");
		}
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/submitPassChange", method = RequestMethod.POST)
	String forgetPasswordVerify3(@RequestParam("txtResetCode") String userInputResetCode,
			@RequestParam("txtPassword1") String password1, @RequestParam("txtPassword2") String password2,
			HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		HttpSession session = request.getSession();
		// ModelAndView model = null;
		String r = "";
		ForgetPasswordService fps = new ForgetPasswordService();
		boolean chkResetCode = false;
		UserDa uda = new UserDa();

		try {
			String sessionUid = session.getAttribute("uid").toString();
			String sessionKey = session.getAttribute("key").toString();
			System.out.println(sessionUid + "|" + sessionKey);
			if (!sessionUid.equals("") || !sessionKey.equals("")) {
				// process update new password

				chkResetCode = fps.verifyResetCodeByUid(sessionUid, userInputResetCode);
				if (chkResetCode) {
					// check new password and retype new password
					if (password1.equals(password2)) {
						// update password in table user
						int i = uda.updatetUserForResetPassword(password1, sessionUid);
						i = fps.updateUser_ForgetPasswordActivate(sessionUid, "YES"); // YES MEAN USER ACTIVATED THIS
																						// RESET CODE
						// System.out.println("check I" + i);
						if (i > 0) {
							r = javascriptBoxOk();
							System.out.println("reset code is corrected");
						} else {
							System.out.println("cannot update new password in db.");
							r = javascriptBox();
						}
					} else {
						r = javascriptBox();
					}
				} else {
					System.out.println("reset code is wrong");
					r = javascriptBox();
				}
			} else {
				// String s=javascriptBox();
				// System.out.println(s);
				r = javascriptBox();
				System.out.println("1 error reset password session control" + this.getClass());
				// r = null;
				// response.sendRedirect("./");
			}
		} catch (Exception e) {
			System.out.println("2 error reset password session control" + e.getClass());
			e.printStackTrace();
			r = javascriptBox();
			// r = null;
			// response.sendRedirect("./");
		}
		return r;
	}

	public String javascriptBox() {
		String show = "";
		show = "<script type = \"text/javascript\">\r\n"
				+ "	alert (\"Invalid information you entered, Please try again!\");\r\n" + "	window.location='./'"
				+ "</script>";

		return show;
	}

	public String javascriptBoxOk() {
		String show = "";
		show = "<script type = \"text/javascript\">\r\n"
				+ "	alert (\"Your password have been reseted. Please login with new password.\");\r\n"
				+ "	window.location='./'" + "</script>";

		return show;
	}
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	ModelAndView resetpassword(HttpServletRequest request,HttpServletResponse respone, ModelMap ModelMap,
			@RequestParam("txtCurrentPWD") String txtCurrentPWD,
			@RequestParam("txtNewPWD") String txtNewPWD,
			@RequestParam("txtConfirm") String txtConfirm,
			@RequestParam("password_strength") String password_strength
			) {
		ModelAndView model=null;
		System.out.println("++++++++++ First Login USER Processing +++++++++++");
		System.out.println("++++++++++ Key flag ++++++++++");
		System.out.println(password_strength);
		
		//int userId=logDa.getUserLoginHashPassword(userName, txtCurrentPWD);
		int userId=component.USER_ID(request);
		try{
			if(txtCurrentPWD.trim().equals(txtNewPWD)){
				System.out.println("++++++++++ USER Input old Password +++++++++++");
				model=new ModelAndView("password/firstloginForm");
				String message="You can't use old password.";
				model.addObject("message", message);
				return model;
			}
			if(!txtNewPWD.trim().equals(txtConfirm.trim())){
				System.out.println("++++++++++ USER Enter mismatched confirm password! +++++++++++");
				model=new ModelAndView("password/firstloginForm");
				String message="Enter mismatched confirm password!";
				model.addObject("message", message);
				return model;
			}
			if(!password_strength.equals("Very Strong")){
				System.out.println("++++++++++ USER Enter Password is "+password_strength+" +++++++++++");
				model=new ModelAndView("password/firstloginForm");
				String message="Your Password is "+password_strength;
				model.addObject("message", message);
				return model;
			}
			if(userId>0){
				MTUser user=userDa.getUserInfo(userId);
				VerifyHashPassword verifyPass=new VerifyHashPassword();
				boolean matched = verifyPass.validatePassword(txtCurrentPWD, user.getPassWord());
				if(matched){
					System.out.println("++++++++ Password matched +++++++++");
					user.setUserId(userId);
					GenerateHashPassword genHashPass=new GenerateHashPassword();
					String hashPassword=genHashPass.generateStorngPasswordHash(txtNewPWD);
					user.setPassWord(hashPassword);
					userDa.changeFirstLogin(user);
					model=new ModelAndView("main");
				}else{
					System.out.println("++++++++ Password mismatched +++++++++");
					model=new ModelAndView("password/firstloginForm");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return model;
	}
}

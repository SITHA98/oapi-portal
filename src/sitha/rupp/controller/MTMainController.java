package sitha.rupp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class MTMainController {
	@RequestMapping(value="/log",method=RequestMethod.GET)
	ModelAndView home(){
		
		ModelAndView model=new ModelAndView("main");//start up form login
		//ModelAndView model=new ModelAndView("");
		return model;
	}
	@RequestMapping(value="/warning",method=RequestMethod.GET)
	ModelAndView warning(){
		
		ModelAndView model=new ModelAndView("warning/warning-page");
		//ModelAndView model=new ModelAndView("");
		return model;
	}
	@RequestMapping(value="/nodata",method=RequestMethod.GET)
	ModelAndView nodata(){
		ModelAndView model=new ModelAndView("error/no-data");
		return model;
	}
	@RequestMapping(value="/nopermission",method=RequestMethod.GET)
	ModelAndView nopermission(){
		
		ModelAndView model=new ModelAndView("warning/noauth-page");
		return model;
	}
	@RequestMapping(value="/firstlogin",method=RequestMethod.GET)
	ModelAndView firstlogin(){
		
		ModelAndView model=new ModelAndView("password/firstloginForm");
		return model;
	}
	@RequestMapping(value="/loginfailed",method=RequestMethod.GET)
	ModelAndView loginFailed(){
		
		ModelAndView model=new ModelAndView("warning/login-failed-page");
		//ModelAndView model=new ModelAndView("");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/loginfailed_2",method=RequestMethod.GET)
	String loginFailed_2(){
		
		//ModelAndView model=new ModelAndView("warning/login-failed-page");
		String str="this is warning";
		//ModelAndView model=new ModelAndView("");
		return str;
	}
}

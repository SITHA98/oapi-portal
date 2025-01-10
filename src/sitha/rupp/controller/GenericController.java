package sitha.rupp.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GenericController {
	private SimpleDateFormat dateFormat;
	
	private ApplicationContext context;
	
	public SimpleDateFormat getDateFormat() {
		if (dateFormat==null)
			dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
		return dateFormat;
	}
	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	public ApplicationContext getContext() {
		context= new ClassPathXmlApplicationContext("Spring_Bean.xml");
		return context;
	}
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	public boolean isLogin(HttpSession session){
		if(session.getAttribute("logout")!=null){
			return true;
		}
		return false;
	}
	//Converting unreadable character in to a normal string
	public String decryptCode(String source){
		String stringReturn="";
		//char ch1='#',ch2='@';
		for(int i=0;i<source.length();i++){
			char c=source.charAt(i);
			if(c!=(char)35 && c!=(char)64 ){
				stringReturn=stringReturn+ Character.toString((char)((int)c-56));
			}
		}
		return stringReturn;
	}
	//Converting character in to unreadable format 
	public String encryptCode(String source){
		String stringReturn="";
		//char ch1='#',ch2='@';
		for(int i=0;i<source.length();i++){
			char c=source.charAt(i);
			if(c!=(char)35 && c!=(char)64 ){
				stringReturn=stringReturn+ Character.toString((char)((int)c+56));
			}
		}
		return stringReturn;
	}
}

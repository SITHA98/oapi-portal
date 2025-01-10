package sitha.rupp.MainTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.service.PrincebankComponent;

public class CALL_STORE_PRO extends GenericDaSupport{
	static ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	static PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	public static void main(String[] args) {
		Application_Properties.SERIAL=1;
		
		int i = component.getJdbcTemplate().update("call ST_ADD_TEST(?,?)","1","p2");
		
		System.out.println("eventLog: "+i);

	}

}

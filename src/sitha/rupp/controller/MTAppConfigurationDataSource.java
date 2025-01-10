package sitha.rupp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sitha.rupp.configuration.Application_Properties;

@RestController
public class MTAppConfigurationDataSource {
	
	/*public static void main(String []arg){
		
		System.out.println("b4:"+Application_Properties.SERIAL);
		Application_Properties.SERIAL=1;
		System.out.println("after:"+Application_Properties.SERIAL);
		
	}*/
	
	@RequestMapping("/configuration/{SERIAL}")
	public String configureDataSource(@PathVariable("SERIAL")int SERIAL){

		Application_Properties.SERIAL=SERIAL;
		
		System.out.println("DATA SOURCE OPTION "+Application_Properties.SERIAL +" IS ACTIVED");
		
		return "=============<<*** PRINCE BANK CONFIGURATION OPTIONS SOURCE ***>>============\n</br>"
				+"*******=  1-SYSTEM CONNECT TO THE SERVER FOR EXTRACT DATA               \n</br>"
				+"*******=  2-SYSTEM CONNECT TO THE SERVER FOR VIEW REPORTS               \n</br>"
				+"<h4 style='color: green;'>*******=  *-NOW ACTIVED OPTION IS "+Application_Properties.SERIAL+ " </h4>\n</br>"
				+"=============<<*** PRINCE BANK CONFIGURATION OPTIONS SOURCE ***>>============</br>";
	}
	@RequestMapping("/configuration")
	public String configureDataSource(){
		
		System.out.println("DATA SOURCE OPTION "+Application_Properties.SERIAL +" IS ACTIVED");
		
		return "=============<<*** PRINCE BANK CONFIGURATION OPTIONS SOURCE ***>>============\n</br>"
				+"*******=  1-SYSTEM CONNECT TO THE SERVER FOR EXTRACT DATA               \n</br>"
				+"*******=  2-SYSTEM CONNECT TO THE SERVER FOR VIEW REPORTS               \n</br>"
				+"<h4 style='color: red;'>*******=  *-NOW ACTIVED OPTION IS "+Application_Properties.SERIAL+ " </h4>\n</br>"
				+"=============<<*** PRINCE BANK CONFIGURATION OPTIONS SOURCE ***>>============</br>";
	}
}

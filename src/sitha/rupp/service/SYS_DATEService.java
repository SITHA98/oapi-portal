package sitha.rupp.service;

import java.util.Date;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;

public class SYS_DATEService extends GenericDaSupport{
	
	public String SYS_FCSRV_DATE() throws Exception{
		
		Date d = new Date();
		
		String retString=ClsHelper.formatDate(d);
		System.out.println("retString: "+retString);
		//retString="10-Feb-2020";
		return retString;
	}
}

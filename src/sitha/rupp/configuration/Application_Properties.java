package sitha.rupp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//@PropertySource(value= {"classpath:jdbc.properties"})
public class Application_Properties {
	//===== SERIAL 1 for Database MIS Backup =====//

	public static String PPWSA_DATA_URL="jdbc:oracle:thin:@172.16.111.5:1521:LHI"; //LIVE-DC
//	public static String PPWSA_DATA_URL="jdbc:oracle:thin:@172.16.7.111:1521:LHI"; //LIVE-DR
//	public static String PPWSA_DATA_URL="jdbc:oracle:thin:@127.0.0.1:1521:LHI";
//	public static String PPWSA_DATA_URL="jdbc:oracle:thin:@172.16.2.20:1521:LHIDB3"; //UAT
	public static String PPWSA_DATA_USER_NAME="APIREPORT";
	public static String PPWSA_DATA_PASSWORD="APIREPORT";
	
//===== SERIAL 2 for Database MIS Backup =====//
//	 SERVER Production //
	public static String AGILIS_PRD_DATA_URL = "jdbc:oracle:thin:@172.16.111.5:1521:LHI";
//	public static String AGILIS_PRD_DATA_URL = "jdbc:oracle:thin:@127.0.0.1:1521:LHI";
//	public static String AGILIS_PRD_DATA_URL = "jdbc:oracle:thin:@172.16.2.20:1521:LHIDB3";
	public static String AGILIS_PRD_DATA_USER_NAME = "RAIMS";
	public static String AGILIS_PRD_DATA_PASSWORD = "RAIMS";
	
	//===============================
	public static String AGILIS_PRD_DATA_URL_UAT = "jdbc:oracle:thin:@172.16.3.20:1521:LHIDB3";
//	public static String AGILIS_PRD_DATA_URL_UAT = "jdbc:oracle:thin:@172.16.2.16:1521:LHIDB1";
//	public static String AGILIS_PRD_DATA_URL_UAT="jdbc:oracle:thin:@127.0.0.1:1521:LHI";
	public static String AGILIS_PRD_DATA_USER_NAME_UAT = "APIREPORT";
	public static String AGILIS_PRD_DATA_PASSWORD_UAT = "APIREPORT";
	
	@Value("${SERIAL}")
	public static int SERIAL=1;
	
}

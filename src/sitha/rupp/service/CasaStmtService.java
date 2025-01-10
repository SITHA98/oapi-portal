package sitha.rupp.service;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;

public class CasaStmtService extends GenericDaSupport {

	public boolean isStaffAC(String accountno){
		
		boolean help=false;
		
		String sql="SELECT COUNT(CUSTOMER_NO)COUNTS FROM STTM_CUSTOMER WHERE CUSTOMER_CATEGORY = 'STAFF'"
				+ " AND CUSTOMER_NO IN (SELECT a.cust_no FROM STTM_CUST_ACCOUNT A WHERE a.cust_ac_no='"+accountno.trim()+"')";
		System.out.println(sql);
		Application_Properties.SERIAL=2;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			int x=Integer.parseInt(row.getString("COUNTS"));
			if(x>0){
				help=true;
			}
		}
		return help;
	}
}

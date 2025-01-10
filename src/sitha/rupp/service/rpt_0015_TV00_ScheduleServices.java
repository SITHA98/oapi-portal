package sitha.rupp.service;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;

public class rpt_0015_TV00_ScheduleServices extends GenericDaSupport {

	public String getExcessDetail(String policyNumber) {
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String P_POLH_SYS_ID=getSysIdByPolNum(policyNumber);
		String resResult="";
		String sql="SELECT FN_GET_TV00_EXCESS_DETAIL('"+P_POLH_SYS_ID+"') AS EXCESS_DETAIL FROM DUAL";
		System.out.println("SQL:"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			resResult=row.getString("EXCESS_DETAIL");
		}		
		System.out.println("resResult:"+resResult);
		return resResult;
	}
	
	public String getSysIdByPolNum(String policyNumber) {
		//policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String sql="SELECT P.POLH_SYS_ID AS POLH_SYS_ID FROM RAIMS.INT_UW_POLH P WHERE P.POLH_POLNUM='"+policyNumber+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- report ---------\n"+sql);
		
		String str="";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			str="";
			str=row.getString("POLH_SYS_ID");
		}
		//System.out.println(ls.toString());
		System.out.println(str);
		return str;
	}
	
	public String getTravelPlan(String policyNumber) {
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		
		String resResult="";
		String sql="SELECT FN_GET_TV00_PLAN('"+policyNumber+"') AS V_PLAN FROM DUAL";
		System.out.println("SQL:"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			resResult=row.getString("V_PLAN");
		}		
		System.out.println("resResult:"+resResult);
		return resResult;
	}
}

package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0005_fire_and_engineering_model;

public class rpt_0005_fire_and_engineeringServices extends GenericDaSupport {

	public String getFireEngineerList(String sys_id){
		String sql="SELECT P1.PCOND_CODE AS PCOND_CODE,C2.COND_DESC_MARINE AS PCOND_DESC FROM RAIMS.INT_UW_PCOND P1 \r\n" + 
				"INNER JOIN RAIMS.INM_MST_COND C2 ON P1.PCOND_CODE=C2.COND_CODE\r\n" + 
				"WHERE P1.PCOND_POLH_SYS_ID='"+sys_id+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- fire and engineering schedule report ---------\n"+sql);
		List<String>ls=new ArrayList<String>();
		String str="";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0005_fire_and_engineering_model fireEng=new rpt_0005_fire_and_engineering_model();
			
			fireEng.setCondictionCode(row.getString("PCOND_CODE"));
			fireEng.setCondictionDesc(row.getString("PCOND_DESC"));
			str +=fireEng.toString();
			//ls.add(fireEng.toString());
		}
		//System.out.println(ls.toString());
		System.out.println(str);
		return str;
	}
	
	public String getSysIdByPolicyNumber(String policyNumber) {
		String sql="SELECT P.POLH_SYS_ID AS POLH_SYS_ID FROM RAIMS.INT_UW_POLH P WHERE P.POLH_POLNUM='"+policyNumber+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- fire and engineering schedule report ---------\n"+sql);
		
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
}

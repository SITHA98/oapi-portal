package sitha.rupp.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.CondictionClausesModel;

public class CondictionClauseDetailService extends GenericDaSupport {

	public List<CondictionClausesModel> getCondClause(String sys_id){
		String sql="SELECT P1.PCOND_SYS_ID,P1.PCOND_CODE AS PCOND_CODE,C2.COND_DESC AS PCOND_DESC,CTX.CTX_TEXT AS COND_CLAUSE_TEXT\r\n" +				
				"FROM INT_UW_PCOND P1  \r\n" + 
				"INNER JOIN INM_MST_COND C2 ON P1.PCOND_CODE=C2.COND_CODE \r\n" + 
				"INNER JOIN INM_MST_CONDTEXT CTX ON P1.PCOND_CODE=CTX.CTX_COND_CODE\r\n" + 
				"WHERE P1.PCOND_POLH_SYS_ID='"+sys_id+"'";
		
		List<CondictionClausesModel>ls=new ArrayList<CondictionClausesModel>();
		String str="";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			CondictionClausesModel cond=new CondictionClausesModel();
			cond.setCondictionCode(row.getString("PCOND_CODE"));
			cond.setCondictionDesc(row.getString("PCOND_DESC"));
			cond.setCtxText(row.getString("COND_CLAUSE_TEXT"));
			ls.add(cond);
		}
		System.out.println(str);
		return ls;
		
	}
}

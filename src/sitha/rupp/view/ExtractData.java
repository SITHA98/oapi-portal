package sitha.rupp.view;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;

public class ExtractData extends GenericDaSupport{
	
	public void branchLst(){
		String sql="Select * from sttm_branch";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			System.out.println("==> "+row.getString("BRANCH_CODE"));
			System.out.println("==> "+row.getString("BRANCH_NAME"));
		}
	}

}

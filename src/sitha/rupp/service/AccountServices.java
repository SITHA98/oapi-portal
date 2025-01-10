package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.Account;

public class AccountServices extends GenericDaSupport{
	
	public List<Account> getAllAccount(){
		List<Account> aList=new ArrayList<Account>();
		String sql="select * from accounts";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		
		while(row.next()) {
			Account ac =new Account();
			ac.setId(row.getString("id"));
			ac.setName(row.getString("name"));
			aList.add(ac);
//			System.out.println(ac.toString());
		}
		System.out.println(aList);
		return aList;
	}
	
	
	
}



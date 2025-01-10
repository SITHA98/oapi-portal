package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.CASADashboard;

public class DashboardService extends GenericDaSupport {
	
	PrincebankComponent component=new PrincebankComponent();
	
	public List<CASADashboard> initlsCASADAB(String branchCode,String date){
		
		Application_Properties.SERIAL=1;
		String sql="SELECT ACCOUNT_TYPE,sum(ACY_CURR_BALANCE)balance,ccy,count(*) counts "+
				" FROM CASA_BALANCE  "+
				" where ACY_CURR_BALANCE <> 0  and bk_date='"+date+"'"+
				" and branch_code like nvl('"+branchCode.trim()+"','%')  "+
				" group by ACCOUNT_TYPE,ccy  "+
				" order by 1,2,3";
		List<CASADashboard>lscasa=new ArrayList<>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			CASADashboard casa=new CASADashboard();
			casa.setAccountType(row.getString("ACCOUNT_TYPE"));
			casa.setBalance(Double.parseDouble(row.getString("balance")));
			casa.setCcy(row.getString("ccy"));
			casa.setCounts(Long.parseLong(row.getString("counts")));
			
			lscasa.add(casa);
		}
		return lscasa;
	}
	
	
	
	public CASADashboard initClientNCASA(String branchCode,String date){
		Application_Properties.SERIAL=1;
		String sql="SELECT COUNT(DISTINCT CUST_NO)NO_CLIENT,count(CUST_AC_NO )NO_CASA  FROM CASA_BALANCE WHERE BK_DATE='"+date+"' AND ACY_CURR_BALANCE< > 0";
		System.out.println(sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		CASADashboard casa=new CASADashboard();
		while(row.next()){
			casa.setNo_of_client(Long.parseLong(row.getString("NO_CLIENT")));
			casa.setNo_of_casa(Long.parseLong(row.getString("NO_CASA")));
		}
		return casa;
	}
}

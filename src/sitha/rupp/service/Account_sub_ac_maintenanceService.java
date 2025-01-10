package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.Account_sub_ac_maintenance;

public class Account_sub_ac_maintenanceService extends GenericDaSupport {

	public List<Account_sub_ac_maintenance> getAllAccountMapping(){
		String sql="SELECT \r\n" + 
				"--M.* \r\n" + 
				"M.ACCOUNT_TYPE\r\n" + 
				",M.ACCOUNT_DESC AS ACCOUNT_TYPE_DESC\r\n" + 
				",A.*\r\n" + 
				"FROM ACCOUNTING_TYPE M\r\n" + 
				"INNER JOIN IBM_ACCOUNT A ON A.BATCH_NUMBER=M.ACCOUNT_CODE";
		System.out.println("-------- Account Maintenance ---------\n"+sql);
		List<Account_sub_ac_maintenance>ls=new ArrayList<Account_sub_ac_maintenance>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			Account_sub_ac_maintenance ac=new Account_sub_ac_maintenance();
			ac.setAccount_type(row.getString("ACCOUNT_TYPE"));
			ac.setAccount_type_desc(row.getString("ACCOUNT_TYPE_DESC"));
			ac.setAc_sys_id(row.getString("AC_SYS_ID"));
			ac.setBatch_number(row.getString("BATCH_NUMBER"));
			ac.setBatch_desc(row.getString("BATCH_DESC"));
			ac.setAccount_code(row.getString("ACCOUNT_CODE"));
			ac.setAccount_Desc(row.getString("ACCOUNT_DESC"));
			ac.setSUB_TYPE_CODE(row.getString("SUB_TYPE_CODE"));
			ac.setSUB_TYPE_DESC(row.getString("SUB_TYPE_DESC"));
			ac.setCURRENCY(row.getString("CURRENCY"));
			ac.setSTATUS(row.getString("STATUS"));
			ac.setCREATED_BY(row.getString("CREATED_BY"));
			ac.setCREATED_DATE(row.getString("CREATED_DATE"));
			ac.setAUTHORIZED_BY(row.getString("AUTHORIZED_BY"));
			ac.setAUTHORIZED_DATE(row.getString("AUTHORIZED_DATE"));
			ls.add(ac);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	
	public Account_sub_ac_maintenance getAccountDetailbyAccountCode(String Account_code) {
		String sql="SELECT \r\n" + 
				"--M.* \r\n" + 
				"M.ACCOUNT_TYPE\r\n" + 
				",M.ACCOUNT_DESC AS ACCOUNT_TYPE_DESC\r\n" + 
				",A.*\r\n" + 
				"FROM ACCOUNTING_TYPE M\r\n" + 
				"INNER JOIN IBM_ACCOUNT A ON A.BATCH_NUMBER=M.ACCOUNT_CODE \r\n" + 
				"--WHERE A.ACCOUNT_CODE='102010' \r\n"+
				"WHERE A.ACCOUNT_CODE='"+Account_code+"' \r\n";
				
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		
		Account_sub_ac_maintenance acModel=new Account_sub_ac_maintenance();
		while(row.next()){
			acModel=new Account_sub_ac_maintenance();
			acModel.setBatch_number(row.getString("BATCH_NUMBER"));
			acModel.setBatch_desc(row.getString("BATCH_DESC"));
			acModel.setAccount_code(row.getString("ACCOUNT_CODE"));
			acModel.setAccount_Desc(row.getString("ACCOUNT_DESC"));	
		}
		
		return acModel;
	}
/*	
	public List<FinCOA>initlsCOA(){
		List<FinCOA>ls=new ArrayList<>();
		Application_Properties.SERIAL=1;
		String sql="SELECT NO,GL_CODE,CODE,GL_DESC,DELETED FROM ACCT_COA_MAIN WHERE DELETED='N'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		FinCOA coa=null;
		while(row.next()){
			coa=new FinCOA();
			coa.setNo(Integer.parseInt(row.getString("NO")));
			coa.setGlCode(row.getString("GL_CODE"));
			coa.setCode(row.getString("CODE"));
			coa.setGlDesc(row.getString("GL_DESC"));
			coa.setDeleted(row.getString("DELETED"));
			ls.add(coa);
		}
		return ls;
	}
	public FinCOA getCOA(int id){
		Application_Properties.SERIAL=1;
		String sql="SELECT NO,GL_CODE,CODE,GL_DESC,DELETED FROM ACCT_COA_MAIN WHERE DELETED='N' AND NO="+id;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		FinCOA coa=null;
		while(row.next()){
			coa=new FinCOA();
			coa.setNo(Integer.parseInt(row.getString("NO")));
			coa.setGlCode(row.getString("GL_CODE"));
			coa.setCode(row.getString("CODE"));
			coa.setGlDesc(row.getString("GL_DESC"));
			coa.setDeleted(row.getString("DELETED"));
		}
		System.out.println(coa.toString());
		return coa;
	}
	
	public int updateCOA(FinCOA coa){
		String sql="";
		sql="UPDATE ACCT_COA_MAIN SET GL_CODE='"+coa.getGlCode()+"',CODE='"+coa.getCode()+"'"
			+ ",GL_DESC='"+coa.getGlDesc()+"',DELETED='"+coa.getDeleted()+"',REMARK='"+coa.getRemark()+"'"
			+ " Where NO="+coa.getNo();
		Application_Properties.SERIAL=1;
		return getJdbcTemplate().update(sql);
	}
	
	public int insertCOA(FinCOA coa){
		String sql="";
		sql="INSERT INTO ACCT_COA_MAIN(NO,GL_CODE,CODE,GL_DESC,CREATED_BY,CREATED_DATE,AUTHORIZED_BY,AUTHORIZED_DATE,REMARK)"
			+ "VALUES(COA_NO_SEQ.NEXTVAL,'"+coa.getGlCode()+"','"+coa.getCode()+"','"+coa.getGlDesc()+"',"+coa.getCreated_by()+""
			+ ",sysdate,"+coa.getAuthorized_by()+",sysdate,'"+coa.getRemark()+"')";
		Application_Properties.SERIAL=1;
		return getJdbcTemplate().update(sql);
	}
	*/
}

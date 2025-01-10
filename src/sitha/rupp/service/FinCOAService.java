package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.FinCOA;

public class FinCOAService extends GenericDaSupport {

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
}

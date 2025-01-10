package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.MTBranch;



public class BranchDa  extends GenericDaSupport{
	public int insertBranch(MTBranch b){
		String SQL="Insert into branch(BRANCH_ID,DESCRIPTION,CODE,NAMES,NAMES_KH,DESCRIPTION_KH,"
				+ "	ADDRESS,EMAIL,WEBSITE,PHONE,CREATE_DATE,CREATE_BY)values "
				+ "("+b.getBranchCode()+",'"+b.getBranchDesen()+"','"+b.getBranchCode()+"','"
				+ b.getBranchNameen()+"','"+b.getBranchNamekh()+"','"+b.getBranchDesKH()+"','"+b.getBranchAddress()+"','"
				+ b.getBranchEmail()+"','"+b.getBranchWebsite()+"','"+b.getBranchPhone()+"',"
				+ "sysdate,1)";
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	
	public List<MTBranch> getBranchList(){
		List<MTBranch> brl=new ArrayList<>();
		String sql="select * from branch order by CODE ASC";
		System.out.println(sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			MTBranch br=new MTBranch();
			br.setBranchId(Integer.parseInt(row.getString("BRANCH_ID")));
			br.setBranchCode(row.getString("CODE"));
			br.setBranchNameen(row.getString("NAMES"));
			br.setBranchNamekh(row.getString("NAMES_KH"));
			br.setBranchPhone(row.getString("PHONE"));
			br.setBranchStatus(row.getString("DELETED"));
			brl.add(br);
		}
		return brl;
	}
	
	
	public MTBranch getBranchInfo(int bId){
		MTBranch br=new MTBranch();
		String sql="select * from branch b where b.BRANCH_ID="+bId;
		System.out.println(sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			br.setBranchCode(row.getString("CODE"));
			br.setBranchNameen(row.getString("NAMES"));
			br.setBranchAddress(row.getString("ADDRESS"));
			br.setBranchDesen(row.getString("DESCRIPTION"));
			br.setBranchDesKH(row.getString("DESCRIPTION_KH"));
			br.setBranchEmail(row.getString("EMAIL"));
			br.setBranchNamekh(row.getString("NAMES_KH"));
			br.setBranchPhone(row.getString("PHONE"));
			br.setBranchWebsite(row.getString("WEBSITE"));
		}
		System.out.println(br.getBranchNameen());
		return br;
	}
	public int getBranchCode(String code){
		String sql="select b.BRANCH_ID from BRANCH b WHERE b.CODE='"+code+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("BRANCH_ID"));
		}
		return id;
	}
	public int getBranchCodeId(String code,int bId){
		String sql="select b.BRANCH_ID from BRANCH b WHERE b.BRANCH_ID="+bId+" and b.CODE='"+code+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("BRANCH_ID"));
		}
		return id;
	}
	public int getBranchName(String name){
		String sql="select b.BRANCH_ID from BRANCH b WHERE b.NAMES='"+name+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("BRANCH_ID"));
		}
		return id;
	}
	public int getBranchNameId(String name,int bId){
		String sql="select b.BRANCH_ID from BRANCH b WHERE b.BRANCH_ID="+bId+" and b.NAMES='"+name+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("BRANCH_ID"));
		}
		return id;
	}
	public int getBranchNameCode(String name,String code,int bId){
		String sql="select b.BRANCH_ID from BRANCH b WHERE b.BRANCH_ID="+bId+" and b.CODE='"+code+"' and b.NAMES='"+name+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("BRANCH_ID"));
		}
		return id;
	}
	
	public int updateBranch(MTBranch bm,int brId) {
		try {			
			String sql = "UPDATE branch SET DESCRIPTION='"+bm.getBranchDesen()+"',CODE='"+bm.getBranchCode()+"',"
					+ "NAMES='"+bm.getBranchNameen()+"',NAMES_KH='"+bm.getBranchNamekh()+"',"
					+ "DESCRIPTION_KH='"+bm.getBranchDesKH()+"',ADDRESS='"+bm.getBranchAddress()+"',EMAIL='"+bm.getBranchEmail()+"',"
					+ "WEBSITE='"+bm.getBranchWebsite()+"',PHONE='"+bm.getBranchPhone()+"',"
					+ "UPDATE_BY=1,UPDATE_DATE=sysdate WHERE BRANCH_ID="+brId;
			return getJdbcTemplate().update(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int closeBranch(int id){
		String SQL="update branch b set b.DELETED='Y' WHERE b.BRANCH_ID="+id;
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	public int openBranch(int id){
		String SQL="update branch b set b.DELETED='N' WHERE b.BRANCH_ID="+id;
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	
}

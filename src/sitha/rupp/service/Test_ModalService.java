package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.Test_Modal;


public class Test_ModalService extends GenericDaSupport{
	
	PrincebankComponent component=PrincebankComponent.getInstance();
//	EntriesService entriesService=new EntriesService();
	
	public int insertTestModal(Test_Modal testmodal){
		String SQL="";
		
		//faInfo.setSubCategory_Id(categoryId);
		SQL+="\nINSERT INTO APIREPORT.TEST_MODAL(TEST_ID,TEST_NAME,VALUE_DATE,DELETED,BRANCH_CODE,RECORD_STATUS,CREATE_BY,CREATE_DATE) VALUES(TEST_MODAL_ID_SEQ.NEXTVAL,'"
				+testmodal.getTest_name()+"',to_date('" + testmodal.getValue_date() + "','dd-MM-yyyy hh24:mi:ss')"
				+ ",'"+"N"+ "','"+ testmodal.getBranch_code()+"',"+"'U','"+testmodal.getUserid()+"',sysdate);";
		
		System.out.println("Test modal creation proceed ........\n");
		System.out.println(SQL);
		return getJdbcTemplate().update(ClsHelper.Begin()+ SQL +ClsHelper.End());
	}
	
	public int editTestModal(Test_Modal testmodal){
		String SQL="";
		
		//faInfo.setSubCategory_Id(categoryId);
		SQL+="\nUPDATE APIREPORT.TEST_MODAL T SET T.TEST_NAME='"+testmodal.getTest_name()+"', T.VALUE_DATE=to_date('" + testmodal.getValue_date() + "','yyyy-MM-dd hh24:mi:ss')"
				+ ",T.BRANCH_CODE='" + testmodal.getBranch_code() +"'"
				+ "WHERE T.TEST_ID='"+testmodal.getTest_id()+"';";
		System.out.println("update test modal proceed ........\n");
		System.out.println(SQL);
		return getJdbcTemplate().update(ClsHelper.Begin()+ SQL +ClsHelper.End());
	}
	
	public int deleteTestModal(Test_Modal testmodal){
		String SQL="";
		
		//faInfo.setSubCategory_Id(categoryId);
		SQL+="\nUPDATE APIREPORT.TEST_MODAL T SET T.DELETED='Y' WHERE T.TEST_ID='"+testmodal.getTest_id()+"';";
		System.out.println("DELETED test modal proceed ........\n");
		System.out.println(SQL);
		return getJdbcTemplate().update(ClsHelper.Begin()+ SQL +ClsHelper.End());
	}
	
	public boolean isExistTestModal(String test_id){
		boolean help=false;
		int count=0;
		String SQL="SELECT COUNT(*)COUNTS FROM APIREPORT.TEST_MODAL WHERE TEST_ID='"+test_id+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(SQL);
		while(row.next()){
			count=Integer.parseInt(row.getString("COUNTS"));
			if(count>0){
				help=true;
			}else{
				help=false;
			}
		}
		return help;
	}
	
	public List<Test_Modal> getListTestModal(int userid){
		Test_Modal testmodal = new Test_Modal();
		List<Test_Modal> alist = new ArrayList<Test_Modal>();
		String SQL="";		
		SQL="SELECT test_id,test_name,to_char(value_date,'DD-Mon-YYYY hh24:mi:ss') value_date,branch_code FROM APIREPORT.TEST_MODAL WHERE DELETED='N' and CREATE_BY="+userid;
		System.out.println(SQL);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(SQL);
		while(row.next()){
			testmodal = new Test_Modal();
			testmodal.setTest_id(row.getString("TEST_ID"));
			testmodal.setTest_name(row.getString("TEST_NAME"));
			testmodal.setValue_date(row.getString("VALUE_DATE"));
			testmodal.setBranch_code(row.getString("branch_code"));
//			System.out.println(testmodal.toString());
			alist.add(testmodal);
		}
		return alist;
	}
	public Test_Modal getOneTestModal(String testid){
		Test_Modal testmodal = new Test_Modal();
		
		String SQL="";
		
			SQL="SELECT * FROM APIREPORT.TEST_MODAL WHERE TEST_ID='"+testid+"'";
		
		System.out.println("getOneTestModal "+SQL);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(SQL);
		while(row.next()){
			testmodal = new Test_Modal();
			testmodal.setTest_id(row.getString("TEST_ID"));
			testmodal.setTest_name(row.getString("TEST_NAME"));
			testmodal.setValue_date(row.getString("VALUE_DATE"));
			testmodal.setBranch_code(row.getString("BRANCH_CODE"));
		}
		
		return testmodal;
	}
}

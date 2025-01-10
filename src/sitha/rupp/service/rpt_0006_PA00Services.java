package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.PA00TotalBenefit_model;
import sitha.rupp.model.rpt_0005_fire_and_engineering_model;

public class rpt_0006_PA00Services extends GenericDaSupport {

	public String getFireEngineerList(String sys_id){
		String sql="SELECT P1.PCOND_CODE AS PCOND_CODE,C2.COND_DESC AS PCOND_DESC FROM RAIMS.INT_UW_PCOND P1 \r\n" + 
				"INNER JOIN RAIMS.INM_MST_COND C2 ON P1.PCOND_CODE=C2.COND_CODE\r\n" + 
				"WHERE P1.PCOND_POLH_SYS_ID='"+sys_id+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- CONDICTION AND CLAUSES ---------\n"+sql);
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
		System.out.println("-------- pa00 report ---------\n"+sql);
		
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
	
	public String getSysIdByPolNum(String policyNumber) {
		String sql="SELECT P.POLH_SYS_ID AS POLH_SYS_ID FROM RAIMS.INT_UW_POLH P WHERE P.POLH_POLNUM='"+policyNumber+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- HC01 report ---------\n"+sql);
		
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
	public String getProductCodeByPolNum(String policyNumber) {
		String sql="SELECT P.POLH_COB AS PRODUCT_CODE FROM RAIMS.INT_UW_POLH P WHERE P.POLH_POLNUM='"+policyNumber+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- getProductCodeByPolNum ---------\n"+sql);
		
		String str="";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			str="";
			str=row.getString("PRODUCT_CODE");
		}
		//System.out.println(ls.toString());
		System.out.println(str);
		return str;
	}
	
	public PA00TotalBenefit_model getTotalBenefitByPolicyNumber(String policynumber) {
		String sql="WITH ALIST AS (\r\n" + 
				"SELECT \r\n" + 
				"rownum as rowNumber, \r\n" +
				"P.POLH_SYS_ID,\r\n" + 
				"P.POLH_SRNUM,\r\n" + 
				"P.POLH_POLNUM,\r\n" + 
				"P.POLH_DISPLAY_POLNUM, \r\n" + 
				"(SELECT B.CVBR_BRANCH_NAME FROM RAIMS.IBM_PARTY_BRANCH B WHERE CVBR_BRANCH_CODE IN(P.POLH_DEBIT_PARTY)) AS INT_NAME,\r\n" + 
				"P.POLH_DEBIT_PARTY AS INT_CODE,\r\n" + 
				"UPPER(P.POLH_ASSRDNM) AS INSURED_NAME,\r\n" + 
				"P.POLH_CLIENT AS INSURED_CODE,\r\n" + 
				"P.POLH_DEBIT_PARTY,\r\n" + 
				"replace(replace(P.POLH_ASSRD_ADDR,chr(10),' '),chr(13),' ') AS ADDRESS,\r\n" + 
				"'N/A' AS BUSINESS_REG_NO,\r\n" + 
				"P.POLH_INDUSTRY AS BUSINESS_OCCUPATION_CODE,\r\n" + 
				"(SELECT IND_DESC FROM RAIMS.INM_MST_IND WHERE IND_CODE=P.POLH_INDUSTRY) AS BUSINESS_OCCUPATION,\r\n" + 
				"TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY')EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') EXPIRY_DATE,\r\n" + 
				"G2.PINT_GEO AS GEO_LIMIT,\r\n" + 
				"case when G2.PINT_GEO='01' THEN 'Standard-24 Hr Worldwide' else 'Working Hr within Cambodia' end AS TERRITORIAL_LIMIT,\r\n" + 
				"ROUND(P.POLH_PREM_FC,2) AS PREMIUM,\r\n" + 
				"'1.00' AS ADMIN_FEE,\r\n" + 
				"(P.POLH_PREM_FC+1) AS TOTAL,\r\n" + 
				"G2.PINT_INSURED,\r\n" + 
				"G2.PINT_SEX,TO_CHAR(G2.PINT_DOB,'DD-MON-YYYY') PINT_DOB,G2.PINT_AGE,G2.PINT_MASS_NAME,G2.PINT_SI_BC,G2.PINT_PREM_BC,G2.PINT_SYS_ID,\r\n" + 
				"G3.* \r\n" + 
				"FROM RAIMS.INT_UW_POLH P\r\n" + 
				"INNER JOIN RAIMS.INT_UW_PINT_MED G2 ON P.POLH_SYS_ID=G2.PINT_POLH_SYS_ID\r\n" + 
				"INNER JOIN (SELECT \r\n" + 
				"    SUM(CASE WHEN G3.PCOV_COVER='BENA' THEN G3.PCOV_SI_BC ELSE 0 END) BENA,\r\n" + 
				"    SUM(CASE WHEN G3.PCOV_COVER='BENB' THEN G3.PCOV_SI_BC ELSE 0 END) BENB,\r\n" + 
				"    SUM(CASE WHEN G3.PCOV_COVER='BENC' THEN G3.PCOV_SI_BC ELSE 0 END) BENC,  \r\n" + 
				"    MAX(G3.PCOV_SYS_ID) AS PCOV_SYS_ID, G3.PCOV_PINTMED_SYS_ID \r\n" + 
				"    FROM RAIMS.INT_UW_PCOV G3 \r\n" + 
				"    GROUP BY G3.PCOV_PINTMED_SYS_ID) G3 ON G3.PCOV_PINTMED_SYS_ID=G2.PINT_SYS_ID\r\n" + 
				"    WHERE P.POLH_POLNUM = '"+policynumber+"' AND P.POLH_EXTNUM=0 AND G2.PINT_EXTNUM=0" + 
				"    )\r\n" + 
				"SELECT \r\n" + 
				"MAX(A.rowNumber) AS rowNumber, \r\n"+
				"SUM(A.BENA) AS BENA, \r\n" + 
				"SUM(A.BENB) AS BENB,\r\n" + 
				"SUM(A.BENC) AS BENC\r\n" + 
				"FROM ALIST A\r\n" + 
				"GROUP BY A.POLH_SRNUM";
			System.out.println(sql);
			SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
			PA00TotalBenefit_model pa00TotalBen=new PA00TotalBenefit_model();
			while(row.next()){
				pa00TotalBen.setTotalRecord(row.getString("rowNumber"));
				pa00TotalBen.setTotalBenA(row.getString("BENA"));
				pa00TotalBen.setTotalBenB(row.getString("BENB"));
				pa00TotalBen.setTotalBenC(row.getString("BENC"));
			}
			
		return pa00TotalBen;
	}
}

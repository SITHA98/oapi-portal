package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.rpt_0003_policy_model;

public class rpt_0003_policyServices extends GenericDaSupport {

	public List<rpt_0003_policy_model> getPolicyList(String fromDate, String toDate){
		String sql="SELECT   \r\n" + 
				"				--P.POLH_SYS_ID AS POLH_SYS_ID, \r\n" + 
				"				--P.POLH_EXTNUM AS POLH_EXTNUM, \r\n" + 
				"				--P.POLH_SRNUM AS POLH_SRNUM, \r\n" + 
				"				--P.POLH_STATUS AS POLH_STATUS, \r\n" + 
				"				P.POLH_POST_PERIOD AS POLH_POST_PERIOD, \r\n" + 
				"				ROW_NUMBER() OVER (order by P.POLH_ISSDT) AS No, \r\n" + 
				"				--P.POLH_SRNUM AS SEQ, \r\n" + 
				"				--P.POLH_POLNUM AS QUOTATION_NO, \r\n" + 
				"				P.POLH_COB AS PRODUCT_CODE, \r\n" + 
				"				CASE WHEN P.POLH_COB='HC00' THEN  \r\n" + 
				"				       SUBSTR(P.POLH_POLNUM,1,1)||'/'||--p \r\n" + 
				"				       SUBSTR(P.POLH_POLNUM,2,2)||'/'||--01 \r\n" + 
				"				       SUBSTR(P.POLH_POLNUM,4,4)||'/'||--product code \r\n" + 
				"				       SUBSTR(P.POLH_POLNUM,8,2)||'/'||--year \r\n" + 
				"				       SUBSTR(P.POLH_POLNUM,10,11)--tail sequence \r\n" + 
				"				ELSE \r\n" + 
				"				    P.POLH_DISPLAY_POLNUM  \r\n" + 
				"				END AS POLICY_NUMBER, \r\n" + 
				"				P.POLH_POLNUM AS POLICY_NUMBER2, \r\n" + 
				"				UPPER(P.POLH_ASSRDNM) AS INSURED_NAME, \r\n" + 
				"				G.IH_JRNL_TYPE||P.POLH_PREM_DOCNUM AS INVOICE, \r\n" + 
				"				P.POLH_SI_FC AS SUM_INSURED, \r\n" + 
				"				--POLH_PREM_FC AS GROSS_PREMIUM, \r\n" + 
				"				ROUND(P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC,2) AS GROSS_PREMIUM,\r\n" + 
				"                P.POLH_ADJ_PREM_BC AS ADJUST_PREMIUM,\r\n" + 
				"                P.POLH_FEES3_BC AS ADMIN_FEE,\r\n" + 
				"                (P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC + P.POLH_FEES3_BC) AS TOTAL,\r\n" + 
				"				TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE, \r\n" + 
				"				TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY')EFFECTIVE_DATE, \r\n" + 
				"				TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') EXPIRY_DATE, \r\n" + 
				"				P.POLH_DEBIT_PARTY AS INTERMEDIARY, \r\n" + 
				"				P.POLH_EMPLOYEE AS ISSUE_BY, \r\n" + 
				"				'' AS REMARK, \r\n" + 
				"				--(SELECT distinct(PINTMV_MOD_DESC) FROM INT_UW_PINT_MOTOR M WHERE M.PINTMV_PINT_SYS_ID=P.POLH_SYS_ID) AS DESCRIPTION \r\n" + 
				"				M.PINTMV_MAKE_MODEL AS PINTMV_MAKE_MODEL, \r\n" + 
				"				M.PINTMV_MOD_DESC AS DESCRIPTION, \r\n" + 
				"				P.POLH_UWYR \r\n" + 
				"				FROM RAIMS.INT_UW_POLH P  \r\n" + 
				"				LEFT JOIN INT_UW_PINT_MOTOR M ON P.POLH_SYS_ID=M.PINTMV_PINT_SYS_ID \r\n" + 
				"				INNER JOIN GLT_INTERFACE_HEAD G ON P.POLH_PREM_DOCNUM=G.IH_DOC_NO AND P.POLH_SYS_ID=G.IH_POLH_SYS_ID \r\n" + 
				"				WHERE  \r\n" + 
				"				--P.POLH_EXTNUM=M.PINTMV_EXTNUM AND \r\n" + 
				"				--P.POLH_STATUS IS NOT NULL AND \r\n" + 
				"				--P.POLH_ISSDT BETWEEN '01-APR-2020' AND '30-sep-2020' \r\n" + 
				"				P.POLH_ISSDT BETWEEN TO_DATE('"+fromDate+"','DD-MON-YYYY') AND TO_DATE('"+toDate+"','DD-MON-YYYY')";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- policy report ---------\n"+sql);
		List<rpt_0003_policy_model>ls=new ArrayList<rpt_0003_policy_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0003_policy_model policy=new rpt_0003_policy_model();
			policy.setNO(row.getString("NO"));
			policy.setPRODUCT_CODE(row.getString("PRODUCT_CODE"));
			policy.setPOLICY_NUMBER(row.getString("POLICY_NUMBER"));
			policy.setINSURED_NAME(row.getString("INSURED_NAME"));
			policy.setINVOICE(row.getString("INVOICE"));
			policy.setSUM_INSURED(row.getDouble("SUM_INSURED"));
			policy.setCROSS_PREMIUM(row.getDouble("GROSS_PREMIUM"));
			policy.setISSUE_DATE(row.getString("ISSUE_DATE"));
			policy.setEFFECTIVE_DATE(row.getString("EFFECTIVE_DATE"));
			policy.setEXPIRY_DATE(row.getString("EXPIRY_DATE"));
			policy.setINTERMEDIARY(row.getString("INTERMEDIARY"));
			policy.setISSUE_BY(row.getString("ISSUE_BY"));
			policy.setREMARK(row.getString("REMARK"));

			ls.add(policy);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	
	public List<rpt_0003_policy_model> getPolicyTobeRenew_rpt_0007(String toDate){
		String sql="SELECT  \r\n" + 
				"P.POLH_POST_PERIOD AS POLH_POST_PERIOD,\r\n" + 
				"ROW_NUMBER() OVER (order by P.POLH_ISSDT) AS No,\r\n" + 
				"P.POLH_COB AS PRODUCT_CODE,\r\n" + 
				"CASE WHEN P.POLH_COB='HC00' THEN \r\n" + 
				"       SUBSTR(P.POLH_POLNUM,1,1)||'/'||--p\r\n" + 
				"       SUBSTR(P.POLH_POLNUM,2,2)||'/'||--01\r\n" + 
				"       SUBSTR(P.POLH_POLNUM,4,4)||'/'||--product code\r\n" + 
				"       SUBSTR(P.POLH_POLNUM,8,2)||'/'||--year\r\n" + 
				"       SUBSTR(P.POLH_POLNUM,10,11)--tail sequence\r\n" + 
				"ELSE\r\n" + 
				"    P.POLH_DISPLAY_POLNUM \r\n" + 
				"END AS POLICY_NUMBER,\r\n" + 
				"P.POLH_POLNUM AS POLICY_NUMBER2,\r\n" + 
				"UPPER(P.POLH_ASSRDNM) AS INSURED_NAME,\r\n" + 
				"G.IH_JRNL_TYPE||P.POLH_PREM_DOCNUM AS INVOICE,\r\n" + 
				"P.POLH_SI_FC AS SUM_INSURED,\r\n" + 
				"POLH_PREM_FC AS GROSS_PREMIUM,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY')EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') EXPIRY_DATE,\r\n" + 
				"P.POLH_DEBIT_PARTY AS INTERMEDIARY,\r\n" + 
				"P.POLH_EMPLOYEE AS ISSUE_BY,\r\n" + 
				"'' AS REMARK,\r\n" + 
				"M.PINTMV_MAKE_MODEL AS PINTMV_MAKE_MODEL,\r\n" + 
				"M.PINTMV_MOD_DESC AS DESCRIPTION,\r\n" + 
				"P.POLH_UWYR\r\n" + 
				"FROM RAIMS.INT_UW_POLH P \r\n" + 
				"LEFT JOIN INT_UW_PINT_MOTOR M ON P.POLH_SYS_ID=M.PINTMV_PINT_SYS_ID\r\n" + 
				"INNER JOIN GLT_INTERFACE_HEAD G ON P.POLH_PREM_DOCNUM=G.IH_DOC_NO AND P.POLH_SYS_ID=G.IH_POLH_SYS_ID\r\n" + 
				"WHERE\r\n" + 
				"P.POLH_ENDPERIOD <= '"+toDate+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- policy report ---------\n"+sql);
		List<rpt_0003_policy_model>ls=new ArrayList<rpt_0003_policy_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0003_policy_model policy=new rpt_0003_policy_model();
			policy.setNO(row.getString("NO"));
			policy.setPRODUCT_CODE(row.getString("PRODUCT_CODE"));
			policy.setPOLICY_NUMBER(row.getString("POLICY_NUMBER"));
			policy.setINSURED_NAME(row.getString("INSURED_NAME"));
			policy.setINVOICE(row.getString("INVOICE"));
			policy.setSUM_INSURED(row.getDouble("SUM_INSURED"));
			policy.setCROSS_PREMIUM(row.getDouble("GROSS_PREMIUM"));
			policy.setISSUE_DATE(row.getString("ISSUE_DATE"));
			policy.setEFFECTIVE_DATE(row.getString("EFFECTIVE_DATE"));
			policy.setEXPIRY_DATE(row.getString("EXPIRY_DATE"));
			policy.setINTERMEDIARY(row.getString("INTERMEDIARY"));
			policy.setISSUE_BY(row.getString("ISSUE_BY"));
			policy.setREMARK(row.getString("REMARK"));

			ls.add(policy);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	
	public List<rpt_0003_policy_model> getPolicyAndEndorsmentList(String fromDate, String toDate){
		String sql="SELECT\r\n" + 
				"rownum as NO\r\n" + 
				",IH.IH_POLH_SYS_ID\r\n" + 
				",IH.IH_PARTY_REF_NO \r\n" + 
				",P.POLH_POLNUM\r\n" + 
				",IH.IH_POLH_EXTNUM\r\n" + 
				",P.POLH_COB AS PRODUCT_CODE\r\n" + 
				",FN_GET_POLICY_FORMAT(P.POLH_POLNUM,P.POLH_COB,IH.IH_POLH_EXTNUM) AS POLICY_NUMBER  \r\n" + 
				",UPPER(P.POLH_ASSRDNM) AS INSURED_NAME\r\n" + 
				",IH.IH_JRNL_TYPE||IH.IH_DOC_NO AS INVOICE_NO\r\n" + 
				",TO_CHAR(IH.IH_DOC_DT,'DD-MON-YYYY') AS INVOICE_DATE\r\n" + 
				",P.POLH_SI_FC AS SUM_INSURED\r\n" + 
				",FN_GET_PREMIUM(P.POLH_POLNUM,P.POLH_COB,IH.IH_POLH_EXTNUM) AS PREMIUM\r\n" + 
				"--,POLH_ADJ_PREM_BC AS ADJUST_PREMIUM\r\n" + 
				",FN_GET_ADJUSTMENT_AMOUNT(P.POLH_POLNUM,P.POLH_COB,IH.IH_POLH_EXTNUM) AS ADJUST_PREMIUM\r\n" + 
				"--,P.POLH_FEES3_BC AS ADMIN_FEE\r\n" + 
				",FN_GET_ADMIN_FEE(P.POLH_POLNUM,P.POLH_COB,IH.IH_POLH_EXTNUM) AS ADMIN_FEE\r\n" + 
				"--,ROUND(P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC,2) AS GROSS_PREMIUM\r\n" + 
				"--,CASE WHEN IH.IH_JRNL_TYPE='DNU' THEN ROUND(P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC+P.POLH_FEES3_BC,2) ELSE ROUND(P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC+P.POLH_FEES3_BC,2) *-1 END AS INVOICE_AMOUNT\r\n" + 
				",FN_GET_ENDORSEMENT_INVOICE_AMOUNT(P.POLH_POLNUM,P.POLH_COB,IH.IH_POLH_EXTNUM) AS INVOICE_AMOUNT\r\n" + 
				",CASE WHEN IH.IH_JRNL_TYPE='CNU' THEN -1 * IH.IH_NET_INV_BC_AMT ELSE IH.IH_NET_INV_BC_AMT END AS CLIENT_ACCOUNTING_AMOUNT\r\n" + 
				",TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE\r\n" + 
				",TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY') AS EFFECTIVE_DATE\r\n" + 
				",TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') AS EXPIRY_DATE\r\n" + 
				",P.POLH_CLIENT AS POLH_CLIENT\r\n" + 
				",P.POLH_DEBIT_PARTY AS INTERMEDIARY\r\n" + 
				",(SELECT BR.CVBR_BRANCH_NAME FROM RACFIN.IBM_PARTY_BRANCH BR WHERE BR.CVBR_BRANCH_CODE=P.POLH_DEBIT_PARTY) AS INTERMEDIARY_NAME \r\n" +
				",P.POLH_POST_PERIOD\r\n" + 
				",P.POLH_UWYR\r\n" + 
				",P.POLH_EMPLOYEE AS ISSUE_BY\r\n" + 
				",(SELECT U.USER_SHORT_NAME FROM RAIMS.MMGR_USERS U WHERE U.USER_ID=(SELECT UPPER(X.POLH_EMPLOYEE) FROM RAIMS.INT_UW_LPOLH X WHERE X.POLH_POLNUM=P.POLH_POLNUM AND X.POLH_EXTNUM=IH.IH_POLH_EXTNUM)) AS USER_NAME \r\n" + 
				",'' AS REMARK  \r\n" + 
				",'' AS PINTMV_MAKE_MODEL  \r\n" + 
				",'' AS DESCRIPTION \r\n" + 
				"--,IH.* \r\n" + 
				"FROM RAIMS.GLT_INTERFACE_HEAD IH \r\n" + 
				"INNER JOIN RAIMS.INT_UW_POLH P ON P.POLH_SYS_ID=IH.IH_POLH_SYS_ID AND P.POLH_EXTNUM=0\r\n" + 
				"WHERE IH.IH_JRNL_TYPE IN('DNU','CNU') AND IH.IH_STATUS='P' AND IH.IH_VEL_CODE IN (163) \r\n" + 
				"--AND IH.IH_DOC_DT BETWEEN '01-OCT-2020' AND '11-NOV-2020'\r\n" + 
				"AND IH.IH_DOC_DT BETWEEN '"+fromDate+"' AND '"+toDate+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
//		System.out.println("-------- policy report ---------\n"+sql);
		List<rpt_0003_policy_model>ls=new ArrayList<rpt_0003_policy_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0003_policy_model policy=new rpt_0003_policy_model();
			policy.setNO(row.getString("NO"));
			policy.setPRODUCT_CODE(row.getString("PRODUCT_CODE"));
			policy.setPOLICY_NUMBER(row.getString("POLICY_NUMBER"));
			policy.setINSURED_NAME(row.getString("INSURED_NAME"));
			policy.setINVOICE(row.getString("INVOICE_NO"));
			policy.setSUM_INSURED(row.getDouble("SUM_INSURED"));
			policy.setPremium(row.getDouble("Premium"));
			policy.setAdjustment_amount(row.getDouble("ADJUST_PREMIUM"));
			policy.setAdmin_fee(row.getDouble("ADMIN_FEE"));
			policy.setInvoice_amount(row.getDouble("INVOICE_AMOUNT"));
			policy.setClient_accounting_amount(row.getDouble("CLIENT_ACCOUNTING_AMOUNT"));
			policy.setISSUE_DATE(row.getString("ISSUE_DATE"));
			policy.setEFFECTIVE_DATE(row.getString("EFFECTIVE_DATE"));
			policy.setEXPIRY_DATE(row.getString("EXPIRY_DATE"));
			policy.setINTERMEDIARY(row.getString("INTERMEDIARY"));
			policy.setINTERMEDIARY_NAME(row.getString("INTERMEDIARY_NAME"));
			policy.setISSUE_BY(row.getString("ISSUE_BY"));
			policy.setREMARK(row.getString("REMARK"));
			ls.add(policy);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	
	public int checkExistedPolicyInvoiceNumber(String policyNumber, String invoiceNumber) {
		int chk=0;
		String sql="SELECT COUNT(*) COUNT_REC FROM APIREPORT.LHI_INT_UW_POLH P WHERE P.POLICY_NUMBER='"+policyNumber+"' AND P.INVOICE_NO='"+invoiceNumber+"'";
//		System.out.println("-------- policy report ---------\n"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			chk=row.getInt("COUNT_REC");
		}
		return chk;
	}
	
	public int insertPolicy(rpt_0003_policy_model policy) {
		String SQL="INSERT INTO APIREPORT.LHI_INT_UW_POLH(POLICY_NUMBER,INSURED_NAME,INVOICE_NO,INVOICE_AMOUNT) "
				+ "VALUES('"+policy.getPOLICY_NUMBER()
				+ "','"+policy.getINSURED_NAME()
				+ "','"+policy.getINVOICE()
				+ "','"+policy.getInvoice_amount()
				+ "');";
		System.out.println("process insert into APIREPORT.LHI_INT_UW_POLH ........\n");
		System.out.println(SQL);
		return getJdbcTemplate().update(ClsHelper.Begin()+ SQL +ClsHelper.End());
	}
}

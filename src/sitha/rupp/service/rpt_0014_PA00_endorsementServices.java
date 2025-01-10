package sitha.rupp.service;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.rpt_0014_PA00_EndorsementModel;

public class rpt_0014_PA00_endorsementServices extends GenericDaSupport {

	public rpt_0014_PA00_EndorsementModel getMainData(String policyNumber, String extNum){
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String sql="SELECT \r\n" + 
				"P.POLH_POLNUM,P.POLH_EXTNUM,P.POLH_SYS_ID,\r\n" + 
				"P.POLH_COB,\r\n" + 
				"(SELECT B.CVBR_BRANCH_NAME FROM RAIMS.IBM_PARTY_BRANCH B WHERE CVBR_BRANCH_CODE IN(P.POLH_DEBIT_PARTY)) AS INT_NAME,\r\n" + 
				"P.POLH_DEBIT_PARTY AS INT_CODE,\r\n" + 
				"P.POLH_CLIENT AS INSURED_CODE,\r\n" + 
				"P.POLH_DEBIT_PARTY,\r\n" + 
				"SUBSTR(P.POLH_POST_PERIOD,1,3) ||'-20'|| SUBSTR(P.POLH_POST_PERIOD,4,2)AS POLH_POST_PERIOD,\r\n" + 
				"P.POLH_UWYR,\r\n" + 
				"regexp_replace(replace(replace(P.POLH_ASSRD_ADDR,chr(10),' '),chr(13),' '), '[[:space:]]+', chr(32)) AS ADDRESS,\r\n" + 
				"'N/A' AS BUSINESS_REG_NO,\r\n" + 
				"P.POLH_INDUSTRY AS BUSINESS_OCCUPATION_CODE,\r\n" + 
				"(SELECT IND_DESC FROM RAIMS.INM_MST_IND WHERE IND_CODE=P.POLH_INDUSTRY) AS BUSINESS_OCCUPATION,\r\n" + 
				"--UPPER(P.POLH_ASSRDNM) AS INSURED_NAME,\r\n" +
				"(SELECT B.CVBR_BRANCH_NAME FROM RAIMS.IBM_PARTY_BRANCH B WHERE CVBR_BRANCH_CODE IN(P.POLH_CLIENT)) AS INSURED_NAME, \r\n" +
				"P.POLH_COB AS PRODUCT_CODE,\r\n" + 
				"(SELECT C.CLS_DESC FROM RAIMS.INM_MST_CLASS C WHERE CLS_CODE=P.POLH_COB) AS PRODUCT_DESC,\r\n" + 
				"P.POLH_SI_BC AS SUM_INSURED,\r\n" + 
				"POLH_PREM_BC AS GROSS_PREMIUM,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY')EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') EXPIRY_DATE\r\n" + 
				"--,P.* \r\n" + 
				"FROM RAIMS.INT_UW_POLH P \r\n" + 
				"WHERE P.POLH_POLNUM='"+policyNumber+"' AND P.POLH_EXTNUM='0'";
		rpt_0014_PA00_EndorsementModel endorsement=new rpt_0014_PA00_EndorsementModel();
		System.out.println("Main data PA00 "+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			endorsement=new rpt_0014_PA00_EndorsementModel();
			endorsement.setPOLH_POLNUM(row.getString("POLH_POLNUM"));
			endorsement.setPOLH_EXTNUM(row.getString("POLH_EXTNUM"));
			endorsement.setPOLH_SYS_ID(row.getString("POLH_SYS_ID"));
			endorsement.setPOLH_COB(row.getString("POLH_COB"));
			endorsement.setINT_NAME(row.getString("INT_NAME"));
			endorsement.setINT_CODE(row.getString("INT_CODE"));
			endorsement.setINSURED_CODE(row.getString("INSURED_CODE"));
			endorsement.setPOLH_DEBIT_PARTY(row.getString("POLH_DEBIT_PARTY"));
			endorsement.setPOLH_POST_PERIOD(row.getString("POLH_POST_PERIOD"));
			endorsement.setPOLH_UWYR(row.getString("POLH_UWYR"));
			endorsement.setADDRESS(row.getString("ADDRESS"));
			endorsement.setBUSINESS_REG_NO(row.getString("BUSINESS_REG_NO"));
			endorsement.setBUSINESS_OCCUPATION_CODE(row.getString("BUSINESS_OCCUPATION_CODE"));
			endorsement.setBUSINESS_OCCUPATION(row.getString("BUSINESS_OCCUPATION"));
			endorsement.setINSURED_NAME(row.getString("INSURED_NAME"));
			endorsement.setPRODUCT_CODE(row.getString("PRODUCT_CODE"));
			endorsement.setPRODUCT_DESC(row.getString("PRODUCT_DESC"));
			endorsement.setSUM_INSURED(row.getString("SUM_INSURED"));
			endorsement.setGROSS_PREMIUM(row.getString("GROSS_PREMIUM"));
			endorsement.setISSUE_DATE(row.getString("ISSUE_DATE"));
			endorsement.setEFFECTIVE_DATE(row.getString("EFFECTIVE_DATE"));
			endorsement.setEXPIRY_DATE(row.getString("EXPIRY_DATE"));
			
		}
		//System.out.println(ls.toString());
		System.out.println("Main data endorsemnt noted : "+endorsement);
		
		sql="SELECT ENDOR.PENDR_POLH_SYS_ID,ENDOR.PENDR_ENDRNUM,ENDOR.PENDR_ENDR_TYPE\r\n" + 
				",FN_GET_POLICY_FORMAT('"+policyNumber+"','PA00','"+extNum+"') AS POLICY_DISPLAY\r\n" + 
				",TO_CHAR(ENDOR.PENDR_DATE,'DD-MON-YYYY') AS PENDR_DATE,TO_CHAR(ENDOR.PENDR_EFFDT,'DD-MON-YYYY') AS PENDR_EFFDT,ENDOR.PENDR_AMT_BC\r\n" + 
				",NVL(ENDOR.PENDR_AMT_BC,0) AS PENDR_AMT_BC\r\n" + 
				",NVL(ENDOR.PENDR_ADJ_PREM_FC,0) AS PENDR_ADJ_PREM_FC\r\n" + 
				",NVL(ENDOR.PENDR_ADJ_PREM_BC,0) AS PENDR_ADJ_PREM_BC\r\n" + 
				",NVL(ENDOR.PENDR_POLH_FEES3_FC,0) AS PENDR_POLH_FEES3_FC\r\n" + 
				",NVL(ENDOR.PENDR_POLH_FEES3_BC,0) AS PENDR_ADMIN_FEE_BC\r\n" +
				",(NVL(ENDOR.PENDR_AMT_BC,0) + NVL(ENDOR.PENDR_ADJ_PREM_BC,0)) AS PENDR_PREMIUM_AMOUNT \r\n"+
				",(NVL(ENDOR.PENDR_AMT_BC,0) + NVL(ENDOR.PENDR_ADJ_PREM_BC,0) + NVL(ENDOR.PENDR_POLH_FEES3_BC,0)) AS PENDR_INVOICE_AMOUNT \r\n" +
				",ENDOR.PENDR_CRE_USER_INIT\r\n" + 
				",SUBSTR(ENDOR.PENDR_POST_PERIOD,1,3)||'-20'||SUBSTR(ENDOR.PENDR_POST_PERIOD,4,2) AS PENDR_POST_PERIOD\r\n" + 
				",(SELECT U.USER_NAME FROM RMENU.MMGR_USERS U WHERE U.USER_INIT=ENDOR.PENDR_CRE_USER_INIT) AS ISSUE_BY \r\n"+
				"--,ENDOR.* \r\n" + 
				"FROM RAIMS.INT_UW_PENDR ENDOR WHERE ENDOR.PENDR_POLNUM='"+policyNumber+"' AND ENDOR.PENDR_EXTNUM='"+extNum+"'";
		System.out.println("Main data endorsement "+sql);
		row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			endorsement.setPENDR_POLH_SYS_ID(row.getString("PENDR_POLH_SYS_ID"));
			endorsement.setPENDR_ENDRNUM(row.getString("PENDR_ENDRNUM"));
			endorsement.setPOLICY_DISPLAY(row.getString("POLICY_DISPLAY"));
			endorsement.setPENDR_DATE(row.getString("PENDR_DATE"));
//			endorsement.setPENDR_EFFDT(row.getString("PENDR_EFFDT"));
			endorsement.setPENDR_AMT_BC(row.getString("PENDR_AMT_BC"));
			endorsement.setPENDR_ADJ_PREM_BC(row.getString("PENDR_ADJ_PREM_BC"));
			endorsement.setPENDR_ADMIN_FEE_BC(row.getString("PENDR_ADMIN_FEE_BC"));
			endorsement.setPENDR_PREMIUM_AMOUNT(row.getString("PENDR_PREMIUM_AMOUNT"));
			endorsement.setPENDR_INVOICE_AMOUNT(row.getString("PENDR_INVOICE_AMOUNT"));
			endorsement.setISSUE_BY(row.getString("ISSUE_BY"));
			endorsement.setPOLH_POST_PERIOD(row.getString("PENDR_POST_PERIOD"));
			endorsement.setPendr_type(row.getString("PENDR_ENDR_TYPE"));
		}
		System.out.println("endorsement data :"+endorsement.toString());
		
		return endorsement;
	}
		
	public String getInvoiceNumberByPolNumber(String policyNumber,String extNum,String POLH_SYS_ID) {
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String sql="SELECT FN_GET_DOCID_INVOICE_ID('"+policyNumber+"','"+extNum+"','"+POLH_SYS_ID+"') AS INVOICE FROM DUAL";
		
		System.out.println("invoice:"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		
		String invoiceNumber="";
		while(row.next()){
			invoiceNumber=row.getString("INVOICE");
		}
		//System.out.println(ls.toString());
		System.out.println("Invoice Number : "+invoiceNumber);
		return invoiceNumber;
	}
	
	public String getInvoiceDateByPolNumber(String policyNumber,String extNum,String POLH_SYS_ID) {
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String sql="SELECT FN_GET_DOCID_INVOICE_DATE('"+policyNumber+"','"+extNum+"','"+POLH_SYS_ID+"') AS INVOICE_DATE FROM DUAL";
		
		System.out.println("invoice date:"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		
		String invoiceDate="";
		while(row.next()){
			invoiceDate=row.getString("INVOICE_DATE");
		}
		//System.out.println(ls.toString());
		System.out.println("Invoice Number : "+invoiceDate);
		return invoiceDate;
	}
	public String getEndorsementCount(String policyNumber, String extNum){
		String polh_sys_id=getSysIdByPolNum(policyNumber);
		String sql="SELECT I.PINT_REC_STATUS,COUNT(PINT_REC_STATUS) AS PINT_REC_STATUS_COUNT\r\n" + 
				"FROM RAIMS.INT_UW_PINT_MED I WHERE I.PINT_POLH_SYS_ID='"+polh_sys_id+"' AND I.PINT_EXTNUM='"+extNum+"'\r\n" + 
				"AND I.PINT_REC_STATUS !='M' GROUP BY I.PINT_REC_STATUS";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		String status="";
		String count="0";
		String countAdd="0";
		String countDel="0";
		String textAdd="";
		String textDel="";
//		String textBoth="";
		String restext="";
		while(row.next()){
			status=row.getString("PINT_REC_STATUS");
			count=row.getString("PINT_REC_STATUS_COUNT");
			if(status.contentEquals("N")) {
				countAdd =count;
				textAdd ="ADDITION OF " + countAdd +" EMPLOYEES (detailed as per attached list)";
			}
			if(status.contentEquals("D")) {
				countDel = count;
				textDel ="DELETION OF " + countDel +" EMPLOYEES (detailed as per attached list)";
			}
		}
		if(!textAdd.isEmpty() && textDel.isEmpty()) {
			restext ="ADDITION OF " + countAdd +" EMPLOYEES (detailed as per attached list)";
		}else if(textAdd.isEmpty() && !textDel.isEmpty()) {
			restext ="DELETION OF " + countDel +" EMPLOYEES (detailed as per attached list)";
		}else if(!textAdd.isEmpty() && !textDel.isEmpty()) {
			restext="ADDITION OF " + countAdd +" EMPLOYEES AND DELETION OF " + countDel +" EMPLOYEES (detailed as per attached list)";
		}else {
			//both is empty
			restext="";
		}
		
		return restext;
	}
	public String getSysIdByPolNum(String policyNumber) {
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String sql="SELECT P.POLH_SYS_ID AS POLH_SYS_ID FROM RAIMS.INT_UW_POLH P WHERE P.POLH_POLNUM='"+policyNumber+"'";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- report ---------\n"+sql);
		
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
	
	public String getMinDateOfEndorsment(String POLH_SYS_ID,String extNum) {
		//GET MIN DATE OF EACH EMPLOYEE
		String sql="SELECT\r\n" + 
				"--I.PINT_POLH_SYS_ID\r\n" + 
				"--,I.PINT_SYS_ID\r\n" + 
				"--,TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE\r\n" + 
				"--,TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') AS EXPIRY_DATE\r\n" + 
				"--,TO_CHAR(P.POLH_EFFDT,'DD-MON-YYYY') AS EFFICTIVE_DATE\r\n" + 
				"TO_CHAR(MIN(I.PINT_EFFDT),'DD-MON-YYYY') AS PINT_EFFDT\r\n" + 
				"--,TO_CHAR(I.PINT_RESGDT,'DD-MON-YYYY') AS RESIGNATION_DATE\r\n" + 
				"--,G3.*\r\n" + 
				"--,I.* \r\n" + 
				"FROM RAIMS.INT_UW_POLH P\r\n" + 
				"INNER JOIN RAIMS.INT_UW_PINT_MED I ON I.PINT_POLH_SYS_ID = P.POLH_SYS_ID\r\n" + 
				"WHERE P.POLH_SYS_ID='"+POLH_SYS_ID+"' AND P.POLH_EXTNUM='0' \r\n" + 
				"AND I.PINT_POLH_SYS_ID='"+POLH_SYS_ID+"' AND I.PINT_EXTNUM='"+extNum+"' AND I.PINT_REC_STATUS NOT IN ('M','U')\r\n" + 
				"GROUP BY I.PINT_POLH_SYS_ID";
		String str="";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			str="";
			str=row.getString("PINT_EFFDT");
		}
		//System.out.println(ls.toString());
		System.out.println(str);
		
		return str;
	}
}

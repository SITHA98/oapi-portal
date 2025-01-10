package sitha.rupp.service;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0008_EndorsementModel;

public class RPT_0010_Endorsement extends GenericDaSupport {

	public rpt_0008_EndorsementModel getMainData(String policyNumber, String extNum){
		String sql="SELECT \r\n" + 
				"P.POLH_POLNUM,P.POLH_EXTNUM,P.POLH_SYS_ID\r\n" + 
				",FN_GET_POLICY_FORMAT_CLAIM(FN_GET_POLICY_FORMAT('"+policyNumber+"','HC00','"+extNum+"')) AS POLICY_DISPLAY \r\n" +
				",(SELECT B.CVBR_BRANCH_NAME FROM RAIMS.IBM_PARTY_BRANCH B WHERE CVBR_BRANCH_CODE IN(P.POLH_DEBIT_PARTY)) AS INT_NAME,\r\n" + 
				"P.POLH_DEBIT_PARTY AS INT_CODE,\r\n" + 
				"P.POLH_CLIENT AS INSURED_CODE,\r\n" + 
				"P.POLH_DEBIT_PARTY, SUBSTR(P.POLH_POST_PERIOD,1,3) ||'-20'|| SUBSTR(P.POLH_POST_PERIOD,4,2)AS POLH_POST_PERIOD,\r\n" + 
				"replace(replace(P.POLH_ASSRD_ADDR,chr(10),' '),chr(13),' ') AS ADDRESS,\r\n" + 
				"'N/A' AS BUSINESS_REG_NO,\r\n" + 
				"P.POLH_INDUSTRY AS BUSINESS_OCCUPATION_CODE,\r\n" + 
				"(SELECT IND_DESC FROM RAIMS.INM_MST_IND WHERE IND_CODE=P.POLH_INDUSTRY) AS BUSINESS_OCCUPATION,\r\n" + 
				"UPPER(P.POLH_ASSRDNM) AS INSURED_NAME,\r\n" + 
				"P.POLH_COB AS PRODUCT_CODE,\r\n" + 
				"(SELECT C.CLS_DESC FROM RAIMS.INM_MST_CLASS C WHERE CLS_CODE=P.POLH_COB) AS PRODUCT_DESC,\r\n" + 
				"P.POLH_SI_BC AS SUM_INSURED,\r\n" + 
				"FN_GET_ADMIN_FEE(P.POLH_POLNUM,P.POLH_COB,'"+extNum+"') AS ADMIN_FEE,\r\n"+
				"--PENDR_ADJ_PREM_BC AS ADMIN_FEE,\r\n"+
				"POLH_PREM_BC AS GROSS_PREMIUM,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY')EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') EXPIRY_DATE\r\n" + 
				"--,P.* \r\n" + 
				"FROM RAIMS.INT_UW_POLH P " + 
				"WHERE P.POLH_POLNUM='"+policyNumber+"' AND P.POLH_EXTNUM='0'";
		rpt_0008_EndorsementModel endorsement=new rpt_0008_EndorsementModel();
		System.out.println("Main data 1 "+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			endorsement=new rpt_0008_EndorsementModel();
			endorsement.setPOLH_POLNUM(row.getString("POLICY_DISPLAY"));
			//endorsement.setPOLH_EXTNUM(row.getString("POLH_EXTNUM"));
			endorsement.setPOLH_SYS_ID(row.getString("POLH_SYS_ID"));
			endorsement.setINT_NAME(row.getString("INT_NAME"));
			endorsement.setPOLH_POST_PERIOD(row.getString("POLH_POST_PERIOD"));
			endorsement.setINT_CODE(row.getString("INT_CODE"));
			endorsement.setINSURED_CODE(row.getString("INSURED_CODE"));
			endorsement.setPOLH_DEBIT_PARTY(row.getString("POLH_DEBIT_PARTY"));
			endorsement.setADDRESS(row.getString("ADDRESS"));
			endorsement.setBUSINESS_OCCUPATION_CODE(row.getString("BUSINESS_OCCUPATION_CODE"));
			endorsement.setINSURED_NAME(row.getString("INSURED_NAME"));
			endorsement.setPRODUCT_CODE(row.getString("PRODUCT_CODE"));
			endorsement.setPRODUCT_DESC(row.getString("PRODUCT_DESC"));
			endorsement.setSUM_INSURED(row.getString("SUM_INSURED"));
			endorsement.setGROSS_PREMIUM(row.getString("GROSS_PREMIUM"));
			endorsement.setISSUE_DATE(row.getString("ISSUE_DATE"));
			endorsement.setEFFECTIVE_DATE(row.getString("EFFECTIVE_DATE"));
			endorsement.setEXPIRY_DATE(row.getString("EXPIRY_DATE"));
			endorsement.setAdmin_fee(row.getString("Admin_fee"));
		}
		//System.out.println(ls.toString());
		//System.out.println(endorsement);
		
		sql="SELECT ENDOR.PENDR_POLH_SYS_ID,FN_GET_POLICY_FORMAT_CLAIM(ENDOR.PENDR_ENDRNUM) AS PENDR_ENDRNUM \r\n" +
				"--,FN_GET_POLICY_FORMAT_CLAIM('"+policyNumber+"',ENDOR.PENDR_COB,'"+extNum+"') AS POLICY_NUMBER \r\n" +
				",FN_GET_POLICY_FORMAT_CLAIM(ENDOR.PENDR_ENDRNUM) AS POLICY_NUMBER \r\n"+
				",TO_CHAR(ENDOR.PENDR_DATE,'DD-MON-YYYY') AS PENDR_DATE,TO_CHAR(ENDOR.PENDR_EFFDT,'DD-MON-YYYY') AS PENDR_EFFDT \r\n" +
				",ENDOR.PENDR_AMT_BC AS SYS_PENDR_AMT_BC\r\n" + 
				",ENDOR.PENDR_ADJ_PREM_BC\r\n" + 
				",(NVL(ENDOR.PENDR_AMT_BC,0) + NVL(ENDOR.PENDR_ADJ_PREM_BC,0)) AS PENDR_AMT_BC \r\n" +
				",ENDOR.PENDR_CRE_USER_INIT\r\n" + 
				",(SELECT U.USER_NAME FROM RMENU.MMGR_USERS U WHERE U.USER_INIT=ENDOR.PENDR_CRE_USER_INIT) AS ISSUE_BY \r\n" +
				"FROM RAIMS.INT_UW_PENDR ENDOR WHERE ENDOR.PENDR_POLNUM='"+policyNumber+"' AND ENDOR.PENDR_EXTNUM='"+extNum+"'";
		System.out.println("Main data endorsement "+sql);
		row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			//endorsement.setPENDR_POLICY_NUMBER(row.getString("POLICY_NUMBER"));
			endorsement.setPENDR_POLICY_NUMBER(row.getString("POLICY_NUMBER"));
			endorsement.setPENDR_DATE(row.getString("PENDR_DATE"));
			endorsement.setPENDR_EFFDT(row.getString("PENDR_EFFDT"));
			endorsement.setPENDR_AMT_BC(row.getString("PENDR_AMT_BC"));
			endorsement.setIssue_by(row.getString("Issue_by"));
		}
		System.out.println("endorsement:"+endorsement.toString());
		return endorsement;
	}
	public String getEndorsementCount(String policyNumber, String extNum){
		String polh_sys_id=getSysIdByPolNum(policyNumber);
		String sql="SELECT I.PINT_REC_STATUS,COUNT(PINT_REC_STATUS) AS PINT_REC_STATUS_COUNT\r\n" + 
				"FROM RAIMS.INT_UW_PINT_MED I WHERE I.PINT_POLH_SYS_ID='"+polh_sys_id+"' AND I.PINT_EXTNUM='"+extNum+"'\r\n" + 
				"AND I.PINT_REC_STATUS !='M' AND I.PINT_PREM_BC!=0 GROUP BY I.PINT_REC_STATUS";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		String status="";
		String count="0";
		String countAdd="0";
		String countDel="0";
		String text="";
		while(row.next()){
			status=row.getString("PINT_REC_STATUS");
			count=row.getString("PINT_REC_STATUS_COUNT");
			if(status.contentEquals("N")) {
				countAdd =count;
			}
			if(status.contentEquals("D")) {
				countDel = count;
			}
			text ="ADDITION OF " + countAdd +" EMPLOYEES AND DELETION OF " + countDel +" EMPLOYEES (detailed as per attached list)";
		}
		return text;
	}
	
	public String getSysIdByPolNum(String policyNumber) {
		String sql="SELECT DISTINCT(P.POLH_SYS_ID) AS POLH_SYS_ID FROM RAIMS.INT_UW_LPOLH P WHERE P.POLH_POLNUM='"+policyNumber+"'";
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
	
	public String getInvoiceNumberByPolNumber(String policyNumber,String product_code) {
		String sql="";
		sql +="SELECT\r\n" + 
				"--rownum as NO,\r\n" + 
				"a.PENDR_POLH_SYS_ID,\r\n" + 
				"NULL PINTMV_PINT_SYS_ID,\r\n" + 
				"a.PENDR_COB PRODUCT_CODE,\r\n" + 
				"a.PENDR_UWYR UWYR,\r\n" + 
				"--PENDR_POLNUM, \r\n" + 
				"a.PENDR_ENDRNUM EN_POL_NUM,\r\n" + 
				"TO_CHAR(a.PENDR_DATE,'DD-MON-YYYY') ENDR_DATE,\r\n" + 
				"TO_CHAR(a.PENDR_EFFDT,'DD-MON-YYYY') ENDR_EFFDT,\r\n" + 
				"a.PENDR_AMT_BC AMOUNT,\r\n" + 
				"TO_CHAR(a.PENDR_APPROVAL_DT,'DD-MON-YYYY') APPROVAL_DATE,\r\n" + 
				"a.PENDR_APPROVAL_BY APPROVAL_BY,\r\n" + 
				"TO_CHAR(a.PENDR_CRE_DT,'DD-MON-YYYY') CREATION_DATE,\r\n" + 
				"TO_CHAR(a.PENDR_UPD_DT,'DD-MON-YYYY') UPDATE_DATE,\r\n" + 
				"a.PENDR_POST_PERIOD POST_PERIOD,\r\n" + 
				"NULL MAKE_MODEL,\r\n" + 
				"NULL ENGINE_NO,\r\n" + 
				"NULL CHASIS_NO,\r\n" + 
				"NULL REGN_NO,\r\n" + 
				"c.POLH_CLIENT CLIENT_CODE,\r\n" + 
				"C.POLH_DEBIT_PARTY ACC_CODE,\r\n" + 
				"--c.POLH_BENFNM,\r\n" + 
				"(c.POLH_PREM_DOCNUM) INVOICE,\r\n" + 
				"(c.POLH_BENFNM) INSURED_NAME,\r\n" + 
				"(c.POLH_POLNUM) POLICY_NUM,\r\n" + 
				"(c.POLH_SI_FC) SUM_INSURED,\r\n" + 
				"(c.POLH_PREM_FC)PREMIUM,\r\n" + 
				"TO_CHAR(c.POLH_ISSDT,'DD-MON-YYYY') POL_ISSUED_DATE,\r\n" + 
				"TO_CHAR(c.POLH_STPERIOD,'DD-MON-YYYY')POL_EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(c.POLH_ENDPERIOD,'DD-MON-YYYY') POL_EXPIRY_DATE,\r\n" + 
				"(SELECT IH_DOC_NO FROM RAIMS.GLT_INTERFACE_HEAD D WHERE D.IH_POLH_SYS_ID = C.POLH_SYS_ID AND D.IH_POLH_EXTNUM = C.POLH_EXTNUM AND D.IH_JRNL_TYPE IN('CNU','DNU')) INVOICE_NO\r\n" + 
				"--(SELECT IH_JRNL_TYPE FROM GLT_INTERFACE_HEAD WHERE IH_DOC_NO='0120000126') AS CNU_DNU\r\n" + 
				"FROM RAIMS.INT_UW_PENDR a,RAIMS.INT_UW_LPOLH c\r\n" + 
				"WHERE \r\n" + 
				"--a.PENDR_POST_PERIOD  ='MAY20'\r\n" + 
				"A.PENDR_POLH_SYS_ID = C.POLH_SYS_ID\r\n" + 
				"AND A.PENDR_EXTNUM = C.POLH_EXTNUM\r\n" + 
				"--AND A.PENDR_ENDRNUM='P01HC0020005300100'\r\n" +
				"--AND a.PENDR_UPD_DT BETWEEN '01-MAY-2020' AND '31-MAY-2020' \r\n" +
				" AND A.PENDR_ENDRNUM='" +policyNumber+"'";
		/*
		if (product_code.equalsIgnoreCase("HC00")||product_code.equalsIgnoreCase("FI00")||product_code.equalsIgnoreCase("PR00")||product_code.equalsIgnoreCase("CR00")||product_code.equalsIgnoreCase("HC01")) {
			sql+="AND A.PENDR_ENDRNUM='"+policyNumber.replace("/", "")+"'";
		}else {
			String s[]=policyNumber.split("/");
			policyNumber=s[0]+s[1]+s[2]+s[3]+s[4]+"/"+s[5]+"/"+s[6];
			sql+="AND A.PENDR_ENDRNUM='"+policyNumber+"'";
		}
		*/
		System.out.println("invoice:"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		double amt=0.0;
		String invoiceNumber="";
		while(row.next()){
			amt=0.0;
			amt=row.getDouble("AMOUNT");
			if(amt<0) {
				invoiceNumber="CNU"+row.getString("INVOICE_NO");
			}else {
				invoiceNumber="DNU"+row.getString("INVOICE_NO");
			}
		}
		//System.out.println(ls.toString());
		System.out.println(invoiceNumber);
		return invoiceNumber;
		
	}
	
	public String getEndorsmentText(String polh_sys_id,String extNum) {
		String sql="select\r\n" + 
				"dbms_lob.substr(PT.PTEXT_TEXT) AS ENDORSEMENT_TEXT \r\n" + 
				"--,PT.* \r\n" + 
				"from RAIMS.INT_UW_PTEXT PT \r\n" + 
				"WHERE PT.PTEXT_POLH_SYS_ID='"+polh_sys_id+"'\r\n" + 
				"AND PT.PTEXT_EXTNUM='"+extNum+"'";
		System.out.println("invoice:"+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		String endr_text="";
		while(row.next()){
			endr_text=row.getString("ENDORSEMENT_TEXT");
		}
		return endr_text;
	}
}

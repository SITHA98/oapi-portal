package sitha.rupp.service;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.platform.collection.PData;

public class InvoiceServices extends GenericDaSupport {

	public PData getHealthCareCovid19(String policyNumber) {
		
		policyNumber=ClsHelper.PolicyNumber(policyNumber);
		String P_POLH_SYS_ID=getSysIdByPolNum(policyNumber);
		String resResult="";
		String sql="SELECT\r\n" + 
				"P.POLH_COB,\r\n" + 
				"'DNU'||P.POLH_PREM_DOCNUM AS INVOICE,\r\n" + 
				"P.POLH_DEBIT_PARTY AS ACCOUNT_CODE,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') INVOICE_DATE,\r\n" + 
				"--TO_CHAR(P.POLH_POST_PERIOD,'DD-MON-YYYY') AS ACC_MONTH_YEAR,\r\n" + 
				"SUBSTR(POLH_POST_PERIOD,1,3) ||'-20'|| SUBSTR(POLH_POST_PERIOD,4,2) AS ACC_MONTH_YEAR,\r\n" + 
				"UPPER(P.POLH_BENFNM) AS CUSTOMER_NAME,\r\n" + 
				"regexp_replace(replace(replace(P.POLH_ASSRD_ADDR,chr(10),' '),chr(13),' '), '[[:space:]]+', chr(32)) AS ADDRESS,\r\n" + 
				"'' AS VAT_TIN,\r\n" + 
				"--'PRODUCT' AS PRODUCT,\r\n" + 
				"(SELECT CLS_DESC FROM RAIMS.INM_MST_CLASS WHERE CLS_CODE=P.POLH_COB) AS PRODUCT,\r\n" + 
				"FN_GET_POLICY_FORMAT_CLAIM(FN_GET_POLICY_FORMAT_V2(P.POLH_POLNUM,P.POLH_COB,P.POLH_EXTNUM)) AS POLICY_NO,\r\n" + 
				"--P.POLH_DISPLAY_POLNUM AS POLICY_NO,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUED_DATE,\r\n" + 
				"TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY') AS EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') AS EXPIRY_DATE,\r\n" + 
				"ROUND(P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC,2) AS GROSS_PREMIUM,\r\n" + 
				"P.POLH_ADJ_PREM_BC AS ADJUST_PREMIUM,\r\n" + 
				"P.POLH_FEES3_BC AS ADMIN_FEE\r\n" + 
				",CASE WHEN P.POLH_COB IN ('HC00','HC02','HC01','PA00','PA01') THEN\r\n" + 
				"    NVL(P.POLH_PREM_BC,0)+NVL(P.POLH_ADJ_PREM_BC,0)+NVL(P.POLH_FEES3_BC,0)\r\n" + 
				" ELSE\r\n" + 
				"	(SELECT TX.IH_NET_INV_BC_AMT FROM RAIMS.GLT_INTERFACE_HEAD TX WHERE TX.IH_POLH_SYS_ID=P.POLH_SYS_ID AND TX.IH_JRNL_SOURCE='UW' AND TX.IH_JRNL_TYPE IN ('DNU','CNU') AND TX.IH_PARTY_BRANCH NOT LIKE 'CN000%' AND TX.IH_POLH_EXTNUM='0')\r\n" + 
				"END AS TOTAL_DUE\r\n" + 
				"--,(SELECT TX.IH_NET_INV_BC_AMT FROM RAIMS.GLT_INTERFACE_HEAD TX WHERE TX.IH_POLH_SYS_ID=P.POLH_SYS_ID AND TX.IH_JRNL_SOURCE='UW' AND TX.IH_JRNL_TYPE IN ('DNU','CNU')) AS TOTAL_DUE\r\n" + 
				"FROM RAIMS.INT_UW_POLH P\r\n" + 
				"WHERE P.POLH_EXTNUM='0'\r\n" + 
				"--AND P.POLH_POLNUM=$P{parPolicyNumber}\r\n" + 
				"--AND P.POLH_POLNUM='P01HC00210115/000/00'\r\n" +
				"AND P.POLH_SYS_ID='"+P_POLH_SYS_ID+"'\r\n" +
				"UNION \r\n" + 
				"SELECT\r\n" + 
				"P.POLH_COB,\r\n" + 
				"'DNU'||P.POLH_PREM_DOCNUM AS INVOICE,\r\n" + 
				"P.POLH_DEBIT_PARTY AS ACCOUNT_CODE,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') INVOICE_DATE,\r\n" + 
				"--TO_CHAR(P.POLH_POST_PERIOD,'DD-MON-YYYY') AS ACC_MONTH_YEAR,\r\n" + 
				"SUBSTR(POLH_POST_PERIOD,1,3) ||'-20'|| SUBSTR(POLH_POST_PERIOD,4,2) AS ACC_MONTH_YEAR,\r\n" + 
				"UPPER(P.POLH_BENFNM) AS CUSTOMER_NAME,\r\n" + 
				"regexp_replace(replace(replace(P.POLH_ASSRD_ADDR,chr(10),' '),chr(13),' '), '[[:space:]]+', chr(32)) AS ADDRESS,\r\n" + 
				"'' AS VAT_TIN,\r\n" + 
				"--'PRODUCT' AS PRODUCT,\r\n" + 
				"(SELECT CLS_DESC FROM RAIMS.INM_MST_CLASS WHERE CLS_CODE=P.POLH_COB) AS PRODUCT,\r\n" + 
				"FN_GET_POLICY_FORMAT_CLAIM(FN_GET_POLICY_FORMAT_V2(P.POLH_POLNUM,P.POLH_COB,P.POLH_EXTNUM)) AS POLICY_NO,\r\n" + 
				"--P.POLH_DISPLAY_POLNUM AS POLICY_NO,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUED_DATE,\r\n" + 
				"TO_CHAR(P.POLH_STPERIOD,'DD-MON-YYYY') AS EFFECTIVE_DATE,\r\n" + 
				"TO_CHAR(P.POLH_ENDPERIOD,'DD-MON-YYYY') AS EXPIRY_DATE,\r\n" + 
				"ROUND(P.POLH_PREM_BC+P.POLH_ADJ_PREM_BC,2) AS GROSS_PREMIUM,\r\n" + 
				"P.POLH_ADJ_PREM_BC AS ADJUST_PREMIUM,\r\n" + 
				"P.POLH_FEES3_BC AS ADMIN_FEE\r\n" + 
				",CASE WHEN P.POLH_COB IN ('HC00','HC02','HC01','PA00','PA01') THEN\r\n" + 
				"    NVL(P.POLH_PREM_BC,0)+NVL(P.POLH_ADJ_PREM_BC,0)+NVL(P.POLH_FEES3_BC,0)\r\n" + 
				" ELSE\r\n" + 
				"	(SELECT TX.IH_NET_INV_BC_AMT FROM RAIMS.GLT_INTERFACE_HEAD TX WHERE TX.IH_POLH_SYS_ID=P.POLH_SYS_ID AND TX.IH_JRNL_SOURCE='UW' AND TX.IH_JRNL_TYPE IN ('DNU','CNU') AND TX.IH_PARTY_BRANCH NOT LIKE 'CN000%' AND TX.IH_POLH_EXTNUM='0')\r\n" + 
				"END AS TOTAL_DUE\r\n" +  
				"FROM RAIMS.INT_UW_POLH P\r\n" + 
				"WHERE P.POLH_EXTNUM='0'\r\n" + 
				"AND P.POLH_INT='"+policyNumber+"'";
		System.out.println("SQL:"+sql);
		PData data=new PData();
		double adminfee=0;
		double total_premium=0;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			if(row.getString("POLH_COB").equals("HC02")) {
				data.put("PRODUCT",row.getString("PRODUCT"));
				data.put("POLICY_NO",row.getString("POLICY_NO"));
				data.put("EFFECTIVE_DATE",row.getString("EFFECTIVE_DATE"));
				data.put("EXPIRY_DATE",row.getString("EXPIRY_DATE"));
				adminfee=adminfee+Double.parseDouble(row.getString("ADMIN_FEE"));
				total_premium=total_premium+Double.parseDouble(row.getString("TOTAL_DUE"));
			} else {
				adminfee=adminfee+Double.parseDouble(row.getString("ADMIN_FEE"));
				total_premium=total_premium+Double.parseDouble(row.getString("TOTAL_DUE"));
			}
			data.put("adminfee",ClsHelper.formatNumber(adminfee));
			data.put("total_premium",ClsHelper.formatNumber(total_premium));
			data.put("gross_premium",ClsHelper.formatNumber(total_premium-adminfee));
		}		
		System.out.println("resResult:"+data);
		return data;
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
}

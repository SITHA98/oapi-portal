package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0007_renewal_policy_model;

public class rpt_0007_renewal_policyService extends GenericDaSupport {

	public List<rpt_0007_renewal_policy_model> getPolicyList(String fromDate, String toDate){
		String sql="WITH ALIST AS (\r\n" + 
				"SELECT  \r\n" + 
				"\r\n" + 
				"P.POLH_COB AS PRODUCT_CODE,\r\n" + 
				"(SELECT P1.POLH_DEBIT_PARTY FROM RAIMS.INT_UW_POLH P1 WHERE P1.POLH_POLNUM=P.POLH_POLNUM AND P1.POLH_EXTNUM='0') AS ACCOUNT_NO\r\n" + 
				",FN_GET_INSURED_NAME_RENEWAL_POLICY (P.POLH_POLNUM) AS INSURED_NAME \r\n" + 
				",P.POLH_POLNUM\r\n" + 
				",FN_GET_POLICY_FORMAT(P.POLH_POLNUM,P.POLH_COB,P.POLH_EXTNUM) AS POLICY_NUMBER\r\n" + 
				",PEND.PENDR_ENDR_TYPE\r\n" + 
				",P.POLH_EXTNUM\r\n" + 
				",M.PINTMV_MOD_DESC AS MAKE_MODEL\r\n" + 
				",M.PINTMV_CHASIS_NO AS CHASSIS_NO\r\n" +
				",M.PINTMV_ENGINE_NO AS ENGINE_NO\r\n" +
				",M.PINTMV_REGN_NO AS REGISTRATION_NO\r\n" + 
				",M.PINTMV_SEQNUM\r\n" + 
				",(SELECT TO_CHAR(P1.POLH_STPERIOD,'DD-MON-YYYY') FROM RAIMS.INT_UW_POLH P1 WHERE P1.POLH_SYS_ID=P.POLH_SYS_ID AND P1.POLH_EXTNUM='0') AS EFFECTIVE_DATE\r\n" + 
				",(SELECT TO_CHAR(P1.POLH_ENDPERIOD,'DD-MON-YYYY') FROM RAIMS.INT_UW_POLH P1 WHERE P1.POLH_SYS_ID=P.POLH_SYS_ID AND P1.POLH_EXTNUM='0') AS EXPIRY_DATE\r\n" + 
				",FN_GET_SUM_INSURE(P.POLH_SYS_ID) AS SUM_INSURED\r\n" + 
				",FN_GET_GROSS_PREMIUM(P.POLH_SYS_ID) AS GROSS_PREMIUM\r\n" + 
				",P.POLH_UWYR\r\n" + 
				",'YEAR '|| (2021 - P.POLH_UWYR + 1) AS RENEWAL_YEAR\r\n" + 
				",FN_GET_CLAIM_OUTSTANDING(P.POLH_SYS_ID,P.POLH_COB) AS CLAIM_OUTSTANDING_AMOUNT\r\n" + 
				",FN_GET_CLAIM_PAID(P.POLH_SYS_ID,P.POLH_COB) AS CLAIM_PAID_AMOUNT\r\n" + 
				",'' LOSS_RATIO\r\n" + 
				"FROM RAIMS.INT_UW_POLH P \r\n" + 
				"LEFT JOIN RAIMS.INT_UW_PENDR PEND ON PEND.PENDR_POLH_SYS_ID=P.POLH_SYS_ID AND PEND.PENDR_EXTNUM=P.POLH_EXTNUM AND PEND.PENDR_APPROVAL_BY IS NOT NULL \r\n" + 
				"LEFT JOIN RAIMS.INT_UW_PINT_MOTOR M ON P.POLH_SYS_ID=M.PINTMV_PINT_SYS_ID AND M.PINTMV_SEQNUM IS NOT NULL\r\n" + 
				"WHERE P.POLH_EXTNUM = (SELECT MAX(P1.POLH_EXTNUM) FROM RAIMS.INT_UW_POLH P1 WHERE P1.POLH_SYS_ID=P.POLH_SYS_ID AND P1.POLH_AU='A')\r\n" + 
				"AND P.POLH_POLNUM LIKE 'P%'\r\n" + 
				"AND P.POLH_COB IN('CV','CV00','PV','PV00','MC','MC00')\r\n" + 
				"--AND P.POLH_ENDPERIOD <='30-dec-2020'\r\n" + 
				"AND P.POLH_ENDPERIOD BETWEEN TO_DATE('"+ fromDate +"','DD-MON-YYYY') AND TO_DATE('"+toDate+"','DD-MON-YYYY') + .99999 \r\n" + 
				") SELECT\r\n" + 
				"A.* FROM ALIST A WHERE A.PENDR_ENDR_TYPE<>'C' OR A.PENDR_ENDR_TYPE IS NULL AND A.PINTMV_SEQNUM IS NOT NULL \r\n" + 
				"GROUP BY PRODUCT_CODE,ACCOUNT_NO,INSURED_NAME,POLH_POLNUM,POLICY_NUMBER,PENDR_ENDR_TYPE,POLH_EXTNUM,MAKE_MODEL,CHASSIS_NO,ENGINE_NO,REGISTRATION_NO,PINTMV_SEQNUM,EFFECTIVE_DATE,EXPIRY_DATE,SUM_INSURED,GROSS_PREMIUM,POLH_UWYR,RENEWAL_YEAR,CLAIM_OUTSTANDING_AMOUNT,CLAIM_PAID_AMOUNT,LOSS_RATIO";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- policy report ---------\n"+sql);
		List<rpt_0007_renewal_policy_model>ls=new ArrayList<rpt_0007_renewal_policy_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0007_renewal_policy_model policy=new rpt_0007_renewal_policy_model();
			policy.setAccount_no(row.getString("Account_no"));
			policy.setInsured_name(row.getString("Insured_name"));
			policy.setPolh_polnum(row.getString("Polh_polnum"));
			policy.setPolicy_number(row.getString("Policy_number"));
			policy.setPendr_endr_type(row.getString("Pendr_endr_type"));
			policy.setPolh_extnum(row.getString("Polh_extnum"));
			policy.setMake_model(row.getString("Make_model"));
			policy.setChassis_no(row.getString("Chassis_no"));
			policy.setEngine_no(row.getString("Engine_no"));
			policy.setRegistration_no(row.getString("Registration_no"));
			policy.setPintmv_seqnum(row.getString("Pintmv_seqnum"));
			policy.setEffective_date(row.getString("Effective_date"));
			policy.setExpiry_date(row.getString("Expiry_date"));
			policy.setSum_insured(row.getDouble("Sum_insured"));
			policy.setGross_premium(row.getDouble("Gross_premium"));
			policy.setPolh_uwyr(row.getString("Polh_uwyr"));
			policy.setRenewal_year(row.getString("Renewal_year"));
			policy.setClaim_outstanding_amount(row.getDouble("Claim_outstanding_amount"));
			policy.setClaim_paid_amount(row.getDouble("Claim_paid_amount"));
			policy.setLoss_ratio(row.getString("Loss_ratio"));

			ls.add(policy);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	

	
}

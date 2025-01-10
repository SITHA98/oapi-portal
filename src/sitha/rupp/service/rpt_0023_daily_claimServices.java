package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0023_daily_claim_model;

public class rpt_0023_daily_claimServices extends GenericDaSupport {

	public List<rpt_0023_daily_claim_model> getDailyClaim(String fromDate, String toDate,String productCode){
//		fromDate="01-SEP-2020";
		String sql="SELECT\r\n" + 
				"rownum as No\r\n" + 
				",C1.CLMH_SYS_ID\r\n" + 
				",G2.PINT_SYS_ID\r\n" + 
				",P.PAPPRH_PAY_TO\r\n" + 
				",P.PAPPRH_NUMBER\r\n" + 
				",P.PAPPRH_INTMTNH_CLMNUM\r\n" + 
				",P.PAPPRH_INTMTNH_POLNUM\r\n" + 
				",P.PAPPRH_SRNO\r\n" + 
				",P.PAPPRH_SETTLE_YN\r\n" + 
				",CASE \r\n" + 
				"    WHEN P.PAPPRH_SETTLE_YN='N' THEN 'ON ACCOUNT'\r\n" + 
				"    WHEN P.PAPPRH_SETTLE_YN='Y' THEN 'FULL SETTLEMENT' \r\n" + 
				"END AS SETTLEMENT_TYPE\r\n" + 
				",C1.CLMH_CL_STATUS\r\n" + 
				"--,CASE WHEN C1.CLMH_CL_STATUS='Y' THEN 'INTIMATION CLOSED' ELSE 'OUTSTANDING' END AS CLAIM_STATUS_DESC\r\n" + 
				",FN_GET_CLAIM_STATUS(P.PAPPRH_SETTLE_YN,C1.CLMH_CL_STATUS) AS CLAIM_STATUS_DESC\r\n" + 
				",FN_GET_CLAIM_REASON(C1.CLMH_CLMNUM) AS REASON_OF_CLAIM_STATUS\r\n" + 
				",FN_GET_CLAIM_SETTLEMENT_DATE(C1.CLMH_CLMNUM) AS SETTLEMENT_DATE\r\n" + 
				",P.PAPPRH_APPROVAL_DT\r\n" + 
				",C1.CLMH_CLMNUM AS CLAIM_NUMBER\r\n" + 
				",C1.CLMH_COB\r\n" + 
				",C1.CLMH_EXTNUM\r\n" + 
				",C1.CLMH_POLNUM AS ORI_POLICY\r\n" + 
				",FN_GET_POLICY_FORMAT(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS POLICY_NUMBER\r\n" + 
				",C1.CLMH_UWYR \r\n" + 
				",SUBSTR(C1.CLMH_POST_PERIOD,1,3)||'-20'||SUBSTR(C1.CLMH_POST_PERIOD,4,2) AS AC_MONTH\r\n" + 
				",FN_GET_INSURED_NAME(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS INSURED_NAME\r\n" + 
				",FN_GET_POLICY_EFFECTIVE_DATE(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS INCEPTION_DATE\r\n" + 
				",FN_GET_POLICY_EXPIRE_DATE(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS EXPIRE_DATE\r\n" + 
				",(SELECT PR.CLS_DESC FROM RAIMS.INM_MST_CLASS PR WHERE PR.CLS_CODE=C1.CLMH_COB) AS PRODUCT\r\n" + 
				",'' AS RISK_DETAIL\r\n" + 
				",TO_CHAR(C1.CLMH_LOSS_DATE,'DD-MON-YYYY') AS LOST_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_REP_DATE,'DD-MON-YYYY') AS REPORTED_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_TXN_DATE,'DD-MON-YYYY') AS TRANSACTION_DATE\r\n" + 
				",FN_CALC_BETWEEN_DATE(C1.CLMH_REP_DATE,'"+toDate+"') AS OS_DAYS\r\n" + 
				",'' HOSPITAL_NAME\r\n" + 
				",C1.CLMH_LNKNUM AS EVENT\r\n" + 
				",C1.CLMH_PLACE_ACCIDENT\r\n" + 
				",C1.CLMH_NATURE AS CAUSE_OF_LOSS\r\n" + 
				",C1.CLMH_NOL_CODE AS CATEGORY_OF_LOSS\r\n" + 
				",C1.CLMH_REM AS NARRATION_REMARK\r\n" + 
				",C2.CLMF_FTR_NAME AS CLAIMANT\r\n" + 
				",C1.CLMH_TOT_EST_BC AS INITIMATED_AMOUNT\r\n" + 
				"--,C1.CLMH_TOT_EST_BC AS TOTAL_NET_RESERVE\r\n" + 
				",P.PAPPRH_TOT_APPR_BC AS PAYMENT_AMOUNT\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN C1.CLMH_TOT_EST_BC ELSE P.PAPPRH_TOT_APPR_BC END AS \"LOSS_100\"\r\n" + 
				",C1.CLMH_ADJ_FEE_BC AS CLAIM_EXPENSE\r\n" + 
				"--,'CAMBODIA RE' AS CAMBODIA_RE\r\n" + 
				"--,'Obligatory' AS OBLIGATORY\r\n" + 
				"--,'Own Retention' AS OWN_RETENTION\r\n" + 
				"--,'20%' AS RE_INSURER_OB\r\n" + 
				"--,'80%' AS OWN_RET\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS OBLIGATORY\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN \r\n" + 
				"        C1.CLMH_TOT_EST_BC * FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"    ELSE \r\n" + 
				"        P.PAPPRH_TOT_APPR_BC * FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"END AS CEDANT_PAID\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN \r\n" + 
				"        C1.CLMH_TOT_EST_BC - C1.CLMH_TOT_EST_BC * FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"    ELSE \r\n" + 
				"        P.PAPPRH_TOT_APPR_BC - P.PAPPRH_TOT_APPR_BC * FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"END AS AMOUNT\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS OWN_RETENSION\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('P','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS QUOTA_SHARE\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('S','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS SURPLUS\r\n" + 
				",TO_CHAR(C1.CLMH_CRE_DT,'DD-MON-YYYY') AS CREATED_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_APPROVAL_DT,'DD-MON-YYYY') AS APPROVED_DATE\r\n" + 
				",(SELECT USER_NAME FROM RMENU.MMGR_USERS WHERE USER_INIT=C1.CLMH_CRE_USER_INIT) AS CREATED_BY\r\n" + 
				",(SELECT USER_NAME FROM RMENU.MMGR_USERS WHERE USER_INIT=C1.CLMH_APPROVAL_BY) AS APPROVED_BY\r\n" + 
				"--,C2.*\r\n" + 
				"--,''C1,C1.*,'C2' C2,C2.*,'P',P.*,'G2',G2.*\r\n" + 
				",G2.PINT_SEQNUM AS EMP_SEQNUM\r\n" + 
				",G2.PINT_INSURED AS EMP_NAME_OF_INSURED\r\n" + 
				",D1.CLMD_COV_SYS_ID\r\n" + 
				",D1.CLMD_COVER\r\n" + 
				",D2.PCOV_SI_BC\r\n" + 
				"--,G2.*\r\n" + 
				"FROM RAIMS.INT_CLM_HEAD C1 \r\n" + 
				"INNER JOIN RAIMS.INT_CLM_FEATURE C2 ON C2.CLMF_CLMH_SYS_ID=C1.CLMH_SYS_ID AND C2.CLMF_REC_STATUS='A'\r\n" + 
				"--INNER JOIN RAIMS.INT_UW_POLH P ON P.POLH_POLNUM=C1.CLMH_POLNUM\r\n" + 
				"INNER JOIN RAIMS.INT_UW_PINT_MED G2 ON G2.PINT_SYS_ID=C2.CLMF_INT_SYS_ID AND G2.PINT_REC_STATUS='A'\r\n" + 
				"LEFT JOIN RAIMS.INT_CLM_DETL_MOTOR D1 ON D1.CLMD_CLMH_SYS_ID=C1.CLMH_SYS_ID AND D1.CLMD_REC_STATUS='A'\r\n" + 
				"LEFT JOIN RAIMS.INT_UW_PCOV D2 ON D2.PCOV_SYS_ID=D1.CLMD_COV_SYS_ID AND D2.PCOV_REC_STATUS='A'\r\n" + 
				"LEFT JOIN RAIMS.INT_CL_PAY_APPR_HEAD P ON P.PAPPRH_INTMTNH_CLMNUM=C1.CLMH_CLMNUM AND P.PAPPRH_INTMTNH_SYS_ID=C1.CLMH_SYS_ID AND P.PAPPRH_APPROVAL_BY IS NOT NULL\r\n" + 
				"--LEFT JOIN RAIMS.INT_CL_OPCL CL ON C1.CLMH_CLMNUM = (SELECT CL.COC_CLMNUM FROM RAIMS.INT_CL_OPCL CL WHERE CL.COC_CLMNUM=C1.CLMH_CLMNUM GROUP BY CL.CLMH_CLMNUM)\r\n" + 
				"--WHERE C1.CLMH_CLMNUM=$P{parClaimNumber}\r\n" + 
				"--WHERE C1.CLMH_CLMNUM='C01PA00200083'\r\n" + 
				"--WHERE C1.CLMH_APPROVAL_DT BETWEEN '01-JAN-2020' AND '26-NOV-2020' --176 RECORD\r\n" + 
				"WHERE C1.CLMH_APPROVAL_DT BETWEEN '"+fromDate+"' AND '"+toDate+"'\r\n" + 
				"AND C1.CLMH_COB IN('HC00','HC01','PA00','PA01')";
		
		System.out.println("-------- daily claim report ---------\n"+sql);
		List<rpt_0023_daily_claim_model>ls=new ArrayList<rpt_0023_daily_claim_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0023_daily_claim_model cliam=new rpt_0023_daily_claim_model();
			cliam.setNo(row.getString("no"));
			cliam.setClmh_sys_id(row.getString("clmh_sys_id"));
			cliam.setPint_sys_id(row.getString("pint_sys_id"));
			cliam.setPapprh_pay_to(row.getString("papprh_pay_to"));
			cliam.setPapprh_number(row.getString("papprh_number"));
			cliam.setPapprh_intmtnh_clmnum(row.getString("papprh_intmtnh_clmnum"));
			cliam.setPapprh_intmtnh_polnum(row.getString("papprh_intmtnh_polnum"));
			cliam.setPapprh_srno(row.getString("papprh_srno"));
			cliam.setPapprh_settle_yn(row.getString("papprh_settle_yn"));
			cliam.setSettlement_type(row.getString("settlement_type"));
			cliam.setClmh_cl_status(row.getString("clmh_cl_status"));
			cliam.setClaim_status_desc(row.getString("claim_status_desc"));
			cliam.setReason_of_claim_status(row.getString("reason_of_claim_status"));
			cliam.setSettlement_date(row.getString("settlement_date"));
			cliam.setPapprh_approval_dt(row.getString("papprh_approval_dt"));
			cliam.setClaim_number(row.getString("claim_number"));
			cliam.setHospital_name(row.getString("hospital_name"));
			cliam.setClmh_cob(row.getString("clmh_cob"));
			cliam.setClmh_extnum(row.getString("clmh_extnum"));
			cliam.setOri_policy(row.getString("ori_policy"));
			cliam.setPolicy_number(row.getString("policy_number"));
			cliam.setClmh_uwyr(row.getString("clmh_uwyr"));
			cliam.setAc_month(row.getString("ac_month"));
			cliam.setInsured_name(row.getString("insured_name"));
			cliam.setInception_date(row.getString("inception_date"));
			cliam.setExpire_date(row.getString("expire_date"));
			cliam.setProduct(row.getString("product"));
			cliam.setRisk_detail(row.getString("risk_detail"));
			cliam.setLost_date(row.getString("lost_date"));
			cliam.setReported_date(row.getString("reported_date"));
			cliam.setTransaction_date(row.getString("transaction_date"));
			cliam.setOs_days(row.getString("os_days"));
			cliam.setEvent(row.getString("event"));
			cliam.setClmh_place_accident(row.getString("clmh_place_accident"));
			cliam.setCause_of_loss(row.getString("cause_of_loss"));
			cliam.setCategory_of_loss(row.getString("category_of_loss"));
			cliam.setNarration_remark(row.getString("narration_remark"));
			cliam.setClaimant(row.getString("claimant"));
			cliam.setInitimated_amount(row.getDouble("initimated_amount"));
			cliam.setPayment_amount(row.getDouble("payment_amount"));
			cliam.setLoss_100(row.getDouble("loss_100"));
			cliam.setClaim_expense(row.getDouble("claim_expense"));
			cliam.setObligatory(row.getDouble("obligatory"));
			cliam.setCedant_paid(row.getDouble("cedant_paid"));
			cliam.setAmount(row.getDouble("amount"));
			cliam.setOwn_retension(row.getDouble("own_retension"));
			cliam.setQuota_share(row.getDouble("quota_share"));
			cliam.setSurplus(row.getDouble("surplus"));
			cliam.setCreated_date(row.getString("created_date"));
			cliam.setApproved_date(row.getString("approved_date"));
			cliam.setCreated_by(row.getString("created_by"));
			cliam.setApproved_by(row.getString("approved_by"));
			cliam.setEmp_seqnum(row.getString("emp_seqnum"));
			cliam.setEmp_name_of_insured(row.getString("emp_name_of_insured"));
			cliam.setClmd_cov_sys_id(row.getString("clmd_cov_sys_id"));
			cliam.setClmd_cover(row.getString("clmd_cover"));
			cliam.setPcov_si_bc(row.getDouble("pcov_si_bc"));
			cliam.setTo_date(toDate);
			ls.add(cliam);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	
	
	
}

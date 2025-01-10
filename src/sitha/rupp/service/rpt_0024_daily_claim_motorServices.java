package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0024_daily_claim_motor_model;


public class rpt_0024_daily_claim_motorServices extends GenericDaSupport {

	public List<rpt_0024_daily_claim_motor_model> getDailyClaim(String fromDate, String toDate,String productCode){
//		fromDate="01-SEP-2020";
		String sql="SELECT\r\n" + 
				"rownum as No\r\n" + 
				",C1.CLMH_SYS_ID\r\n" + 
				",C1.CLMH_INT_SYS_ID\r\n" + 
				",C1.CLMH_POLH_SYS_ID\r\n" + 
				",C1.CLMH_UWYR \r\n" + 
				",SUBSTR(C1.CLMH_POST_PERIOD,1,3)||'-20'||SUBSTR(C1.CLMH_POST_PERIOD,4,2) AS AC_MONTH\r\n" + 
				",P.PAPPRH_PAY_TO\r\n" + 
				",P.PAPPRH_NUMBER AS APPROVE_NUMBER\r\n" + 
				"--,P.PAPPRH_INTMTNH_CLMNUM AS CLAIM_NUMBER\r\n" + 
				",P.PAPPRH_INTMTNH_POLNUM\r\n" + 
				",P.PAPPRH_SRNO\r\n" + 
				",P.PAPPRH_SETTLE_YN\r\n" + 
				",CASE \r\n" + 
				"    WHEN P.PAPPRH_SETTLE_YN='N' THEN 'ON ACCOUNT'\r\n" + 
				"    WHEN P.PAPPRH_SETTLE_YN='Y' THEN 'FULL SETTLEMENT' \r\n" + 
				"END AS SETTLEMENT_TYPE\r\n" + 
				",C1.CLMH_CL_STATUS\r\n" + 
				",FN_GET_CLAIM_STATUS(P.PAPPRH_SETTLE_YN,C1.CLMH_CL_STATUS) AS CLAIM_STATUS_DESC\r\n" + 
				",FN_GET_CLAIM_REASON(C1.CLMH_CLMNUM) AS REASON_OF_CLAIM_STATUS\r\n" + 
				",FN_GET_CLAIM_SETTLEMENT_DATE(C1.CLMH_CLMNUM) AS SETTLEMENT_DATE\r\n" + 
				",TO_CHAR(P.PAPPRH_APPROVAL_DT,'DD-MON-YYYY') AS PAPPRH_APPROVAL_DT\r\n" + 
				",C1.CLMH_CLMNUM AS CLAIM_NUMBER\r\n" + 
				",C1.CLMH_COB\r\n" + 
				",C1.CLMH_EXTNUM\r\n" + 
				",C1.CLMH_POLNUM AS ORI_POLICY\r\n" + 
				",FN_GET_POLICY_FORMAT(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS POLICY_NUMBER\r\n" + 
				",FN_GET_INSURED_NAME(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS INSURED_NAME\r\n" + 
				",(SELECT PR.CLS_DESC FROM RAIMS.INM_MST_CLASS PR WHERE PR.CLS_CODE=C1.CLMH_COB) AS PRODUCT\r\n" + 
				",'' AS RISK_DETAIL\r\n" + 
				",FN_GET_POLICY_EFFECTIVE_DATE(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS INCEPTION_DATE\r\n" + 
				",FN_GET_POLICY_EXPIRE_DATE(C1.CLMH_POLNUM,C1.CLMH_COB,C1.CLMH_EXTNUM) AS EXPIRE_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_LOSS_DATE,'DD-MON-YYYY') AS LOST_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_REP_DATE,'DD-MON-YYYY') AS REPORTED_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_TXN_DATE,'DD-MON-YYYY') AS TRANSACTION_DATE\r\n" + 
				",FN_CALC_BETWEEN_DATE(C1.CLMH_REP_DATE,'"+toDate+"') AS OS_DAYS\r\n" + 
				",C1.CLMH_LNKNUM AS EVENT\r\n" + 
				",C1.CLMH_PLACE_ACCIDENT\r\n" + 
				",C1.CLMH_REM AS NARRATION_REMARK\r\n" + 
				",C1.CLMH_TOT_EST_BC AS INITIMATED_AMOUNT\r\n" + 
				",'' HOSPITAL_NAME\r\n" + 
				",C1.CLMH_TOT_EST_BC AS TOTAL_NET_RESERVE\r\n" + 
				",C1.CLMH_ADJ_FEE_BC AS CLAIM_EXPENSE\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN C1.CLMH_TOT_EST_BC ELSE P.PAPPRH_TOT_APPR_BC END AS \"LOSS_100Percent\"\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('O','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS OWN_RETENSION\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN \r\n" + 
				"        C1.CLMH_TOT_EST_BC * FN_GET_RI_SHARE_TAX_RECOMM('O','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"    ELSE \r\n" + 
				"        P.PAPPRH_TOT_APPR_BC * FN_GET_RI_SHARE_TAX_RECOMM('O','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"END AS AMOUNT_OWN\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS OBLIGATORY\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN \r\n" + 
				"        C1.CLMH_TOT_EST_BC * FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"    ELSE \r\n" + 
				"        P.PAPPRH_TOT_APPR_BC * FN_GET_RI_SHARE_TAX_RECOMM('C','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"END AS AMOUNT\r\n" + 
				",FN_GET_RI_SHARE_TAX_RECOMM('P','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) AS QUOTA_SHARE\r\n" + 
				",CASE WHEN P.PAPPRH_NUMBER IS NULL THEN \r\n" + 
				"        C1.CLMH_TOT_EST_BC * FN_GET_RI_SHARE_TAX_RECOMM('P','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"    ELSE \r\n" + 
				"        P.PAPPRH_TOT_APPR_BC * FN_GET_RI_SHARE_TAX_RECOMM('P','SHARED',C1.CLMH_COB,C1.CLMH_UWYR) /100\r\n" + 
				"END AS AMOUNT_QUOTA_SHARE\r\n" + 
				",TO_CHAR(C1.CLMH_CRE_DT,'DD-MON-YYYY') AS CREATED_DATE\r\n" + 
				",TO_CHAR(C1.CLMH_APPROVAL_DT,'DD-MON-YYYY') AS APPROVED_DATE\r\n" + 
				",(SELECT USER_NAME FROM RMENU.MMGR_USERS WHERE USER_INIT=C1.CLMH_CRE_USER_INIT) AS CREATED_BY\r\n" + 
				",(SELECT USER_NAME FROM RMENU.MMGR_USERS WHERE USER_INIT=C1.CLMH_APPROVAL_BY) AS APPROVED_BY\r\n" + 
				",C1.CLMH_MKMODEL AS MAKE_MODEL_CODE\r\n" + 
				",(SELECT MM.MOD_DESC FROM RAIMS.INM_MST_MKMOD MM WHERE MM.MOD_CODE=C1.CLMH_MKMODEL) AS MAKE_MODEL\r\n" + 
				",C1.CLMH_REGN_NO\r\n" + 
				",C1.CLMH_COL\r\n" + 
				",(SELECT DISTINCT(COL.COL_DESC) FROM RAIMS.INM_MST_COL COL WHERE COL.COL_CODE=C1.CLMH_COL) AS CAUSE_OF_LOSS\r\n" + 
				",FN_GET_FEATURE_CODE(C1.CLMH_SYS_ID) AS CAUSE_TYPE_OF_LOSS --LIKE OD AND TPL \r\n" + 
				",MD.PINTMV_SI_BC AS SUM_INSURED\r\n" + 
				",'' TO_DATE\r\n" + 
				"FROM RAIMS.INT_CLM_HEAD C1\r\n" + 
				"LEFT JOIN RAIMS.INT_UW_PINT_MOTOR MD ON MD.PINTMV_SYS_ID=C1.CLMH_INT_SYS_ID AND MD.PINTMV_PINT_SYS_ID=C1.CLMH_POLH_SYS_ID AND MD.PINTMV_REC_STATUS='A'\r\n" + 
				"LEFT JOIN RAIMS.INT_CL_PAY_APPR_HEAD P ON P.PAPPRH_INTMTNH_CLMNUM=C1.CLMH_CLMNUM AND P.PAPPRH_INTMTNH_SYS_ID=C1.CLMH_SYS_ID\r\n" + 
				"WHERE C1.CLMH_APPROVAL_DT BETWEEN '"+fromDate+"' AND '"+toDate+"' --179 RECORDS\r\n" + 
				"AND C1.CLMH_COB IN('MC','MC00','CV','CV00','PV','PV00')";
		
		System.out.println("-------- daily claim report ---------\n"+sql);
		List<rpt_0024_daily_claim_motor_model>ls=new ArrayList<rpt_0024_daily_claim_motor_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0024_daily_claim_motor_model claim=new rpt_0024_daily_claim_motor_model();
			claim.setNo(row.getString("no"));
			claim.setClmh_sys_id(row.getString("clmh_sys_id"));
			claim.setClmh_int_sys_id(row.getString("clmh_int_sys_id"));
			claim.setClmh_polh_sys_id(row.getString("clmh_polh_sys_id"));
			claim.setClmh_uwyr(row.getString("clmh_uwyr"));
			claim.setAc_month(row.getString("ac_month"));
			claim.setPapprh_pay_to(row.getString("papprh_pay_to"));
			claim.setApprove_number(row.getString("approve_number"));
			claim.setClaim_number(row.getString("claim_number"));
			claim.setPapprh_intmtnh_polnum(row.getString("papprh_intmtnh_polnum"));
			claim.setPapprh_srno(row.getString("papprh_srno"));
			claim.setPapprh_settle_yn(row.getString("papprh_settle_yn"));
			claim.setSettlement_type(row.getString("settlement_type"));
			claim.setClmh_cl_status(row.getString("clmh_cl_status"));
			claim.setClaim_status_desc(row.getString("claim_status_desc"));
			claim.setReason_of_claim_status(row.getString("reason_of_claim_status"));
			claim.setSettlement_date(row.getString("settlement_date"));
			claim.setPapprh_approval_dt(row.getString("papprh_approval_dt"));
			claim.setClmh_cob(row.getString("clmh_cob"));
			claim.setClmh_extnum(row.getString("clmh_extnum"));
			claim.setOri_policy(row.getString("ori_policy"));
			claim.setPolicy_number(row.getString("policy_number"));
			claim.setInsured_name(row.getString("insured_name"));
			claim.setProduct(row.getString("product"));
			claim.setRisk_detail(row.getString("risk_detail"));
			claim.setInception_date(row.getString("inception_date"));
			claim.setExpire_date(row.getString("expire_date"));
			claim.setLost_date(row.getString("lost_date"));
			claim.setReported_date(row.getString("reported_date"));
			claim.setTransaction_date(row.getString("transaction_date"));
			claim.setOs_days(row.getString("os_days"));
			claim.setEvent(row.getString("event"));
			claim.setClmh_place_accident(row.getString("clmh_place_accident"));
			claim.setNarration_remark(row.getString("narration_remark"));
			claim.setInitimated_amount(row.getString("initimated_amount"));
			claim.setHospital_name(row.getString("hospital_name"));
			claim.setTotal_net_reserve(row.getString("total_net_reserve"));
			claim.setClaim_expense(row.getString("claim_expense"));
			claim.setLoss_100percent(row.getDouble("loss_100percent"));
			claim.setOwn_retension(row.getDouble("own_retension"));
			claim.setAmount_own(row.getDouble("amount_own"));
			claim.setObligatory(row.getDouble("obligatory"));
			claim.setAmount(row.getDouble("amount"));
			claim.setQuota_share(row.getDouble("quota_share"));
			claim.setAmount_quota_share(row.getDouble("amount_quota_share"));
			claim.setCreated_date(row.getString("created_date"));
			claim.setApproved_date(row.getString("approved_date"));
			claim.setCreated_by(row.getString("created_by"));
			claim.setApproved_by(row.getString("approved_by"));
			claim.setMake_model_code(row.getString("make_model_code"));
			claim.setMake_model(row.getString("make_model"));
			claim.setClmh_regn_no(row.getString("clmh_regn_no"));
			claim.setClmh_col(row.getString("clmh_col"));
			claim.setCause_of_loss(row.getString("cause_of_loss"));
			claim.setCause_type_of_loss(row.getString("cause_type_of_loss"));
			claim.setSum_insured(row.getDouble("sum_insured"));
			//claim.setTo_date(row.getString("to_date"));
			claim.setTo_date(toDate);
			ls.add(claim);
		}
		//System.out.println(ls.toString());
		return ls;
	}
	
	
	
}

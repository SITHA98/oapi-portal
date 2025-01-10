package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0004_Endorsment_model;

public class rpt_0004_EndorsmentServices extends GenericDaSupport {

	public List<rpt_0004_Endorsment_model> getEndorsmentList(String fromDate, String toDate){
		String sql="WITH DATALIST AS (SELECT" +
				"--rownum as NO,\r\n" + 
				"a.PENDR_POLH_SYS_ID,\r\n" + 
				"b.PINTMV_PINT_SYS_ID PINTMV_PINT_SYS_ID,\r\n" + 
				"a.PENDR_COB PRODUCT_CODE,\r\n" + 
				"a.PENDR_UWYR UWYR,\r\n" + 
				"--PENDR_POLNUM, \r\n" + 
				"a.PENDR_ENDRNUM EN_POL_NUM,\r\n" + 
				"TO_CHAR(a.PENDR_DATE,'DD-MON-YYYY') ENDR_DATE,\r\n" + 
				"TO_CHAR(a.PENDR_EFFDT,'DD-MON-YYYY') ENDR_EFFDT,\r\n" + 
				"a.PENDR_AMT_FC AMOUNT,\r\n" + 
				"TO_CHAR(a.PENDR_APPROVAL_DT,'DD-MON-YYYY') APPROVAL_DATE,\r\n" + 
				"a.PENDR_APPROVAL_BY APPROVAL_BY,\r\n" + 
				"TO_CHAR(a.PENDR_CRE_DT,'DD-MON-YYYY') CREATION_DATE,\r\n" + 
				"TO_CHAR(a.PENDR_UPD_DT,'DD-MON-YYYY') UPDATE_DATE,\r\n" + 
				"a.PENDR_POST_PERIOD POST_PERIOD,\r\n" + 
				"b.PINTMV_MOD_DESC MAKE_MODEL,\r\n" + 
				"b.PINTMV_ENGINE_NO ENGINE_NO,\r\n" + 
				"b.PINTMV_CHASIS_NO CHASIS_NO,\r\n" + 
				"b.PINTMV_REGN_NO REGN_NO,\r\n" + 
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
				"(SELECT IH_DOC_NO FROM GLT_INTERFACE_HEAD D WHERE D.IH_POLH_SYS_ID = C.POLH_SYS_ID AND D.IH_POLH_EXTNUM = C.POLH_EXTNUM AND D.IH_JRNL_TYPE='CNU') INVOICE_NO\r\n" + 
				"FROM INT_UW_PENDR a,INT_UW_LPINT_MOTOR b,INT_UW_LPOLH c\r\n" + 
				"WHERE a.PENDR_POLH_SYS_ID  = b.PINTMV_PINT_SYS_ID\r\n" + 
				"AND   b.PINTMV_PINT_SYS_ID = c.POLH_SYS_ID\r\n" + 
				"AND   a.PENDR_EXTNUM       = b.PINTMV_EXTNUM \r\n" + 
				"AND   b.PINTMV_EXTNUM      = c.POLH_EXTNUM\r\n" + 
				"--AND   a.PENDR_POST_PERIOD  ='MAY20'\r\n" + 
				"--AND a.PENDR_UPD_DT BETWEEN '01-MAY-2020' AND '31-MAY-2020'\r\n" + 
				"AND a.PENDR_UPD_DT BETWEEN TO_DATE('"+fromDate+" 00:01','DD-MON-YYYY HH24:MI') AND TO_DATE('"+toDate+" 23:59','DD-MON-YYYY HH24:MI')" + 
				" UNION\r\n" + 
				"SELECT\r\n" + 
				"--rownum as NO,\r\n" + 
				"a.PENDR_POLH_SYS_ID,\r\n" + 
				"NULL PINTMV_PINT_SYS_ID,\r\n" + 
				"a.PENDR_COB PRODUCT_CODE,\r\n" + 
				"a.PENDR_UWYR UWYR,\r\n" + 
				"--PENDR_POLNUM, \r\n" + 
				"a.PENDR_ENDRNUM EN_POL_NUM,\r\n" + 
				"TO_CHAR(a.PENDR_DATE,'DD-MON-YYYY') ENDR_DATE,\r\n" + 
				"TO_CHAR(a.PENDR_EFFDT,'DD-MON-YYYY') ENDR_EFFDT,\r\n" + 
				"a.PENDR_AMT_FC AMOUNT,\r\n" + 
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
				"(SELECT IH_DOC_NO FROM GLT_INTERFACE_HEAD D WHERE D.IH_POLH_SYS_ID = C.POLH_SYS_ID AND D.IH_POLH_EXTNUM = C.POLH_EXTNUM AND D.IH_JRNL_TYPE='CNU') INVOICE_NO\r\n" + 
				"FROM INT_UW_PENDR a,INT_UW_LPOLH c\r\n" + 
				"WHERE \r\n" + 
				"--a.PENDR_POST_PERIOD  ='MAY20'\r\n" + 
				"A.PENDR_POLH_SYS_ID = C.POLH_SYS_ID\r\n" + 
				"AND A.PENDR_EXTNUM = C.POLH_EXTNUM\r\n" + 
				"--AND a.PENDR_UPD_DT BETWEEN '01-MAY-2020' AND '31-MAY-2020';\r\n" + 
				"AND a.PENDR_UPD_DT BETWEEN TO_DATE('"+fromDate+" 00:01','DD-MON-YYYY HH24:MI') AND TO_DATE('"+toDate+" 23:59','DD-MON-YYYY HH24:MI'))\r\n" +
				"SELECT rownum AS NO, D.* FROM DATALIST D";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- endorsement report ---------\n"+sql);
		List<rpt_0004_Endorsment_model>ls=new ArrayList<rpt_0004_Endorsment_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0004_Endorsment_model endorsment=new rpt_0004_Endorsment_model();
			endorsment.setNO(row.getString("NO"));
			endorsment.setPENDR_POLH_SYS_ID(row.getString("PENDR_POLH_SYS_ID"));
			endorsment.setPINTMV_PINT_SYS_ID(row.getString("PINTMV_PINT_SYS_ID"));
			endorsment.setPRODUCT_CODE(row.getString("PRODUCT_CODE"));
			endorsment.setUWYR(row.getString("UWYR"));
			endorsment.setEN_POL_NUM(row.getString("EN_POL_NUM"));
			endorsment.setENDR_DATE(row.getString("ENDR_DATE"));
			endorsment.setENDR_EFFDT(row.getString("ENDR_EFFDT"));
			endorsment.setAMOUNT(row.getDouble("AMOUNT"));
			endorsment.setAPPROVAL_DATE(row.getString("APPROVAL_DATE"));
			endorsment.setAPPROVAL_BY(row.getString("APPROVAL_BY"));
			endorsment.setCREATION_DATE(row.getString("CREATION_DATE"));
			endorsment.setUPDATE_DATE(row.getString("UPDATE_DATE"));
			endorsment.setPOST_PERIOD(row.getString("POST_PERIOD"));
			endorsment.setMAKE_MODEL(row.getString("MAKE_MODEL"));
			endorsment.setENGINE_NO(row.getString("ENGINE_NO"));
			endorsment.setCHASIS_NO(row.getString("CHASIS_NO"));
			endorsment.setREGN_NO(row.getString("REGN_NO"));
			endorsment.setCLIENT_CODE(row.getString("CLIENT_CODE"));
			endorsment.setACC_CODE(row.getString("ACC_CODE"));
			endorsment.setINVOICE(row.getString("INVOICE"));
			endorsment.setINSURED_NAME(row.getString("INSURED_NAME"));
			endorsment.setPOLICY_NUM(row.getString("POLICY_NUM"));
			endorsment.setSUM_INSURED(row.getDouble("SUM_INSURED"));
			endorsment.setPREMIUM(row.getDouble("PREMIUM"));
			endorsment.setPOL_ISSUED_DATE(row.getString("POL_ISSUED_DATE"));
			endorsment.setPOL_EFFECTIVE_DATE(row.getString("POL_EFFECTIVE_DATE"));
			endorsment.setPOL_EXPIRY_DATE(row.getString("POL_EXPIRY_DATE"));
			endorsment.setINVOICE_NO(row.getString("INVOICE_NO"));

			ls.add(endorsment);
		}
		System.out.println(ls.toString());
		return ls;
	}
}

package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.rpt_0002_quotation_model;

public class rpt_0002_quotationServices extends GenericDaSupport {

	public List<rpt_0002_quotation_model> getQuotationList(String productcode, String fromDate, String toDate){
		String sql="SELECT  \r\n" + 
				"--P.POLH_SYS_ID AS POLH_SYS_ID,\r\n" + 
				"--P.POLH_EXTNUM AS POLH_EXTNUM,\r\n" + 
				"--P.POLH_SRNUM AS POLH_SRNUM,\r\n" + 
				"--P.POLH_STATUS AS POLH_STATUS,\r\n" + 
				"--P.POLH_POST_PERIOD AS POLH_POST_PERIOD,\r\n" + 
				"rownum as NO,\r\n" + 
				"--P.POLH_SRNUM AS SEQ,\r\n" + 
				"P.POLH_POLNUM AS QUOTATION_NO,\r\n" + 
				"UPPER(P.POLH_ASSRDNM) AS INSURED_NAME,\r\n" + 
				"P.POLH_SI_FC AS SUM_INSURED,\r\n" + 
				"POLH_PREM_FC AS CROSS_PREMIUM,\r\n" + 
				"TO_CHAR(P.POLH_ISSDT,'DD-MON-YYYY') AS ISSUE_DATE,\r\n" + 
				"P.POLH_DEBIT_PARTY AS INTERMEDIARY,\r\n" + 
				"P.POLH_EMPLOYEE AS ISSUE_BY,\r\n" + 
				"'' AS REMARK\r\n" + 
				"FROM RAIMS.INT_UW_POLH P \r\n" + 
				"--WHERE POLH_POLNUM='Q01PV002000001'; --POLH_QUOTNUM='Q01PV00200000001';\r\n" + 
				"WHERE POLH_POLNUM LIKE 'Q%' AND \r\n" + 
				"POLH_CRE_DT BETWEEN TO_DATE('"+fromDate+"','DD-MON-YYYY') AND TO_DATE('"+toDate+"','DD-MON-YYYY')+1";
				//"--POLH_ISSDT BETWEEN TO_DATE('"+fromDate+"','DD-MON-YYYY') AND TO_DATE('"+toDate+"','DD-MON-YYYY')";
		/*if(date!=null || !date.equalsIgnoreCase("")){
				sql+="	WHERE TO_DATE(A.CREATED_DATE)<='"+date+"' ";
		}*/
		System.out.println("-------- quotation report ---------\n"+sql);
		List<rpt_0002_quotation_model>ls=new ArrayList<rpt_0002_quotation_model>();
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rpt_0002_quotation_model quotation=new rpt_0002_quotation_model();
			quotation.setNo(row.getString("No"));
			quotation.setQuotationNo(row.getString("QUOTATION_NO"));
			quotation.setInsuredName(row.getString("INSURED_NAME"));
			quotation.setSumInsured(row.getDouble("SUM_INSURED"));
			quotation.setCrossPremium(row.getDouble("CROSS_PREMIUM"));
			quotation.setIssueDate(row.getString("ISSUE_DATE"));
			quotation.setIntermediary(row.getString("INTERMEDIARY"));
			quotation.setIssueBy(row.getString("ISSUE_BY"));
			quotation.setRemark(row.getString("REMARK"));
			ls.add(quotation);
		}
		System.out.println(ls.toString());
		return ls;
	}
	
}

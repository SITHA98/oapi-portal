package sitha.rupp.model;

public class rpt_0003_policy_model{
	String NO="";
	String PRODUCT_CODE="";
	String POLICY_NUMBER="";
	String INSURED_NAME="";
	String INVOICE="";
	double SUM_INSURED;
	double CROSS_PREMIUM;
	String ISSUE_DATE="";
	String EFFECTIVE_DATE="";
	String EXPIRY_DATE="";
	String INTERMEDIARY="";
	String INTERMEDIARY_NAME="";
	String ISSUE_BY="";
	String REMARK="";
	double premium;
	double adjustment_amount;
	double admin_fee;
	double invoice_amount;
	double client_accounting_amount;
	
	public String getNO() {
		return NO;
	}
	public void setNO(String nO) {
		NO = nO;
	}
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getPOLICY_NUMBER() {
		return POLICY_NUMBER;
	}
	public void setPOLICY_NUMBER(String pOLICY_NUMBER) {
		POLICY_NUMBER = pOLICY_NUMBER;
	}
	public String getINSURED_NAME() {
		return INSURED_NAME;
	}
	public void setINSURED_NAME(String iNSURED_NAME) {
		INSURED_NAME = iNSURED_NAME;
	}
	public String getINVOICE() {
		return INVOICE;
	}
	public void setINVOICE(String iNVOICE) {
		INVOICE = iNVOICE;
	}
	public double getSUM_INSURED() {
		return SUM_INSURED;
	}
	public void setSUM_INSURED(double sUM_INSURED) {
		SUM_INSURED = sUM_INSURED;
	}
	public double getCROSS_PREMIUM() {
		return CROSS_PREMIUM;
	}
	public void setCROSS_PREMIUM(double cROSS_PREMIUM) {
		CROSS_PREMIUM = cROSS_PREMIUM;
	}
	public String getISSUE_DATE() {
		return ISSUE_DATE;
	}
	public void setISSUE_DATE(String iSSUE_DATE) {
		ISSUE_DATE = iSSUE_DATE;
	}
	public String getEFFECTIVE_DATE() {
		return EFFECTIVE_DATE;
	}
	public void setEFFECTIVE_DATE(String eFFECTIVE_DATE) {
		EFFECTIVE_DATE = eFFECTIVE_DATE;
	}
	public String getEXPIRY_DATE() {
		return EXPIRY_DATE;
	}
	public void setEXPIRY_DATE(String eXPIRY_DATE) {
		EXPIRY_DATE = eXPIRY_DATE;
	}
	public String getINTERMEDIARY() {
		return INTERMEDIARY;
	}
	public void setINTERMEDIARY(String iNTERMEDIARY) {
		INTERMEDIARY = iNTERMEDIARY;
	}
	public String getISSUE_BY() {
		return ISSUE_BY;
	}
	public void setISSUE_BY(String iSSUE_BY) {
		ISSUE_BY = iSSUE_BY;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public double getPremium() {
		return premium;
	}
	public void setPremium(double premium) {
		this.premium = premium;
	}
	public double getAdjustment_amount() {
		return adjustment_amount;
	}
	public void setAdjustment_amount(double adjustment_amount) {
		this.adjustment_amount = adjustment_amount;
	}
	public double getAdmin_fee() {
		return admin_fee;
	}
	public void setAdmin_fee(double admin_fee) {
		this.admin_fee = admin_fee;
	}
	public double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public double getClient_accounting_amount() {
		return client_accounting_amount;
	}
	public void setClient_accounting_amount(double client_accounting_amount) {
		this.client_accounting_amount = client_accounting_amount;
	}
	
	
	public String getINTERMEDIARY_NAME() {
		return INTERMEDIARY_NAME;
	}
	public void setINTERMEDIARY_NAME(String iNTERMEDIARY_NAME) {
		INTERMEDIARY_NAME = iNTERMEDIARY_NAME;
	}
	@Override
	public String toString() {
		return "rpt_0003_policy_model [NO=" + NO + ", PRODUCT_CODE=" + PRODUCT_CODE + ", POLICY_NUMBER=" + POLICY_NUMBER
				+ ", INSURED_NAME=" + INSURED_NAME + ", INVOICE=" + INVOICE + ", SUM_INSURED=" + SUM_INSURED
				+ ", CROSS_PREMIUM=" + CROSS_PREMIUM + ", ISSUE_DATE=" + ISSUE_DATE + ", EFFECTIVE_DATE="
				+ EFFECTIVE_DATE + ", EXPIRY_DATE=" + EXPIRY_DATE + ", INTERMEDIARY=" + INTERMEDIARY + ", ISSUE_BY="
				+ ISSUE_BY + ", REMARK=" + REMARK + ", premium=" + premium + ", adjustment_amount=" + adjustment_amount
				+ ", admin_fee=" + admin_fee + ", invoice_amount=" + invoice_amount + ", client_accounting_amount="
				+ client_accounting_amount + "]";
	}
	

	
	
}

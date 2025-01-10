package sitha.rupp.model;

public class rpt_0002_quotation_model{
	private String No="";
	private String QuotationNo="";
	private String InsuredName="";
	private double SumInsured;
	private double CrossPremium;
	private String IssueDate="";
	private String Intermediary="";
	private String IssueBy="";
	private String Remark="";
	public String getNo() {
		return No;
	}
	public void setNo(String no) {
		No = no;
	}
	public String getQuotationNo() {
		return QuotationNo;
	}
	public void setQuotationNo(String quotationNo) {
		QuotationNo = quotationNo;
	}
	public String getInsuredName() {
		return InsuredName;
	}
	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}
	public double getSumInsured() {
		return SumInsured;
	}
	public void setSumInsured(double sumInsured) {
		SumInsured = sumInsured;
	}
	public double getCrossPremium() {
		return CrossPremium;
	}
	public void setCrossPremium(double crossPremium) {
		CrossPremium = crossPremium;
	}
	public String getIssueDate() {
		return IssueDate;
	}
	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}
	public String getIntermediary() {
		return Intermediary;
	}
	public void setIntermediary(String intermediary) {
		Intermediary = intermediary;
	}
	public String getIssueBy() {
		return IssueBy;
	}
	public void setIssueBy(String issueBy) {
		IssueBy = issueBy;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	@Override
	public String toString() {
		return "rpt_0002_quotation_model [No=" + No + ", QuotationNo=" + QuotationNo + ", InsuredName=" + InsuredName
				+ ", SumInsured=" + SumInsured + ", CrossPremium=" + CrossPremium + ", IssueDate=" + IssueDate
				+ ", Intermediary=" + Intermediary + ", IssueBy=" + IssueBy + ", Remark=" + Remark + "]";
	}
	
	
}

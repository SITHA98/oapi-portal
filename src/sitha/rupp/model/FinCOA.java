package sitha.rupp.model;

public class FinCOA {
	
	private int no;
	private String glCode;
	private String code;
	private String glDesc;
	private String deleted;
	private String remark;
	private int created_by;
	private String created_date;
	private int authorized_by;
	private String authorized_date;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGlDesc() {
		return glDesc;
	}
	public void setGlDesc(String glDesc) {
		this.glDesc = glDesc;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCreated_by() {
		return created_by;
	}
	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public int getAuthorized_by() {
		return authorized_by;
	}
	public void setAuthorized_by(int authorized_by) {
		this.authorized_by = authorized_by;
	}
	public String getAuthorized_date() {
		return authorized_date;
	}
	public void setAuthorized_date(String authorized_date) {
		this.authorized_date = authorized_date;
	}
	@Override
	public String toString() {
		return "COA [no=" + no + ", glCode=" + glCode + ", code=" + code + ", glDesc=" + glDesc + ", deleted=" + deleted
				+ ", remark=" + remark + "]";
	}
	
}

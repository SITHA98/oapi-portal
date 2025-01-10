package sitha.rupp.model;

import java.sql.Date;

public class MTGroup {
	private int gr_Id;
	private String gr_Name;
	private int gr_Status;
	private int gr_CreateBy;
	private String createDate;
	private int updateBy;
	private String updateDate;
	private String created_by;
	private Date created_date;
	
	public int getGr_Id() {
		return gr_Id;
	}
	public void setGr_Id(int gr_Id) {
		this.gr_Id = gr_Id;
	}
	public String getGr_Name() {
		return gr_Name;
	}
	public void setGr_Name(String gr_Name) {
		this.gr_Name = gr_Name;
	}
	public int getGr_Status() {
		return gr_Status;
	}
	public void setGr_Status(int gr_Status) {
		this.gr_Status = gr_Status;
	}
	public int getGr_CreateBy() {
		return gr_CreateBy;
	}
	public void setGr_CreateBy(int gr_CreateBy) {
		this.gr_CreateBy = gr_CreateBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}	
	
}

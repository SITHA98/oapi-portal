package sitha.rupp.model;

import org.springframework.stereotype.Component;


public class Test_Modal {
	private String test_id;
	private String test_name;
	private String value_date;
	private String branch_code;
	private int userid;
	
	public String getTest_id() {
		return test_id;
	}
	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}
	public String getTest_name() {
		return test_name;
	}
	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}
	public String getValue_date() {
		return value_date;
	}
	public void setValue_date(String value_date) {
		this.value_date = value_date;
	}
	public String getBranch_code() {
		return branch_code;
	}
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	@Override
	public String toString() {
		return "Test_Modal [test_id=" + test_id + ", test_name=" + test_name + ", value_date=" + value_date
				+ ", branch_code=" + branch_code + ", userid=" + userid + "]";
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
	
	
	
}

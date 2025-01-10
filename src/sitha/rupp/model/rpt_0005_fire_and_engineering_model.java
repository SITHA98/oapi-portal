package sitha.rupp.model;

public class rpt_0005_fire_and_engineering_model{
	String sysid="";
	String condictionCode="";
	String condictionDesc="";
	public String getSys_id() {
		return sysid;
	}
	public void setSys_id(String sys_id) {
		this.sysid = sys_id;
	}
	public String getCondictionCode() {
		return condictionCode;
	}
	public void setCondictionCode(String condictionCode) {
		this.condictionCode = condictionCode;
	}
	public String getCondictionDesc() {
		return condictionDesc;
	}
	public void setCondictionDesc(String condictionDesc) {
		this.condictionDesc = condictionDesc;
	}
	
	@Override
	public String toString() {
		return condictionCode + "            " + condictionDesc + "\n";
	}
	
	
	
}

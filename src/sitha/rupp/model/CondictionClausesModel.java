package sitha.rupp.model;

public class CondictionClausesModel{
	String sysid="";
	String condictionCode="";
	String condictionDesc="";
	String ctxText="";
	
	public String getCtxText() {
		return ctxText;
	}
	public void setCtxText(String ctxText) {
		this.ctxText = ctxText;
	}
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
		return condictionCode + "          " + condictionDesc + "\n";
	}
	
	
	
}

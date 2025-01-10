package sitha.rupp.model;

public class SYS_EVENT_LOG {
	private long ID;
	private long USER_ID;
	private String EVENT_CODE;
	private String EVENT_DESCRIPTION;
	private String DATE_DONE;
	private char DELETED;
	private String RPT_TYPE;
	public SYS_EVENT_LOG() {
		super();
	}
	public SYS_EVENT_LOG(long iD, long uSER_ID, String eVENT_CODE, String eVENT_DESCRIPTION, String dATE_DONE,
			char dELETED, String rPT_TYPE) {
		super();
		ID = iD;
		USER_ID = uSER_ID;
		EVENT_CODE = eVENT_CODE;
		EVENT_DESCRIPTION = eVENT_DESCRIPTION;
		DATE_DONE = dATE_DONE;
		DELETED = dELETED;
		RPT_TYPE = rPT_TYPE;
	}
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public long getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(long uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getEVENT_CODE() {
		return EVENT_CODE;
	}
	public void setEVENT_CODE(String eVENT_CODE) {
		EVENT_CODE = eVENT_CODE;
	}
	public String getEVENT_DESCRIPTION() {
		return EVENT_DESCRIPTION;
	}
	public void setEVENT_DESCRIPTION(String eVENT_DESCRIPTION) {
		EVENT_DESCRIPTION = eVENT_DESCRIPTION;
	}
	public String getDATE_DONE() {
		return DATE_DONE;
	}
	public void setDATE_DONE(String dATE_DONE) {
		DATE_DONE = dATE_DONE;
	}
	public char getDELETED() {
		return DELETED;
	}
	public void setDELETED(char dELETED) {
		DELETED = dELETED;
	}
	public String getRPT_TYPE() {
		return RPT_TYPE;
	}
	public void setRPT_TYPE(String rPT_TYPE) {
		RPT_TYPE = rPT_TYPE;
	}
	@Override
	public String toString() {
		return "SYS_EVENT_LOG [ID=" + ID + ", USER_ID=" + USER_ID + ", EVENT_CODE=" + EVENT_CODE
				+ ", EVENT_DESCRIPTION=" + EVENT_DESCRIPTION + ", DATE_DONE=" + DATE_DONE + ", DELETED=" + DELETED
				+ ", RPT_TYPE=" + RPT_TYPE + "]";
	}
	public String strSQL(SYS_EVENT_LOG event){
		
		String sql="INSERT INTO SYS_EVENT_LOG(ID,USER_ID,EVENT_CODE,EVENT_DESCRIPTION,DATE_DONE,DELETED,RPT_TYPE) "
				+ " VALUES(SYS_EVENT_LOG_ID_SEQ.NEXTVAL,"+event.getUSER_ID()+",'"+event.getEVENT_CODE()+"'"
				+ ",'"+event.getEVENT_DESCRIPTION()+"',sysdate,'N','"+event.getRPT_TYPE()+"')";
		return sql;
	}
	public SYS_EVENT_LOG initEvent(int reportType,int USER_ID,String eventDesc){
		SYS_EVENT_LOG event=new SYS_EVENT_LOG();
		event.setUSER_ID(USER_ID);
		event.setEVENT_CODE("RPT");
		event.setEVENT_DESCRIPTION(eventDesc);
		String strRPTType="";
		if(reportType==0){
			strRPTType="text/html";
		}else if(reportType==2){
			strRPTType="text/excel";
		}else{
			strRPTType="text/pdf";
		}
		event.setRPT_TYPE(strRPTType);
		return event;
	}
}

package sitha.rupp.model;

public class UserForgetPassword {

	  private int USER_ID; 
	  private String RESET_CODE_HASH;
	  private String EMAIL; 
	  private String PHONE; 
	  private String HASH_LINK;
	  private int RESET_COUNT;
	  private String VALUE_DATE;
	  private String ACTIVATED;
	  
	  
	public String getACTIVATED() {
		return ACTIVATED;
	}
	public void setACTIVATED(String aCTIVATED) {
		ACTIVATED = aCTIVATED;
	}
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getRESET_CODE_HASH() {
		return RESET_CODE_HASH;
	}
	public void setRESET_CODE_HASH(String rESET_CODE_HASH) {
		RESET_CODE_HASH = rESET_CODE_HASH;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getHASH_LINK() {
		return HASH_LINK;
	}
	public void setHASH_LINK(String hASH_LINK) {
		HASH_LINK = hASH_LINK;
	}
	public int getRESET_COUNT() {
		return RESET_COUNT;
	}
	public void setRESET_COUNT(int rESET_COUNT) {
		RESET_COUNT = rESET_COUNT;
	}
	public String getVALUE_DATE() {
		return VALUE_DATE;
	}
	public void setVALUE_DATE(String vALUE_DATE) {
		VALUE_DATE = vALUE_DATE;
	}
	  
	  
}

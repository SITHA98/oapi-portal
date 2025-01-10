package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.RsaKey;
import sitha.rupp.model.RsaKeyInfo;
import sitha.rupp.model.RsaKeyList;
import sitha.rupp.model.RsaKeyView;

public class RsaKeyDa extends GenericDaSupport {
	
	public List<RsaKeyList> getRequest(int pId){
		List<RsaKeyList> key_ls=new ArrayList<>();
		String sql=" SELECT PARTNER_ID, PARTNER_NAME, PARTNER_PUBLIC_KEY FROM RSA_KEYS_PAIR \r\n" + 
					" WHERE ACTIVE = 1 AND APPROVAL_STATUS = 0 AND PARTNER_ID = '"+pId+"'";			
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			RsaKeyList ls=new RsaKeyList();
			ls.setPartner_id(row.getInt("PARTNER_ID"));
			ls.setPartner_name(row.getString("PARTNER_NAME"));
			ls.setPublic_key(row.getString("PARTNER_PUBLIC_KEY"));	
			key_ls.add(ls);
		}
		return key_ls;
	}
	
	public RsaKeyInfo getInfo(int pId){
		RsaKeyInfo ls=new RsaKeyInfo();
		String sql=" SELECT PARTNER_ID, PARTNER_NAME, PARTNER_PUBLIC_KEY FROM RSA_KEYS_PAIR \r\n" + 
					" WHERE ACTIVE = 1 AND APPROVAL_STATUS = 0 AND PARTNER_ID = '"+pId+"' ";		
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			ls.setPartner_id(row.getInt("PARTNER_ID"));
			ls.setPartner_name(row.getString("PARTNER_NAME"));
			ls.setPublic_key(row.getString("PARTNER_PUBLIC_KEY"));	
		}
		return ls;
	}

	public int getRowNum(int pId){
		int rowNum = 0;
		String sql=" SELECT COUNT(PARTNER_ID) AS COUNT_ROW FROM RSA_KEYS_PAIR " + 
					" WHERE ACTIVE = 1 AND PARTNER_ID = '"+pId+"' ";		
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rowNum = row.getInt("COUNT_ROW");
		}
		return rowNum;
	}
	
	public int disableRsaKey(RsaKey r) {
		String SQL="UPDATE RSA_KEYS_PAIR SET ACTIVE = 0,LAST_UPDATE_BY='"+r.getCreate_by()+"',LAST_UPDATE_DATE=sysdate " +
					" WHERE PARTNER_ID = '"+r.getPartner_id()+"' AND ACTIVE = 1 AND APPROVAL_STATUS = 1";				
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(SQL);
		System.out.println("disableRsaKey: "+SQL+"\nresult: "+i);
		return i;
	}
	
	public int insertRsaKey(RsaKey r) {
		Random random = new Random();
		int otp = random.nextInt(900000) + 100000;
		String SQL= " INSERT INTO RSA_KEYS_PAIR (PARTNER_ID, PARTNER_NAME, PARTNER_PUBLIC_KEY, CREATE_BY, CREATE_DATE, APPROVAL_STATUS, ACTIVE "
					+ " ,APPROVAL_OTP, OTP_EXPIRY_TIME) "
					+ " VALUES ('"+r.getPartner_id()+"','"+r.getPartner_name()+"','"+r.getPublic_key()+"','"+r.getCreate_by()+"',sysdate,0,1 "
					+ " , '"+otp+"', SYSDATE + (.000694 * 5))";
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(SQL);
		System.out.println("insertRsaKey: "+SQL+"\nresult: "+i);
		if (i>0) {
			i = sendOTP(otp, r.getPhone_number());
		}
		return i;
	}
	
	public int updateRsaKey(RsaKey r) {	
		String SQL="UPDATE RSA_KEYS_PAIR SET PARTNER_PUBLIC_KEY = '"+r.getPublic_key()+"',LAST_UPDATE_BY='"+r.getCreate_by()+"' "
				+ ",LAST_UPDATE_DATE=sysdate "
				+ " WHERE PARTNER_ID = '"+r.getPartner_id()+"' AND ACTIVE = 1 AND APPROVAL_STATUS = 0";
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(SQL);
		System.out.println("updateRsaKey: "+SQL+"\nresult: "+i);
		return i;
	}
	
	public int sendOTP(int otp, String phone_number) {
		RestTemplate template = new RestTemplate();
		String content = "Dear Value Member,\nYour OTP is " + otp + "\nIt will be expired in 5 minutes";
		try {
			String url="http://10.80.80.9:8081/SMSGateway/public/api/sendsms";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("phonenumber", phone_number)
					.queryParam("content", content).queryParam("sendfrom", "PrinceSMS").queryParam("countrycode", "855");
			HttpEntity<?> entity = new HttpEntity<>(headers);
			HttpEntity<String> response = template.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
			System.out.println("OTP sending to customer number:" + phone_number);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int confirmOTP(RsaKey r){
		int rowNum = 0;
		String sql="SELECT CASE WHEN APPROVAL_OTP = '"+r.getOtp()+"' THEN ( " + 
					"			CASE WHEN OTP_EXPIRY_TIME >= SYSDATE THEN '1' " + 
					"			ELSE '2' END) " + 
					"	   ELSE '0' END AS CONFIRM " + 
					"FROM RSA_KEYS_PAIR WHERE PARTNER_ID = '"+r.getPartner_id()+"' AND ACTIVE = 1 AND APPROVAL_STATUS = 0";		
		Application_Properties.SERIAL=1;
		System.out.println("sql confirmOTP: "+ sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			rowNum = row.getInt("CONFIRM");
		}
		return rowNum;
	}
	
	public int updateApproval(RsaKey r){
		String sql="UPDATE RSA_KEYS_PAIR SET APPROVAL_STATUS = 1, APPROVAL_BY = '"+r.getCreate_by()+"', APPROVAL_DATE = SYSDATE WHERE PARTNER_ID = '"+r.getPartner_id()+"' AND ACTIVE = 1";		
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(sql);
		return i;
	}
	
	public RsaKeyView getPublicKey(int pId){
		RsaKeyView r = new RsaKeyView();
		String sql=" SELECT PARTNER_PUBLIC_KEY, PRINCE_PUBLIC_KEY FROM RSA_KEYS_PAIR " + 
					" WHERE ACTIVE = 1 AND  APPROVAL_STATUS = 1 AND PARTNER_ID = '"+pId+"' ";		
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			r.setPartner_public_key(row.getString("PARTNER_PUBLIC_KEY"));
			r.setPrince_public_key(row.getString("PRINCE_PUBLIC_KEY"));
		}
		return r;
	}

	public int cancelRsaKey(RsaKey r) {	
		String SQL="UPDATE RSA_KEYS_PAIR SET ACTIVE = 0, LAST_UPDATE_BY='"+r.getCreate_by()+"' "
				+ ",LAST_UPDATE_DATE=sysdate "
				+ " WHERE PARTNER_ID = '"+r.getPartner_id()+"' AND ACTIVE = 1 AND APPROVAL_STATUS = 0";
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(SQL);
		System.out.println("cancelRsaKey: "+SQL+"\nresult: "+i);
		return i;
	}
}

package sitha.rupp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.password.VerifyHashPassword;


public class LoginDa extends GenericDaSupport {
	
	public int getUserLogin(String userName, String passWord) {
		
		Application_Properties.SERIAL=1;
		
		String sql = "SELECT * FROM USERS U WHERE U.USER_NAME='" + userName + "' AND U.PASSWORD='" + passWord
				+ "' AND ROWNUM=1";
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		int userId = 0;
		while (row.next()) {
			userId = Integer.parseInt(row.getString("USER_ID"));
		}
		return userId;
	}
	
	public int getUserLoginHashPassword(String userName, String passWord) throws NoSuchAlgorithmException, InvalidKeySpecException {
		//GenerateHashPassword ghPass=new GenerateHashPassword();
		//String generatedSecuredPasswordHash=ghPass.generateStorngPasswordHash(passWord);
		Application_Properties.SERIAL=1;
		
		String sql = "SELECT * FROM USERS U WHERE UPPER(U.USER_NAME)=UPPER('" + userName + "') AND DELETED='N'";
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		int userId = 0;
		String ReadDbPassword="";
		while (row.next()) {
			userId = Integer.parseInt(row.getString("USER_ID"));
			ReadDbPassword=row.getString("password");
		}
		//System.out.println("generatedSecuredPasswordHash="+generatedSecuredPasswordHash);
		//System.out.println("ReadDbPassword="+ReadDbPassword);
		VerifyHashPassword verifyPass=new VerifyHashPassword();
		boolean matched = verifyPass.validatePassword(passWord, ReadDbPassword);
		System.out.println(matched);
		if(matched==false) {
			userId=0;
		}
//		System.out.println("user id : "+userId);
		return userId;
	}

	public int getUserGr(int id) {
		String sql = "SELECT GROUP_ID FROM USERS U WHERE U.USER_ID='" + id + "'";
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		int grId = 0;
		while (row.next()) {
			grId = Integer.parseInt(row.getString("GROUP_ID"));
		}
		return grId;
	}

	public int insertLoginLogout(int user) {
		String SQL = "Insert into LOGIN_LOG(LOGIN_LOG_ID,USER_ID,LOGIN_DATE,LOG_STATUS)values "
				+ " (LOGIN_LOG_ID_SEQ.nextval,'" + user + "',sysdate,'O')";
		Application_Properties.SERIAL=1;
		int i = getJdbcTemplate().update(SQL);
		return i;
	}

	public int updateUserStatusOnline(int user, int status) {
		String SQL = "UPDATE USERS u SET u.STATUS_ONLINE=" + status + " WHERE u.USER_ID=" + user;
		Application_Properties.SERIAL=1;
		int i = getJdbcTemplate().update(SQL);
		return i;
	}

	public int getUserName(String name) {
		String sql = "SELECT u.USER_ID FROM USERS U WHERE U.USER_NAME='" + name + "'";
		Application_Properties.SERIAL=1;
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		int uId = 0;
		while (row.next()) {
			uId = Integer.parseInt(row.getString("USER_ID"));
		}
		return uId;
	}

	public int getPassWord(String pass) {
		String sql = "SELECT u.USER_ID FROM USERS U WHERE U.PASSWORD='" + pass + "'";
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		int uId = 0;
		while (row.next()) {
			uId = Integer.parseInt(row.getString("USER_ID"));
		}
		return uId;
	}

	/*public int updateLogout(int user) {
		String SQL = "UPDATE LOGIN_LOGOUT L SET L.LOGOUT_STATUS=1,L.LOGOUT_DATE=sysdate_mcb WHERE l.LOGIN_LOGOUT_ID="
				+ "(SELECT MAX(ll.LOGIN_LOGOUT_ID) FROM LOGIN_LOGOUT ll WHERE ll.USER_ID=" + user + ");";
		SQL +="UPDATE USERS SET STATUS_ONLINE=0 WHERE USER_ID="+user+";";
		int i = getJdbcTemplate().update(ClsHelper.Begin()+SQL+ClsHelper.End());
		return i;
	}*/
}

package sitha.rupp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.MTUser;
import sitha.rupp.model.UserForgetPassword;
import sitha.rupp.password.GenerateHashPassword;
import sitha.rupp.password.VerifyHashPassword;


public class ForgetPasswordService extends GenericDaSupport {

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");

	public void processUserForgetPassword(String userName, String Email, String Phone)
			throws Exception {
		int i = 0;
		UserDa uda = new UserDa();
		MTUser u = new MTUser();
		MTUser u_name = new MTUser();
		int uid = uda.getUserByNameEmail(userName, Email);
		u_name = uda.getUserById(uid);
		if (uid > 0) {
			// 1- insert record in table user_reset_password.
			// 2- generate email information and hash link.
			// 3- send mail to user.
			UserForgetPassword URpass = new UserForgetPassword();
			u = uda.getUserByUserNameAndEmail(userName, Email);
			// generate hash code email
			GenerateHashPassword genHash = new GenerateHashPassword();
			String hashEmailLink = genHash.generateStorngPasswordHash(Email);

			String genCode = ClsHelper.generateOTP();
			String hashResetCode = genHash.generateStorngPasswordHash(genCode);

			URpass.setUSER_ID(u.getUserId());
			URpass.setRESET_CODE_HASH(hashResetCode);
			URpass.setRESET_COUNT(0);
			URpass.setEMAIL(Email);

			URpass.setPHONE(u.getPhone());
			URpass.setHASH_LINK(hashEmailLink);
			URpass.setVALUE_DATE(ClsHelper.getCurrentDateTime());
			URpass.setACTIVATED("NO");

			// Start send email
			String forgetPasswordLinkToUser = "";
			String _URL=component.getScalareString("SELECT URL FROM LINK_MAIN WHERE DELETED='N' AND LINK_ID=1");
			/*forgetPasswordLinkToUser = "http://localhost:8088/princebank-reports-engine/forgetPasswordVerify1?id="
					+ uid + "&key=" + hashEmailLink;*/
			forgetPasswordLinkToUser = _URL+"forgetPasswordVerify1?id="
					+ uid + "&key=" + hashEmailLink;

			SendEmailServicesMsExchange emailService = new SendEmailServicesMsExchange();
			String ContentMessage = emailContent(u_name.getUser_Name(), genCode, forgetPasswordLinkToUser);
			ArrayList<String> recipientsList = new ArrayList<String>();
			recipientsList.add(URpass.getEMAIL());
			emailService.sendEmail(recipientsList, "Forget Password", ContentMessage);

			// After send mail then store in database
			i = insertUserForgetPassword(URpass);

			System.out.println(i + "|" + "request reset password done.");

		} else {

			System.out.println("invalid user name or email address.");
		}

		// return i;
	}

	public int insertUserForgetPassword(UserForgetPassword URpass)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String SQL = "Insert into USERS_FORGET_PASSWORD(USER_ID, RESET_CODE_HASH, EMAIL, PHONE, HASH_LINK, RESET_COUNT, VALUE_DATE,ACTIVATED)"
				+ "values" + " ('" + URpass.getUSER_ID() + "','" + URpass.getRESET_CODE_HASH() + "','"
				+ URpass.getEMAIL() + "','" + URpass.getPHONE() + "','" + URpass.getHASH_LINK() + "','"
				+ URpass.getRESET_COUNT() + "',TO_TIMESTAMP('" + URpass.getVALUE_DATE() + "','DD-MM-YYYY HH24:MI:SS'),'"
				+ URpass.getACTIVATED() + "')";

		Application_Properties.SERIAL = 1;

		int i = getJdbcTemplate().update(SQL);
		System.out.println(i + "|" + SQL);
		return i;
	}

	public String emailContent(String User_Name, String resetCode, String HashLink) {
		String strEmail = "";
		strEmail = "<html>";
		strEmail += "<body>";
		strEmail += "<table>";
		// strEmail+="<tr>";
		strEmail += "<tr><td>Dear " + User_Name + ",</td></tr>";
		strEmail += "<tr><td><br>Dataware House has received a request to reset the password for your account. If you did not initiate this request, please disregard.</td></tr>";
		strEmail += "<tr><td>Your password reset code is:" + resetCode + "</td></tr>";
		strEmail += "<tr><td>you can follow this <a href=" + HashLink
				+ ">link</a> and complete the form within 24 hours.</td></tr>";
		strEmail += "<tr><td>If you don't want to change your password or didn't request this, please ignore and delete this message.</td></tr>";
		strEmail += "<tr><td>Thank You,</td></tr>";
		strEmail += "</table>";
		strEmail += "</body>";
		strEmail += "</html>";

		return strEmail;
	}

	public UserForgetPassword getForgetPasswordInfo(int uid) {
		Application_Properties.SERIAL = 1;
		UserForgetPassword ufp = new UserForgetPassword();

		String sql = "SELECT * FROM USERS_FORGET_PASSWORD U WHERE U.USER_ID='" + uid
				+ "' and value_date=(select Max(value_date) from USERS_FORGET_PASSWORD)";
		System.out.println(sql);
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		// int rows=0;
		while (row.next()) {
			ufp.setUSER_ID(row.getInt("USER_ID"));
			ufp.setRESET_CODE_HASH(row.getString("RESET_CODE_HASH"));
			ufp.setEMAIL(row.getString("Email"));
			ufp.setPHONE(row.getString("phone"));
			ufp.setHASH_LINK(row.getString("hash_link"));
			ufp.setRESET_COUNT(row.getInt("Reset_count"));
			ufp.setVALUE_DATE(row.getString("Value_Date"));
		}
		return ufp;
	}

	public boolean verifyResetCodeByUid(String uid,String userInputResetCode) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		GenerateHashPassword ghPass=new GenerateHashPassword();
		String p=ghPass.generateStorngPasswordHash(uid);
		System.out.println(p);

		VerifyHashPassword verifyPass=new VerifyHashPassword();
		String hashIndb=getResetCodeHashByUid(uid);
		boolean matched =false;
		if(hashIndb!=null) {
			matched = verifyPass.validatePassword(userInputResetCode, hashIndb);
		}else {
			matched=false;
		}
		System.out.println(matched);

		return matched;
	}

	public String getResetCodeHashByUid(String uid) {
		Application_Properties.SERIAL = 1;
		// UserForgetPassword ufp=new UserForgetPassword();
		String hashStr = "";
		String sql = "SELECT * FROM USERS_FORGET_PASSWORD U WHERE U.User_ID='" + uid
				+ "' and activated='NO' and value_date=(select Max(value_date) from USERS_FORGET_PASSWORD)";
		System.out.println(sql);
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		// int rows=0;
		while (row.next()) {
			hashStr = row.getString("Reset_Code_Hash");
		}
		return hashStr;
	}
	
	public int updateUser_ForgetPasswordActivate(String uid,String Activate) {
		String SQL="update USERS_FORGET_PASSWORD U set "
				+ " U.ACTIVATED='"+Activate+"'"
				+ " WHERE U.USER_ID="+uid
				+ " AND value_date=(select Max(value_date) from USERS_FORGET_PASSWORD)";
		Application_Properties.SERIAL=1;
		System.out.println(SQL);
		int i=getJdbcTemplate().update(SQL);
		
		return i;
	}
	
	public boolean checkEmailDomain(String Email) {
		int index = Email.indexOf('@');
		// email = email.substring(0,index);
		Email = Email.substring(index + 1, Email.length());
		if(!Email.equalsIgnoreCase("princebank.com.kh") && !Email.equalsIgnoreCase("princeplc.com.kh")) {
			return false;
		}else {
			return true;
		}
	}
}

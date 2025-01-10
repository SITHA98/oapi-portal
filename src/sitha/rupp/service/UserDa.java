package sitha.rupp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.MTUser;
import sitha.rupp.password.GenerateHashPassword;
import sitha.rupp.password.VerifyHashPassword;

public class UserDa  extends GenericDaSupport{
	GenerateHashPassword genHashPass=new GenerateHashPassword();
	PrincebankComponent component=PrincebankComponent.getInstance();
	
	public int insertUser(MTUser u) throws NoSuchAlgorithmException, InvalidKeySpecException{
		String hashPassword=genHashPass.generateStorngPasswordHash(u.getPassWord());
		String SQL="Insert into Users(USER_ID,DESCRIPTION,DELETED,PASSWORD,CREATE_BY,CREATE_DATE,"
				+ " ALLOW_WORK_NON_BZ_DAYS,APPROVAL,LAST_PWD_CHANGE_DATE,branch_id,GROUP_ID,USER_NAME"
				+ " ,AUTHORIZED_BY,AUTHORIZED_DATE,EXPIRY_DATE,EMAIL,TILL_ID,REMARK,PHONE,APPROVAL_ROLE)values"
				+ " (USERS_ROLE_ID_SEQ.nextval,UPPER('"+u.getUserName()+"'),'N','"+hashPassword+"','"+u.getCreated_by()+"' "
				+ ",sysdate,'"+u.getAllowBusinessday()+"',"+u.getUseraprove()+", "
				+ " sysdate,"+u.getBrachId()+","+u.getGrId()+",UPPER('"+u.getUser_Name()+"'),"+u.getCreated_by()+" "
				+ ",sysdate,(SELECT to_date(ADD_MONTHS(SYSDATE,3)) FROM DUAL),'"+u.getEmail()+"','"+u.getTill_id()+"' "
				+ ",'"+u.getRemark()+"','"+u.getPhone()+"','"+u.getApproval_role()+"' ) ";
		
		Application_Properties.SERIAL=1;
		System.out.println("create new user : "+SQL);
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	
	public List<UserListModel> getUserId(String key){
		List<UserListModel> ulst=new ArrayList<>();
		String sql="SELECT * FROM users u "
				+ " LEFT JOIN GROUP_ROLE g on u.GROUP_ID=g.GR_ID "
				+ " INNER JOIN branch B ON B.BRANCH_ID=U.BRANCH_ID ORDER BY U.DESCRIPTION ASC";
				
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			UserListModel lstu=new UserListModel();
			lstu.setUserId(Integer.parseInt(row.getString("USER_ID")));
			lstu.setUserName(row.getString("DESCRIPTION"));
			lstu.setUserBranch(row.getString("NAMES"));
			lstu.setUserRole(row.getString("GR_NAME"));
			lstu.setUserDisable(row.getString("DELETED"));
			lstu.setUser_Name(row.getString("USER_NAME"));		
			ulst.add(lstu);
		}
		return ulst;
	}
	public MTUser getUserInfo(int uId){
		MTUser um=new MTUser();
		String sql="SELECT u.*, trim(regexp_substr(u.PHONE , '[^|]+', 1, 1)) AS PHONE_NUMBER, g.GR_NAME FROM USERS u, GROUP_ROLE g WHERE u.GROUP_ID = g.GR_ID AND u.USER_ID="+uId;
		Application_Properties.SERIAL=1;
		//System.out.println("user sql: "+sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			um.setUserName(row.getString("DESCRIPTION"));
			um.setBrachId(Integer.parseInt(row.getString("BRANCH_ID")));
			um.setGrId(Integer.parseInt(row.getString("GROUP_ID")));
			um.setUser_Name(row.getString("USER_NAME"));
			um.setPassWord(row.getString("PASSWORD"));
			um.setUseraprove(Integer.parseInt(row.getString("APPROVAL")));
			um.setEmail(row.getString("EMAIL"));
			um.setPhone(row.getString("PHONE"));
			um.setFirst_login(row.getString("first_login"));
			um.setTill_id(row.getString("TILL_ID"));
			um.setRemark(row.getString("REMARK"));
			um.setApproval_role(row.getInt("APPROVAL_ROLE"));
			um.setGorup_name(row.getString("GR_NAME"));
			um.setPhone_number(row.getString("PHONE_NUMBER"));
		}
		return um;
	}
	public int updatetUser(MTUser u,int id) throws NoSuchAlgorithmException, InvalidKeySpecException{
		/*
		String SQL="update USERS U set U.DESCRIPTION='"+u.getUserName()+"',"
				+ " U.PASSWORD='"+u.getPassWord()+"',U.ALLOW_WORK_NON_BZ_DAYS='"+u.getAllowBusinessday()+"',"
				+ " U.LAST_PWD_CHANGE_DATE=sysdate,U.branch_id="+u.getBrachId()+",U.GROUP_ID="+u.getGrId()+","
				+ " U.USER_NAME='"+u.getUser_Name()+"',U.APPROVAL="+u.getUseraprove()+""
				+ " WHERE U.USER_ID="+id; // smey
		*/
		
		String sql="INSERT INTO USERS_HIS SELECT USER_HIS_ID_SEQ.NEXTVAL,A.*,sysdate FROM USERS A WHERE A.USER_ID="+u.getUserId();
		getJdbcTemplate().update(sql);
		System.out.println("++++++++++++ Backup users history ++++++++++++");
		System.out.println(sql);
		String hashPassword=genHashPass.generateStorngPasswordHash(u.getPassWord());
		
		String SQL="update USERS U set U.DESCRIPTION=UPPER('"+u.getUserName()+"'),"
				+ " U.PASSWORD='"+hashPassword+"',U.ALLOW_WORK_NON_BZ_DAYS='"+u.getAllowBusinessday()+"',"
				+ " U.LAST_PWD_CHANGE_DATE=sysdate,U.branch_id="+u.getBrachId()+",U.GROUP_ID="+u.getGrId()+","
				+ " U.USER_NAME=UPPER('"+u.getUser_Name()+"'),U.APPROVAL="+u.getUseraprove()+""
				+ " ,TILL_ID = '"+u.getTill_id()+"' "
				+ " ,REMARK = '"+u.getRemark()+"', PHONE = '"+u.getPhone()+"', APPROVAL_ROLE = '"+u.getApproval_role()+"' "
				+ " WHERE U.USER_ID="+id;
		Application_Properties.SERIAL=1;
		System.out.println(SQL);
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	
	public int updatetUserForResetPassword(String newPassword,String uid) throws NoSuchAlgorithmException, InvalidKeySpecException{
		String hashPassword=genHashPass.generateStorngPasswordHash(newPassword);
		String SQL="update USERS U set "
				+ " U.PASSWORD='"+hashPassword+"',"
				+ " U.LAST_PWD_CHANGE_DATE=sysdate"
				+ " WHERE U.USER_ID="+uid;
		Application_Properties.SERIAL=1;
		System.out.println(SQL);
		int i=getJdbcTemplate().update(SQL);
		
		return i;
	}
	public int getUserName(String name){
		String sql="select u.USER_ID from users u WHERE UPPER(U.USER_NAME)=UPPER('"+name+"')";
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("USER_ID"));
		}
		return id;
	}
	public int getUserNameId(String name,int uId){
		String sql="select u.USER_ID from users u WHERE u.USER_ID="+uId+" and UPPER(U.USER_NAME)=UPPER('"+name+"')";
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int id=0;
		while(row.next()){
			id=Integer.parseInt(row.getString("USER_ID"));
		}
		return id;
	}
	
	public int getUserByNameEmail(String userName,String Email) {
		int id=0;
		String sql="select u.USER_ID from users u WHERE UPPER(u.USER_NAME)=UPPER('"+userName+"') and U.Email='"+Email+"'";
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			id=Integer.parseInt(row.getString("USER_ID"));
		}
		return id;
	}
	public int deleteUser(int id){
		String SQL="update USERS U set U.DELETED='Y' WHERE U.USER_ID="+id;
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	public int enableUser(int id){
		String SQL="update USERS U set U.DELETED='N' WHERE U.USER_ID="+id;
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	public MTUser getUserById(int userId){
//		String SQL="SELECT USER_ID,DESCRIPTION,USER_NAME,PASSWORD,BRANCH_ID,GROUP_ID,ALLOW_WORK_NON_BZ_DAYS,CREATE_BY,CREATE_DATE,LAST_PWD_CHANGE_DATE,LIMITS,DELETED FROM USERS WHERE DELETED=0 AND USER_ID="+userId;
		String SQL="SELECT * FROM USERS WHERE DELETED='N' AND USER_ID="+userId;
		MTUser u=new MTUser();
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(SQL);
		while(row.next()){
			u.setUserId(Integer.parseInt(row.getString("USER_ID")));
			u.setUser_Name(row.getString("DESCRIPTION"));
			u.setUserName(row.getString("USER_NAME"));
			u.setBrachId(Integer.parseInt(row.getString("BRANCH_ID")));
			u.setGrId(Integer.parseInt(row.getString("GROUP_ID")));
			u.setEmail(row.getString("email"));
			u.setPhone(row.getString("phone"));
		}
		return u;
	}
	public MTUser getUserByUserNameAndEmail(String userName,String email){
		String SQL="SELECT * FROM USERS WHERE DELETED='N' AND UPPER(USER_NAME)=UPPER('"+userName+"') and email='"+email+"'";
		MTUser u=new MTUser();
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(SQL);
		while(row.next()){
			u.setUserId(Integer.parseInt(row.getString("USER_ID")));
			//u.setUser_Name(row.getString("DESCRIPTION"));
			//u.setUserName(row.getString("USER_NAME"));
			//u.setBrachId(Integer.parseInt(row.getString("BRANCH_ID")));
			//u.setGrId(Integer.parseInt(row.getString("GROUP_ID")));
			u.setEmail(row.getString("email"));
			u.setPhone(row.getString("phone"));
		}
		return u;
	}
	public int changeFirstLogin(MTUser u){
		int i=0;
		String sql="INSERT INTO USERS_HIS SELECT USER_HIS_ID_SEQ.NEXTVAL,A.*,sysdate FROM USERS A WHERE A.USER_ID="+u.getUserId();
		getJdbcTemplate().update(sql);
		sql="UPDATE USERS SET FIRST_LOGIN='N',EXPIRY_DATE='',PASSWORD='"+u.getPassWord()+"' WHERE USER_ID="+u.getUserId();
		getJdbcTemplate().update(sql);
		
		return i;
	}
	public int checkUserExpiry(){
		String sql="UPDATE USERS A SET A.ENABLED='Y' WHERE A.EXPIRY_DATE IS NOT NULL and NVL(SYSDATE-A.EXPIRY_DATE,0)>=0";
		return getJdbcTemplate().update(sql);
	}
	public boolean isExist(MTUser u,String pwd){
		String sql ="Select count(*)xxx from users_his where user_id="+u.getUserId()+" AND PASSWORD='"+pwd+"' and rownum<=6 order by user_id desc";
		System.out.println("+++++++++ Check history records +++++++++\n"+sql);
		int count=component.getScarlare(sql);
		if( count > 0 && count <= 5 ){
			System.out.println("+++++++++ This Password Already used +++++++++");
			return false;
		}else return true;
	}
	public boolean isExistcur(int uId,String pwd) throws NoSuchAlgorithmException, InvalidKeySpecException{
		Application_Properties.SERIAL=1;
		String sql="select * from users u where u.USER_ID="+uId +" order by 1 desc ";
		Application_Properties.SERIAL=1;
		System.out.println(sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		boolean help = false;
		while(row.next()){
			String dbPassword=row.getString("PASSWORD");
			VerifyHashPassword verifyPass=new VerifyHashPassword();
			boolean matched = verifyPass.validatePassword(pwd, dbPassword);
			if(matched){
				help=true;
				return help;
			}else{
				help=false;
			}
		}
		return help;
	}
	public boolean isUsersExist(int uId,String pwd) throws NoSuchAlgorithmException, InvalidKeySpecException{
		Application_Properties.SERIAL=1;
		boolean help = isExistcur(uId,pwd);
		if (help){
			System.out.println("+++++ This is current password +++++");
			return help;
		}
		String sql="select * from ( select * from users_his u where u.USER_ID="+uId +" order by 1 desc ) where rownum < 5 order by 1 asc";
		Application_Properties.SERIAL=1;
		System.out.println(sql);
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int i=0;
		while(row.next()){
			String dbPassword=row.getString("PASSWORD");
			VerifyHashPassword verifyPass=new VerifyHashPassword();
			boolean matched = verifyPass.validatePassword(pwd, dbPassword);
			if(matched){
				System.out.println("++++ This Password is already used ++++xxx++++ Cycle ["+i+"] ++++");
				help=true;
				return help;
			}else{
				System.out.println("++++ This Password is can use ++++xxx++++ Cycle ["+i+"] ++++");
				help=false;
			}
			i++;
		}
		return help;
	}
	
	public boolean isAlreadyUsed(int userId,String newpass) throws NoSuchAlgorithmException, InvalidKeySpecException{
		List<MTUser>ls=null;// initUsersExist(userId,newpass);
		//System.out.println("ReadDbPassword="+ReadDbPassword);
		boolean help=true;
		VerifyHashPassword verifyPass=new VerifyHashPassword();
		for(int i=0;i<ls.size();i++){
			MTUser u=ls.get(i);
			System.out.println("["+(i+1)+"]  "+newpass +" vs "+u.getPassWord());
			boolean matched = verifyPass.validatePassword(newpass, u.getPassWord());
			System.out.println("It is matched password : "+matched);
			if(matched){help=false;return help;}
			//else help=true;
		}
		return help;
	}
	public int lockedUser(String USER_NAME){
		int i=0;
		String sql="UPDATE USERS SET ENABLED='Y' WHERE upper(trim(USER_NAME))=upper('"+USER_NAME.trim()+"')";
		i=getJdbcTemplate().update(sql);
		return i;
	}
	public String isUserLocked(int userId) throws Exception{
		String sql="SELECT ENABLED FROM USERS WHERE USER_ID="+userId;
		System.out.println("Querying locked account \n"+sql);
		return component.getScalareString(sql);
	}
}

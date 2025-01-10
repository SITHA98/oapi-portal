package sitha.rupp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.Student;
import sitha.rupp.password.GenerateHashPassword;

public class StudentService  extends GenericDaSupport{
	
	GenerateHashPassword genHashPass=new GenerateHashPassword();
	PrincebankComponent component=PrincebankComponent.getInstance();
	
	public int importStudent(Student student, String partnerId) throws NoSuchAlgorithmException, InvalidKeySpecException{
		String sql="INSERT INTO STUDENT" + 
				"(STD_ID, STD_NAME, AMOUNT,CRT_DATE,PARTNER_ID)" + 
				" VALUES('"+student.getStudentId()+"', '"+student.getStudentName()+"', '"+student.getAmount()+"',sysdate,'"+student.getGroupId()+"')";
		System.out.println(sql);
		Application_Properties.SERIAL=1;
		int i=getJdbcTemplate().update(sql);
		return i;
	}

	private  List<Student> findStudentbyPartnerId(String partnerId){
		String sql = "SELECT * FROM STUDENT WHERE PARTNER_ID = " + partnerId;
		SqlRowSet rowSet=getJdbcTemplate().queryForRowSet(sql);
		List<Student> studentList = new ArrayList<>();
		while(rowSet.next()) {
			Student student = new Student();
			String studentId = rowSet.getString("STD_ID");
			String studentName = rowSet.getString("STD_NAME");
			String amount = rowSet.getString("AMOUNT");
			String studentCreateDate = rowSet.getString("CRT_DATE");
			student.setStudentId(studentId);
			student.setStudentName(studentName);
			student.setAmount(Double.valueOf(amount));
			student.setCreateDate(studentCreateDate);
			student.setStudentId(studentId);
			studentList.add(student);
		}
		
	return studentList;
	}
	
	public void deleteUser(String partnerId) {				
			String sql = "DELETE FROM STUDENT WHERE PARTNER_ID="+partnerId;
			Application_Properties.SERIAL=1;
			getJdbcTemplate().update(sql);
	}
	
	public void backupUser(String partnerId) {
		List<Student> studentList = findStudentbyPartnerId(partnerId);		
		for(Student studentBackup: studentList) {
			String sqlBackup = "INSERT INTO HISTORY_STUDENT (STD_ID, STD_NAME, AMOUNT, CRT_DATE, BACKUP_DATE, PARTNER_ID) "
					+ "VALUES('"+studentBackup.getStudentId()+"', '"+studentBackup.getStudentName()+"', '"+studentBackup.getAmount()+"', '"+String.valueOf(studentBackup.getCreateDate())+"', sysdate, '"+partnerId+"')";			
			getJdbcTemplate().update(sqlBackup);
		}
		deleteUser(partnerId);
	}
	
	
}

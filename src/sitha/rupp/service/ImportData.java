package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.Student;
import sitha.rupp.password.GenerateHashPassword;

public class ImportData  extends GenericDaSupport{
	GenerateHashPassword genHashPass=new GenerateHashPassword();
	PrincebankComponent component=PrincebankComponent.getInstance();
	public List<Student> getStudent(Integer groupId){
		List<Student> ulst=new ArrayList<>();
		String sql="SELECT * FROM STUDENT WHERE PARTNER_ID="+groupId;	
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			Student student = new Student();
			student.setStudentId(row.getString("STD_ID"));
			student.setStudentName(row.getString("STD_NAME"));
			student.setAmount(Double.valueOf(row.getString("AMOUNT")));
			ulst.add(student);
		}
		return ulst;
	}
}

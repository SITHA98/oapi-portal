package sitha.rupp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sitha.rupp.model.Student;
import sitha.rupp.service.StudentService;


@Controller
public class ImportController extends GenericController{

	@RequestMapping(value="/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String submit(@RequestParam MultipartFile file, @RequestParam("groupId") Integer groupId, ModelMap modelMap) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	 
		ApplicationContext context=new ClassPathXmlApplicationContext("Spring_Bean.xml");
		StudentService studentService=(StudentService) context.getBean("studentService");		
		
	    //modelMap.addAttribute("file", file);
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet datatypeSheet = (Sheet) workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		Set<String> studentSet = new HashSet<>();
		List<Student> studentList = new ArrayList<>();
		while (iterator.hasNext()) {
		//for(int i=0;i<3;i++) {
			Row currentRow = iterator.next();
			int rowNumber = currentRow.getRowNum();
			
			Student student = new Student();
			student.setGroupId(groupId);
			if(rowNumber!=0) {
				Iterator<Cell> cellIterator = currentRow.iterator();								
				for(int i=0;i<3;i++) {	  					
	                Cell currentCell = cellIterator.next();	                	               
	                if(currentCell.getColumnIndex() == 0) {	                	
	                	student.setStudentId(currentCell.getStringCellValue());	                	
	                }else if(currentCell.getColumnIndex() == 1) {	                
	                	student.setStudentName(currentCell.getStringCellValue());
	                }else {	                					
	                	student.setAmount(currentCell.getNumericCellValue() );
	                }    	                
	            }
				studentSet.add(student.getStudentId());
				studentList.add(student);		   
	            //studentService.importStudent(student,String.valueOf(groupId));	            
			}
         
		}
		
		if(studentList.size() > studentSet.size()) {			
			workbook.close();
			return "Duplicate ID";
		}else {	
			studentService.backupUser(String.valueOf(groupId));
			for(Student student: studentList) {
				studentService.importStudent(student,String.valueOf(groupId));
			}
			
		}
		workbook.close();
		
	    return "success";
	}
	
	
}

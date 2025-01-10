package sitha.rupp.spreedsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public Excel() {}
	
	public static void modify(String file,int sheetIndex,int cell,int row,String val) throws IOException{
		
		FileInputStream fis = new FileInputStream(new File(file));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		
		XSSFRow row1 = sheet.getRow(row);
		XSSFCell cell1 = row1.getCell(cell);
		
		cell1.setCellValue((String)val);
		
		//cell1.setCellValue(sheet.getRow(row).getCell(cell));
		
		fis.close();
		FileOutputStream fos = new FileOutputStream(new File(file));
		workbook.write(fos);
		fos.close();
		System.out.println("Done");
	}
}

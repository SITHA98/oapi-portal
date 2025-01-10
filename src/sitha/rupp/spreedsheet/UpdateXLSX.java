package sitha.rupp.spreedsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateXLSX {

	public static void main(String[] args) throws IOException {
		/*FileInputStream fis = new FileInputStream(new File("D:\\xlsx\\test.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(1);
		XSSFRow row1 = sheet.getRow(10);
		XSSFCell cell1 = row1.getCell(1);
		cell1.setCellValue("Mahesh");
		XSSFRow row2 = sheet.getRow(11);
		XSSFCell cell2 = row2.getCell(1);
		cell2.setCellValue("Reaksmey");
		fis.close();
		FileOutputStream fos = new FileOutputStream(new File("D:\\xlsx\\test.xlsx"));
		workbook.write(fos);
		fos.close();
		System.out.println("Done");*/
		String file="C:/Prince Bank/App/princebank-reports-engine/2_COA_Monthly.xlsx";
		for(int i=13;i<=21;i++){
			Excel.modify(file, 0,0, i,1000+"");
		}
	}

}

package sitha.rupp.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OpenFileForReadWrite {
	//String fileName = "";
	public void writeAppendStrToFile(String fileName, boolean appendYN, String str) {
		try {
			// File file = new File("d:\\fileName.txt");
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file, appendYN);// true mean append
			// fileWriter.write("str to write into a file");
			fileWriter.write(str);
			fileWriter.write("\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void OpenFileForRead(String filepath) {
		BufferedReader br;
		String curline;
		try {
			br = new BufferedReader(new FileReader(filepath));

			while ((curline = br.readLine()) != null) {
				System.out.println(curline);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean CheckisEmptyFile(String filepath) {
		boolean empty = false;
		File newFile = new File(filepath);
		
		String str="";
		if (newFile.length() == 0) {
			str=newFile.getName()+ "| File no data";
			//System.out.println(str);
			//wFile.writeAppendStrToFile("d:\\file1.txt", str);
			empty = true;
		}else {
			str=newFile.getName()+ "| File has data";
			//System.out.println(str);
			//wFile.writeAppendStrToFile("d:\\file1.txt", str);
			empty=false;
		}
		return empty;
	}
	/*
	public void CreateEmptyExcelFile(String filepath, String AtmissPosFolder, String folderDate, String fileName) throws IOException {
		String outFolder = "";
		
		outFolder = FolderFileStructure.outputFolder + AtmissPosFolder + "\\" + folderDate;
		String outFileName=outFolder + "\\" + fileName + FolderFileStructure.fileExtXls;
		//this.fileName = fileName;
		//System.out.println(outFolder);
		
		File createFolder = new File(outFolder);
		createFolder.mkdirs();
		
		try {
			//Create Blank workbook
		      XSSFWorkbook workbook = new XSSFWorkbook();		      
		      XSSFSheet sheet = workbook.createSheet("Sheet1");
		      //Create file system using specific name
		      FileOutputStream out;
			out = new FileOutputStream(new File(outFileName));
			//write operation workbook using file out object
		      workbook.write(out);
		      out.close();
		      //System.out.println("createworkbook.xlsx written successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      
	}
	*/
	public void WriteDataFromListToExcel(String outFileName, ArrayList<String> alist1) {
		
		/* Create Workbook and Worksheet */
        //HSSFWorkbook my_workbook = new HSSFWorkbook();
        //HSSFSheet my_sheet = my_workbook.createSheet("Cell Font");
        /* Get access to HSSFCellStyle */
        //HSSFCellStyle my_style = my_workbook.createCellStyle();
        /* Create HSSFFont object from the workbook */
        
        //XSSFFont my_font=my_workbook.createFont();
        /* Set the font name to Verdana */
        //my_font.setFontName("Verdana");
        /* Also make the font color to RED */
        //my_font.setColor(HSSFFont.COLOR_RED);
        /* attach the font to the style created earlier */
        //my_style.setFont(my_font);
        
		
		String header1 = alist1.get(0);
		String header2[] = header1.split("\\|");
		int col = header2.length;
		int r = alist1.size();
		String[][] a = new String[r][col];

		for (int i = 0; i < r; i++) {
			for (int c = 0; c < col; c++) {
				String cols[] = alist1.get(i).split("\\|");
				a[i][c] = cols[c];
				//System.out.print(a[i][c] + " ");
			}
			//System.out.println();
		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		XSSFFont my_font = workbook.createFont();
		my_font.setFontName("Courier New");
		my_font.setFontHeight(10);
		
		XSSFCellStyle my_style= workbook.createCellStyle();
		my_style.setFont(my_font);
		
		int rowNum = 0;
		System.out.println("Creating excel");

		for (String[] datatype : a) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					cell.setCellStyle(my_style);
				}
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(outFileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}

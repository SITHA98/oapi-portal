package sitha.rupp.view;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JRExporter;
//import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
//import net.sf.jasperreports.engine.export.JRHtmlExporter;
//import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
//import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRProperties;
//import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import sitha.rupp.configuration.GenericDaSupport;

public class ReportEngine extends GenericDaSupport {
	//JasperReport jasperReport = (JasperReport) JRLoader.loadObject("");
	JRExporter exporter=null;
	JasperPrint jasperPrint=null;
	
	public void initReport(String ReportUrl,HashMap ReportPara) throws JRException, SQLException{
		jasperPrint=JasperFillManager.fillReport(ReportUrl,ReportPara,getJdbcTemplate().getDataSource().getConnection());
	}
	public void initReport(String ReportUrl,HashMap ReportPara,JRBeanCollectionDataSource jrBeanCollectionDataSource) throws JRException, SQLException{
		jasperPrint=JasperFillManager.fillReport(ReportUrl,ReportPara,jrBeanCollectionDataSource);
	}
	public void initReport(String ReportUrl,HashMap ReportPara,JRBeanArrayDataSource jrBeanArrayDataSource) throws JRException, SQLException{
		jasperPrint=JasperFillManager.fillReport(ReportUrl,ReportPara,jrBeanArrayDataSource);
	}
	public void exportHtml(ServletOutputStream out,HttpServletRequest request){
		 exporter = new JRHtmlExporter();
		 exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "UTF-8");
		 exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		 //exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
		 exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		 //exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
         exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES, Boolean.TRUE);
         exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.5f);
         //exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.5f);
         request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint); 
         exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "images?image="); 
		 exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, out);
		 //exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, out);
		 
		 try {
			 
			// System.out.println("export to html");
			 exporter.exportReport();
			} catch (JRException e) {
				 Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, e);
			}/*finally{
				if(out!=null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}*/
	}
	public void exportHtml2(ServletOutputStream out,HttpServletRequest request){
		 exporter = new JRHtmlExporter();
		 exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "UTF-8");
		 exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		 //exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
		 exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		 //exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
         exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES, Boolean.TRUE);
         exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.5f);
         request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint); 
         exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "images?image="); 
		 exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, out);
		 //exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, out);
		 
		 try {
			 exporter.exportReport();
			} catch (JRException e) {
				 //Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, e);
			}/*finally{
				if(out!=null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}*/
	}
	public void exportExcel(ServletOutputStream out,HttpServletRequest request, HttpServletResponse response,String strReportName){
		exporter=new JRXlsxExporter();
		
		System.out.println("Report Name : "+jasperPrint.getName());
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ strReportName + ".xlsx");
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
				Boolean.TRUE);
		exporter.setParameter(
				JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.FALSE);
		exporter.setParameter(
				JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
				Boolean.TRUE);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		//exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, strSaveReportPath + "/" + strReportName + ".xlsx");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void exportPDF(ServletOutputStream out){
		exporter=new JRPdfExporter();
		
		JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1250");
        
		//JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "Cp1250");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		//exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, strSaveReportPath + "/" + strReportName + ".pdf");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		try {
			exporter.exportReport();
			//JasperExportManager.exportReportToPdfStream(jasperPrint,out);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void exportPDF2(ServletOutputStream out, HttpServletRequest request) throws UnsupportedEncodingException{
//		String path = this.getClass().getClassLoader().getResource("").getPath();
		
		HttpSession session = request.getSession();
		
		ServletContext servletContext = session.getServletContext();
		exporter=new JRPdfExporter();

		JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
        
		//JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "Cp1250");
//		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		
//		JRProperties.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "D:\\SITHA\\9-MY-JAVA\\MY_JAVA\\oapi-portal\\WebContent\\WEB-INF\\reports\\khmer-unicode\\KhmerOS_0.ttf");
//		JRProperties.setProperty(JRFont.DEFAULT_PDF_ENCODING, "Identity-H");
//		JRProperties.setProperty(JRFont.DEFAULT_PDF_EMBEDDED, "TRUE");
		
		//JRProperties.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, servletContext.getRealPath("WEB-INF/reports/rpt-0005-fire-and-engineering/times.ttf"));
//		JRProperties.setProperty(JRFont.DEFAULT_FONT_NAME, "D:/SITHA/9-MY-JAVA/MY_JAVA/oapi-portal/WebContent/WEB-INF/reports/rpt-0005-fire-and-engineering/timesbd.ttf");
//		JRProperties.setProperty(JRFont.DEFAULT_FONT_NAME, "D:/SITHA/9-MY-JAVA/MY_JAVA/oapi-portal/WebContent/WEB-INF/reports/rpt-0005-fire-and-engineering/times.ttf");
//		JRProperties.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "D:/SITHA/9-MY-JAVA/MY_JAVA/oapi-portal/WebContent/WEB-INF/reports/rpt-0005-fire-and-engineering/timesbi.ttf");
//		JRProperties.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "D:/SITHA/9-MY-JAVA/MY_JAVA/oapi-portal/WebContent/WEB-INF/reports/rpt-0005-fire-and-engineering/times.ttf");
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); //okay for webview
		
//		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, strSaveReportPath + "/" + "strReportName" + ".pdf");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		
		try {
			exporter.exportReport();
//			JasperExportManager.exportReportToPdfStream(jasperPrint,out);
			// Fill the Jasper Report
	         // JasperPrint jasperPrint 
	         // = JasperFillManager.fillReport(jasperReport, parameters, connection);
	 
	          // Creation of the HTML Jasper Reports
	          //JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Upload/MyJasperReport.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, e);
		}
		System.out.println("report viewer done");
	}
	
	public void exportPDFCreatePdfFile(ServletOutputStream out, HttpServletRequest request,String path) throws UnsupportedEncodingException, JRException{
//		String path = this.getClass().getClassLoader().getResource("").getPath();
		exporter=new JRPdfExporter();
		JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "Cp1250");	
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); //okay for webview
		System.out.println("path:"+path);
//		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		
		try {
			exporter.exportReport();
//			JasperExportManager.exportReportToPdfStream(jasperPrint,out);
			// Fill the Jasper Report
	         // JasperPrint jasperPrint 
	         // = JasperFillManager.fillReport(jasperReport, parameters, connection);
	          // Creation of the HTML Jasper Reports
			JasperExportManager.exportReportToPdfFile(jasperPrint, path);
		} catch (JRException e) {
			// TODO Auto-generated catch block
//			Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, e);
		}
		System.out.println("report viewer done");
	}
}

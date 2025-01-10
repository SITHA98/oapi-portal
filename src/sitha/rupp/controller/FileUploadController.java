package sitha.rupp.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.helper.ClsHelper;
import sitha.rupp.model.SYS_EVENT_LOG;

import sitha.rupp.model.rpt_0014_PA00_EndorsementModel;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.UserDa;

import sitha.rupp.service.rpt_0014_PA00_endorsementServices;
import sitha.rupp.view.ReportEngine;

@Controller
public class FileUploadController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	UserDa userDa = (UserDa) context.getBean("userDa");
	// private String strImagePath = "images/report_logo.jpg";
	Gson gson = new Gson();

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String upload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		String path = session.getServletContext().getRealPath("d:/");
		String filename = file.getOriginalFilename();

		System.out.println(path + " " + filename);
		try {
			byte barr[] = file.getBytes();

			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + filename));
			bout.write(barr);
			bout.flush();
			bout.close();
			return "completed";
		} catch (Exception e) {
			System.out.println(e);
			return "error";
		}
//		return new ModelAndView("upload-success", "filename", path + "/" + filename);
	}
}

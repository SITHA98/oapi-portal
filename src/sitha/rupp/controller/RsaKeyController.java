package sitha.rupp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sitha.rupp.model.RsaKey;
import sitha.rupp.service.PrincebankComponent;
import sitha.rupp.service.RsaKeyDa;

@Controller
@RequestMapping(value = "/partnership")
public class RsaKeyController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	PrincebankComponent component = (PrincebankComponent) context.getBean("component");
	private final Logger logger = LoggerFactory.getLogger(PPWSATxnController.class);

	@ResponseBody
	@RequestMapping(value = "/rsa-key", method = RequestMethod.POST)
	int rsa_key(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("pID") int pID,
			@RequestParam("partner_id") int partner_id,
			@RequestParam("partner_name") String partner_name,
			@RequestParam("public_key") String public_key,
			@RequestParam("created_by") int created_by
			) 
	throws Exception {
		int check = 0;
		if (partner_name == null || public_key == null) { return check; }
		try {
			RsaKey r= new RsaKey();
			r.setPartner_id(partner_id);
			r.setPartner_name(partner_name);
			r.setPublic_key(public_key);
			r.setCreate_by(created_by);
			RsaKeyDa rs = new RsaKeyDa();
			if (pID==0) {			
				/*if (rs.getRowNum( partner_id)==0) {
					check = rs.insertRsaKey(r);
				}else {
					if (rs.disableRsaKey(r)>0) {
						check = rs.insertRsaKey(r);
					}	
				}*/
				check = rs.insertRsaKey(r);
			}else {
				check = rs.updateRsaKey(r);
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage());		
		}
		return check;
	}
	
	@ResponseBody
	@RequestMapping(value = "/rsa-key-approve", method = RequestMethod.POST)
	int rsa_key_approve(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("partner_id") int partner_id,
			@RequestParam("otp") String otp,
			@RequestParam("approval_by") int approval_by
			) 
	throws Exception {
		int check = 0;
		if (otp == null) { return check; }
		try {
			if (partner_id!=0) {
				RsaKey r = new RsaKey();
				r.setPartner_id(partner_id);
				r.setCreate_by(approval_by);
				r.setOtp(otp);
				RsaKeyDa rs = new RsaKeyDa();
				check = rs.confirmOTP(r);
				if (check==1) {					
					check = rs.disableRsaKey(r);
					check=rs.updateApproval(r);
				}
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage());		
		}
		return check;
	}

	@ResponseBody
	@RequestMapping(value = "/rsa-key-new", method = RequestMethod.POST)
	int rsa_key_new(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("partner_id") int partner_id,
			@RequestParam("partner_name") String partner_name,
			@RequestParam("p_number") String p_number,
			@RequestParam("public_key") String public_key,
			@RequestParam("created_by") int created_by

			) 
	throws Exception {
		int check = 0;
		if (partner_name == null || public_key == null) { return check; }
		try {
			RsaKey r= new RsaKey();
			r.setPartner_id(partner_id);
			r.setPartner_name(partner_name);
			r.setPublic_key(public_key);
			r.setCreate_by(created_by);
			r.setPhone_number(p_number);
			RsaKeyDa rs = new RsaKeyDa();		
			/*if (rs.getRowNum(partner_id)==0) {
				check = rs.insertRsaKey(r);			
			}else {
				if (rs.disableRsaKey(r)>0) {
					check = rs.insertRsaKey(r);
				}	
			}*/
			check = rs.insertRsaKey(r);
		}catch (Exception ex) {
			logger.error(ex.getMessage());		
		}
		return check;
	}
	
	@ResponseBody
	@RequestMapping(value = "/rsa-key-cancel", method = RequestMethod.POST)
	int rsa_key_cancel(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("partner_id") int partner_id,
			@RequestParam("approval_by") int approval_by
			) 
	throws Exception {
		int check = 0;
		try {
			if (partner_id!=0) {
				RsaKey r = new RsaKey();
				r.setPartner_id(partner_id);
				r.setCreate_by(approval_by);
				RsaKeyDa rs = new RsaKeyDa();
				check = rs.cancelRsaKey(r);
			}
		}catch (Exception ex) {
			logger.error(ex.getMessage());		
		}
		return check;
	}

}

package sitha.rupp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sitha.rupp.model.MTGroup;
import sitha.rupp.model.MTGroupDetail;
import sitha.rupp.service.GroupDa;

@Controller
@RequestMapping(value = "/group")
public class MTGroupController extends GenericController {
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring_Bean.xml");
	GroupDa grDa = (GroupDa) context.getBean("groupDa");

	@ResponseBody
	@RequestMapping(value = "/insertGroupDetail", method = RequestMethod.POST)
	String insertGroupDetail(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("txtgrId") String grId, @RequestParam("txtGrName") String grName,
			@RequestParam("txtgr_id") int gr_id, @RequestParam("userId") int userId) throws Exception, Exception {
		MTGroupDetail grd = new MTGroupDetail();
		String num = grId.replaceAll("[^\\d,]", "");
		String[] numm = num.split(",");
		int i = 0;
		String retString = "";
		MTGroup gm = new MTGroup();
		gm.setGr_Name(grName);
		gm.setGr_CreateBy(userId);
		gm.setUpdateBy(userId);
		gm.setGr_Status(0);
		System.out.println(grName);
		System.out.println(gr_id);
		if (gr_id > 0) {
			int gn = grDa.getGroupNameId(grName, gr_id);
			if (gn > 0) {
				grDa.updateGroupname(gm, gr_id);
				grDa.deleteGroupDetail(gr_id);
				for (int j = 0; j < numm.length; j++) {
					grd.setGrId(gr_id);
					grd.setMenuId(Integer.parseInt(numm[j]));
					i = grDa.updateGroupDetail(grd, gr_id);
					if (i > 0) {
						retString = "2";
					}
				}
			} else {
				gn = grDa.getGroupName(grName);
				if (gn > 0) {
					retString = "3";
				} else {
					grDa.updateGroupname(gm, gr_id);
					grDa.deleteGroupDetail(gr_id);
					for (int j = 0; j < numm.length; j++) {
						grd.setGrId(gr_id);
						grd.setMenuId(Integer.parseInt(numm[j]));
						i = grDa.updateGroupDetail(grd, gr_id);
						if (i > 0) {
							retString = "2";
						}
					}
				}
			}
		} else {
			int gn = grDa.getGroupName(grName);
			if (gn > 0) {
				return retString = "3";
			} else {
				grDa.insertGroupName(gm);
				int gd = (grDa.getLastGroupRoleId());
				for (int j = 0; j < numm.length; j++) {
					grd.setGrId(gd);
					grd.setMenuId(Integer.parseInt(numm[j]));
					i = grDa.insertGroupDetail(grd);
					if (i > 0) {
						retString = "1";
					}
				}
			}
		}
		return retString;
	}

	@ResponseBody
	@RequestMapping(value = "/disableGroup", method = RequestMethod.POST)
	String disableGroup(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("id") int grId) throws Exception, Exception {
		int i = grDa.disableGroup(grId);
		if (i > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/enableGroup", method = RequestMethod.POST)
	String enableGroup(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,
			@RequestParam("id") int grId) throws Exception, Exception {
		int i = grDa.enableGroup(grId);
		if (i > 0) {
			return "1";
		} else {
			return "0";
		}
	}

}

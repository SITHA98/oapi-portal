package sitha.rupp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.MTMenu;


public class MenuDa extends GenericDaSupport{
	
	public List<MTMenu> getMenuall(int gr){
		List<MTMenu> me=new ArrayList<>();
		String sql="select * from GROUP_DETAIL gd "
				+ " inner join menu m on m.MENU_ID=gd.MENU_ID where gd.GR_ID="+gr
				+ " order by m.for_order ASC ";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			MTMenu menu=new MTMenu();
			menu.setMenu_name(row.getString("MENU_NAME"));
			menu.setMenu_description(row.getString("DESCRIPTION"));
			menu.setMenu_id(Integer.parseInt(row.getString("MENU_ID")));
			menu.setMenu_main(Integer.parseInt(row.getString("MAIN")));
			menu.setMenu_icon(row.getString("ICON"));
			me.add(menu);
		}
		return me;
	}
	
	public List<MTMenu> getMenu(int user,int id,HttpServletResponse response) throws IOException{
		try{
		List<MTMenu> me=new ArrayList<>();
		String sql="select * from GROUP_DETAIL gd "
				+ " inner join menu m on m.MENU_ID=gd.MENU_ID where gd.GR_ID="+user
				+ " and m.MAIN="+id +" ORDER BY m.for_order ASC ";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			MTMenu menu=new MTMenu();
			menu.setMenu_name(row.getString("MENU_NAME"));
			menu.setMenu_description(row.getString("DESCRIPTION"));
			menu.setMenu_id(Integer.parseInt(row.getString("MENU_ID")));
			menu.setMenu_main(Integer.parseInt(row.getString("MAIN")));
			menu.setMenu_icon(row.getString("ICON"));
			me.add(menu);
		}
//		System.out.println("Menu:"+me.toString());
		return me;
		}catch(Exception e){
			response.sendRedirect("/login");
			return null;
		}
	}
	
}

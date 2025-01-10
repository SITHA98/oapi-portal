package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.MTGroup;
import sitha.rupp.model.MTGroupDetail;
import sitha.rupp.model.MTMenu;

public class GroupDa extends GenericDaSupport{
	
	public List<MTGroup> getGroupList(){
		List<MTGroup> gr=new ArrayList<>();
		String sql="SELECT g.*, (SELECT u.DESCRIPTION FROM users u WHERE u.USER_ID = g.CREATE_BY) AS CREATED_BY FROM GROUP_ROLE g ORDER BY 1";
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			MTGroup grm=new MTGroup();
			grm.setGr_Id(Integer.parseInt(row.getString("GR_ID")));
			grm.setGr_Name(row.getString("GR_NAME"));
			grm.setGr_Status(Integer.parseInt(row.getString("STATUS")));
			grm.setCreated_by(row.getString("CREATED_BY"));
			grm.setCreated_date(row.getDate("CREATE_DATE"));
			gr.add(grm);
		}
		return gr;
		
	}
	
	public List<MTMenu> getMenuall(){
		List<MTMenu> me=new ArrayList<>();
		String sql="select * from menu m "
				+ " order by m.MENU_ID ASC ";
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
	
	public List<MTMenu> getMenu(int id){
		List<MTMenu> me=new ArrayList<>();
		String sql="select * from menu m "
				+ " where m.MAIN="+id;
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
	public int insertGroupDetail(MTGroupDetail gr){
		String SQL="INSERT INTO GROUP_DETAIL(GR_ID,MENU_ID) VALUES('"+gr.getGrId()+"','"+gr.getMenuId()+"')";
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	public int insertGroupName(MTGroup gr){
		String SQL="INSERT INTO GROUP_ROLE(GR_ID,GR_NAME,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,STATUS) VALUES"
				+ " (group_role_id_seq.nextval,'"+gr.getGr_Name()+"',"+gr.getGr_CreateBy()+",sysdate,"+gr.getUpdateBy()+","
				+ " sysdate,'"+gr.getGr_Status()+"')";
		int i=getJdbcTemplate().update(SQL);
		return i;
	}
	public int getLastGroupRoleId(){
		String sql="SELECT MAX(G.GR_ID) as gr_id  FROM GROUP_ROLE G";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int gr_id=0;
		while(row.next()){
			gr_id=Integer.parseInt(row.getString("gr_id"));
		}
		return gr_id;
	}
	public int getGroupName(String name){
		String sql="SELECT g.GR_ID FROM GROUP_ROLE G where g.GR_NAME='"+name+"'";
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int gr_id=0;
		while(row.next()){
			gr_id=Integer.parseInt(row.getString("gr_id"));
		}
		return gr_id;
	}
	public int getGroupNameId(String name,int id){
		String sql="SELECT g.GR_ID FROM GROUP_ROLE G where g.GR_NAME='"+name+"' and g.GR_ID="+id;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		int gr_id=0;
		while(row.next()){
			gr_id=Integer.parseInt(row.getString("gr_id"));
		}
		return gr_id;
	}
	public List<MTGroupDetail> getGroupDetail(int gId){
		List<MTGroupDetail> grd=new ArrayList<>();
		String sql="SELECT * FROM GROUP_DETAIL g "
				+ " inner join GROUP_ROLE gr on gr.GR_ID=g.GR_ID "
				+ " where g.GR_ID="+gId;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		while(row.next()){
			MTGroupDetail gr=new MTGroupDetail();
			gr.setGrName(row.getString("GR_NAME"));
			gr.setGrId(Integer.parseInt(row.getString("GR_ID")));
			gr.setMenuId(Integer.parseInt(row.getString("MENU_ID")));
			grd.add(gr);
		}
		return grd;
	}
	public int updateGroupname(MTGroup gr,int id){
		String sql="UPDATE GROUP_ROLE G SET G.GR_NAME='"+gr.getGr_Name()+"',g.UPDATE_BY="+gr.getUpdateBy()+","
				+ " g.UPDATE_DATE=sysdate WHERE G.GR_ID="+id;
		int i=getJdbcTemplate().update(sql);
		return i;
	}
	public int deleteGroupDetail(int id){
		String sqld="DELETE  GROUP_DETAIL G WHERE G.GR_ID="+id;
		int i=getJdbcTemplate().update(sqld);
		return i;
	}
	public int updateGroupDetail(MTGroupDetail gr,int id){		
		String sqlu="INSERT INTO GROUP_DETAIL(GR_ID,MENU_ID) VALUES('"+id+"','"+gr.getMenuId()+"')";
		int i=getJdbcTemplate().update(sqlu);
		return i;
	}
	public int disableGroup(int id){
		String sql="UPDATE GROUP_ROLE G set g.STATUS=1, g.UPDATE_DATE=sysdate WHERE G.GR_ID="+id;
		int i=getJdbcTemplate().update(sql);
		return i;
	}
	public int enableGroup(int id){
		String sql="UPDATE GROUP_ROLE G set g.STATUS=0, g.UPDATE_DATE=sysdate WHERE G.GR_ID="+id;
		int i=getJdbcTemplate().update(sql);
		return i;
	}
}

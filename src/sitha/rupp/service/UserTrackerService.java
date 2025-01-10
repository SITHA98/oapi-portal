package sitha.rupp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.MTLogged;
import sitha.rupp.model.MTUserTracker;

public class UserTrackerService extends GenericDaSupport {

	public List<MTUserTracker>initLsTracker(){
		String sql=
				"SELECT L.ID,TO_CHAR(B.BRANCH_ID,'000')BRANCH_ID, B.CODE BRANCH_CODE,U.DESCRIPTION DISPLAY_NAME ,G.GR_NAME ROLE \r\n"+
				",to_char(l.DATE_DONE,'DD-Mon-YYYY HH24:MI:SS AM')DATE_DONE,L.* FROM SYS_EVENT_LOG L \r\n"+
				" LEFT JOIN USERS U ON L.USER_ID=U.USER_ID  \r\n"+
				" LEFT JOIN BRANCH B ON U.BRANCH_ID=B.BRANCH_ID  \r\n"+
				" LEFT JOIN GROUP_ROLE G ON U.GROUP_ID=G.GR_ID  \r\n" +
				" --WHERE B.BRANCH_ID IS NOT NULL  \r\n" +
				" where U.GROUP_ID not in(26,35) AND B.BRANCH_ID IS NOT NULL \r\n " +
				 " order by L.ID DESC";
				//" WHERE TO_DATE(L.DATE_DONE)=TO_DATE(SYSDATE)";
		System.out.println("++++ Querying users checking reports ++++\n"+sql);
		List<MTUserTracker>ls=new ArrayList<>();
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		MTUserTracker track=null;
		while(row.next()){
			track=new MTUserTracker();
			track.setLID(row.getString("ID"));
			track.setBRANCH_ID(row.getString("BRANCH_ID"));
			track.setBRANCH_CODE(row.getString("BRANCH_CODE"));
			track.setDISPLAY_NAME(row.getString("DISPLAY_NAME"));
			track.setROLE(row.getString("ROLE"));
			track.setDATE_DONE(row.getString("DATE_DONE"));
			track.setEVENT_CODE(row.getString("EVENT_CODE"));
			track.setEVENT_DESCRIPTION(row.getString("EVENT_DESCRIPTION"));
			track.setDELETED(row.getString("DELETED"));
			track.setRPT_TYPE(row.getString("RPT_TYPE"));
			ls.add(track);
		}
		return ls;
	}
	
	public List<MTLogged>initLsLogged(){
		String sql=
				"SELECT TO_CHAR(B.BRANCH_ID,'000')BRANCH_ID, B.CODE ,U.DESCRIPTION,G.GR_NAME"
				+"  ,TO_CHAR(L.LOGIN_DATE,'DD-Mon-YYYY HH24:MI:SS AM')LOGIN_DT"
				+"	,NVL(TO_CHAR(L.LOGOUT_DATE,'DD-Mon-YYYY HH24:MI:SS AM'),'')LOGOUT_DT"
				+"	FROM LOGIN_LOG L LEFT JOIN USERS U ON L.USER_ID=U.USER_ID"
				+"	LEFT JOIN BRANCH B ON U.BRANCH_ID=B.BRANCH_ID"
				+"	LEFT JOIN GROUP_ROLE G ON U.GROUP_ID=G.GR_ID"
				//+"  where rownum<=100 "
				+"	ORDER BY L.LOGIN_LOG_ID DESC";
		List<MTLogged>ls=new ArrayList<>();
		Application_Properties.SERIAL=1;
		SqlRowSet row=getJdbcTemplate().queryForRowSet(sql);
		MTLogged track=null;
		while(row.next()){
			track=new MTLogged();
			track.setBranchId(row.getString("BRANCH_ID"));
			track.setBranchCode(row.getString("CODE"));
			track.setDisplayName(row.getString("DESCRIPTION"));
			track.setRole(row.getString("GR_NAME"));
			track.setLogin_DT(row.getString("LOGIN_DT"));
			track.setLogout_DT(row.getString("LOGOUT_DT"));
			ls.add(track);
		}
		return ls;
	}
}

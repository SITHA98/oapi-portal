package sitha.rupp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;


public class PrincebankComponent extends GenericDaSupport {
	
	private static PrincebankComponent obj;

	public static PrincebankComponent getInstance() {
		Application_Properties.SERIAL=1;
		if (obj == null)
			obj = new PrincebankComponent();
		return obj;
	}
	public String createOptionValue(String sql, String value, String desc, int selected) {
		try {
			//System.out.println(sql);
			Application_Properties.SERIAL=1;
			SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
			String str = "";// "<option value='-1' selected></option>\n";
			int sel = 0;
			while (rowSet.next()) {
				if (sel == 0) {
					if (rowSet.getInt(value) == selected) {
						str = str + "<option value='" + rowSet.getString(value) + "' selected  title='"
								+ rowSet.getString(desc) + "'>" + rowSet.getString(desc) + "</option>" + "\n";
						sel = 1;
					} else {
						str = str + "<option value='" + rowSet.getString(value) + "'  title='" + rowSet.getString(desc)
								+ "'>" + rowSet.getString(desc) + "</option>" + "\n";
					}
				} else {
					str = str + "<option value='" + rowSet.getString(value) + "'  title='" + rowSet.getString(desc) + "'>"
							+ rowSet.getString(desc) + "</option>" + "\n";
				}
			}
			//System.out.println("After= "+str);
			return str;
		} catch (Exception e) {
			return "<option value='0' selected  title='" + e.getMessage() + "'>" + e.getMessage() + "</option>";
		}
	}
	public String createOptionString(String sql, String value, String desc, String selected) {
		try {
			System.out.println(sql);
			Application_Properties.SERIAL=1;
			SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
			String str = "";// "<option value='-1' selected></option>\n";
			int sel = 0;
			while (rowSet.next()) {
				if (sel == 0) {
					if (rowSet.getString(value).equalsIgnoreCase(selected)) {
						str = str + "<option value='" + rowSet.getString(value) + "' selected  title='"
								+ rowSet.getString(desc) + "'>" + rowSet.getString(desc) + "</option>" + "\n";
						sel = 1;
					} else {
						str = str + "<option value='" + rowSet.getString(value) + "'  title='" + rowSet.getString(desc)
								+ "'>" + rowSet.getString(desc) + "</option>" + "\n";
					}
				} else {
					str = str + "<option value='" + rowSet.getString(value) + "'  title='" + rowSet.getString(desc) + "'>"
							+ rowSet.getString(desc) + "</option>" + "\n";
				}
			}
			return str;
		} catch (Exception e) {
			return "<option value='0' selected  title='" + e.getMessage() + "'>" + e.getMessage() + "</option>";
		}
	}
	
	public int getScarlare(String sql) {
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
		int value = 0;
		while (rowSet.next()) {
			int v = Integer.parseInt(rowSet.getString(1));
			value = v;
		}
		return value;
	}

	public double getScarlareDBL(String sql) {
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
		double value = 0;
		while (rowSet.next()) {
			double v = Double.parseDouble(rowSet.getString(1));
			value = v;
		}
		return value;
	}


	public int BRANCH_ID(int userId) {
		Application_Properties.SERIAL=1;
		String SQL = "SELECT BRANCH_ID FROM USERS WHERE USER_ID=" + userId;
		int branchId = 0;
		SqlRowSet row = getJdbcTemplate().queryForRowSet(SQL);
		while (row.next()) {
			branchId = Integer.parseInt(row.getString("BRANCH_ID"));
		}
		return branchId;
	}

	
	public double getValue(String sql, int index) {
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
		double value = 0;
		while (rowSet.next()) {
			String v = rowSet.getString(index);
			if (null != v)
				value = Double.parseDouble(v);
		}
		return value;
	}

	public String getString(String sql, int index) {
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
		String value = "";
		while (rowSet.next()) {
			value = rowSet.getString(index);
		}
		return value;
	}

	public String getString(String sql, String columnName) {
		SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
		String value = "";
		while (rowSet.next()) {
			value = rowSet.getString(columnName);
		}
		return value;
	}
	public int USER_ID(HttpServletRequest request) {
		try{
		int USER_ID = 0;
		HttpSession session = request.getSession();
		USER_ID = Integer.parseInt((String) session.getAttribute("PRINCE_USER_ID"));
		return USER_ID;
		}catch(Exception e){
			return 0;
		}
	}

	
	public String USER_DISPLAY_NAME(int userId) throws Exception {
		String SQL = "SELECT DESCRIPTION FROM USERS WHERE USER_ID=" + userId;
		Application_Properties.SERIAL=1;
		return getScalareString(SQL);
	}

	public int USER_ID_BY_Des(String userDes) {
		String SQL = "SELECT USER_ID  FROM USERS WHERE DESCRIPTION='" + userDes + "'";
		return getScarlare(SQL);
	}

	public String FIRST_DAY_OF_MONTH() throws Exception {
		String SQL = "select trunc(to_date(sysdate),'MON') first_date from dual";
		return getScalareString(SQL);
	}

	public String getGroupDesc(int groupId) throws Exception{
		try{
			Application_Properties.SERIAL=1;
			String SQL="Select GR_NAME From GROUP_ROLE Where GR_ID="+groupId;
			System.out.println("+++++++ Querying role ++++++\n"+SQL);
			return getScalareString(SQL);
		}catch(Exception e){
			HttpServletResponse response = null;
			response.sendRedirect("/login");
			return null;
		}
	}
	public String getBranchCode(int branchId) throws Exception{
		String SQL1="Select count(*)count from Branch where Branch_id="+branchId;
		int count=getScarlare(SQL1);
		if(count<=0)return "N/A";
		String SQL="Select nvl(Code,'NA')code from Branch where Branch_id="+branchId;
		//System.out.println(SQL);
		return getScalareString(SQL);
	}
	public String getCurrencyCode(int currId) throws Exception{
		String SQL="Select Code from Currency_code where curr_id="+currId;
		Application_Properties.SERIAL=1;
		return getScalareString(SQL);
	}
	public String getSYSTEMDATE() throws Exception{
		String SQL="Select TO_CHAR(sysdate,'DD-Mon-YYYY') SERVER_DATE from dual";
		Application_Properties.SERIAL=1;
		return getScalareString(SQL);
	}
	public String getDateAddMounth() throws Exception{
		String SQL="Select to_char(add_months(TO_CHAR(sysdate,'DD-Mon-YYYY'),3),'DD-Mon-YYYY') SERVER_DATE from dual";
		Application_Properties.SERIAL=1;
		return getScalareString(SQL);
	}
	public boolean isHQ(int userId) throws Exception{
		Application_Properties.SERIAL=1;
		String SQL="SELECT IS_MAIN FROM BRANCH WHERE BRANCH_ID IN( SELECT BRANCH_ID FROM USERS WHERE USER_ID="+userId+")";
		String result=getScalareString(SQL);
		if(result.trim().equalsIgnoreCase("Y")){
			return true;
		}else return false;
	}
	public String dateTime() throws Exception{
		return getScalareString("SELECT to_char(SYSDATE,'dd-Mon-YYYY hh24:mi') FROM DUAL");
	}
	public String TILL_ID(int USER_ID) throws Exception{
		String sql="SELECT NVL(TILL_ID,' ')TILL_ID FROM USERS WHERE USER_ID="+USER_ID;
		System.out.println("Retreive Till Id \n"+sql);
		return getScalareString(sql);
	}
	public String BR_NAME(int branchId) throws Exception{
		String SQL1="Select count(*)count from Branch where Branch_id="+branchId;
		int count=getScarlare(SQL1);
		if(count<=0)return "N/A";
		String SQL="Select nvl(DESCRIPTION,'NA')code from Branch where Branch_id="+branchId;
		//System.out.println(SQL);
		return getScalareString(SQL);
	}
	public double getExchangeRate(String date){
		/*String sql="SELECT distinct mid_rate FROM cytb_rates_history where rate_date='"+date+"'";
		double premonth_Rate=getScarlareDBL(sql);*/
		Application_Properties.SERIAL=1;
		String sql="SELECT distinct mid_rate FROM cytb_rates_history where rate_date='"+date+"'";
		double exRate=getScarlareDBL(sql);
		
		if(exRate<=0) exRate=4015;
		return exRate;
	}
	public String getUSER_NAME(int USER_ID ) throws Exception{
		String sql="Select user_name from users where user_id="+USER_ID;
//		Application_Properties.SERIAL=1;
		return getScalareString(sql);
	}
}
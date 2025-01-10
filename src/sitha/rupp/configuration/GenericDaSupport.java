package sitha.rupp.configuration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class GenericDaSupport {
	
	/*@Autowired*/
	//private DataSource dataSource;
	private SimpleDriverDataSource dataSource;
	private String url="";
	private String userName="";
	private String passWord="";
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public void activeProfile(){
		switch(Application_Properties.SERIAL){		
			case 1 : //System.out.println("PPWSA connection");
				url=Application_Properties.PPWSA_DATA_URL;
				userName=Application_Properties.PPWSA_DATA_USER_NAME;
				passWord=Application_Properties.PPWSA_DATA_PASSWORD;					
				break;		
			case 2 : 
				System.out.println("AGILIS PRODUCTION connection");
				url=Application_Properties.AGILIS_PRD_DATA_URL;
				userName=Application_Properties.AGILIS_PRD_DATA_USER_NAME;
				passWord=Application_Properties.AGILIS_PRD_DATA_PASSWORD;			
				break;			
			case 3 : 
				//System.out.println("AGILE UAT connection");
				url=Application_Properties.AGILIS_PRD_DATA_URL_UAT;
				userName=Application_Properties.AGILIS_PRD_DATA_USER_NAME_UAT;
				passWord=Application_Properties.AGILIS_PRD_DATA_PASSWORD_UAT;			
				break;
			case 4 : 
				//System.out.println("LHPP connection");
//				url=Application_Properties.BAKONG_DATA_URL;
//				userName=Application_Properties.BAKONG_DATA_USER_NAME;
//				passWord=Application_Properties.BAKONG_DATA_PASSWORD;
			
				break;
		}
	}
	
	public GenericDaSupport(){
		activeProfile();
		dataSource=new SimpleDriverDataSource();
		if(Application_Properties.SERIAL!=4){
			dataSource.setDriver(new oracle.jdbc.OracleDriver());
		}else{
			dataSource.setDriver(new org.postgresql.Driver());
		}
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		/*System.out.println("Connected!");*/
	}
	/*	
 	public GenericDaSupport(DataSource d){
		this.dataSource=d;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
	}*/
	//=============================================================================//
	/*@Autowired*/
	public void setDataSource(SimpleDriverDataSource dataSource) {
		
		activeProfile();
		dataSource=new SimpleDriverDataSource();
		if(Application_Properties.SERIAL!=4){
			dataSource.setDriver(new oracle.jdbc.OracleDriver());
		}else{
			dataSource.setDriver(new org.postgresql.Driver());
		}
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		//System.out.println("Connected!");
	}
	//=============================================================================//
	public JdbcTemplate getJdbcTemplate() {
		activeProfile();
		dataSource=new SimpleDriverDataSource();
		if(Application_Properties.SERIAL!=4){
			dataSource.setDriver(new oracle.jdbc.OracleDriver());
		}else{
			dataSource.setDriver(new org.postgresql.Driver());
		}
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		//System.out.println("Connected!");
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
		//return null;
	}
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public SqlRowSet getRowSet(String sql)throws Exception{
		return getJdbcTemplate().queryForRowSet(sql);
	}
	public int update(String sql){
		return getJdbcTemplate().update(sql);
	}
	public int update(String sql,SqlParameterSource parameterSource){
		return getNamedParameterJdbcTemplate().update(sql, parameterSource);
	}
	public  String getScalareString(String sql)throws Exception{
//		Application_Properties.SERIAL=1;
		return getJdbcTemplate().queryForObject(sql,String.class);
	}
}

package sitha.rupp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import sitha.rupp.configuration.Application_Properties;
import sitha.rupp.configuration.GenericDaSupport;
import sitha.rupp.model.PPWSATxn;
import sitha.rupp.model.PPWSATxnParam;

public class PPWSATxnDa extends GenericDaSupport {
	Gson gson = new Gson();

	public List<PPWSATxn> getPPWSATxn(PPWSATxnParam txnPar) {
		List<PPWSATxn> LsTxn = new ArrayList<>();
		String sql = "SELECT CUS_NAME, CUS_NUMBER, PHONE, BILL_NUMBER, BILL_AMOUNT, BILL_DATE, BILL_PERIOD, CREATE_DATE \r\n"
				+ " FROM SABAYDB.PPWSA_TRANSACTION_LOG WHERE CUS_NAME IS NOT NULL \r\n" +
				// " AND TO_DATE(BILL_DATE,'dd-mm-yyyy') <= TO_DATE("+ txnPar.getFromDate()
				// +",'dd-mm-yyyy')\r\n" +
				// " AND TO_DATE(BILL_DATE,'dd-mm-yyyy') >= TO_DATE("+
				// txnPar.getToDate()+",'dd-mm-yyyy')";
				" AND TO_DATE(BILL_DATE,'dd-MM-yyyy') BETWEEN TO_DATE('" + txnPar.getFromDate()
				+ "','dd-MM-yyyy') AND TO_DATE('" + txnPar.getToDate() + "','dd-MM-yyyy')";
		System.out.println(sql);
		Application_Properties.SERIAL = 2;
		SqlRowSet row = getJdbcTemplate().queryForRowSet(sql);
		while (row.next()) {
			PPWSATxn txn = new PPWSATxn();
			txn.setCus_num(row.getString("CUS_NUMBER"));
			txn.setCus_num(row.getString("CUS_NAME"));
			txn.setPhone(row.getString("PHONE"));
			txn.setBill_num(row.getString("BILL_NUMBER"));
			txn.setBill_amt(row.getString("BILL_AMOUNT"));
			txn.setBill_date(row.getString("BILL_DATE"));
			txn.setBill_period(row.getString("BILL_PERIOD"));
			LsTxn.add(txn);
		}
		return LsTxn;
	}
	
	public JSONObject OKhttp_GetRequest(String account_no, String dateFrom, String dateTo) throws JSONException, IOException {
		OkHttpClient client = new OkHttpClient();
		HttpUrl.Builder urlBuilder = HttpUrl.parse("http:10.80.80.119:8080/PRINCEBANKService/api/v1/account/getCASAStmt").newBuilder();
		urlBuilder.addQueryParameter("account_no", account_no);
		urlBuilder.addQueryParameter("dateFrom", dateFrom);
		urlBuilder.addQueryParameter("dateTo", dateTo);
		String url = urlBuilder.build().toString();
		Request req = new Request.Builder().url(url).build();
		Response res = client.newCall(req).execute();
		String data = res.body().string().toString();
		System.out.println("result: " + data);
		return new JSONObject(data);
	}

	public JSONObject sendGETRequest(String url,String param) throws IOException, JSONException {
		URL obj = new URL(url+param);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String data = response.toString();
			System.out.println("response: "+response.toString());
			return new JSONObject(data);
		} else {
			System.out.println("GET request not worked");
			return null;
		}
	}
		
	private static OAuth2RestTemplate oauth2RestTemplate = null;
	
	private static void init() {
		try {
			SSLContext sc = SSLContext.getInstance("SSL");

			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			TrustManager[] trustAllCerts = { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };
			sc.init(null, trustAllCerts, new SecureRandom());

			SSLContext.setDefault(sc);
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static OAuth2RestTemplate restTemplate() {
		
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setGrantType("password");
		resourceDetails.setAccessTokenUri("https://10.80.80.46:8246/token");
	
		//-- set the clients info
		resourceDetails.setClientId("UVP2tUfBCxMUSylt0XZTyQGm98Ua"); //Consumer Key
		resourceDetails.setClientSecret("GHofCxbTCJ6MjSiolOn0wDSq2dMa"); //Consumer Secret
		
		// set scopes
		List<String> scopes = new ArrayList<String>();
		scopes.add("read"); 
		scopes.add("write");
		scopes.add("trust");
		resourceDetails.setScope(scopes);
		
		//-- set Resource Owner info
		resourceDetails.setUsername("sopheap.sitha");//IN OAuth2SecurityConfiguration CLASS OF SERVER
		//resourceDetails.setUsername("bob");
		resourceDetails.setPassword("123456Aa"); //IN OAuth2SecurityConfiguration CLASS OF SERVER		
		return new OAuth2RestTemplate(resourceDetails);	
	}
	
	public static JSONObject getCustInfo(String url, String param) throws JSONException {
		init();
		oauth2RestTemplate = restTemplate();
		String data = oauth2RestTemplate.getForObject(url+param, String.class);
		System.out.println("response: "+data);
		return new JSONObject(data);
	}
	
	
	/*static String url = "https://10.80.80.46:8246/princebankservice/1.0.0/getCASAStmt?"; 
	static String param ="account_no=000822888&dateFrom=25-Feb-2018&dateTo=25-Feb-2020";
	public static void main(String[] args) throws JSONException {
		System.out.println(getCustInfo(url,param)); 
	}*/
	 
	
}

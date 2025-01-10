package sitha.rupp.service;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;

//import com.squareup.okhttp.HttpUrl;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

public class Testing {
	private static Gson gson = new Gson();

	public static void main(String[] args) throws IOException, JSONException {
		//System.out.println("A: " + getRequest()); // Not Work
		//System.out.println("GETRequest: " + GETRequest());
		//System.out.println("POSTRequest: " +POSTRequest());
		System.out.println(randomUUID());
	}
	
	public static String randomUUID() {
        return java.util.UUID.randomUUID().toString().toUpperCase();
   }

	/*
	 * public static JSONObject getRequest() throws IOException, JSONException {//
	 * OkHttp OkHttpClient client = new OkHttpClient(); HttpUrl.Builder urlBuilder =
	 * HttpUrl.parse(GET_URL).newBuilder();
	 * urlBuilder.addQueryParameter("account_no", "000070958");
	 * urlBuilder.addQueryParameter("dateFrom", "01-Jan-2019");
	 * urlBuilder.addQueryParameter("dateTo", "31-Dec-2020"); String url =
	 * urlBuilder.build().toString(); Request request = new
	 * Request.Builder().url(url).build();
	 * 
	 * Response response = client.newCall(request).execute(); String data =
	 * response.body().string().toString();
	 * 
	 * System.out.println("result: " + data); return new JSONObject(data); }
	 */

	private static String url_casa = "http://10.80.80.119:8080/PRINCEBANKService/api/v1/account/getCASAStmt?account_no=000070958&dateFrom=14-Feb-2019&dateTo=14-Feb-2020";
	private static JSONObject GETRequest() throws IOException, JSONException {
		URL obj = new URL(url_casa);
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
			//System.out.println(response.toString());
			return new JSONObject(data);
		} else {
			System.out.println("GET request not worked");
			return null;
		}
	}

	private static String url_inquiry = "https://bankapi-demo.bill24.net/payment/v2/inquiry";
	private static String url_fee = "https://bankapi-demo.bill24.net/payment/fee"; 
	public static JSONObject POSTRequest(Map<String, Object> param) throws IOException, JSONException {
		Map<String, Object> map_inquiry = new HashMap<>();
		map_inquiry.put("bill_code", "203577044311");
		map_inquiry.put("customer_code", "203577044311");
		map_inquiry.put("currency", "USD");
		
		Map<String, Object> map_fee = new HashMap<>();
		map_fee.put("amount", "100");
		map_fee.put("payment_token", "eyJhbGciOiJIUzI1NiIsImV4cCI6MTU4MjE3MDUzMCwiaWF0IjoxNTgyMTY5OTMwfQ.eyJiY28iOiIyMDM1NzcwNDQzMTEiLCJ0eXAiOiJwcm94eSIsImFtdCI6IjEwMC4wIiwiZmVlIjoiMC4yMDAwIiwiY2lkIjoiIiwicGNwIjpudWxsLCJpZHMiOm51bGwsImNjbyI6IjIwMzU3NzA0NDMxMSIsInBjbiI6Ik1TV01BL1JJVEhZIFNBTU5BTkciLCJjc3giOiJVU0QiLCJzaWQiOiJKbXh1SVNSWSIsInBjYyI6Ijc3MDQ0MzExIn0.CsQxCmBGosWByVNFL0P7qQ9Ouej-iVRnw8cXmPhK6fk");
	    URL obj = new URL(url_inquiry);	   
	    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
	    postConnection.setRequestMethod("POST");
	    postConnection.setRequestProperty("Content-Type", "application/json");
	    postConnection.setRequestProperty("Token", "d40852d3dfd14207b32c63d019874a39");
	    postConnection.setDoOutput(true);

	    OutputStream os = postConnection.getOutputStream();
	    os.write(gson.toJson(param).getBytes());
	    os.flush();
	    os.close();
	    int responseCode = postConnection.getResponseCode();
	    //System.out.println("POST Response Code :  " + responseCode);
	    //System.out.println("POST Response Message : " + postConnection.getResponseMessage());
	    if (responseCode == HttpURLConnection.HTTP_OK) { //success
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	        postConnection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = in .readLine()) != null) {
	            response.append(inputLine);
	        } 
	        in.close();
	        JSONObject respon = new JSONObject(response.toString());
	        return respon;
	    } else {
	        return new JSONObject("POST NOT WORKED");
	    }
	}
}

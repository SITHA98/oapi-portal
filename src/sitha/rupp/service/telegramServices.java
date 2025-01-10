package sitha.rupp.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import sitha.rupp.configuration.GenericDaSupport;

public class telegramServices extends GenericDaSupport {
	private static final Logger logger = LoggerFactory.getLogger(telegramServices.class);
	RestTemplate template = new RestTemplate();
	String url="https://api.telegram.org/bot1274689426:AAEEIfRVIpvJFPLHnYC7TLm1WGaJ5-w2SnI/sendMessage?chat_id=-431455375&";
	
	ResponseEntity<String> response=null;
	
	
	public ResponseEntity<String> sendTotelegram(String strTextSend) {
		try {
		//https://api.telegram.org/bot1274689426:AAEEIfRVIpvJFPLHnYC7TLm1WGaJ5-w2SnI/sendMessage?chat_id=-431455375&text=***bot send*** --group LHI-IT
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
//		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		headers.set("Authorization", "Basic " + GBVariable.AUTHROIZED_TOKEN);
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//body-> x-www-form-urlencode
		
		headers.setContentType(MediaType.APPLICATION_JSON);			
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		/*
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PPI_TOPUP_ACCOUNT)
				.queryParam("gw-username", "lyhourinsukhapi")
				.queryParam("password", param.getString("password"))
				.queryParam("gw-from", "SMS Info")
				.queryParam("gw-coding", "1")
				.queryParam("gw-to", param.getString("gw_to"))
				.queryParam("gw-text", param.getString("gw_text"));
		String url=builder.toUriString()+"&gw-password=!L#0iY*s&gw-from=SMS Info&gw-text=hi lhi";//+param.getString("password");
		HttpEntity<?> entity = new HttpEntity<>(headers);
//		ResponseEntity<PData> response = template.exchange(url, HttpMethod.POST, entity,PData.class);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity,String.class);
		System.out.println("url:"+url);
		*/
		// TO CONSUME API WITH BODY=x-www-urlencode
		
		Map<String, Object> map = new HashMap<>();
//		map.put("gw-from", "SMS Info");
//		map.put("gw-coding", "1");
		map.put("text", strTextSend);
		map.put("parse_mode", "html");
		
		
		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
//		System.out.println("entity:"+entity);
		// send POST request
		logger.info("3-calling... url " + map.toString() );
		response = template.postForEntity(url, entity, String.class);		
		
		logger.info("response:"+response);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
//			outputData.put("data", response.getBody());
//			outputData.put("result", true);
		} else {
//			outputData.put("data", response.getBody());
//			outputData.put("result", false);
		}

	} catch (Exception e) {
		logger.info("Error exception "+e.getMessage());
		e.printStackTrace();
	}
	//logger.info("output data "+outputData);
	return response;
	}
	
}

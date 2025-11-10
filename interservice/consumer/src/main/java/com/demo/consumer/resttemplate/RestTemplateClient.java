package com.demo.consumer.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
//	private static final String PROVIDER_URL="http://localhost:8081";
	private static final String PROVIDER_URL="http://provider-service";
	
	public String getInstanceInfo() {
		return restTemplate.getForObject(PROVIDER_URL+"/instance-info",String.class);
	}

}

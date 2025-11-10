package com.demo.consumer.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/rest-template")
public class RestTemplateController {
	
	@Autowired
	private RestTemplateClient restTemplateClient;

	@GetMapping("/instance")
	public String getInstance() {
		
//		RestTemplate restTemplate =new RestTemplate();
//		String response = restTemplate.getForObject("http://localhost:8081/instance-info", String.class);//we have used url from the provider microservice
//		return response;
		
		return restTemplateClient.getInstanceInfo();
		
		
	}
}

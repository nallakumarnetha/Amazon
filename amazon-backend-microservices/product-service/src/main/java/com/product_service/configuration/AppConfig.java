package com.product_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	/*
	@Bean
	@LoadBalanced	// required only if Feign Client/API Gateway not using
	public RestTemplate restTemplate() {
		return new RestTemplate() ;
	}
	*/
}

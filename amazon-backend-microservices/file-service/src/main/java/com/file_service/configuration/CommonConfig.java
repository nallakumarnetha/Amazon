package com.file_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class CommonConfig {

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	public S3Client s3Client() {
	    return S3Client.builder()
//	            .region(Region.US_EAST_1)
	    		.region(Region.of(System.getenv("AWS_REGION")))
	            .build();
	}

}

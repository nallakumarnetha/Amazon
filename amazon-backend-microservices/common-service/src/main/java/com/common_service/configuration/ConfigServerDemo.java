package com.common_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ConfigServerDemo {

	@Value("${demo.text}")
	String str;
	
	@PostConstruct
    public void init() {
        System.out.println("demo.text = " + str);
    }
}

package com.common_service.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ConfigServerEx {

	@Value("${demo.text:default_value}")
	private String str;
	
	@PostConstruct
    public void init() {
        System.out.println("demo.text = " + str);
    }
}

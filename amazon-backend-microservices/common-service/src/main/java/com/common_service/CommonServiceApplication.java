package com.common_service;

import org.springframework.boot.SpringApplication;
import static com.shared_contract.original.Logger.log;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CommonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApplication.class, args);
		log.info("\u001B[32m✅ Common Service Application started successfully!\u001B[0m");
	}

}

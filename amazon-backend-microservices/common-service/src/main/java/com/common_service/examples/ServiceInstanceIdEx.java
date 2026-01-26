package com.common_service.examples;

import static com.shared_contract.original.Logger.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common/demo")
public class ServiceInstanceIdEx {

	@Value("${eureka.instance.instance-id}")
	String instanceId;
	
	@GetMapping
	public void demo() {
		log.info("request is handling by service instance: {}", instanceId);
	}
	
}

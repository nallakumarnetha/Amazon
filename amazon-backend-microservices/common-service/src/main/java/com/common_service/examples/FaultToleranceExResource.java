package com.common_service.examples;

import static com.shared_contract.original.Logger.log;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("common/fault-tolerance")
public class FaultToleranceExResource {
	
	@Autowired
	private FaultToleranceExService service;
	
	// circuit breaker
	@GetMapping("demo1")
	public String demo1() {
		return service.demo1();
	}
	
	// retry
	@GetMapping("demo2")
	public String demo2() {
		return service.demo2();
	}
	
	// rate limiter
	@GetMapping("demo3")
	public String demo3() {
		return service.demo3();
	}
	
	// bulkhead
	@GetMapping("demo4")
	public String demo4() {
		return service.demo4();
	}
	
	// time limiter
	@GetMapping("demo5")
	public CompletableFuture<String> demo5() {
		return service.demo5();
	}

}

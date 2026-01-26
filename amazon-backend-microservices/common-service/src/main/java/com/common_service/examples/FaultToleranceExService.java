package com.common_service.examples;

import static com.shared_contract.original.Logger.log;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class FaultToleranceExService {

	// circuit breaker
	@CircuitBreaker(name = "commonBreaker", fallbackMethod = "demo1Fallback")
	public String demo1() {
		// code
		 throw new RuntimeException("internal server erro!");
		// code
	}
	
	public String demo1Fallback(Throwable ex) {
		return "Common service temporarily unavailable";
	}
	
	// retry
	@Retry(name = "commonBreaker", fallbackMethod = "demo2Fallback")
	public String demo2() {
		// code
		log.info("Retry attempt...");
		 throw new RuntimeException("internal server erro!");
		// code
	}
	
	public String demo2Fallback(Throwable ex) {
		log.info("demo2 fallback ...");
		return "Common service temporarily unavailable";
	}
	
	// rate limiter
	@RateLimiter(name = "commonBreaker")
	public String demo3() {
		return "hi";
	}

	// bulkhead
	@Bulkhead(name = "commonBreaker", 
			type = Bulkhead.Type.SEMAPHORE)
	public String demo4() {
		return "hi";
	}
	
	// time limiter
	@TimeLimiter(name = "commonBreaker", fallbackMethod = "demo5Fallback")
	public CompletableFuture<String> demo5() {
		return CompletableFuture.supplyAsync(() -> "hi");
	}
	
	public CompletableFuture<String> demo5Fallback(Throwable ex) {
		return CompletableFuture.supplyAsync(() -> "Common service temporarily unavailable");
	}
}

package com.api_gateway.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackResource {

	@GetMapping("/cart")
    public String cartFallback() {
        return "Cart service is down. Please try again later!";
    }
	
	@GetMapping("/common")
    public String commonFallback() {
        return "Common service is down. Please try again later!";
    }
	
	@GetMapping("/file")
    public String fileFallback() {
        return "File service is down. Please try again later!";
    }
	
	@GetMapping("/order")
    public String orderFallback() {
        return "Order service is down. Please try again later!";
    }
	
	@GetMapping("/product")
    public String productFallback() {
        return "Product service is down. Please try again later!";
    }
	
	@GetMapping("/user")
    public String userFallback() {
        return "User service is down. Please try again later!";
    }
	
}

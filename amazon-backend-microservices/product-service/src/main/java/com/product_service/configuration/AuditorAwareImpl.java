package com.product_service.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.amazon.user.User;
import com.amazon.user.UserService;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
	
	@Autowired
	private UserService userService;
	
    @Override
    public Optional<String> getCurrentAuditor() {
    	User user = userService.getCurrentUser();
    	if(user != null) {
    		return Optional.ofNullable(user.getName());
    	}
        return Optional.of("unauthorized_user");
    }
}

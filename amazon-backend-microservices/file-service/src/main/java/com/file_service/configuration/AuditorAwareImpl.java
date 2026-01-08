package com.file_service.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.file_service.client.UserClient;
import com.shared_contract.dto.user_service.UserDTO;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
	
	@Autowired
	private UserClient userClient;
	
    @Override
    public Optional<String> getCurrentAuditor() {
    	UserDTO user = userClient.getCurrentUser();
    	if(user != null) {
    		return Optional.ofNullable(user.getName());
    	}
        return Optional.of("unauthorized_user");
    }
}

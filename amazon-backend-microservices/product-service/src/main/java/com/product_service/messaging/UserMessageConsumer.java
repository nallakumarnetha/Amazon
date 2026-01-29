package com.product_service.messaging;

import static com.shared_contract.original.Logger.log;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.shared_contract.dto.user_service.UserDTO;

@Service
public class UserMessageConsumer {
	
	// consumes messages from the userProductQueue queue
	@RabbitListener(queues = "userProductQueue")	
	public void consumeMessage(UserDTO userDTO) {
		log.info("message received from rabbitmq: {}", "userProductQueue");
		// use userDTO in this service
	}

}

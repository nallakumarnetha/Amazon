package com.cart_service.messaging;

import static com.shared_contract.original.Logger.log;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.shared_contract.dto.cart_service.CartDTO;
import com.shared_contract.dto.order_service.OrderDTO;
import com.shared_contract.dto.product_service.ProductDTO;
import com.shared_contract.dto.user_service.UserDTO;

@Service
public class MessageConsumer {

	// consumes messages from the orderQueue queue
	@RabbitListener(queues = "orderQueue")	
	public void consumeOrderMessage(OrderDTO orderDTO) {
		log.info("message received from rabbitmq: {}", "orderQueue");
		// use orderDTO in this service
	}

	// consumes messages from the productQueue queue
	@RabbitListener(queues = "productQueue")	
	public void consumeProductMessage(ProductDTO productDTO) {
		log.info("message received from rabbitmq: {}", "productQueue");
		// use productDTO in this service
	}

	// consumes messages from the userQueue queue
	@RabbitListener(queues = "userQueue")	
	public void consumeUserMessage(UserDTO userDTO) {
		log.info("message received from rabbitmq: {}", "userQueue");
		// use userDTO in this service
	}

}

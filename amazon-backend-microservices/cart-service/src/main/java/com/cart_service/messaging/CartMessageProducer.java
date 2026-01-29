package com.cart_service.messaging;

import static com.shared_contract.original.Logger.log;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart_service.cart.Cart;
import com.shared_contract.dto.cart_service.CartDTO;


@Service
public class CartMessageProducer {	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void sendMessage(Cart cart) {
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		// publishes the cartDTO message to the myQueue queue
		// direct
		rabbitTemplate.convertAndSend("myQueue", cartDTO);
		log.info("message sent to rabbitmq: {}", "myQueue");
	}
	
	public void sendMessageExchange(String routingKey, Cart cart) {
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		// publishes the cartDTO message to the myQueue queue
		// fanout exchange
		rabbitTemplate.convertAndSend("fanoutExchange", "", cartDTO);
		// Topic exchange
		rabbitTemplate.convertAndSend("topicExchange", routingKey, cartDTO);
		log.info("message sent to rabbitmq: {}", "myQueue");
	}
	
}

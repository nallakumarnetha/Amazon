package com.user_service.messaging;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared_contract.dto.user_service.UserDTO;
import com.user_service.user.User;

import static com.shared_contract.original.Logger.log;

@Service
public class UserMessageProducer {	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void sendMessage(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		// publishes the userDTO message to the userProductQueue queue
		// direct
		rabbitTemplate.convertAndSend("userProductQueue", userDTO);
		log.info("message sent to rabbitmq: {}", "userProductQueue");
	}
	
	public void sendMessageExchange(String routingKey, User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		// publishes the userDTO message to the userProductQueue queue
		// fanout exchange
		rabbitTemplate.convertAndSend("fanoutExchange", "", userDTO);
		// Topic exchange
		rabbitTemplate.convertAndSend("topicExchange", routingKey, userDTO);
		log.info("message sent to rabbitmq: {}", "userProductQueue");
	}
	
}

package com.order_service.messaging;

import static com.shared_contract.original.Logger.log;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order_service.order.Order;
import com.shared_contract.dto.order_service.OrderDTO;

@Service
public class OrderMessageProducer {	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void sendMessage(Order order) {
		OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
		// publishes the orderDTO message to the myQueue queue
		// direct
		rabbitTemplate.convertAndSend("myQueue", orderDTO);
		log.info("message sent to rabbitmq: {}", "myQueue");
	}
	
	public void sendMessageExchange(String routingKey, Order order) {
		OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
		// publishes the orderDTO message to the myQueue queue
		// fanout exchange
		rabbitTemplate.convertAndSend("fanoutExchange", "", orderDTO);
		// Topic exchange
		rabbitTemplate.convertAndSend("topicExchange", routingKey, orderDTO);
		log.info("message sent to rabbitmq: {}", "myQueue");
	}
	
}

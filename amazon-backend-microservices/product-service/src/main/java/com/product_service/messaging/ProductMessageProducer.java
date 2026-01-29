package com.product_service.messaging;

import static com.shared_contract.original.Logger.log;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product_service.product.Product;
import com.shared_contract.dto.product_service.ProductDTO;


@Service
public class ProductMessageProducer {	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void sendMessage(Product product) {
		ProductDTO produdtDTO = modelMapper.map(product, ProductDTO.class);
		// publishes the produdtDTO message to the myQueue queue
		// direct
		rabbitTemplate.convertAndSend("myQueue", produdtDTO);
		log.info("message sent to rabbitmq: {}", ",,myQueue");
	}
	
	public void sendMessageExchange(String routingKey, Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		// publishes the productDTO message to the myQueue queue
		// fanout exchange
		rabbitTemplate.convertAndSend("fanoutExchange", "", productDTO);
		// Topic exchange
		rabbitTemplate.convertAndSend("topicExchange", routingKey, productDTO);
		log.info("message sent to rabbitmq: {}", "myQueue");
	}
	
}

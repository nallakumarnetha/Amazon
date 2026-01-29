package com.cart_service.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	@Bean
	public Queue userProductQueue(){
		return new Queue("myQueue") ;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate =
				new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter()) ;
		return rabbitTemplate;
	}

	// --------------Fanout Exchange--------
	@Bean
	public FanoutExchange logsExchange() {
		return new FanoutExchange("fanoutExchange");
	}

	@Bean
	public Queue queue1() { 
		return new Queue("queue1"); 
	}

	@Bean
	public Queue queue2() { 
		return new Queue("queue2"); 
	}

	@Bean
	public Queue queue3() { 
		return new Queue("queue3"); 
	}
	
	@Bean
	public Binding bindingA(Queue queue1, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue1).to(fanoutExchange);
	}
	
	@Bean
	public Binding bindingB(Queue queue2, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue2).to(fanoutExchange);
	}
	
	@Bean
	public Binding bindingC(Queue queue3, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue3).to(fanoutExchange);
	}

	// ----------------Topic Exchange------------
	@Bean
	public TopicExchange userExchange() {
		return new TopicExchange("topicExchange");
	}
	
	@Bean
	public Queue userQueueAll() { 
		return new Queue("userQueueAll"); 
	}
	
	@Bean
	public Queue userQueueIndia() { 
		return new Queue("userQueueIndia"); 
	}
	
	@Bean
	public Binding bindingAll(Queue userQueueAll, TopicExchange topicExchange) {
		return BindingBuilder.bind(userQueueAll).to(topicExchange).with("user.*");
	}
	
	@Bean
	public Binding bindingIndia(Queue userQueueIndia, TopicExchange topicExchange) {
		return BindingBuilder.bind(userQueueIndia).to(topicExchange).with("user.india.#");
	}

	// -------------------------------
}

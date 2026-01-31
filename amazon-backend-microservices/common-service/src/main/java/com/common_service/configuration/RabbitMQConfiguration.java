package com.common_service.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	@Bean
	public Queue cartQueue() {
	    return new Queue("cartQueue");
	}
	
	@Bean
	public Queue orderQueue() {
	    return new Queue("orderQueue");
	}
	
	@Bean
	public Queue productQueue() {
	    return new Queue("productQueue");
	}
	
	@Bean
	public Queue userQueue() {
	    return new Queue("userQueue");
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
	public Binding bindingA(@Qualifier("queue1") Queue queue1, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue1).to(fanoutExchange);
	}
	
	@Bean
	public Binding bindingB(@Qualifier("queue2") Queue queue2, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(queue2).to(fanoutExchange);
	}
	
	@Bean
	public Binding bindingC(@Qualifier("queue3") Queue queue3, FanoutExchange fanoutExchange) {
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
	public Binding bindingAll(@Qualifier("userQueueAll") Queue userQueueAll, TopicExchange topicExchange) {
		return BindingBuilder.bind(userQueueAll).to(topicExchange).with("user.*");
	}
	
	@Bean
	public Binding bindingIndia(@Qualifier("userQueueIndia") Queue userQueueIndia, TopicExchange topicExchange) {
		return BindingBuilder.bind(userQueueIndia).to(topicExchange).with("user.india.#");
	}

	// -------------------------------
}

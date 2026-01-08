package com.order_service.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import com.order_service.common.Constants;


@Configuration
public class CommonConfig {

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(Constants.MAIL_HOST);
        mailSender.setPort(Constants.MAIL_PORT);
        mailSender.setUsername(Constants.MAIL_USERNAME);
        mailSender.setPassword(Constants.MAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", Constants.MAIL_PROTOCOL);
        props.put("mail.smtp.auth", Constants.MAIL_SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", Constants.MAIL_SMTP_STARTTLS);

        return mailSender;
    }
	
}

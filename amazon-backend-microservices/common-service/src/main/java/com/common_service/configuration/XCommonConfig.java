package com.common_service.configuration;

import java.net.URI;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import com.amazon.common.Constants;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class XCommonConfig {

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(XConstants.MAIL_HOST);
        mailSender.setPort(XConstants.MAIL_PORT);
        mailSender.setUsername(XConstants.MAIL_USERNAME);
        mailSender.setPassword(XConstants.MAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", XConstants.MAIL_PROTOCOL);
        props.put("mail.smtp.auth", XConstants.MAIL_SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", XConstants.MAIL_SMTP_STARTTLS);

        return mailSender;
    }
	
	@Bean
	public S3Client s3Client() {
	    return S3Client.builder()
//	            .region(Region.US_EAST_1)
	    		.region(Region.of(System.getenv("AWS_REGION")))
	            .build();
	}

}

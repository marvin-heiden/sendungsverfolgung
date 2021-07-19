package com.senacor.tecco.MessageFilterService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class MessageFilterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageFilterServiceApplication.class, args);
	}
}

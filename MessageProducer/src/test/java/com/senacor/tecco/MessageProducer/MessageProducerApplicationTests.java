package com.senacor.tecco.MessageProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.tecco.MessageProducer.config.JacksonConfig;
import com.senacor.tecco.MessageProducer.models.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootTest
class MessageProducerApplicationTests {

	@Autowired
	ObjectMapper mapper;

	@Test
	void contextLoads() throws JsonProcessingException {;
		Message message = Message.generate();
		System.out.println(message.toString());
		System.out.println(mapper.writeValueAsString(message));
	}

}

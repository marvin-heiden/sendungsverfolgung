package com.senacor.tecco.MessageProducer.services;

import com.senacor.tecco.MessageProducer.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    public void sendSingleMessage(){
        Message message = Message.generate();
        kafkaTemplate.send("input", message);
        System.out.println("Message sent:");
        System.out.println(message);
    }


}

package com.senacor.tecco.MessageProducer.services;

import com.senacor.tecco.MessageProducer.models.Event;
import com.senacor.tecco.MessageProducer.models.Message;
import com.senacor.tecco.MessageProducer.models.MessageHeader;
import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


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

    public void sendMessageSeries(){
        for (int j = 0; j < 50000; j++) {
            Event.EventType[] eventTypes = Event.EventType.values();
            MockNeat mock = MockNeat.threadLocal();

            Event event = Event.generate(eventTypes[0]);
            MessageHeader header = MessageHeader.generate();
            for (int i = 0; i < 4; i++) {
                event.setUuid(UUID.randomUUID().toString());
                event.setFacility(mock.cities().capitalsEurope().get());
                event.setCreationTimestamp(new Date(event.getCreationTimestamp().getTime() + i * 3600000 * 4));
                event.setType(eventTypes[i].toString());
                event.setMessage(Event.getMessageString(eventTypes[i]));

                header.setMsgUuid(UUID.randomUUID().toString());
                header.setMsgTimestamp(new Date(header.getMsgTimestamp().getTime() + i * 3600000 * 4));

                Message message = new Message(header, event);
                kafkaTemplate.send("input", message);
            }
        }
    }


}

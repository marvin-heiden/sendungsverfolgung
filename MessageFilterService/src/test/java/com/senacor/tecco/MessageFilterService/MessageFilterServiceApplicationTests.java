package com.senacor.tecco.MessageFilterService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;
import com.senacor.tecco.MessageFilterService.models.Event;
import com.senacor.tecco.MessageFilterService.models.Message;
import com.senacor.tecco.MessageFilterService.models.TrackingHistory;
import com.senacor.tecco.MessageFilterService.services.InputListener;
import com.senacor.tecco.MessageFilterService.services.SchemaValidator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 2,
        topics = {"input", "error"}
)
class MessageFilterServiceApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    SchemaValidator schemaValidator;

    @Autowired
    InputListener inputListener;

    @Autowired
    KafkaTemplate<String, Message> kafkaTemplate;

    @Value("classpath:TrackingMessageValid.json")
    File trackingMessageValid;

    @BeforeEach
    void waitForAssignment() {
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }
    @AfterEach
    void cleanup(){
        mongoTemplate.getDb().drop();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void schemaValidatorTest() throws IOException {
        String message = Files.readString(trackingMessageValid.toPath());

        Set<ValidationMessage> errors = schemaValidator.validate(message);
        assertThat(errors).isNotNull().isEmpty();
    }

    @Test
    public void inputListenerTest() throws IOException {
        String message = Files.readString(trackingMessageValid.toPath());

        inputListener.listen(message); // <-- This works!

        Event event = mapper.readValue(message, Message.class).getEvent();

        // create history form event
        Set<Event> history = new HashSet<>();
        history.add(event);
        Set<String> identifiers = new HashSet<>();
        event.getIdentifiers().forEach(id -> identifiers.add(id.getValue()));
        TrackingHistory comparisonTrackingHistory = new TrackingHistory(history,identifiers);

        List<TrackingHistory> result = mongoTemplate.findAll(TrackingHistory.class);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(comparisonTrackingHistory);
    }

/*
    @Test
    public void kafkaListenerTest() throws IOException {
        String message = Files.readString(trackingMessageValid.toPath());

        Message messageObject = mapper.readValue(message, Message.class);

        kafkaTemplate.send("input", messageObject);

        Event event = mapper.readValue(message, Message.class).getEvent();

        // create history form event
        Set<Event> history = new HashSet<>();
        history.add(event);
        Set<String> identifiers = new HashSet<>();
        event.getIdentifiers().forEach(id -> identifiers.add(id.getValue()));
        TrackingHistory comparisonTrackingHistory = new TrackingHistory(history,identifiers);

        List<TrackingHistory> result = mongoTemplate.findAll(TrackingHistory.class);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(comparisonTrackingHistory);
    }
*/

}

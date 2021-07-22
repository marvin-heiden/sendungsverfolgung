package com.senacor.tecco.MessageFilterService.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.senacor.tecco.MessageFilterService.models.Person;
import org.apache.kafka.clients.producer.internals.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@Service
public class InputListener {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    JsonSchema jsonSchemaTrackingEvent;

    @Autowired
    public InputListener() {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        try {
            jsonSchemaTrackingEvent = factory.getSchema(new URI("classpath:TrackingEventSchema.json"));
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
    }

    @KafkaListener(topics = "input", groupId = "groupId")
    public void listen(String message) throws IOException, URISyntaxException {
        //System.out.println(message);

        // Validate message against JSON schema
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);
        Set<ValidationMessage> errors = jsonSchemaTrackingEvent.validate(node);

        //Send to dedicated Topics
        if (errors.isEmpty()) {
            // Send to Storage Topic
            // kafkaTemplate.send("storage", message);

            // Send multiple times to different partitions if message contains multiple Identifiers!
            // hash(key)%num_partition // REMAINDER % CAN PRODUCE NEGATIVE

            ArrayNode identifiers = (ArrayNode) node.get("Event").get("Identifiers");
            for (JsonNode identifier : identifiers) {
                String key = identifier.get("Value").asText();
                int partition = Math.floorMod(key.hashCode(), 10);
                // Add key to message for querying
                ((ObjectNode) node).put("Key", key);
                String jsonMessage = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                kafkaTemplate.send("storage", partition,key,jsonMessage);
                System.out.println("Published key "+key+" to partition "+partition+" on topic storage");
            }

        } else {
            for (ValidationMessage error : errors) {
                System.out.println(error);
            }
            // Send to Error Topic
            kafkaTemplate.send("error", message);
            System.out.println("Published to topic error");
        }
    }
}

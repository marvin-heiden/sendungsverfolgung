package com.senacor.tecco.MessageFilterService.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@Service
public class InputListener {
    @KafkaListener(topics = "input", groupId = "groupId")
    public void listen(String message) throws IOException, URISyntaxException {
        System.out.println(message);


        JsonSchema jsonSchemaTrackingEvent;
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        jsonSchemaTrackingEvent = factory.getSchema(new URI("classpath:TrackingEventSchema.json"));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);
        Set<ValidationMessage> errors = jsonSchemaTrackingEvent.validate(node);

        if(errors.isEmpty()){
            // Send to Storage Topic
        } else {
            for (ValidationMessage error:errors) {
                System.out.println(error);
            }
            // Send to Error Topic
        }
    }
}

package com.senacor.tecco.MessageFilterService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

@Service
@Log4j2
public class SchemaValidator {

    @Autowired
    ObjectMapper mapper;

    JsonSchema jsonSchemaTrackingEvent;

    public SchemaValidator(){
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        File trackingEventSchema = null;
        try {
            trackingEventSchema = ResourceUtils.getFile("classpath:TrackingEventSchema.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        jsonSchemaTrackingEvent = factory.getSchema(trackingEventSchema.toURI());
    }

    public Set<ValidationMessage> validate(String message){
        // Validate message against JSON schema
        JsonNode node = null;
        try {
            node = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return jsonSchemaTrackingEvent.validate(node);
    }
}

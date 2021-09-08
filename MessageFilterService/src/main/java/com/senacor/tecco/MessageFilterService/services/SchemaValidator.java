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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Set;

@Service
@Log4j2
public class SchemaValidator {

    @Autowired
    ObjectMapper mapper;

    @Value("${schema.path}")
    String schemaPath;

    JsonSchema jsonSchemaTrackingEvent;

    @PostConstruct
    public void init() throws FileNotFoundException {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        log.info(schemaPath);
        InputStream trackingEventSchema = new FileInputStream(schemaPath);//getClass().getResourceAsStream(schemaPath);
        jsonSchemaTrackingEvent = factory.getSchema(trackingEventSchema);
    }

    public Set<ValidationMessage> validate(String message){
        // Validate message against JSON schema
        JsonNode node = null;
        try {
            node = mapper.readTree(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return jsonSchemaTrackingEvent.validate(node);
    }
}

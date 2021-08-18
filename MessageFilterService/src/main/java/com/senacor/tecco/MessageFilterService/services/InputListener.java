package com.senacor.tecco.MessageFilterService.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.senacor.tecco.MessageFilterService.models.Event;
import com.senacor.tecco.MessageFilterService.models.IdentifierLookup;
import com.senacor.tecco.MessageFilterService.models.Message;
import com.senacor.tecco.MessageFilterService.models.TrackingHistory;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Log4j2
public class InputListener {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    SchemaValidator schemaValidator;

    @KafkaListener(topics = "input", groupId = "groupId")
    public void listen(String message) throws IOException {

        // Validate message against JSON schema
        Set<ValidationMessage> errors = schemaValidator.validate(message);

        //Send to dedicated destinations
        if (errors != null && errors.isEmpty()) {

            Event event = mapper.readValue(message, Message.class).getEvent();

            // create history form event
            Set<Event> history = new HashSet<>();
            history.add(event);

            // create identifier list
            Set<String> identifiers = new HashSet<>();
            event.getIdentifiers().forEach(id -> identifiers.add(id.getValue()));

            // Query if TrackingHistory exist - returns List with distinct trackingHistoryId values
            List<String> lookupList = mongoTemplate.query(IdentifierLookup.class)
                    .distinct("trackingHistoryId")
                    .matching(query(where("_id").in(identifiers)))
                    .as(String.class)
                    .all();


            log.info("Found objects: " + lookupList);

            // If no previous Data exists
            if (lookupList.isEmpty()) {
                // Create new
                TrackingHistory trackingHistory = new TrackingHistory(history, identifiers);
                mongoTemplate.insert(trackingHistory);

                // Insert new lookup documents
                identifiers.forEach(identifier -> {
                    mongoTemplate.save(new IdentifierLookup(trackingHistory.getId(), identifier));
                });

            }
            // If entries exists
            else {
                // Get TrackingHistoryObjects
                List<TrackingHistory> foundTrackingHistoryObjects = new ArrayList<>();
                lookupList.forEach(trackingHistoryId -> {
                    foundTrackingHistoryObjects.add(
                            mongoTemplate.findById(trackingHistoryId, TrackingHistory.class)
                    );
                });

                // Update with new Step, Sender, Receiver, Identifiers
                Update update = new Update();
                foundTrackingHistoryObjects.forEach(trackingHistory -> {
                    history.addAll(trackingHistory.getHistory());
                    identifiers.addAll(trackingHistory.getIdentifiers());
                });
                update.addToSet("history").each(history);
                update.addToSet("identifiers").each(identifiers);

                TrackingHistory result = mongoTemplate.findAndModify(
                        query(where("_id").is(foundTrackingHistoryObjects.get(0).getId())), // first entry
                        update,
                        TrackingHistory.class
                );

                // Delete excess documents if more than one document was found
                if (foundTrackingHistoryObjects.size() > 1)
                    for (int i = 1; i < foundTrackingHistoryObjects.size(); i++) {
                        mongoTemplate.remove(foundTrackingHistoryObjects.get(i));
                    }

                // Update or insert lookup documents
                identifiers.forEach(identifier -> {
                    mongoTemplate.save(new IdentifierLookup(Objects.requireNonNull(result).getId(), identifier));
                });

            }
        }
        // If errors exist
        else {
            errors.forEach(log::error);
            // Send to Error Topic
            kafkaTemplate.send("error", message);
            log.info("Published message to error topic");
        }
    }
}

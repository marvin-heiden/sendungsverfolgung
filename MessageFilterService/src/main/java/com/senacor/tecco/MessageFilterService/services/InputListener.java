package com.senacor.tecco.MessageFilterService.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.senacor.tecco.MessageFilterService.db.TrackingRepository;
import com.senacor.tecco.MessageFilterService.models.Event;
import com.senacor.tecco.MessageFilterService.models.IdentifierLookup;
import com.senacor.tecco.MessageFilterService.models.Message;
import com.senacor.tecco.MessageFilterService.models.TrackingHistory;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    JsonSchema jsonSchemaTrackingEvent;

    @Autowired
    public InputListener() {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

        try {
            jsonSchemaTrackingEvent = factory.getSchema(new URI("classpath:TrackingEventSchema.json"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "input", groupId = "groupId")
    public void listen(String message) throws IOException {

        // Validate message against JSON schema
        JsonNode node = mapper.readTree(message);
        Set<ValidationMessage> errors = jsonSchemaTrackingEvent.validate(node);

        //Send to dedicated destinations
        if (errors.isEmpty()) {

            Event event = mapper.readValue(message, Message.class).getEvent();
            // Send to Storage DB

            // create history form event
            Set<Event> history = new HashSet<>();
            history.add(event);

            // sort list by timestamp
            //history.sort(Comparator.comparing(Step::getTimestamp));

            // create identifier list
            Set<String> identifiers = new HashSet<>();
            event.getIdentifiers().forEach(id -> identifiers.add(id.getValue()));

            // Query if TrackingHistory exist - returns List
            List<TrackingHistory> foundTrackingHistoryObjects =
                    mongoTemplate.find(query(where("Identifiers").in(identifiers)), TrackingHistory.class);
            log.info("Found objects: " + foundTrackingHistoryObjects);

            // If no previous Data exists
            if (foundTrackingHistoryObjects.isEmpty()) {
                // Create new
                TrackingHistory trackingHistory = new TrackingHistory(history, identifiers);
                mongoTemplate.insert(trackingHistory);

                // Insert new lookup documents
                identifiers.forEach(identifier -> {
                    mongoTemplate.save(new IdentifierLookup(trackingHistory.getId(), identifier));
                });

            }
            // If one entry exists
            else if (foundTrackingHistoryObjects.size() == 1) {
                // Update with new Step, Sender, Receiver, Identifiers
                Update update = new Update();
                foundTrackingHistoryObjects.forEach(trackingHistory -> {
                    identifiers.addAll(trackingHistory.getIdentifiers());
                });
                update.addToSet("history", event);
                update.addToSet("identifiers").each(identifiers);

                TrackingHistory result = mongoTemplate.findAndModify(
                        query(where("_id").is(foundTrackingHistoryObjects.get(0).getId().toString())),
                        update,
                        TrackingHistory.class
                );

                // Update or insert lookup documents
                identifiers.forEach(identifier -> {
                    mongoTemplate.save(new IdentifierLookup(Objects.requireNonNull(result).getId(), identifier));
                });

            }
            // If more then one entry exists
            else {
                // Aggregate first entry with other Data's Steps, Update, Delete others
                Update update = new Update();

                foundTrackingHistoryObjects.forEach(trackingHistory -> {
                    update.addToSet("history").each(trackingHistory.getHistory()); // add to collection instead
                    identifiers.addAll(trackingHistory.getIdentifiers());
                });
                update.addToSet("history", event); // add collection here
                update.addToSet("identifiers").each(identifiers);

                // Commit
                TrackingHistory result = mongoTemplate.findAndModify(
                        query(where("_id").is(foundTrackingHistoryObjects.get(0).getId().toString())),
                        update,
                        TrackingHistory.class
                );

                // Delete
                for (int i = 1; i < foundTrackingHistoryObjects.size(); i++) {
                    mongoTemplate.remove(foundTrackingHistoryObjects.get(i));
                }

                // Update lookup documents
                Update lookupUpdate = new Update();
                lookupUpdate.set("trackingHistoryId", Objects.requireNonNull(result).getId());
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

package com.senacor.tecco.MessageReadService.services;

import com.senacor.tecco.MessageReadService.models.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Log4j2
public class TrackingService {

    @Autowired
    MongoTemplate mongoTemplate;

    public TrackingHistoryShort getShortTrackingHistoryByTrackingNumber(String trackingNumber) {
        // Check existing IdentifierLookup documents
        IdentifierLookup lookup = mongoTemplate.findOne(query(where("_id").is(trackingNumber)), IdentifierLookup.class);

        log.info(lookup);
        if(lookup == null) return null;

        // Get History Object from DB
        TrackingHistory trackingHistory = mongoTemplate.findOne(query(where("_id").is(lookup.getTrackingHistoryId())), TrackingHistory.class);

        return createTrackingHistoryShort(trackingHistory);
    }

    public TrackingHistoryShort createTrackingHistoryShort(TrackingHistory trackingHistory){
        // Sort history by date
        List<Event> history = trackingHistory.getHistory()
                .stream()
                .sorted(Comparator.comparing(Event::getCreationTimestamp))
                .collect(Collectors.toList());

        // Create steps for short Tracking History
        ArrayList<Step> steps = new ArrayList<>();
        history.forEach(event -> {
            steps.add(new Step(
                    event.getCreationTimestamp(),
                    event.getMessage(),
                    event.getType(),
                    event.getFacility()
            ));
        });

        return new TrackingHistoryShort(
                history.get(history.size()-1).getSender(), // most recent sender
                history.get(history.size()-1).getReceiver(), // most recent receiver
                steps,
                trackingHistory.getIdentifiers()
        );
    }

}

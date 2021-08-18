package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document
@EqualsAndHashCode(exclude = {"id"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Sharded(shardKey = "id")
public class TrackingHistory {

    @Id
    private String id;

    private Set<Event> history;

    @Indexed
    private Set<String> identifiers;

    //@Indexed(expireAfterSeconds=20)
    private long ttl;

    public TrackingHistory(Set<Event> history, Set<String> identifiers) {
        this.id = ObjectId.get().toString();
        this.history = history;
        this.identifiers = identifiers;
        this.ttl = 7776000; // 90 days = 7776000
    }
}

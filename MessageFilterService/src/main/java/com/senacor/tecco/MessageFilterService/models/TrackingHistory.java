package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document
@EqualsAndHashCode(exclude = {"id", "lastModifiedDate", "createdDate"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Sharded(shardKey = "id")
public class TrackingHistory {

    @Id
    private String id;

    private Set<Event> history;

    @Indexed
    private Set<String> identifiers;

    private long ttl;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @CreatedDate
    private Instant createdDate;


    public TrackingHistory(Set<Event> history, Set<String> identifiers) {

        this.id = ObjectId.get().toString();

        this.history = history;

        this.identifiers = identifiers;

        this.ttl = 7776000; // 90 days = 7776000
    }
}

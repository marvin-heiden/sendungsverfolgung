package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.Instant;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Sharded(shardKey = "id")
public class TrackingHistory {

    @Id
    @JsonProperty("_id")
    private ObjectId id;

    private Set<Event> history;

    @Indexed
    private Set<String> identifiers;

    private long ttl;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @CreatedDate
    private Instant createdDate;
}

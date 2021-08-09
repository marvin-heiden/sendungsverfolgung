package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.Date;
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
    private ObjectId id;

    private Set<Event> history;

    @Indexed
    private Set<String> identifiers;

    private long ttl;
}

package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.*;

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

    @Indexed(expireAfterSeconds=7776000) // 90 days
    private Date lastModified;
}

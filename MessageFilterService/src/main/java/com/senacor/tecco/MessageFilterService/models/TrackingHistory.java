package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingHistory {

    @Id
    private ObjectId id;

    private Person sender;

    private Person receiver;

    private ArrayList<Step> history;

    @Indexed
    private HashSet<String> identifiers;

    @Indexed(expireAfterSeconds=7776000) // 90 days
    private Date lastModified;
}

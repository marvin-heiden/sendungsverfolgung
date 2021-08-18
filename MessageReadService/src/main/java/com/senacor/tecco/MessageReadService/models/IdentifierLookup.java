package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Document
@EqualsAndHashCode
@Sharded(shardKey = "identifier")
public class IdentifierLookup {


    @Id
    @JsonProperty("_id")
    private String identifier;

    private ObjectId trackingHistoryId;

    private long ttl;

    public IdentifierLookup(ObjectId trackingHistoryId, String identifier){
        this.trackingHistoryId = trackingHistoryId;
        this.identifier = identifier;
        this.ttl = 7776000; // 90 days = 7776000
    }
}

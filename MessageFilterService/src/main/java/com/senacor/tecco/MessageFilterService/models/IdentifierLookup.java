package com.senacor.tecco.MessageFilterService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Data
@AllArgsConstructor
@Document
@EqualsAndHashCode
@Sharded(shardKey = "identifier")
public class IdentifierLookup {


    @Id
    private String identifier;

    private ObjectId trackingHistoryId;

    private long ttl;

    public IdentifierLookup(ObjectId trackingHistoryId, String identifier){
        this.trackingHistoryId = trackingHistoryId;
        this.identifier = identifier;
        this.ttl = 7776000; // 90 days = 7776000
    }
}

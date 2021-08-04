package com.senacor.tecco.MessageReadService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document
@EqualsAndHashCode
public class TrackingHistoryShort {

    private Person sender;
    private Person receiver;
    private List<Step> history;
    private Set<String> identifiers;
}
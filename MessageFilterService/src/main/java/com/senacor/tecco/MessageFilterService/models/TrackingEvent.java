package com.senacor.tecco.MessageFilterService.models;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
@AllArgsConstructor
public class TrackingEvent {

    private String uuid;
    private String creationTimestamp;
    private String type;
    private String facility;
    private String message;
    private Person sender;
    private Person receiver;
    private ArrayList<Identifier> identifiers;
}

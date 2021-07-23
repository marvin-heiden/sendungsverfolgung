package com.senacor.tecco.MessageReadService.models;

import lombok.*;

import java.util.ArrayList;
import java.util.TreeSet;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrackingHistory {
    @Getter
    @Setter
    private Person receiver;
    @Getter
    @Setter
    private Person sender;
    @Getter
    @Setter
    private ArrayList<Step> history;
    @Getter
    @Setter
    private TreeSet<String> identifiers;
}

package com.senacor.tecco.MessageReadService.models;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;

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
    private HashSet<String> identifiers;
}

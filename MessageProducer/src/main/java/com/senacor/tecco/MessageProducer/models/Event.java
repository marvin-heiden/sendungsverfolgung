package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.andreinc.mockneat.MockNeat;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @Getter
    @Setter
    @JsonProperty("EventUUID")
    private String uuid;
    @Getter
    @Setter
    @JsonProperty("EventTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date creationTimestamp;
    @Getter
    @Setter
    @JsonProperty("EventType")
    private String type;
    @Getter
    @Setter
    @JsonProperty("ScanFacility")
    private String facility;
    @Getter
    @Setter
    @JsonProperty("Message")
    private String message;
    @Getter
    @Setter
    @JsonProperty("Sender")
    private Person sender;
    @Getter
    @Setter
    @JsonProperty("Receiver")
    private Person receiver;
    @Getter
    @Setter
    @JsonProperty("Identifiers")
    private ArrayList<Identifier> identifiers;

    public enum EventType {Vorankündigung, Abgangszentrum, Eingangszentrum, Zugestellt, Weiterleitung, Zustellhindernis};


    public static Event generate(EventType eventType){
        MockNeat mock = MockNeat.threadLocal();
        String message = "";
        switch (eventType) {
            case Vorankündigung: message = "Die Sendung wurde angemeldet und befindet sich bald auf dem Versandweg."; break;
            case Abgangszentrum: message = "Die Sendung wurde im Abgangszentrum bearbeitet und befindet sich auf dem Weg zum Zielgebiet."; break;
            case Eingangszentrum: message = "Die Sendung wurde im Eingangszentrum bearbeitet und wird in Kürze zugestellt."; break;
            case Zugestellt: message = "Die Sendung wurde erfolgreich zugestellt."; break;
            case Weiterleitung: message = "Die Sendung wird aufgrund einer Adressänderung an eine neue Adresse weitergeleitet."; break;
            case Zustellhindernis: message = "Die Sendung konnte nicht zugestellt werden. Bitte kontaktieren Sie unseren Kundendienst."; break;
        }

        ArrayList<Identifier> identifiers = new ArrayList<>();
        // Add additional identifiers in 1% of all cases
        int identifierCount = 1;
        if (Math.random() > 0.99){
            identifierCount += ((int) Math.floor(Math.random() * 10) + 1);
        }
        for (int i = 0; i<identifierCount; i++) identifiers.add(Identifier.generate());

        return new Event(
                UUID.randomUUID().toString(),
                new Date(),
                eventType.toString(),
                mock.cities().capitalsEurope().get(),
                message,
                Person.generate(),
                Person.generate(),
                identifiers
        );
    }
}





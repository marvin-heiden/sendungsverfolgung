import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TrackingEvent {

    private String uuid;
    private String creationTimestamp;
    public enum EventType {Vorankündigung, Abgangszentrum, Eingangszentrum, Zugestellt, Weiterleitung, Zustellhindernis};
    private EventType type;
    private String facility;
    private String message;
    private Person sender;
    private Person receiver;
    private ArrayList<Identifier> identifiers;

    public TrackingEvent(EventType type, String facility, String message, Person sender, Person receiver) {
        this.uuid = UUID.randomUUID().toString();
        this.creationTimestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
        this.type = type;
        this.facility = facility;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.identifiers = new ArrayList<>();

        //Add additional identifiers in 1% of all cases
        int identifierCount = 1;
        if (Math.random() > 0.20){
            identifierCount += ((int) Math.random() * 10 + 1);
        }
        for (int i = 0; i<identifierCount; i++) this.identifiers.add(new Identifier());
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode eventNode = mapper.createObjectNode();

        eventNode.put("EventUUID", uuid);
        eventNode.put("EventTimestamp",creationTimestamp);
        eventNode.put("EventType", type.toString());
        eventNode.put("ScanFacility", facility);
        eventNode.put("Message", message);
        eventNode.set("Sender", sender.toJson());
        eventNode.set("Receiver", receiver.toJson());
        //Identifiers
        ArrayNode identifierNode = eventNode.putArray("Identifiers");
        for (Identifier identifier:identifiers) {
            identifierNode.add(identifier.toJson());
        }

        return eventNode;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public EventType getType() {
        return type;
    }

    public String getFacility() {
        return facility;
    }

    public String getMessage() {
        return message;
    }

    public Person getSender() {
        return sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public ArrayList<Identifier> getIdentifiers() {
        return identifiers;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setIdentifiers(ArrayList<Identifier> identifiers) {
        this.identifiers = identifiers;
    }


}
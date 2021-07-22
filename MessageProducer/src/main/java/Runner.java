import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        MessageProducer messageProducer = new MessageProducer("input", "localhost", "29092");

        Person test = new Person("Max", "Musterstr.", "2", "Musterburg", "", "4567892", "","DE", "", "","");
        TrackingEvent eventValid = new TrackingEvent(TrackingEvent.EventType.Vorankündigung,"Berlin Zentral", "Die Sendung wurde angekündigt.", test,test);

        Identifier id = new Identifier();
        id.setValue("b083185032517b");
        ArrayList<Identifier> ids = new ArrayList<>();
        ids.add(id);

        eventValid.setIdentifiers(ids);

        //TrackingEvent eventInvalid = new TrackingEvent(TrackingEvent.EventType.Vorankündigung,"Berlin Zentral", "Die Sendung wurde angekündigt.", test,test);
        //eventInvalid.setUuid("Banane");

        messageProducer.sendMessage("1",eventValid.toJson());
        //messageProducer.sendMessage("2", eventInvalid.toJson());
        messageProducer.closeProducer();
    }
}

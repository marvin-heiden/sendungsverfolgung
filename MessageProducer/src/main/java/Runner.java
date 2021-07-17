import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Runner {
    public static void main(String[] args) {
        MessageProducer messageProducer = new MessageProducer("input", "localhost", "9092");

        Person test = new Person("Max", "Musterstr.", "2", "Musterburg", "", "4567892", "","Germany", "", "","");
        TrackingEvent event = new TrackingEvent("d4g5f6sdfg4fd6", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()), TrackingEvent.EventType.Vorankündigung,"Berlin Zentral", "Die Sendung wurde angekündigt.", test,test);

        messageProducer.sendMessage("1",event.toJson());
        messageProducer.closeProducer();
    }
}

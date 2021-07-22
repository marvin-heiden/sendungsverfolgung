import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class MessageProducer {
    private Properties properties;
    private KafkaProducer kafkaProducer;
    private String topic;

    public MessageProducer(String topic, String kafkaHost, String kafkaPort) {
        properties = new Properties();
        properties.put("bootstrap.servers", kafkaHost + ":" + kafkaPort);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer(properties);
        this.topic = topic;
    }

    public void sendMessage(String key, ObjectNode value) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode messageHeader = mapper.createObjectNode();
        messageHeader.put("MsgUUID", UUID.randomUUID().toString());
        messageHeader.put("MsgSender", "MessageProducer");
        messageHeader.put("MsgReceiver", "TrackingSystem");
        messageHeader.put("MsgTimestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));

        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.set("MessageHeader", messageHeader);
        rootNode.set("Event", value);

        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

            ProducerRecord producerRecord = new ProducerRecord(topic, key, jsonString); // Partition Key can also be declared! -> Modulo Operator
            kafkaProducer.send(producerRecord);
            System.out.println("Message delivered.");
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void closeProducer() {
        kafkaProducer.close();
    }
}

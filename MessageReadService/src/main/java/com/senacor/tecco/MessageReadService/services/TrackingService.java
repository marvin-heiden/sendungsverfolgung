package com.senacor.tecco.MessageReadService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.tecco.MessageReadService.config.KafkaConsumerConfig;
import com.senacor.tecco.MessageReadService.models.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrackingService {


    public TrackingHistory createTrackingHistory(List<Message> messages, TreeSet<String> identifiers) {
        ArrayList<Step> history = new ArrayList<>();
        for (Message message : messages) {
            history.add(new Step(
                    message.getEvent().getCreationTimestamp(),
                    message.getEvent().getMessage(),
                    message.getEvent().getType()
            ));
        }

        Event lastMessageEvent = messages.get(messages.size()-1).getEvent();

        TrackingHistory trackingHistory = new TrackingHistory(
                lastMessageEvent.getReceiver(),
                lastMessageEvent.getSender(),
                history,
                identifiers
        );

        return trackingHistory;
    }

    public TrackingHistory getAllAssociatedMessages(String trackingNumber) {
        // Get initial messages by tracking number
        ArrayList<Message> messages = getMessagesByTrackingNumber(trackingNumber);

        TreeSet<String> foundNumbers = new TreeSet<>();
        foundNumbers.add(trackingNumber);

        ObjectMapper mapper = new ObjectMapper();

        // Check for associated messages referenced by Identifier
        for (int i = 0; i < messages.size(); i++) {

            //ArrayNode identifiers = (ArrayNode) node.get("Event").get("Identifiers");
            for (Identifier identifier : messages.get(i).getEvent().getIdentifiers()) {
                String value = identifier.getValue();

                // Add new messages if new tracking number was found
                if (foundNumbers.add(value)) {
                    messages.addAll(getMessagesByTrackingNumber(value));
                }
            }
        }

        return createTrackingHistory(
                messages.stream()
                .distinct()
                .sorted(Comparator.comparing(o -> o.getEvent().getCreationTimestamp()))
                .collect(Collectors.toList()),
                foundNumbers);
    }

    public ArrayList<Message> getMessagesByTrackingNumber(String trackingNumber) {
        // Create Container for return values
        ArrayList<Message> messages = new ArrayList<>();

        // Create consumer
        Properties consumerProps = KafkaConsumerConfig.getConsumerProps();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProps);

        // Assign partition
        int partitionNumber = Math.floorMod(trackingNumber.hashCode(), 10);
        TopicPartition partition = new TopicPartition("storage", partitionNumber);
        LinkedList<TopicPartition> partitions = new LinkedList<>();
        partitions.add(partition);
        consumer.assign(partitions);
        System.out.println("Assigned to partition "+partitionNumber);

        // Retrieve all current messages on partition
        long maxOffset = consumer.endOffsets(partitions).get(partition) - 1;
        long currentOffset = 0;
        ConsumerRecords<String, String> records;
        ObjectMapper mapper = new ObjectMapper();

        outerLoop:
        while (currentOffset < maxOffset) {
            records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                if (record.key().equals(trackingNumber)) {
                    try {
                        messages.add(mapper.readValue(record.value(), Message.class));
                        System.out.println(record.value());
                        // Stop searching if final message was already processed
                        if (record.value().contains("Zugestellt")) break outerLoop;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                currentOffset++;
            }
        }

        // consumer.seekToBeginning()
        consumer.close();

        return messages;
    }
}

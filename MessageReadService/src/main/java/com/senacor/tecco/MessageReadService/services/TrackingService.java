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

    public ArrayList<Message> getMessagesByTrackingNumber(String trackingNumber) {
        // Create Container for return values
        ArrayList<Message> messages = new ArrayList<>();

        // Create consumer
        Properties consumerProps = KafkaConsumerConfig.getConsumerProps();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);

        // Assign partition
        int partitionNumber = Math.floorMod(trackingNumber.hashCode(), 1);
        TopicPartition partition = new TopicPartition("storage", partitionNumber);
        LinkedList<TopicPartition> partitions = new LinkedList<>();
        partitions.add(partition);
        consumer.assign(partitions);

        // Retrieve all current messages on partition
        long maxOffset = consumer.endOffsets(partitions).get(partition);
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

    public TrackingHistory createTrackingHistory(List<Message> messages, HashSet<String> identifiers) {
        ArrayList<Step> history = new ArrayList<>();
        for (Message message : messages) {
            history.add(new Step(
                    message.getEvent().getCreationTimestamp(),
                    message.getEvent().getMessage(),
                    message.getEvent().getType()
            ));
        }

        Event lastMessageEvent = messages.get(messages.size() - 1).getEvent();

        return new TrackingHistory(
                lastMessageEvent.getReceiver(),
                lastMessageEvent.getSender(),
                history,
                identifiers
        );
    }

    public TrackingHistory getAllAssociatedMessages(String trackingNumber) {
        // Get initial messages by tracking number
        ArrayList<Message> messages = getMessagesByTrackingNumber(trackingNumber);

        // Return empty if nothing was found
        if (messages.size() == 0) return new TrackingHistory(new Person(), new Person(), new ArrayList<>(), new HashSet<>());

        HashSet<String> foundNumbers = new HashSet<>();
        foundNumbers.add(trackingNumber);

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
}

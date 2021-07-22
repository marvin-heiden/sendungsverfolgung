package com.senacor.tecco.MessageReadService.services;

import com.senacor.tecco.MessageReadService.kafka.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class TrackingService {

    public List<String> getTrackingHistory(String trackingNumber) {
        // Create Container for return values
        LinkedList<String> events = new LinkedList<>();

        // Create consumer
        Properties consumerProps = KafkaConsumerConfig.getConsumerProps();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProps);

        // Assign partition
        int partitionNumber = Math.floorMod(trackingNumber.hashCode(), 10);
        TopicPartition partition = new TopicPartition("storage", partitionNumber);
        LinkedList<TopicPartition> partitions = new LinkedList<>();
        partitions.add(partition);
        consumer.assign(partitions);

        // Retrieve all current messages on partition
        long maxOffset = consumer.endOffsets(partitions).get(partition) - 1;
        long currentOffset = 0;
        ConsumerRecords<String, String> records;
        while (currentOffset < maxOffset) {
            records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record: records) {
                if (record.key().equals(trackingNumber)) events.add(record.value());
                currentOffset = record.offset();
            }
        }

        //events.forEach(System.out::println);

        consumer.close();

        return events;
    }
}

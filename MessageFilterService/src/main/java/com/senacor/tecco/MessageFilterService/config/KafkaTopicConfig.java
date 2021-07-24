package com.senacor.tecco.MessageFilterService.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic topicInput() {
        return TopicBuilder.name("input")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicStorage() {
        return TopicBuilder.name("storage")
                .partitions(10)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "7776000000") // Retention time of 90 Days
                .build();
    }

    @Bean
    public NewTopic topicError() {
        return TopicBuilder.name("error")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
package com.senacor.tecco.MessageFilterService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.tecco.MessageFilterService.models.Message;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestKafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String saslJaasConfig;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ProducerFactory<String, Message> testProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        config.put(
                "security.protocol",
                securityProtocol);
        config.put(
                "sasl.jaas.config",
                saslJaasConfig);
        config.put(
                "sasl.mechanism",
                saslMechanism);

        return new DefaultKafkaProducerFactory<>(
                config,
                new StringSerializer(),
                new JsonSerializer<Message>(objectMapper)
        );
    }

    @Bean
    public KafkaTemplate<String, Message> testKafkaTemplate(){
        return new KafkaTemplate<>(testProducerFactory());
    }
}

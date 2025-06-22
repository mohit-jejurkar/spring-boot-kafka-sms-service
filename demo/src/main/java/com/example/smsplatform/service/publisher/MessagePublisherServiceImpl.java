package com.example.smsplatform.service.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MessagePublisherServiceImpl implements MessagePublisherService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public MessagePublisherServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishToKafka(String topic, Map<String, Object> message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(topic, payload);
            log.info("Message published to Kafka: topic={}, payload={}", topic, payload);
        } catch (Exception e) {
            log.error("Failed to publish message to Kafka", e);
            throw new RuntimeException("Kafka publish failed");
        }
    }
}

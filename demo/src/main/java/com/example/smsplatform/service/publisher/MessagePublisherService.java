package com.example.smsplatform.service.publisher;

import java.util.Map;

public interface MessagePublisherService {
    void publishToKafka(String topic, Map<String, Object> message);
}
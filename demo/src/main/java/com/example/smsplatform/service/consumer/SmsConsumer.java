package com.example.smsplatform.service.consumer;

import com.example.smsplatform.dao.entity.SendMsg;
import com.example.smsplatform.dao.repository.SendMsgRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Slf4j
@Component
public class SmsConsumer {

    @Autowired
    private SendMsgRepository sendMsgRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "sms-topic", groupId = "sms-consumer-group")
    public void consume(String payload) {
        log.info("Received message from Kafka: {}", payload);

        try {
            JsonNode node = objectMapper.readTree(payload);
            SendMsg msg = buildSendMsg(node);
            SendMsg savedMsg = sendMsgRepo.save(msg);
            log.info("Message saved to DB successfully: ack_id={}, account_id={}", savedMsg.getAck_id(), savedMsg.getAccount_id());

        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", payload, e);
        }
    }

    private SendMsg buildSendMsg(JsonNode node) {
        SendMsg msg = new SendMsg();
        msg.setAck_id(node.get("ack_id").asText());
        msg.setAccount_id(node.get("account_id").asLong());
        msg.setMobile(node.get("mobile").asLong());
        msg.setMessage(node.get("message").asText());
        msg.setStatus("NEW");
        msg.setReceivedTs(new Timestamp(System.currentTimeMillis()));
        return msg;
    }
}

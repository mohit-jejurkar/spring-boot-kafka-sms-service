package com.example.smsplatform.service.processor;

import com.example.smsplatform.dao.entity.SendMsg;
import com.example.smsplatform.dao.repository.SendMsgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Component
public class MessageProcessor {
    @Autowired
    private SendMsgRepository repo;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${sms.telco.url}")
    private String vendorUrl;

    @Scheduled(fixedRate = 1000)
    public void processMessages() {
        List<SendMsg> newMsgs = repo.findByStatus("NEW");
        for (SendMsg msg : newMsgs) {

            try {
                msg.setStatus("INPROCESS");
                repo.save(msg);
                log.info("Processing message: ack_id={}", msg.getAck_id());
                String url = String.format(vendorUrl, msg.getAccount_id(), msg.getMobile(), URLEncoder.encode(msg.getMessage(), StandardCharsets.UTF_8));
                String response;
                response = restTemplate.getForObject(url, String.class);
                msg.setTelcoResponse(response);
                msg.setStatus("SENT");
                msg.setSentTs(new Timestamp(System.currentTimeMillis()));
                repo.save(msg);
            } catch (Exception e) {
                msg.setStatus("FAILED");
                repo.save(msg);
                log.error("Failed to send SMS: ack_id={}, error={}", msg.getAck_id(), e.getMessage(), e);
            }

        }
    }
}
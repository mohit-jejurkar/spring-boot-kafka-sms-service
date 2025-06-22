package com.example.smsplatform.controller;

import com.example.smsplatform.dao.entity.User;
import com.example.smsplatform.dao.repository.UserRepository;
import com.example.smsplatform.utils.Constants;
import com.example.smsplatform.service.publisher.MessagePublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/telco")
public class CustomerController {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  MessagePublisherService messagePublisherService;



    @GetMapping("/sendmsg")
    public String sendMessage(@RequestParam(Constants.Request.USERNAME) String username,
                              @RequestParam(Constants.Request.PASSWORD) String password,
                              @RequestParam(Constants.Request.MOBILE) Long mobile,
                              @RequestParam(Constants.Request.MESSAGE) String message) throws JsonProcessingException {

        if (String.valueOf(mobile).length() != Constants.Validation.MOBILE_LENGTH ||
                message.length() > Constants.Validation.MAX_MESSAGE_LENGTH) {
            log.warn("Validation failed: mobile={}, messageLength={}", mobile, message.length());
            return Constants.Response.STATUS_REJECTED;
        }


        Optional<User> userOpt = userRepository.findByUsernameAndPassword(username, password);
        if (userOpt.isEmpty()) {
            log.warn("Authentication failed for username={}", username);
            return Constants.Response.STATUS_REJECTED;
        }


        String ackId = UUID.randomUUID().toString();
        Map<String, Object> payload = Map.of(
                Constants.Request.ACK_ID, ackId,
                Constants.Request.ACCOUNT_ID, userOpt.get().getAccountId(),
                Constants.Request.MOBILE, mobile,
                Constants.Request.MESSAGE, message
        );


        messagePublisherService.publishToKafka(Constants.Kafka.SMS_TOPIC, payload);

        log.info("Message accepted and sent to Kafka for ackId={}", ackId);
        return Constants.Response.STATUS_ACCEPTED_PREFIX + ackId;
    }
}

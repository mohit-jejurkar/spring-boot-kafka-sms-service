package com.example.smsplatform.controller;

import com.example.smsplatform.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/telco")
public class TelcoOperatorController {

    @GetMapping("/smsc")
    public String receiveSms(@RequestParam Long account_id,
                             @RequestParam Long mobile,
                             @RequestParam String message) {
        try {
            log.info("Received SMS request: account_id={}, mobile={}, message={}", account_id, mobile, message);

            if (account_id == null || String.valueOf(mobile).length() != Constants.Validation.MOBILE_LENGTH
                    || message.length() > Constants.Validation.MAX_MESSAGE_LENGTH) {
                log.warn("Validation failed: account_id={}, mobile={}, message_length={}",
                        account_id, mobile, message.length());
                return Constants.Response.STATUS_REJECTED;
            }

            String ackId = UUID.randomUUID().toString();
            log.info("SMS accepted with request_id: {}", ackId);
            return Constants.Response.STATUS_ACCEPTED_PREFIX + ackId;

        } catch (Exception e) {
            log.error("Exception in TelcoOperatorController: {}", e.getMessage(), e);
            return Constants.Response.STATUS_REJECTED;
        }
    }
}

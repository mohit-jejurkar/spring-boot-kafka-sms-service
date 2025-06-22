package com.example.smsplatform.utils;

public class Constants {

    public static class Request {
        public static final String ACK_ID = "ack_id";
        public static final String ACCOUNT_ID = "account_id";
        public static final String MOBILE = "mobile";
        public static final String MESSAGE = "message";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
    }

    public static class Response {
        public static final String STATUS_REJECTED = "STATUS: REJECTED~~ RESPONSE_CODE: FAILURE";
        public static final String STATUS_ACCEPTED_PREFIX = "STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~";
    }

    public static class Kafka {
        public static final String SMS_TOPIC = "sms-topic";
    }

    public static class Validation {
        public static final int MOBILE_LENGTH = 10;
        public static final int MAX_MESSAGE_LENGTH = 160;
    }
}

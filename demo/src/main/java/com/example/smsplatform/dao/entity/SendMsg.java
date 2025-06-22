package com.example.smsplatform.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "send_msg")
@Data
public class SendMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long mobile;
    private String message;
    private String status;
    private Timestamp receivedTs;
    private Timestamp sentTs;
    private String telcoResponse;
    private Long account_id;
    private String ack_id;
}
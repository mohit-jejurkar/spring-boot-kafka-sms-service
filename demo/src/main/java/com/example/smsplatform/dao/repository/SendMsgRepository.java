package com.example.smsplatform.dao.repository;

import com.example.smsplatform.dao.entity.SendMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SendMsgRepository extends JpaRepository<SendMsg, Long> {
    List<SendMsg> findByStatus(String status);
}

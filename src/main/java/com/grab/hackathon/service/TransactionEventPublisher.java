package com.grab.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grab.hackathon.model.Transaction;
import com.grab.hackathon.model.TransactionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TransactionEventPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    public void publishTransactionEvent(Transaction txn, TransactionResult result) throws JsonProcessingException {
//        Map<String, Object> event = new HashMap<>();
//        event.put("transactionId", txn.getTransactionId());
//        event.put("status", result.getStatus().toString());
//        event.put("sender", txn.getSenderAccountId());
//        event.put("receiver", txn.getReceiverAccountId());
//        event.put("timestamp", LocalDateTime.now().toString());
//
//        String message = new ObjectMapper().writeValueAsString(event);
//        kafkaTemplate.send("transaction-events-topic", message);
//    }

    public void publishTransactionEvent(Transaction txn, TransactionResult result) throws JsonProcessingException {
        Map<String, Object> event = new HashMap<>();
        event.put("transactionId", txn.getTransactionId());
        event.put("status", result.getStatus().toString());
        event.put("sender", txn.getSenderAccountId());
        event.put("receiver", txn.getReceiverAccountId());
        event.put("timestamp", LocalDateTime.now().toString());

        String message = new ObjectMapper().writeValueAsString(event);

        log.info("✅ [MOCK Kafka] Would publish to topic 'transaction-events-topic': {}", message);
    }
}

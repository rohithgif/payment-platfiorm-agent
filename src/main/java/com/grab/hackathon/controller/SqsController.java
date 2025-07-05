package com.grab.hackathon.controller;

import com.grab.hackathon.publisher.SqsPublisher;
import com.grab.hackathon.request.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sqs")
public class SqsController {
    
    @Autowired
    private SqsPublisher sqsPublisher;
    
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody TransactionEvent message) {
        sqsPublisher.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }
    
    @PostMapping("/send/{queueName}")
    public ResponseEntity<String> sendMessageToQueue(
            @PathVariable String queueName,
            @RequestBody String message) {
        sqsPublisher.publishMessageToQueue(queueName, message);
        return ResponseEntity.ok("Message sent to " + queueName + " successfully");
    }
} 
package com.grab.hackathon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grab.hackathon.model.PaymentRequest;
import com.grab.hackathon.model.TransactionResult;
import com.grab.hackathon.service.PaymentPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentPlatformController {

    @Autowired
    private PaymentPlatformService paymentPlatformService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<Object> initiatePayment(@RequestBody PaymentRequest request) throws JsonProcessingException {
        Object result = paymentPlatformService.initiatePaymentRequest(request);
        return ResponseEntity.ok(result);
    }
}


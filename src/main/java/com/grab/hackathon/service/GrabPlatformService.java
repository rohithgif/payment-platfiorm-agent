package com.grab.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grab.hackathon.controller.TransactionController;
import com.grab.hackathon.model.*;
import com.grab.hackathon.publisher.SqsPublisher;
import com.grab.hackathon.repository.TransactionRepository;
import com.grab.hackathon.request.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrabPlatformService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SqsPublisher sqsPublisher;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionController transactionController;

    public TransactionResult initiateTransaction(Transaction transaction) throws JsonProcessingException {

        TransactionResult result;

        try {
            result = transactionController.initiateTransaction(transaction).getBody();
        } catch (Exception e) {
            result = new TransactionResult(
                    TransactionStatus.FAILED,
                    e
            );
        }

        // Publish event to SQS
        sqsPublisher.sendMessage(new TransactionEvent(result));

        return result;
    }

    public static List<UserPaymentOptionDetails> getPaymentOptions(String userId) {
        List<UserPaymentOptionDetails> options = new ArrayList<>();

        // Add mock Paypal
        Paypal paypal = new Paypal(userId, "PAYPAL_1", 1, "user@paypal.com");
        options.add(paypal);

        // Add mock Card
        Card card = new Card(userId, "CARD_1", 1, 1234567890);
        options.add(card);

        // Add mock GrabWallet
        Wallet wallet = new Wallet(userId, "WALLET_1", 1);
        options.add(wallet);

        // Add mock NEFT
        Neft neft = new Neft(userId, "NEFT_1", "NEFT001", "Mock Bank", "1234567890", "BANK0001");
        options.add(neft);

        return options;
    }
}

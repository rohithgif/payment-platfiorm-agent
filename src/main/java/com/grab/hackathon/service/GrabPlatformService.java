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
import java.util.Random;

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
        Paypal paypal = new Paypal(userId, "PAYPAL_1", 1, "user@paypal.com", new OffersDetails(
                "OFFER_1", "5% cashback on PayPal transactions", 5
        ));
        options.add(paypal);

        // Add mock Card
        Card card = new Card(userId, "CARD_1", 1, 1234567890, new OffersDetails(
                "OFFER_1", "10% off on card transactions", 10
        ));
        options.add(card);

        // Add mock GrabWallet
        Wallet wallet = new Wallet(userId, "WALLET_1", 1, new OffersDetails(
                "OFFER_1", "2% cashback on wallet transactions", 2
        ));
        options.add(wallet);

        // Add mock NEFT
        Neft neft = new Neft(userId, "NEFT_1", "NEFT001", "Mock Bank", "1234567890", "BANK0001", new OffersDetails(
                "OFFER_1", "5% cashback on NEFT transactions", 5
        ));
        options.add(neft);

        return options;
    }

    public BankHealthStatus getBankHealthStatus(String bankName) {
        boolean isHealthy = new Random().nextBoolean();
        BankHealthStatus status = isHealthy ? BankHealthStatus.HEALTHY : BankHealthStatus.UNHEALTHY;
        return status;
    }

    public int getCashbackPercentage(String paymentOptionId) {
        // Mock cashback percentage based on payment option ID
        switch (paymentOptionId) {
            case "PAYPAL_1":
                return 5; // 5% cashback for PayPal
            case "CARD_1":
                return 3; // 3% cashback for Card
            case "WALLET_1":
                return 2; // 2% cashback for Wallet
            case "NEFT_1":
                return 1; // 1% cashback for NEFT
            default:
                return 0; // No cashback for unknown options
        }
    }

    public int getDiscountPercentage(String paymentOptionId) {
        // Mock discount percentage based on payment option ID
        switch (paymentOptionId) {
            case "PAYPAL_1":
                return 10; // 10% discount for PayPal
            case "CARD_1":
                return 5; // 5% discount for Card
            case "WALLET_1":
                return 2; // 2% discount for Wallet
            case "NEFT_1":
                return 0; // No discount for NEFT
            default:
                return 0; // No discount for unknown options
        }
    }

    public List<OffersDetails>getUserOffers(String userId) {
        List<OffersDetails> offers = new ArrayList<>();

        // Mock offers for the user
        offers.add(new OffersDetails("OFFER_1", "10% off on next transaction", 10));
        offers.add(new OffersDetails("OFFER_2", "5% Discount on current transaction", 5));

        return offers;
    }
}

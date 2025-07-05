package com.grab.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grab.hackathon.entity.BankCard;
import com.grab.hackathon.entity.GrabWallet;
import com.grab.hackathon.entity.Payment;
import com.grab.hackathon.entity.PaymentDetails;
import com.grab.hackathon.entity.User;
import com.grab.hackathon.model.*;
import com.grab.hackathon.publisher.SqsPublisher;
import com.grab.hackathon.repository.PaymentDetailsRepository;
import com.grab.hackathon.repository.PaymentRepository;
import com.grab.hackathon.repository.TransactionRepository;
import com.grab.hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentPlatformService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRecommendationService recommendationService;

    @Autowired
    private SqsPublisher sqsPublisher;

    @Autowired
    private GrabPlatformService grabPlatformService;

    /**
     * Initiates a payment request by performing the following steps:
     * 1. Fetches the user based on the provided user ID.
     * 2. Saves the payment details.
     * 3. Saves the payment record.
     * 4. Recommends the best payment gateway using PSP and LLM.
     * 5. Creates a transaction record.
     * 6. Calls GrabPlatform to execute the transaction.
     * 7. Saves the transaction with its final status.
     *
     * @param request The payment request containing user ID and amount.
     * @return The result of the transaction initiation.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */


    public TransactionResult initiatePaymentRequest(PaymentRequest request) throws JsonProcessingException {
        // 1. Fetch User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserPaymentOptionDetails> paymentOptions = grabPlatformService.getPaymentOptions(String.valueOf(user.getId()));

        // 2. Save PaymentDetails
        PaymentDetails details = PaymentDetails.builder()
                .amount(request.getAmount())
                .build();
        paymentDetailsRepository.save(details);

        // 3. Save Payment
        Payment payment = Payment.builder()
                .user(user)
                .paymentDetails(details)
                .build();
        paymentRepository.save(payment);

        // 4. Get recommended gateway from PSP + LLM
        String gatewayName = recommendationService.recommendBestPaymentGatewayOption(paymentOptions);

        // 5. Create Transaction
        Transaction transaction = Transaction.builder()
                .payment(payment)
                .senderAccountId("sender@example.com")
                .receiverAccountId("receiver@example.com")
                .transactionDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .build();

        // 6. Call GrabPlatform to execute transaction
        TransactionResult result = grabPlatformService.initiateTransaction(transaction);

        // 7. Save Transaction with final status
        transaction.setTransactionStatus(result.getStatus() != null ? result.getStatus() : TransactionStatus.FAILED);
        transactionRepository.save(transaction);

        return result;
    }
}

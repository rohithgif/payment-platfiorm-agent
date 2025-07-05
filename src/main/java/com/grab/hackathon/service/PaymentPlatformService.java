package com.grab.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grab.hackathon.entity.BankCard;
import com.grab.hackathon.entity.GrabWallet;
import com.grab.hackathon.entity.Payment;
import com.grab.hackathon.entity.PaymentDetails;
import com.grab.hackathon.entity.User;
import com.grab.hackathon.model.PaymentRequest;
import com.grab.hackathon.model.Transaction;
import com.grab.hackathon.model.TransactionResult;
import com.grab.hackathon.model.TransactionStatus;
import com.grab.hackathon.publisher.SqsPublisher;
import com.grab.hackathon.repository.PaymentDetailsRepository;
import com.grab.hackathon.repository.PaymentRepository;
import com.grab.hackathon.repository.TransactionRepository;
import com.grab.hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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


    public TransactionResult initiatePaymentRequest(PaymentRequest request) throws JsonProcessingException {
        // 1. Fetch User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserPaymentOptionDetails
        GrabWallet wallet = user.getGrabWallet();
        if (wallet == null || wallet.getBankCard() == null) {
            throw new RuntimeException("GrabWallet or BankCard not configured for user");
        }
        BankCard card = wallet.getBankCard();

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
        String gatewayName = recommendationService.recommendBestPaymentGatewayOption(wallet);

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

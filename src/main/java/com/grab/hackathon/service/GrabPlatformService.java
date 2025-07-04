package com.grab.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grab.hackathon.model.Transaction;
import com.grab.hackathon.model.TransactionResult;
import com.grab.hackathon.model.TransactionStatus;
import com.grab.hackathon.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GrabPlatformService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionEventPublisher publisher; // your Kafka or SQS publisher

    public TransactionResult initiateTransaction(Transaction transaction) throws JsonProcessingException {
        String url = "http://localhost:8080/v1/transactions/initiate-transaction";

        TransactionResult result;

        try {
            result = restTemplate.postForObject(url, transaction, TransactionResult.class);
        } catch (Exception e) {
            result = new TransactionResult(TransactionStatus.FAILED);
        }

        // Publish event to Kafka/SQS
        publisher.publishTransactionEvent(transaction, result);

        return result;
    }
}

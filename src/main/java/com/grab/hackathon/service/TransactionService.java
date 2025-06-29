package com.grab.hackathon.service;

import com.grab.hackathon.model.Transaction;
import com.grab.hackathon.model.TransactionStatus;
import com.grab.hackathon.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionDetailsBySenderAccountId(String senderAccountId, TransactionStatus transactionStatus) {
        try {
            log.info("Fetching transaction details for sender account ID: {}", senderAccountId);
            return transactionRepository.findTransactionBySenderAccountIdAndTransactionStatus(senderAccountId, transactionStatus);
        } catch (Exception e) {
            log.error("Error fetching transaction details for sender account ID: {}", senderAccountId, e);
            throw e;
        }
    }

    public void createTransaction(Transaction transaction) {
        try {
            log.info("Creating transaction for sender account ID: {}", transaction.getSenderAccountId());
            transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Error creating transaction for sender account ID: {}", transaction.getSenderAccountId(), e);
            throw e;
        }
    }

    public List<Transaction> getAllTransactions() {
        try {
            log.info("Fetching all transactions");
            return transactionRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all transactions", e);
            throw e;
        }
    }
}

package com.grab.hackathon.controller;

import com.grab.hackathon.model.Transaction;
import com.grab.hackathon.model.TransactionResult;
import com.grab.hackathon.model.TransactionStatus;
import com.grab.hackathon.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/get-transaction-details")
    public List<Transaction> getTransactionDetailsBySenderAccountId(@RequestParam String senderAccountId,@RequestParam(required = false) TransactionStatus transactionStatus) {
        return transactionService.getTransactionDetailsBySenderAccountId(senderAccountId, transactionStatus);
    }

    @PostMapping("/create-transaction")
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return ResponseEntity.ok("Transaction created successfully");
    }

    @GetMapping("/get-transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/initiate-transaction")
    public ResponseEntity<TransactionResult> initiateTransaction(@RequestBody Transaction transaction) {
        if (transaction.getPaymentType() .equals("Exception")) {
            throw new RuntimeException("Transaction failed due to internal Visa Card Network call");
        }
        TransactionStatus status = TransactionStatus.SUCCESS;
        return ResponseEntity.ok(new TransactionResult(status,"Transaction Successful"));
    }

}

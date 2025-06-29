package com.grab.hackathon.repository;

import com.grab.hackathon.model.Transaction;
import com.grab.hackathon.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionBySenderAccountIdAndTransactionStatus(String senderAccountId, TransactionStatus transactionStatus);

}

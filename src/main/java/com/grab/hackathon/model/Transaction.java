package com.grab.hackathon.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private int transactionId;
    private String senderAccountId;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionDate;
}
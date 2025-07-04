package com.grab.hackathon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResult {
    private TransactionStatus status;
    Object message;

    public TransactionResult(TransactionStatus status , Object message) {
        this.status = status;
        this.message = message;
    }
}

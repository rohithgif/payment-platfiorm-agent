package com.grab.hackathon.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PaymentRequest {
    private Long userId;
    private Double amount;
    private String senderAccountId;
    private String receiverAccountId;
}


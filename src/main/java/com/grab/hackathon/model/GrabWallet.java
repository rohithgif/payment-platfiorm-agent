package com.grab.hackathon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrabWallet extends UserPaymentOptionDetails {
    private int walletId;
    private double balance;

    @Builder
    public GrabWallet(String userId, String paymentOptionId, int walletId, double balance) {
        super(userId, paymentOptionId);
        this.walletId = walletId;
        this.balance = balance;
    }

    @Override
    public void pay() {
        System.out.println("Payment made using GrabWallet with ID: " + walletId +
                           ", Balance: " + balance +
                           " for User ID: " + userId);
    }
} 
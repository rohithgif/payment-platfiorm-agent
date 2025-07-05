package com.grab.hackathon.model;

public class Wallet extends UserPaymentOptionDetails{
    private int userId;
    private int walletId;
    public Wallet(String userId, String paymentOptionId, int walletId, OffersDetails offersDetails) {
        super(userId, paymentOptionId, offersDetails);
        this.walletId = walletId;
        this.userId = Integer.parseInt(userId);
    }
    @Override
    public void pay(){
        System.out.println("Payment made using Wallet with ID: " + walletId + " for User ID: " + userId);
    }
}

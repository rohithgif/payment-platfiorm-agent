package com.grab.hackathon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paypal extends UserPaymentOptionDetails {
    private int paypalId;
    private String emailId;

    @Builder
    public Paypal(String userId, String paymentOptionId, int paypalId, String emailId) {
        super(userId, paymentOptionId);
        this.paypalId = paypalId;
        this.emailId = emailId;
    }

    @Override
    public void pay() {
        System.out.println("Payment made using PayPal with ID: " + paypalId +
                           ", Email: " + emailId +
                           " for User ID: " + userId);
    }
}

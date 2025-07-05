package com.grab.hackathon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class UserPaymentOptionDetails {
    protected String userId;
    protected String paymentOptionId;

    protected UserPaymentOptionDetails() {
        // Default constructor for subclasses
    }

    protected UserPaymentOptionDetails(String userId, String paymentOptionId) {
        this.userId = userId;
        this.paymentOptionId = paymentOptionId;
    }

    public abstract void pay();
}

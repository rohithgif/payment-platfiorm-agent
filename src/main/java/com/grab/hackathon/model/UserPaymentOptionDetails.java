package com.grab.hackathon.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class UserPaymentOptionDetails {
    protected String userId;
    protected String paymentOptionId;
    protected OffersDetails offersDetails;

    protected UserPaymentOptionDetails() {
        // Default constructor for subclasses
    }

    protected UserPaymentOptionDetails(String userId, String paymentOptionId, OffersDetails offersDetails) {
        this.userId = userId;
        this.paymentOptionId = paymentOptionId;
        this.offersDetails = offersDetails;
    }

    public abstract void pay();
}

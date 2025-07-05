package com.grab.hackathon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card extends UserPaymentOptionDetails {
    private int cardId;
    private int cardNumber;

    @Builder
    public Card(String userId, String paymentOptionId, int cardId, int cardNumber) {
        super(userId, paymentOptionId);
        this.cardId = cardId;
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay() {
        System.out.println("Payment made using Card with ID: " + cardId +
                           ", Card Number: " + cardNumber +
                           " for User ID: " + userId);
    }
}

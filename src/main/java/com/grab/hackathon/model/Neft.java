package com.grab.hackathon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Neft extends UserPaymentOptionDetails {
    private String neftId;
    private String bankName;
    private String accountNumber;
    private String ifscCode;

    @Builder
    public Neft(String userId, String paymentOptionId, String neftId, String bankName, String accountNumber, String ifscCode, OffersDetails offersDetails) {
        super(userId, paymentOptionId, offersDetails);
        this.neftId = neftId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
    }

    @Override
    public void pay() {
        System.out.println("Payment made using NEFT with ID: " + neftId +
                           ", Bank: " + bankName +
                           ", Account Number: " + accountNumber +
                           ", IFSC Code: " + ifscCode +
                           " for User ID: " + userId);
    }
}

package com.grab.hackathon.service;

import com.grab.hackathon.entity.BankCard;
import com.grab.hackathon.entity.GrabWallet;
import com.grab.hackathon.model.PSPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentRecommendationService {

    @Autowired
    private PaymentServiceProvider paymentServiceProvider;

    @Autowired
    private LLMService llmService;

    public String recommendBestPaymentGatewayOption(GrabWallet wallet) {
        BankCard card = wallet.getBankCard();

        // Step 1: Call PSP Lambda with BankCard details
        PSPResponse pspResponse = paymentServiceProvider.callPSPLambda(card);

        // Step 2: Extract recommended gateway from PSP
        String pspRecommended = pspResponse.getRecommendedGateway();

        // Step 3: Return the final gateway (from LLM)
        return llmService.getBestGatewayRecommendation(pspResponse);
    }
}

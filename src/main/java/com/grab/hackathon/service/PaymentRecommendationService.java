package com.grab.hackathon.service;

import com.grab.hackathon.entity.BankCard;
import com.grab.hackathon.entity.GrabWallet;
import com.grab.hackathon.model.PSPResponse;
import com.grab.hackathon.model.UserPaymentOptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentRecommendationService {

    @Autowired
    private PaymentServiceProvider paymentServiceProvider;

    @Autowired
    private LLMService llmService;

    public String recommendBestPaymentGatewayOption(List<UserPaymentOptionDetails> paymentOptions){

        // Step 3: Return the final gateway (from LLM)
//        return llmService.getBestGatewayRecommendation(paymentOptions);
         return "Go with Paypal";
    }
}

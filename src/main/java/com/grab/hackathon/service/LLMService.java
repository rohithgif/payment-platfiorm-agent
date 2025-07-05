package com.grab.hackathon.service;

import com.grab.hackathon.model.PSPResponse;
import com.grab.hackathon.model.UserPaymentOptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LLMService {

    @Autowired
    private RestTemplate restTemplate;

//    public String getBestGatewayRecommendation(PSPResponse pspResponse) {
//        String url = "https://mock-llm-lambda.com/api/llm-suggest-gateway";
//        try {
//            return restTemplate.postForObject(url, pspResponse, String.class);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to call LLM Lambda", e);
//        }
//    }

    public String getBestGatewayRecommendation(List<UserPaymentOptionDetails>paymentOptionDetails) {
        return "FallbackPay";
    }
}

package com.grab.hackathon.service;

import com.grab.hackathon.model.PSPResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public String getBestGatewayRecommendation(PSPResponse pspResponse) {
        if ("HEALTHY".equalsIgnoreCase(pspResponse.getBankHealthStatus()) &&
                pspResponse.getOffers().stream().anyMatch(o -> o.getCashbackAmount() > 20)) {
            return pspResponse.getRecommendedGateway();
        }
        return "FallbackPay";
    }
}

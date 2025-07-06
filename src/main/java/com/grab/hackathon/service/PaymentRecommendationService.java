package com.grab.hackathon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grab.hackathon.entity.BankCard;
import com.grab.hackathon.entity.GrabWallet;
import com.grab.hackathon.model.PSPResponse;
import com.grab.hackathon.model.UserPaymentOptionDetails;
import com.grab.hackathon.model.OffersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentRecommendationService {

    @Autowired
    private PaymentServiceProvider paymentServiceProvider;

    @Autowired
    private LLMService llmService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String recommendBestPaymentGatewayOption(List<UserPaymentOptionDetails> paymentOptions) {
        try {
            List<Map<String, Object>> paymentOptionsList = new ArrayList<>();

            // Prepare flattened payment options
            for (UserPaymentOptionDetails option : paymentOptions) {
                Map<String, Object> optionMap = new HashMap<>();
                optionMap.put("payment_id", option.getPaymentOptionId());

                OffersDetails offers = option.getOffersDetails();
                if (offers != null) {
                    optionMap.put("cashback_amount", offers.getCashbackAmount());
                    optionMap.put("offer_description", offers.getDescription());
                }

                paymentOptionsList.add(optionMap);
            }

            // Create request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("options", paymentOptionsList);

            // Convert to JSON string (for curl printout)
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            System.out.println("Equivalent curl command:");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

// Send raw JSON string as request body
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8000/api/query-payment-option-recommendation/";
            String response = restTemplate.postForObject(url, entity, String.class);
            return response;


        } catch (Exception e) {
            System.out.println("Error calling recommendation API: " + e.getMessage());
            return "Go with PayPal (fallback recommendation)";
        }
    }
}

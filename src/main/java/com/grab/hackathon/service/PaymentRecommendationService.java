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
            // Create list to hold payment option details
            List<Map<String, Object>> paymentOptionsList = new ArrayList<>();


            // Extract relevant information from each payment option
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

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("options", paymentOptions);

// Convert request body to JSON string
            String jsonBody = objectMapper.writeValueAsString(requestBody);

// Log equivalent curl
            String curlCommand = String.format(
                    "curl -X POST http://localhost:8000/api/query-payment-option-recommendation/ \\\n" +
                            "  -H \"Content-Type: application/json\" \\\n" +
                            "  -d '%s'",
                    jsonBody.replace("'", "'\\''")
            );
            System.out.println("Equivalent curl command:");
            System.out.println(curlCommand);

// Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

// Create HTTP entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

// Make API call
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8000/api/query-payment-option-recommendation/";
            return restTemplate.postForObject(url, entity, String.class);
        } catch (Exception e) {
            System.out.println("Error calling recommendation API: " + e.getMessage());
            return "Go with PayPal (fallback recommendation)";
        }
    }
}

package com.grab.hackathon.service;

import com.grab.hackathon.entity.BankCard;
import com.grab.hackathon.entity.GrabWallet;
import com.grab.hackathon.model.*;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentServiceProvider {

    @Autowired
    private RestTemplate restTemplate;

    public PSPResponse callPSPLambda(BankCard card) {
        // ✅ Create mock offers
        OffersDetails offer1 = OffersDetails.builder()
                .offerId("OFFER1001")
                .description("5% cashback on PayFast")
                .cashbackAmount(25.0)
                .build();

        OffersDetails offer2 = OffersDetails.builder()
                .offerId("OFFER2002")
                .description("Flat ₹50 off on QuickPay")
                .cashbackAmount(50.0)
                .build();

        List<OffersDetails> offers = Arrays.asList(offer1, offer2);
        List<String> gateways = Arrays.asList("PayFast", "QuickPay", "UPay");

        // ✅ Mock full response
        PSPResponse response = new PSPResponse();
        response.setOffers(offers);
        response.setAvailableGateways(gateways);
        response.setBankHealthStatus("HEALTHY");
        response.setRecommendedGateway("PayFast");

        return response;
    }
}

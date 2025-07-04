package com.grab.hackathon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PSPResponse {
    private List<OffersDetails> offers;
    private List<String> availableGateways;
    private String bankHealthStatus;
    private String recommendedGateway;
}

